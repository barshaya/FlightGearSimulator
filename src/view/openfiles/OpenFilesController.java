package view.openfiles;

import java.io.File;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class OpenFilesController {


	@FXML MenuItem csvTrain;
	@FXML MenuItem csvTest;
	@FXML MenuItem algo;

	StringProperty CsvTrainPath,CSVTestPath,AlgoPath,AlgoName;

	public OpenFilesController() {
		super();
		CsvTrainPath = new SimpleStringProperty();
		CSVTestPath=new SimpleStringProperty();
		ObservableList<Object> csvTitle = FXCollections.observableArrayList();
		AlgoPath = new SimpleStringProperty();
		AlgoName = new SimpleStringProperty();


	}

	public void openCSVTrainFile() {
		FileChooser fc = new FileChooser();
		fc.setTitle("\"The File Choose");
		fc.setInitialDirectory(new File("./resources"));
		ExtensionFilter ef = new ExtensionFilter("CSV Files (*.csv)","*.csv");
		fc.getExtensionFilters().add(ef);
		File chosen = fc.showOpenDialog(null);
		if(chosen!=null)
		{
			CsvTrainPath.setValue("resources/"+chosen.getName());
		}
	}


	public void openCSVTestFile() {
		FileChooser fc = new FileChooser();
		fc.setTitle("\"The File Choose");
		fc.setInitialDirectory(new File("./resources"));
		ExtensionFilter ef = new ExtensionFilter("CSV Files (*.csv)","*.csv");
		fc.getExtensionFilters().add(ef);
		File chosen = fc.showOpenDialog(null);
		if(chosen!=null)
		{
			CSVTestPath.setValue("resources/"+chosen.getName());
		}
	}

	public void openCLASSFile() {
		FileChooser fc = new FileChooser();
		fc.setTitle("The File Choose");
		fc.setInitialDirectory(new File("./out/production/PTM/algorithms"));
		ExtensionFilter ef = new ExtensionFilter("Class Files (*.class)","*.class");
		fc.getExtensionFilters().add(ef);
		File chosen = fc.showOpenDialog(null);
		if(chosen!=null)
		{
			AlgoPath.setValue("resources/"+chosen.getName());
			AlgoName.setValue("algorithms."+chosen.getName().substring(0, chosen.getName().length()-6));
		}
	}
}

