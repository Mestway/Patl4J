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

//		this.A = (b1+b2)*h/2;
//		this.P = 2 * (l + w);
//		this.biotNumber = h * (A / P) / k;
//		this.Ac = w * t;
//		this.m = Math.sqrt((double) (h * P) / (k * Ac));
//		this.Lc = l + Ac / P;
//		this.tetab = Tb - Tsigma;
//
//		this.infiniteLength = Math.sqrt(h * P * k * Ac) * tetab;
//		this.adiabatic = infiniteLength * Math.tanh(m * Lc);
//		this.convHeatT = infiniteLength
//				* ((Math.sinh(m * Lc) + (h / (m * k)) * Math.cosh(m * Lc)) 
//				/ (Math.cosh(m * Lc) + (h / (m * k)) * Math.sinh(m * Lc)));
//		
//		this.longFin=1/(m*l);
//		this.insulatedFin=Math.tanh(m*l)/(m*l);
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
