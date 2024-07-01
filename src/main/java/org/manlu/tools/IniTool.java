package org.manlu.tools;

import java.io.File;

public class IniTool {
    static File file=new File("FoSpi.ini");
    public static int getTimeout(){
        if (!file.exists())
            return -1;
        IniUtils ini=new IniUtils(file);
        String i=ini.get("basic","timeout").strip();
        if (i.equals(""))return -1;
        try{
            return Integer.parseInt(i);
        }catch (NumberFormatException e) {return -1;}
    }
    public static String getProxy(){
        if (!file.exists())
            return "";
        IniUtils ini=new IniUtils(file);
        String ip=ini.get("proxy","ip").strip();
        String port=ini.get("proxy","port").strip();
        if (ip.equals("") || port.equals(""))return "";
        if (!ip.matches("^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$"))return "";
        int p=-1;
        try{
            p=Integer.parseInt(port);
        }catch (NumberFormatException e){return "";}
        if (p<0||p>65535)return "";
        return ip+":"+port;
    }
    public static int getTimeWait(){
        if (!file.exists())
            return -1;
        IniUtils ini=new IniUtils(file);
        String i=ini.get("basic","timewait").strip();
        if (i.equals(""))return -1;
        try{
            return Integer.parseInt(i);
        }catch (NumberFormatException e) {return -1;}
    }
    public static int getPageNum(){
        if (!file.exists())
            return -1;
        IniUtils ini=new IniUtils(file);
        String i=ini.get("basic","pagenum").strip();
        if (i.equals(""))return -1;
        try{
            return Integer.parseInt(i);
        }catch (NumberFormatException e) {return -1;}
    }
    public static void main(String[] args) {
        System.out.println(getTimeout());
        System.out.println(getProxy());
        System.out.println(getTimeWait());
        System.out.println(getPageNum());
    }
}
