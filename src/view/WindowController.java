//package view;
//
//import Server.HandleXml;
//import algorithms.Hybrid;
//import algorithms.TimeSeriesAnomalyDetector;
//import algorithms.ZScore;
//import javafx.fxml.FXML;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.ListView;
//import javafx.scene.layout.AnchorPane;
//import javafx.stage.FileChooser;
//
//import java.awt.event.ActionEvent;
//import java.io.File;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLClassLoader;
//
//public class WindowController {
//
//
//    @FXML
//    private Button openBTN;
//
//    @FXML
//    private Button trainFile;
//
//    @FXML
//    private Button detectFile;
//
//    @FXML
//    private Button algoFile;
//
//    @FXML
//    private ListView listView;
//
//    //shared variables
//    File xmlLastUpload;
//    File detectCSVFile;
//    File trainCSVFile;
//    TimeSeriesAnomalyDetector algoInstance;
//
//
//    @FXML
//    private void handleButtonOpen(javafx.event.ActionEvent event){
//        FileChooser fc=new FileChooser();
//        File selectedFile=fc.showOpenDialog(null);
//
//        if(selectedFile == null){
//           selectedFile=xmlLastUpload;
//        }
//        else{
//            xmlLastUpload=selectedFile;
//        }
//        HandleXml handleXml=new HandleXml();
//        handleXml.readXml(selectedFile.getAbsolutePath());
//        listView.getItems().clear();
//        for(String name : handleXml.getRealToAssosicate().keySet()){
//            listView.getItems().add(name);
//        }
//
//    }
//
//
//
//    @FXML
//    private void handleButtonTrain(javafx.event.ActionEvent event){
//        FileChooser fc=new FileChooser();
//        trainCSVFile=fc.showOpenDialog(null);
//    }
//
//    @FXML
//    private void handleButtonDetect(javafx.event.ActionEvent event){
//        FileChooser fc=new FileChooser();
//        detectCSVFile=fc.showOpenDialog(null);
//
//    }
//
//    @FXML
//    private void handleButtonAlgo(javafx.event.ActionEvent event) throws Exception {
//        FileChooser fc=new FileChooser();
//        File selectedFile=fc.showOpenDialog(null);
//
//        if(selectedFile == null){
//            //send an error
//        }
//        else{
//            URLClassLoader urlClassLoader= new URLClassLoader(new URL[] {
//                    new URL(selectedFile.getPath())
//            });
//            Class<?> classInstance=urlClassLoader.loadClass("algorithms.TimeSeriesAnomalyDetector");
//            algoInstance=(TimeSeriesAnomalyDetector)classInstance.newInstance();
//        }
//
//    }
//
//
//
//
//
//
//}
package view;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import view.buttons.MyButtons;
import view.graphs.MyGraphs;
import view.joystick.MyJoystick;
import view.viewlist.MyViewList;
import view.openfiles.MyOpenFiles;
import viewModel.ViewModel;

public class WindowController implements Initializable {


    ViewModel vm;

    @FXML MyJoystick myJoystick;

    @FXML MyViewList viewlist;

    @FXML MyGraphs Graph;


    @FXML MyButtons myButtons;

    @FXML MyOpenFiles openFiles;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//	/*		// TODO Auto-generated method stub
//		myJoystick.aileron = new SimpleDoubleProperty();
//		myJoystick.elevators = new SimpleDoubleProperty();
//		myJoystick.rudder = new SimpleDoubleProperty();
//		myJoystick.throttle = new SimpleDoubleProperty();*/
//    }
//
//
//
//    @SuppressWarnings("unchecked")
//    public void init(ViewModel vm2) {
//        // TODO Auto-generated method stub
//        this.vm = vm2;
//
//        openFiles.xmlpath.addListener((o,ov,nv)->{
//            this.vm.loadXml(nv);
//            if (vm.getXs() != null) {
//                double maxR = this.vm.getXs().getSetting("rudder").getMax();
//                double minR = this.vm.getXs().getSetting("rudder").getMin();
//                double maxT = this.vm.getXs().getSetting("throttle").getMax();
//                double minT = this.vm.getXs().getSetting("throttle").getMin();
//                myJoystick.SetMaxMinForSliders(maxR, minR, maxT, minT);
//            }
//        });
//
//        openFiles.csvpath.addListener((o,ov,nv)->{
//            this.vm.loadCsv(nv);
//            ArrayList<String> titles =  this.vm.getColTitels();
//            if (titles != null) {
//                ObservableList<String> list = FXCollections.observableArrayList(titles);
//                viewlist.list.setItems(list);
//            }
//
//        });
//
//        openFiles.algoname.addListener((o,ov,nv)->{
//            this.vm.loadAnomalyAlgo(openFiles.algopath.get(), nv);
//            if (this.vm.getAd() !=null) {
//                Graph.Bchart.setTitle(nv.substring(6));
//            }
//        });
//
//        myButtons.forwardCnt.addListener((o,ov,nv)->{
//            vm.Forward1();
//        });
//        myButtons.forward2Cnt.addListener((o,ov,nv)->{
//            vm.Forward2();
//        });
//
//        myButtons.backwardCnt.addListener((o,ov,nv)->{
//            vm.Backward1();
//        });
//
//        myButtons.backward2Cnt.addListener((o,ov,nv)->{
//            vm.Backward2();
//        });
//
//
//
//
//
//        vm.rate.bindBidirectional(myButtons.videoSpeed.valueProperty());
//        myButtons.videoSpeed.valueProperty().addListener((o,ov,nv)->{
//            myButtons.videoSpeed.setValue(nv);
//        });
//        myButtons.FlightStatus.bindBidirectional(vm.FlightStatus);
//        myButtons.FlightGear.textProperty().bind(vm.FlightMessage);
//        myButtons.FlightStatus.addListener((o,ov,nv)->{
//            if (((String)nv).equals("Fly")) {
//                this.vm.StartFligt(0);
//            }
//            else if (((String)nv).equals("not Fly")) {
//                this.vm.stopFlight();
//            }
//            else if (((String)nv).equals("pause Fly")) {
//                this.vm.pauseFlight();
//            }
//        });
//
//        myJoystick.aileron.bind(this.vm.aileron);
//        myJoystick.aileron.addListener((o,ov,nv)->{
//            double maxA = this.vm.getXs().getSetting("aileron").getMax();
//            double minA = this.vm.getXs().getSetting("aileron").getMin();
//            double a=myJoystick.bigCircle.getLayoutX()-myJoystick.bigCircle.getRadius();
//            double b=myJoystick.bigCircle.getLayoutX()+myJoystick.bigCircle.getRadius();
//            nv=myJoystick.NormlaizeJoystic((double)nv ,maxA,minA,a,b);
//            myJoystick.joyCircle.setLayoutX((double) nv);
//        });
//        myJoystick.elevators.bind(this.vm.elevators);
//        myJoystick.elevators.addListener((o,ov,nv)->{
//            double maxE = this.vm.getXs().getSetting("elevator").getMax();
//            double minE = this.vm.getXs().getSetting("elevator").getMin();
//            double a=myJoystick.bigCircle.getLayoutY()-myJoystick.bigCircle.getRadius();
//            double b=myJoystick.bigCircle.getLayoutY()+myJoystick.bigCircle.getRadius();
//            nv=myJoystick.NormlaizeJoystic((double)nv ,maxE,minE,a,b);
//            myJoystick.joyCircle.setLayoutY((double) nv);
//        });
//
//        myJoystick.rudder.bind(this.vm.rudder);
//        myJoystick.throttle.bind(this.vm.throttle);
//
//        myJoystick.AltitudeValue.textProperty().bind(this.vm.heigth);
//        myJoystick.DirectionValue.textProperty().bind(this.vm.direction);
//        myJoystick.PitchValue.textProperty().bind(this.vm.pitch);
//        myJoystick.RollValue.textProperty().bind(this.vm.roll);
//        myJoystick.speedValue.textProperty().bind(this.vm.speed);
//        myJoystick.yawValue.textProperty().bind(this.vm.yaw);
//
//    }

}