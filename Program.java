package com.adi.thinkoop.perwalianonline.model;



import java.util.HashSet;
import java.util.Set;

/**
* @author adi nuralim adinuralim@gmail.com
 * @since 03-Mar-2013
 */
public class Program  implements java.io.Serializable {


     private Integer id;
     private String program;
     private Set mahasiswas = new HashSet(0);

    public Program() {
    }

	
    public Program(String program) {
        this.program = program;
    }
    public Program(String program, Set mahasiswas) {
       this.program = program;
       this.mahasiswas = mahasiswas;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getProgram() {
        return this.program;
    }
    
    public void setProgram(String program) {
        this.program = program;
    }
    public Set getMahasiswas() {
        return this.mahasiswas;
    }
    
    public void setMahasiswas(Set mahasiswas) {
        this.mahasiswas = mahasiswas;
    }




}


