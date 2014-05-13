/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adi.thinkoop.perwalianonline.controller;

import com.adi.thinkoop.perwalianonline.implement.ProdiImpl;
import com.adi.thinkoop.perwalianonline.interfaces.ProdiInterface;
import com.adi.thinkoop.perwalianonline.model.Prodi;
import com.adi.thinkoop.perwalianonline.utils.DataHolder;
import java.sql.SQLException;
import java.util.Map;

/**
 *
 * @author adi nuralim adinuralim@gmail.com
 * @since 03-Mar-2013
 */
public class ProdiController {

    private ProdiInterface prodiInterface;
    private static ProdiController prodiController;

    public static ProdiController getInstance() {
        if (prodiController == null) {
            prodiController = new ProdiController();
        }

        return prodiController;
    }

    public ProdiController() {
        prodiInterface = new ProdiImpl();
    }

    public Prodi getProdi(String prodi) {
        return DataHolder.getInstance().getProdiMaps().get(prodi);
    }

    public Map getAll() throws SQLException {
        return prodiInterface.getAll();
    }
}
