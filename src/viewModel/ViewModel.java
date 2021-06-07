package viewModel;
import java.awt.List;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.AlgoLoader;
import model.FeatureSettings;
import model.ModelInterface;
import algorithms.TimeSeries;
import algorithms.TimeSeriesAnomalyDetector;
import model.XmlComplete;
import model.XmlSettings;
import algorithms.TimeSeries.col;

public class ViewModel implements Observer {

    ModelInterface m;
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

    XmlSettings xs;
    XmlComplete xc;
    TimeSeries ts;
    TimeSeriesAnomalyDetector ad;

    public TimeSeriesAnomalyDetector getAd() {
        return ad;
    }

    public TimeSeries getTs() {
        return ts;
    }
    public XmlSettings getXs() {
        return xs;
    }

    public DoubleProperty getAileron() {
        return aileron;
    }

    public void setAileron(DoubleProperty o) {
        this.aileron = o;
    }

    public DoubleProperty getElevators() {
        return elevators;
    }

    public void setElevators(DoubleProperty elevators) {
        this.elevators = elevators;
    }

    public DoubleProperty getRudder() {
        return rudder;
    }

    public void setRudder(DoubleProperty rudder) {
        this.rudder = rudder;
    }

    public DoubleProperty getThrottle() {
        return throttle;
    }

    public void setThrottle(DoubleProperty throttle) {
        this.throttle = throttle;
    }

    public void loadCsv(String csvPath) {
        if (this.xs == null) {
            //System.out.println();
            Alert a = new Alert(AlertType.ERROR);
            a.setHeaderText("Xml Error");
            a.setContentText("please upload fixed xml before upload csv flight");
            a.showAndWait();
        }
        else {
            this.ts = new TimeSeries(csvPath);
            if (this.ts.getFeatures() == null) {
                Alert a = new Alert(AlertType.ERROR);
                a.setHeaderText("csv Failed");
                a.setContentText("Error in csv loading try again");
                a.showAndWait();
                this.ts = null;
            }
            else {
                Alert a = new Alert(AlertType.INFORMATION);
                a.setHeaderText("csv success");
                a.setContentText("success in csv loading");
                a.showAndWait();
                m.setTimeSeries(ts);
            }
        }
    }


    public ArrayList<String> getColTitels(){
        if (ts != null) {
            return ts.getFeaturesNames();
        }
        return null;
    }

    public ViewModel(ModelInterface m) {
        super();
        this.m = m;
        xc = new XmlComplete();
        m.addObservers(this);
        rate.addListener((o,ov,nv)->m.setRate(nv.doubleValue()));
    }


    @Override
    public void update(Observable o, Object arg) {
        // TODO Auto-generated method stub
        DecimalFormat d = new DecimalFormat("#.##");
        //System.out.println(o);
        //System.out.println(arg);
        if (o == m && arg.equals("line")) {
            Platform.runLater(()->yaw.setValue(m.getLine()));
            //System.out.println(this.aileron);
        }

        if (o == m && arg.equals("aileron")) {
            this.aileron.setValue((m.getAileron()));
            //System.out.println(this.aileron);
        }
        if (o == m && arg.equals("elevators")) {
            this.elevators.setValue((m.getElevators()));
        }
        if (o == m && arg.equals("rudder")) {
            this.rudder.setValue((m.getRudder()));
        }
        if (o == m && arg.equals("throttle")) {
            this.throttle.setValue((m.getThrottle()));
            //System.out.println(this.throttle);
        }
        if (o == m && arg.equals("yaw")) {
            //this.yaw.setValue(String.valueOf(m.getYaw()));
            Platform.runLater(()->yaw.setValue(d.format(m.getYaw())));
            //System.out.println(this.throttle);
        }
        if (o == m && arg.equals("heigth")) {
            //this.heigth.setValue(String.valueOf(m.getHeigth()));
            Platform.runLater(()->heigth.setValue(d.format(m.getHeigth())));
            //System.out.println(this.heigth);
        }
        if (o == m && arg.equals("speed")) {
            //this.speed.setValue(String.valueOf(m.getSpeed()));
            Platform.runLater(()->speed.setValue(d.format(m.getSpeed())));
            //System.out.println(this.throttle);
        }
        if (o == m && arg.equals("direction")) {
            Platform.runLater(()->direction.setValue(d.format(m.getDirection())));
            //this.direction.setValue(String.valueOf(m.getDirection()));
            //System.out.println(this.throttle);
        }
        if (o == m && arg.equals("roll")) {
            Platform.runLater(()->roll.setValue(d.format(m.getRoll())));
            //this.roll.setValue(String.valueOf(m.getRoll()));
            //System.out.println(this.throttle);
        }
        if (o == m && arg.equals("pitch")) {
            //this.pitch.setValue(String.valueOf(m.getPitch()));
            Platform.runLater(()->pitch.setValue(d.format(m.getPitch())));
            //System.out.println(this.throttle);
        }
        if (o == m && arg.equals("FligthStatus")) {
            this.FlightStatus.setValue((m.getFlightStatus()));
        }
        if (o == m && arg.equals("flightMessage")) {
            this.FlightMessage.setValue((m.getConnectMessage()));
        }



    }
    public void loadXml(String name) {
        // TODO Auto-generated method stub
        xs = xc.LoadSettingsFromClient(name);
        if (xs != null && xs.getHost() != null && xs.getPort() != 0 && xs.getTimeout() != 0.0) {
            m.setClientSettings(xs);
            ArrayList<Double> checkSpeed = new ArrayList<Double>(Arrays.asList(0.5,1.0,1.5,2.0));
            if (checkSpeed.contains(xs.getTimeout())) {
                this.rate.setValue(xs.getTimeout());
            }
            else {
                Alert a = new Alert(AlertType.ERROR);
                a.setHeaderText("wrong Speed");
                a.setContentText("speed as set to defult 1.0");
                a.showAndWait();
                this.rate.setValue(1.0);
            }

        }

    }

    public FeatureSettings getFeatureSetting(String ColName) {
        for (FeatureSettings fs  : xs.getAfs()) {
            if (fs.getReal_col_name().equals(ColName)) {
                return fs;
            }
        }
        return null;
    }



    public void loadAnomalyAlgo(String p, String name) {
        // TODO Auto-generated method stub
        try {
            this.ad = new AlgoLoader(p, name).getAlgo();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Alert a = new Alert(AlertType.ERROR);
            a.setHeaderText("Algo load Failed");
            a.setContentText("unable to load this algorithm please try again");
            a.showAndWait();
            this.ad = null;
        }
        if (this.ad != null) {
            System.out.println("");
            Alert a = new Alert(AlertType.INFORMATION);
            a.setHeaderText("Algo Success");
            a.setContentText("Success Algo Loading");
            a.showAndWait();
            m.setAnomalyDetector(ad);
        }
    }

    public void StartFligt(int start) {
        // TODO Auto-generated method stub
        if (this.ts == null) {
            Alert a = new Alert(AlertType.ERROR);
            a.setHeaderText("Error on play flight");
            a.setContentText("please upload csv fligt before try to fly");
            a.showAndWait();
        }
        else {
            m.play(start);
        }

    }
    public Runnable getPaint() {
        return ad.paint();
    }

    public void stopFlight() {
        m.stop();
    }

    public void pauseFlight() {
        m.pause();

    }

    public void Forward1() {
        if (m.getTime() + 10 < ts.getFeatures().length-1) {
            m.ClearTask();
            m.play(m.getTime() + 10);
        }

    }

    public void Forward2() {
        // TODO Auto-generated method stub
        if (m.getTime() + 20 < ts.getFeatures().length-1) {
            m.ClearTask();
            m.play(m.getTime() + 20);
        }

    }

    public void Backward1() {
        // TODO Auto-generated method stub
        if (m.getTime() - 10 > 0) {
            m.ClearTask();
            m.play(m.getTime() - 10);
        }

    }

    public void Backward2() {
        // TODO Auto-generated method stub
        if (m.getTime() - 20 > 0) {
            m.ClearTask();
            m.play(m.getTime() - 20);
        }
    }
}
