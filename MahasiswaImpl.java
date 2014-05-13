/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adi.thinkoop.perwalianonline.implement;

import com.adi.thinkoop.perwalianonline.controller.*;
import com.adi.thinkoop.perwalianonline.interfaces.MahasiswaInterface;
import com.adi.thinkoop.perwalianonline.launcher.SplashLauncher;
import com.adi.thinkoop.perwalianonline.model.*;
import com.adi.thinkoop.perwalianonline.utils.DataHolder;
import com.adi.thinkoop.perwalianonline.utils.DatabaseUtils;
import com.mysql.jdbc.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 *
 * @author adi nuralim adinuralim@gmail.com
 * @since 03-Mar-2013
 */
public class MahasiswaImpl implements MahasiswaInterface {
    
    @Override
    public Map getAll() throws SQLException {
        Statement st = (Statement) DatabaseUtils.getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT m.id, m.npm, m.nama, m.ipk,m.status, pr.program AS program, p.prodi AS prodi,d.nama AS dosen_wali "
                + "FROM mahasiswa m, program pr, prodi p,dosen_wali d "
                + "where pr.id=m.program_id and p.id=m.prodi_id and d.id=m.dosen_wali_id");
        Map<String, Mahasiswa> mahasiswaMap = new HashMap<>();
        
        
        
        while (rs.next()) {
            
            try {
                Mahasiswa mahasiswa = new Mahasiswa();
                mahasiswa.setId(rs.getInt("id"));
                mahasiswa.setNpm(rs.getString("npm"));
                mahasiswa.setNama(rs.getString("nama"));
                mahasiswa.setIpk(rs.getFloat("ipk"));
                mahasiswa.setProgram(ProgramController.getInstance().getProgram(rs.getString("program")));
                mahasiswa.setProdi(ProdiController.getInstance().getProdi(rs.getString("prodi")));
                mahasiswa.setDosenWali(DosenWaliController.getInstance().getDosenWali(rs.getString("dosen_wali")));
                mahasiswa.setLogin(LoginController.getInstance().getLogin(mahasiswa.getNpm()));
                mahasiswa.setStatus(rs.getBoolean("status"));
                
                Statement st1 = (Statement) DatabaseUtils.getConnection().createStatement();
                ResultSet rs1 = st1.executeQuery("SELECT p.id, mk.id as kode_kmk, ta.tahun AS tahun, p.status "
                        + "FROM perwalian p, kontrak_matakuliah mk, mahasiswa m, tahun_akademik ta "
                        + "WHERE p.kontrak_matakuliah_id = mk.id AND p.mahasiswa_id = m.id AND p.tahun_akademik_id = ta.id AND m.id =" + mahasiswa.getId() + "");
                
                
                while (rs1.next()) {
                    try {
                        Perwalian perwalian = new Perwalian();
                        perwalian.setId(rs1.getInt("id"));
                        perwalian.setMahasiswa(mahasiswa);
                        KontrakMatakuliah kontrakMatakuliah = KontrakMatakuliahController.getInstance().getKontrakMatakuliah(rs1.getInt("kode_kmk"));
                        perwalian.setKontrakMatakuliah(kontrakMatakuliah);
                        perwalian.setTahunAkademik(TahunAkademikController.getInstance().getTahunAkademik(rs1.getString("tahun")));
                        perwalian.setStatus(rs1.getBoolean("status"));
                        DataHolder.getInstance().getPerwalianMaps().put(perwalian.getId(), perwalian);
                        
                        
                        mahasiswa.getPerwalians().add(perwalian);
                        
                    } catch (SQLException sQLException) {
                        SplashLauncher.logger.log(Level.SEVERE, null, sQLException);
                        
                    }
                    
                }
                
                Statement st2 = (Statement) DatabaseUtils.getConnection().createStatement();
                ResultSet rs2 = st2.executeQuery("SELECT ips.id, ips.ips, ta.tahun "
                        + "FROM mahasiswa mhs, ips_persemester ips, tahun_akademik ta "
                        + "WHERE mhs.id = ips.mahasiswa_id "
                        + "AND ta.id = ips.tahun_akademik_id AND mhs.id =" + mahasiswa.getId() + "");
                
                while (rs2.next()) {
                    IpsPersemester ipsPersemester = new IpsPersemester();
                    
                    ipsPersemester.setId(rs2.getInt("id"));
                    ipsPersemester.setIps(rs2.getFloat("ips"));
                    
                    TahunAkademik tahunAkademik = TahunAkademikController.getInstance().getTahunAkademik(rs2.getString("tahun"));
                    ipsPersemester.setMahasiswa(mahasiswa);
                    ipsPersemester.setTahunAkademik(tahunAkademik);
                    
                    mahasiswa.getIpsPersemesters().put(ipsPersemester.getId(), ipsPersemester);
                }
                
                
                mahasiswaMap.put(mahasiswa.getNpm(), mahasiswa);
            } catch (SQLException sQLException) {
                SplashLauncher.logger.log(Level.SEVERE, null, sQLException);
            }
        }
        return mahasiswaMap;
    }
}
