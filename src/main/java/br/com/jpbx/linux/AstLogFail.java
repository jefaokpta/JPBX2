/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.linux;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class AstLogFail {
    
    private Process proc;
    private InputStream stdout;
    
    public void logfail(){
        try {      
            String ls_str; 
            FileWriter fw=new FileWriter(new File("/tmp/syslog"));
            proc = Runtime.getRuntime().exec(new LinuxInfo().commandPath("tail")+ " -n1000 /var/log/syslog");
            BufferedReader bf=new BufferedReader(
                    new InputStreamReader(proc.getInputStream()));            
            while ((ls_str = bf.readLine()) != null) {
                fw.write(ls_str+"\n");
            }
            fw.close();
            proc=Runtime.getRuntime().exec(new LinuxInfo().commandPath("tail")+ " -n1000 /var/log/asterisk/full");
            fw=new FileWriter(new File("/tmp/full"));
            bf=new BufferedReader(
                    new InputStreamReader(proc.getInputStream()));            
            while ((ls_str = bf.readLine()) != null) {
                fw.write(ls_str+"\n");
            }
            fw.close();
            Runtime.getRuntime().exec(new LinuxInfo().commandPath("tar")+ " -cf /tmp/failLog.tar /tmp/syslog /tmp/full");
        } catch (IOException ex) {
            System.out.println("DEU RUIM NO LOG DA FALHA DO AST VIXXX: "+ex.getMessage());
        }
    }
    public void killAllAst(){
        try {
            Runtime.getRuntime().exec(new LinuxInfo().commandPath("killall")+ " -KILL asterisk");
        } catch (IOException ex) {
            System.out.println("DEU RUIM AO KILLAR AST VIXXX: "+ex.getMessage());
        }
    }
    public void startAst(){
        try {
            Runtime.getRuntime().exec("/etc/init.d/asterisk restart");
            stdout=proc.getInputStream();
            while (stdout.read()>=0){}
        } catch (IOException ex) {
            System.out.println("DEU RUIM AO INICIAR AST VIXXX: "+ex.getMessage());
        }
    }

}
