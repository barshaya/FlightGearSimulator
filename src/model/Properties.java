package model;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Properties implements Serializable {



    private String name;
    private String associateName;
    private int min;
    private int max;
    //altitude,

    public Properties() { }

    public Properties(String name, String associateName, int min, int max){
        this.setName(name);
        this.setAssociateName(associateName);
        this.setMin(min);
        this.setMax(max);
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
        this.associateName  = associateName;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

}