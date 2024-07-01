package org.manlu.classes;

import javafx.concurrent.Task;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.manlu.controller.MainController;
import org.manlu.tools.B64;
import org.manlu.tools.IniTool;

import java.util.HashMap;

public class Spider extends Task<String> {


    @Override
    protected String call() throws Exception {
        String kw = MainController.spi_kw;
        String cookie = MainController.cookie;
        int endpage = MainController.endpage;
        String url = "https://fofa.info/result?qbase64=" + B64.b64encode(kw) + "&page_size=" + IniTool.getPageNum() + "&page=";
        HashMap<String, String> header = new HashMap<>();
        header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36");
        header.put("host", "fofa.info");
        header.put("Referer", "https://fofa.info/");
        header.put("Connection", "keep-alive");
        header.put("cookie", cookie);
        int timewait = 1;
        int n = 0;
        if (IniTool.getTimeWait() != -1) timewait = IniTool.getTimeWait();
        int beginpage=1;
        for (int i = beginpage; i < endpage + 1; i++) {
            String u = url + i;
            try {
                Requester requester = new Requester(u, header, IniTool.getTimeout(), IniTool.getProxy());
                Connection.Response response = requester.get();
                Document document = response.parse();
//                Element ipsele=document.getElementsByClass("result-item").get(0).getElementsByClass("innerContentLeft").get(0).getElementsByTag("p").get(1).getElementsByClass("jumpA").get(0);
//                Elements reselels = document.getElementsByClass("result-item");
                Elements reselels = document.getElementsByClass("el-checkbox-group").get(1).children();

                for (Element resele : reselels) {
//                    System.out.println(resele);
                    String ip = resele.getElementsByTag("p").get(1).getElementsByClass("hsxa-jump-a").get(0).text();
//                    String ip = resele.getElementsByClass("innerContentLeft").get(0).getElementsByTag("p").get(1).getElementsByClass("jumpA").get(0).text();
//                        String host = resele.getElementsByClass("aSpan").get(0).getElementsByTag("a").get(0).text().replace("https://", "").replace("http://", "");
                    String host = resele.getElementsByClass("hsxa-host").get(0).text().replace("https://", "").replace("http://", "");
                    String port = "";
                    if (host.contains(":")) {
                        port = host.split(":")[1];
                    } else {
                        port = "80";
                    }
                    n++;
                    updateValue("0" + ip + "!" + host + "!" + port + "!" + n);
                    Thread.sleep(30);
                }
            } catch (Exception e) {
                e.printStackTrace();
                updateValue("1" + e.getMessage() + "\n");
            }
            Thread.sleep(timewait * 1000);
        }


        return null;
    }

    @Override
    protected void updateValue(String value) {
        if (!(value == null)) super.updateValue(value);
    }
}
