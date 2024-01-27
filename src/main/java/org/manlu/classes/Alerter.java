package org.manlu.classes;

import javafx.scene.control.Alert;
import javafx.stage.Window;

public class Alerter {
    public static void InfomationAlert(String title, String header, String content, Window owner){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.initOwner(owner);
        alert.show();
    }
    public static void ErrorAlert(String title, String header, String content, Window owner){
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.initOwner(owner);
        alert.show();
    }
}
