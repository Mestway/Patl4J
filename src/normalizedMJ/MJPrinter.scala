package normalizedMJ

import normalizedMJ.ast._

package object MJPrinter {
  
  val basicIndent = "  "
  
  def newline = ()=>(println(" "))

  def printTree(indent:String, p: Program) {
    p match {
      case Program(classdecls) => {
        for (cd <- classdecls) {
          printTree(indent, cd)
          newline()
        }
      }
    }
  }
  
  def printTree(indent: String, cd: ClassDecl) {
    val in = indent + basicIndent
    cd match {
      case ClassDecl(c, d, fields, cnd, methods) => {
        println(indent + "class " + c + " extends " + d + " {")
        for (field <- fields)
          printTree(in, field)
        newline()
        printTree(in, cnd)
        newline()
        for (method <- methods)
          printTree(in, method)
        println(indent + "}")
      }
    }
  }
  
  def printTree(indent: String, field:FieldDecl) {
    field match {
      case FieldDecl(c, v) =>
        println(indent + c + " " + v + ";")
    }
  }
  
  def printTree(indent: String, cnd: ConstrDecl) {
    cnd match {
      case ConstrDecl(c, paras, cpstmts) => {
        print(indent + c + " " + "(")
        var first: Boolean = true
        for (para <- paras) {
          printArgList[VarBinding](para, first)
          first = false
        }
        println(") {")
        for (cpstmt <- cpstmts) {
          printTree(indent + basicIndent, cpstmt)
        }
        println(indent + "}")
      }
    }
  }
  
  def printTree(indent: String, method: MethodDecl) {
    method match {
      case MethodDecl(c, m, args, cpstmts, retval) => {
        print(indent + c + " " + m + "(")
        var first:Boolean = true
        for (arg <- args) {
          printArgList[VarBinding](arg, first)
          first = false
        }
        println(") {")
        for (cpstmt <- cpstmts)
          printTree(indent + basicIndent, cpstmt)
        println(indent + basicIndent + "return " + retval + ";")
        println(indent + "}\n")
      }
    }
  }
  
  def printTree(indent: String, cpstmt: CpStmt) {
    cpstmt match {
      case CStmt(s) => { println(indent + s.toString());}
      case CIf(x, ifb, elseb) => {
        println(indent + "if (" + x + ") {")
        for (cpstmt <- ifb)
          printTree(indent + basicIndent, cpstmt)
        println(indent + "} else {")
        for (cpstmt <- elseb)
          printTree(indent + basicIndent, cpstmt)
        println(indent + "}")
      }
      case CWhile(x, loops) => {
        println(indent + "while (" + x + ") {")
        for (cpstmt <- loops)
          printTree(indent + basicIndent, cpstmt)
        println(indent + "}")
      }
    }
  }
  
  def printArgList[T](s: T, first: Boolean) {
    first match {
      case true => print(s)
      case false => print(", " + s)
    }
  }
  
}