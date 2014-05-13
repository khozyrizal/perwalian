/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adi.thinkoop.perwalianonline.controller;


import com.adi.thinkoop.perwalianonline.implement.KontrakMatakuliahiImpl;
import com.adi.thinkoop.perwalianonline.interfaces.KontrakMatakuliahiInterface;
import com.adi.thinkoop.perwalianonline.launcher.SplashLauncher;
import com.adi.thinkoop.perwalianonline.model.KontrakMatakuliah;
import com.adi.thinkoop.perwalianonline.utils.DataHolder;
import com.adi.thinkoop.perwalianonline.utils.Message;
import com.adi.thinkoop.perwalianonline.view.KontrakDialog;
import com.adi.thinkoop.perwalianonline.view.MainFrame;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author adi nuralim adinuralim@gmail.com
 * @since 03-Mar-2013
 */
public class KontrakMatakuliahController {

    private KontrakMatakuliahiInterface kontrakMatakuliahiInterface;
    private static KontrakMatakuliahController kontrakMatakuliahController;

    public static KontrakMatakuliahController getInstance() {
        if (kontrakMatakuliahController == null) {
            kontrakMatakuliahController = new KontrakMatakuliahController();
        }

        return kontrakMatakuliahController;
    }

    public KontrakMatakuliahController() {
        kontrakMatakuliahiInterface = new KontrakMatakuliahiImpl();
    }

    public KontrakMatakuliah getKontrakMatakuliah(Integer kontrakMkId) {
        return DataHolder.getInstance().getKontrakMatakuliahMaps().get(kontrakMkId);
    }

    public String getKey(KontrakMatakuliah kontrakMatakuliah) {

        return kontrakMatakuliah.getDetailMatakuliah().getMatakuliah().getKodeMk() + kontrakMatakuliah.getDetailMatakuliah().getKelas();
    }

    public Map getAll() throws SQLException {
        return kontrakMatakuliahiInterface.getAll();
    }

    public void populateTableKontrakMatakuliah() {

        new SwingWorker<Void, Void>() {

            @Override
            protected Void doInBackground() throws Exception {
                DefaultTableModel defaultTableModel = (DefaultTableModel) KontrakDialog.getInstance(false).getTabelKontrakMatakuliah().getModel();
                defaultTableModel.setRowCount(0);

                //get tahun 
                String tahun = MainFrame.getInstance().getComboTahunAkademikKrs().getSelectedItem().toString();

                int semseter = Integer.valueOf(tahun.substring(4, 5));

                Map<String, KontrakMatakuliah> kontrakMatakuliahMap = new TreeMap<>(LoginController.getInstance().getMahasiswa().getKontrakMatakuliahs());


                for (Map.Entry<String, KontrakMatakuliah> entry : kontrakMatakuliahMap.entrySet()) {
                    String string = entry.getKey();
                    KontrakMatakuliah kontrakMatakuliah = entry.getValue();
                    int terisi = DetailMatakuliahController.getInstance().getJumlahKontrakDetailMatakuliah(kontrakMatakuliah.getDetailMatakuliah());
                    int kapasitas = kontrakMatakuliah.getDetailMatakuliah().getKapasitas();

                    System.out.println("terisi : "+terisi);
                    System.out.println("kapasistas : "+kapasitas);
                    
                    System.out.println(terisi<kapasitas);
                    if (semseter % 2 == 0) {
                        int kode = Integer.valueOf(kontrakMatakuliah.getDetailMatakuliah().getMatakuliah().getKodeMk().substring(2, 3));


                        if (!kontrakMatakuliah.isKontrak() && terisi < kapasitas && kode % 2 == 0) {
                            defaultTableModel.addRow(new Object[]{defaultTableModel.getRowCount() + 1, kontrakMatakuliah.isKontrak(), kontrakMatakuliah.getDetailMatakuliah().getMatakuliah().getKodeMk(), kontrakMatakuliah.getDetailMatakuliah().getMatakuliah().getMatakuliah(), kontrakMatakuliah.getDetailMatakuliah().getKelas(), terisi, kapasitas, kontrakMatakuliah.getDetailMatakuliah().getMatakuliah().getSks()});

                        }
                    } else {
                        int kode = Integer.valueOf(kontrakMatakuliah.getDetailMatakuliah().getMatakuliah().getKodeMk().substring(2, 3));


                        if (!kontrakMatakuliah.isKontrak() && terisi < kapasitas && kode % 2 != 0) {
                            defaultTableModel.addRow(new Object[]{defaultTableModel.getRowCount() + 1, kontrakMatakuliah.isKontrak(), kontrakMatakuliah.getDetailMatakuliah().getMatakuliah().getKodeMk(), kontrakMatakuliah.getDetailMatakuliah().getMatakuliah().getMatakuliah(), kontrakMatakuliah.getDetailMatakuliah().getKelas(), terisi, kapasitas, kontrakMatakuliah.getDetailMatakuliah().getMatakuliah().getSks()});

                        }
                    }
                }
                KontrakDialog.getInstance(false).getTabelKontrakMatakuliah().setModel(defaultTableModel);

                return null;

            }
        }.execute();
    }

    public void reset() {
        DefaultTableModel defaultTableModel = (DefaultTableModel) KontrakDialog.getInstance(false).getTabelKontrakMatakuliah().getModel();

        int rowCount = KontrakDialog.getInstance(false).getTabelKontrakMatakuliah().getRowCount();

        if (rowCount == 0) {
            Message.getInstance(KontrakDialog.getInstance(false)).runErrorMessage("Daftar matakuliah kosong!");
        } else {
            for (int i = 0; i < rowCount; i++) {
                defaultTableModel.setValueAt(false, i, 1);
            }
        }

        KontrakDialog.getInstance(false).getTabelKontrakMatakuliah().setModel(defaultTableModel);
    }

    public void updateKontrakMatakuliah(KontrakMatakuliah kontrakMatakuliah) {
        try {
            if (kontrakMatakuliahiInterface.update(kontrakMatakuliah)) {
            } else {
                SplashLauncher.logger.log(Level.SEVERE, null, "error update status kontrak");
            }
        } catch (SQLException ex) {
            SplashLauncher.logger.log(Level.SEVERE, null, ex);
        }
    }

    public int getId(int mId, int pId) {
        int toReturn;
        try {
            toReturn = kontrakMatakuliahiInterface.getIdKontrak(mId, pId);
        } catch (SQLException ex) {
            toReturn = 0;
            SplashLauncher.logger.log(Level.SEVERE, null, ex);
        }

        return toReturn;
    }
}
