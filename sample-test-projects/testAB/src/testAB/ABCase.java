package testAB;
import api_a.A;
import api_b.B;
import api_b.IntPair;
public class ABCase {
  Integer field;
  public ABCase(  Integer fd){
    testAB.ABCase genVar0;
    genVar0=this;
    genVar0.field=fd;
    testAB.ABCase genVar1;
    genVar1=this;
    genVar1.print();
    int genVar2;
    genVar2=1;
    field=fd + genVar2;
  }
  public void print(){
    System.out.println(field);
  }
  public static void main(  String[] args){
    java.lang.String genVar3;
    genVar3="Start print";
    System.out.println(genVar3);
    A a;
    a=new A();
    java.lang.String genVar4;
    genVar4=a.toString();
    System.out.println(genVar4);
    int genVar5;
    genVar5=0;
    MyInt my;
    my=new MyInt(genVar5);
    my.z.y=1;
    testAB.YourInt genVar6;
    genVar6=my.getZ();
    genVar6.y=1;
    int x;
    x=my.x;
    int genVar7;
    genVar7=0;
    boolean genVar8;
    genVar8=x > genVar7;
    if (genVar8) {
      int genVar9;
      genVar9=1;
      x=x + genVar9;
    }
 else {
      ;
    }
    int genVar10;
    genVar10=0;
    boolean genVar11;
    genVar11=x > genVar10;
    if (genVar11) {
      int u;
      u=10;
      int v;
      v=20;
      a.set(u,v);
      a.lAdd();
    }
 else {
      A d;
      d=new A();
      int i;
      i=1;
      int j;
      j=5;
      d.set(i,j);
      d.rDec();
    }
    a.print();
  }
}
