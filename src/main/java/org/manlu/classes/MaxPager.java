package org.manlu.classes;

import javafx.concurrent.Task;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.manlu.controller.MainController;
import org.manlu.tools.B64;
import org.manlu.tools.IniTool;

import java.util.HashMap;
import java.util.Objects;

public class MaxPager extends Task<String> {
    public static String msg;
    @Override
    protected String call() {
        String kw = MainController.spi_kw;
        String cookie = MainController.cookie;
        String url = "https://fofa.info/result?qbase64=" + B64.b64encode(kw) + "&page_size=" + IniTool.getPageNum();
        HashMap<String, String> header = new HashMap<>();
        header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36");
        header.put("host", "fofa.info");
        header.put("Referer", "https://fofa.info/");
        header.put("Connection", "keep-alive");
        header.put("cookie", cookie);
        try {
            Requester requester = new Requester(url, header, IniTool.getTimeout(), IniTool.getProxy());
            Connection.Response response = requester.get();
            Document document = response.parse();
//            Elements elements1 = document.getElementsByClass("pSpanColor");
            Elements elements1 = document.getElementsByClass("hsxa-highlight-color");

            String num = Objects.requireNonNull(elements1.first()).text().replace(",", "");
            System.out.println(num);
            Elements elements2 = document.getElementsByClass("number");
            String pages = Objects.requireNonNull(elements2.last()).text();
            System.out.println(pages);
            updateValue("1识别成功\n");
            Thread.sleep(200);
            updateValue("1共有" + num + "条数据，共" + pages + "页\n");
            MainController.maxpage=Integer.parseInt(pages);
//            MainController.allnum=Integer.parseInt(num);
            msg="共有" + num + "条数据，共" + pages + "页";
            return "2";
        } catch (Exception e) {
            e.printStackTrace();
            updateValue("1Time out\n");
        }
        return null;
    }


    @Override
    protected void updateValue(String value) {
        if (!(value == null)) super.updateValue(value);
    }
}
