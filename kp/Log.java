package kp;

import java.io.*;

public class Log
{
    private static final PrintStream printstream;
    
    static {
        printstream = new PrintStream(new FileOutputStream(FileDescriptor.out));
    }
    
    public static void i(final Object s) {
        Log.printstream.print("[KoreanPatch] ");
        Log.printstream.println(s);
    }
    
    public static void warn(final Object s) {
        Log.printstream.print("[KoreanPatch][WARNING] ");
        Log.printstream.println(s);
    }
    
    public static void error(final Object s) {
        Log.printstream.print("[KoreanPatch][ERROR] ");
        Log.printstream.println(s);
    }
}
