package model;

import java.util.Observable;

import algorithms.TimeSeries;
import algorithms.TimeSeriesAnomalyDetector;
import viewModel.ViewModel;

public class Model extends Observable {


	TimeSeries ts;
	XmlComplete settings;
	FGConnection fg;
	TimeSeriesAnomalyDetector ad;
	Properties ClientSettings;
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
	MyActiveObject ao;
	String connectMessage;
	double rate;
	int Time;


	public Model() {
		super();
		this.ao = new MyActiveObject();
		this.ao.start();
	}

	public int getTime() {
		return Time;
	}

	public void setTime(int time) {
		Time = time;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getConnectMessage() {
		return connectMessage;
	}

	public void setConnectMessage(String connectMessage) {
		this.connectMessage = connectMessage;
		this.setChanged();
		this.notifyObservers("flightMessage");
	}

	public String getFlightStatus() {
		return flightStatus;
	}

	public void setFlightStatus(String flightStatus) {
		this.flightStatus = flightStatus;
		this.setChanged();
		this.notifyObservers("FligthStatus");
	}



	public String getLine() {
		return line;
	}
	
	public void setLine(String line) {
		this.line = line;
		this.setChanged();
		this.notifyObservers("line");
	}

	public double getAileron() {
		return aileron;
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
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
		this.setChanged();
		this.notifyObservers("speed");
	}

	public double getRoll() {
		return roll;
	}

	public void setRoll(double roll) {
		this.roll = roll;
		this.setChanged();
		this.notifyObservers("roll");
	}

	public double getHeigth() {
		return height;
	}

	public void setHeigth(double height) {
		this.height = height;
		this.setChanged();
		this.notifyObservers("heigth");
	}

	public FGConnection getFg() {
		return fg;
	}

	public TimeSeries getTs() {
		return ts;
	}

	public Properties getClientSettings() {
		return ClientSettings;
	}

	public void setClientSettings(Properties clientSettings) {
		ClientSettings = clientSettings;
	}

	public void setTimeSeries(TimeSeries ts) {
		this.ts=ts;
		resetValues();
	}

	public void play(int start) {
		if (ao.stop == true) {
			ao.start();
		}
		try {
			if (this.fg == null) {
				this.fg = new FGConnection(ClientSettings);
			}
			this.setConnectMessage("");
			for (int i = start; i < ts.num; i++) {
				final int j = i;
				ao.execute(()->{
					updateValues(j);
					setTime(j);
					fg.SendCommand(ts.ReadLine(j));
					try {Thread.sleep((long) (100/rate));} catch (InterruptedException e) {}

				});
			}
			ao.execute(()->fg.CloseSocket());
			ao.execute(()->fg = null);

		}catch (Exception e) {
			this.setConnectMessage("The FlightGear is not connected");
			for (int i = start; i < ts.num; i++) {
				final int j = i;
				ao.execute(()->{
					updateValues(j);
					setTime(j);
					try {Thread.sleep((long) (100/rate));} catch (InterruptedException ex) {}
				});
			}
		}
		ao.execute(()->resetValues());
		ao.execute(()->setFlightStatus("No fly"));

	}

	public void stop() {
		this.ao.stop();
		if (fg != null) {
			fg.CloseSocket();
			fg = null;
		}
		
		System.out.println("The flight is finish");
		resetValues();
	}


	public void pause() {
		this.ao.pause();
		
	}


	public void setAnomalyDetector(TimeSeriesAnomalyDetector ad) {
		this.ad=ad;
		
	}


	public void addObservers(ViewModel viewModel) {
		this.addObserver(viewModel);
		
	}


	public void setXmlComplete(XmlComplete c) {
		this.settings=c;
	}

		
	public void updateValues(int i) {
		setAileron(getTs().getValue(getClientSettings().getAssociate("aileron"),i));
		setElevators(getTs().getValue(getClientSettings().getAssociate("elevator"),i));
		setRudder(getTs().getValue(getClientSettings().getAssociate("rudder"), i));
		setThrottle(getTs().getValue(getClientSettings().getAssociate("throttle"), i));
		setYaw(getTs().getValue(getClientSettings().getAssociate("yaw"), i));
		setPitch(getTs().getValue(getClientSettings().getAssociate("pitch"), i));
		setRoll(getTs().getValue(getClientSettings().getAssociate("roll"), i));
		setHeigth(getTs().getValue(getClientSettings().getAssociate("heigth"), i));
		setDirection(getTs().getValue(getClientSettings().getAssociate("direction"), i));
		setSpeed(getTs().getValue(getClientSettings().getAssociate("speed"), i));
	}
	
	public void resetValues() {
		double x = (this.ClientSettings.getSetting("aileron").getMax()+this.ClientSettings.getSetting("aileron").getMin())/2;
		setAileron(x);
		x=(this.ClientSettings.getSetting("elevator").getMax()+this.ClientSettings.getSetting("elevator").getMin())/2;
		setElevators(x);
		setRudder(0);
		setThrottle(0);
		setYaw(0);
		setPitch(0);
		setRoll(0);
		setHeigth(0);
		setDirection(0);
		setSpeed(0);
	}


	public void ClearTask() {
		// TODO Auto-generated method stub
		ao.ClearTasks();
		
	}










}
