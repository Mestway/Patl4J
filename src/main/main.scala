import normalizedMJ.ast._
import normalizedMJ.MJParser
import normalizedMJ._
import scala.util.parsing._

object parser extends MJParser {
  def parse(input: String) = parseAll(p, input)
}

package object main {
  def main(args: Array[String]) {
    println("what happend?")
    //parser.parse("if(x) {x.f = x.f(a,b); x.f(a,b); if(x){x.f = x.f(a,b); x.f(a,b); }else{x.f = x.f(a,b);}}else{x.f = x.f(a,b);}")
    //parser.parse("while(x)    { if(x){x.f = x.f(a,b); x.f(a,b); }else{x.f = x.f(a,b); while(x){x=3;}} }")
    
    //parser.parse("C(A a,B y){this.super(a,y);}")
    val program: Program = parser.parse("class C extends Object { A x; B y; C(A a,B y){this.super(a,y);} A m(A a, B y){while(x)    { if(x){x.f = x.f(a,b); x.f(a,b); }else{x.f = x.f(a,b); while(x){x=3;}} } return x;} A m(A a, B y){while(x)    { if(x){x.f = x.f(a,b); x.f(a,b); }else{x.f = x.f(a,b); while(x){x=3;}} } return x;}}").get
    
    MJPrinter.printTree(" ", program)
  }
}