package model;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Properties implements Serializable {



    public Properties() {}
    private ArrayList<FeatureProperties> features;
    String host;
    int port;
    double timeout;

    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }
    public double getTimeout() {
        return timeout;
    }
    public void setTimeout(double timeout) {
        this.timeout = timeout;
    }
    public ArrayList<FeatureProperties> getFeatures() {
        return features;
    }
    public void setFeatures(ArrayList<FeatureProperties> features) {
        this.features = features;
    }

    public String toString() {
        String output =" Properties [host: " + host + " port: " + port + " timeout: " + timeout  ;

        for (FeatureProperties f : features) {
            output += f.toString();
        }

        output += "]";
        return output;

    }

    public String getAssociate(String realName) {
        for (FeatureProperties f : features) {
            if (f.getAssociateName().equals(realName)) {
                return f.getAssociateName();
            }
        }
        System.out.println("invalid name");
        return null;
    }

    public FeatureProperties getProperties(String realName) {
        try {
            for (FeatureProperties f : features) {
                if (f.getAssociateName().equals(realName)) {
                    return f;
                }
            }
        }catch (Exception e) {System.out.println("invalid name");}
        return null;
    }

}