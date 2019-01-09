/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
public class ResolvDNS {

    public String getIpAddress(String dns){
        try {
            InetAddress address=InetAddress.getByName(dns);
            return address.getHostAddress();
        } catch (UnknownHostException ex) {
            return "Ops. "+ex.getMessage();
        }
    }
    public String getIpAddress(String dns,String currentIP){
        try {
            InetAddress address=InetAddress.getByName(dns);
            return address.getHostAddress();
        } catch (UnknownHostException ex) {
            return currentIP;
        }
    }
}
