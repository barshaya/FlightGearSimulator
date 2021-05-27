package view.viewlist;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

public class MyViewList extends AnchorPane {
    public ListView list;

    public MyViewList() {
        try {
            FXMLLoader fxl = new FXMLLoader();
            AnchorPane viewlist = (AnchorPane)fxl.load(this.getClass().getResource("MyViewList.fxml").openStream());
            MyViewListController myViewListController = (MyViewListController)fxl.getController();
            this.list = myViewListController.listView;
            this.getChildren().add(viewlist);
        } catch (IOException var4) {
            var4.printStackTrace();
        }

    }
}
