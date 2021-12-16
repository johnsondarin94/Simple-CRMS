package util;

import java.io.*;

public class ReaderWriter {
    public static void writer() throws IOException {
        File loginAttempts = new File("Login Attempts.txt");
        FileWriter fw = new FileWriter(loginAttempts);

        PrintWriter pw = new PrintWriter(fw);

        pw.println("This is a test");
        pw.println("Can you see this?");
    }
}
