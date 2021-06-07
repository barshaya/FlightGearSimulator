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

	@FXML MenuItem xml;
	@FXML MenuItem csv;
	@FXML MenuItem algo;
	
	StringProperty XmlPath,CsvPath,AlgoPath,AlgoName;
	
	public OpenFilesController() {
		super();
		XmlPath = new SimpleStringProperty();
		CsvPath = new SimpleStringProperty();	
		ObservableList<Object> csvTitle = FXCollections.observableArrayList();
		AlgoPath = new SimpleStringProperty();		
		AlgoName = new SimpleStringProperty();
		
	
	}
	
	
	public void openXMLFile() {
		FileChooser fc = new FileChooser();
		fc.setTitle("File Choose");
		fc.setInitialDirectory(new File("./resources"));
		ExtensionFilter ef = new ExtensionFilter("XML Files (*.xml)","*.xml");
		fc.getExtensionFilters().add(ef);
		File chosen = fc.showOpenDialog(null);
		if(chosen!=null)
		{
			//vm.loadXml("resources/"+chosen.getName());
			XmlPath.setValue("resources/"+chosen.getName());
			
		}
	}
	
	public void openCSVFile() {
		FileChooser fc = new FileChooser();
		fc.setTitle("File Choose");
		fc.setInitialDirectory(new File("./resources"));
		ExtensionFilter ef = new ExtensionFilter("CSV Files (*.csv)","*.csv");
		fc.getExtensionFilters().add(ef);
		File chosen = fc.showOpenDialog(null);
		if(chosen!=null)
		{
				CsvPath.setValue("resources/"+chosen.getName());
	}
}
	
	
	
	public void openCLASSFile() {
		FileChooser fc = new FileChooser();
		fc.setTitle("File Choose");
		fc.setInitialDirectory(new File("./out/production/PTM/algorithms"));
		ExtensionFilter ef = new ExtensionFilter("Class Files (*.class)","*.class");
		fc.getExtensionFilters().add(ef);
		File chosen = fc.showOpenDialog(null);
		if(chosen!=null)
		{
			AlgoPath.setValue("resources/"+chosen.getName());
			AlgoName.setValue("model."+chosen.getName().substring(0, chosen.getName().length()-6));
			}
		}
}
