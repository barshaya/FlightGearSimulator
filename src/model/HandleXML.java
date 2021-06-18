package model;

import java.beans.ExceptionListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class HandleXML {
    ViewNameLoader LoadRealNames ;
    Properties ClientSettings;
    String stringPath;
    ArrayList<String> fd;
    Properties backup;

    public HandleXML() {
        // TODO Auto-generated constructor stub
        LoadRealNames = new ViewNameLoader();
        ClientSettings = new Properties();
        stringPath = "resources/ViewNamesSettings.txt";
        fd = new ArrayList<String>();
    }

    public void  LoadRealNames() {
        this.fd  =  LoadRealNames.Load(stringPath);
    }

    public void WriteXmlToUser() throws IOException {
        this.LoadRealNames();
        ClientSettings.setHost("localhost");
        ClientSettings.setPort(5400);
        ClientSettings.setTimeout(10);
        ArrayList<FeatureProperties> fs = new ArrayList<FeatureProperties>();
        for (String s : this.fd) {
            FeatureProperties f = new FeatureProperties();
            f.setRealName(s);
            f.setAssociateName("Enter name in CSV file");
            f.setMax(1);
            f.setMin(-1);
            fs.add(f);
        }
        ClientSettings.setAfs(fs);
        WriteToXML(ClientSettings);
    }

    public Properties LoadSettingsFromClient(String path){
        Properties new_setting = new Properties();
        try {
            new_setting = deserializeFromXML(path);
            this.SettingCheck(new_setting);
            Alert a = new Alert(AlertType.INFORMATION);
            a.setHeaderText("XML Uploaded");
            a.setContentText("please upload CSV file");
            a.show();
            this.backup = new_setting;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            if (this.backup != null) {
                Alert a = new Alert(AlertType.WARNING);
                a.setHeaderText("XML Failed");
                a.showAndWait();
                return this.backup;
            }
            Alert a = new Alert(AlertType.ERROR);
            a.setHeaderText("Failed load XML settings");
            a.showAndWait();
            new_setting = null;
        }
        return new_setting;
    }

    public void SettingCheck(Properties xs) throws Exception {
        for (FeatureProperties FeatureSetting : xs.getAfs()) {
            if (FeatureSetting.getAssociateName().equals("") || FeatureSetting.getAssociateName().equals("Enter name in CSV file(Assosicate_name)")  ) {
                throw new Exception("Missing Associate name");
            }
            if (FeatureSetting.getMax() <= FeatureSetting.getMin()) {
                throw new Exception("Invalid min max Values");
            }
        }
        if (xs.timeout == 0.0) {
            throw new Exception("Invalid timeout Value");
        }
        ArrayList<Double> checkSpeed = new ArrayList<Double>(Arrays.asList(0.25,0.5,0.75,1.0,1.25,1.5,1.75,2.0));
        if (!(checkSpeed.contains(xs.getTimeout()))) {
            throw new Exception("Wrong speed");
        }

    }
    public void SaveXml(Properties xs) {

        try {
            WriteToXML(xs);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("file could not save");
        }
    }



    public static void WriteToXML (Properties settings) throws IOException
    {
        FileOutputStream fos = new FileOutputStream("resources/wrongSpeed.xml");
        XMLEncoder encoder = new XMLEncoder(fos);
        encoder.setExceptionListener(new ExceptionListener() {
            public void exceptionThrown(Exception e) {
                System.out.println("Exception! :"+e.toString());
            }
        });
        encoder.writeObject(settings);
        encoder.close();
        fos.close();
    }


    public static Properties deserializeFromXML(String path) throws Exception{
        FileInputStream fis = new FileInputStream(path);
        XMLDecoder decoder = new XMLDecoder(fis);
        Properties decodedSettings = (Properties) decoder.readObject();
        decoder.close();
        fis.close();
        return decodedSettings;
    }
}
