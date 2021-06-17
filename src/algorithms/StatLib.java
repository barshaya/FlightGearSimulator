package algorithms;

import java.util.ArrayList;
import java.util.List;
import algorithms.TimeSeries.Feature;

public class StatLib {


	public static float avg(float[] x){
		float sum=0;
		for(int i=0;i<x.length;sum+=x[i],i++);
		return sum/x.length;
	}

	public static float var(float[] x){
		float av=avg(x);
		float sum=0;
		for(int i=0;i<x.length;i++){
			sum+=x[i]*x[i];
		}
		return sum/x.length - av*av;
	}

	public static float cov(float[] x, float[] y){
		float sum=0;
		for(int i=0;i<x.length;i++){
			sum+=x[i]*y[i];
		}
		sum/=x.length;
		return sum - avg(x)*avg(y);
	}


	public static float pearson(float[] x, float[] y){
		return (float) (cov(x,y)/(Math.sqrt(var(x))*Math.sqrt(var(y))));
	}

	public static Line linear_reg(Point[] points){
			float x[]=new float[points.length];
			float y[]=new float[points.length];
			for(int i=0;i<points.length;i++){
				x[i]=points[i].x;
				y[i]=points[i].y;
			}
			float a=cov(x,y)/var(x);
			float b=avg(y) - a*(avg(x));

			return new Line(a,b);
		}

	public static float dev(Point p,Point[] points){
		Line lrg = linear_reg(points);
		return Math.abs(lrg.f(p.x) - p.y);
	}

	public static float dev(Point p,Line l){
		return Math.abs(p.y-l.f(p.x));
	}
	public static String RevString(String s) {
	        byte[] strAsByteArray = s.getBytes();
	        byte[] result = new byte[strAsByteArray.length];
	        for (int i = 0; i < strAsByteArray.length; i++)
	            result[i] = strAsByteArray[strAsByteArray.length - i - 1];
	        return new String(result);
	    
	}

	public static float[]  FloatListToFloatArr(List<Float> list) {
		int i = 0;
		float[] array = new float[list.size()];
		for (Float value: list) {
			array[i++] = value;
		}
		return array;
	}

	public static boolean isContain(ArrayList<AnomalyReport> arrayl, AnomalyReport array) {
		for (AnomalyReport anomalyReport : arrayl) {
			if (anomalyReport.description.equals(array.description) &&
					anomalyReport.timeStep == array.timeStep)
			{
				return true;
			}
		}
		return false;
	}
	public static CorrelatedFeatures FindCorrelatedFeatures(TimeSeries ts){
		CorrelatedFeatures matchf = null ;
		ArrayList<CorrelatedFeatures> match = new ArrayList<CorrelatedFeatures>();
		ArrayList<Feature> notMatch = new ArrayList<Feature>();
		float max =-1;
		for (int i = 0; i < ts.getTable().size()-1; i++) {
			TimeSeries.Feature f1 = ts.getTable().get(i);
			for (int j = i+1; j < ts.getTable().size(); j++) {
				TimeSeries.Feature f2 = ts.getTable().get(j);
				float cor = Math.abs(pearson(FloatListToFloatArr(f1.getExamples()), FloatListToFloatArr(f2.getExamples())));
				if(max<cor) {
					max = cor;
					matchf = new CorrelatedFeatures(f1.getName(), f2.getName(), cor);
				}
			}
			if (matchf != null) {
				match.add(matchf);
			}
			else {
				notMatch.add(f1);
			}
			max = -1;
			matchf= null;
		}
		return new CorrelatedFeatures(match, notMatch);
	}
}
