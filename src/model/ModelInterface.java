package model;

import algorithms.TimeSeries;
import algorithms.TimeSeriesAnomalyDetector;
import javafx.beans.property.DoubleProperty;
import viewModel.ViewModel;

public interface ModelInterface {
    public void setTimeSeries(TimeSeries ts);
    public void play(int start);
    public void stop();
    public void pause();
    public void setClientSettings(XmlSettings clientSettings);
    public void setXmlComplete(XmlComplete c);
    public void setAnomalyDetector(TimeSeriesAnomalyDetector ad);
    public void addObservers(ViewModel viewModel);
    public double getAileron();
    public double getElevators();
    public double getRudder();
    public double getThrottle();
    public double getYaw();
    public double getHeigth();
    public double getSpeed();
    public double getDirection();
    public double getRoll();
    public double getPitch();
    public String getLine();
    public String getFlightStatus();
    public String getConnectMessage();
    public void setRate(double rate);
    public int getTime();
    public void setTime(int time);
    public void ClearTask();

}
