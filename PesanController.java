/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adi.thinkoop.perwalianonline.controller;

import com.adi.thinkoop.perwalianonline.implement.PesanImpl;
import com.adi.thinkoop.perwalianonline.interfaces.PesanInterface;
import com.adi.thinkoop.perwalianonline.launcher.SplashLauncher;
import com.adi.thinkoop.perwalianonline.model.Mahasiswa;
import com.adi.thinkoop.perwalianonline.model.Pesan;
import com.adi.thinkoop.perwalianonline.utils.DataHolder;
import com.adi.thinkoop.perwalianonline.utils.Message;
import com.adi.thinkoop.perwalianonline.view.MainFrame;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author adi nuralim adinuralim@gmail.com
 * @since 03-Mar-2013
 */
public class PesanController {

    private PesanInterface pesanInterface;
    private static PesanController pesanController;

    public static PesanController getInstance() {
        if (pesanController == null) {
            pesanController = new PesanController();
        }

        return pesanController;
    }

    public PesanController() {
        pesanInterface = new PesanImpl();
    }

    public Pesan getPesan(Integer id) {
        return DataHolder.getInstance().getPesanMaps().get(id);
    }

    public int getIdCounterPesan() {
        int mapPesan;
        try {
            mapPesan = new TreeMap<>(DataHolder.getInstance().getPesanMaps()).descendingKeySet().first();
        } catch (Exception e) {
            mapPesan = 0;

        }

        return mapPesan;
    }

    public int getId(String pesanCari) {
        Pesan pesan = DataHolder.getInstance().getPesanCariMaps().get(pesanCari);
        System.out.println("object pesan : " + pesan);
        return pesan.getId();
    }

    public Map getAll() throws SQLException {
        return pesanInterface.getAll();
    }

    public void kirimPesan() {
        String textPesan = MainFrame.getInstance().getTextPesan().getText();

        if (textPesan.isEmpty()) {
            Message.getInstance(MainFrame.getInstance()).runMessage("Pesan tidak boleh kosong!");
        } else {
            try {
                Mahasiswa mahasiswa = LoginController.getInstance().getMahasiswa();
                Pesan pesan = new Pesan(mahasiswa, LoginController.getInstance().getMahasiswa().getDosenWali(), textPesan, false, new Date().toString(), false);
                pesan.setId(PesanController.getInstance().getIdCounterPesan() + 1);
                if (pesanInterface.kirimPesan(pesan)) {
                    Message.getInstance(MainFrame.getInstance()).runMessage("Pesan berhasil dikirim!");
                    DataHolder.getInstance().getPesanMaps().put(pesan.getId(), pesan);
                    DataHolder.getInstance().getPesanCariMaps().put(pesan.getWaktu(), pesan);
                    mahasiswa.getPesans().put(pesan.getId(), pesan);
                    populateTablePesan();
                } else {
                    Message.getInstance(MainFrame.getInstance()).runErrorMessage("Pesan gagal dikirim!");
                }


            } catch (SQLException ex) {
                Logger.getLogger(PesanController.class.getName()).log(Level.SEVERE, null, ex);
                SplashLauncher.logger.log(Level.SEVERE, null, ex);
            }

            reset();
        }
    }

    public void populateTablePesan() {
        new SwingWorker<Void, Void>() {

            @Override
            protected Void doInBackground() throws Exception {

                DefaultTableModel tabelModelPesanMasuk = (DefaultTableModel) MainFrame.getInstance().getTabelPesanMasuk().getModel();
                DefaultTableModel tabelModelPesanTerkirim = (DefaultTableModel) MainFrame.getInstance().getTabelPesanTerkirim().getModel();
                tabelModelPesanMasuk.setRowCount(0);
                tabelModelPesanTerkirim.setRowCount(0);
                Mahasiswa mahasiswa = LoginController.getInstance().getMahasiswa();

                Map<Integer, Pesan> pesanMap = new TreeMap<>(mahasiswa.getPesans()).descendingMap();
                for (Map.Entry<Integer, Pesan> entry : pesanMap.entrySet()) {
                    Integer string = entry.getKey();
                    Pesan pesan = entry.getValue();
                    if (pesan.isPengirim()) {
                        //pesan diterima dari dosen wali ke mahasiswa --> pesan masuk
                        tabelModelPesanMasuk.addRow(new Object[]{tabelModelPesanMasuk.getRowCount() + 1, pesan.getDosenWali().getNama(), pesan.getPesan(), pesan.isStatus(), pesan.getWaktu()});
                    } else {
                        //pesan dikirim ke dosen wali oleh mahasiswa --> pesan terkirim
                        tabelModelPesanTerkirim.addRow(new Object[]{tabelModelPesanTerkirim.getRowCount() + 1, pesan.getDosenWali().getNama(), pesan.getPesan(), pesan.getWaktu()});
                    }

                }


                MainFrame.getInstance().getTabelPesanMasuk().setModel(tabelModelPesanMasuk);
                MainFrame.getInstance().getTabelPesanTerkirim().setModel(tabelModelPesanTerkirim);

                return null;
            }
        }.execute();

    }

    public void reset() {
        MainFrame.getInstance().getTextPesan().setText(null);
    }

    public void bacaPesanMasuk() {
        MainFrame.getInstance().getTextPesanMasuk().setText(null);
        DefaultTableModel defaultTableModel = (DefaultTableModel) MainFrame.getInstance().getTabelPesanMasuk().getModel();
        int row = MainFrame.getInstance().getTabelPesanMasuk().getSelectedRow();

        if (row == -1) {
            Message.getInstance(MainFrame.getInstance()).runMessage("Pilih pesan!");
        } else {
            try {
                String pesan = defaultTableModel.getValueAt(row, 2).toString();
                defaultTableModel.setValueAt(true, row, 3);
                MainFrame.getInstance().getTextPesanMasuk().setText(pesan);

                //update status pesan --> pesan dibaca atau tidak

                String pesanCari = defaultTableModel.getValueAt(row, 4).toString();
                int id = getId(pesanCari);
                Pesan oPesan = DataHolder.getInstance().getPesanMaps().get(id);
                oPesan.setStatus(true);
                if (pesanInterface.updatePesan(oPesan)) {
                    Mahasiswa mahasiswa = LoginController.getInstance().getMahasiswa();
                    mahasiswa.getPesans().put(id, oPesan);

                    DataHolder.getInstance().getPesanMaps().put(id, oPesan);
                    DataHolder.getInstance().getPesanCariMaps().put(oPesan.getWaktu(), oPesan);
                } else {
                    Message.getInstance(MainFrame.getInstance()).runErrorMessage("kesalahan membuka pesan!");
                }
            } catch (SQLException ex) {
                SplashLauncher.logger.log(Level.SEVERE, null, ex);
            }

        }
    }

    public void bacaPesanTerkirim() {
        MainFrame.getInstance().getTextPesanTerkirim().setText(null);
        DefaultTableModel defaultTableModel = (DefaultTableModel) MainFrame.getInstance().getTabelPesanTerkirim().getModel();
        int row = MainFrame.getInstance().getTabelPesanTerkirim().getSelectedRow();

        if (row == -1) {
            Message.getInstance(MainFrame.getInstance()).runMessage("Pilih pesan!");
        } else {
            String pesan = defaultTableModel.getValueAt(row, 2).toString();
            MainFrame.getInstance().getTextPesanTerkirim().setText(pesan);
        }
    }

    public void hapusPesanTerkirim() {
        MainFrame.getInstance().getTextPesanTerkirim().setText(null);
        DefaultTableModel defaultTableModel = (DefaultTableModel) MainFrame.getInstance().getTabelPesanTerkirim().getModel();
        int row = MainFrame.getInstance().getTabelPesanTerkirim().getSelectedRow();

        if (row == -1) {
            Message.getInstance(MainFrame.getInstance()).runMessage("Pilih pesan!");
        } else {
            try {
                String pesanCari = defaultTableModel.getValueAt(row, 3).toString();
                int id = getId(pesanCari);
                Pesan pesan = DataHolder.getInstance().getPesanMaps().get(id);
                if (pesanInterface.hapusPesan(pesan)) {
                    DataHolder.getInstance().getPesanMaps().remove(id);
                    DataHolder.getInstance().getPesanCariMaps().remove(pesan.getWaktu());
                    Mahasiswa mahasiswa = LoginController.getInstance().getMahasiswa();
                    mahasiswa.getPesans().remove(id);
                    Message.getInstance(MainFrame.getInstance()).runMessage("pesan berhasil dihapus!");
                    populateTablePesan();
                } else {
                    Message.getInstance(MainFrame.getInstance()).runErrorMessage("pesan gagal dihapus!");
                }
            } catch (SQLException ex) {
                SplashLauncher.logger.log(Level.SEVERE, null, ex);
            }
        }
    }

    public void hapusSemuaPesanTerkirim() {
        MainFrame.getInstance().getTextPesanTerkirim().setText(null);
        DefaultTableModel defaultTableModel = (DefaultTableModel) MainFrame.getInstance().getTabelPesanTerkirim().getModel();
        int row = MainFrame.getInstance().getTabelPesanTerkirim().getRowCount();

        if (row ==0) {
            Message.getInstance(MainFrame.getInstance()).runMessage("pesan terkirim kosong!");
        } else {
            try {
                boolean b = false;
                for (int i = 0; i < row; i++) {
                    System.out.println("i : " + i);
                    System.out.println("row count : " + row);
                    String pesanCari = defaultTableModel.getValueAt(i, 3).toString();
                    System.out.println("cari : " + pesanCari);
                    int id = getId(pesanCari);
                    Pesan pesan = DataHolder.getInstance().getPesanMaps().get(id);
                    if (pesanInterface.hapusPesan(pesan)) {
                        DataHolder.getInstance().getPesanMaps().remove(id);
                        DataHolder.getInstance().getPesanCariMaps().remove(pesan.getWaktu());
                        Mahasiswa mahasiswa = LoginController.getInstance().getMahasiswa();
                        mahasiswa.getPesans().remove(id);

                        b = true;

                    } else {
                        b = false;
                        break;

                    }
                }

                if (b) {
                    Message.getInstance(MainFrame.getInstance()).runMessage("pesan berhasil dihapus!");
                    populateTablePesan();
                } else {
                    Message.getInstance(MainFrame.getInstance()).runErrorMessage("pesan gagal dihapus!");
                }

            } catch (SQLException ex) {
                SplashLauncher.logger.log(Level.SEVERE, null, ex);
            }
        }
    }

    public void hapusPesanMasuk() {
        MainFrame.getInstance().getTextPesanMasuk().setText(null);
        DefaultTableModel defaultTableModel = (DefaultTableModel) MainFrame.getInstance().getTabelPesanMasuk().getModel();
        int row = MainFrame.getInstance().getTabelPesanMasuk().getSelectedRow();

        if (row == -1) {
            Message.getInstance(MainFrame.getInstance()).runMessage("Pilih pesan!");
        } else {
            try {
                String pesanCari = defaultTableModel.getValueAt(row, 4).toString();
                System.out.println("pesan cari : " + pesanCari);
                int id = getId(pesanCari);
                Pesan pesan = DataHolder.getInstance().getPesanMaps().get(id);
                if (pesanInterface.hapusPesan(pesan)) {
                    DataHolder.getInstance().getPesanMaps().remove(id);
                    DataHolder.getInstance().getPesanCariMaps().remove(pesan.getWaktu());
                    Mahasiswa mahasiswa = LoginController.getInstance().getMahasiswa();
                    mahasiswa.getPesans().remove(id);
                    Message.getInstance(MainFrame.getInstance()).runMessage("pesan berhasil dihapus!");
                    populateTablePesan();
                } else {
                    Message.getInstance(MainFrame.getInstance()).runErrorMessage("pesan gagal dihapus!");
                }
            } catch (SQLException ex) {
                SplashLauncher.logger.log(Level.SEVERE, null, ex);
            }
        }
    }

    public void hapusSemuaPesanMasuk() {
        MainFrame.getInstance().getTextPesanTerkirim().setText(null);
        DefaultTableModel defaultTableModel = (DefaultTableModel) MainFrame.getInstance().getTabelPesanMasuk().getModel();
        int row = MainFrame.getInstance().getTabelPesanMasuk().getRowCount();

        if (row ==0) {
            Message.getInstance(MainFrame.getInstance()).runMessage("pesan masuk kosong!");
        } else {
            try {
                boolean b = false;
                for (int i = 0; i < row; i++) {

                    String pesanCari = defaultTableModel.getValueAt(i, 4).toString();

                    int id = getId(pesanCari);
                    Pesan pesan = DataHolder.getInstance().getPesanMaps().get(id);
                    if (pesanInterface.hapusPesan(pesan)) {
                        DataHolder.getInstance().getPesanMaps().remove(id);
                        DataHolder.getInstance().getPesanCariMaps().remove(pesan.getWaktu());
                        Mahasiswa mahasiswa = LoginController.getInstance().getMahasiswa();
                        mahasiswa.getPesans().remove(id);

                        b = true;

                    } else {
                        b = false;
                        break;

                    }
                }

                if (b) {
                    Message.getInstance(MainFrame.getInstance()).runMessage("pesan berhasil dihapus!");
                    populateTablePesan();
                } else {
                    Message.getInstance(MainFrame.getInstance()).runErrorMessage("pesan gagal dihapus!");
                }

            } catch (SQLException ex) {
                SplashLauncher.logger.log(Level.SEVERE, null, ex);
            }
        }
    }
}
