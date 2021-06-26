package view;

import algorithms.CorrelatedFeatures;
import algorithms.Line;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.XYChart;
import javafx.util.Duration;
import view.buttons.MyButtons;
import view.graphs.MyGraphs;
import view.joystick.MyJoystick;
import view.openfiles.OpenFiles;
import view.featuresList.MyFeaturesList;
import viewModel.ViewModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class WindowController implements Initializable {

    @FXML
    MyJoystick myJoystick;

    @FXML
    MyFeaturesList viewlist;

    @FXML MyGraphs myGraphs;

    @FXML MyButtons myButtons;

    @FXML OpenFiles openFiles;

    ViewModel vm;


    private StringProperty selectedName=new SimpleStringProperty();
    public StringProperty selectedF=new SimpleStringProperty();
    public StringProperty correlatedF=new SimpleStringProperty();
    private XYChart.Series seriesPointA;
    private XYChart.Series seriesPointB;
    private XYChart.Series seriesPointAnomaly;
    private XYChart.Series seriesTimeAnomaly;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

//    public void setSliderMax(int max){
//        this.myButtons.myButtonsController;
//    }

    @SuppressWarnings("unchecked")
    public void init(ViewModel vm) {
        // TODO Auto-generated method stub
        this.vm = vm;
        MyFeaturesList.xmlpath.addListener((o, ov, nv) -> {
            this.vm.loadXml(nv);
            if (vm.getXs() != null) {
                double maxR = this.vm.getXs().getSetting("rudder").getMax();
                double minR = this.vm.getXs().getSetting("rudder").getMin();
                double maxT = this.vm.getXs().getSetting("throttle").getMax();
                double minT = this.vm.getXs().getSetting("throttle").getMin();
                myJoystick.SetMaxMinForSliders(maxR, minR, maxT, minT);
            }
        });


        selectedName = new SimpleStringProperty("");

        MyFeaturesList.list.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> {
            String selected = MyFeaturesList.list.getSelectionModel().getSelectedItem().toString();
            selectedName.setValue(selected);
        });


        openFiles.csvTrainpath.addListener((o, ov, nv) -> {
            this.vm.loadTrainCsv(nv);

        });

        openFiles.csvTestpath.addListener((o, ov, nv) -> {
            this.vm.loadTestCsv(nv);
            ArrayList<String> titles = this.vm.getColTitles();
            if (titles != null) {
                ObservableList<String> list = FXCollections.observableArrayList(titles);
                viewlist.list.setItems(list);
            }
            this.myButtons.myButtonsController.setSliderMax(vm.getTsTest().num);

        });

        openFiles.algoname.addListener((o, ov, nv) -> {
            this.vm.loadAnomalyAlgo(openFiles.algopath.get(), nv);
            if (this.vm.getAd() != null) {
                // myGraphs.Bchart.setTitle(nv.substring(11));
            }
        });

        myButtons.forwardCnt.addListener((o, ov, nv) -> {
            vm.Forward1();
        });
        myButtons.forward2Cnt.addListener((o, ov, nv) -> {
            vm.Forward2();
        });

        myButtons.backwardCnt.addListener((o, ov, nv) -> {
            vm.Backward1();
        });

        myButtons.backward2Cnt.addListener((o, ov, nv) -> {
            vm.Backward2();
        });

        myButtons.videoSlider.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(
                    ObservableValue<? extends Number> observableValue,
                    Number oldValue,
                    Number newValue) {
                if (vm.getTsTest() != null) {
                    if (myButtons.myButtonsController.getSlider().isPressed()) {
                        myButtons.videoSlider.unbindBidirectional(vm.videoslider);
                        myButtons.myButtonsController.getSlider().setOnMouseReleased(e -> {
                            vm.getModel().clearData(seriesPointA);
                            vm.setTimeStemp((int) myButtons.myButtonsController.getSlider().getValue());
                            myButtons.videoSlider.bindBidirectional(vm.videoslider);
                        });
                    }
                    myButtons.VideoTime.textProperty().setValue(toStringTime(myButtons.videoSlider.doubleValue()));
                }
            }
        });

        vm.rate.bindBidirectional(myButtons.videoSpeed.valueProperty());
        myButtons.videoSpeed.valueProperty().addListener((o, ov, nv) -> {
            myButtons.videoSpeed.setValue(nv);
        });
        myButtons.FlightStatus.bindBidirectional(vm.FlightStatus);
        myButtons.FlightGear.textProperty().bind(vm.FlightMessage);
        myButtons.FlightStatus.addListener((o, ov, nv) -> {
            if (((String) nv).equals("Fly")) {
                this.vm.StartFlight(0);
            } else if (((String) nv).equals("not Fly")) {
                seriesPointA.getData().clear();
                seriesPointB.getData().clear();
                this.vm.stopFlight();
                this.vm.setTime(0);
            } else if (((String) nv).equals("pause Fly")) {
                int currentTime = this.vm.getTime();
                this.vm.pauseFlight();
                this.vm.setTime(currentTime);
            }
        });

        myJoystick.aileron.bind(this.vm.aileron);
        myJoystick.aileron.addListener((o, ov, nv) -> {
            double maxA = this.vm.getXs().getSetting("aileron").getMax();
            double minA = this.vm.getXs().getSetting("aileron").getMin();
            double a = myJoystick.bigCircle.getLayoutX() - myJoystick.bigCircle.getRadius();
            double b = myJoystick.bigCircle.getLayoutX() + myJoystick.bigCircle.getRadius();
            nv = myJoystick.NormlaizeJoystic((double) nv, maxA, minA, a, b);
            myJoystick.joyCircle.setLayoutX((double) nv);
        });
        myJoystick.elevators.bind(this.vm.elevators);
        myJoystick.elevators.addListener((o, ov, nv) -> {
            double maxE = this.vm.getXs().getSetting("elevator").getMax();
            double minE = this.vm.getXs().getSetting("elevator").getMin();
            double a = myJoystick.bigCircle.getLayoutY() - myJoystick.bigCircle.getRadius();
            double b = myJoystick.bigCircle.getLayoutY() + myJoystick.bigCircle.getRadius();
            nv = myJoystick.NormlaizeJoystic((double) nv, maxE, minE, a, b);
            myJoystick.joyCircle.setLayoutY((double) nv);
        });

        myJoystick.rudder.bind(this.vm.rudder);
        myJoystick.throttle.bind(this.vm.throttle);

        myJoystick.AltitudeValue.textProperty().bind(this.vm.height);
        myJoystick.DirectionValue.textProperty().bind(this.vm.direction);
        myJoystick.PitchValue.textProperty().bind(this.vm.pitch);
        myJoystick.RollValue.textProperty().bind(this.vm.roll);
        myJoystick.speedValue.textProperty().bind(this.vm.speed);
        myJoystick.yawValue.textProperty().bind(this.vm.yaw);
        myButtons.videoSlider.bindBidirectional(this.vm.videoslider);
        myGraphs.selectedF.textProperty().bind(this.selectedF);
        myGraphs.correlatedF.textProperty().bind(this.correlatedF);


        seriesPointA = new XYChart.Series();
        seriesPointB = new XYChart.Series();
        seriesPointAnomaly = new XYChart.Series();
        seriesTimeAnomaly = new XYChart.Series();

        myGraphs.Fchart.getData().add(seriesPointA);
        myGraphs.CorChart.getData().add(seriesPointB);
        myGraphs.Bchart.getData().addAll(seriesPointAnomaly, seriesTimeAnomaly);

        selectedName.addListener((o, ov, nv) -> {
            if ((!(selectedName.getValue().equals(""))) && nv.equals(ov)) {
                this.vm.getModel().addValueAtTime(selectedName.getValue(), seriesPointA);
            } else if (!nv.equals(ov)) {
                selectedF.setValue(nv);
                this.vm.getModel().addValueUntilTime(selectedName.getValue(), seriesPointA);
                this.vm.getModel().clearData(seriesPointA);
            }


            String fCor = this.vm.getModel().FindCorrelative(selectedName.getValue(), openFiles.algoname.getValue());
             if (fCor != null) {
                 correlatedF.setValue(fCor);
                 if((openFiles.algoname.getValue().substring(11)).equals("Linear")) {
                     Line l = this.vm.getModel().FindCorrelativeLine(selectedName.getValue(), openFiles.algoname.getValue());
                     this.vm.getModel().addLine(l, seriesTimeAnomaly);
                 }
             } else {
                 correlatedF.setValue("no correlated feature");
                 this.vm.getModel().clearData(seriesPointB);
                 this.vm.getModel().clearData(seriesTimeAnomaly);
                 this.vm.getModel().clearData(seriesPointAnomaly);

             }



        });


        this.vm.videoslider.addListener((o, ov, nv) -> {
            if (!(selectedName.getValue().equals(""))) {
                this.vm.getModel().addValueAtTime(selectedName.getValue(), seriesPointA);
                String fCor = this.vm.getModel().FindCorrelative(selectedName.getValue(), openFiles.algoname.getValue());

                if (fCor != null) {

                    if ((ov.intValue() + 1) == nv.intValue()) {
                        this.vm.getModel().addValueAtTime(fCor, seriesPointB);
                        if((openFiles.algoname.getValue().substring(11)).equals("Linear")) {
                            this.vm.getModel().addAnomalyPoint(selectedName.getValue(), fCor, seriesPointAnomaly);
                        }
                    }else {
                        this.vm.getModel().addValueUntilTime(fCor, seriesPointB);
                    }

                } else { //no correlative
                    correlatedF.setValue("no correlated feature");
                    this.vm.getModel().clearData(seriesPointB);
                    this.vm.getModel().clearData(seriesTimeAnomaly);
                    this.vm.getModel().clearData(seriesPointAnomaly);


                }

            }
        });
    }



    public String toStringTime(Double object) {
        long seconds = object.longValue();
        long minutes = TimeUnit.SECONDS.toMinutes(seconds);
        long remainingseconds = seconds - TimeUnit.MINUTES.toSeconds(minutes);
        return String.format("%02d", minutes) + ":" + String.format("%02d", remainingseconds);
    }
}