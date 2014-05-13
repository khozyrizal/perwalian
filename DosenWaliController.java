/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adi.thinkoop.perwalianonline.controller;

import com.adi.thinkoop.perwalianonline.implement.DosenWaliImpl;
import com.adi.thinkoop.perwalianonline.interfaces.DosenWaliInterface;
import com.adi.thinkoop.perwalianonline.model.DosenWali;
import com.adi.thinkoop.perwalianonline.utils.DataHolder;
import java.sql.SQLException;
import java.util.Map;

/**
 *
 * @author adi nuralim adinuralim@gmail.com
 * @since 03-Mar-2013
 */
public class DosenWaliController {

    private DosenWaliInterface dosenWaliInterface;
    private static DosenWaliController dosenWaliController;

    public static DosenWaliController getInstance() {
        if (dosenWaliController == null) {
            dosenWaliController = new DosenWaliController();
        }

        return dosenWaliController;
    }

    public DosenWaliController() {
        dosenWaliInterface = new DosenWaliImpl();
    }

    public DosenWali getDosenWali(String dosenWali) {
        return DataHolder.getInstance().getDosenwaliMaps().get(dosenWali);
    }

    public Map getAll() throws SQLException {
        return dosenWaliInterface.getAll();
    }
}
