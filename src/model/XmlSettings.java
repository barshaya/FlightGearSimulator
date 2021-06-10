package model;

import java.io.Serializable;
import java.util.ArrayList;

public class XmlSettings implements Serializable{
    public XmlSettings() {}
    private ArrayList<FeatureSettings> afs;
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
    public ArrayList<FeatureSettings> getAfs() {
        return afs;
    }
    public void setAfs(ArrayList<FeatureSettings> afs) {
        this.afs = afs;
    }

    public String toString() {
        String output ="XmlSettings [" + host + " = Host " + port + " = Port " + timeout + " = readRate " ;

        for (FeatureSettings featureSettings : afs) {
            output += featureSettings.toString();
        }

        output += "]";
        return output;

    }

    public String getAssociate(String realName) {
        for (FeatureSettings featureSettings : afs) {
            if (featureSettings.getReal_col_name().equals(realName)) {
                return featureSettings.getAssosicate_name();
            }
        }
        System.out.println("Invalid name");
        return null;
    }

    public FeatureSettings getSetting(String realName) {
        try {
            for (FeatureSettings featureSettings : afs) {
                if (featureSettings.getReal_col_name().equals(realName)) {
                    return featureSettings;
                }
            }
        }catch (Exception e) {System.out.println("Invalid name");}
        return null;
    }
}
