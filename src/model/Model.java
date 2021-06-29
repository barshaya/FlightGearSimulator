package model;

import java.util.Observable;
import algorithms.*;
import javafx.application.Platform;
import javafx.scene.chart.XYChart;
import viewModel.ViewModel;

public class Model extends Observable {
	
	TimeSeries tsTrain;
	TimeSeries tsTest;

	FGConnection flightGear;
	TimeSeriesAnomalyDetector tsAnomalyDetector;
	Properties clientSettings;
	double aileron ;
	double elevators;
	double rudder;
	double throttle;
	double pitch;
	double yaw;
	double direction ;
	double speed;
	double roll;
	double height;
	String line;
	String flightStatus;
	MyActiveObject activeObject;
	String message;
	double rate;
	int time;

	public Model() {
		super();
		this.activeObject = new MyActiveObject();
		this.activeObject.start();
	}


	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
		this.setChanged();
		this.notifyObservers("time");
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getConnectMessage() {
		return message;
	}

	public void setConnectMessage(String message) {
		this.message = message;
		this.setChanged();
		this.notifyObservers("flightMessage");
	}

	public String getFlightStatus() {
		return this.flightStatus;
	}

	public void setFlightStatus(String flightStatus) {
		this.flightStatus = flightStatus;
		this.setChanged();
		this.notifyObservers("FligthStatus");
	}

	public String getLine() {
		return this.line;
	}

	public double getAileron() {
		return this.aileron;
	}

	public void setAileron(double aileron) {
		this.aileron = aileron;
		this.setChanged();
		this.notifyObservers("aileron");
	}

	public double getElevators() {
		return elevators;
	}

	public void setElevators(double elevators) {
		this.elevators = elevators;
		this.setChanged();
		this.notifyObservers("elevators");
	}

	public double getRudder() {
		return rudder;
	}

	public void setRudder(double rudder) {
		this.rudder = rudder;
		this.setChanged();
		this.notifyObservers("rudder");
	}

	public double getThrottle() {
		return throttle;
	}

	public void setThrottle(double throttle) {
		this.throttle = throttle;
		this.setChanged();
		this.notifyObservers("throttle");
	}

	public double getPitch() {
		return pitch;
	}

	public void setPitch(double pitch) {
		this.pitch = pitch;
		this.setChanged();
		this.notifyObservers("pitch");
	}

	public double getYaw() {
		return yaw;
	}

	public void setYaw(double yaw) {
		this.yaw = yaw;
		this.setChanged();
		this.notifyObservers("yaw");
	}

	public double getDirection() {
		return direction;
	}

	public void setDirection(double direction) {
		this.direction = direction;
		this.setChanged();
		this.notifyObservers("direction");
	}

	public double getSpeed() {
		return this.speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
		this.setChanged();
		this.notifyObservers("speed");
	}

	public double getRoll() {
		return this.roll;
	}

	public void setRoll(double roll) {
		this.roll = roll;
		this.setChanged();
		this.notifyObservers("roll");
	}

	public double getHeight() {
		return this.height;
	}

	public void setHeight(double height) {
		this.height = height;
		this.setChanged();
		this.notifyObservers("heigth");
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

	public Properties getClientSettings() {
		return clientSettings;
	}

	public void setClientSettings(Properties clientSettings) {
		this.clientSettings = clientSettings;
	}



	public void play(int start) {
		if (this.activeObject.stop == true) {
			this.activeObject.start();
		}
		try {
			if (this.flightGear == null) {
				this.flightGear = new FGConnection(clientSettings);
			}
			this.setConnectMessage("FlightGear is connected!");
			for (int i = start; i < tsTest.num; i++) {
				final int j = i;
				activeObject.execute(()->{
					updateValues(j);
					setTime(j);
					flightGear.SendCommand(tsTest.ReadLine(j));
					try {Thread.sleep((long) (100/rate));} catch (InterruptedException e) {
					}
				});
			}
			activeObject.execute(()->flightGear.CloseSocket());
			activeObject.execute(()->flightGear = null);

		} catch (Exception e) {
			this.setConnectMessage("FlightGear is not connected!");
			for (int i = start; i < tsTest.num; i++) {
				final int j = i;
				activeObject.execute(()->{
					updateValues(j);
					setTime(j);
					try {Thread.sleep((long) (100/rate));} catch (InterruptedException ex) {
						System.out.println(e);
					}
				});
			}
		}
		activeObject.execute(()->resetValues());
		activeObject.execute(()->setFlightStatus("No fly"));
	}

	public void pause() {
		this.activeObject.pause();
	}

	public void stop() {
		this.activeObject.stop();
		if (flightGear != null) {
			flightGear.CloseSocket();
			flightGear = null;
		}
		System.out.println("Flight Stop");
		resetValues();
	}

	public void setAnomalyDetector(TimeSeriesAnomalyDetector tsAnomalyDetector) {
		this.tsAnomalyDetector = tsAnomalyDetector;
	}

	public TimeSeriesAnomalyDetector getTsAnomalyDetector() {
		return tsAnomalyDetector;
	}

	public void addObservers(ViewModel viewModel) {
		this.addObserver(viewModel);
	}

	public void updateValues(int i) {
		setThrottle(getTsTest().getTimeStempValue(getClientSettings().getAssociate("throttle"), i));
		setYaw(getTsTest().getTimeStempValue(getClientSettings().getAssociate("yaw"), i));
		setPitch(getTsTest().getTimeStempValue(getClientSettings().getAssociate("pitch"), i));
		setRoll(getTsTest().getTimeStempValue(getClientSettings().getAssociate("roll"), i));
		setAileron(getTsTest().getTimeStempValue(getClientSettings().getAssociate("aileron"),i));
		setElevators(getTsTest().getTimeStempValue(getClientSettings().getAssociate("elevator"),i));
		setRudder(getTsTest().getTimeStempValue(getClientSettings().getAssociate("rudder"), i));
		setHeight(getTsTest().getTimeStempValue(getClientSettings().getAssociate("heigth"), i));
		setDirection(getTsTest().getTimeStempValue(getClientSettings().getAssociate("direction"), i));
		setSpeed(getTsTest().getTimeStempValue(getClientSettings().getAssociate("speed"), i));
	}

	public void resetValues() {
		double x=(this.clientSettings.getSetting("elevator").getMin() + this.clientSettings.getSetting("elevator").getMax())/2;
		setElevators(x);
		x = (this.clientSettings.getSetting("aileron").getMin() + this.clientSettings.getSetting("aileron").getMax())/2;
		setPitch(0);
		setRoll(0);
		setHeight(0);
		setDirection(0);
		setSpeed(0);
		setAileron(x);
		setRudder(0);
		setThrottle(0);
		setYaw(0);
		setTime(0);
	}

	public void addValueAtTime(String attribute, XYChart.Series s) {
		Platform.runLater(()->{
			double temp = tsTest.getTimeStempValue(attribute,time);
			s.getData().add(new XYChart.Data(time, temp));
		});
	}
	public void addLine(Line l, XYChart.Series s) {
		Platform.runLater(()->{
			s.getData().clear();
			s.getData().add(new XYChart.Data(0,l.f(0)));
			s.getData().add(new XYChart.Data(1000,l.f(1000)));

		});
	}


	public void addValueUntilTime(String attribute, XYChart.Series s) {
		Platform.runLater(()->{
			for(int i=1;i<time;i++){
				clearData(s);
				float temp = tsTest.getTimeStempValue(attribute,time);
				s.getData().add(new XYChart.Data(time, temp));

			}

		});
	}

	public void addAnomalyPoint(String a, String b, XYChart.Series s){
		Platform.runLater(()-> {
			float tempA = tsTest.getTimeStempValue(a, time);
			float tempB = tsTest.getTimeStempValue(b, time);

			s.getData().add(new XYChart.Data(tempA,tempB));

		});
	}

	public void clearData(XYChart.Series s){
		Platform.runLater(()-> {
			s.getData().clear();

		});
	}

	public void ClearTasks() {
		// TODO Auto-generated method stub
		activeObject.ClearTasks();
	}

	public String FindCorrelative(String value,String algo) {

		String s=null;
		if((algo.substring(11)).equals("Linear"))
			s=((Linear)tsAnomalyDetector).getMapL().get(value);


		if((algo.substring(11)).equals("Hybrid"))
			s = ((Hybrid) tsAnomalyDetector).getMapL().get(value);


		if (s == null)
			return null;

		return s;

	}


	public Line FindCorrelativeLine(String value,String algo){

		Line l=null;
		if((algo.substring(11)).equals("Linear")){
			l=((Linear)tsAnomalyDetector).getMapLine().get(value).lin_reg;
		}
		if((algo.substring(11)).equals("hybrid")){
			l=((Linear)tsAnomalyDetector).getMapLine().get(value).lin_reg;
		}

		if(l==null){
			return null;
		}
		return l;
	}


}
