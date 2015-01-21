package normalizedMJ

import scala.collection.immutable.ListMap
import scala.collection.immutable.Map

package object ast {
  type X = String // variable name
  type C = String // class name
  type M = String // method name
  type V = String // value
  
  case class Program(class_decls: List[ClassDecl])
  
  case class ClassDecl( cn: C, 
                        parentclass: C, 
                        fields: List[FieldDecl],
                        constructor: ConstrDecl,
                        methods: List[MethodDecl])
  
  case class FieldDecl(c:C, f:V)
  
  case class ConstrDecl(cn: C, 
                        params: List[VarBinding],
                        cpstmts: List[CpStmt])
                        
  case class MethodDecl(rettype: C,
                        methodname: M,
                        arguments: List[VarBinding],
                        cpstmts: List[CpStmt],
                        retval: X)
                        
  case class VarBinding(c: C, f: V) {
    override def toString() = c.toString() + " " + f.toString()
  }
  
  abstract class CpStmt
    case class CStmt(s: Stmt) extends CpStmt 
    case class CIf(x: X, ifbrach: List[CpStmt], elsebranch: List[CpStmt]) extends CpStmt
    case class CWhile(x: X, loop: List[CpStmt]) extends CpStmt
  
  class Stmt
  case class SVAssign(x: X, exp: Exp) extends Stmt {
     override def toString = x + " = " + exp.toString()
  }
  case class SFAssign(x: X, field: X, exp: Exp) extends Stmt {
    override def toString = x + "." + field + " = " + exp.toString()
  }
  case class SPExp(exp: PExp) extends Stmt {
    override def toString = exp.toString()
  }
  
  class Exp
  case class ENull() extends Exp {
    override def toString = "null"
  }
  case class EVar(x: X) extends Exp {
    override def toString = x
  }
  case class EField(x: X, f: X) extends Exp {
    override def toString = x + "." + f
  }
  case class ECast(c: C, x: X) extends Exp {
    override def toString = "(" + c + ") " + x
  }
  case class EPExp(exp: PExp) extends Exp {
    override def toString = exp.toString()
  }
  case class EVal(v: V) extends Exp {
    override def toString = v
  }
  
  class PExp
  case class ECall(x: X, m: M, arguments: List[X]) extends PExp {
    override def toString:String = {
      var first: Boolean = true;
      var s:String = "";
      for( arg <- arguments ) {
        if (first) {
          s += arg; first = false;
        } else 
          s += ", " + arg;
      }
      x + "." + m + "(" + s + ")"
    }
  }
  case class ENew(c: C, arguments: List[X]) extends PExp {
    override def toString:String = {
      var first: Boolean = true;
      var s:String = "";
      for( arg <- arguments ) {
        if (first){ 
          s += arg; first = false;
        } else 
          s += ", " + arg;
      }
      c + "(" + s + ")"
    }
  }
}