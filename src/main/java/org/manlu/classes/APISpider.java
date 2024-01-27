package org.manlu.classes;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.concurrent.Task;
import org.jsoup.Connection;
import org.manlu.controller.MainController;
import org.manlu.tools.B64;
import org.manlu.tools.IniTool;

import java.util.HashMap;

public class APISpider extends Task<String> {

    @Override
    protected String call() throws Exception {
        String email = MainController.email;
        String key = MainController.key;
        String kw = MainController.kw;
        String url = "https://fofa.info/api/v1/search/all?email=" + email + "&key=" + key + "&qbase64=" + B64.b64encode(kw);
        Requester requester = new Requester(url, new HashMap<>(), IniTool.getTimeout(), IniTool.getProxy());
        Connection.Response response = requester.get();
        if (response.statusCode() == 200) {
            Gson gson = new Gson();
            JsonObject jo = gson.fromJson(response.body(), JsonObject.class);
            JsonArray results = jo.getAsJsonArray("results");
            for (int i = 0; i < results.size(); i++) {
                JsonArray tmp = results.get(i).getAsJsonArray();
                String host = tmp.get(0).toString();
                String ip = tmp.get(1).toString();
                String port = tmp.get(2).toString();
                updateValue(ip + "!" + host + "!" + port);
                Thread.sleep(35); // 一定要有，否则会少数据
            }
        }
        return null;
    }


    @Override
    protected void updateValue(String value) {
        if (!(value == null)) super.updateValue(value);
    }
}
