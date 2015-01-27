package normalizedMJ

import scala.util.parsing.combinator._
import scala.util.matching.Regex
import normalizedMJ.ast._

package object MJParser extends JavaTokenParsers {
  
  def id  = "[a-zA-Z][a-zA-Z0-9]*".r // identifiers
  def optws = "\\s*".r // optional white space
  def ws = "\\s*".r // while space
  
  // Label counter to generate label
  object labelCounter {
    var count:Integer = 1
    def genLabel():Integer = {
      val temp:Integer = count
      count = count + 1
      return temp
    }
    def refresh {
      count = 1
    }
  }
  
  // program
  def p: Parser[Program] =
    repsep(cd, "\\s*".r) ^^ { case cd => Program(cd) }
  
  // class declaration
  def cd: Parser[ClassDecl] = 
    "class"~id~"extends"~id~"{"~rep(fd <~ ";")~cnd~repsep(md,"\\s*".r)~"}" ^^
    {
      case k1~c~k2~d~lb~fds~cnd~mtds~rb 
        => ClassDecl(c,d,fds,cnd,mtds)
    }
  
  // constructor
  def cnd: Parser[ConstrDecl] = 
    id~"("~repsep(id~ws~id,",")~")"~"{"~repsep(cs,"\\s*".r)~"}" ^^
    { 
      case c~lb~decl~rb~lb2~cs~rb2 
        => { labelCounter.refresh; ConstrDecl(c,decl.map((x)=>VarBinding(x._1._1,x._2)),cs) }
    }
  
  // field declaration
  def fd: Parser[FieldDecl] =
    id~ws~id ^^ 
    { case c~w~f => FieldDecl(c,f) }
  
  // method declaration
  def md: Parser[MethodDecl] = 
    id~ws~id~"("~repsep(id~ws~id,",")~")"~"{"~repsep(cs,"\\s*".r)~"return"~id~";"~"}" ^^
    { 
      case c~w1~m~lb~decl~rb~lb2~cs~ret~x~col~rb2 => 
      { labelCounter.refresh; MethodDecl(c,m,decl.map((x)=>VarBinding(x._1._1,x._2)),cs,x) }
    } 
  
  // compound statements
  def cs: Parser[CpStmt] = 
    // single statement
    s~";" ^^ { case s~col => CStmt(s) } |
    // if statment
    "if"~optws~"("~id~")"~optws~"{"~repsep(cs,"\\s*".r)~"}"~optws~"else"~optws~"{"~repsep(cs,"\\s*".r)~"}" ^^
    { case k1~w0~lb~x~rb~w1~lb2~cs1~rb2~w2~k2~w3~lb3~cs2~rb3 => CIf(x,cs1,cs2) } |
    // while statement
    "while"~optws~"("~id~")"~optws~"{"~repsep(cs,"\\s*".r)~"}" ^^
    { case k1~w0~lb~x~rb~w1~lb2~cs1~rb2 => CWhile(x,cs1) }
    
  // statement
  def s : Parser[Stmt] = 
    // assignment to a value
    id~optws~"="~optws~e ^^ 
    { case x~w1~eq~w2~e => { val t = SVAssign(x, e); t.genLabel(labelCounter.genLabel()); t } } |
    // assignment to a field
    id~"."~id~optws~"="~optws~e ^^
    { case x~dot~f~w1~eq~w2~e => { val t = SFAssign(x, f, e); t.genLabel(labelCounter.genLabel()); t } } |
    // promotable expression
    pe ^^ { case p => { val t = SPExp(p); t.genLabel(labelCounter.genLabel()); t }}
  
  // expression
  def e : Parser[Exp]  = 
    // promotable expression
    pe ^^ { case p => EPExp(p) } |
    // field access
    id~"."~id ^^ 
    { case x~dot~f => EField(x,f) } |
    // cast expression
    "("~id~")"~optws~id ^^
    { case lb~c~rb~v => ECast(c,v) } |
    // variable
    id ^^ { case x => EVar(x) } |
    // Integer
    this.decimalNumber ^^ { case x => EVal(x) } |
    // String
    this.stringLiteral ^^ { case x => ast.EVal(x) } |
    // Floating point
    this.floatingPointNumber ^^ { case x => ast.EVal(x) }
  
  // promotable expression
  def pe : Parser[PExp] = 
    //method invocation
    id~"."~id~"("~repsep(id,",")~")" ^^ 
    { case x~dot~m~lb~list~rb => ECall(x,m,list) } |
    //new object
    "new"~id~"("~repsep(id,",")~")" ^^
    { case nw~c~lb~list~rb => ENew(c,list) }
}