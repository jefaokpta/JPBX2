/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
public class Ping {

    public String doPing(String host){
        InetAddress address;
        try {
            address=InetAddress.getByName(host);           
        } catch (UnknownHostException ex) {
            return "FALHA: "+ex.getMessage();
        }
        try {
            if (address.isReachable(5000)) {
              long nanos = 0;
              long millis = 0;

                try {
                  nanos = System.nanoTime();
                  address.isReachable(500); // this invocation is the offender
                  nanos = System.nanoTime()-nanos;
                } catch (IOException ex) {
                  return "Inalcançável: "+ex.getMessage();
                }
                millis = Math.round(nanos/Math.pow(10,6));
                return "Host: "+address.getHostAddress()+" respondendo em "+millis+" ms";
            } 
            else
              return "Host não parece estar alcançável.";
        } catch (IOException | NullPointerException ex) {
          return "Rede inexistente: "+ex.getMessage();
        }
    }
}
