package model;

import algorithms.AnomalyReport;
import algorithms.TimeSeries;
import algorithms.TimeSeriesAnomalyDetector;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;


public class AlgoPlugIn implements TimeSeriesAnomalyDetector
{

    TimeSeriesAnomalyDetector ad;

    public AlgoPlugIn(String p, String classname) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        String path =  "file://" + p;
        URL[] urls = new URL[1];
        try {
            urls[0] = new URL(path);
            URLClassLoader classLoader = new URLClassLoader(urls);
            Class<?> classInstance = classLoader.loadClass(classname);
            ad = (TimeSeriesAnomalyDetector)classInstance.newInstance();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public TimeSeriesAnomalyDetector getAd() {
        return ad;
    }

    public void setAlgo(TimeSeriesAnomalyDetector ad) {
        this.ad = ad;
    }

    @Override
    public void learnNormal(TimeSeries ts) {
        ad.learnNormal(ts);

    }

    @Override
    public List<AnomalyReport> detect(TimeSeries ts) {
        return ad.detect(ts);
    }


}
