package patl

package object PATLAst {
  
  type X = String // name
  type C = String // type
  
  case class RuleSeq(rules: List[Rule])
  
  case class Rule(id: String, decls: List[VarDecl], instrs: List[Instr])
  
  case class VarDecl(x: X, c: C, d: C)
  
  abstract class Instr {
    var label: Integer = 0;
    def setLabel(l: Integer) = label = l;
  }
  case class ModInstr(p: StmtPattern) extends Instr
  case class AddInstr(p: StmtPattern) extends Instr
  case class SubInstr(p: StmtPattern) extends Instr
  
  abstract class StmtPattern
  // Assignment pattern, e.g. x = x.f
  case class SAPattern(x: X, r: RPattern) extends StmtPattern
  // Promotable expression pattern
  case class SPEPattern(x: PEPattern) extends StmtPattern
  
  abstract class RPattern
  case class RPEPattern(x: PEPattern) extends RPattern
  case class RFPattern(x: X, f: X) extends RPattern
  
  abstract class PEPattern
  case class MethodPattern(x: X, m: X, args: List[X]) extends PEPattern
  case class NewPattern(c: C, args: List[X]) extends PEPattern
    
}