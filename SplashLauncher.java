/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adi.thinkoop.perwalianonline.launcher;

import com.adi.thinkoop.perwalianonline.utils.DataHolder;
import com.adi.thinkoop.perwalianonline.utils.DatabaseUtils;
import com.adi.thinkoop.perwalianonline.utils.GlobalSetting;
import com.adi.thinkoop.perwalianonline.view.MainFrame;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/*
 * @author adi nuralim adinuralim@gmail.com @since 03-Mar-2013
 * 
*/
public class SplashLauncher {

    public static final String APP_NAME = "perwalian_online.";
    public static final Logger logger = Logger.getLogger(APP_NAME);
    private final SplashScreen splash;
    private static final int X = 20;
    private static final int W = 200;
    private static final int TEXT_H = 10;
    private static final int BAR_W = 120;
    private static final int BAR_H = 5;
    private static final int NUM_BUBBLES = 10;
    private int textY = 0;
    private int barY = 0;
    private int barPos = 0;
    private Graphics2D graph;

    static {
        try {
            boolean append = true;
            String now = new SimpleDateFormat("yyyyMMdd").format(new Date());
            String filename = GlobalSetting.LOG_PATH + APP_NAME + now + ".log";
            File f;

            f = new File(GlobalSetting.LOG_PATH);
            if (!f.exists()) {
                f.mkdirs();
            }

            FileHandler fh = new FileHandler(filename, append);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "{0}", e);
        }
    }

    /**
     * Constructor of this class, initialize required object
     */
    public SplashLauncher() {
        splash = SplashScreen.getSplashScreen();
        if (null == splash) {
            return;
        }

        // compute base positions for text and progress bar
        Dimension splashSize = splash.getSize();
        textY = splashSize.height - 50;
        barY = splashSize.height - 30;
        graph = splash.createGraphics();
        drawSplashMessage("Initializing");
    }

    /**
     * Close splash
     */
    public void closeSplash() {
        if (null != splash) {
            splash.close();
        }
    }

    /**
     * Draw splash progress bar
     */
    public void drawSplashProgress(final String msg) {
        // clear what we don't need from previous state
        graph.setComposite(AlphaComposite.Clear);
        graph.fillRect(X, textY, W, TEXT_H);
        if (barPos == 0) {
            graph.fillRect(X, barY, W, BAR_H);
        }

        // draw new state
        graph.setPaintMode();

        // draw message
        graph.setColor(Color.WHITE);
        graph.drawString(msg, X, textY + TEXT_H);

        // draw progress bar
        graph.setColor(Color.RED);

        graph.fillRect(X + barPos * (BAR_W), barY, BAR_W, BAR_H);

        // show changes
        splash.update();
        barPos = (barPos + 1) % NUM_BUBBLES;
    }

    /**
     * Draw Splash Message
     */
    private void drawSplashMessage(final String msg) {
        graph.setPaintMode();
        graph.setColor(Color.WHITE);
        Font font = new Font(null, Font.BOLD, 20);
        graph.setColor(Color.WHITE);
        graph.setFont(font);
        String version = "0.1";
        graph.drawString("v " + version, 180, 80);

        Font font2 = font.deriveFont(Font.PLAIN, 12);

        graph.setColor(Color.WHITE);
        graph.setFont(font2);
        graph.drawString(msg, 180, 100);
        splash.update();
    }

    /**
     * Run window splash launcher.
     */
    private static void runSplashLauncher() {
        SplashLauncher sl = new SplashLauncher();
        String[] msg = {"Preparing", "initialize data", "Finalize"};



        try {

            for (int x = 0; x < msg.length; x++) {
                switch (x) {
                    case 0:
                        if (DatabaseUtils.getConnection() != null) {
                            DataHolder.getInstance().execute();
                        }
                        try {
                            Thread.sleep(1000);

                        } catch (InterruptedException ex) {
                            logger.log(Level.SEVERE, "thread case 1 :{0}", ex);
                        }
                        break;
                    case 1:

                        try {
                            Thread.sleep(1000);

                        } catch (InterruptedException ex) {
                            logger.log(Level.SEVERE, "thread case 1 :{0}", ex);
                        }

                        break;

                    case 2:

                        try {
                            Thread.sleep(1000);

                        } catch (InterruptedException ex) {
                            logger.log(Level.SEVERE, "thread case 2 :{0}", ex);
                        }


                        break;

                    default:
                        break;
                }
                sl.drawSplashProgress(msg[x]);

            }
        } catch (NullPointerException e) {
            logger.log(Level.SEVERE, "Cannot load splash screen:{0}", e);
        }
    }

    /**
     * Main class invoke SplashLauncher class
     */
    public static void main(final String args[]) {
        try {
            runSplashLauncher();
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                logger.log(Level.SEVERE, "Cannot load Lookandfeel:{0}", e);
            }

            Runnable app = new Runnable() {

                @Override
                public void run() {
                    MainFrame.getInstance().setVisible(true);
                }
            };
            SwingUtilities.invokeAndWait(app);
        } catch (InterruptedException | InvocationTargetException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
}
