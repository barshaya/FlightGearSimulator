package model;

import algorithms.TimeSeriesAnomalyDetector;

public class PlugIn {
    TimeSeriesAnomalyDetector algoChose;

    public void setAnomalyDetector(TimeSeriesAnomalyDetector ad){
        algoChose=ad;
    }

    public void start(){}
}
