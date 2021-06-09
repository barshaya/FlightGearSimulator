package algorithms;


import java.util.ArrayList;
import java.util.List;

import algorithms.TimeSeries.Feature;



public class LinearAnomalyDetector implements TimeSeriesAnomalyDetector {

	ArrayList<CorrelatedFeatures> cofeatures_ls = new ArrayList<CorrelatedFeatures>();
	
	

	public Point[] points_gen(float[] x, float[] y) {
		Point[] p_array = new Point[x.length];
		for (int i = 0; i < p_array.length; i++) {
			p_array[i] = new Point(x[i], y[i]);
		}
		return p_array;
	}
	
	public boolean contain(CorrelatedFeatures cof) {
		boolean b = false;
		for (CorrelatedFeatures correlatedFeature : this.cofeatures_ls) {
			if (correlatedFeature.feature1.equals(cof.feature2) && correlatedFeature.feature2.equals(cof.feature1)){
				b = true;
			}
		}
		return b;
	} 
	
	
	public void learnNormal(TimeSeries ts) {
		float max = 0;
		ArrayList<Feature> f_array = ts.getTable();
		for (int i = 0; i < f_array.size(); i++) {
			float [] mainfeature = StatLib.al_to_fl(f_array.get(i).getSamples());
			int index = 0;
			for (int j = 0; j < f_array.size(); j++) {
				if (i != j) {
					float [] subfeature = StatLib.al_to_fl(f_array.get(j).getSamples());
					float res  = StatLib.pearson(mainfeature, subfeature);
					res = Math.abs(res);
					if (res >= max && res > 0.9) {
						max = res;
						index = j;
					}
				}	
			}
			if (max != 0) {
				float [] subfeature = StatLib.al_to_fl(f_array.get(index).getSamples());
				Point[] points = points_gen(mainfeature, subfeature);
				Line lrg = StatLib.linear_reg(points);
				float threshold = 0;
				for (Point point : points) {
					float dev = StatLib.dev(point, lrg);
					if (threshold < dev) {
						threshold = dev;
					}
				}
				CorrelatedFeatures cof = new CorrelatedFeatures(f_array.get(i).getName(), f_array.get(index).getName(), max, lrg,(float) (threshold+0.025));
				if (!contain(cof)) {
					this.cofeatures_ls.add(cof);
				}
			}
			max =0;
			
		}
	}


	@Override
	public List<AnomalyReport> detect(TimeSeries ts) {
		ArrayList<AnomalyReport> arl = new ArrayList<AnomalyReport>();
		for (CorrelatedFeatures correlatedFeature : this.cofeatures_ls) {
			String discription =  correlatedFeature.feature1 + "-" + correlatedFeature.feature2;
			ArrayList<Float> t1 = ts.getFeatureByName(correlatedFeature.feature1);
			ArrayList<Float> t2 = ts.getFeatureByName(correlatedFeature.feature2);
			float[] f1 = StatLib.al_to_fl(t1);
			float[] f2 = StatLib.al_to_fl(t2);
			Point[] points = points_gen(f1, f2);
			int tindex = 1;
			for (Point p : points) {
				float dev_temp = StatLib.dev(p, correlatedFeature.lin_reg);
				if ( dev_temp > correlatedFeature.threshold) {
					String d = ts.getFeatureByName2(correlatedFeature.feature1).name_id + "-" + ts.getFeatureByName2(correlatedFeature.feature2).name_id;
					AnomalyReport ar = new AnomalyReport(d, tindex);
						arl.add(ar);
				}
				tindex++;
			}	
		}
		return arl;
	}
	
	public List<CorrelatedFeatures> getNormalModel(){
		return this.cofeatures_ls;
	}

	@Override
	public Runnable paint() {
		// TODO Auto-generated method stub
		return null;
	}
}
