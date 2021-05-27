package view.graphs;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class MyGraphs extends AnchorPane
{

    public BubbleChart Bchart;
    public LineChart CorChart;
    public LineChart Fchart;



    public MyGraphs() {
        super();
        try {
            FXMLLoader fxl = new FXMLLoader();
            AnchorPane graph = fxl.load(getClass().getResource("MyGraphs.fxml").openStream());
            MyGraphController myGraphController = fxl.getController();
            Bchart = myGraphController.algoChart;
            CorChart = myGraphController.CorrelatedChart;
            Fchart = myGraphController.FeatureChart;



            this.getChildren().add(graph);


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

}

