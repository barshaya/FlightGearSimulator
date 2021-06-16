package model;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class FGConnection
{
    Model model;
    Properties properties;
    Socket fg;
    PrintWriter outToFG;

    //create the connection to the simulator
    public FGConnection(Properties setting) throws UnknownHostException, IOException {
        this.properties = setting;
        fg = new Socket(properties.host, properties.port);
        outToFG = new PrintWriter(fg.getOutputStream());
    }

    //send a line of values from the csv to the simulator
    public void SendCommand(ArrayList<Float> values) {
        String out = "";
        for (Float val : values) {
            out += val + ",";
        }
        String res = out.substring(0, out.length()-1);
        outToFG.println(res);
        outToFG.flush();
    }

    public void CloseSocket() {
        try {
            fg.close();
            outToFG.close();
        } catch (IOException e) {}
    }
}
