package com.adi.thinkoop.perwalianonline.model;




/**
 * @author adi nuralim adinuralim@gmail.com
 * @since 03-Mar-2013
 */
public class Perwalian  implements java.io.Serializable {


     private Integer id;
     private TahunAkademik tahunAkademik;
     private KontrakMatakuliah kontrakMatakuliah;
     private Mahasiswa mahasiswa;
     private boolean status;

    public Perwalian() {
    }

    public Perwalian(TahunAkademik tahunAkademik, KontrakMatakuliah kontrakMatakuliah, Mahasiswa mahasiswa, boolean status) {
       this.tahunAkademik = tahunAkademik;
       this.kontrakMatakuliah = kontrakMatakuliah;
       this.mahasiswa = mahasiswa;
       this.status = status;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public TahunAkademik getTahunAkademik() {
        return this.tahunAkademik;
    }
    
    public void setTahunAkademik(TahunAkademik tahunAkademik) {
        this.tahunAkademik = tahunAkademik;
    }
    public KontrakMatakuliah getKontrakMatakuliah() {
        return this.kontrakMatakuliah;
    }
    
    public void setKontrakMatakuliah(KontrakMatakuliah kontrakMatakuliah) {
        this.kontrakMatakuliah = kontrakMatakuliah;
    }
    public Mahasiswa getMahasiswa() {
        return this.mahasiswa;
    }
    
    public void setMahasiswa(Mahasiswa mahasiswa) {
        this.mahasiswa = mahasiswa;
    }
    public boolean isStatus() {
        return this.status;
    }
    
    public void setStatus(boolean status) {
        this.status = status;
    }




}


