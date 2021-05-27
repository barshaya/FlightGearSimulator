package view.viewlist;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;

import java.io.File;

public class MyViewListController {


        @FXML
        ListView listView;


        StringProperty XmlPath;

        public MyViewListController() {
                super();
                XmlPath = new SimpleStringProperty();
        }

        public void openXMLFile() {
                FileChooser fc = new FileChooser();
                fc.setTitle("File Choose");
                fc.setInitialDirectory(new File("./resources"));
                FileChooser.ExtensionFilter ef = new FileChooser.ExtensionFilter("XML Files (*.xml)", "*.xml");
                fc.getExtensionFilters().add(ef);
                File chosen = fc.showOpenDialog(null);
                if (chosen != null) {
                        //vm.loadXml("resources/"+chosen.getName());
                        XmlPath.setValue("resources/" + chosen.getName());

                }
        }

}
