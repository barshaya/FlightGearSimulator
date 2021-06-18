package view.openfiles;

import java.io.IOException;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

public class OpenFiles extends AnchorPane{

	public MenuItem algo;
	public StringProperty algoname;
	public StringProperty algopath;
	public MenuItem csv;
	public StringProperty csvpath;

	public OpenFiles() {
		super();
		try {
			FXMLLoader fxl = new FXMLLoader();
			AnchorPane open = fxl.load(getClass().getResource("OpenFiles.fxml").openStream());
			OpenFilesController myOpenFilesController = fxl.getController();
			algo = myOpenFilesController.algo;
			algoname = myOpenFilesController.AlgoName;
			algopath = myOpenFilesController.AlgoPath;
			csv = myOpenFilesController.csv;
			csvpath = myOpenFilesController.CsvPath;
			this.getChildren().add(open);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}



}
