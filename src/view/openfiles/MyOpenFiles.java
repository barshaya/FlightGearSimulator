package view.openfiles;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MyOpenFiles extends AnchorPane {
    public MenuItem algo;
    public StringProperty algoname;
    public StringProperty algopath;
    public MenuItem csv;
    public StringProperty csvpath;
    public MenuItem xml;
    public StringProperty xmlpath;

    public MyOpenFiles() {
        super();
        try {
            FXMLLoader fxl = new FXMLLoader();
            AnchorPane open = fxl.load(getClass().getResource("OpenFiles.fxml").openStream());
            MyOpenFilesController myOpenFilesController = fxl.getController();
            algo = myOpenFilesController.algo;
            algoname = myOpenFilesController.AlgoName;
            algopath = myOpenFilesController.AlgoPath;
            csv = myOpenFilesController.csv;
            csvpath = myOpenFilesController.CsvPath;
            xml = myOpenFilesController.xml;
            xmlpath = myOpenFilesController.XmlPath;
            this.getChildren().add(open);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
