package view;


import javafx.application.Application;
import javafx.stage.Stage;
import model.Model;
import model.ModelInterface;
import model.XmlComplete;
import viewModel.ViewModel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            XmlComplete xc = new XmlComplete();
            xc.WriteXmlToUser();
            FXMLLoader fxl = new FXMLLoader();
            BorderPane root = fxl.load(getClass().getResource("sample.fxml").openStream());
            Model m = new Model();
            WindowController wc = fxl.getController();
            ViewModel vm = new ViewModel(m);
            wc.init(vm);

            Scene scene = new Scene(root,900,600);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
