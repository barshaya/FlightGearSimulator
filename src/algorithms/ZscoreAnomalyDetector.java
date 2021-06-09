package algorithms;

import java.util.ArrayList;
import java.util.List;

public class ZscoreAnomalyDetector implements TimeSeriesAnomalyDetector{
	ArrayList<Double> t_x = new ArrayList<Double>();

	@Override
	public void learnNormal(TimeSeries ts) {
		// TODO Auto-generated method stub
		for (TimeSeries.Feature f : ts.getTable()) {
			double max = -1;
			for (int i = 2; i < f.getSamples().size(); i++) {
				double x_avg = StatLib.avg( StatLib.al_to_fl( f.getSamples().subList(0, i)));
				double std =  Math.sqrt(StatLib.var(StatLib.al_to_fl(f.getSamples().subList(0, i))));
				for (int j = 0; j < i; j++) {
					double z_score;
					if (std != 0 ) {
						z_score = Math.abs( f.getSamples().get(j) - x_avg) / std;
					}
					else {
						z_score = 0;
					}
					if (z_score > max) {
						max = z_score;
					}
				}	
			}
			t_x.add(max);
			max = -1;
		}
	}
		

	@Override
	public List<AnomalyReport> detect(TimeSeries ts) {
		ArrayList<AnomalyReport> arl = new ArrayList<AnomalyReport>();
		// TODO Auto-generated method stub
		for (int i = 0; i < ts.getTable().size(); i++) {
			TimeSeries.Feature f = ts.getTable().get(i);
			for (int j = 2; j < f.getSamples().size(); j++) {
				double x_avg = StatLib.avg( StatLib.al_to_fl( f.getSamples().subList(0, j)));
				double std =  Math.sqrt(StatLib.var(StatLib.al_to_fl(f.getSamples().subList(0, j))));
				if (std != 0 ) {
					for (int k = 0; k < j; k++) {
						double z_score = Math.abs( f.getSamples().get(k) - x_avg) / std;
						if (z_score > t_x.get(i)) {
							AnomalyReport ar = new AnomalyReport(f.getName_id(), k+1);
							if (!StatLib.isContain(arl, ar)) {
								arl.add(ar);
							}
						}
					}
				}
			}	
		}
		
		return arl;
	}


	@Override
	public Runnable paint() {
		// TODO Auto-generated method stub
		return null;
	}

}
