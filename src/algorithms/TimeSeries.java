package algorithms;
import utils.Point;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;



public class TimeSeries {
	
	public class col{
		String name;
		ArrayList <Float> parameters;
		
		public col(String name) {
			this.name = name;
			this.parameters=new ArrayList<>();
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public ArrayList<Float> getParameters() {
			return parameters;
		}
		public void setParameters(ArrayList<Float> parameters) {
			this.parameters = parameters;
		}
	}
	
	private col[] features;
	private BufferedReader reader;
	public col[] getFeatures() {
		return features;
	}
	public TimeSeries(String csvFileName) {
		try{
			reader = new BufferedReader(new FileReader(csvFileName));
		    String line = "";
		    line= reader.readLine();
		    String [] value = line.split(",");
		    features= new col[value.length]; 
		    for(int i=0;i<value.length;i++) {
		    	features[i]=new col(value[i]);
		    }
		    while((line=reader.readLine())!= null) {
		    	 
		    		 value = line.split(",");
		    		 for(int i=0;i<value.length;i++)
		    			 features[i].getParameters().add(Float.parseFloat(value[i]));
		    }
		    
		    }
		catch (FileNotFoundException e) {
			e.printStackTrace();
	    	} 
		catch (IOException e) {
			e.printStackTrace();
	    	}
	}
	
	public float [] ArrFloats(ArrayList<Float> parameters) {
		float [] arr= new float [parameters.size()];
		for(int i=0;i<arr.length;i++) {
			arr[i]=parameters.get(i);
		}
		return arr;
	}
	
	public Point[] arrPoints(float [] arr1, float [] arr2 ) {
		Point [] arr= new Point [arr1.length];
		for (int i = 0; i < arr.length; i++) {
			arr[i]=new Point(arr1[i],arr2[i]);
		}
		return arr;
	}
}

