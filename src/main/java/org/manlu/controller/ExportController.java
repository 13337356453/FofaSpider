package org.manlu.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.manlu.ExportFrame;
import org.manlu.classes.*;
import org.manlu.tools.DB;
import org.manlu.tools.DataUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ExportController implements Initializable {
    public static int ns;
    public static Thread exporter;
    public static int[] nds;
    public static int flg = 0;
    public static List data;
    public static String e_host;
    public static String e_port;
    public static String e_uname;
    public static String e_pwd;
    public static String e_db;
    public static String e_table;
    public static boolean no_repeat=false;

    @FXML
    private Button db_cls_btn;

    @FXML
    private CheckBox port_box;

    @FXML
    private TextField host_field;

    @FXML
    private ComboBox<String> datasource_box;

    @FXML
    private Button db_save_btn;

    @FXML
    private Button ex_path_btn;

    @FXML
    private TextField ex_path_field;

    @FXML
    private TextField port_field;

    @FXML
    private Button db_ex_btn;

    @FXML
    private TextField pwd_field;

    @FXML
    private CheckBox host_box;

    @FXML
    private TextField table_field;

    @FXML
    private CheckBox ip_box;

    @FXML
    private Button db_test_btn;

    @FXML
    private ProgressBar progressbar;
    @FXML
    private Button path_cls_btn;

    @FXML
    private TextField uname_field;

    @FXML
    private Button path_btn;

    @FXML
    private TextField db_field;

    @FXML
    private RadioButton no_repeat_btn;

    @FXML
    private RadioButton http_btn;

    @FXML
    private RadioButton no_no_repeat_btn;

    @FXML
    void path_up(Event event) {

    }

    @FXML
    void choice_path(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择导出位置");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("文本文件", "*.txt"), new FileChooser.ExtensionFilter("全部文件", "*.*"));
        File file = fileChooser.showOpenDialog(ExportFrame.stage);
        if (!(file == null)) {
            ex_path_field.setText(file.toString());
        }
    }

    @FXML
    void ex_path(ActionEvent event) throws IOException {
        String source = datasource_box.getValue();
        int[] needs = null;
        boolean flag = false;
        if (!(source == null)) {
            if (!ex_path_field.getText().strip().equals("")) {
                needs = getDataNeed();
                if (needs != null) {
                    flag = true;
                }
            } else {
                Alerter.ErrorAlert("错误", null, "请选择导出路径", ExportFrame.stage);
            }
        } else {
            Alerter.ErrorAlert("错误", null, "请选择数据源", ExportFrame.stage);

        }
        if (flag) {
//            System.out.println(no_repeat_btn.isSelected());
            List data;
            if (datasource_box.getValue().strip().split("\\.")[0].equals("2")) {
                data = MainController.api_data;
            } else {
                data = MainController.datas;
            }
            if (data.size() > 0) {
                File file = new File(ex_path_field.getText());
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileWriter writer = new FileWriter(file, true);
                List<String> ips=new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
//                    System.out.println("HOST=" + data.get(i).getHost() + ",IP=" + data.get(i).getIp() + ",PORT=" + data.get(i).getPort());
                    Datai datai;
                    if (data.get(i) instanceof Data){
                        Data tmp= (Data) data.get(i);
                        datai=new Datai(tmp.getIp(),tmp.getHost(),tmp.getPort());
                    }else{
                        Api_Data tmp= (Api_Data) data.get(i);
                        datai=new Datai(tmp.getIp(),tmp.getHost(),tmp.getPort());
                    }
                    if (no_repeat_btn.isSelected()){
                        if (ips.contains(datai.getIp())){
                            continue;
                        }
                        ips.add(datai.getIp());
                    }
                    StringBuilder sb = new StringBuilder();
                    if (needs[0] == 1) sb.append(datai.getIp()).append("\t");
                    if (needs[1] == 1) {
                        if (http_btn.isSelected()) {
                            sb.append("http://").append(datai.getHost()).append("\t");
                        } else{
                            sb.append(datai.getHost()).append("\t");
                        }
                    }if (needs[2] == 1) sb.append(datai.getPort()).append("\t");
                    String res = sb.toString().strip() + "\n";
                    System.out.println(res);
                    writer.write(res);
                }
                writer.close();
                Alerter.InfomationAlert("已保存", null, "已保存", ExportFrame.stage);
            }
        }
    }



    @FXML
    void cls_path(ActionEvent event) {
        ex_path_field.setText("");
    }

    @FXML
    void sql_up(Event event) {

    }

    @FXML
    void db_save(ActionEvent event) {
        String host;
        if (host_field.getText().strip().equals("")) host = "127.0.0.1";
        else host = host_field.getText().strip();
        String port;
        if (port_field.getText().strip().equals("")) port = "3306";
        else port = port_field.getText().strip();
        String uname;
        if (uname_field.getText().strip().equals("")) uname = "root";
        else uname = uname_field.getText().strip();
        String pwd = pwd_field.getText().strip();
        String db = db_field.getText().strip();
        if (!(pwd.equals("") || db.equals(""))) {
            DataUtils du = new DataUtils(new File("data.tmp"));
            du.append("db_host", host);
            du.append("db_port", port);
            du.append("db_uname", uname);
            du.append("db_pwd", pwd);
            du.append("db_db", db);
        } else {
            Alerter.ErrorAlert("错误", null, "请输入数据库连接信息", ExportFrame.stage);
        }
    }

    @FXML
    void db_ex(ActionEvent event) {
        String host;
        if (host_field.getText().strip().equals("")) host = "127.0.0.1";
        else host = host_field.getText().strip();
        String port;
        if (port_field.getText().strip().equals("")) port = "3306";
        else port = port_field.getText().strip();
        String uname;
        if (uname_field.getText().strip().equals("")) uname = "root";
        else uname = uname_field.getText().strip();
        String pwd = pwd_field.getText().strip();
        String db = db_field.getText().strip();
        String table;
        if (table_field.getText().strip().equals("")) table = "fofadata";
        else table = table_field.getText().strip();
        String source = datasource_box.getValue();
        int[] needs = null;
        boolean flag = false;
        if (source != null) {
            if (!(pwd.equals("") || db.equals(""))) {
                needs = getDataNeed();
                if (needs != null) {
                    flag = true;
                }
            } else {
                Alerter.ErrorAlert("错误", null, "请输入数据库相关信息", ExportFrame.stage);
            }
        } else {
            Alerter.ErrorAlert("错误", null, "请选择数据源", ExportFrame.stage);

        }
        if (flag) {
            boolean issaved = false;
            int n = 0;
            e_host = host;
            e_port = port;
            e_uname = uname;
            e_pwd = pwd;
            e_db = db;
            e_table = table;
            progressbar.setProgress(0);
            if (datasource_box.getValue().strip().split("\\.")[0].equals("2")) {
                data = MainController.api_data;
                flg=2;
            }else{
                data= MainController.datas;
                flg=1;
            }
            if (data.size()>0) {
                DBSaver saver = new DBSaver();
                nds = needs;
                no_repeat=no_no_repeat_btn.isSelected();
                exporter = new Thread(saver);
                exporter.start();
                saver.valueProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        if (newValue.startsWith("1")) {
                            ErrorAlert(newValue.substring(1));
                        } else if (newValue.startsWith("0")) {
                            progressbar.setProgress(Double.parseDouble(newValue.substring(1)) / data.size());
                        }
                    }
                });
            }
            if (issaved) {
                Alerter.InfomationAlert("已保存", null, "插入了" + n + "条数据", ExportFrame.stage);
            }
        }
    }

    @FXML
    void db_cls(ActionEvent event) {
        host_field.setText("");
        port_field.setText("");
        uname_field.setText("");
        pwd_field.setText("");
        db_field.setText("");
        table_field.setText("");
    }

    @FXML
    void db_test(ActionEvent event) {
        String host = host_field.getText().strip();
        String port = port_field.getText().strip();
        String uname = uname_field.getText().strip();
        String pwd = pwd_field.getText().strip();
        String dbn = db_field.getText().strip();
        DB db = new DB(host, port, uname, pwd, dbn);
        String s = db.test();
        Alerter.InfomationAlert(null, null, s, ExportFrame.stage);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> list = FXCollections.observableArrayList("1.从常规爬取中导出", "2.从API爬取中导出");
        datasource_box.setItems(list);

        loadData();
    }

    int[] getDataNeed() {
        if (ip_box.isSelected() || host_box.isSelected() || port_box.isSelected()) {
            int[] result = new int[3];
            if (ip_box.isSelected()) {
                result[0] = 1;
            }
            if (host_box.isSelected()) {
                result[1] = 1;
            }
            if (port_box.isSelected()) {
                result[2] = 1;
            }
            return result;
        } else {
            Alerter.ErrorAlert("错误", null, "请选择导出数据", ExportFrame.stage);
            return null;
        }
    }

    void loadData() {
        DataUtils du = new DataUtils(new File("data.tmp"));
        String host = du.get("db_host");
        String port = du.get("db_port");
        String uname = du.get("db_uname");
        String pwd = du.get("db_pwd");
        String db = du.get("db_db");
        host_field.setText(host);
        port_field.setText(port);
        uname_field.setText(uname);
        pwd_field.setText(pwd);
        db_field.setText(db);
    }

    void ErrorAlert(String msg) {
        Alerter.ErrorAlert("错误", null, msg, ExportFrame.stage);
    }
}


