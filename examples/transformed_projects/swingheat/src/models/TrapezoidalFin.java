package models;

public class TrapezoidalFin implements FinInterface{
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

	public TrapezoidalFin(double b1, double b2, double height, double Tsigma,
			double Tb, int N, double k, double h) {
		this.b1 = b1;
		this.b2 = b2;
		this.height = height;
		this.Tsigma = Tsigma;
		this.Tb = Tb;
		this.N = N;
		this.k = k;
		this.h = h;

	}

	@Override
	public double getBiotNumber() {
		return biotNumber;
	}

	@Override
	public double getConvHeatT() {
		return convHeatT*N;
	}

	@Override
	public double getAdiabatic() {
		return adiabatic*N;
	}

	@Override
	public double getInfiniteLength() {
		return infiniteLength*N;
	}

	@Override
	public double getLongFin() {
		return longFin;
	}

	@Override
	public double getInsulatedFin() {
		return insulatedFin;
	}
}
