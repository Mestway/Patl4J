package models;

public class CylinderFin  implements FinInterface{
	
	private double l;
	private double d;
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
	
	

	public CylinderFin(double l, double d, double tsigma, double tb, int n,
			double k, double h) {
		super();
		this.l = l;
		this.d = d;
		Tsigma = tsigma;
		Tb = tb;
		N = n;
		this.k = k;
		this.h = h;
		
		this.P = Math.PI*d;
		this.biotNumber = h * (A / P) / k;
		this.Ac = Math.PI*(Math.pow(d, 2))/4;
		this.m = Math.sqrt((double) (h * P) / (k * Ac));
		this.Lc = l + Ac / P;
		this.tetab = Tb - Tsigma;

		this.infiniteLength = Math.sqrt(h * P * k * Ac) * tetab;
		this.adiabatic = infiniteLength * Math.tanh(m * Lc);
		this.convHeatT = infiniteLength
				* ((Math.sinh(m * Lc) + (h / (m * k)) * Math.cosh(m * Lc)) 
				/ (Math.cosh(m * Lc) + (h / (m * k)) * Math.sinh(m * Lc)));
		
		this.longFin=1/(m*l);
		this.insulatedFin=Math.tanh(m*l)/(m*l);
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