package algorithms;

public class MatchFeature {

	String f1;
	String f2;
	float correlation;
	
	
	public MatchFeature(String a, String b , float num) {
		// TODO Auto-generated constructor stub
		this.f1 = a;
		this.f2 = b;
		this.correlation = num;
	}


	protected String getF1() {
		return f1;
	}


	protected void setF1(String f1) {
		this.f1 = f1;
	}


	protected String getF2() {
		return f2;
	}


	protected void setF2(String f2) {
		this.f2 = f2;
	}


	protected float getCorrelation() {
		return correlation;
	}


	protected void setCorrelation(float correlation) {
		this.correlation = correlation;
	}
	
}
