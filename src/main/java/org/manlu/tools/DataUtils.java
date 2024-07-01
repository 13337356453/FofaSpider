package org.manlu.tools;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DataUtils {
    File file;

    public DataUtils(File f) {
        this.file = f;
        if (!this.file.exists()){
            createFile();
        }
    }

    public boolean createFile() {
        try {
            if (!(this.file == null)) {
                return this.file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String readFile() {
        try {
            if (!(this.file == null)) {
                FileReader reader = new FileReader(this.file);
Long length=this.file.length();
                char[] bs = new char[length.intValue()];
                reader.read(bs);
                reader.close();
                return Cipher.decrypt(new String(bs).strip());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String[] readLines() {
        return this.readFile().split("\\r?\\n");
    }

    public String get(String key) {
        String[] lines = this.readLines();
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].split("=")[0].equals(key)) {
//                return lines[i].split("=")[1];
                return lines[i].substring(lines[i].indexOf("=")+1);
            }
        }
        return "";
    }

    public void put(String key, String data) {
        String[] lines = this.readLines();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].split("=")[0].equals(key)) {
                sb.append(key).append("=").append(data).append("\n");
            } else {
                sb.append(lines[i]).append("\n");
            }
        }
        try {
            FileWriter writer = new FileWriter(this.file,false);
            writer.write(Cipher.encrypt(sb.toString()));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void append(String key,String data){
        String[] lines=this.readLines();
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<lines.length;i++){
            if (lines[i].split("=")[0].equals(key)){
                put(key,data);
                return;
            }
            sb.append(lines[i]).append("\n");
        }
        sb.append(key).append("=").append(data);
        try{
            FileWriter writer = new FileWriter(this.file,false);
            writer.write(Cipher.encrypt(sb.toString()));
            writer.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DataUtils du = new DataUtils(new File("data.tmp"));
        System.out.println(du.readFile());
    }
}

