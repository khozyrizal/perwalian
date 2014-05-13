/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adi.thinkoop.perwalianonline.controller;

import com.adi.thinkoop.perwalianonline.implement.ProgramImpl;
import com.adi.thinkoop.perwalianonline.interfaces.ProgramInterface;
import com.adi.thinkoop.perwalianonline.model.Program;
import com.adi.thinkoop.perwalianonline.utils.DataHolder;
import java.sql.SQLException;
import java.util.Map;

/**
 *
 * @author adi nuralim adinuralim@gmail.com
 * @since 03-Mar-2013
 */
public class ProgramController {

    private ProgramInterface programInterface;
    private static ProgramController programController;

    public static ProgramController getInstance() {
        if (programController == null) {
            programController = new ProgramController();
        }

        return programController;
    }

    public ProgramController() {
        programInterface = new ProgramImpl();
    }

    public Program getProgram(String program) {
        return DataHolder.getInstance().getProgramMaps().get(program);
    }

    public Map getAll() throws SQLException {
        return programInterface.getAll();
    }
}
