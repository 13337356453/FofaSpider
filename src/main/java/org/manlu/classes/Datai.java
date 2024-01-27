package org.manlu.classes;

public class Datai {
    public static String ip;
    public static String host;
    public static String port;
    public Datai(String i, String h, String p) {
        ip=i;host=h;port=p;
    }
    public String getIp(){return ip;}
    public String getHost(){return host;}
    public String getPort(){return port;}
}
