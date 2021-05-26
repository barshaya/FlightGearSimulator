package Server;

import model.Properties;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.*;

public class HandleXml {

    private List<Properties> PropertiesList;
    private Map<String, String> RealToAssosicate;
    private Map<String, String> AssosicateToReal;
    private Map<String, Integer> maxMap;
    private Map<String, Integer> minMap;
    private boolean WrongFormatAlert;
    private boolean MissingArgumentsAlert;

    public HandleXml(){
        this.PropertiesList = new ArrayList<>();
        this.RealToAssosicate = new HashMap<>();
        this.AssosicateToReal = new HashMap<>();
        this.maxMap = new HashMap<>();
        this.minMap = new HashMap<>();
        this.MissingArgumentsAlert = false;
        this.WrongFormatAlert = false;
    }

    public List<Properties> getPropertiesList() {
        return PropertiesList;
    }

    public Map<String, String> getRealToAssosicate() {
        return RealToAssosicate;
    }

    public Map<String, String> getAssosicateToReal() {
        return AssosicateToReal;
    }

    public Map<String, Integer> getMaxMap() {
        return maxMap;
    }

    public Map<String, Integer> getMinMap() {
        return minMap;
    }

    public boolean isWrongFormatAlert() {
        return WrongFormatAlert;
    }

    public boolean isMissingArgumentsAlert() {
        return MissingArgumentsAlert;
    }





    /*
    public static void createXml(List<Properties> pr) throws FileNotFoundException
    {
        try{
            XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("properties.xml")));
            for(int i=0; i < pr.size(); i++){
                encoder.writeObject(pr.get(i));
                encoder.flush();
            }

            encoder.close();
        } catch(FileNotFoundException ex){
            throw ex;
        }
    }
     */


    public void readXml(String xmlFile) {
        XMLDecoder decoder = null;
        try {
            decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(xmlFile)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 9; i++) {
            try {
                Properties decodedSettings = (Properties) decoder.readObject();
                PropertiesList.add(decodedSettings);

            } catch (NumberFormatException e) {
                if (MissingArgumentsAlert == false)
                    WrongFormatAlert = true;
                break;
            } catch (ArrayIndexOutOfBoundsException e) {
                if (MissingArgumentsAlert == false)
                    WrongFormatAlert = true;
                break;
            } catch (ClassCastException e) {
                if (WrongFormatAlert == false)
                    MissingArgumentsAlert = true;
                break;
            }
        }
        decoder.close();


        for (Properties properties : PropertiesList) {
            RealToAssosicate.put(properties.getName(), properties.getAssociateName());
            AssosicateToReal.put(properties.getAssociateName(), properties.getName());
            maxMap.put(properties.getName(), properties.getMax());
            minMap.put(properties.getName(), properties.getMin());
        }
    }


    public static void main(String[] args) throws FileNotFoundException {
    /*
        Properties pr1= new Properties();
        pr1.setName("speed");
        pr1.setAssociateName("sp");
        pr1.setMax(1000);
        pr1.setMin(-1000);

        Properties pr2= new Properties("speed1", "sp1", 1000, -1000);
        Properties pr3= new Properties("speed2", "sp2", 1000, -1000);
        Properties pr4= new Properties("speed3", "sp3", 1000, -1000);
        Properties pr5= new Properties("speed4", "sp4", 1000, -1000);
        Properties pr6= new Properties("speed5", "sp5", 1000, -1000);
        Properties pr7= new Properties("speed6", "sp6", 1000, -1000);
        Properties pr8= new Properties("speed7", "sp7", 1000, -1000);
        Properties pr9= new Properties("speed8", "sp8", 1000, -1000);

        List<Properties> plist = new ArrayList<>();
        plist.add(pr1);
        plist.add(pr2);
        plist.add(pr3);
        plist.add(pr4);
        plist.add(pr5);
        plist.add(pr6);
        plist.add(pr7);
        plist.add(pr8);
        plist.add(pr9);


        createXml(plist);

     */

//        readXml("properties.xml");
//
//        for(int i=0;i<PropertiesList.size();i++)
//            System.out.println(PropertiesList.get(i).getName());



//        Properties pr3= new Properties();
//        pr1.setName("speed");
//        pr1.setAssociateName("sp");
//        pr1.setMax(1000);
//        pr1.setMin(-1000);
//
//
//
//        Properties pr4= new Properties();
//        pr1.setName("speed");
//        pr1.setAssociateName("sp");
//        pr1.setMax(1000);
//        pr1.setMin(-1000);
//
//
//        Properties pr5= new Properties();
//        pr1.setName("speed");
//        pr1.setAssociateName("sp");
//        pr1.setMax(1000);
//        pr1.setMin(-1000);
//
//
//
//        Properties pr6= new Properties();
//        pr1.setName("speed");
//        pr1.setAssociateName("sp");
//        pr1.setMax(1000);
//        pr1.setMin(-1000);
//
//
//
//        Properties pr7= new Properties();
//        pr1.setName("speed");
//        pr1.setAssociateName("sp");
//        pr1.setMax(1000);
//        pr1.setMin(-1000);
//
//
//
//
//        Properties pr8= new Properties();
//        pr1.setName("speed");
//        pr1.setAssociateName("sp");
//        pr1.setMax(1000);
//        pr1.setMin(-1000);
//
//
//
//
//        Properties pr9= new Properties();
//        pr1.setName("speed");
//        pr1.setAssociateName("sp");
//        pr1.setMax(1000);
//        pr1.setMin(-1000);




        //HandleXml h1=new HandleXml();
       // h1.readXml("C:\\Users\\barsh\\IdeaProjects\\PTM\\properties.xml");



    }
}