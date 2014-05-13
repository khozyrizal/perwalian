/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adi.thinkoop.perwalianonline.interfaces;

import com.adi.thinkoop.perwalianonline.model.Pesan;
import java.sql.SQLException;
import java.util.Map;

/**
 *
 * @author adi nuralim
 */
public interface PesanInterface {

    Map getAll() throws SQLException;
    boolean kirimPesan(Pesan pesan) throws SQLException;
    boolean hapusPesan(Pesan pesan) throws SQLException;
    boolean updatePesan(Pesan pesan) throws SQLException;
}
