package com.mediaymedia.gdata.service;

/**
 * User: juan
 * Date: 14-oct-2007
 * Time: 19:01:52
 */
public interface GoogleProperties {
     static String gmailExtension = "@gmail.com";
    static String seamName="googleProperties";

    String getUsuarioGmail();

    String getClave();

    String getGmailExtension();

    String getGmailCuenta();

    String getBlog();


    String logea();
}
