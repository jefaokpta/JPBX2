/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.asterisk;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class AsteriskAuthentication {
    
    private static String ip;
    private static int port;
    private static String user;
    private static String password;
    private static String userEvent;
    private static String passwordEvent;

    public AsteriskAuthentication() {
        ip="localhost";
        port=5038;
        user="jpbx";
        password="jpbxadmin";
        userEvent="jpbxEvents";
        passwordEvent="myJPBXEvents";
    }

    public  String getUserEvent() {
        return userEvent;
    }

    public  String getPasswordEvent() {
        return passwordEvent;
    }
    
    

    public  String getIp() {
        return ip;
    }

    public  int getPort() {
        return port;
    }

    public  String getUser() {
        return user;
    }

    public  String getPassword() {
        return password;
    }
    
    

}
