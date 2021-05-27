package viewModel;

import java.util.Observable;
import java.util.Observer;

import javafx.beans.property.*;
import model.Features;
import model.Model;
import view.HandleGUI;

public class ViewModel implements Observer {

    Features features;
    public DoubleProperty aileron=new SimpleDoubleProperty();
    public DoubleProperty elevators=new SimpleDoubleProperty();
    public DoubleProperty rudder=new SimpleDoubleProperty();
    public DoubleProperty throttle=new SimpleDoubleProperty();
    public StringProperty yaw=new SimpleStringProperty();
    public StringProperty roll=new SimpleStringProperty();
    public StringProperty pitch=new SimpleStringProperty();
    public StringProperty speed=new SimpleStringProperty();
    public StringProperty direction=new SimpleStringProperty();
    public StringProperty heigth=new SimpleStringProperty();
    public StringProperty line=new SimpleStringProperty();
    public StringProperty FlightStatus = new SimpleStringProperty();
    public StringProperty FlightMessage= new SimpleStringProperty();
    public SimpleDoubleProperty rate = new SimpleDoubleProperty();

    public ViewModel(Features features) {
        this.features=features;
        features.addObserver(this);
        aileron=new SimpleDoubleProperty();
        elevators=new SimpleDoubleProperty();
        rudder=new SimpleDoubleProperty();
        throttle=new SimpleDoubleProperty();

        aileron.addListener((obj,oldval,newval)->features.setAileron((double)newval));
        elevators.addListener((obj,oldval,newval)->features.setElevators((double)newval));
        rudder.addListener((obj,oldval,newval)->features.setRudder((double)newval));
        throttle.addListener((obj,oldval,newval)->features.setThrottle((double)newval));

    }

    @Override
    public void update(Observable o, Object arg) {

    }

}
