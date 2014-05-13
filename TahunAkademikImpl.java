/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adi.thinkoop.perwalianonline.implement;

import com.adi.thinkoop.perwalianonline.interfaces.TahunAkademikInterface;
import com.adi.thinkoop.perwalianonline.model.TahunAkademik;
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
public class TahunAkademikImpl implements TahunAkademikInterface {

    @Override
    public Map getAll() throws SQLException {
        Statement st = (Statement) DatabaseUtils.getConnection().createStatement();
        ResultSet rs = st.executeQuery("select * from tahun_akademik");
        Map<String, TahunAkademik> tahunAkademikMap = new HashMap<>();
        while (rs.next()) {
            TahunAkademik tahunAkademik = new TahunAkademik();
            tahunAkademik.setId(rs.getInt("id"));
            tahunAkademik.setTahun(rs.getString("tahun"));
            tahunAkademik.setSemester(rs.getString("semester"));
            tahunAkademikMap.put(tahunAkademik.getTahun(), tahunAkademik);
        }
        return tahunAkademikMap;
    }
}
