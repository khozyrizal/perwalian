/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adi.thinkoop.perwalianonline.implement;

import com.adi.thinkoop.perwalianonline.interfaces.DosenWaliInterface;
import com.adi.thinkoop.perwalianonline.model.DosenWali;
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
public class DosenWaliImpl implements DosenWaliInterface {

    @Override
    public Map getAll() throws SQLException {
        Statement st = (Statement) DatabaseUtils.getConnection().createStatement();
        ResultSet rs = st.executeQuery("select * from dosen_wali");
        Map<String, DosenWali> dosenWaliMap = new HashMap<>();
        while (rs.next()) {
            DosenWali dosenWali = new DosenWali();
            dosenWali.setId(rs.getInt("id"));
            dosenWali.setNama(rs.getString("nama"));

            dosenWaliMap.put(dosenWali.getNama(), dosenWali);
        }
        return dosenWaliMap;
    }
}
