package com.adi.thinkoop.perwalianonline.model;



import java.util.HashMap;
import java.util.Map;

/**
* @author adi nuralim adinuralim@gmail.com
 * @since 03-Mar-2013
 */
public class Matakuliah  implements java.io.Serializable {


     private Integer id;
     private String kodeMk;
     private String matakuliah;
     private byte sks;
     private Map<Integer,DetailMatakuliah> detailMatakuliahs = new HashMap<>();

    public Matakuliah() {
    }

	
    public Matakuliah(String kodeMk, String matakuliah, byte sks) {
        this.kodeMk = kodeMk;
        this.matakuliah = matakuliah;
        this.sks = sks;
    }
    public Matakuliah(String kodeMk, String matakuliah, byte sks, Map<Integer,DetailMatakuliah> detailMatakuliahs) {
       this.kodeMk = kodeMk;
       this.matakuliah = matakuliah;
       this.sks = sks;
       this.detailMatakuliahs = detailMatakuliahs;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getKodeMk() {
        return this.kodeMk;
    }
    
    public void setKodeMk(String kodeMk) {
        this.kodeMk = kodeMk;
    }
    public String getMatakuliah() {
        return this.matakuliah;
    }
    
    public void setMatakuliah(String matakuliah) {
        this.matakuliah = matakuliah;
    }
    public byte getSks() {
        return this.sks;
    }
    
    public void setSks(byte sks) {
        this.sks = sks;
    }
    public Map<Integer,DetailMatakuliah> getDetailMatakuliahs() {
        return this.detailMatakuliahs;
    }
    
    public void setDetailMatakuliahs(Map<Integer,DetailMatakuliah> detailMatakuliahs) {
        this.detailMatakuliahs = detailMatakuliahs;
    }




}


