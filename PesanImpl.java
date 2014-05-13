/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adi.thinkoop.perwalianonline.implement;

import com.adi.thinkoop.perwalianonline.controller.DosenWaliController;
import com.adi.thinkoop.perwalianonline.controller.MahasiswaController;
import com.adi.thinkoop.perwalianonline.interfaces.PesanInterface;
import com.adi.thinkoop.perwalianonline.launcher.SplashLauncher;
import com.adi.thinkoop.perwalianonline.model.DosenWali;
import com.adi.thinkoop.perwalianonline.model.Mahasiswa;
import com.adi.thinkoop.perwalianonline.model.Pesan;
import com.adi.thinkoop.perwalianonline.utils.DataHolder;
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
public class PesanImpl implements PesanInterface {

    @Override
    public Map getAll() throws SQLException {
        Statement st = (Statement) DatabaseUtils.getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT p.id, m.npm, d.nama, p.pesan, p.pengirim,p.waktu,p.status "
                + "FROM pesan p, mahasiswa m, dosen_wali d "
                + "WHERE p.mahasiswa_id = m.id AND p.dosen_wali_id = d.id");
        Map<Integer, Pesan> pesanMap = new HashMap<>();
        while (rs.next()) {
            Pesan pesan = new Pesan();
            pesan.setId(rs.getInt("id"));
            Mahasiswa mahasiswa = MahasiswaController.getInstance().getMahasiswa(rs.getString("npm"));
            pesan.setMahasiswa(mahasiswa);
            DosenWali dosenWali = DosenWaliController.getInstance().getDosenWali(rs.getString("nama"));
            pesan.setDosenWali(dosenWali);
            pesan.setPesan(rs.getString("pesan"));
            pesan.setPengirim(rs.getBoolean("pengirim"));
            pesan.setWaktu(rs.getString("waktu"));
            pesan.setStatus(rs.getBoolean("status"));
            mahasiswa.getPesans().put(pesan.getId(), pesan);
            DataHolder.getInstance().getMahasiswaMaps().put(mahasiswa.getNpm(), mahasiswa);
            pesanMap.put(pesan.getId(), pesan);
            DataHolder.getInstance().getPesanCariMaps().put(pesan.getWaktu(), pesan);
        }
        return pesanMap;
    }

    @Override
    public boolean kirimPesan(Pesan pesan) throws SQLException {

        boolean toReturn;
        try {
            String query = "insert into pesan values (?,?,?,?,?,?,?)";
            PreparedStatement st = (PreparedStatement) DatabaseUtils.getConnection().prepareStatement(query);
            st.setInt(1, pesan.getId());
            st.setInt(2, pesan.getMahasiswa().getId());
            st.setInt(3, pesan.getDosenWali().getId());
            st.setString(4, pesan.getPesan());
            st.setBoolean(5, pesan.isPengirim());
            st.setString(6, pesan.getWaktu());
            st.setBoolean(7, pesan.isStatus());
            st.executeUpdate();
            toReturn = true;
        } catch (SQLException sQLException) {
            toReturn = false;
            SplashLauncher.logger.log(Level.SEVERE, null, sQLException);
        }
        return toReturn;
    }

    @Override
    public boolean hapusPesan(Pesan pesan) throws SQLException {
        boolean toReturn;
        try {
            PreparedStatement st = (PreparedStatement) DatabaseUtils.getConnection().prepareStatement("delete from pesan where id=?");
            st.setInt(1, pesan.getId());
            st.executeUpdate();
            toReturn = true;
        } catch (SQLException sQLException) {
            toReturn = false;
            SplashLauncher.logger.log(Level.SEVERE, null, sQLException);
        }
        return toReturn;
    }

    @Override
    public boolean updatePesan(Pesan pesan) throws SQLException {
        boolean toReturn;
        try {
            PreparedStatement st = (PreparedStatement) DatabaseUtils.getConnection().prepareStatement("update pesan set status=? where id=?");
            st.setBoolean(1, pesan.isStatus());
            st.setInt(2, pesan.getId());
            st.executeUpdate();
            toReturn = true;
        } catch (SQLException sQLException) {
            toReturn = false;
            SplashLauncher.logger.log(Level.SEVERE, null, sQLException);
        }
        return toReturn;
    }
}
