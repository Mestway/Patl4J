package models;
public class TrapezoidalFin implements FinInterface {
  private double b1;
  private double b2;
  private double height;
  private double Tsigma;
  private double Tb;
  private int N;
  private double k;
  private double h;
  private double biotNumber;
  private double A;
  private double P;
  private double m;
  private double Ac;
  private double Lc;
  private double tetab;
  private double convHeatT;
  private double adiabatic;
  private double infiniteLength;
  private double longFin;
  private double insulatedFin;
  public TrapezoidalFin(  double b1,  double b2,  double height,  double Tsigma,  double Tb,  int N,  double k,  double h){
    models.TrapezoidalFin genVar1;
    genVar1=this;
    genVar1.b1=b1;
    models.TrapezoidalFin genVar2;
    genVar2=this;
    genVar2.b2=b2;
    models.TrapezoidalFin genVar3;
    genVar3=this;
    genVar3.height=height;
    models.TrapezoidalFin genVar4;
    genVar4=this;
    genVar4.Tsigma=Tsigma;
    models.TrapezoidalFin genVar5;
    genVar5=this;
    genVar5.Tb=Tb;
    models.TrapezoidalFin genVar6;
    genVar6=this;
    genVar6.N=N;
    models.TrapezoidalFin genVar7;
    genVar7=this;
    genVar7.k=k;
    models.TrapezoidalFin genVar8;
    genVar8=this;
    genVar8.h=h;
  }
  @Override public double getBiotNumber(){
    return biotNumber;
  }
  @Override public double getConvHeatT(){
    double genVar9;
    genVar9=convHeatT * N;
    return genVar9;
  }
  @Override public double getAdiabatic(){
    double genVar10;
    genVar10=adiabatic * N;
    return genVar10;
  }
  @Override public double getInfiniteLength(){
    double genVar11;
    genVar11=infiniteLength * N;
    return genVar11;
  }
  @Override public double getLongFin(){
    return longFin;
  }
  @Override public double getInsulatedFin(){
    return insulatedFin;
  }
}
