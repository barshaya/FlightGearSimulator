package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Model;
import model.XmlComplete;
import view_model.ViewModel;

public class Main extends Application {
    public Main() {
    }

    public void start(Stage primaryStage) {
        try {
            XmlComplete xc = new XmlComplete();
            xc.WriteXmlToUser();
            FXMLLoader fxl = new FXMLLoader();
            BorderPane root = (BorderPane)fxl.load(this.getClass().getResource("Window.fxml").openStream());
            Model m = new Model();
            WindowController wc = (WindowController)fxl.getController();
            ViewModel vm = new ViewModel(m);
            wc.init(vm);
            Scene scene = new Scene(root, 900.0D, 600.0D);
            scene.getStylesheets().add(this.getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception var9) {
            var9.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
