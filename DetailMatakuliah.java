package com.adi.thinkoop.perwalianonline.model;

import java.util.HashSet;
import java.util.Set;

/**
 * @author adi nuralim adinuralim@gmail.com
 * @since 03-Mar-2013
 */
public class DetailMatakuliah implements java.io.Serializable {

    private Integer id;
    private Matakuliah matakuliah;
    private String kelas;
    private String dosen;
    private int kapasitas;
    private Set kontrakMatakuliahs = new HashSet(0);

    public DetailMatakuliah() {
    }

    public DetailMatakuliah(Matakuliah matakuliah, String kelas, String dosen, int kapasitas) {
        this.matakuliah = matakuliah;
        this.kelas = kelas;
        this.dosen = dosen;
        this.kapasitas = kapasitas;
    }

    public DetailMatakuliah(Matakuliah matakuliah, String kelas, String dosen, int kapasitas, Set kontrakMatakuliahs) {
        this.matakuliah = matakuliah;
        this.kelas = kelas;
        this.dosen = dosen;
        this.kapasitas = kapasitas;
        this.kontrakMatakuliahs = kontrakMatakuliahs;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Matakuliah getMatakuliah() {
        return this.matakuliah;
    }

    public void setMatakuliah(Matakuliah matakuliah) {
        this.matakuliah = matakuliah;
    }

    public String getKelas() {
        return this.kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getDosen() {
        return this.dosen;
    }

    public void setDosen(String dosen) {
        this.dosen = dosen;
    }

    public int getKapasitas() {
        return this.kapasitas;
    }

    public void setKapasitas(int kapasitas) {
        this.kapasitas = kapasitas;
    }

    public Set getKontrakMatakuliahs() {
        return this.kontrakMatakuliahs;
    }

    public void setKontrakMatakuliahs(Set kontrakMatakuliahs) {
        this.kontrakMatakuliahs = kontrakMatakuliahs;
    }
}
