package normalizedMJ

import scala.util.parsing.combinator._
import scala.util.matching.Regex

class MJParser extends JavaTokenParsers {
  
  def id  = "[a-zA-Z][a-zA-Z]*".r // identifiers
  def optws = "\\s*".r // optional white space
  def ws = "\\s*".r // while space
  
  // program
  def p: Parser[ast.Program] =
    repsep(cd, "\\s*".r) ^^ { case cd => ast.Program(cd) }
  
  // class declaration
  def cd: Parser[ast.ClassDecl] = 
    "class"~id~"extends"~id~"{"~rep(fd <~ ";")~cnd~repsep(md,"\\s*".r)~"}" ^^
    {
      case k1~c~k2~d~lb~fds~cnd~mtds~rb 
        => ast.ClassDecl(c,d,fds,cnd,mtds)
    }
  
  // constructor
  def cnd: Parser[ast.ConstrDecl] = 
    id~"("~repsep(id~ws~id,",")~")"~"{"~repsep(cs,"\\s*".r)~"}" ^^
    { 
      case c~lb~decl~rb~lb2~cs~rb2 
        => ast.ConstrDecl(c,decl.map((x)=>ast.VarBinding(x._1._1,x._2)),cs)
    }
  
  // field declaration
  def fd: Parser[ast.FieldDecl] =
    id~ws~id ^^ 
    { case c~w~f => ast.FieldDecl(c,f) }
  
  // method declaration
  def md: Parser[ast.MethodDecl] = 
    id~ws~id~"("~repsep(id~ws~id,",")~")"~"{"~repsep(cs,"\\s*".r)~"return"~id~";"~"}" ^^
    { 
      case c~w1~m~lb~decl~rb~lb2~cs~ret~x~col~rb2 => 
        ast.MethodDecl(c,m,decl.map((x)=>ast.VarBinding(x._1._1,x._2)),cs,x)
    } 
  
  // compound statements
  def cs: Parser[ast.CpStmt] = 
    // single statement
    s~";" ^^ { case s~col => ast.CStmt(s)} |
    // if statment
    "if"~optws~"("~id~")"~optws~"{"~repsep(cs,"\\s*".r)~"}"~optws~"else"~optws~"{"~repsep(cs,"\\s*".r)~"}" ^^
    { case k1~w0~lb~x~rb~w1~lb2~cs1~rb2~w2~k2~w3~lb3~cs2~rb3 => ast.CIf(x,cs1,cs2)} |
    // while statement
    "while"~optws~"("~id~")"~optws~"{"~repsep(cs,";")~"}" ^^
    { case k1~w0~lb~x~rb~w1~lb2~cs1~rb2 => ast.CWhile(x,cs1)}
    
  // statement
  def s : Parser[ast.Stmt] = 
    // assignment to a value
    id~optws~"="~optws~e ^^ 
    { case x~w1~eq~w2~e => ast.SVAssign(x, e)} |
    // assignment to a field
    id~"."~id~optws~"="~optws~e ^^
    { case x~dot~f~w1~eq~w2~e => ast.SFAssign(x, f, e)} |
    // promotable expression
    pe ^^ { case p => ast.SPExp(p)}
  
  // expression
  def e : Parser[ast.Exp]  = 
    // promotable expression
    pe ^^ { case p => ast.EPExp(p) } |
    // field access
    id~"."~id ^^ 
    { case x~dot~f => ast.EField(x,f) } |
    // cast expression
    "("~id~")"~optws~id ^^
    { case lb~c~rb~v => ast.ECast(c,v) } |
    // variable
    id ^^ { case x => ast.EVar(x) } |
    // Integer
    this.decimalNumber ^^ { case x => ast.EVal(x) } |
    // String
    this.stringLiteral ^^ { case x => ast.EVal(x) } |
    // Floating point
    this.floatingPointNumber ^^ { case x => ast.EVal(x) }
  
  // promotable expression
  def pe : Parser[ast.PExp] = 
    //method invocation
    id~"."~id~"("~repsep(id,",")~")" ^^ 
    { case x~dot~m~lb~list~rb => ast.ECall(x,m,list) } |
    //new object
    "new"~id~"("~repsep(id,",")~")" ^^
    { case nw~c~lb~list~rb => ast.ENew(c,list) }
}