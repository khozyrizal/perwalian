package com.adi.thinkoop.perwalianonline.model;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
* @author adi nuralim adinuralim@gmail.com
 * @since 03-Mar-2013
 */
public class Mahasiswa implements java.io.Serializable {

    private Integer id;
    private Program program;
    private Prodi prodi;
    private DosenWali dosenWali;
    private Login login;
    private String npm;
    private String nama;
    private float ipk;
    private boolean status;
    private Map<Integer, Pesan> pesans = new HashMap<>();
    private Map<String, KontrakMatakuliah> kontrakMatakuliahs = new HashMap<>();
    private Map<Integer, IpsPersemester> ipsPersemesters = new HashMap<>();
    private Set perwalians = new HashSet(0);

    public Mahasiswa() {
    }

    public Mahasiswa(Program program, Prodi prodi, DosenWali dosenWali, Login login, String npm, String nama, float ipk) {
        this.program = program;
        this.prodi = prodi;
        this.dosenWali = dosenWali;
        this.login = login;
        this.npm = npm;
        this.nama = nama;
        this.ipk = ipk;
    }

    public Mahasiswa(Program program, Prodi prodi, DosenWali dosenWali, Login login, String npm, String nama, float ipk, Map<Integer, Pesan> pesans, Map<String, KontrakMatakuliah> kontrakMatakuliahs, Map<Integer,IpsPersemester> ipsPersemesters, Set perwalians) {
        this.program = program;
        this.prodi = prodi;
        this.dosenWali = dosenWali;
        this.login = login;
        this.npm = npm;
        this.nama = nama;
        this.ipk = ipk;
        this.pesans = pesans;

        this.kontrakMatakuliahs = kontrakMatakuliahs;
        this.ipsPersemesters = ipsPersemesters;
        this.perwalians = perwalians;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Program getProgram() {
        return this.program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Prodi getProdi() {
        return this.prodi;
    }

    public void setProdi(Prodi prodi) {
        this.prodi = prodi;
    }

    public DosenWali getDosenWali() {
        return this.dosenWali;
    }

    public void setDosenWali(DosenWali dosenWali) {
        this.dosenWali = dosenWali;
    }

    public Login getLogin() {
        return this.login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public String getNpm() {
        return this.npm;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }

    public String getNama() {
        return this.nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public float getIpk() {
        return this.ipk;
    }

    public void setIpk(float ipk) {
        this.ipk = ipk;
    }

    public Map<Integer, Pesan> getPesans() {
        return pesans;
    }

    public void setPesans(Map<Integer, Pesan> pesans) {
        this.pesans = pesans;
    }

    public Map<String, KontrakMatakuliah> getKontrakMatakuliahs() {
        return this.kontrakMatakuliahs;
    }

    public void setKontrakMatakuliahs(Map<String, KontrakMatakuliah> kontrakMatakuliahs) {
        this.kontrakMatakuliahs = kontrakMatakuliahs;
    }

    public Map<Integer, IpsPersemester> getIpsPersemesters() {
        return ipsPersemesters;
    }

    public void setIpsPersemesters(Map<Integer, IpsPersemester> ipsPersemesters) {
        this.ipsPersemesters = ipsPersemesters;
    }

    public Set getPerwalians() {
        return this.perwalians;
    }

    public void setPerwalians(Set perwalians) {
        this.perwalians = perwalians;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    
}
