package view.buttons;

import java.util.concurrent.TimeUnit;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;

public class MyButtonsController {

    @FXML Button doubleBack;
    @FXML Button back;
    @FXML Button play;
    @FXML Button pause;
    @FXML Button stop;
    @FXML Button forward;
    @FXML Button doubleForward;
    @FXML ChoiceBox flightSpeed;
    @FXML Slider flightSlider;
    @FXML Label flightTime;
    SimpleDoubleProperty backwardSkip;
    SimpleDoubleProperty backwardDoubleSkip;
    DoubleProperty forwardSkip;
    SimpleDoubleProperty forwardDoubleSkip;
    StringProperty FlightStatus;
    double d = 0;

    public MyButtonsController() {
        super();
        // TODO Auto-generated constructor stub
        FlightStatus = new SimpleStringProperty();
        forwardSkip = new SimpleDoubleProperty();
        forwardDoubleSkip = new SimpleDoubleProperty();
        backwardSkip = new SimpleDoubleProperty();
        backwardDoubleSkip = new SimpleDoubleProperty();
    }

    @FXML public void startFlight() {
        FlightStatus.setValue("Fly");
    }

    @FXML private void stopFlight() {
        if ((!FlightStatus.getValue().equals("Fly") && !FlightStatus.getValue().equals("pause Fly") &&!FlightStatus.getValue().equals("skip") ) || FlightStatus.getValue() == null ) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Error - can't stop the flight");
            alert.setContentText("No flight");
            alert.showAndWait();
        }
        else {
            FlightStatus.set("Not Fly");
        }
    }

    @FXML private void pauseFlight() {
        if ( FlightStatus.getValue().equals("not Fly") || FlightStatus.getValue() == null) {
            Alert a = new Alert(AlertType.ERROR);
            a.setHeaderText("Error - can't pause flight");
            a.setContentText("No flight");
            a.showAndWait();
        }
        else {
            FlightStatus.set("Pause Fly");
        }
    }

    @FXML public void skipForward() {
        FlightStatus.set("skip");
        forwardSkip.setValue(d++);
    }

    @FXML public void skipDoubleForward() {
        FlightStatus.set("skip");
        forwardDoubleSkip.setValue(d++);
    }

    @FXML public void skipBack() {
        FlightStatus.set("skip");
        backwardSkip.setValue(d++);
    }

    @FXML public void skipDoubleBack() {
        FlightStatus.set("skip");
        backwardDoubleSkip.setValue(d++);
    }

    public String toStringTime(Double obj) {
        long sec = obj.longValue();
        long min = TimeUnit.SECONDS.toMinutes(sec);
        long remainSeconds = sec - TimeUnit.MINUTES.toSeconds(min);
        return String.format("%02d", min) + ":" + String.format("%02d", remainSeconds);
    }
}
