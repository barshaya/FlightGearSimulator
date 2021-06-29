package viewModel;

import java.text.DecimalFormat;
import java.util.*;
import algorithms.AnomalyReport;
import algorithms.TimeSeries;
import algorithms.TimeSeriesAnomalyDetector;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.*;
import model.Properties;
import view.buttons.MyButtons;

public class ViewModel implements Observer {
	
	Model model;
	public DoubleProperty aileron=new SimpleDoubleProperty();
	public DoubleProperty elevators=new SimpleDoubleProperty();
	public DoubleProperty rudder=new SimpleDoubleProperty();
	public DoubleProperty throttle=new SimpleDoubleProperty();
	public StringProperty yaw=new SimpleStringProperty();
	public StringProperty roll=new SimpleStringProperty();
	public StringProperty pitch=new SimpleStringProperty();
	public StringProperty speed=new SimpleStringProperty();
	public StringProperty direction=new SimpleStringProperty();
	public StringProperty height=new SimpleStringProperty();
	public StringProperty FlightStatus = new SimpleStringProperty();
	public StringProperty FlightMessage= new SimpleStringProperty();
	public SimpleDoubleProperty rate = new SimpleDoubleProperty();
	public DoubleProperty videoslider =new SimpleDoubleProperty();


	List<AnomalyReport> reportsList = new LinkedList<>();




	TimeSeries tsTrain;
	TimeSeries tsTest;
	TimeSeriesAnomalyDetector tsAnomalyDetector;
	Properties properties;
	HandleXML handlexml;
	MyButtons mb;

	public ViewModel(Model m) {
		super();
		this.model = m;
		handlexml = new HandleXML();
		m.addObservers(this);
		rate.addListener((o,ov,nv)->m.setRate(nv.doubleValue()));
		this.mb = new MyButtons();
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		DecimalFormat dFormat = new DecimalFormat("#.##");
		if (o == model && arg.equals("line")) {
			Platform.runLater(() -> yaw.setValue(model.getLine()));
		} else if (o == model && arg.equals("aileron")) {
			this.aileron.setValue((model.getAileron()));
		} else if (o == model && arg.equals("elevators")) {
			this.elevators.setValue((model.getElevators()));
		} else if (o == model && arg.equals("rudder")) {
			this.rudder.setValue((model.getRudder()));
		} else if (o == model && arg.equals("throttle")) {
			this.throttle.setValue((model.getThrottle()));
		} else if (o == model && arg.equals("yaw")) {
			Platform.runLater(() -> yaw.setValue(dFormat.format(model.getYaw())));
		} else if (o == model && arg.equals("time")) {
			Platform.runLater(() -> videoslider.setValue((double)this.model.getTime()));
		} else if (o == model && arg.equals("heigth")) {
			Platform.runLater(() -> height.setValue(dFormat.format(model.getHeight())));
		} else if (o == model && arg.equals("pitch")) {
			Platform.runLater(() -> pitch.setValue(dFormat.format(model.getPitch())));
		} else if (o == model && arg.equals("FligthStatus")) {
			this.FlightStatus.setValue((model.getFlightStatus()));
		} else if (o == model && arg.equals("flightMessage")) {
			this.FlightMessage.setValue((model.getConnectMessage()));
		} else if (o == model && arg.equals("speed")) {
			Platform.runLater(() -> speed.setValue(dFormat.format(model.getSpeed())));
		} else if (o == model && arg.equals("direction")) {
			Platform.runLater(() -> direction.setValue(dFormat.format(model.getDirection())));
		} else if (o == model && arg.equals("roll")) {
			Platform.runLater(() -> roll.setValue(dFormat.format(model.getRoll())));
		}
	}


	public DoubleProperty aileronProperty() {
		return aileron;
	}

	public void setAileron(double aileron) {
		this.aileron.set(aileron);
	}

	public TimeSeries getTsTrain() {
		return tsTrain;
	}

	public void setTsTrain(TimeSeries tsTrain) {
		this.tsTrain = tsTrain;
	}

	public TimeSeries getTsTest() {
		return tsTest;
	}

	public void setTsTest(TimeSeries tsTest) {
		this.tsTest = tsTest;
	}

	public void loadTrainCsv(String csvTrainPath) {
		if (this.properties == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error - Please load XML");
			alert.showAndWait();
		} else {
			this.tsTrain = new TimeSeries(csvTrainPath);
			if (this.tsTrain.table == null) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("Error with CSV");
				alert.showAndWait();
				this.tsTrain = null;
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("Success loading CSV file");
				alert.setContentText("please upload CSV Test file");
				alert.showAndWait();
				model.setTsTrain(tsTrain);
			}
		}
	}

	public void loadTestCsv (String csvTestPath) {
			if (this.tsTrain == null) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("Error - Please load Train CSV file");
				alert.showAndWait();
			} else {
				this.tsTest = new TimeSeries(csvTestPath);
				if (this.tsTest.table == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setHeaderText("Error with CSV");
					alert.showAndWait();
					this.tsTest = null;
				} else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setHeaderText("Success loading CSV Test file");
					alert.setContentText("please upload Algo file");
					alert.showAndWait();
					model.setTsTest(tsTest);
				}
			}
		}



	public void loadXml(String name) {
		// TODO Auto-generated method stub
		properties = handlexml.LoadSettingsFromClient(name);
		if (properties != null && properties.getHost() != null && properties.getPort() != 0 && properties.getTimeout() != 0.0) {
			model.setClientSettings(properties);
			ArrayList<Double> checkSpeed = new ArrayList<Double>(Arrays.asList(0.25,0.5,0.75,1.0,1.25,1.5,1.75,2.0));
			if (checkSpeed.contains(properties.getTimeout())) {
				this.rate.setValue(properties.getTimeout());
			}
			else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("Wrong Speed");
				alert.setContentText("The default speed is 1.0");
				alert.showAndWait();
				this.rate.setValue(1.0);
			}
		}
	}

	public void loadAnomalyAlgo(String p, String name) {
		// TODO Auto-generated method stub
		if(this.tsTest == null){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error - Please load CSV");
			alert.showAndWait();
		} else {
			try {
				this.tsAnomalyDetector = new AlgoPlugIn(p, name).getAd();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Alert a = new Alert(AlertType.ERROR);
				a.setHeaderText("Failed load the algorithm - Please try again");
				a.showAndWait();
				this.tsAnomalyDetector = null;
			}
			if (this.tsAnomalyDetector != null) {
				Alert a = new Alert(AlertType.INFORMATION);
				a.setHeaderText("Success Algo Loading");
				a.showAndWait();
				model.setAnomalyDetector(tsAnomalyDetector);
				model.getTsAnomalyDetector().learnNormal(model.getTsTrain());
				reportsList=model.getTsAnomalyDetector().detect(model.getTsTest());

			}
		}
	}

	public int getTime(){
		return this.model.getTime();
	}

	public void setTime(int time ){
		this.model.setTime(time);
	}
	
	public TimeSeriesAnomalyDetector getAd() {
		return tsAnomalyDetector;
	}

	public DoubleProperty getRudder() {
		return rudder;
	}

	public void setRudder(DoubleProperty rudder) {
		this.rudder = rudder;
	}

	public DoubleProperty getThrottle() {
		return throttle;
	}

	public Properties getXs() {
		return properties;
	}
	
	public DoubleProperty getAileron() {
		return aileron;
	}

	public void setAileron(DoubleProperty o) {
		this.aileron = o;
	}

	public DoubleProperty getElevators() {
		return elevators;
	}

	public void setElevators(DoubleProperty elevators) {
		this.elevators = elevators;
	}

	public void setThrottle(DoubleProperty throttle) {
		this.throttle = throttle;
	}

	public ArrayList<String> getColTitles(){
		if (tsTest != null) {
			return tsTest.ColumnNames;
		}
		return null;
	}

	
	public FeatureProperties getFeatureSetting(String ColName) {
		for (FeatureProperties fs  : properties.getAfs()) {
			if (fs.getRealName().equals(ColName)) {
				return fs;
			}
		}
		return null;
	}

	public void StartFlight(int start) {
		// TODO Auto-generated method stub
		if (this.tsTest == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error with play flight");
			alert.setContentText("Please upload CSV before you fly");
			alert.showAndWait();
		}
		else {
			model.play(start);
		}
	}

	public void stopFlight() {
		model.stop();
	}

	public void pauseFlight() {
		model.pause();
	}

	public void setTimeStemp(int timestemp){
		model.ClearTasks();
		model.play(timestemp);
	}

	public void Forward1() {
		if (model.getTime() + 10 < tsTest.num-1) {
			model.ClearTasks();
			model.play(model.getTime() + 10);
		}
	}

	public void Forward2() {
		// TODO Auto-generated method stub
		if (model.getTime() + 20 < tsTest.num-1) {
			model.ClearTasks();
			model.play(model.getTime() + 20);
		}
		
	}

	public void Backward1() {
		// TODO Auto-generated method stub
		if (model.getTime() - 10 > 0) {
			model.ClearTasks();
			model.play(model.getTime() - 10);
		}
		
	}

	public void Backward2() {
		// TODO Auto-generated method stub
		if (model.getTime() - 20 > 0) {
			model.ClearTasks();
			model.play(model.getTime() - 20);
		}
	}


	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

}
