package com.adi.thinkoop.perwalianonline.model;



import java.util.HashSet;
import java.util.Set;

/**
 * @author adi nuralim adinuralim@gmail.com
 * @since 03-Mar-2013
 */
public class KontrakMatakuliah  implements java.io.Serializable {


     private Integer id;
     private DetailMatakuliah detailMatakuliah;
     private Mahasiswa mahasiswa;
     private boolean kontrak;
     private Set perwalians = new HashSet(0);

    public KontrakMatakuliah() {
    }

	
    public KontrakMatakuliah(DetailMatakuliah detailMatakuliah, Mahasiswa mahasiswa, boolean kontrak) {
        this.detailMatakuliah = detailMatakuliah;
        this.mahasiswa = mahasiswa;
        this.kontrak = kontrak;
    }
    public KontrakMatakuliah(DetailMatakuliah detailMatakuliah, Mahasiswa mahasiswa, boolean kontrak, Set perwalians) {
       this.detailMatakuliah = detailMatakuliah;
       this.mahasiswa = mahasiswa;
       this.kontrak = kontrak;
       this.perwalians = perwalians;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public DetailMatakuliah getDetailMatakuliah() {
        return this.detailMatakuliah;
    }
    
    public void setDetailMatakuliah(DetailMatakuliah detailMatakuliah) {
        this.detailMatakuliah = detailMatakuliah;
    }
    public Mahasiswa getMahasiswa() {
        return this.mahasiswa;
    }
    
    public void setMahasiswa(Mahasiswa mahasiswa) {
        this.mahasiswa = mahasiswa;
    }
    public boolean isKontrak() {
        return this.kontrak;
    }
    
    public void setKontrak(boolean kontrak) {
        this.kontrak = kontrak;
    }
    public Set getPerwalians() {
        return this.perwalians;
    }
    
    public void setPerwalians(Set perwalians) {
        this.perwalians = perwalians;
    }




}


