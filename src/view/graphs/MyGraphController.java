package view.graphs;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.BubbleChart;

public class MyGraphController {

    @FXML LineChart <Number,Number> CorChart;
    @FXML LineChart  <Number,Number> Fchart;
    @FXML BubbleChart  <Number,Number> Bchart;


    public MyGraphController() {
        super();


    }

    public BubbleChart getBchart() {
        return Bchart;
    }

    public void setBchart(BubbleChart bchart) {
        Bchart = bchart;
    }

    public LineChart getCorChart() {
        return CorChart;
    }

    public void setCorChart(LineChart corChart) {
        CorChart = corChart;
    }

    public LineChart getFchart() {
        return Fchart;
    }

    public void setFchart(LineChart fchart) {
        Fchart = fchart;
    }
    }