package viewModel;

import algorithms.TimeSeriesAnomalyDetector;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class PlugIn {

    URL url;
    String input;

    {
        try {
            url = new URL("file://"+input);
            URLClassLoader urlClassLoader= new URLClassLoader(new URL[]{url});
            Class<?> c=urlClassLoader.loadClass(input); //find how to connect a class
            TimeSeriesAnomalyDetector ad= (TimeSeriesAnomalyDetector) c.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }




}
