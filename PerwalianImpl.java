/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adi.thinkoop.perwalianonline.implement;

import com.adi.thinkoop.perwalianonline.controller.KontrakMatakuliahController;
import com.adi.thinkoop.perwalianonline.controller.MahasiswaController;
import com.adi.thinkoop.perwalianonline.controller.TahunAkademikController;
import com.adi.thinkoop.perwalianonline.interfaces.PerwalianInterface;
import com.adi.thinkoop.perwalianonline.launcher.SplashLauncher;
import com.adi.thinkoop.perwalianonline.model.Perwalian;
import com.adi.thinkoop.perwalianonline.utils.DatabaseUtils;
import com.mysql.jdbc.PreparedStatement;
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
public class PerwalianImpl implements PerwalianInterface {

    @Override
    public Map getAll() throws SQLException {
        System.out.println("perwalian populate table");
        Statement st = (Statement) DatabaseUtils.getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT p.id, m.npm, mk.id as kode_kmk, ta.tahun,p.status "
                + "FROM perwalian p, mahasiswa m, kontrak_matakuliah mk, tahun_akademik ta "
                + "WHERE p.mahasiswa_id = m.id AND p.kontrak_matakuliah_id = mk.id AND p.tahun_akademik_id = ta.id");
        Map<Integer, Perwalian> perwalianMap = new HashMap<>();

        while (rs.next()) {
            Perwalian perwalian = new Perwalian();
            perwalian.setId(rs.getInt("id"));
            perwalian.setMahasiswa(MahasiswaController.getInstance().getMahasiswa(rs.getString("npm")));
            perwalian.setKontrakMatakuliah(KontrakMatakuliahController.getInstance().getKontrakMatakuliah(rs.getInt("kode_kmk")));
            perwalian.setTahunAkademik(TahunAkademikController.getInstance().getTahunAkademik(rs.getString("tahun")));
            perwalian.setStatus(rs.getBoolean("status"));
            System.out.println("perwalian tahun akademik : " + perwalian.getTahunAkademik());
            perwalianMap.put(perwalian.getId(), perwalian);
        }
        return perwalianMap;
    }

    @Override
    public boolean simpanPerwalian(Perwalian perwalian) throws SQLException {

        boolean toReturn;
        try {
            String query = "insert into perwalian values (?,?,?,?,?)";
            PreparedStatement st = (PreparedStatement) DatabaseUtils.getConnection().prepareStatement(query);
            st.setInt(1, 0);
            st.setInt(2, perwalian.getMahasiswa().getId());
            st.setInt(3, perwalian.getKontrakMatakuliah().getId());
            st.setInt(4, perwalian.getTahunAkademik().getId());
            st.setBoolean(5, perwalian.isStatus());
            st.executeUpdate();
            toReturn = true;
        } catch (SQLException sQLException) {
            toReturn = false;
            SplashLauncher.logger.log(Level.SEVERE, null, sQLException);
        }
        return toReturn;
    }

    @Override
    public boolean hapusPerwalian(Perwalian perwalian) throws SQLException {
        boolean toReturn;
        try {
            PreparedStatement st = (PreparedStatement) DatabaseUtils.getConnection().prepareStatement("delete from perwalian where id=?");
            st.setInt(1, perwalian.getId());
            st.executeUpdate();
            toReturn = true;
        } catch (SQLException sQLException) {
            toReturn = false;
            SplashLauncher.logger.log(Level.SEVERE, null, sQLException);
        }
        return toReturn;
    }
}
