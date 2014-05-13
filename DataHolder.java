/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adi.thinkoop.perwalianonline.utils;

import com.adi.thinkoop.perwalianonline.controller.*;
import com.adi.thinkoop.perwalianonline.launcher.SplashLauncher;
import com.adi.thinkoop.perwalianonline.model.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import javax.swing.SwingWorker;

/**
 *
 * @author adi nuralim adinuralim@gmail.com
 * @since 03-Mar-2013
 */
public class DataHolder extends SwingWorker<Void, Void> {

    private static DataHolder dataHolder;

    public static DataHolder getInstance() {
        if (dataHolder == null) {
            dataHolder = new DataHolder();
        }

        return dataHolder;
    }
    private Map<String, Login> loginMaps;
    private Map<String, DosenWali> dosenwaliMaps;
    private Map<String, Mahasiswa> mahasiswaMaps;
    private Map<String, Matakuliah> matakuliahMaps;
    private Map<Integer, DetailMatakuliah> detailMatakuliahMaps;
    private Map<String, KontrakMatakuliah> searchkontrakMatakuliahMaps;
    private Map<Integer, KontrakMatakuliah> kontrakMatakuliahMaps;
    private Map<Integer, Perwalian> perwalianMaps;
    private Map<Integer, Pesan> pesanMaps;
    private Map<String, Pesan> pesanCariMaps;
    private Map<String, Prodi> prodiMaps;
    private Map<String, Program> programMaps;
    private Map<String, TahunAkademik> tahunAkademikMaps;

    public DataHolder() {
        loginMaps = new HashMap<>();
        dosenwaliMaps = new HashMap<>();
        mahasiswaMaps = new HashMap<>();
        matakuliahMaps = new HashMap<>();
        kontrakMatakuliahMaps = new HashMap<>();
        searchkontrakMatakuliahMaps = new HashMap<>();
        perwalianMaps = new HashMap<>();
        pesanMaps = new HashMap<>();
        pesanCariMaps = new HashMap<>();
        prodiMaps = new HashMap<>();
        programMaps = new HashMap<>();
        tahunAkademikMaps = new HashMap<>();
        detailMatakuliahMaps = new HashMap<>();
    }

    public Map<String, Login> getLoginMaps() {
        return loginMaps;
    }

    public void setLoginMaps(Map<String, Login> loginMaps) {
        this.loginMaps = loginMaps;
    }

    public Map<String, DosenWali> getDosenwaliMaps() {
        return dosenwaliMaps;
    }

    public void setDosenwaliMaps(Map<String, DosenWali> dosenwaliMaps) {
        this.dosenwaliMaps = dosenwaliMaps;
    }

    public Map<String, Mahasiswa> getMahasiswaMaps() {
        return mahasiswaMaps;
    }

    public void setMahasiswaMaps(Map<String, Mahasiswa> mahasiswaMaps) {
        this.mahasiswaMaps = mahasiswaMaps;
    }

    public Map<String, Matakuliah> getMatakuliahMaps() {
        return matakuliahMaps;
    }

    public void setMatakuliahMaps(Map<String, Matakuliah> matakuliahMaps) {
        this.matakuliahMaps = matakuliahMaps;
    }

    public Map<Integer, Perwalian> getPerwalianMaps() {
        return perwalianMaps;
    }

    public void setPerwalianMaps(Map<Integer, Perwalian> perwalianMaps) {
        this.perwalianMaps = perwalianMaps;
    }

    public Map<Integer, Pesan> getPesanMaps() {
        return pesanMaps;
    }

    public void setPesanMaps(Map<Integer, Pesan> pesanMaps) {
        this.pesanMaps = pesanMaps;
    }

    public Map<String, Prodi> getProdiMaps() {
        return prodiMaps;
    }

    public void setProdiMaps(Map<String, Prodi> prodiMaps) {
        this.prodiMaps = prodiMaps;
    }

    public Map<String, Program> getProgramMaps() {
        return programMaps;
    }

    public void setProgramMaps(Map<String, Program> programMaps) {
        this.programMaps = programMaps;
    }

    public Map<String, TahunAkademik> getTahunAkademikMaps() {
        return tahunAkademikMaps;
    }

    public void setTahunAkademikMaps(Map<String, TahunAkademik> tahunAkademikMaps) {
        this.tahunAkademikMaps = tahunAkademikMaps;
    }

    public Map<Integer, KontrakMatakuliah> getKontrakMatakuliahMaps() {
        return kontrakMatakuliahMaps;
    }

    public void setKontrakMatakuliahMaps(Map<Integer, KontrakMatakuliah> kontrakMatakuliahMaps) {
        this.kontrakMatakuliahMaps = kontrakMatakuliahMaps;
    }

    public Map<String, KontrakMatakuliah> getSearchkontrakMatakuliahMaps() {
        return searchkontrakMatakuliahMaps;
    }

    public void setSearchkontrakMatakuliahMaps(Map<String, KontrakMatakuliah> searchkontrakMatakuliahMaps) {
        this.searchkontrakMatakuliahMaps = searchkontrakMatakuliahMaps;
    }

    public Map<Integer, DetailMatakuliah> getDetailMatakuliahMaps() {
        return detailMatakuliahMaps;
    }

    public void setDetailMatakuliahMaps(Map<Integer, DetailMatakuliah> detailMatakuliahMaps) {
        this.detailMatakuliahMaps = detailMatakuliahMaps;
    }

    public Map<String, Pesan> getPesanCariMaps() {
        return pesanCariMaps;
    }

    public void setPesanCariMaps(Map<String, Pesan> pesanCariMaps) {
        this.pesanCariMaps = pesanCariMaps;
    }

    void initilaizeLogin() {
        try {
            loginMaps = LoginController.getInstance().getAll();
        } catch (SQLException ex) {
            SplashLauncher.logger.log(Level.SEVERE, null, ex);
        }
    }

    void initilaizeDosenWali() {
        try {
            dosenwaliMaps = DosenWaliController.getInstance().getAll();
        } catch (SQLException ex) {
            SplashLauncher.logger.log(Level.SEVERE, null, ex);
        }
    }

    void initilaizeProdi() {
        try {
            prodiMaps = ProdiController.getInstance().getAll();
        } catch (SQLException ex) {
            SplashLauncher.logger.log(Level.SEVERE, null, ex);
        }
    }

    void initilaizeProgram() {
        try {
            programMaps = ProgramController.getInstance().getAll();
        } catch (SQLException ex) {
            SplashLauncher.logger.log(Level.SEVERE, null, ex);
        }
    }

    void initilaizeTahunAkademik() {
        try {
            tahunAkademikMaps = TahunAkademikController.getInstance().getAll();
        } catch (SQLException ex) {
            SplashLauncher.logger.log(Level.SEVERE, null, ex);
        }
    }

    void initilaizeMahasiswa() {
        try {
            mahasiswaMaps = MahasiswaController.getInstance().getAll();
        } catch (SQLException ex) {
            SplashLauncher.logger.log(Level.SEVERE, null, ex);
        }
    }

    void initilaizeMatakuliah() {
        try {
            matakuliahMaps = MatakuliahController.getInstance().getAll();
        } catch (SQLException ex) {
            SplashLauncher.logger.log(Level.SEVERE, null, ex);
        }
    }

    void initilaizeDetailMatakuliah() {
        try {
            detailMatakuliahMaps = DetailMatakuliahController.getInstance().getAll();
        } catch (SQLException ex) {
            SplashLauncher.logger.log(Level.SEVERE, null, ex);
        }
    }

    void initilaizePerwalian() {
        try {
            perwalianMaps = PerwalianController.getInstance().getAll();
        } catch (SQLException ex) {
            SplashLauncher.logger.log(Level.SEVERE, null, ex);
        }
    }

    void initilaizeKontrakMatakuliah() {
        try {
            kontrakMatakuliahMaps = KontrakMatakuliahController.getInstance().getAll();
        } catch (SQLException ex) {
            SplashLauncher.logger.log(Level.SEVERE, null, ex);
        }
    }

    void initilaizePesan() {
        try {
            pesanMaps = PesanController.getInstance().getAll();
        } catch (SQLException ex) {
            SplashLauncher.logger.log(Level.SEVERE, null, ex);
        }
    }

    void initializeData() {

        System.out.println("initialize dosen wali");
        initilaizeDosenWali();

        System.out.println("initialize login");
        initilaizeLogin();

        System.out.println("initialize prodi");
        initilaizeProdi();

        System.out.println("initialize program");
        initilaizeProgram();

        System.out.println("initialize tahun akademik");
        initilaizeTahunAkademik();

        System.out.println("initialize matakuliah");
        initilaizeMatakuliah();

        System.out.println("initialize detail matakuliah");
        initilaizeDetailMatakuliah();

        System.out.println("initialize mahasiswa");
        initilaizeMahasiswa();

        System.out.println("initialze kontrak matakuliah");
        initilaizeKontrakMatakuliah();

        System.out.println("initialze perwalian ");
        initilaizePerwalian();

        System.out.println("initialze pesan");
        initilaizePesan();


    }

    @Override
    protected Void doInBackground() throws Exception {

        while (true) {
            initializeData();
            Thread.sleep(3600000);
        }
    }
}
