package kp;

import kp.keyboards.*;
import kp.input.*;
import java.util.*;
import java.io.*;

public class Config
{
    public static File configFile;
    public static boolean DELETE_JASO;
    public static final KeyLoader keyloader;
    
    static {
        Config.configFile = new File(InputMethod.getMinecraftInterface().getMinecraftDir(), "koreanpatch.cfg");
        Config.DELETE_JASO = true;
        keyloader = new KeyLoader();
        load();
    }
    
    public static void load() {
        FileInputStream fin = null;
        try {
            if (!Config.configFile.exists()) {
                Config.configFile.getParentFile().mkdirs();
                save();
            }
            else {
                fin = new FileInputStream(Config.configFile);
                final Properties p = new Properties();
                p.load(fin);
                Config.DELETE_JASO = p.getProperty("DELETE_JASO").equals("true");
                Config.keyloader.setKeyboard(Integer.parseInt(p.getProperty("KEYBOARD")));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            try {
                if (fin != null) {
                    fin.close();
                }
            }
            catch (IOException e2) {
                e2.printStackTrace();
            }
            return;
        }
        finally {
            try {
                if (fin != null) {
                    fin.close();
                }
            }
            catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        try {
            if (fin != null) {
                fin.close();
            }
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
    }
    
    public static void save() {
        FileWriter writer = null;
        try {
            if (!Config.configFile.getParentFile().exists()) {
                Config.configFile.getParentFile().mkdirs();
            }
            writer = new FileWriter(Config.configFile);
            final Properties p = new Properties();
            p.setProperty("DELETE_JASO", String.valueOf(Config.DELETE_JASO));
            p.setProperty("KEYBOARD", String.valueOf(Config.keyloader.getKeyboardArrayIndex()));
            p.store(writer, "Korean Patch Configuration File.");
        }
        catch (IOException e) {
            e.printStackTrace();
            try {
                if (writer != null) {
                    writer.close();
                }
            }
            catch (IOException e2) {
                e2.printStackTrace();
            }
            return;
        }
        finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            }
            catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        try {
            if (writer != null) {
                writer.close();
            }
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
    }
}
