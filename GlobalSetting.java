/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adi.thinkoop.perwalianonline.utils;

/**
 * @author adi nuralim adinuralim@gmail.com
 * @since 03-Mar-2013
 */
public class GlobalSetting {

    public static final String DATA_PATH = System.getProperty("user.home") + System.getProperty("file.separator") + "perwalian_online" + System.getProperty("file.separator");
    public static final String LOG_PATH = DATA_PATH + "logs" + System.getProperty("file.separator");
    public static final String CONFIG_PATH = DATA_PATH + "configs" + System.getProperty("file.separator");
}
