/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adi.thinkoop.perwalianonline.controller;


import com.adi.thinkoop.perwalianonline.implement.PerwalianImpl;
import com.adi.thinkoop.perwalianonline.interfaces.PerwalianInterface;
import com.adi.thinkoop.perwalianonline.launcher.SplashLauncher;
import com.adi.thinkoop.perwalianonline.model.KontrakMatakuliah;
import com.adi.thinkoop.perwalianonline.model.Mahasiswa;
import com.adi.thinkoop.perwalianonline.model.Perwalian;
import com.adi.thinkoop.perwalianonline.model.TahunAkademik;
import com.adi.thinkoop.perwalianonline.utils.DataHolder;
import com.adi.thinkoop.perwalianonline.utils.Message;
import com.adi.thinkoop.perwalianonline.view.KontrakDialog;
import com.adi.thinkoop.perwalianonline.view.MainFrame;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author adi nuralim adinuralim@gmail.com
 * @since 03-Mar-2013
 */
public class PerwalianController {

    private PerwalianInterface perwalianInterface;
    private static PerwalianController perwalianController;

    public static PerwalianController getInstance() {
        if (perwalianController == null) {
            perwalianController = new PerwalianController();
        }

        return perwalianController;
    }

    public PerwalianController() {
        perwalianInterface = new PerwalianImpl();
    }

    public TahunAkademik getTahunAkademik(String tahunAkademik) {
        return DataHolder.getInstance().getTahunAkademikMaps().get(tahunAkademik);
    }

    public Map getAll() throws SQLException {
        return perwalianInterface.getAll();
    }

    public void simpanPerwalian() {
        DefaultTableModel defaultTableModel = (DefaultTableModel) KontrakDialog.getInstance(false).getTabelKontrakMatakuliah().getModel();

        int rowCount = defaultTableModel.getRowCount();

        if (rowCount == 0) {
            Message.getInstance(KontrakDialog.getInstance(false)).runErrorMessage("Data matakuliah kosong!");
        } else {

            boolean simpan = false;
            for (int i = 0; i < rowCount; i++) {
                try {

                    boolean kontrak = (boolean) defaultTableModel.getValueAt(i, 1);

                    if (kontrak) {
                        String kodeMatakuliah = defaultTableModel.getValueAt(i, 2).toString();
                        String kelas = defaultTableModel.getValueAt(i, 4).toString();

                        //get mahasiswa
                        Mahasiswa mahasiswa = LoginController.getInstance().getMahasiswa();
                        Map<String, KontrakMatakuliah> kontrakMatakuliahMap = mahasiswa.getKontrakMatakuliahs();
                        //get kontrak matakuliah from mahasiswa
                        KontrakMatakuliah kontrakMatakuliah = kontrakMatakuliahMap.get(kodeMatakuliah + kelas);

                        //create perwalian
                        Perwalian perwalian = new Perwalian();
                        perwalian.setId(PerwalianController.getInstance().getIdCounterPerwalian() + 1);
                        perwalian.setMahasiswa(LoginController.getInstance().getMahasiswa());
                        perwalian.setStatus(false);

                        //get tahun akademik
                        String tahun = MainFrame.getInstance().getComboTahunAkademikKrs().getSelectedItem().toString();
                        kontrakMatakuliah.setKontrak(true);

                        //update status kontrak matakuliah
                        KontrakMatakuliahController.getInstance().updateKontrakMatakuliah(kontrakMatakuliah);

                        perwalian.setTahunAkademik(TahunAkademikController.getInstance().getTahunAkademik(tahun.substring(0, 5)));
                        perwalian.setKontrakMatakuliah(KontrakMatakuliahController.getInstance().getKontrakMatakuliah(kontrakMatakuliah.getId()));

                        //simpan perwalian
                        if (perwalianInterface.simpanPerwalian(perwalian)) {
                            simpan = true;
                            DataHolder.getInstance().getPerwalianMaps().put(perwalian.getId(), perwalian);
                            mahasiswa.getPerwalians().add(perwalian);
                            mahasiswa.getKontrakMatakuliahs().put(kodeMatakuliah + kelas, kontrakMatakuliah);

                        } else {
                            simpan = false;
                        }
                    }
                } catch (SQLException ex) {
                    simpan = false;
                    SplashLauncher.logger.log(Level.SEVERE, null, ex);
                }
            }

            if (simpan) {
                MahasiswaController.getInstance().populateDaftarKrs(LoginController.getInstance().getMahasiswa());
                KontrakMatakuliahController.getInstance().populateTableKontrakMatakuliah();
                Message.getInstance(KontrakDialog.getInstance(false)).runMessage("kontrak matakuliah berhasil!");
            } else {
                Message.getInstance(KontrakDialog.getInstance(false)).runErrorMessage("kontrak matakuliah gagal!");
            }
        }
    }

    public void updatePerwalian() {
        DefaultTableModel defaultTableModel = (DefaultTableModel) MainFrame.getInstance().getTabelKrs().getModel();

        int row = MainFrame.getInstance().getTabelKrs().getSelectedRow();


        if (row == -1) {
            Message.getInstance(KontrakDialog.getInstance(false)).runErrorMessage("pilih matakuliah!");
        } else {



            try {

                String kodeMatakuliah = defaultTableModel.getValueAt(row, 1).toString();
                String kelas = defaultTableModel.getValueAt(row, 4).toString();

                //get mahasiswa
                Mahasiswa mahasiswa = LoginController.getInstance().getMahasiswa();
                Map<String, KontrakMatakuliah> kontrakMatakuliahMap = mahasiswa.getKontrakMatakuliahs();

                //get kontrak matakuliah from mahasiswa
                KontrakMatakuliah kontrakMatakuliah = kontrakMatakuliahMap.get(kodeMatakuliah + kelas);

                //create perwalian
                Perwalian perwalian = getPerwalianMahasiswa(mahasiswa, kontrakMatakuliah.getId());

                //update status kontrak matakuliah
                kontrakMatakuliah.setKontrak(false);
                KontrakMatakuliahController.getInstance().updateKontrakMatakuliah(kontrakMatakuliah);

                //hapus perwalian
                if (perwalianInterface.hapusPerwalian(perwalian)) {
                    DataHolder.getInstance().getPerwalianMaps().remove(perwalian.getId());
                    mahasiswa.getPerwalians().remove(perwalian);
                    mahasiswa.getKontrakMatakuliahs().put(kodeMatakuliah + kelas, kontrakMatakuliah);
                    MahasiswaController.getInstance().populateDaftarKrs(LoginController.getInstance().getMahasiswa());
                    KontrakMatakuliahController.getInstance().populateTableKontrakMatakuliah();
                    Message.getInstance(KontrakDialog.getInstance(false)).runMessage("transaksi berhasil!");
                } else {
                    Message.getInstance(KontrakDialog.getInstance(false)).runErrorMessage("transaksi gagal!");
                }

            } catch (SQLException ex) {
                SplashLauncher.logger.log(Level.SEVERE, null, ex);
            }
        }
    }

    public int getIdCounterPerwalian() {
        int mapPerwalain;
        try {
            mapPerwalain = new TreeMap<>(DataHolder.getInstance().getPerwalianMaps()).descendingKeySet().first();
        } catch (Exception e) {
            mapPerwalain = 0;

        }

        return mapPerwalain;
    }

    public Perwalian getPerwalianMahasiswa(Mahasiswa mahasiswa, int kontrakMatakuliahId) {
        Perwalian toReturn = null;

        try {
            for (Iterator it = mahasiswa.getPerwalians().iterator(); it.hasNext();) {
                Perwalian perwalian = (Perwalian) it.next();
                KontrakMatakuliah kontrakMatakuliah = KontrakMatakuliahController.getInstance().getKontrakMatakuliah(KontrakMatakuliahController.getInstance().getId(mahasiswa.getId(), perwalian.getId()));

                System.out.println("kontrak matakuliah : " + kontrakMatakuliah);
                if (kontrakMatakuliah.getId() == kontrakMatakuliahId) {
                    toReturn = perwalian;
                    break;
                }
            }
        } catch (Exception e) {
            toReturn = null;
            SplashLauncher.logger.log(Level.SEVERE, null, e);
        }

        return toReturn;
    }
}
