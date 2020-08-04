/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.linux;

import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * @author jefaokpta <jefferson@jpbx.com.br>
 */
public class HandleIptable {

    public void blockIpTable(String ip,String mask){
        Process proc;
        try {
            proc=Runtime.getRuntime().exec(new LinuxInfo().commandPath("iptables")+ " -I INPUT -s "+ip+"/"+mask+" -j DROP");
            InputStream stdout=proc.getInputStream();
            while (stdout.read()>=0){}
        } catch (IOException ex) {
            System.out.println("::::: DEU RUIM BLOQUEIO IPTABLES: "+ex.getMessage());
        }
    }
    
    public void releaseIp(String ip){
        Process proc;
        try {
            proc=Runtime.getRuntime().exec(new LinuxInfo().commandPath("iptables")+ " -D INPUT -s "+ip+" -j DROP");
            InputStream stdout=proc.getInputStream();
            while (stdout.read()>=0){}
        } catch (IOException ex) {
            System.out.println("::::: DEU RUIM LIBERAR IP: "+ex.getMessage());
        }
    }
    
    public void clearIpTable(){
        Process proc;
        try {
            proc=Runtime.getRuntime().exec(new LinuxInfo().commandPath("iptables")+ " -F INPUT");
            InputStream stdout=proc.getInputStream();
            while (stdout.read()>=0){}
        } catch (IOException ex) {
            System.out.println("::::: DEU RUIM LIMPEZA IPTABLES: "+ex.getMessage());
        }
    }
}
