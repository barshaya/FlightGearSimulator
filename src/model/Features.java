package model;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Observable;

public class Features extends Observable {


    HashMap<String,String> properties;
    Socket flightGear;
    PrintWriter out2flightGear;

    public Features(String propertiesFileName) {
        properties=new HashMap<>();
        try {
            BufferedReader in =new BufferedReader(new FileReader(propertiesFileName));
            String line;
            while((line=in.readLine())!=null) {
                String sp[]=line.split(",");
                properties.put(sp[0], sp[1]);
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int port=Integer.parseInt(properties.get("port"));

        try {
            flightGear=new Socket(properties.get("ip"),port);
            out2flightGear=new PrintWriter(flightGear.getOutputStream());

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //we need to replace it to Interface for each other
    public void setAileron(double x) {
        String command= properties.get("aileron");
        out2flightGear.println(command+" "+x);
        out2flightGear.flush();
    }

    public void setElevators(double x) {
        String command= properties.get("elevators");
        out2flightGear.println(command+" "+x);
        out2flightGear.flush();
    }

    public void setRudder(double x) {
        String command= properties.get("rudder");
        out2flightGear.println(command+" "+x);
        out2flightGear.flush();
    }

    public void setThrottle(double x) {
        String command= properties.get("throttle");
        out2flightGear.println(command+" "+x);
        out2flightGear.flush();
    }

    @Override
    public void finalize() {
        try {
            flightGear.close();
            out2flightGear.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

