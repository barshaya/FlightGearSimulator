package model;

import algorithms.AnomalyReport;
import algorithms.TimeSeries;
import algorithms.TimeSeriesAnomalyDetector;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;


public class AlgoLoader implements TimeSeriesAnomalyDetector {
    TimeSeriesAnomalyDetector algo;

    public AlgoLoader(String p,String classname) throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        // TODO Auto-generated constructor stub
        String path =  "file://" + p;
        URL[] urls = new URL[1];
        urls[0] = new URL(path);
        URLClassLoader classLoader = new URLClassLoader(urls);
        Class<?> classInstance = classLoader.loadClass(classname);
        algo = (TimeSeriesAnomalyDetector)classInstance.newInstance();
    }

    public TimeSeriesAnomalyDetector getAlgo() {
        return algo;
    }

    public void setAlgo(TimeSeriesAnomalyDetector algo) {
        this.algo = algo;
    }

    @Override
    public void learnNormal(TimeSeries ts) {
        // TODO Auto-generated method stub
        algo.learnNormal(ts);

    }

    @Override
    public List<AnomalyReport> detect(TimeSeries ts) {
        // TODO Auto-generated method stub
        return algo.detect(ts);
    }

    @Override
    public Runnable paint() {
        // TODO Auto-generated method stub
        return null;
    }
}
