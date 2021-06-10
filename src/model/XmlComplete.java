package model;

import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class XmlComplete {
    ViewNameLoader LoadRealNames ;
    XmlSettings ClientSettings;
    String Path_to_real_name;
    ArrayList<String> fd;
    String backup;
    XmlSettings ClientBackUp;

    public XmlComplete() {
        // TODO Auto-generated constructor stub
        LoadRealNames = new ViewNameLoader();
        ClientSettings = new XmlSettings();
        Path_to_real_name = "resources/ViewNamesSettings.txt";
        fd = new ArrayList<String>();
    }

    public void  LoadRealNames() {
        this.fd  =  LoadRealNames.Load(Path_to_real_name);
    }

    public void WriteXmlToUser() throws IOException {
        this.LoadRealNames();
        ClientSettings.setHost("localhost");
        ClientSettings.setPort(5400);
        ClientSettings.setTimeout(10);
        ArrayList<FeatureSettings> fs = new ArrayList<FeatureSettings>();
        for (String s : this.fd) {
            FeatureSettings f = new FeatureSettings();
            f.setReal_col_name(s);
            f.setAssosicate_name("Enter name in CSV file");
            f.setMax(1);
            f.setMin(-1);
            fs.add(f);
        }
        ClientSettings.setAfs(fs);
        XmlWR.WriteToXML(ClientSettings);
    }

    public XmlSettings LoadSettingsFromClient(String path){
        XmlSettings new_setting = new XmlSettings();
        try {
            new_setting = XmlWR.deserializeFromXML(path);
            this.SettingCheck(new_setting);
            Alert a = new Alert(AlertType.INFORMATION);
            a.setHeaderText("XML Success");
//            a.setContentText("Success load the XML settings");
            a.show();
            this.ClientBackUp = new_setting;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            if (this.ClientBackUp != null) {
                Alert a = new Alert(AlertType.WARNING);
                a.setHeaderText("XML Failed");
//                a.setContentText("Failed load XML settings instead load backup settings");
                a.showAndWait();
                return this.ClientBackUp;
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
    public void SettingCheck(XmlSettings xs) throws Exception {
        for (FeatureSettings FeatureSetting : xs.getAfs()) {
            if (FeatureSetting.getAssosicate_name().equals("") || FeatureSetting.getAssosicate_name().equals("Enter name in CSV file(Assosicate_name)")  ) {
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
    public void SaveXml(XmlSettings xs) {

        try {
            XmlWR.WriteToXML(xs);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("file could not save");
        }
    }


}
