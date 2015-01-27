package patl

import patl.PATLAst._
import normalizedMJ.ast._
import scala.collection.mutable.{Map, HashMap}

object PATLMatcher {
  
  type X = String // variable type
  type I = Integer // index val
  
  case class MatcherSet(m: List[Matcher])
  
  case class Matcher(rule: Rule, vb: Bindings[X, MJVal], rb: Bindings[I, I]) {
    
    def matchPoint(): (StmtPattern, I) = {
      for ( i <- rule.instrs ) {
        if (! rb.lookup(i.label)) {
          i match {
            case ModInstr(s) => return (s, i.label);
            case SubInstr(s) => return (s, i.label);
            case _ => ;
          } 
        }
      }
      (null, null)
    }
    
    def checkVarMatch(sp: StmtPattern, s: Stmt): Boolean = {
      
    }
  }
  
  abstract class MJVal
  case class MJVar(x: X) extends MJVal
  case class MJField(x: X, f: X) extends MJVal
  
  case class Bindings[L,R]() {
    case class Binding(l: L, r: R)
    var map: Map[L, Binding] = new HashMap();
    def addBinding(l: L, r: R) {
      map.+((l, Binding(l, r)))
    }
    
    def lookup(label: L): Boolean = map.contains(label)
  }
}