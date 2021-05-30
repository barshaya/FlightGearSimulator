package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FormatXML {


    Properties properties;
    String path;
    ArrayList<String> featuresNames;


    public FormatXML() {
        // TODO Auto-generated constructor stub
        properties = new Properties();
        path= "resources/featuresNames.txt";
        featuresNames = new ArrayList<String>();
    }




    public void WriteXmlToUser() throws IOException {

        //load featuresNames txt file
        Scanner s;
        try {
            s = new Scanner(new BufferedReader(new FileReader(path)));
            while (s.hasNext()) {
                String string = (String) s.next();
                featuresNames.add(string);
            }
            s.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        properties.setHost("localhost");
        properties.setPort(5400);
        properties.setTimeout(10);
        ArrayList<FeatureProperties> featuresList = new ArrayList<FeatureProperties>();
        for (String featureName : this.featuresNames) {
            FeatureProperties f = new FeatureProperties();
            f.setName(featureName);
            f.setAssociateName("");
            f.setMax(1);
            f.setMin(-1);
            featuresList.add(f);
        }
        properties.setFeatures(featuresList);
        WriteXML.writeXML(properties);
    }
}
