package patl

import scala.util.parsing.combinator._
import scala.util.matching.Regex
import patl.PATLAst._

package object PATLParser extends JavaTokenParsers {
  
  def id  = "[a-zA-Z][a-zA-Z0-9]*".r // identifiers
  def optws = "\\s*".r // optional white space
  def ws = "\\s*".r // while space
  
  object labelCounter {
    var count = 1;
    def genCount(): Integer = { 
      val temp = count; 
      count = count + 1; 
      temp
    }
  }
  
  // rule sequence
  def PiS: Parser[RuleSeq] =
    rep(pi) ^^ { case ruleseq => RuleSeq(ruleseq)}
  
  // rule
  def pi: Parser[Rule] = 
    id~"("~repsep(decl, ",")~")"~"{"~rep(i)~"}" ^^ 
    { case n~lb~decls~rb~lb2~instrs~rb2 => Rule(n,decls,instrs) }
  
  // variable declaration
  def decl: Parser[VarDecl] = 
    id~":"~id~"->>"~id ^^ {case x~dot~c~arr~d => VarDecl(x, c, d) }
    
  // instruction
  def i: Parser[Instr] = 
    "+"~p~";" ^^ { case add~p~column => { val t = AddInstr(p); t.setLabel(labelCounter.genCount()); t } } |
    "-"~p~";" ^^ { case sub~p~column => { val t = SubInstr(p); t.setLabel(labelCounter.genCount()); t } } |
    "m"~p~";" ^^ { case mod~p~column => { val t = ModInstr(p); t.setLabel(labelCounter.genCount()); t } }
  
  // statement pattern
  def p: Parser[StmtPattern] = 
    id~"="~r ^^ { case x~eq~r => SAPattern(x, r) } |
    e ^^ {case e => SPEPattern(e) }
  
  // right hand side pattern
  def r: Parser[RPattern] = 
    e ^^ { case e => RPEPattern(e) } |
    id~"."~id ^^ { case x~dot~f => RFPattern(x,f) }
  
  // promotable expression pattern
  def e: Parser[PEPattern] = 
    id~"."~id~"("~repsep(id,",")~")" ^^
    { case x~dot~m~lb~args~rb => MethodPattern(x,m,args)} |
    "new"~id~"("~repsep(id,",")~")" ^^
    { case k1~c~lb~args~rb => NewPattern(c,args)}
  
}