package com.adi.thinkoop.perwalianonline.model;



import java.util.HashSet;
import java.util.Set;

/**
* @author adi nuralim adinuralim@gmail.com
 * @since 03-Mar-2013
 */
public class DosenWali  implements java.io.Serializable {


     private Integer id;
     private String nama;
     private Set pesans = new HashSet(0);
     private Set mahasiswas = new HashSet(0);

    public DosenWali() {
    }

	
    public DosenWali(String nama) {
        this.nama = nama;
    }
    public DosenWali(String nama, Set pesans, Set mahasiswas) {
       this.nama = nama;
       this.pesans = pesans;
       this.mahasiswas = mahasiswas;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNama() {
        return this.nama;
    }
    
    public void setNama(String nama) {
        this.nama = nama;
    }
    public Set getPesans() {
        return this.pesans;
    }
    
    public void setPesans(Set pesans) {
        this.pesans = pesans;
    }
    public Set getMahasiswas() {
        return this.mahasiswas;
    }
    
    public void setMahasiswas(Set mahasiswas) {
        this.mahasiswas = mahasiswas;
    }




}


