package org.manlu;

import javafx.application.Application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    static void checkini() {
        try {
            File file = new File("FoSpi.ini");
            if (!file.exists()) {
                file.createNewFile();
                FileWriter writer=new FileWriter(file);
                writer.write("[basic]\n#url=fofa.so\nurl = fofa.info\n#argument=Authorization\nargument = cookie\ntimeout = 10\ntimewait = 1\npagenum = 20\n[proxy]\nip =\nport = ");
                writer.flush();
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        checkini();
        Application.launch(MainFrame.class, args);
    }
}
