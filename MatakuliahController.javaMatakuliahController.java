/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adi.thinkoop.perwalianonline.controller;

import com.adi.thinkoop.perwalianonline.implement.MatakuliahImpl;
import com.adi.thinkoop.perwalianonline.interfaces.MatakuliahiInterface;
import com.adi.thinkoop.perwalianonline.model.Matakuliah;
import com.adi.thinkoop.perwalianonline.model.TahunAkademik;
import com.adi.thinkoop.perwalianonline.utils.DataHolder;
import com.adi.thinkoop.perwalianonline.view.MainFrame;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author adi nuralim adinuralim@gmail.com
 * @since 03-Mar-2013
 */
public class MatakuliahController {

    private MatakuliahiInterface matakuliahiInterface;
    private static MatakuliahController matakuliahController;

    public static MatakuliahController getInstance() {
        if (matakuliahController == null) {
            matakuliahController = new MatakuliahController();
        }

        return matakuliahController;
    }

    public MatakuliahController() {
        matakuliahiInterface = new MatakuliahImpl();
    }

    public Matakuliah getMatakuliah(String matakuliah) {
        return DataHolder.getInstance().getMatakuliahMaps().get(matakuliah);
    }

    public Map getAll() throws SQLException {
        return matakuliahiInterface.getAll();
    }

    public void populateTableDaftarMatakuliah() {
        new SwingWorker<Void, Void>() {

            @Override
            protected Void doInBackground() throws Exception {
                DefaultTableModel defaultTableModel = (DefaultTableModel) MainFrame.getInstance().getTabelMatakuliah().getModel();
                defaultTableModel.setRowCount(0);
                Map<String, Matakuliah> matakuliahSoring = new TreeMap<>(DataHolder.getInstance().getMatakuliahMaps());

                String tahun = MainFrame.getInstance().getComboTahunAkademikKrs().getSelectedItem().toString();

                int semseter = Integer.valueOf(tahun.substring(4, 5));

                for (Map.Entry<String, Matakuliah> entry : matakuliahSoring.entrySet()) {
                    String string = entry.getKey();
                    Matakuliah matakuliah = entry.getValue();

                    if (semseter % 2 == 0) {
                        int kode = Integer.valueOf(matakuliah.getKodeMk().substring(2, 3));

                        if (kode % 2 == 0) {
                            defaultTableModel.addRow(new Object[]{defaultTableModel.getRowCount() + 1, string, matakuliah.getMatakuliah(), matakuliah.getSks()});
                        }
                    } else {
                        int kode = Integer.valueOf(matakuliah.getKodeMk().substring(2, 3));

                        if (kode % 2 != 0) {
                            defaultTableModel.addRow(new Object[]{defaultTableModel.getRowCount() + 1, string, matakuliah.getMatakuliah(), matakuliah.getSks()});
                        }
                    }
                }



                MainFrame.getInstance().getTabelMatakuliah().setModel(defaultTableModel);









                return null;

            }
        }.execute();
    }
}
