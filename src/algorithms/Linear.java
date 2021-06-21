package algorithms;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import algorithms.TimeSeries.Feature;


public class Linear implements TimeSeriesAnomalyDetector {

	ArrayList<CorrelatedFeatures> corFeaturesLs = new ArrayList<CorrelatedFeatures>();
	HashMap<String,String> mapL=new HashMap<>();

	public void learnNormal(TimeSeries ts) {
		ArrayList<Feature> featureArray = ts.getTable();
		float maximum = 0;
		float [] mainf;
		float [] subf;
		int index = 0;
		float result;
		float threshold = 0;
		for (int i = 0; i < featureArray.size(); i++) {
			mainf= StatLib.FloatListToFloatArr(featureArray.get(i).getExamples());
			for (int j = 0; j < featureArray.size(); j++) {
				if (j != i) {
					 subf = StatLib.FloatListToFloatArr(featureArray.get(j).getExamples());
					result  = StatLib.pearson(mainf, subf);
					result = Math.abs(result);
					if ( result > 0.9 && result >= maximum) {
						maximum = result;
						index = j;
					}
				}	
			}
			if (maximum != 0) {
				float [] subfea = StatLib.FloatListToFloatArr(featureArray.get(index).getExamples());
				Point[] points = gen_points(mainf, subfea);
				Line lrg = StatLib.linear_reg(points);
				for (Point point : points) {
					float dev = StatLib.dev(point, lrg);
					if (dev>threshold ) {
						threshold = dev;
					}
				}
				CorrelatedFeatures cofe = new CorrelatedFeatures(featureArray.get(i).getName(), featureArray.get(index).getName(), maximum, lrg,(float) (threshold+0.025));
				if (!contain(cofe)) {
					this.corFeaturesLs.add(cofe);
				}
				mapL.put(featureArray.get(i).getNameId(),featureArray.get(index).getNameId());

			}
			maximum =0;
		}
	}

	@Override
	public List<AnomalyReport> detect(TimeSeries ts) {
		String description;
		float[] f1;
		float[] f2;
		int index2 = 1;
		ArrayList<AnomalyReport> arrayl = new ArrayList<AnomalyReport>();
		for (CorrelatedFeatures correlatedFeature : this.corFeaturesLs) {
			ArrayList<Float> t1 = ts.getFeatureByName(correlatedFeature.f1);
			ArrayList<Float> t2 = ts.getFeatureByName(correlatedFeature.f2);
			description =  correlatedFeature.f1 + "-" + correlatedFeature.f2;
			 f1 = StatLib.FloatListToFloatArr(t1);
			 f2 = StatLib.FloatListToFloatArr(t2);
			Point[] points = gen_points(f1, f2);
			for (Point p : points) {
				float dev_temp = StatLib.dev(p, correlatedFeature.lin_reg);
				if ( dev_temp > correlatedFeature.threshold) {
					String d = ts.getFeatureByName1(correlatedFeature.f1).nameId + "-" + ts.getFeatureByName1(correlatedFeature.f2).nameId;
					AnomalyReport ar = new AnomalyReport(d, index2);
					arrayl.add(ar);
				}
				index2++;
			}	
		}
		return arrayl;
	}
	
	public List<CorrelatedFeatures> getNormalModel(){
		return this.corFeaturesLs;
	}
	public Point[]gen_points(float[] x, float[] y) {
		Point[] p_array = new Point[x.length];
		for (int i = 0; i < p_array.length; i++) {
			p_array[i] = new Point(x[i], y[i]);
		}
		return p_array;
	}

	public boolean contain(CorrelatedFeatures cof) {
		boolean flag = false;
		for (CorrelatedFeatures correlatedFeature : this.corFeaturesLs) {
			if (  correlatedFeature.f2.equals(cof.f1) && correlatedFeature.f1.equals(cof.f2)){
				flag = true;
			}
		}
		return flag;
	}

	public HashMap<String, String> getMapL() {
		return mapL;
	}

	public void setMapL(HashMap<String, String> mapL) {
		this.mapL = mapL;
	}
}
