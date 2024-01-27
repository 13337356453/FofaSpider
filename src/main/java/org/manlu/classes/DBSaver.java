package org.manlu.classes;

import javafx.concurrent.Task;
import org.manlu.controller.ExportController;
import org.manlu.tools.DB;

import java.sql.Connection;
import java.sql.Statement;
import java.util.*;

public class DBSaver extends Task<String> {


    @Override
    protected String call() throws Exception {
        int flg = ExportController.flg;
        int[] needs = ExportController.nds;
        String host = ExportController.e_host;
        String port = ExportController.e_port;
        String uname = ExportController.e_uname;
        String pwd = ExportController.e_pwd;
        String db = ExportController.e_db;
        String table = ExportController.e_table;
        DB dbb = new DB(host, port, uname, pwd, db);
        String dbbt = dbb.test();
        if (!dbbt.equals("ok")) {
            updateValue("1" + dbbt);
            return null;
        }
        String sql1 = "create table if not exists " + table + "(id int auto_increment,ip varchar(100),host varchar(100),port varchar(100),primary key(id))charset=utf8;";
        Connection c = dbb.getConnection();
        Statement st = c.createStatement();
        if (0 != st.executeUpdate(sql1)) {
            st.close();
            c.close();
            updateValue("1无法创建数据表");
            return null;
        }
        StringBuilder sb = new StringBuilder("insert into " + table + " (");
        if (needs[0] == 1) sb.append("ip,");
        if (needs[1] == 1) sb.append("host,");
        if (needs[2] == 1) sb.append("port,");
        sb = sb.deleteCharAt(sb.length() - 1);
        sb.append(") values ");
        List data = ExportController.data;
        int gs = 0;
        if (data.size() % 100 == 0) gs = data.size() / 100;
        else gs = (data.size() / 100) + 1;
        List<String> ips=new ArrayList<>();
        for (int i = 0; i < gs; i++) {
            StringBuilder sql2 = new StringBuilder(sb);
            for (int j = i * 100; j < 100 * (i + 1) && j < data.size(); j++) {
                Datai datai;
                String ip="";
                if (flg == 1) {
                    Data tmp = (Data) data.get(j);
                    datai = new Datai(tmp.getIp(), tmp.getHost(), tmp.getPort());
                    ip=tmp.getIp();
                } else {
                    Api_Data tmp = (Api_Data) data.get(j);
                    datai = new Datai(tmp.getIp(), tmp.getHost(), tmp.getPort());
                    ip=tmp.getIp();
                }
                if (ExportController.no_repeat) {
                    if (ips.contains(ip)) {
                        continue;
                    }
                    ips.add(ip);
                }
                sql2.append("(");
                if (needs[0] == 1) sql2.append("'").append(datai.getIp()).append("'").append(",");
                if (needs[1] == 1) sql2.append("'").append(datai.getHost()).append("'").append(",");
                if (needs[2] == 1) sql2.append("'").append(datai.getPort()).append("'").append(",");
                sql2 = sql2.deleteCharAt(sql2.length() - 1).append("),");
            }
            sql2 = sql2.deleteCharAt(sql2.length() - 1);
//            System.out.println(sql2);
            st.executeUpdate(sql2.toString());
            updateValue("0" + (i + 1) * 100);
        }
        return null;
    }

    @Override
    protected void updateValue(String value) {
        if (value != null) super.updateValue(value);
    }
}
