/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.asterisk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.TimeoutException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
public class AsteriskTest {
    
//    //@Test
//    public void testGetInfos() throws Exception {
//        
//        List<SipShowPeer> ssps=new ArrayList<>();
//        Asterisk ast=new Asterisk("192.168.1.129", "jpbx", "jpbxadmin");
//        String[] line;
//        String qualify="";
//        for (String str : ast.getInfos("sip show peers")) {
//            try{
//                line=str.replaceAll("\\s+", " ").split(" ");
//                if(String.valueOf(line[6].charAt(0)).matches("[A-Z]")){                   
//                    for (int i = 6; i < line.length; i++)
//                       qualify+=line[i];
//                }
//                else{
//                    for (int i = 7; i < line.length; i++)
//                       qualify+=line[i];
//                }
//               // ssps.add(new SipShowPeer(line[0].split("/")[0], line[1], line[2], line[3],qualify));
//                qualify="";
//                    
//            } catch(NumberFormatException | IndexOutOfBoundsException ex){}
//        }   
//        for (SipShowPeer sp : ssps) {
//            System.out.println(sp.getName()+" ,"+sp.getHost()+" ,"+sp.getForcerPort()+", "+sp.getQualify());
//        }
//    }
//    //@Test
//    public void testGetVar(){
//        try {
//            System.out.println("RESULTADO "+ new Asterisk("192.168.1.54", "jpbx", "jpbxadmin").getVar("GROUP_COUNT(route5)"));
//        } catch (IOException ex) {
//            Logger.getLogger(AsteriskTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    //@Test
//    public void testShowChannels(){
//        try {
//            for (String s : new Asterisk("192.168.1.54", "jpbx", "jpbxadmin").getInfos("core show channels verbose")) {
//                System.out.println(s);
//                if(s.contains("Channel")||s.contains("active")||s.contains("calls"))
//                    continue;
//                String[] line=s.replaceAll("\\s+", " ").split(" ");
//                String t=line[8].matches("^[0-9]{2}:[0-9]{2}:[0-9]{2}$")?line[8]:line[9];
//                System.out.println(line[0]+" "+line[4]+" "+t);
//            }
//        } catch (IOException ex) {
//            System.out.println("DEU RUIM TESTE "+ex.getMessage());
//        }
//    }
//    
    
}
