package view.buttons;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MyButtons extends AnchorPane {
    public Button back;
    public Button doubleBack;
    public Button doubleForward;
    public StringProperty FlightStatus;
    public Button forward;
    public Button pause;
    public Button play;
    public Button stop;
    public Slider flightSlider;
    public ChoiceBox flightSpeed;
    public Label flightTime;
    public SimpleDoubleProperty backwardSkip;
    public SimpleDoubleProperty backwardDoubleSkip;
    public DoubleProperty forwardSkip;
    public SimpleDoubleProperty forwardDoubleSkip;

    public MyButtons() {
        super();
        try {
            FXMLLoader fxl = new FXMLLoader();
            AnchorPane Buttons = fxl.load(getClass().getResource("MyButtons.fxml").openStream());
            MyButtonsController myButtonsController = fxl.getController();
            doubleBack = myButtonsController.doubleBack;
            back = myButtonsController.back;
            play = myButtonsController.play;
            pause = myButtonsController.pause;
            stop = myButtonsController.stop;
            forward = myButtonsController.forward;
            doubleForward = myButtonsController.doubleForward;
            flightSlider = myButtonsController.flightSlider;
            flightSpeed = myButtonsController.flightSpeed;
            FlightStatus = myButtonsController.FlightStatus;
            ObservableList<Number> speedList  = FXCollections.observableArrayList(0.5,1.0,1.5,2.0);
            flightSpeed.setItems(speedList);
            flightSpeed.setValue(1.0);
            flightTime = myButtonsController.flightTime;
            backwardSkip = myButtonsController.backwardSkip;
            backwardDoubleSkip = myButtonsController.backwardDoubleSkip;
            forwardSkip = myButtonsController.forwardSkip;
            forwardDoubleSkip = myButtonsController.forwardDoubleSkip;
            this.getChildren().add(Buttons);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String toStringTime(Double obj) {
        long sec = obj.longValue();
        long min = TimeUnit.SECONDS.toMinutes(sec);
        long remainSeconds = sec - TimeUnit.MINUTES.toSeconds(min);
        return String.format("%02d", min) + ":" + String.format("%02d", remainSeconds);
    }
}
