package com.adi.thinkoop.perwalianonline.model;




/**
* @author adi nuralim adinuralim@gmail.com
 * @since 03-Mar-2013
 */
public class IpsPersemester  implements java.io.Serializable {

     private Integer id;
     private TahunAkademik tahunAkademik;
     private Mahasiswa mahasiswa;
     private float ips;

    public IpsPersemester() {
    }

    public IpsPersemester(TahunAkademik tahunAkademik, Mahasiswa mahasiswa, float ips) {
       this.tahunAkademik = tahunAkademik;
       this.mahasiswa = mahasiswa;
       this.ips = ips;
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
    public Mahasiswa getMahasiswa() {
        return this.mahasiswa;
    }
    
    public void setMahasiswa(Mahasiswa mahasiswa) {
        this.mahasiswa = mahasiswa;
    }
    public float getIps() {
        return this.ips;
    }
    
    public void setIps(float ips) {
        this.ips = ips;
    }




}


