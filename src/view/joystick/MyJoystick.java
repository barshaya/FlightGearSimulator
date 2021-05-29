package view.joystick;

import java.io.IOException;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import view.joystick.MyJoystickController;

public class MyJoystick extends AnchorPane {
        public DoubleProperty aileron;
        public DoubleProperty elevators;
        public DoubleProperty rudder;
        public DoubleProperty throttle;
        public Circle bigCircle;
        public Circle joyCircle;
        public Label AltitudeValue;
        public Label DirectionValue;
        public Label PitchValue;
        public Label RollValue;
        public Label speedValue;
        public Label yawValue;
        public MyJoystickController myJoystickController;

        public MyJoystick() {
            try {
                FXMLLoader fxl = new FXMLLoader();
                AnchorPane joy = (AnchorPane)fxl.load(this.getClass().getResource("MyJoyStick.fxml").openStream());
                this.myJoystickController = (MyJoystickController)fxl.getController();
                this.aileron = new SimpleDoubleProperty();
                this.elevators = new SimpleDoubleProperty();
                this.rudder = this.myJoystickController.rudder.valueProperty();
                this.throttle = this.myJoystickController.throttle.valueProperty();
                this.bigCircle = this.myJoystickController.CanvasCircle;
                this.joyCircle = this.myJoystickController.movingCircle;
                this.AltitudeValue = this.myJoystickController.AltitudeValue;
                this.DirectionValue = this.myJoystickController.DirectionValue;
                this.PitchValue = this.myJoystickController.PitchValue;
                this.RollValue = this.myJoystickController.RollValue;
                this.speedValue = this.myJoystickController.speedValue;
                this.yawValue = this.myJoystickController.yawValue;
                this.getChildren().add(joy);
            } catch (IOException var3) {
                var3.printStackTrace();
            }

        }

        public double NormlaizeJoystic(double x, double max, double min, double a, double b) {
            double res = a + (x - min) * (b - a) / (max - min);
            return res;
        }

        public void SetMaxMinForSliders(double max1, double min1, double max2, double min2) {
            this.myJoystickController.rudder.setMax(max1);
            this.myJoystickController.rudder.setMin(min1);
            this.myJoystickController.throttle.setMax(max2);
            this.myJoystickController.throttle.setMin(min2);
        }
    }


