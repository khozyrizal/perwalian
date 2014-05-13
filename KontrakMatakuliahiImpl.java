/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adi.thinkoop.perwalianonline.implement;

import com.adi.thinkoop.perwalianonline.controller.*;
import com.adi.thinkoop.perwalianonline.interfaces.KontrakMatakuliahiInterface;
import com.adi.thinkoop.perwalianonline.launcher.SplashLauncher;
import com.adi.thinkoop.perwalianonline.model.*;
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
public class KontrakMatakuliahiImpl implements KontrakMatakuliahiInterface {

    @Override
    public Map getAll() throws SQLException {
        System.out.println("load all kontrak matakuliah");
        Statement st = (Statement) DatabaseUtils.getConnection().createStatement();


        ResultSet rs = st.executeQuery("SELECT kmk.id, kmk.kontrak, mk.id AS dmk_id, mtk.kode_mk AS kode_mk,m.npm "
                + "FROM kontrak_matakuliah kmk, detail_matakuliah mk, matakuliah mtk,mahasiswa m "
                + "WHERE kmk.detail_matakuliah_id = mk.id AND mtk.id = mk.matakuliah_id and m.id=kmk.mahasiswa_id ORDER BY kode_mk");

        Map<Integer, KontrakMatakuliah> kontrakMatakuliahMap = new HashMap<>();

        while (rs.next()) {

            KontrakMatakuliah kontrakMatakuliah = new KontrakMatakuliah();
            kontrakMatakuliah.setId(rs.getInt("id"));
            kontrakMatakuliah.setKontrak(rs.getBoolean("kontrak"));
            DetailMatakuliah detailMatakuliah = DetailMatakuliahController.getInstance().getDetailMatakuliah(rs.getInt("dmk_id"));
            kontrakMatakuliah.setDetailMatakuliah(detailMatakuliah);
            Mahasiswa mahasiswa = MahasiswaController.getInstance().getMahasiswa(rs.getString("npm"));
            kontrakMatakuliah.setMahasiswa(mahasiswa);
            Statement st1 = (Statement) DatabaseUtils.getConnection().createStatement();
            ResultSet rs1 = st1.executeQuery("SELECT p.id, m.npm, p.status, ta.tahun "
                    + "FROM kontrak_matakuliah kmk, perwalian p, mahasiswa m, tahun_akademik ta "
                    + "WHERE kmk.id = p.kontrak_matakuliah_id AND p.mahasiswa_id = m.id "
                    + "AND p.tahun_akademik_id = ta.id AND kmk.id =" + kontrakMatakuliah.getId() + "");

            while (rs1.next()) {
                Perwalian perwalian = new Perwalian();
                perwalian.setId(rs1.getInt("id"));
                perwalian.setMahasiswa(mahasiswa);
                perwalian.setKontrakMatakuliah(kontrakMatakuliah);
                perwalian.setTahunAkademik(TahunAkademikController.getInstance().getTahunAkademik(rs1.getString("tahun")));
                perwalian.setStatus(rs1.getBoolean("status"));

                kontrakMatakuliah.getPerwalians().add(perwalian);
                DataHolder.getInstance().getPerwalianMaps().put(perwalian.getId(), perwalian);

            }
            String keyKontrakMatakuliah = KontrakMatakuliahController.getInstance().getKey(kontrakMatakuliah);
            Matakuliah matakuliah = detailMatakuliah.getMatakuliah();
            detailMatakuliah.getKontrakMatakuliahs().add(kontrakMatakuliah);

            DataHolder.getInstance().getDetailMatakuliahMaps().put(detailMatakuliah.getId(), detailMatakuliah);
            DataHolder.getInstance().getMahasiswaMaps().put(mahasiswa.getNpm(), mahasiswa);

            matakuliah.getDetailMatakuliahs().put(detailMatakuliah.getId(), detailMatakuliah);
            mahasiswa.getKontrakMatakuliahs().put(keyKontrakMatakuliah, kontrakMatakuliah);
            kontrakMatakuliahMap.put(kontrakMatakuliah.getId(), kontrakMatakuliah);
            DataHolder.getInstance().getSearchkontrakMatakuliahMaps().put(keyKontrakMatakuliah, kontrakMatakuliah);


        }

        return kontrakMatakuliahMap;
    }

    @Override
    public boolean update(KontrakMatakuliah kontrakMatakuliah) throws SQLException {
        boolean toReturn;
        try {
            PreparedStatement st = (PreparedStatement) DatabaseUtils.getConnection().prepareStatement("update kontrak_matakuliah set kontrak=? where id=?");
            st.setBoolean(1, kontrakMatakuliah.isKontrak());
            st.setInt(2, kontrakMatakuliah.getId());
            st.executeUpdate();
            toReturn = true;
        } catch (SQLException sQLException) {
            toReturn = false;
            SplashLauncher.logger.log(Level.SEVERE, null, sQLException);
        }
        return toReturn;
    }

    @Override
    public int getIdKontrak(int mId, int pId) throws SQLException {

        int toReturn = 0;
        Statement st = (Statement) DatabaseUtils.getConnection().createStatement();


        ResultSet rs = st.executeQuery("SELECT kmk.id "
                + "FROM perwalian p, kontrak_matakuliah kmk, mahasiswa m, tahun_akademik ta "
                + "WHERE m.id = p.mahasiswa_id "
                + "AND kmk.id = p.kontrak_matakuliah_id "
                + "AND p.tahun_akademik_id = ta.id "
                + "AND m.id =" + mId + " AND p.id =" + pId + "");

        while (rs.next()) {
            toReturn = rs.getInt("id");
            break;
        }

        return toReturn;
    }
}
