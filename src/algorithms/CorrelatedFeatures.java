package algorithms;

import java.util.ArrayList;

public class CorrelatedFeatures {
	public String f1,f2;
	public float correlation;
	public Line lin_reg;
	public float threshold;




	public CorrelatedFeatures(String a, String b , float num) {
		// TODO Auto-generated constructor stub
		this.f1 = a;
		this.f2 = b;
		this.correlation = num;
	}

	ArrayList<CorrelatedFeatures> correlated;
	ArrayList<TimeSeries.Feature> notCorrelated;

	public CorrelatedFeatures(ArrayList<CorrelatedFeatures> match, ArrayList<TimeSeries.Feature> notMatch) {
		this.correlated = match;
		this.notCorrelated = notMatch;
	}
	protected float getCorrelation() {
		return correlation;
	}
	protected void setCorrelation(float correlation) {
		this.correlation = correlation;
	}
	protected String getFeature1() {
		return f1;
	}
	protected void setFeature1(String f1) {
		this.f1 = f1;
	}
	protected String getFeature2() {
		return f2;
	}
	protected void setFeature2(String f2) {
		this.f2 = f2;
	}
	
	public CorrelatedFeatures(String feature1, String feature2, float correlation, Line lin_reg, float threshold) {
		this.f1 = feature1;
		this.f2 = feature2;
		this.correlation = correlation;
		this.lin_reg = lin_reg;
		this.threshold = threshold;
	}
	
}
