package org.manlu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import org.manlu.controller.MainController;

public class MainFrame extends Application {
   public static Stage stage;
    @Override
    public void start(Stage stage) throws Exception {
//        TabPane root= FXMLLoader.load(getClass().getResource("fxml/Main.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/main.fxml"));
        Scene scene=new Scene(fxmlLoader.load());
        MainFrame.stage=stage;
        stage.setScene(scene);
        stage.setTitle("Fofa爬虫");
        stage.show();

        stage.setOnCloseRequest(event -> {
            if (MainController.api_thread!=null && MainController.api_thread.isAlive()){
                MainController.api_thread.stop();
            }
            if (MainController.spi_thread!=null && MainController.spi_thread.isAlive()){
                MainController.spi_thread.stop();
            }
        });
    }

}
