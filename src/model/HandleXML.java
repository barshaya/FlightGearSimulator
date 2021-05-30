package model;

import javafx.scene.control.Alert;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class HandleXML {

    Properties backup;


    public Properties LoadSettingsFromClient(String path){
        Properties new_properties = new Properties();
        try {
            new_properties = WriteXML.deserializeFromXML(path);
            this.checkProperties(new_properties);
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("Xml Success");
            a.setContentText("Success xml settings load");
            a.show();
            this.backup = new_properties;
        } catch (Exception e) {
            if (this.backup != null) {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setHeaderText("Xml Failed");
                a.setContentText("Failed xml load settings load backup instaed");
                a.showAndWait();
                return this.backup;
            }
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Xml Failed");
            a.setContentText("Failed xml load settings ");
            a.showAndWait();
            new_properties = null;
        }
        return new_properties;
    }

    public void checkProperties(Properties p) throws Exception {
        for (FeatureProperties f : p.getFeatures()) {
            if (f.getAssociateName().equals("")) {
                throw new Exception("Missing Associate_name");
            }
            if (f.getMax() <= f.getMin()) {
                throw new Exception("invalid min max Values");
            }
        }
        if (p.timeout == 0.0) {
            throw new Exception("invalid timeout Value");
        }

    }
    public void SaveXml(Properties p) {

        try {
            WriteXML.writeXML(p);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("file could not save");
        }
    }

}
