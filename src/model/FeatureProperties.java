package model;

import java.io.Serializable;

public class FeatureProperties implements Serializable {

    //the serializable feature item


    private String name;
    private String associateName;
    private double min;
    private double max;
    //altitude,

    public FeatureProperties() {
    }

    public FeatureProperties(String name, String associateName, double min, double max) {
        this.name = name;
        this.associateName = associateName;
        this.min = min;
        this.max = max;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssociateName() {
        return associateName;
    }

    public void setAssociateName(String associateName) {
        this.associateName = associateName;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    @Override
    public String toString() {
        return " FeatureProperty [feature name: " + name + ", associate name: " + associateName
                + ", min: " + min + ", max: " + max  + "]";
    }

}
