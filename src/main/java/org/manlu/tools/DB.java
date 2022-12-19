package org.manlu.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {
    String host, port, uname, pwd, dburl, db;
    String dbClassName = "com.mysql.cj.jdbc.Driver";

    public DB(String host, String port, String uname, String pwd, String db) {
        this.host = host;
        this.port = port;
        this.uname = uname;
        this.pwd = pwd;
        this.db = db;
        this.dburl = "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.db + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(dbClassName);
        Connection c=null;
        c= DriverManager.getConnection(dburl,uname,pwd);
        if (!(c==null)){
            return c;
        }
        return null;
    }
    public String test(){
        try{
            Class.forName(dbClassName);
            Connection c=null;
            c= DriverManager.getConnection(dburl,uname,pwd);
            if (!(c==null)){
                c.close();
                return "ok";
            }
        }catch (ClassNotFoundException e){return null;}
        catch (SQLException e){
            System.out.println(e.getMessage());
            return e.getMessage();
        }
        return null;
    }
   public  String insertData(String tablename,String[] data)  {
        if (!test().equals("ok")){
            return "0"+test();
        }
        String sql="create table if not exists "+tablename+"(id int auto_increment,ip varchar(100),host varchar(100),port varchar(100),primary key(id))charset=utf8;";
        try {
            Connection c = getConnection();
            Statement st = c.createStatement();
            if (0 != st.executeUpdate(sql)) {
                st.close();
                c.close();
                return "0无法创建数据表";
            }
            StringBuilder sb = new StringBuilder();
            sb.append("insert into ").append(tablename).append("(");
            if (data[0] != null) sb.append("ip,");
            if (data[1] != null) sb.append("host,");
            if (data[2] != null) sb.append("port,");
            sb.deleteCharAt(sb.length()-1);
            sb.append(")values(");
            if (data[0] != null) sb.append("\"").append(data[0]).append("\"").append(",");
            if (data[1] != null) sb.append("\"").append(data[1]).append("\"").append(",");
            if (data[2] != null) sb.append("\"").append(data[2]).append("\"").append(",");
            sb.deleteCharAt(sb.length()-1);
            sb.append(");");
            int res=st.executeUpdate(sb.toString());
            st.close();
            c.close();
            if (res==1){
                return "1";
            }
            return "1";
        }catch (Exception e){
            return "0"+e.getMessage();
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DB db=new DB("localhost","3306","root","root","urls");
        String[] d={"1","11","111"};
db.insertData("fofadata",d);

    }
}