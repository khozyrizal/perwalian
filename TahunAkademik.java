package com.adi.thinkoop.perwalianonline.model;



import java.util.HashSet;
import java.util.Set;

/**
* @author adi nuralim adinuralim@gmail.com
 * @since 03-Mar-2013
 */
public class TahunAkademik  implements java.io.Serializable {


     private Integer id;
     private String tahun;
     private String semester;
     private Set ipsPersemesters = new HashSet(0);
     private Set perwalians = new HashSet(0);

    public TahunAkademik() {
    }

	
    public TahunAkademik(String tahun, String semester) {
        this.tahun = tahun;
        this.semester = semester;
    }
    public TahunAkademik(String tahun, String semester, Set ipsPersemesters, Set perwalians) {
       this.tahun = tahun;
       this.semester = semester;
       this.ipsPersemesters = ipsPersemesters;
       this.perwalians = perwalians;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTahun() {
        return this.tahun;
    }
    
    public void setTahun(String tahun) {
        this.tahun = tahun;
    }
    public String getSemester() {
        return this.semester;
    }
    
    public void setSemester(String semester) {
        this.semester = semester;
    }
    public Set getIpsPersemesters() {
        return this.ipsPersemesters;
    }
    
    public void setIpsPersemesters(Set ipsPersemesters) {
        this.ipsPersemesters = ipsPersemesters;
    }
    public Set getPerwalians() {
        return this.perwalians;
    }
    
    public void setPerwalians(Set perwalians) {
        this.perwalians = perwalians;
    }




}


