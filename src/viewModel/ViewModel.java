package viewModel;

import java.awt.List;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;

import algorithms.TimeSeries;
import algorithms.TimeSeriesAnomalyDetector;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.AlgoLoader;
import model.FeatureSettings;
import model.ModelInterface;
import model.XmlComplete;
import model.XmlSettings;

public class ViewModel implements Observer {
	
	ModelInterface m;
	public DoubleProperty aileron=new SimpleDoubleProperty();
	public DoubleProperty elevators=new SimpleDoubleProperty();
	public DoubleProperty rudder=new SimpleDoubleProperty();
	public DoubleProperty throttle=new SimpleDoubleProperty();
	public StringProperty yaw=new SimpleStringProperty();
	public StringProperty roll=new SimpleStringProperty();
	public StringProperty pitch=new SimpleStringProperty();
	public StringProperty speed=new SimpleStringProperty();
	public StringProperty direction=new SimpleStringProperty();
	public StringProperty heigth=new SimpleStringProperty();
	public StringProperty line=new SimpleStringProperty();
	public StringProperty FlightStatus = new SimpleStringProperty();
	public StringProperty FlightMessage= new SimpleStringProperty();
	public SimpleDoubleProperty rate = new SimpleDoubleProperty();
	public DoubleProperty time =new SimpleDoubleProperty();


	XmlSettings xs;
	XmlComplete xc;
	TimeSeries ts;
	TimeSeriesAnomalyDetector ad;

	public TimeSeriesAnomalyDetector getAd() {
		return ad;
	}
	
	public TimeSeries getTs() {
		return ts;
	}
	public XmlSettings getXs() {
		return xs;
	}

	public DoubleProperty getTime() {
		return time;
	}

	public void setTime(DoubleProperty time) {
		this.time = time;
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

	public DoubleProperty getRudder() {
		return rudder;
	}

	public void setRudder(DoubleProperty rudder) {
		this.rudder = rudder;
	}

	public DoubleProperty getThrottle() {
		return throttle;
	}

	public void setThrottle(DoubleProperty throttle) {
		this.throttle = throttle;
	}
	
	public void loadCsv(String csvPath) {
		if (this.xs == null) {
			Alert a = new Alert(AlertType.ERROR);
			a.setHeaderText("Error with XML - Please upload correct xml before upload csv flight");
			a.showAndWait();
		}
		else {
			this.ts = new TimeSeries(csvPath);
			if (this.ts.table == null) {
				Alert a = new Alert(AlertType.ERROR);
				a.setHeaderText("Error with CSV loading - please try again");
				a.showAndWait();
				this.ts = null;
			}
			else {
				Alert a = new Alert(AlertType.INFORMATION);
				a.setHeaderText("Success loading CSV file");
				a.showAndWait();
				m.setTimeSeries(ts);
			}
		}
	}

	
	public ArrayList<String> getColTitels(){
		if (ts != null) {
			return ts.ColNames;
		}
		return null;
	}
	
	public ViewModel(ModelInterface m) {
		super();
		this.m = m;
		xc = new XmlComplete();
		m.addObservers(this);
		rate.addListener((o,ov,nv)->m.setRate(nv.doubleValue()));
	}
	
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		DecimalFormat d = new DecimalFormat("#.##");
		if (o == m && arg.equals("line")) {
			Platform.runLater(()->yaw.setValue(m.getLine()));
		}

		if (o == m && arg.equals("time")) {
			this.time.setValue((m.getTime()));
		}
		
		if (o == m && arg.equals("aileron")) {
			this.aileron.setValue((m.getAileron()));
		}
		if (o == m && arg.equals("elevators")) {
			this.elevators.setValue((m.getElevators()));
		}
		if (o == m && arg.equals("rudder")) {
			this.rudder.setValue((m.getRudder()));
		}
		if (o == m && arg.equals("throttle")) {
			this.throttle.setValue((m.getThrottle()));
		}
		if (o == m && arg.equals("yaw")) {
			Platform.runLater(()->yaw.setValue(d.format(m.getYaw())));
		}
		if (o == m && arg.equals("heigth")) {
			Platform.runLater(()->heigth.setValue(d.format(m.getHeigth())));
		}
		if (o == m && arg.equals("speed")) {
			Platform.runLater(()->speed.setValue(d.format(m.getSpeed())));
		}
		if (o == m && arg.equals("direction")) {
			Platform.runLater(()->direction.setValue(d.format(m.getDirection())));
		}
		if (o == m && arg.equals("roll")) {
			Platform.runLater(()->roll.setValue(d.format(m.getRoll())));
		}
		if (o == m && arg.equals("pitch")) {
			Platform.runLater(()->pitch.setValue(d.format(m.getPitch())));
		}
		if (o == m && arg.equals("FligthStatus")) {
			this.FlightStatus.setValue((m.getFlightStatus()));
		}
		if (o == m && arg.equals("flightMessage")) {
			this.FlightMessage.setValue((m.getConnectMessage()));
		}
	}
	public void loadXml(String name) {
		// TODO Auto-generated method stub
		xs = xc.LoadSettingsFromClient(name);
		if (xs != null && xs.getHost() != null && xs.getPort() != 0 && xs.getTimeout() != 0.0) {
			m.setClientSettings(xs);
			ArrayList<Double> checkSpeed = new ArrayList<Double>(Arrays.asList(0.25,0.5,0.75,1.0,1.25,1.5,1.75,2.0));
			if (checkSpeed.contains(xs.getTimeout())) {
				this.rate.setValue(xs.getTimeout());
			}
			else {
				Alert a = new Alert(AlertType.ERROR);
				a.setHeaderText("Wrong Speed");
				a.setContentText("The default speed is 1.0");
				a.showAndWait();
				this.rate.setValue(1.0);
			}
		}
	}
	
	public FeatureSettings getFeatureSetting(String ColName) {
		for (FeatureSettings fs  : xs.getAfs()) {
			if (fs.getReal_col_name().equals(ColName)) {
				return fs;
			}
		}
		return null;
	}

	

	public void loadAnomalyAlgo(String p, String name) {
		// TODO Auto-generated method stub
		try {
			this.ad = new AlgoLoader(p, name).getAlgo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Alert a = new Alert(AlertType.ERROR);
			a.setHeaderText("Failed load the algorithm - Please try again");
			a.showAndWait();
			this.ad = null;
		}
		if (this.ad != null) {
			System.out.println("");
			Alert a = new Alert(AlertType.INFORMATION);
			a.setHeaderText("Success Algo Loading");
			a.showAndWait();
			m.setAnomalyDetector(ad);
		}	
	}

	public void StartFligt(int start) {
		// TODO Auto-generated method stub
		if (this.ts == null) {
			Alert a = new Alert(AlertType.ERROR);
			a.setHeaderText("Error - play flight");
			a.setContentText("Please upload CSV before you fly");
			a.showAndWait();
		}
		else {
			m.play(start);
		}
		
	}
	public Runnable getPaint() {
		return ad.paint();
	}

	public void stopFlight() {
		m.stop();	
	}

	public void pauseFlight() {
		m.pause();
	}

	public void Forward1() {
		if (m.getTime() + 10 < ts.NumOfRows-1) {
			m.ClearTask();
			m.play(m.getTime() + 10);
		}
		
	}

	public void Forward2() {
		// TODO Auto-generated method stub
		if (m.getTime() + 20 < ts.NumOfRows-1) {
			m.ClearTask();
			m.play(m.getTime() + 20);
		}
		
	}

	public void Backward1() {
		// TODO Auto-generated method stub
		if (m.getTime() - 10 > 0) {
			m.ClearTask();
			m.play(m.getTime() - 10);
		}
		
	}

	public void Backward2() {
		// TODO Auto-generated method stub
		if (m.getTime() - 20 > 0) {
			m.ClearTask();
			m.play(m.getTime() - 20);
		}
	}
}
