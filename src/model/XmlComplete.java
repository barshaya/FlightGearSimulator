package model;

import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class XmlComplete {
    ViewNameLoader LoadRealNames ;
    Properties ClientSettings;
    String stringPath;
    ArrayList<String> fd;
    Properties backup;

    public XmlComplete() {
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
        XmlWR.WriteToXML(ClientSettings);
    }

    public Properties LoadSettingsFromClient(String path){
        Properties new_setting = new Properties();
        try {
            new_setting = XmlWR.deserializeFromXML(path);
            this.SettingCheck(new_setting);
            Alert a = new Alert(AlertType.INFORMATION);
            a.setHeaderText("XML Success");
//            a.setContentText("Success load the XML settings");
            a.show();
            this.backup = new_setting;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            if (this.backup != null) {
                Alert a = new Alert(AlertType.WARNING);
                a.setHeaderText("XML Failed");
//                a.setContentText("Failed load XML settings instead load backup settings");
                a.showAndWait();
                return this.backup;
            }
            Alert a = new Alert(AlertType.ERROR);
            a.setHeaderText("Failed load XML settings");
//            a.setContentText("Failed load XML settings");
            a.showAndWait();
            new_setting = null;
        }
        return new_setting;
    }
    //|| FeatureSetting.getAssosicate_name().equals("please Enter Title here")
    public void SettingCheck(Properties xs) throws Exception {
        for (FeatureProperties FeatureSetting : xs.getAfs()) {
            if (FeatureSetting.getAssociateName().equals("") || FeatureSetting.getAssociateName().equals("Enter name in CSV file(Assosicate_name)")  ) {
                throw new Exception("Missing Assosicate name");
            }
            if (FeatureSetting.getMax() <= FeatureSetting.getMin()) {
                throw new Exception("Invalid min max Values");
            }
        }
        if (xs.timeout == 0.0) {
            throw new Exception("Invalid timeout Value");
        }

    }
    public void SaveXml(Properties xs) {

        try {
            XmlWR.WriteToXML(xs);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("file could not save");
        }
    }


}
