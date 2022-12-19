package org.manlu.tools;


import org.ini4j.Ini;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;

public class IniUtils {
    Wini ini;
    File file;

    public IniUtils(File f) {
        try {
            this.file = f;
            this.ini = new Wini(this.file);
        } catch (IOException e) {
            e.printStackTrace();
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

    public String get(String section, String key) {
        if (this.ini != null) {
            Ini.Section s = ini.get(section);
            return s.get(key);
        }
        return "";
    }

    public void put(String section, String key, String value) {
        try {
            if (this.ini != null) {
                this.ini.put(section, key, value);
                this.ini.store();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        IniUtils ini = new IniUtils(new File("FoSpi.ini"));
//        String ip=ini.get("proxy","ip");
        System.out.println(ini.get("proxy", "ip"));
    }
}
