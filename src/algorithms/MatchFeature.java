package algorithms;
import java.util.ArrayList;
import algorithms.TimeSeries.Feature;
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

	ArrayList<MatchFeature> match;
	ArrayList<Feature> notMatch;

	public MatchFeature(ArrayList<MatchFeature> match, ArrayList<Feature> notMatch) {
		this.match = match;
		this.notMatch = notMatch;
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

}
