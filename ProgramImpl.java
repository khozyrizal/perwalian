/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adi.thinkoop.perwalianonline.implement;

import com.adi.thinkoop.perwalianonline.interfaces.ProgramInterface;
import com.adi.thinkoop.perwalianonline.model.Program;
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
public class ProgramImpl implements ProgramInterface {

    @Override
    public Map getAll() throws SQLException {
        Statement st = (Statement) DatabaseUtils.getConnection().createStatement();
        ResultSet rs = st.executeQuery("select * from program");
        Map<String, Program> programMap = new HashMap<>();
        while (rs.next()) {
            Program program = new Program();
            program.setId(rs.getInt("id"));
            program.setProgram(rs.getString("program"));

            programMap.put(program.getProgram(), program);
        }
        return programMap;
    }
}
