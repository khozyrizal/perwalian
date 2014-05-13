/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adi.thinkoop.perwalianonline.controller;

import com.adi.thinkoop.perwalianonline.implement.LoginImpl;
import com.adi.thinkoop.perwalianonline.interfaces.LoginInterface;
import com.adi.thinkoop.perwalianonline.launcher.SplashLauncher;
import com.adi.thinkoop.perwalianonline.model.Login;
import com.adi.thinkoop.perwalianonline.model.Mahasiswa;
import com.adi.thinkoop.perwalianonline.utils.DataHolder;
import com.adi.thinkoop.perwalianonline.view.MainFrame;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;

/**
 *
 * @author adi nuralim adinuralim@gmail.com
 * @since 03-Mar-2013
 */
public class LoginController {

    private Mahasiswa mahasiswa;
    private LoginInterface loginInterface;
    private static LoginController loginController;

    public static LoginController getInstance() {
        if (loginController == null) {
            loginController = new LoginController();
        }

        return loginController;
    }

    public LoginController() {
        loginInterface = new LoginImpl();
    }

    public Mahasiswa getMahasiswa() {
        return mahasiswa;
    }

    public boolean cekLogin() {
        boolean toReturn = false;

        try {
            String username = MainFrame.getInstance().getTextUsername().getText();
            String sandi = MainFrame.getInstance().getTextSandi().getText();

            Login userLogin = getLogin(username);
            if (userLogin != null) {
                if (userLogin.getPassword().equals(sandi)) {
                    toReturn = true;
                    Mahasiswa tempMahasiswa = DataHolder.getInstance().getMahasiswaMaps().get(username);
                    this.mahasiswa = tempMahasiswa;
                    MahasiswaController.getInstance().populateMahasiswa(tempMahasiswa);
                    
                    reset();
                } else {
                    toReturn = false;
                }
            }
        } catch (Exception e) {
            toReturn = false;
            SplashLauncher.logger.log(Level.SEVERE, null, e);
        }
        return toReturn;
    }

    public Login getLogin(String username) {
        return DataHolder.getInstance().getLoginMaps().get(username);
    }

    public Map getAll() throws SQLException {
        return loginInterface.getAll();
    }

    public void reset() {
        MainFrame.getInstance().getTextUsername().setText(null);
        MainFrame.getInstance().getTextSandi().setText(null);
    }
}
