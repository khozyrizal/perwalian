/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adi.thinkoop.perwalianonline.interfaces;

import com.adi.thinkoop.perwalianonline.model.Perwalian;
import java.sql.SQLException;
import java.util.Map;

/**
 *
 * @author adi nuralim
 */
public interface PerwalianInterface {

    Map getAll() throws SQLException;

    boolean simpanPerwalian(Perwalian perwalian) throws SQLException;
    boolean hapusPerwalian(Perwalian perwalian) throws SQLException;
}
