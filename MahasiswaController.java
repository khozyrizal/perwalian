/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adi.thinkoop.perwalianonline.controller;

import com.adi.thinkoop.perwalianonline.implement.MahasiswaImpl;
import com.adi.thinkoop.perwalianonline.interfaces.MahasiswaInterface;
import com.adi.thinkoop.perwalianonline.launcher.SplashLauncher;
import com.adi.thinkoop.perwalianonline.model.*;
import com.adi.thinkoop.perwalianonline.utils.DataHolder;
import com.adi.thinkoop.perwalianonline.view.MainFrame;
import java.awt.Cursor;
import java.io.File;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author adi nuralim adinuralim@gmail.com
 * @since 03-Mar-2013
 */
public class MahasiswaController {

    private MahasiswaInterface mahasiswaInterface;
    private static MahasiswaController mahasiswaController;

    public static MahasiswaController getInstance() {
        if (mahasiswaController == null) {
            mahasiswaController = new MahasiswaController();
        }

        return mahasiswaController;
    }

    public MahasiswaController() {
        mahasiswaInterface = new MahasiswaImpl();
    }

    public Mahasiswa getMahasiswa(String npm) {
        return DataHolder.getInstance().getMahasiswaMaps().get(npm);
    }

    public Map getAll() throws SQLException {
        return mahasiswaInterface.getAll();
    }

    public void populateMahasiswa(Mahasiswa mahasiswa) {

        //set data diri
        MainFrame.getInstance().getTextNpm().setText(mahasiswa.getNpm());
        MainFrame.getInstance().getTextNama().setText(mahasiswa.getNama());
        MainFrame.getInstance().getTextIpk().setText(String.valueOf(mahasiswa.getIpk()));
        MainFrame.getInstance().getTextProdi().setText(mahasiswa.getProdi().getProdi());
        MainFrame.getInstance().getTextProgram().setText(mahasiswa.getProgram().getProgram());
        MainFrame.getInstance().getTextDosenWali().setText(mahasiswa.getDosenWali().getNama());

        String status;
        if (mahasiswa.isStatus()) {
            status = "Aktif(A)";
            MainFrame.getInstance().getButtonCetakKrs().setEnabled(true);

        } else {
            status = "Tidak Aktif";
            MainFrame.getInstance().getButtonCetakKrs().setEnabled(false);
        }

        MainFrame.getInstance().getTextStatus().setText(status);


        //set krs
        MainFrame.getInstance().getTextNpmKrs1().setText(mahasiswa.getNpm());
        MainFrame.getInstance().getTextNpmKrs().setText(mahasiswa.getNpm());
        MainFrame.getInstance().getTextNamaKrs().setText(mahasiswa.getNama());
        MainFrame.getInstance().getTextProdiKrs().setText(mahasiswa.getProdi().getProdi());
        MainFrame.getInstance().getTextProgramKrs().setText(mahasiswa.getProgram().getProgram());
        MainFrame.getInstance().getTextDosenKrs().setText(mahasiswa.getDosenWali().getNama());
        MainFrame.getInstance().getTextIpkKrs().setText(String.valueOf(mahasiswa.getIpk()));
        MainFrame.getInstance().getTextIpsKrs().setText(String.valueOf(getIps(mahasiswa)));
        MainFrame.getInstance().getTotalSksMahasiswa().setText(getTotalSks(getIps(mahasiswa)));
        populateTahunAkademikKrs();


        //populate tabel krs
        populateDaftarKrs(mahasiswa);

        //populate table daftar matakuliah
        MatakuliahController.getInstance().populateTableDaftarMatakuliah();

        //populate pesan
        MainFrame.getInstance().getTextPesanDosenWali().setText(mahasiswa.getDosenWali().getNama());
        PesanController.getInstance().populateTablePesan();


    }

    public void populateTahunAkademikKrs() {
        Map<String, TahunAkademik> tahunAkademisSorting = new TreeMap<>(DataHolder.getInstance().getTahunAkademikMaps()).descendingMap();

        MainFrame.getInstance().getComboTahunAkademikKrs().removeAllItems();
        for (Map.Entry<String, TahunAkademik> entry : tahunAkademisSorting.entrySet()) {
            String string = entry.getKey();
            TahunAkademik tahunAkademik = entry.getValue();
            MainFrame.getInstance().getComboTahunAkademikKrs().addItem(string + " - " + tahunAkademik.getSemester());

        }




    }

    class PopulateDaftarKrs extends SwingWorker<Void, Void> {

        Mahasiswa mahasiswa;

        @Override
        protected Void doInBackground() throws Exception {
            String tahun = MainFrame.getInstance().getComboTahunAkademikKrs().getSelectedItem().toString();
         

            TahunAkademik tahunAkademik = TahunAkademikController.getInstance().getTahunAkademik(tahun.substring(0, 5));
            DefaultTableModel defaultTableModel = (DefaultTableModel) MainFrame.getInstance().getTabelKrs().getModel();
            defaultTableModel.setRowCount(0);

            int tempSks = 0;

            for (Iterator it = mahasiswa.getPerwalians().iterator(); it.hasNext();) {
                Perwalian perwalian = (Perwalian) it.next();

            
                Perwalian tempPerwalian = DataHolder.getInstance().getPerwalianMaps().get(perwalian.getId());

                if (tempPerwalian.getTahunAkademik().getTahun().equalsIgnoreCase(tahunAkademik.getTahun())) {
                    String status;
                 
                    if (tempPerwalian.isStatus()) {
                        status = "DITERIMA";
                    } else {
                        status = "DITUNDA";
                    }

                    defaultTableModel.addRow(new Object[]{defaultTableModel.getRowCount() + 1, tempPerwalian.getKontrakMatakuliah().getDetailMatakuliah().getMatakuliah().getKodeMk(), tempPerwalian.getKontrakMatakuliah().getDetailMatakuliah().getMatakuliah().getMatakuliah(), status, tempPerwalian.getKontrakMatakuliah().getDetailMatakuliah().getKelas(), tempPerwalian.getKontrakMatakuliah().getDetailMatakuliah().getMatakuliah().getSks()});
                    tempSks = tempSks + tempPerwalian.getKontrakMatakuliah().getDetailMatakuliah().getMatakuliah().getSks();
                }
            }

            MainFrame.getInstance().getTextTotalSks().setText(String.valueOf(tempSks));
            MainFrame.getInstance().getTabelKrs().setModel(defaultTableModel);

            return null;

        }
    }

    public void populateDaftarKrs(Mahasiswa mahasiswa) {
        PopulateDaftarKrs populateDaftarKrs = new PopulateDaftarKrs();
        populateDaftarKrs.mahasiswa = mahasiswa;
        populateDaftarKrs.execute();
    }

    public float getIps(Mahasiswa mahasiswa) {
        float toReturn = 0;
        try {

            Map<Integer, IpsPersemester> ipsMap = new TreeMap<>(mahasiswa.getIpsPersemesters());

            for (Map.Entry<Integer, IpsPersemester> entry : ipsMap.entrySet()) {
                Integer integer = entry.getKey();
                IpsPersemester ipsPersemester = entry.getValue();
                toReturn = ipsPersemester.getIps();
                break;
            }


        } catch (Exception e) {
            toReturn = 0;
            SplashLauncher.logger.log(Level.SEVERE, null, e);
        }

        return toReturn;
    }

    public String getTotalSks(float ips) {
        String toReturn;


        toReturn = ips + ", MAX : " + getMax(ips);

        return toReturn;
    }

    public int getMax(float ips) {
        int toReturn;

        int max;
        if (ips >= 3.5) {
            max = 24;
        } else if (ips < 3.5 && ips >= 3.0) {
            max = 20;
        } else if (ips < 3.0 && ips >= 2.5) {
            max = 18;
        } else if (ips < 2.5 && ips >= 2.0) {
            max = 16;
        } else if (ips < 2.0 && ips >= 1.5) {
            max = 14;
        } else if (ips < 1.5 && ips >= 1.0) {
            max = 12;
        } else {
            max = 10;
        }



        return max;
    }

    public void krsPreview() {
        try {

           MainFrame.getInstance().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Mahasiswa mahasiswa = LoginController.getInstance().getMahasiswa();

            List<Krs> krses = new ArrayList<>();

            int i = 1;
            for (Iterator it = mahasiswa.getPerwalians().iterator(); it.hasNext();) {
                Perwalian perwalian = (Perwalian) it.next();

                int kontrakId = KontrakMatakuliahController.getInstance().getId(mahasiswa.getId(), perwalian.getId());
                KontrakMatakuliah kontrakMatakuliah = KontrakMatakuliahController.getInstance().getKontrakMatakuliah(kontrakId);

                Krs krs = new Krs();
                krs.setNo(i);
                krs.setDosen(kontrakMatakuliah.getDetailMatakuliah().getDosen());
                krs.setKelas(kontrakMatakuliah.getDetailMatakuliah().getKelas());
                krs.setKode(kontrakMatakuliah.getDetailMatakuliah().getMatakuliah().getKodeMk());
                krs.setMatakuliah(kontrakMatakuliah.getDetailMatakuliah().getMatakuliah().getMatakuliah());
                krs.setSks(kontrakMatakuliah.getDetailMatakuliah().getMatakuliah().getSks());

                String status;
                if (perwalian.isStatus()) {
                    status = "DITERIMA";
                } else {
                    status = "TUNDA";
                }
                krs.setStatus(status);

                krses.add(krs);
                i++;
            }

            Collections.sort(krses);
            JRBeanCollectionDataSource collection = new JRBeanCollectionDataSource(krses);
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(new File("src/com/adi/thinkoop/perwalianonline/report/KRS.jasper"));
            HashMap< String, Object> parameters = new HashMap<>();


            String tahunAkademik;
            try {
                tahunAkademik = MainFrame.getInstance().getComboTahunAkademikKrs().getSelectedItem().toString();
            } catch (Exception e) {
                tahunAkademik = "Tahun Akademik Genap 2012/2013";
            }
            parameters.put("TAHUN_AKADEMIK", tahunAkademik);
            parameters.put("NPM", mahasiswa.getNpm());
            parameters.put("NAMA", mahasiswa.getNama());
            parameters.put("DOSEN_WALI", mahasiswa.getDosenWali().getNama());
            parameters.put("IPS", String.valueOf(getIps(mahasiswa)));
            parameters.put("PRODI", mahasiswa.getProdi().getProdi());
            parameters.put("SKS_MAX", String.valueOf(getMax(getIps(mahasiswa))));
            parameters.put("JUMLAH_SKS", MainFrame.getInstance().getTextTotalSks().getText());
            parameters.put("TAHUN", tahunAkademik.substring(0, 4));
            jasperPrintPreview(jasperReport, parameters, collection);
        } catch (Exception e) {
            SplashLauncher.logger.log(Level.SEVERE, null, e);
            System.out.println("Error print" + e.getMessage());
        }

        MainFrame.getInstance().setCursor(Cursor.getDefaultCursor());

    }

    public void jasperPrintPreview(JasperReport jasperReport, HashMap< String, Object> parameters, JRBeanCollectionDataSource collection) {
        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, collection);
            JasperViewer.viewReport(jasperPrint, false);

        } catch (Exception e) {
            SplashLauncher.logger.log(Level.SEVERE, null, e);
            System.out.println("Error Print : " + e.getMessage());
        }

    }
}
