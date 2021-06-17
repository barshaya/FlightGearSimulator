package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Properties implements Serializable{
    public Properties() {}
    private ArrayList<FeatureProperties> afs;
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
    public ArrayList<FeatureProperties> getAfs() {
        return afs;
    }
    public void setAfs(ArrayList<FeatureProperties> afs) {
        this.afs = afs;
    }

    public String toString() {
        String output ="XmlSettings [" + host + " = Host " + port + " = Port " + timeout + " = readRate " ;

        for (FeatureProperties featureProperties : afs) {
            output += featureProperties.toString();
        }

        output += "]";
        return output;

    }

    public String getAssociateName(String realName) {
        for (FeatureProperties featureProperties : afs) {
            if (featureProperties.getRealName().equals(realName)) {
                return featureProperties.getAssociateName();
            }
        }
        System.out.println("invalid name");
        return null;
    }

    public FeatureProperties getSetting(String realName) {
        try {
            for (FeatureProperties featureProperties : afs) {
                if (featureProperties.getRealName().equals(realName)) {
                    return featureProperties;
                }
            }
        }catch (Exception e) {System.out.println("invalid name");}
        return null;
    }
}
