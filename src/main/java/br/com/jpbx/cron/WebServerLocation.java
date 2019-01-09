/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.cron;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class WebServerLocation {
    
    private static String address;
    private static String port;

    public WebServerLocation() {
        address="localhost";
        port="8080";
    }
    
    

    public String getAddress() {
        return address;
    }

    public String getPort() {
        return port;
    }

}
