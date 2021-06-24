package view.graphs;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.BubbleChart;
import javafx.scene.control.Label;

public class MyGraphController {

    @FXML LineChart <Number,Number> CorChart;
    @FXML LineChart  <Number,Number> Fchart;
    @FXML BubbleChart  <Number,Number> Bchart;
    @FXML Label selectedF;
    @FXML Label correlatedF;


    public MyGraphController() {
        super();


    }

    }