/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adi.thinkoop.perwalianonline.controller;

import com.adi.thinkoop.perwalianonline.implement.DetailMatakuliahImpl;
import com.adi.thinkoop.perwalianonline.interfaces.DetailMatakuliahiInterface;
import com.adi.thinkoop.perwalianonline.launcher.SplashLauncher;
import com.adi.thinkoop.perwalianonline.model.DetailMatakuliah;
import com.adi.thinkoop.perwalianonline.model.KontrakMatakuliah;
import com.adi.thinkoop.perwalianonline.model.Matakuliah;
import com.adi.thinkoop.perwalianonline.utils.DataHolder;
import com.adi.thinkoop.perwalianonline.view.MainFrame;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author adi nuralim adinuralim@gmail.com
 * @since 03-Mar-2013
 */
public class DetailMatakuliahController {

    private DetailMatakuliahiInterface detailMatakuliahiInterface;
    private static DetailMatakuliahController detailMatakuliahController;

    public static DetailMatakuliahController getInstance() {
        if (detailMatakuliahController == null) {
            detailMatakuliahController = new DetailMatakuliahController();
        }

        return detailMatakuliahController;
    }

    public DetailMatakuliahController() {
        detailMatakuliahiInterface = new DetailMatakuliahImpl();
    }

    public DetailMatakuliah getDetailMatakuliah(Integer detail) {
        return DataHolder.getInstance().getDetailMatakuliahMaps().get(detail);
    }

    public Map getAll() throws SQLException {
        return detailMatakuliahiInterface.getAll();
    }

    public Matakuliah getMatakuliah() {
        Matakuliah matakuliah;
        try {
            DefaultTableModel defaultTableModel = (DefaultTableModel) MainFrame.getInstance().getTabelMatakuliah().getModel();
            int row = MainFrame.getInstance().getTabelMatakuliah().getSelectedRow();

            if (row == -1) {
                matakuliah = null;

            } else {
                String kodeMk = defaultTableModel.getValueAt(row, 1).toString();
                matakuliah = MatakuliahController.getInstance().getMatakuliah(kodeMk);
            }
        } catch (Exception e) {
            matakuliah = null;
            SplashLauncher.logger.log(Level.SEVERE, null, e);
        }
        return matakuliah;
    }

    public int getJumlahKontrakDetailMatakuliah(DetailMatakuliah detailMatakuliah) {
        int toReturn = 0;
       
        for (Iterator it1 = detailMatakuliah.getKontrakMatakuliahs().iterator(); it1.hasNext();) {
            KontrakMatakuliah kontrakMatakuliah = (KontrakMatakuliah) it1.next();
            if (kontrakMatakuliah.isKontrak()) {
                toReturn += 1;
            }
        }


        return toReturn;
    }
}
