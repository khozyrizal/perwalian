/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adi.thinkoop.perwalianonline.implement;

import com.adi.thinkoop.perwalianonline.interfaces.MatakuliahiInterface;
import com.adi.thinkoop.perwalianonline.model.DetailMatakuliah;
import com.adi.thinkoop.perwalianonline.model.Matakuliah;
import com.adi.thinkoop.perwalianonline.utils.DataHolder;
import com.adi.thinkoop.perwalianonline.utils.DatabaseUtils;
import com.mysql.jdbc.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author adi nuralim adinuralim@gmail.com
 * @since 03-Mar-2013
 */
public class MatakuliahImpl implements MatakuliahiInterface {

    @Override
    public Map getAll() throws SQLException {
        Statement st = (Statement) DatabaseUtils.getConnection().createStatement();
        ResultSet rs = st.executeQuery("select * from matakuliah");
        Map<String, Matakuliah> matakuliahMap = new HashMap<>();
        while (rs.next()) {
            Matakuliah matakuliah = new Matakuliah();
            matakuliah.setId(rs.getInt("id"));
            matakuliah.setKodeMk(rs.getString("kode_mk"));
            matakuliah.setMatakuliah(rs.getString("matakuliah"));
            matakuliah.setSks(rs.getByte("sks"));


            Statement st1 = (Statement) DatabaseUtils.getConnection().createStatement();
            ResultSet rs1 = st1.executeQuery("SELECT dm.id, dm.kelas, dm.kapasitas, dm.dosen "
                    + "FROM matakuliah mk, detail_matakuliah dm "
                    + "WHERE mk.id = dm.matakuliah_id AND mk.id =" + matakuliah.getId() + "");

            while (rs1.next()) {
                DetailMatakuliah detailMatakuliah = new DetailMatakuliah();
                detailMatakuliah.setId(rs1.getInt("id"));
                detailMatakuliah.setKelas(rs1.getString("kelas"));
                detailMatakuliah.setKapasitas(rs1.getInt("kapasitas"));
                detailMatakuliah.setDosen(rs1.getString("dosen"));
                detailMatakuliah.setMatakuliah(matakuliah);
                DataHolder.getInstance().getDetailMatakuliahMaps().put(detailMatakuliah.getId(), detailMatakuliah);
                matakuliah.getDetailMatakuliahs().put(detailMatakuliah.getId(), detailMatakuliah);
            }
            matakuliahMap.put(matakuliah.getKodeMk(), matakuliah);
        }
        return matakuliahMap;
    }
}
