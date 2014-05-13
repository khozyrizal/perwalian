/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adi.thinkoop.perwalianonline.controller;

import com.adi.thinkoop.perwalianonline.implement.TahunAkademikImpl;
import com.adi.thinkoop.perwalianonline.interfaces.TahunAkademikInterface;
import com.adi.thinkoop.perwalianonline.model.TahunAkademik;
import com.adi.thinkoop.perwalianonline.utils.DataHolder;
import java.sql.SQLException;
import java.util.Map;

/**
 *
 * @author adi nuralim adinuralim@gmail.com
 * @since 03-Mar-2013
 */
public class TahunAkademikController {

    private TahunAkademikInterface tahunAkademikInterface;
    private static TahunAkademikController tahunAkademikController;

    public static TahunAkademikController getInstance() {
        if (tahunAkademikController == null) {
            tahunAkademikController = new TahunAkademikController();
        }

        return tahunAkademikController;
    }

    public TahunAkademikController() {
        tahunAkademikInterface = new TahunAkademikImpl();
    }

    public TahunAkademik getTahunAkademik(String tahunAkademik) {
        return DataHolder.getInstance().getTahunAkademikMaps().get(tahunAkademik);
    }

    public Map getAll() throws SQLException {
        return tahunAkademikInterface.getAll();
    }
}
