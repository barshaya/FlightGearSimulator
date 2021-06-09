package view.viewlist;

import java.io.IOException;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

public class MyViewList extends AnchorPane {
    public ListView list;
    public Button xml;
    public static StringProperty xmlpath;

    public MyViewList() {
        try {
            FXMLLoader fxl = new FXMLLoader();
            AnchorPane viewlist = (AnchorPane)fxl.load(this.getClass().getResource("MyViewList.fxml").openStream());
            MyViewListController myViewListController = (MyViewListController)fxl.getController();
            this.list = myViewListController.listView;
            xml = myViewListController.xml;
            xmlpath = myViewListController.XmlPath;
            this.getChildren().add(viewlist);
        } catch (IOException var4) {
            var4.printStackTrace();
        }
    }
}
