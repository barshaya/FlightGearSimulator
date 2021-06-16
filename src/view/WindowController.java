package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import view.buttons.MyButtons;
import view.graphs.MyGraphs;
import view.joystick.MyJoystick;
import view.openfiles.OpenFiles;
import view.viewlist.MyViewList;
import viewModel.ViewModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class WindowController implements Initializable {


    ViewModel vm;

    @FXML MyJoystick myJoystick;

    @FXML MyViewList viewlist;

    @FXML MyGraphs Graph;


    @FXML MyButtons myButtons;

    @FXML OpenFiles openFiles;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }



    @SuppressWarnings("unchecked")
    public void init(ViewModel vm2) {
        // TODO Auto-generated method stub
        this.vm = vm2;

        MyViewList.xmlpath.addListener((o,ov,nv)->{
            this.vm.loadXml(nv);
            if (vm.getXs() != null) {
                double maxR = this.vm.getXs().getSetting("rudder").getMax();
                double minR = this.vm.getXs().getSetting("rudder").getMin();
                double maxT = this.vm.getXs().getSetting("throttle").getMax();
                double minT = this.vm.getXs().getSetting("throttle").getMin();
                double maxTime = this.vm.getTs().NumOfRows;
                myJoystick.SetMaxMinForSliders(maxR, minR, maxT, minT);
                myButtons.setMaxSlider(maxTime);
            }
        });

        openFiles.csvpath.addListener((o,ov,nv)->{
            this.vm.loadCsv(nv);
            ArrayList<String> titles =  this.vm.getColTitels();
            if (titles != null) {
                ObservableList<String> list = FXCollections.observableArrayList(titles);
                viewlist.list.setItems(list);
            }

        });

        openFiles.algoname.addListener((o,ov,nv)->{
            this.vm.loadAnomalyAlgo(openFiles.algopath.get(), nv);
            if (this.vm.getAd() !=null) {
                Graph.Bchart.setTitle(nv.substring(11));
            }
        });

        myButtons.forwardCnt.addListener((o,ov,nv)->{
            vm.Forward1();
        });
        myButtons.forward2Cnt.addListener((o,ov,nv)->{
            vm.Forward2();
        });

        myButtons.backwardCnt.addListener((o,ov,nv)->{
            vm.Backward1();
        });

        myButtons.backward2Cnt.addListener((o,ov,nv)->{
            vm.Backward2();
        });

        vm.rate.bindBidirectional(myButtons.videoSpeed.valueProperty());
        myButtons.videoSpeed.valueProperty().addListener((o,ov,nv)->{
            myButtons.videoSpeed.setValue(nv);

        });


        myButtons.videoSlider.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(
                    ObservableValue<? extends Number> observableValue,
                    Number oldValue,
                    Number newValue) {
                myButtons.VideoTime.textProperty().setValue(
                        String.valueOf(newValue.intValue()));
            }
        });

        myButtons.FlightStatus.bindBidirectional(vm.FlightStatus);
        myButtons.FlightGear.textProperty().bind(vm.FlightMessage);
        myButtons.FlightStatus.addListener((o,ov,nv)->{
            if (((String)nv).equals("Fly")) {
                this.vm.StartFligt(0);
            }
            else if (((String)nv).equals("not Fly")) {
                this.vm.stopFlight();
            }
            else if (((String)nv).equals("pause Fly")) {
                this.vm.pauseFlight();
            }
        });

        myJoystick.aileron.bind(this.vm.aileron);
        myJoystick.aileron.addListener((o,ov,nv)->{
            double maxA = this.vm.getXs().getSetting("aileron").getMax();
            double minA = this.vm.getXs().getSetting("aileron").getMin();
            double a=myJoystick.bigCircle.getLayoutX()-myJoystick.bigCircle.getRadius();
            double b=myJoystick.bigCircle.getLayoutX()+myJoystick.bigCircle.getRadius();
            nv=myJoystick.NormlaizeJoystic((double)nv ,maxA,minA,a,b);
            myJoystick.joyCircle.setLayoutX((double) nv);
        });
        myJoystick.elevators.bind(this.vm.elevators);
        myJoystick.elevators.addListener((o,ov,nv)->{
            double maxE = this.vm.getXs().getSetting("elevator").getMax();
            double minE = this.vm.getXs().getSetting("elevator").getMin();
            double a=myJoystick.bigCircle.getLayoutY()-myJoystick.bigCircle.getRadius();
            double b=myJoystick.bigCircle.getLayoutY()+myJoystick.bigCircle.getRadius();
            nv=myJoystick.NormlaizeJoystic((double)nv ,maxE,minE,a,b);
            myJoystick.joyCircle.setLayoutY((double) nv);
        });

        myJoystick.rudder.bind(this.vm.rudder);
        myJoystick.throttle.bind(this.vm.throttle);

        myJoystick.AltitudeValue.textProperty().bind(this.vm.heigth);
        myJoystick.DirectionValue.textProperty().bind(this.vm.direction);
        myJoystick.PitchValue.textProperty().bind(this.vm.pitch);
        myJoystick.RollValue.textProperty().bind(this.vm.roll);
        myJoystick.speedValue.textProperty().bind(this.vm.speed);
        myJoystick.yawValue.textProperty().bind(this.vm.yaw);

    }
}