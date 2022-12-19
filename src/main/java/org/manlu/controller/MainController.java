package org.manlu.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.jsoup.Connection;
import org.manlu.ExportFrame;
import org.manlu.MainFrame;
import org.manlu.classes.*;
import org.manlu.tools.B64;
import org.manlu.tools.DataUtils;
import org.manlu.tools.IniTool;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

public class MainController implements Initializable {
    public static Thread api_thread;
    public static Thread spi_thread;
    public static Thread pager;
    public static String spi_kw;
    public static String cookie;
    public static String email;
    public static String key;
    public static String kw;
    public static List<Api_Data> api_data = new ArrayList<>();
    public static List<Data> datas = new ArrayList<>();
    public static int endpage = -1;
    public static int maxpage;
    public static int allnum;
    @FXML
    private TextField kw_js_field;

    @FXML
    private Button fo_cls_cookie_btn;

    @FXML
    private TextField kw_title_field;

    @FXML
    private TextField kw_ip_field;

    @FXML
    private TextField kw_host_field;

    @FXML
    private TableColumn<Api_Data, String> api_table_host_col;

    @FXML
    private TextField kw_server_field;

    @FXML
    private TextField kw_domain_field;

    @FXML
    private TextField kw_header_field;

    @FXML
    private Label fofa_label1;

    @FXML
    private TextField api_email_field;

    @FXML
    private Label kw_label_3;

    @FXML
    private Label api_nd;

    @FXML
    private Button kw_gene_btn;

    @FXML
    private TextField api_kw_field;

    @FXML
    private Button api_start_btn;

    @FXML
    private TableColumn<Api_Data, String> api_table_port_col;

    @FXML
    private TextField kw_fid_field;

    @FXML
    private TextField kw_icon_field;

    @FXML
    private TextField kw_body_field;

    @FXML
    private Label api_label1;

    @FXML
    private Button fo_test_btn;

    @FXML
    private Button api_ex_btn;

    @FXML
    private TextField kw_port_field;

    @FXML
    private Button fo_kw_cls_btn;

    @FXML
    private TextField kw_city_field;

    @FXML
    private TextField kw_statuscode_field;

    @FXML
    private Button kw_reset_btn;

    @FXML
    private TextField api_key_field;

    @FXML
    private TextField kw_app_field;

    @FXML
    private Button api_end_btn;

    @FXML
    private TextField kw_country_field;

    @FXML
    private Button api_save_data_btn;

    @FXML
    private Button api_cls_res_btn;

    @FXML
    private TextArea fo_cookie_field;

    @FXML
    private Button api_clear_kw_btn;

    @FXML
    private Label kw_label1;

    @FXML
    private TextField fo_kw_field;

    @FXML
    private Label kw_label2;

    @FXML
    private TextArea kw_kw_field;

    @FXML
    private Button fo_save_cookie_btn;

    @FXML
    private TableColumn<Api_Data, String> api_table_ip_col;

    @FXML
    private Button kw_copy_btn;

    @FXML
    private Button api_test_api_btn;

    @FXML
    private Label kw_label;

    @FXML
    private TextField kw_region_field;

    @FXML
    private TextArea fo_console;

    @FXML
    private TableView<Data> fo_result;

    @FXML
    private TableColumn<Data, String> fo_ip_col;

    @FXML
    private TableColumn<Data, String> fo_host_col;

    @FXML
    private TableColumn<Data, String> fo_port_col;

    @FXML
    private TableView<Api_Data> api_result;

    @FXML
    private Label fo_nd;

    @FXML
    private ProgressBar fo_progressbar;

    @FXML
    void fo_kw_cls(ActionEvent event) {
        fo_kw_field.setText("");
    }

    @FXML
    void fo_save_cookie(ActionEvent event) {
        String cookie = fo_cookie_field.getText().strip();
        if (!cookie.equals("")) {
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
            String data = date + "!" + cookie;
            boolean issaved = false;
            DataUtils du = new DataUtils(new File("data.tmp"));
            du.append("cookie", data);
            issaved = true;
            if (issaved) {
                Alerter.InfomationAlert("已保存", null, "已保存", MainFrame.stage);
            }
        } else {
            Alerter.ErrorAlert("错误", null, "请输入cookie", MainFrame.stage);
        }
    }

    @FXML
    void fo_test_btn(ActionEvent event) {
        String cookie = fo_cookie_field.getText().strip();
        if (cookie.equals("")) {
            Alerter.ErrorAlert("错误", null, "请输入cookie", MainFrame.stage);
        } else {
            boolean b = false;
            try {
                String url = "https://fofa.info/result?qbase64=" + B64.b64encode("1");
                HashMap<String, String> headers = new HashMap<>();
                headers.put("cookie", cookie);
                headers.put("host", "fofa.info");
                headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36");
                headers.put("Referer", "https://fofa.info/");
                headers.put("Connection", "keep-alive");
                Requester requester = new Requester(url, headers, IniTool.getTimeout(), IniTool.getProxy());
                Connection.Response response = requester.get();
                if (response.statusCode() == 200) b = true;
            } catch (Exception e) {
            }
            if (b) {
                Alerter.InfomationAlert("识别成功", null, "识别成功", MainFrame.stage);
                fo_console.appendText("cookie可用\n");
            } else {
                Alerter.ErrorAlert("出错", null, "无法识别的Cookie", MainFrame.stage);
            }
        }
    }

    @FXML
    void fo_cls_cookie(ActionEvent event) {
        fo_cookie_field.setText("");
    }

    void getEndPage() {
        MaxPager mp = new MaxPager();
        pager = new Thread(mp);
        pager.start();
        mp.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.startsWith("1")) {
                    fo_console.appendText(newValue.substring(1));
                } else if (newValue.startsWith("2")) {
                    try {
                        pager.stop();
                        TextInputDialog dialog = new TextInputDialog();
                        dialog.setTitle("fofa");
                        dialog.setHeaderText(MaxPager.msg);
                        dialog.setContentText("请输入终止页码：");
                        Optional<String> result = dialog.showAndWait();
                        if (result.isPresent()) {
                            if (!result.get().strip().equals("")) {
                                int a = Integer.parseInt(result.get().strip());
                                if (a < 0 || a > maxpage) {
                                    Alerter.ErrorAlert("错误", null, "终止页码输入错误。线程已终止，请重新启动!", MainFrame.stage);
                                } else {
                                    endpage = a;
                                    allnum=endpage*IniTool.getPageNum();
                                    spider();
                                }
                            } else {
                                Alerter.ErrorAlert("错误", null, "终止页码输入错误。线程已终止，请重新启动!", MainFrame.stage);
                            }
                        }
                    } catch (NumberFormatException e) {
                        Alerter.ErrorAlert("错误", null, "终止页码输入错误。线程已终止，请重新启动!", MainFrame.stage);
                    }
                }
            }
        });
    }

    void spider() {
        Spider spider = new Spider();
        spi_thread = new Thread(spider);
        spi_thread.start();

        spider.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.startsWith("0")) {
                    String ip = newValue.substring(1).split("!")[0];
                    String host = newValue.split("!")[1];
                    String port = newValue.split("!")[2];
//                    System.out.println(Double.parseDouble(newValue.split("!")[3]) / allnum);
                    fo_progressbar.setProgress(Double.parseDouble(newValue.split("!")[3]) / allnum);
                    fo_result.getItems().add(new Data(ip, host, port));
                    fo_nd.setText("当前数据量：" + fo_result.getItems().size());
                }else if (newValue.startsWith("1")){
                    fo_console.appendText(newValue.substring(1));
                }
            }
        });
    }

    @FXML
    void fo_start(ActionEvent event) {
        String kw = fo_kw_field.getText().strip();
        String cookie = fo_cookie_field.getText().strip();
        if (kw.equals("") || cookie.equals("")) {
            Alerter.ErrorAlert("错误", null, "请输入关键字，cookie", MainFrame.stage);
        } else {
            MainController.spi_kw = kw;
            MainController.cookie = cookie;
            fo_progressbar.setProgress(0);
            getEndPage();
        }
    }

    @FXML
    void fo_end_spi(ActionEvent event) {
        if (spi_thread != null && spi_thread.isAlive()) {
            spi_thread.stop();
        }
    }

    @FXML
    void fo_cls_res(ActionEvent event) {
        fo_result.getItems().clear();
        fo_nd.setText("当前数据量：0");
    }

    @FXML
    void fo_ex(ActionEvent event) throws Exception {
        if (fo_result.getItems().size() > 0) {
            datas = fo_result.getItems();
            Stage stage = new Stage();
            new ExportFrame().start(stage);
        }
    }


    @FXML
    void api_save_data(ActionEvent event) {
        String email = api_email_field.getText().strip();
        String key = api_key_field.getText().strip();
        if (!email.equals("") && !email.matches("^[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?$")) {
            Alerter.ErrorAlert("错误", null, "请输入正确的邮箱", MainFrame.stage);
            api_email_field.setText("");
        } else {
            boolean issaved = false;
            DataUtils du = new DataUtils(new File("data.tmp"));
            du.append("email", email);
            du.append("key", key);
            issaved = true;
            if (issaved) {
                Alerter.InfomationAlert("已保存", null, "已保存", MainFrame.stage);
            }
        }
    }

    @FXML
    void api_clear_kw(ActionEvent event) {
        api_kw_field.setText("");
    }

    @FXML
    void api_test_api(ActionEvent event) {
        String email = api_email_field.getText().strip();
        String key = api_key_field.getText().strip();
        if (!email.equals("") && !key.equals("")) {
            boolean b = false;
            try {
                String url = "https://fofa.info/api/v1/search/all?email=" + email + "&key=" + key + "&qbase64=aXA9IjEwMy4zNS4xNjguMzgi";
                Requester requester = new Requester(url, new HashMap<>(), IniTool.getTimeout(), IniTool.getProxy());
                Connection.Response response = requester.get();
                Gson gson = new Gson();
                JsonObject jo = gson.fromJson(response.body(), JsonObject.class);
                if (jo.get("error").toString().equals("false")) {
                    Alerter.InfomationAlert("成功", null, "识别成功", MainFrame.stage);
                    b = true;
                }
            } catch (Exception ignored) {
            }
            if (!b) {
                Alerter.ErrorAlert("出错", null, "无法识别的邮箱或APIKEY", MainFrame.stage);

            }
        } else {
            Alerter.ErrorAlert("出错", null, "请输入邮箱和APIKEY", MainFrame.stage);

        }
    }

    @FXML
    void api_start(ActionEvent event) {
//        api_nd.setText("当前数据量：0");
        String email = api_email_field.getText().strip();
        String key = api_key_field.getText().strip();
        String kw = api_kw_field.getText().strip();
        if (!email.equals("") && !key.equals("") && !kw.equals("")) {
            MainController.email = email;
            MainController.key = key;
            MainController.kw = kw;
//            api_result.getItems().clear();
            APISpider apiSpider = new APISpider();
            api_thread = new Thread(apiSpider);
            api_thread.start();
            int n=0;
            apiSpider.valueProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    String ip = newValue.toString().split("!")[0].replace("\"", "");
                    String host = newValue.toString().split("!")[1].replace("\"", "");
                    String port = newValue.toString().split("!")[2].replace("\"", "");
                    api_result.getItems().add(new Api_Data(ip, host, port));
                    api_nd.setText("当前数据量：" + api_result.getItems().size());
                }
            });
        } else {
            Alerter.ErrorAlert("出错", null, "请输入邮箱，APIKEY，关键词", MainFrame.stage);
        }
    }

    @FXML
    void api_end(ActionEvent event) {
        if (api_thread != null && api_thread.isAlive()) {
            api_thread.stop();
        }
    }

    @FXML
    void api_cls_res(ActionEvent event) {
        api_result.getItems().clear();
        api_nd.setText("当前数据量：0");
    }

    @FXML
    void api_ex(ActionEvent event) throws Exception {
        if (api_result.getItems().size() > 0) {
            api_data = api_result.getItems();
            Stage stage = new Stage();
            new ExportFrame().start(stage);
        }
    }

    @FXML
    void gene_kw(ActionEvent event) {
        String app = kw_app_field.getText().strip();
        String port = kw_port_field.getText().strip();
        String header = kw_header_field.getText().strip();
        String country = "CN";
        if ((!kw_country_field.getText().strip().equals("")) && (!kw_country_field.getText().strip().equals("*")))
            country = kw_country_field.getText().strip();
        else if (kw_country_field.getText().strip().equals("*")) country = "";
        String region = kw_region_field.getText().strip();
        String title = kw_title_field.getText().strip();
        String body = kw_body_field.getText().strip();
        String fid = kw_fid_field.getText().strip();
        String domain = kw_domain_field.getText().strip();
        String status_code = "200";
        if ((!kw_statuscode_field.getText().equals("")) && (!kw_statuscode_field.getText().equals("*")))
            status_code = kw_statuscode_field.getText();
        else if (kw_statuscode_field.getText().strip().equals("*")) status_code = "";
        String ip = kw_ip_field.getText().strip();
        String host = kw_host_field.getText().strip();
        String server = kw_server_field.getText().strip();
        String icon = kw_icon_field.getText().strip();
        String js = kw_js_field.getText().strip();
        String city = kw_city_field.getText().strip();
        StringBuilder builder = new StringBuilder();
        if (!app.equals("")) builder.append("app=\"").append(app).append("\" && ");
        if (!port.equals("")) builder.append("port=\"").append(port).append("\" && ");
        if (!header.equals("")) builder.append("header=\"").append(header).append("\" && ");
        if (!country.equals("")) builder.append("country=\"").append(country).append("\" && ");
        if (!region.equals("")) builder.append("region=\"").append(region).append("\" && ");
        if (!title.equals("")) builder.append("title=\"").append(title).append("\" && ");
        if (!body.equals("")) builder.append("body=\"").append(body).append("\" && ");
        if (!fid.equals("")) builder.append("fid=\"").append(fid).append("\" && ");
        if (!fid.equals("")) builder.append("fid=\"").append(fid).append("\" && ");
        if (!domain.equals("")) builder.append("domain=\"").append(domain).append("\" && ");
        if (!status_code.equals("")) builder.append("status_code=\"").append(status_code).append("\" && ");
        if (!ip.equals("")) builder.append("ip=\"").append(ip).append("\" && ");
        if (!host.equals("")) builder.append("host=\"").append(host).append("\" && ");
        if (!server.equals("")) builder.append("server=\"").append(server).append("\" && ");
        if (!icon.equals("")) builder.append("icon=\"").append(icon).append("\" && ");
        if (!js.equals("")) builder.append("js=\"").append(js).append("\" && ");
        if (!city.equals("")) builder.append("city=\"").append(city).append("\" && ");
        String result = builder.toString().substring(0, builder.toString().length() - 3).strip();
        System.out.println(result);
        kw_kw_field.setText(result);
    }


    @FXML
    void copy_kw(ActionEvent event) {
        String kw = kw_kw_field.getText().strip();
        if (!kw.equals("")) {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            Transferable trans = new StringSelection(kw);
            clipboard.setContents(trans, null);
        }
    }

    @FXML
    void reset_kw(ActionEvent event) {
        kw_app_field.setText("");
        kw_port_field.setText("");
        kw_header_field.setText("");
        kw_country_field.setText("");
        kw_region_field.setText("");
        kw_title_field.setText("");
        kw_body_field.setText("");
        kw_fid_field.setText("");
        kw_domain_field.setText("");
        kw_statuscode_field.setText("");
        kw_ip_field.setText("");
        kw_host_field.setText("");
        kw_server_field.setText("");
        kw_icon_field.setText("");
        kw_js_field.setText("");
        kw_city_field.setText("");
        kw_kw_field.setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 初始化数据
        loadData();
        // 初始化表格
        loadApiTable();
    }

    private void loadData() {
        DataUtils du = new DataUtils(new File("data.tmp"));
        String email = du.get("email");
        String key = du.get("key");
        api_email_field.setText(email);
        api_key_field.setText(key);
        String c = du.get("cookie");
        String date = c.split("!")[0];

        if (date.equals(new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis())))) {
            fo_cookie_field.setText(c.split("!")[1]);
        } else {
            du.append("cookie", "");
        }
    }

    void loadApiTable() {
        api_table_ip_col.setCellValueFactory(new PropertyValueFactory<>("ip"));
        api_table_host_col.setCellValueFactory(new PropertyValueFactory<>("host"));
        api_table_port_col.setCellValueFactory(new PropertyValueFactory<>("port"));
        api_result.setRowFactory(tv -> {
            TableRow<Api_Data> row = new TableRow<Api_Data>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Api_Data data = row.getItem();
                    Desktop desktop = Desktop.getDesktop();
                    if (desktop.isSupported(Desktop.Action.BROWSE)) {
                        try {
                            String url = data.getHost();
                            if (url.substring(0, 4).equals("http")) desktop.browse(URI.create(data.getHost()));
                            else desktop.browse(URI.create("http://" + url));
                        } catch (IOException e) {
                        }
                    }
                }
            });
            return row;
        });
        fo_ip_col.setCellValueFactory(new PropertyValueFactory<>("ip"));
        fo_host_col.setCellValueFactory(new PropertyValueFactory<>("host"));
        fo_port_col.setCellValueFactory(new PropertyValueFactory<>("port"));
        fo_result.setRowFactory(tv -> {
            TableRow<Data> row = new TableRow<Data>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Data data = row.getItem();
                    Desktop desktop = Desktop.getDesktop();
                    if (desktop.isSupported(Desktop.Action.BROWSE)) {
                        try {
                            String url = data.getHost();
                            if (url.substring(0, 4).equals("http")) desktop.browse(URI.create(data.getHost()));
                            else desktop.browse(URI.create("http://" + url));
                        } catch (IOException e) {
                        }
                    }
                }
            });
            return row;
        });
    }
}
