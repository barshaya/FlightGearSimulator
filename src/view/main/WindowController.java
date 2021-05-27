package view.main;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import view.buttons.MyButtons;
import view.graphs.MyGraphs;
import view.graphs.MyGraphs;
import view.joystick.MyJoyStick;
import view.openfiles.OpenFiles;
import view.viewlist.MyViewList;
import view_model.ViewModel;

public class WindowController implements Initializable {
    ViewModel vm;
    @FXML
    MyJoystick myJoystick;
    @FXML
    MyViewList viewlist;
    @FXML
    MyGraphs Graph;
    @FXML
    MyButtons myButtons;
    @FXML
    OpenFiles openFiles;

    public WindowController() {
    }

    public void initialize(URL location, ResourceBundle resources) {
    }

    public void init(ViewModel vm2) {
        this.vm = vm2;
        this.openFiles.xmlpath.addListener((o, ov, nv) -> {
            this.vm.loadXml(nv);
            if (this.vm.getXs() != null) {
                double maxR = this.vm.getXs().getSetting("rudder").getMax();
                double minR = this.vm.getXs().getSetting("rudder").getMin();
                double maxT = this.vm.getXs().getSetting("throttle").getMax();
                double minT = this.vm.getXs().getSetting("throttle").getMin();
                this.myJoystick.SetMaxMinForSliders(maxR, minR, maxT, minT);
            }

        });
        this.openFiles.csvpath.addListener((o, ov, nv) -> {
            this.vm.loadCsv(nv);
            ArrayList<String> titles = this.vm.getColTitels();
            if (titles != null) {
                ObservableList<String> list = FXCollections.observableArrayList(titles);
                this.viewlist.list.setItems(list);
            }

        });
        this.openFiles.algoname.addListener((o, ov, nv) -> {
            this.vm.loadAnomalyAlgo((String)this.openFiles.algopath.get(), nv);
            if (this.vm.getAd() != null) {
                this.Graph.Bchart.setTitle(nv.substring(6));
            }

        });
        this.myButtons.forwardCnt.addListener((o, ov, nv) -> {
            this.vm.Forward1();
        });
        this.myButtons.forward2Cnt.addListener((o, ov, nv) -> {
            this.vm.Forward2();
        });
        this.myButtons.backwardCnt.addListener((o, ov, nv) -> {
            this.vm.Backward1();
        });
        this.myButtons.backward2Cnt.addListener((o, ov, nv) -> {
            this.vm.Backward2();
        });
        this.vm.rate.bindBidirectional(this.myButtons.videoSpeed.valueProperty());
        this.myButtons.videoSpeed.valueProperty().addListener((o, ov, nv) -> {
            this.myButtons.videoSpeed.setValue(nv);
        });
        this.myButtons.FlightStatus.bindBidirectional(this.vm.FlightStatus);
        this.myButtons.FlightGear.textProperty().bind(this.vm.FlightMessage);
        this.myButtons.FlightStatus.addListener((o, ov, nv) -> {
            if (nv.equals("Fly")) {
                this.vm.StartFligt(0);
            } else if (nv.equals("not Fly")) {
                this.vm.stopFlight();
            } else if (nv.equals("pause Fly")) {
                this.vm.pauseFlight();
            }

        });
        this.myJoystick.aileron.bind(this.vm.aileron);
        this.myJoystick.aileron.addListener((o, ov, nv) -> {
            double maxA = this.vm.getXs().getSetting("aileron").getMax();
            double minA = this.vm.getXs().getSetting("aileron").getMin();
            double a = this.myJoystick.bigCircle.getLayoutX() - this.myJoystick.bigCircle.getRadius();
            double b = this.myJoystick.bigCircle.getLayoutX() + this.myJoystick.bigCircle.getRadius();
            Number nvx = this.myJoystick.NormlaizeJoystic((Double)nv, maxA, minA, a, b);
            this.myJoystick.joyCircle.setLayoutX((Double)nvx);
        });
        this.myJoystick.elevators.bind(this.vm.elevators);
        this.myJoystick.elevators.addListener((o, ov, nv) -> {
            double maxE = this.vm.getXs().getSetting("elevator").getMax();
            double minE = this.vm.getXs().getSetting("elevator").getMin();
            double a = this.myJoystick.bigCircle.getLayoutY() - this.myJoystick.bigCircle.getRadius();
            double b = this.myJoystick.bigCircle.getLayoutY() + this.myJoystick.bigCircle.getRadius();
            Number nvx = this.myJoystick.NormlaizeJoystic((Double)nv, maxE, minE, a, b);
            this.myJoystick.joyCircle.setLayoutY((Double)nvx);
        });
        this.myJoystick.rudder.bind(this.vm.rudder);
        this.myJoystick.throttle.bind(this.vm.throttle);
        this.myJoystick.AltitudeValue.textProperty().bind(this.vm.heigth);
        this.myJoystick.DirectionValue.textProperty().bind(this.vm.direction);
        this.myJoystick.PitchValue.textProperty().bind(this.vm.pitch);
        this.myJoystick.RollValue.textProperty().bind(this.vm.roll);
        this.myJoystick.speedValue.textProperty().bind(this.vm.speed);
        this.myJoystick.yawValue.textProperty().bind(this.vm.yaw);
    }
}
