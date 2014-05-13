/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adi.thinkoop.perwalianonline.utils;

import adi.glasspane.JGlassPane;
import adi.glasspane.component.JGlassPaneComponent;
import adi.glasspane.component.MessageComponent;
import java.awt.GridLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingWorker;

/**
 * @author adi nuralim adinuralim@gmail.com
 * @since 01-Mar-2013
 */
public class Message {

    private adi.glasspane.component.MessageComponent component;
    private adi.glasspane.JGlassPane glassPane;
    private static JFrame frame;
    private static JDialog dialog;
    private static Message message;

    public static Message getInstance(JFrame jFrame) {


        if (message == null) {
            message = new Message();
        }
        Message.frame = jFrame;
        return message;
    }

    public static Message getInstance(JDialog dialog) {


        if (message == null) {
            message = new Message();
        }
        Message.dialog = dialog;
        return message;
    }

    public Message() {

        glassPane = new adi.glasspane.JGlassPane();
        component = new adi.glasspane.component.MessageComponent();
    }

    class RunMessage extends SwingWorker<Void, Void> {

        String message;
        private adi.glasspane.component.MessageComponent component;
        private adi.glasspane.JGlassPane glassPane;
        private JFrame frame;
        private JDialog dialog;

        @Override
        protected Void doInBackground() throws Exception {
            glassPane = new JGlassPane();
            if (dialog != null) {
                dialog.setGlassPane(glassPane);
                dialog.getGlassPane().setVisible(true);

                dialog = null;
            }

            if (frame != null) {
                frame.setGlassPane(glassPane);
                frame.getGlassPane().setVisible(true);
                frame = null;
            }
            component = new MessageComponent();
            glassPane.setLayout(new GridLayout());
            //component.setLayout(new GridBagLayout());
            glassPane.addGlassPaneComponent((JGlassPaneComponent) component);
            component.show(message, 2000);

            return null;
        }
    }

    class RunErrorMessage extends SwingWorker<Void, Void> {

        String message;
        private adi.glasspane.component.MessageComponent component;
        private adi.glasspane.JGlassPane glassPane;
        private JFrame frame;
        private JDialog dialog;

        @Override
        protected Void doInBackground() throws Exception {
            glassPane = new JGlassPane();
            if (dialog != null) {
                dialog.setGlassPane(glassPane);
                dialog.getGlassPane().setVisible(true);

                dialog = null;
            }

            if (frame != null) {
                frame.setGlassPane(glassPane);
                frame.getGlassPane().setVisible(true);
                frame = null;
            }
            component = new MessageComponent();
            glassPane.setLayout(new GridLayout());
            //component.setLayout(new GridBagLayout());
            glassPane.addGlassPaneComponent((JGlassPaneComponent) component);
            component.showError(message, 2000);

            return null;
        }
    }

    public void runMessage(String message) {

        RunMessage runMessage = new RunMessage();
        runMessage.component = component;
        runMessage.dialog = dialog;
        runMessage.frame = frame;
        runMessage.glassPane = glassPane;
        runMessage.message = message;
        runMessage.execute();


    }

    public void runErrorMessage(String message) {

        RunErrorMessage runMessage = new RunErrorMessage();
        runMessage.component = component;
        runMessage.dialog = dialog;
        runMessage.frame = frame;
        runMessage.glassPane = glassPane;
        runMessage.message = message;
        runMessage.execute();


    }
}
