package algorithms;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;


public class TimeSeries {
	public class Feature{
		public String name;
		public int size;
		public ArrayList<Float> examples = new ArrayList<Float>();
		public String nameId;

		public Feature(String n) {
			this.nameId = n;
		}
		public Feature() {
			super();
		}
		public Feature(ArrayList<Float> s) {
			this.examples = s;
		}
		public ArrayList<Float> getExamples() {
			return examples;
		}
		public void addExample(Float s) {
			this.examples.add(s);
			this.size++;
		}
		public void setExamples(ArrayList<Float> Examples) {
			this.examples = Examples;
		}
		public String getNameId() {
			return nameId;
		}
		protected void setNameId(String Id) {
			this.nameId = Id;
		}
		public String getName() {
			return name;
		}
		public void setName(String n) {
			this.name = n;
		}

	}
	
	public String csvName;
	public ArrayList<String> ColumnNames = new ArrayList<String>();
	public ArrayList<Feature> table = new ArrayList<Feature>();
	public int num = 0; //num of rows
	
	public TimeSeries(String cv) {
		this.csvName = cv;
		try {
		Path pathToFile = Paths.get(csvName);
			BufferedReader br = Files.newBufferedReader(pathToFile,
			        StandardCharsets.US_ASCII);
			String line = br.readLine();
			String[] titles = line.split(",");
			int s = 0;
			for (String title : titles) {
				Feature temp  = new Feature(title);
				this.ColumnNames.add(title);
				temp.name = "" + s;
				this.table.add(temp);
				s++;
			}
			line = br.readLine();
			while (line != null) {
				String[] data = line.split(",");
				for (int i = 0; i < data.length; i++) {
					float f = Float.parseFloat(data[i]);
					table.get(i).addExample(f);
				}
				line = br.readLine();
			}
			br.close();
			this.num = this.table.get(0).getExamples().size();
		}catch (Exception e) {
			// TODO: handle exception
			this.table = null;
		}
	}
	

	public TimeSeries(Feature ... features) {
		// TODO Auto-generated constructor stub
		ArrayList<Feature> fs = new ArrayList<TimeSeries.Feature>();
		for (Feature feature : features) {
			fs.add(feature);
		}
		this.table = fs;
		
	}

	public TimeSeries() {
		super();
	}
	public ArrayList<Feature> getTable() {
		return table;
	}

	public ArrayList<Float> getFeatureByName(String s){
		for (Feature feature : table) {
			String n = feature.getName(); 
			if (n.equals(s)) {
				return feature.getExamples();
			}
		}
		return null;	
	}
	
	public Feature getFeatureByName1(String s){
		for (Feature feature : table) {
			String n = feature.getName(); 
			if (n.equals(s)) {
				return feature;	
			}
		}
		return null;	
	}
	
	public Feature getFeatureByNameid(String s){
		for (Feature feature : table) {
			String n = feature.getNameId();
			if (n.equals(s)) {
				return feature;	
			}
		}
		return null;	
	}

	public String getFeatureNameByNameid(String s){
		return getFeatureByNameid(s).getName();
	}

	// return the specific value of the colname at the timestemp
	public float getTimeStempValue(String ColName , int TimeStemp) {
		Feature f = this.getFeatureByNameid(ColName);
		if (f == null) {
			return (Float) null;
		}
		if (TimeStemp > f.getExamples().size()) {
			return (Float) null;
		}
		return f.getExamples().get(TimeStemp);
	}

	public int getColumnIndex(String s) {
		for (int i = 0; i < ColumnNames.size(); i++) {
			if (ColumnNames.get(i).equals(s)) {
				return i;
			}
		}
		System.out.println("the column not found");
		return -1;
	}
	public ArrayList<Float> ReadLine(int index){
		ArrayList<Float> line = new ArrayList<Float>();
		for (int i = 0; i < this.ColumnNames.size(); i++) {
			line.add(this.table.get(i).getExamples().get(index));
		}
		return line;
	}
}


