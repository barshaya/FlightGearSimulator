package view.joystick;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.shape.Circle;

public class MyJoystickController {
    @FXML
    Circle movingCircle;
    @FXML
    Slider rudder;
    @FXML
    Slider throttle;
    @FXML
    Label yawValue;
    @FXML
    Label AltitudeValue;
    @FXML
    Label DirectionValue;
    @FXML
    Label speedValue;
    @FXML
    Label PitchValue;
    @FXML
    Label RollValue;
    @FXML
    Circle CanvasCircle;

    public MyJoystickController() {
    }
}
