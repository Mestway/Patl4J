import normalizedMJ.ast._
import normalizedMJ._
import normalizedMJ._
import patl.PATLParser
import patl.PATLAst._
import scala.util.parsing._
import scala.io._

package object main {
  def main(args: Array[String]) {
    println("what happend?")
    
    val javaSource = Source.fromFile("testfile\\MJ-prog1.mj")
    val javalines = javaSource.mkString
    javaSource.close()
    
    val patlSource = Source.fromFile("testfile\\prog1.ptal")
    val patllines = patlSource.mkString
    javaSource.close()
    
    val program: Program = MJParser.parseAll[Program](MJParser.p, javalines).get
    val patlprogram:RuleSeq = PATLParser.parseAll[RuleSeq](PATLParser.PiS, patllines).get
   
    println(patlprogram)
    MJPrinter.printTree("", program)
  }
}