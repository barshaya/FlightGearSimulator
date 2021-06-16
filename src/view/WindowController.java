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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

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
                myJoystick.SetMaxMinForSliders(maxR, minR, maxT, minT);
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

        myButtons.videoSlider.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(
                    ObservableValue<? extends Number> observableValue,
                    Number oldValue,
                    Number newValue) {
//                LocalTime time = LocalTime.of(newValue.intValue() / 100, newValue.intValue() % 100, newValue.intValue() / 1000);
//                String timeString = time.format(DateTimeFormatter.ofPattern("HH:mm"));
//                int seconds = newValue.intValue() % 60;
//                int hours = newValue.intValue() / 100;
//                int minutes = (newValue.intValue() - hours * 100) % 60;
//                System.out.println(minutes + ":" + seconds);
                String currTime = toStringTime(myButtons.videoSlider.doubleValue());
                System.out.println(currTime);
                myButtons.VideoTime.textProperty().setValue(
                        currTime);
                vm.setTime(newValue.intValue());
            }
        });

        vm.rate.bindBidirectional(myButtons.videoSpeed.valueProperty());
        myButtons.videoSpeed.valueProperty().addListener((o,ov,nv)->{
            myButtons.videoSpeed.setValue(nv);

        });
        myButtons.FlightStatus.bindBidirectional(vm.FlightStatus);
        myButtons.FlightGear.textProperty().bind(vm.FlightMessage);
        myButtons.FlightStatus.addListener((o,ov,nv)->{
            if (((String)nv).equals("Fly")) {
                this.vm.StartFligt(0);
            }
            else if (((String)nv).equals("not Fly")) {
                this.vm.stopFlight();
                this.vm.setTime(0);
            }
            else if (((String)nv).equals("pause Fly")) {
                int currentTime = this.vm.getTime();
                System.out.println(currentTime);
                this.vm.pauseFlight();
                this.vm.setTime(currentTime);

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
        myButtons.videoSlider.bind(this.vm.videoslider);
    }

    public String toStringTime(Double object) {
        long seconds = object.longValue();
        long minutes = TimeUnit.SECONDS.toMinutes(seconds);
        long remainingseconds = seconds - TimeUnit.MINUTES.toSeconds(minutes);
        return String.format("%02d", minutes) + ":" + String.format("%02d", remainingseconds);
    }
}