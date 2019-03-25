/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.linux;

import br.com.jpbx.util.MD5Factory;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
public class LinuxInfoTest {
    
    
    @Test
    public void testGetCPUPercent() {
        System.out.println("NUCLEOS DA CPU "+new LinuxInfo().getCPUCores());
        System.out.println("USO ATUAL DA CPU "+new LinuxInfo().getCPUPercent()+"%");
    }
    
    //@Test
    public void testLerArq(){
        String line,cut;
        List<String> ips=new ArrayList<>();
        try {
            BufferedReader  br = new BufferedReader(new FileReader("/tmp/block"));
            while(br.ready()){  // POE SOMENTE IPS
                line = br.readLine();  
                if(line.contains("Wrong password")||line.contains("matching peer")){
                    cut=line.substring(line.indexOf("failed"));
                    ips.add(cut.substring(cut.indexOf("'")+1, cut.indexOf(":")));
                    //System.out.println("register "+cut.substring(cut.indexOf("'")+1, cut.indexOf(":")));
                    //System.out.println(line.split("([0-9]{1,3}\\.){3}[0-9]{1,3}")[1]);  
                continue;
                }
                if(line.contains("tried to authenticate")){
                    cut=line.split(" ")[5];
                    if(!cut.matches("([0-9]{1,3}\\.){3}[0-9]{1,3}"))
                        cut=line.split(" ")[6];
                        ips.add(cut);
                    //System.out.println("manager "+cut);
                }
            }
            br.close();
            FileWriter out=new FileWriter(new File("/tmp/blockIps")); // ARQ DE IPS PRA SER CONTADO AS OCORRENCIAS
            for (String ip : ips) {
                out.write(ip+"\n");
            }
            out.close();
            String[] cmd=new String[3];
            cmd[0]="/bin/bash";
            cmd[1]="-c";
            cmd[2]="cat /tmp/blockIps | sort | uniq -c";
            Process ls_proc=Runtime.getRuntime().exec(cmd);
            BufferedReader ls_in = new BufferedReader(new InputStreamReader(ls_proc.getInputStream()));
            String str;
            while ((str = ls_in.readLine()) != null) {
                System.out.println(str.trim());
                if(Integer.parseInt(str.trim().split(" ")[0])>2)
                    System.out.println("BLOQUEIA O DE CIMA");
            }
            
        } catch (FileNotFoundException ex) {
            System.out.println("ops : "+ex.getMessage());
        } catch (IOException ex) {
            System.out.println("ops : "+ex.getMessage());
        }
    }
    //@Test
    public void testGetRAMUsage() {
        List list=new LinuxInfo().getRAMUsage();
        
    }
    //@Test
    public void testGetHDUsage() {
        List<Long> list=new LinuxInfo().getHDUsage();
        for(long line:list)
            System.out.println(line);
    }
    
}
