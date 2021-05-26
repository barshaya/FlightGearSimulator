package inputOutput;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import algorithms.SimpleAnomalyDetector;
import algorithms.TimeSeries;
import algorithms.AnomalyReport;

public class Commands {
	
	// Default IO interface
	public interface DefaultIO{
		public String readText();
		public void write(String text);
		public float readVal();
		public void write(float val);

		// you may add default methods here
	}
	
	// the default IO to be used in all commands
	DefaultIO dio;
	public Commands(DefaultIO dio) {
		this.dio=dio;
	}
	
	// you may add other helper classes here
	
	
	
	public SharedState getSharedState() {
		return sharedState;
	}

	public void setSharedState(SharedState sharedState) {
		this.sharedState = sharedState;
	}

	// the shared state of all commands
	private class SharedState{
		// implement here whatever you need
		SimpleAnomalyDetector anomalyDetector= new SimpleAnomalyDetector();
		List<AnomalyReport> detect = new ArrayList<AnomalyReport>();
	
		
		
	}
	
	private  SharedState sharedState=new SharedState();

	
	// Command abstract class
	public abstract class Command{
		protected String description;
		
		public Command(String description) {
			this.description=description;
		}
		
		public abstract void execute();
	}
	
	
	
	//write to file
	public void writeToFile(PrintWriter writer) {
  		String line = "";
		while(!(line.equals("done")))
		 {
			 line=dio.readText();
			 if(line.equals("done"))
				 break;
			 writer.write(line +"\n");
		 }
		writer.close();
		dio.write("Upload complete.\n");
	}

	
	public class command1 extends Command{
		
		public command1 () {
			super("1. upload a time series csv file\n");
		}
		@Override
		public void execute() {
			dio.write("Please upload your local train CSV file.\n");
			
			 try {
				PrintWriter writer=new PrintWriter("anomalyTrain.csv");
				writeToFile(writer);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 dio.write("Please upload your local test CSV file.\n");
			 try {
					PrintWriter writer=new PrintWriter("anomalyTest.csv");
					writeToFile(writer);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
			
	}
	
	public class command2 extends Command{
		public command2 () {
			super("2. algorithm settings\n");
		}
		@Override
		public void execute() {
			dio.write("The current correlation threshold is 0.9\n"
					+ "Type a new threshold\n");
			float threshold =  java.lang.Float.parseFloat(dio.readText());
			if(threshold < 0 || threshold >1)
			{
				dio.write("please choose a value between 0 and 1.");
				new command2().execute();
			}
			else
				sharedState.anomalyDetector.setThreshold(threshold);	
		}		
	}
	
	public class command3 extends Command{
		public command3 () {
			super("3. detect anomalies\n");
		}
		@Override
		public void execute() {
			TimeSeries tsTrain= new TimeSeries("anomalyTrain.csv");
			sharedState.anomalyDetector.learnNormal(tsTrain);
			TimeSeries tsTest= new TimeSeries("anomalyTest.csv");
			sharedState.detect=sharedState.anomalyDetector.detect(tsTest);
			dio.write("anomaly detection complete.\n");
		}
		
		
	}
	
	public class command4 extends Command{
		public command4 () {
			super("4. display results\n");
		}
		@Override
		public void execute() {
			for(int i=0;i<sharedState.detect.size();i++) {
				dio.write(sharedState.detect.get(i).timeStep + "	" + sharedState.detect.get(i).description+"\n");
			}
			dio.write("Done.\n");
			
		}
		
		
	}
	public class exceptionRange{
		float start,end;
		
		exceptionRange(float start,float end){
			this.start=start;
			this.end=end;	
		}
	}
	
	public class command5 extends Command{
		
		public command5 () {
			super("5. upload anomalies and analyze results\n");
		}
		@Override
		public void execute() {
			
			
			dio.write("Please upload your local anomalies file.\n");
			//the new anomalies
			ArrayList<exceptionRange> currectAnomalies = new ArrayList <exceptionRange>();
			String line = "";
			while(!(line.equals("done")))
			 {
				 line=dio.readText();
				 if(line.equals("done"))
					 break;
				 String [] value = line.split(",");
				 exceptionRange e=new exceptionRange(java.lang.Float.parseFloat(value[0]),java.lang.Float.parseFloat(value[1]));
				 currectAnomalies.add(e);
			 }
			dio.write("Upload complete.\n");
			
			float P= currectAnomalies.size();
			float range=0;
			for(int i=0;i<currectAnomalies.size();i++) 
				range+=currectAnomalies.get(i).end - currectAnomalies.get(i).start + 1;
			
			float N=200-range;
			
			ArrayList<exceptionRange> original = new ArrayList <exceptionRange>();
			
			int i=0;
			boolean flag;
			while(i<sharedState.detect.size()-1) {
				//flag=false;
				long startTime = sharedState.detect.get(i).timeStep;
				while(sharedState.detect.get(i).timeStep==sharedState.detect.get(i+1).timeStep-1 && sharedState.detect.get(i).description.equals(sharedState.detect.get(i+1).description)) {
					//flag=true;
					i++;
					if(i>sharedState.detect.size()-2)
						break;
				
				}
				//if(flag==true) {
					exceptionRange e=new exceptionRange((float)startTime, (float)sharedState.detect.get(i).timeStep);
					original.add(e);	
				//}
				i++;				
			}
		
			float falsePositive=0, truePositive=0;
			for(i=0;i<original.size();i++)
			{
				flag=false;
				for(int j=0;j<currectAnomalies.size();j++)
				{
				   //if(original.get(i).start>= currectAnomalies.get(j).start&& original.get(i).start<=currectAnomalies.get(j).end && original.get(i).end >=currectAnomalies.get(j).end) 		
					// flag=true;
				//	if(((original.get(i).start < currectAnomalies.get(j).start) && (original.get(i).end< currectAnomalies.get(j).start)) || ((original.get(i).start> currectAnomalies.get(j).start) && (original.get(i).start<=currectAnomalies.get(j).end) &&(original.get(i).end>=currectAnomalies.get(j).end)) 
							//|| ((original.get(i).start> currectAnomalies.get(j).start) && (original.get(i).end < currectAnomalies.get(j).end)) || ((original.get(i).start<currectAnomalies.get(j).start) && (original.get(i).end> currectAnomalies.get(j).end)))
						//flag=true;
				
				
					if((original.get(i).start< currectAnomalies.get(j).start && original.get(i).end> currectAnomalies.get(j).start &&  original.get(i).end<= currectAnomalies.get(j).end) ||
							(original.get(i).start> currectAnomalies.get(j).start && original.get(i).start<=currectAnomalies.get(j).end &&original.get(i).end>=currectAnomalies.get(j).end ) || 
							(original.get(i).start> currectAnomalies.get(j).start&& original.get(i).end <=currectAnomalies.get(j).end) ||
							(original.get(i).start<=currectAnomalies.get(j).start&& original.get(i).start<currectAnomalies.get(j).end && original.get(i).end >=currectAnomalies.get(j).start))
						flag= true;
				
				
				
				}
				if(flag==true)
					truePositive++;
				else
					falsePositive++;
			}
			
			truePositive/=P;
			falsePositive/=N;
			int TP3d =(int)(truePositive*1000);
			double resultTP=TP3d/1000d;
			int FP3d= (int)(falsePositive*1000);
			double resultFP=FP3d/1000d;
			dio.write("True Positive Rate: " + resultTP +"\n");
			dio.write("False Positive Rate: " + resultFP+"\n");
			
		}
		
		
	}
	
	public class command6 extends Command{
		public command6 () {
			super("6. exit\n");
		}
		@Override
		public void execute() {
			dio.write("bye");
			
		}	
	}
	
}
