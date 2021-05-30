package model;

import java.beans.ExceptionListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WriteXML {

    public static void writeXML(Properties pr) throws IOException
    {
        XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("properties.xml")));
        encoder.setExceptionListener(new ExceptionListener() {
            public void exceptionThrown(Exception e) {
                System.out.println("Exception : "+e.toString());
            }
        });
        encoder.writeObject(pr);
        encoder.close();
    }


    public static Properties deserializeFromXML(String path) throws Exception{
        FileInputStream fis = new FileInputStream(path);
        XMLDecoder decoder = new XMLDecoder(fis);
        Properties decodedSettings = (Properties) decoder.readObject();
        decoder.close();
        fis.close();
        return decodedSettings;
    }




}
