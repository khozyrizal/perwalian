/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adi.thinkoop.perwalianonline.model;

import java.io.Serializable;

/**
 *
* @author adi nuralim adinuralim@gmail.com
 * @since 03-Mar-2013
 */
public class Krs implements Serializable, Comparable<Krs> {

    private int no;
    private String kode;
    private String matakuliah;
    private int sks;
    private String dosen;
    private String kelas;
    private String status;

    public String getDosen() {
        return dosen;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public void setDosen(String dosen) {
        this.dosen = dosen;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getMatakuliah() {
        return matakuliah;
    }

    public void setMatakuliah(String matakuliah) {
        this.matakuliah = matakuliah;
    }

    public int getSks() {
        return sks;
    }

    public void setSks(int sks) {
        this.sks = sks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int compareTo(Krs o) {
        return getKode().compareTo(o.getKode());
    }
}
