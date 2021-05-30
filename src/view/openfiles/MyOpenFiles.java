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


    public MyOpenFiles() {
        super();
        try {
            FXMLLoader fxl = new FXMLLoader();
            AnchorPane open = fxl.load(getClass().getResource("MyOpenFiles.fxml").openStream());
            MyOpenFilesController myOpenFilesController = fxl.getController();
            algo = myOpenFilesController.algo;
            algoname = myOpenFilesController.AlgoName;
            algopath = myOpenFilesController.AlgoPath;
            csv = myOpenFilesController.csv;
            csvpath = myOpenFilesController.CsvPath;
            this.getChildren().add(open);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
