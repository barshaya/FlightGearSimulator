package view.buttons;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;

public class MyButtonsController {

    @FXML Button doubleback;
    @FXML Button back;
    @FXML Button play;
    @FXML Button pause;
    @FXML Button stop;
    @FXML Button forward;
    @FXML Button doubleforward;
    @FXML ChoiceBox videoSpeed;
    @FXML Slider videoSlider;
    @FXML Label VideoTime;
    StringProperty FlightStatus;
    @FXML Label FlightGear;
    DoubleProperty forwardCnt;
    double f = 0;
    SimpleDoubleProperty forward2Cnt;
    SimpleDoubleProperty backwardCnt;
    SimpleDoubleProperty backward2Cnt;




    public MyButtonsController() {
        super();
        // TODO Auto-generated constructor stub
        FlightStatus = new SimpleStringProperty();
        forwardCnt = new SimpleDoubleProperty();
        forward2Cnt = new SimpleDoubleProperty();
        backwardCnt = new SimpleDoubleProperty();
        backward2Cnt = new SimpleDoubleProperty();
        this.videoSlider = new Slider();
    }

    public void setSliderMax(int max){
        this.videoSlider.setMax((double)max);
    }

    public Slider getSlider(){
        return this.videoSlider;
    }

    @FXML public void startFlight() {
        FlightStatus.setValue("Fly");
    }

    @FXML private void stopFlight() {
        if ( FlightStatus.getValue() == null || (!FlightStatus.getValue().equals("Fly") && !FlightStatus.getValue().equals("pause Fly") &&!FlightStatus.getValue().equals("skip") ) ) {
            Alert a = new Alert(AlertType.ERROR);
            a.setHeaderText("Error on stop flight");
            a.setContentText("there is no flight to stop");
            a.showAndWait();
        }
        else {
            FlightStatus.set("not Fly");
        }
    }

    @FXML private void pauseFlight() {
        if ( FlightStatus.getValue() == null || FlightStatus.getValue().equals("not Fly")) {
            Alert a = new Alert(AlertType.ERROR);
            a.setHeaderText("Error on pause flight");
            a.setContentText("there is no flight to pause");
            a.showAndWait();
        }
        else {
            FlightStatus.set("pause Fly");
        }
    }

    @FXML public void SkipForward() {
        forwardCnt.setValue(f++);
        FlightStatus.set("skip");
    }
    @FXML public void SkipForwardDouble() {
        forward2Cnt.setValue(f++);
        FlightStatus.set("skip");
    }
    @FXML public void Skipbackward() {
        backwardCnt.setValue(f++);
        FlightStatus.set("skip");
    }
    @FXML public void SkipbackwardDouble() {
        backward2Cnt.setValue(f++);
        FlightStatus.set("skip");
    }


}
