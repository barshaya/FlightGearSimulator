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
    public Button doubleback;
    public Button doubleforward;
    public StringProperty FlightStatus;
    public Button forward;
    public Button pause;
    public Button play;
    public Button stop;
    public DoubleProperty videoSlider;
    public ChoiceBox videoSpeed;
    public Label VideoTime;
    public Label FlightGear;
    public DoubleProperty forwardCnt;
    public SimpleDoubleProperty forward2Cnt;
    public SimpleDoubleProperty backwardCnt;
    public SimpleDoubleProperty backward2Cnt;


    public MyButtons() {
        super();
        try {
            FXMLLoader fxl = new FXMLLoader();
            AnchorPane Buttons = fxl.load(getClass().getResource("MyButtons.fxml").openStream());
            MyButtonsController myButtonsController = fxl.getController();
            back = myButtonsController.back;
            doubleback = myButtonsController.doubleback;
            doubleforward = myButtonsController.doubleforward;
            FlightStatus = myButtonsController.FlightStatus;
            forward = myButtonsController.forward;
            pause = myButtonsController.pause;
            play = myButtonsController.play;
            stop = myButtonsController.stop;
            videoSlider = myButtonsController.videoSlider.valueProperty();
            videoSpeed = myButtonsController.videoSpeed;
            ObservableList<Number> s  = FXCollections.observableArrayList(0.25,0.5,0.75,1.0,1.25,1.5,1.75,2.0);
            videoSpeed.setItems(s);
            videoSpeed.setValue(1.0);
            VideoTime = myButtonsController.VideoTime;
            FlightGear = myButtonsController.FlightGear;
            forwardCnt = myButtonsController.forwardCnt;
            forward2Cnt = myButtonsController.forward2Cnt;
            backwardCnt = myButtonsController.backwardCnt;
            backward2Cnt = myButtonsController.backward2Cnt;
            this.getChildren().add(Buttons);


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
