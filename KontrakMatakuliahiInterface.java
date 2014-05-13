/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adi.thinkoop.perwalianonline.interfaces;

import com.adi.thinkoop.perwalianonline.model.KontrakMatakuliah;
import java.sql.SQLException;
import java.util.Map;

/**
 *
 * @author adi nuralim
 */
public interface KontrakMatakuliahiInterface {

    Map getAll() throws SQLException;

    boolean update(KontrakMatakuliah kontrakMatakuliah) throws SQLException;

    int getIdKontrak(int mId, int pId) throws SQLException;
}
