/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adi.thinkoop.perwalianonline.implement;

import com.adi.thinkoop.perwalianonline.controller.MatakuliahController;
import com.adi.thinkoop.perwalianonline.interfaces.DetailMatakuliahiInterface;
import com.adi.thinkoop.perwalianonline.model.DetailMatakuliah;
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
public class DetailMatakuliahImpl implements DetailMatakuliahiInterface {

    @Override
    public Map getAll() throws SQLException {
        Statement st = (Statement) DatabaseUtils.getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT dmk.*,mk.kode_mk "
                + "FROM detail_matakuliah dmk, matakuliah mk "
                + "WHERE dmk.matakuliah_id=mk.id");
        Map<Integer, DetailMatakuliah> detailMatakulihMap = new HashMap<>();
        while (rs.next()) {
            DetailMatakuliah detailMatakuliah = new DetailMatakuliah();
            detailMatakuliah.setId(rs.getInt("id"));
            detailMatakuliah.setDosen(rs.getString("dosen"));
            detailMatakuliah.setKapasitas(rs.getInt("kapasitas"));
            detailMatakuliah.setKelas(rs.getString("kelas"));
            detailMatakuliah.setMatakuliah(MatakuliahController.getInstance().getMatakuliah(rs.getString("kode_mk")));
            detailMatakulihMap.put(detailMatakuliah.getId(), detailMatakuliah);
        }
        return detailMatakulihMap;
    }
}
