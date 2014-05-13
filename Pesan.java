package com.adi.thinkoop.perwalianonline.model;




/**
* @author adi nuralim adinuralim@gmail.com
 * @since 03-Mar-2013
 */
public class Pesan  implements java.io.Serializable {


     private Integer id;
     private Mahasiswa mahasiswa;
     private DosenWali dosenWali;
     private String pesan;
     private boolean pengirim;
     private String waktu;
     private boolean status;

    public Pesan() {
    }

    public Pesan(Mahasiswa mahasiswa, DosenWali dosenWali, String pesan, boolean pengirim, String waktu, boolean status) {
       this.mahasiswa = mahasiswa;
       this.dosenWali = dosenWali;
       this.pesan = pesan;
       this.pengirim = pengirim;
       this.waktu = waktu;
       this.status = status;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Mahasiswa getMahasiswa() {
        return this.mahasiswa;
    }
    
    public void setMahasiswa(Mahasiswa mahasiswa) {
        this.mahasiswa = mahasiswa;
    }
    public DosenWali getDosenWali() {
        return this.dosenWali;
    }
    
    public void setDosenWali(DosenWali dosenWali) {
        this.dosenWali = dosenWali;
    }
    public String getPesan() {
        return this.pesan;
    }
    
    public void setPesan(String pesan) {
        this.pesan = pesan;
    }
    public boolean isPengirim() {
        return this.pengirim;
    }
    
    public void setPengirim(boolean pengirim) {
        this.pengirim = pengirim;
    }
    public String getWaktu() {
        return this.waktu;
    }
    
    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }
    public boolean isStatus() {
        return this.status;
    }
    
    public void setStatus(boolean status) {
        this.status = status;
    }




}


