package testAB;
import api_a.A;
import api_b.B;
import api_b.IntPair;
public class ABCase {
  public static void main(  String[] args){
    System.out.println("Start print");
    A a=new A();
    System.out.println(a.toString());
    MyInt my=new MyInt(0);
    my.z.y=1;
    my.getZ().y=1;
    int x=my.x;
    if (x > 0) {
      x=x + 1;
    }
    if (x > 0) {
      int u=10;
      int v=20;
      a.set(u,v);
      a.lAdd();
    }
 else {
      A d=new A();
      int i=1;
      int j=5;
      d.set(i,j);
      d.rDec();
    }
    a.print();
  }
}
