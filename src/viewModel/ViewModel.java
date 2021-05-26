package viewModel;

import java.util.Observable;
import java.util.Observer;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import model.Features;
import model.Model;
import view.HandleGUI;

public class ViewModel implements Observer {

    Features features;
    public DoubleProperty aileron,elevators,rudder,throttle;

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
