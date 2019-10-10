/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.linux;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
public class LinuxInfo {
    public String commandPath(String command){
        String ls_str,ret = command;
        Process ls_proc;
        try {
            ls_proc = Runtime.getRuntime().exec("/usr/bin/which "+command);
            // get its output (your input) stream  
            BufferedReader bf=new BufferedReader(
                    new InputStreamReader(ls_proc.getInputStream()));
            if ((ls_str = bf.readLine()) != null) {
                ret=ls_str;
            }
        } catch (IOException ex) {
            ret=command;
        }
        return ret;
    }
    public String hwKey(){
        Process ls_proc;
        try {
            ls_proc = Runtime.getRuntime().exec(commandPath("dmidecode"));
            BufferedReader bf=new BufferedReader(
                    new InputStreamReader(ls_proc.getInputStream()));
            String ls_str; 
            while ((ls_str = bf.readLine()) != null) {
                if(ls_str.contains("UUID"))
                    return ls_str.split(":")[1].trim();
            }
        } catch (IOException ex) {
            System.out.println("DEU RUIM AO PEGAR UUID DA PARTICAO: "+ex.getMessage());
        }
        return "SEM_UUID";
    }
//    public String hwKey(){
//        Process ls_proc;
//        String partition=getPartition();
//        try {
//            ls_proc = Runtime.getRuntime().exec(commandPath("blkid"));
//            BufferedReader bf=new BufferedReader(
//                    new InputStreamReader(ls_proc.getInputStream()));
//            String ls_str; 
//            while ((ls_str = bf.readLine()) != null) {
//                if(ls_str.contains(partition))
//                    return ls_str.substring(ls_str.indexOf("UUID")).split("\"")[1];
//            }
//        } catch (IOException ex) {
//            System.out.println("DEU RUIM AO PEGAR UUID DA PARTICAO: "+ex.getMessage());
//        }
//        return "SEM_UUID";
//    }
    String getPartition(){ // TRAZ A PARTICAO DO /  EX: /DEV/SDA
        Process ls_proc;
        try {
            ls_proc = Runtime.getRuntime().exec(commandPath("df"));
            BufferedReader bf=new BufferedReader(
                    new InputStreamReader(ls_proc.getInputStream()));
            String ls_str; 
            while ((ls_str = bf.readLine()) != null) {
                String[] line=ls_str.split(" ");
                for(String c:line){
                    if(c.equals("/")){
                        return line[0];
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("DEU RUIM AO PEGAR PARTICAO DO SISTEMA: "+ex.getMessage());
        }    
        return "NUM_ACHEI";
    }
    public void executeSox(String filename){
        Process proc;
        try {
            proc=Runtime.getRuntime().exec(commandPath("sox")+" /tmp/"+filename+".wav -t raw -r 8000 -c 1 /tmp/"+filename+".sln");
            InputStream stdout=proc.getInputStream();
            while (stdout.read()>=0){}
        } catch (IOException ex) {
            System.out.println("::::: DEU RUIM SOX: "+ex.getMessage());
        }
    }
    public void executeMPG123(String filename){
        Process proc;
        try {
            proc=Runtime.getRuntime().exec(commandPath("mpg123")+" -w /tmp/"+filename+".wav /tmp/"+filename+".mp3");
            InputStream stdout=proc.getInputStream();
            while (stdout.read()>=0){}
        } catch (IOException ex) {
            System.out.println("::::: DEU RUIM MPG123 "+ex.getMessage());
        }
    }
    public void createMohTree(String mohName,String ext){
        File dst=new File("/etc/asterisk/jpbx/moh/"+mohName);
        if(!dst.exists())
            dst.mkdir();
        dst=new File("/etc/asterisk/jpbx/moh/"+mohName+"/"+mohName+"."+ext);
        File src=new File("/tmp/"+mohName+"."+ext);
        src.renameTo(dst);
        src=new File("/tmp/"+mohName+".sln");
        dst=new File("/etc/asterisk/jpbx/moh/"+mohName+"/"+mohName+".sln");
        src.renameTo(dst);
    }
    public List<Long> getHDUsage(){
        List<Long> ret=new ArrayList();
        long total=9,used=9;
        try {
            Process ls_proc = Runtime.getRuntime().exec(commandPath("df"));
            // get its output (your input) stream  
            BufferedReader bf=new BufferedReader(
                    new InputStreamReader(ls_proc.getInputStream()));
            String ls_str;         
            while ((ls_str = bf.readLine()) != null) {
                String seek[]=ls_str.split(" ");
                if(seek[seek.length-1].equals("/")){
                    for(String l:seek){
                        try{
                            total=Integer.parseInt(l);
                            break;
                        }catch(NumberFormatException ex){
                            total=9;
                        }
                    }
                    for(String l:seek){
                        try{
                            //System.out.println(l+" e "+total);
                            if(total!=Integer.parseInt(l)){
                                used=Integer.parseInt(l);
                                break;
                            }
                        }catch(NumberFormatException ex){
                            used=9;
                        }
                    }
                    break;
                }              
            }
            ret.add(total);
            ret.add(used);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return ret;
    }
    public List<Integer> getRAMUsage(){
        List<Integer> ret=new ArrayList();
        int total = 0,used = 0,cache=0;
        try {
            Process ls_proc = Runtime.getRuntime().exec(commandPath("free")+" -m");
            // get its output (your input) stream  
            BufferedReader bf=new BufferedReader(
                    new InputStreamReader(ls_proc.getInputStream()));
            String ls_str;         
            while ((ls_str = bf.readLine()) != null) {
                if(ls_str.contains("Mem:")){
                    //System.out.println(ls_str);
                    String seek[]=ls_str.split(" ");
                    for(String l:seek){
                        try{
                            total=Integer.parseInt(l);
                            break;
                        }catch(NumberFormatException ex){
                            total=0;
                        }
                    }
                    for(String l:seek){
                        try{
                            //System.out.println(l+" e "+total);
                            if(total!=Integer.parseInt(l)){
                                used=Integer.parseInt(l);
                                break;
                            }
                        }catch(NumberFormatException ex){
                            used=0;
                        }
                    }
                    try{
                        cache=Integer.parseInt(seek[seek.length-1]);
                    }catch(NumberFormatException ex){
                        cache=0;
                    }
                    break;                  
                }                
            }
            //System.out.println(total+" "+used+" "+cache); 
            ret.add(total);
            ret.add(used);
            ret.add(cache);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return ret;
    }
    public int getCPUCores(){
        int cpus=2;
        try {
            Process ls_proc = Runtime.getRuntime().exec(commandPath("lscpu"));
            // get its output (your input) stream  
            BufferedReader bf=new BufferedReader(
                    new InputStreamReader(ls_proc.getInputStream()));
            String ls_str;
            while ((ls_str = bf.readLine()) != null) {
                if(ls_str.contains("CPU(s)")){
                    String seek[]=ls_str.split(" ");
                    for(String l:seek){
                        try{
                            cpus=Integer.parseInt(l);
                            break;
                        }catch(NumberFormatException ex){
                            cpus=2;
                        }
                    }
                    break;
                }
                    
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return cpus;
    }
    public float getCPUPercent(){
        List<String> list=new ArrayList();
        try {
            Process ls_proc = Runtime.getRuntime().exec(commandPath("ps")+" aux");
            // get its output (your input) stream  
            BufferedReader bf=new BufferedReader(
                    new InputStreamReader(ls_proc.getInputStream()));
            String ls_str;           
            while ((ls_str = bf.readLine()) != null) {
                list.add(ls_str);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        String q[];
        float sum=0;
        for (String list1 : list) {
            q = list1.split(" ");
            for(String per:q)
                if(per.contains(".")){
                    try{
                        sum+=Float.parseFloat(per);
                    }catch(NumberFormatException ex){
                        break;
                    }
                    break;
                }
        }
        return Math.round(sum/getCPUCores());
    }
}
