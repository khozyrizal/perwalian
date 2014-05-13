/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adi.thinkoop.perwalianonline.implement;

import com.adi.thinkoop.perwalianonline.interfaces.LoginInterface;
import com.adi.thinkoop.perwalianonline.model.Login;
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
public class LoginImpl implements LoginInterface {

    @Override
    public Map getAll() throws SQLException {
        Statement st = (Statement) DatabaseUtils.getConnection().createStatement();
        ResultSet rs = st.executeQuery("select * from login");
        Map<String, Login> loginMap = new HashMap<>();
        while (rs.next()) {
            Login login = new Login();
            login.setId(rs.getInt("id"));
            login.setUsername(rs.getString("username"));
            login.setPassword(rs.getString("password"));
            login.setRole(rs.getByte("role"));
            loginMap.put(login.getUsername(), login);
        }
        return loginMap;
    }
}
