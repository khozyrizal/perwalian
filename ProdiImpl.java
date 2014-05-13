/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adi.thinkoop.perwalianonline.implement;

import com.adi.thinkoop.perwalianonline.interfaces.ProdiInterface;
import com.adi.thinkoop.perwalianonline.model.Prodi;
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
public class ProdiImpl implements ProdiInterface {

    @Override
    public Map getAll() throws SQLException {
        Statement st = (Statement) DatabaseUtils.getConnection().createStatement();
        ResultSet rs = st.executeQuery("select * from prodi");
        Map<String, Prodi> prodiMap = new HashMap<>();
        while (rs.next()) {
            Prodi prodi = new Prodi();
            prodi.setId(rs.getInt("id"));
            prodi.setProdi(rs.getString("prodi"));
            prodiMap.put(prodi.getProdi(), prodi);
        }
        return prodiMap;
    }
}
