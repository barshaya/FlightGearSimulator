package model;

import java.util.Observable;

import algorithms.TimeSeries;
import algorithms.TimeSeriesAnomalyDetector;
import viewModel.ViewModel;

public class Model extends Observable {
	
	TimeSeries ts;
	XmlComplete settings;
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

	public TimeSeries getTs() {
		return ts;
	}

	public Properties getClientSettings() {
		return clientSettings;
	}

	public void setClientSettings(Properties clientSettings) {
		this.clientSettings = clientSettings;
	}

	public void setTimeSeries(TimeSeries ts) {
		this.ts=ts;
		resetValues();
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
			for (int i = start; i < ts.num; i++) {
				final int j = i;
				activeObject.execute(()->{
					updateValues(j);
					setTime(j);
					flightGear.SendCommand(ts.ReadLine(j));
					try {Thread.sleep((long) (100/rate));} catch (InterruptedException e) {}
				});
			}
			activeObject.execute(()->flightGear.CloseSocket());
			activeObject.execute(()->flightGear = null);

		} catch (Exception e) {
			this.setConnectMessage("FlightGear is not connected!");
			for (int i = start; i < ts.num; i++) {
				final int j = i;
				activeObject.execute(()->{
					updateValues(j);
					setTime(j);
					try {Thread.sleep((long) (100/rate));} catch (InterruptedException ex) {}
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

	public void addObservers(ViewModel viewModel) {
		this.addObserver(viewModel);
	}

	public void updateValues(int i) {
		setThrottle(getTs().getValue(getClientSettings().getAssociateName("throttle"), i));
		setYaw(getTs().getValue(getClientSettings().getAssociateName("yaw"), i));
		setPitch(getTs().getValue(getClientSettings().getAssociateName("pitch"), i));
		setRoll(getTs().getValue(getClientSettings().getAssociateName("roll"), i));
		setAileron(getTs().getValue(getClientSettings().getAssociateName("aileron"),i));
		setElevators(getTs().getValue(getClientSettings().getAssociateName("elevator"),i));
		setRudder(getTs().getValue(getClientSettings().getAssociateName("rudder"), i));
		setHeight(getTs().getValue(getClientSettings().getAssociateName("heigth"), i));
		setDirection(getTs().getValue(getClientSettings().getAssociateName("direction"), i));
		setSpeed(getTs().getValue(getClientSettings().getAssociateName("speed"), i));
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
	}

	public void ClearTasks() {
		// TODO Auto-generated method stub
		activeObject.ClearTasks();
	}
}
