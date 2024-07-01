package org.manlu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.manlu.controller.ExportController;

public class ExportFrame  extends Application {
    public static Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception {
//        AnchorPane root= FXMLLoader.load(getClass().getResource("fxml/export.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/manlu/fxml/export.fxml"));
        Scene scene=new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.initOwner(MainFrame.stage);
        primaryStage.show();
        stage=primaryStage;
        stage.setOnCloseRequest(e->{
            if (ExportController.exporter!=null && ExportController.exporter.isAlive()){
                ExportController.exporter.stop();
            }
        });
    }
}
