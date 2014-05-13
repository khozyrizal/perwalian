package com.adi.thinkoop.perwalianonline.model;



import java.util.HashSet;
import java.util.Set;

/**
* @author adi nuralim adinuralim@gmail.com
 * @since 03-Mar-2013
 */
public class Prodi  implements java.io.Serializable {


     private Integer id;
     private String prodi;
     private Set mahasiswas = new HashSet(0);

    public Prodi() {
    }

	
    public Prodi(String prodi) {
        this.prodi = prodi;
    }
    public Prodi(String prodi, Set mahasiswas) {
       this.prodi = prodi;
       this.mahasiswas = mahasiswas;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getProdi() {
        return this.prodi;
    }
    
    public void setProdi(String prodi) {
        this.prodi = prodi;
    }
    public Set getMahasiswas() {
        return this.mahasiswas;
    }
    
    public void setMahasiswas(Set mahasiswas) {
        this.mahasiswas = mahasiswas;
    }




}


