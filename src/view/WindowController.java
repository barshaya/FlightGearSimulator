package view;

import Server.HandleXml;
import algorithms.Hybrid;
import algorithms.TimeSeriesAnomalyDetector;
import algorithms.ZScore;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.awt.event.ActionEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class WindowController {


    @FXML
    private Button openBTN;

    @FXML
    private Button trainFile;

    @FXML
    private Button detectFile;

    @FXML
    private Button algoFile;

    @FXML
    private ListView listView;

    //shared variables
    File xmlLastUpload;
    File detectCSVFile;
    File trainCSVFile;
    TimeSeriesAnomalyDetector algoInstance;


    @FXML
    private void handleButtonOpen(javafx.event.ActionEvent event){
        FileChooser fc=new FileChooser();
        File selectedFile=fc.showOpenDialog(null);

        if(selectedFile == null){
           selectedFile=xmlLastUpload;
        }
        else{
            xmlLastUpload=selectedFile;
        }
        HandleXml handleXml=new HandleXml();
        handleXml.readXml(selectedFile.getAbsolutePath());
        listView.getItems().clear();
        for(String name : handleXml.getRealToAssosicate().keySet()){
            listView.getItems().add(name);
        }

    }



    @FXML
    private void handleButtonTrain(javafx.event.ActionEvent event){
        FileChooser fc=new FileChooser();
        trainCSVFile=fc.showOpenDialog(null);
    }

    @FXML
    private void handleButtonDetect(javafx.event.ActionEvent event){
        FileChooser fc=new FileChooser();
        detectCSVFile=fc.showOpenDialog(null);

    }

    @FXML
    private void handleButtonAlgo(javafx.event.ActionEvent event) throws Exception {
        FileChooser fc=new FileChooser();
        File selectedFile=fc.showOpenDialog(null);

        if(selectedFile == null){
            //send an error
        }
        else{
            URLClassLoader urlClassLoader= new URLClassLoader(new URL[] {
                    new URL(selectedFile.getPath())
            });
            Class<?> classInstance=urlClassLoader.loadClass("algorithms.TimeSeriesAnomalyDetector");
            algoInstance=(TimeSeriesAnomalyDetector)classInstance.newInstance();
        }

    }






}
