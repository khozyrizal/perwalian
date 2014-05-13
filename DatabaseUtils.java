/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adi.thinkoop.perwalianonline.utils;

import com.adi.thinkoop.perwalianonline.launcher.SplashLauncher;
import com.mysql.jdbc.Connection;
import java.io.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 *
 * @author adi nuralim adinuralim@gmail.com
 * @since 03-Mar-2013
 */
public class DatabaseUtils {

    private static String host = "jdbc:mysql://localhost:3306/";
    private static String db = "db_perwalian";
    private static String root = "root";
    private static Connection conn;

    public static Connection getConnection() {
        if (conn == null) {
            try {

                DriverManager.registerDriver(new com.mysql.jdbc.Driver());

                config();

                String tempHost = host + db;
                System.out.println("temp host : " + tempHost);


                conn = (Connection) DriverManager.getConnection(tempHost, root, "");
                System.out.println("entered connection");
            } catch (SQLException ex) {
                SplashLauncher.logger.log(Level.SEVERE, null, ex);
            }
        }
        return conn;
    }

    public static void config() {
        File f = new File(GlobalSetting.CONFIG_PATH);
        if (!f.exists()) {
            f.mkdirs();
        }
        f = new File(GlobalSetting.CONFIG_PATH + "perwalian-online.cfg");
        if (f.exists()) {
            f.delete();
        }
        if (!f.exists()) {
            try {
                f.createNewFile();
                FileWriter fstream = new FileWriter(f, true);
                try (BufferedWriter out = new BufferedWriter(fstream)) {
                    out.write("HOST " + host);
                    out.newLine();
                    out.write("DB " + db);
                    out.newLine();
                    out.write("ROOT " + root);
                    out.newLine();

                    out.flush();
                }
            } catch (IOException ex) {
                SplashLauncher.logger.log(Level.SEVERE, ex.getMessage());
            }
        }
        FileInputStream fstream = null;
        try {
            fstream = new FileInputStream(f);
            try (DataInputStream in = new DataInputStream(fstream)) {
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String strLine;
                // Read File Line By Line
                int line = 0;
                while ((strLine = br.readLine()) != null) {
                    String[] tmp = strLine.split(" ");
                    if (line == 0) {
                         host = tmp[1];
                    } else if (line == 1) {
                        db = tmp[1];
                    } else if (line == 2) {
                        root = tmp[1];
                    }
                    line++;
                }
            }
        } catch (IOException ex) {
            SplashLauncher.logger.log(Level.SEVERE, ex.getMessage());
        } finally {
            try {
                fstream.close();
            } catch (IOException ex) {
                SplashLauncher.logger.log(Level.SEVERE, ex.getMessage());
            }
        }
        System.out.println("host " + host);
        System.out.println("db " + db);
        System.out.println("root " + root);
    }
}
