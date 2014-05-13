package com.adi.thinkoop.perwalianonline.model;



import java.util.HashSet;
import java.util.Set;


public class Login  implements java.io.Serializable {


     private Integer id;
     private String username;
     private String password;
     private byte role;
     private Set mahasiswas = new HashSet(0);

    public Login() {
    }

	
    public Login(String username, String password, byte role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
    public Login(String username, String password, byte role, Set mahasiswas) {
       this.username = username;
       this.password = password;
       this.role = role;
       this.mahasiswas = mahasiswas;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    public byte getRole() {
        return this.role;
    }
    
    public void setRole(byte role) {
        this.role = role;
    }
    public Set getMahasiswas() {
        return this.mahasiswas;
    }
    
    public void setMahasiswas(Set mahasiswas) {
        this.mahasiswas = mahasiswas;
    }




}


