package view.graphs;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class MyGraphs extends AnchorPane
{

    public LineChart <Number,Number> Bchart;
    public LineChart <Number,Number> CorChart;
    public LineChart  <Number,Number> Fchart;
    MyGraphController myGraphController;
    public Label selectedF;
    public Label correlatedF;
    public Label selectedAlgo;


    public MyGraphs() {
        super();
        try {
            FXMLLoader fxl = new FXMLLoader();
            AnchorPane graph = fxl.load(getClass().getResource("MyGraphs.fxml").openStream());
            myGraphController = fxl.getController();
            Bchart = myGraphController.Bchart;
            CorChart = myGraphController.CorChart;
            Fchart = myGraphController.Fchart;
            selectedF=this.myGraphController.selectedF;
            correlatedF= this.myGraphController.correlatedF;
            selectedAlgo=this.myGraphController.selectedAlgo;



            this.getChildren().add(graph);


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
    public MyGraphController getMyGraphController() {
        return myGraphController;
    }

    public void setMyGraphController(MyGraphController myGraphController) {
        this.myGraphController = myGraphController;
    }




}

