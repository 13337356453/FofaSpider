package org.manlu.classes;

import javafx.beans.property.SimpleStringProperty;

public class Api_Data {
    private SimpleStringProperty ip;
    private SimpleStringProperty host;
    private SimpleStringProperty port;
    public Api_Data(String ip,String host,String  port){
        this.ip=new SimpleStringProperty(ip);
        this.host=new SimpleStringProperty(host);
        this.port=new SimpleStringProperty(port);

    }

    public String getIp() {
        return ip.get();
    }

    public SimpleStringProperty ipProperty() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip.set(ip);
    }

    public String getHost() {
        return host.get();
    }

    public SimpleStringProperty hostProperty() {
        return host;
    }

    public void setHost(String host) {
        this.host.set(host);
    }

    public String getPort() {
        return port.get();
    }

    public SimpleStringProperty portProperty() {
        return port;
    }

    public void setPort(String port) {
        this.port.set(port);
    }
}
