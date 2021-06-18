package view.featuresList;

import java.io.IOException;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

public class MyFeaturesList extends AnchorPane {
    public static ListView list;
    public Button xml;
    public static StringProperty xmlpath;

    public MyFeaturesList() {
        try {
            FXMLLoader fxl = new FXMLLoader();
            AnchorPane viewlist = (AnchorPane)fxl.load(this.getClass().getResource("MyFeaturesList.fxml").openStream());
            MyFeaturesListController myFeaturesListController = (MyFeaturesListController)fxl.getController();
            this.list = myFeaturesListController.listView;
            xml = myFeaturesListController.xml;
            xmlpath = myFeaturesListController.XmlPath;
            this.getChildren().add(viewlist);
        } catch (IOException var4) {
            var4.printStackTrace();
        }
    }
}
