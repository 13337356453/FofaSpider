package org.manlu.classes;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.manlu.tools.IniTool;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.HashMap;

public class Requester {
    String url;
    HashMap<String, String> header;
    int timeout;
    String proxy;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HashMap<String, String> getHeader() {
        return header;
    }

    public void setHeader(HashMap<String, String> header) {
        this.header = header;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getProxy() {
        return proxy;
    }

    public void setProxy(String proxy) {
        this.proxy = proxy;
    }

    public Requester(String url, HashMap<String, String> header, int timeout, String proxy) {
        setUrl(url);
        setHeader(header);
        setTimeout(timeout);
        setProxy(proxy);
    }

    public Connection.Response get() {
        try {
            SslUtils.ignoreSsl();
            Proxy proxies = null;
            if (!proxy.equals("")) {
                proxies = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(getProxy().split(":")[0], Integer.parseInt(getProxy().split(":")[1])));
            }
            Connection connect = Jsoup.connect(url).proxy(proxies).timeout(timeout * 1000).ignoreContentType(true).maxBodySize(0);
            Connection conheader = connect.headers(header);
            return conheader.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        HashMap<String,String> header=new HashMap<>();
        String email="1571120423@qq.com";
        String api="daeaf3904d20b11437178e7ce6aaf9ca";
        String proxies=IniTool.getProxy();
        int timeout1= IniTool.getTimeout();
        Requester requester=new Requester("https://fofa.info/api/v1/search/all?email="+email+"&key="+api+"&qbase64=aXA9IjEwMy4zNS4xNjguMzgi",header,timeout1,proxies);
        Connection.Response response=requester.get();
        if (response.statusCode()==200){
            System.out.println(response.body());
            Gson gson=new Gson();
            JsonObject obj=gson.fromJson(response.body(),JsonObject.class);
            System.out.println(obj.get("error"));
        }
    }
}
