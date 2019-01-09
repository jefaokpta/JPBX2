/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.util;

import br.com.jpbx.linux.LinuxInfo;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.primefaces.model.UploadedFile;

/**
 * 
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
public class HandleAudio {
    public String newMoh(UploadedFile audio,String mohName){
        FileOutputStream out = null;
        String ext="wav";
        try{
            if(audio.getContentType().equals("audio/mpeg"))
                ext="mp3";
            out=new FileOutputStream(new File("/tmp/"+mohName+"."+ext));
            out.write(audio.getContents(), 0, (int) audio.getSize());
            
        }catch (IOException ex) {
            return "FALHA NA ESCRITA DO AUDIO: "+ex.getMessage();
        }finally{
            try {
                out.flush();
                out.close();
            } catch (IOException ex) {
                return "FALHA NA ESCRITA DO AUDIO (CLOSE): "+ex.getMessage();
            }    
        }
        File moh;
        switch(ext){
            case "mp3":
                new LinuxInfo().executeMPG123(mohName);
                moh=new File("/tmp/"+mohName+".wav");
                if(!moh.exists())
                    return "FALHA NA CONVERSÃO (MPG123)";
                new LinuxInfo().executeSox(mohName);
                moh=new File("/tmp/"+mohName+".sln");
                if(!moh.exists())
                    return "FALHA NA CONVERSÃO (SOX) mp3";
                new LinuxInfo().createMohTree(mohName, ext);
                return "ok";
            case "wav":
                new LinuxInfo().executeSox(mohName);
                moh=new File("/tmp/"+mohName+".sln");
                if(!moh.exists())
                    return "FALHA NA CONVERSÃO (SOX) wav";
                new LinuxInfo().createMohTree(mohName, ext);
                return "ok";
            default:
                return "CONTEÚDO DO ARQUIVO INDETERMINADO";
        }
    }
    public String removeVoicemailGreeting(int peer){
        File file=new File("/var/spool/asterisk/voicemail/default/"+peer+"/unavail.sln");
        if(!file.exists())
            return "Não há Saudação personalizada.";
        if(!file.delete())
            return "Falha em apagar saudação";
        return "ok";
    }
    public void removeConference(String rec){
        File file=new File("/etc/asterisk/jpbx/records/"+rec+".wav");
        if(!file.exists())
            file.delete();
    }
    public String removeMoh(String moh){
        File file=new File("/etc/asterisk/jpbx/moh/"+moh);
        if(file.exists()){
            for(String arq:file.list()){
                if(!new File(file, arq).delete())
                    return "Falha em apagar Música";            
            }
            if(!file.delete())
                return "Falha em apagar pasta da Música"; 
        }
        return "ok";
    }
    
    public String saveAudioForConvert(UploadedFile audio,int peer){
        String res="ok";
        FileOutputStream out = null;
        String ext="wav";
        if(audio.getContentType().equals("audio/mpeg"))
            ext="mp3";      
        try{
            out=new FileOutputStream(new File("/tmp/voicemail"+peer+"."+ext));
            out.write(audio.getContents(), 0, (int) audio.getSize());
        }catch(FileNotFoundException ex){
            return "FALHA NA ESCRITA DO AUDIO: "+ex.getMessage();
        } catch (IOException ex) {
            return "FALHA NA ESCRITA DO AUDIO: "+ex.getMessage();
        }finally{
            try {
                out.flush();
                out.close();
            } catch (IOException ex) {
                return "FALHA NA ESCRITA DO AUDIO (CLOSE): "+ex.getMessage();
            }         
        }
        File vm;
        switch(ext){
            case "mp3":
                new LinuxInfo().executeMPG123("voicemail"+peer);
                vm=new File("/tmp/"+"voicemail"+peer+".wav");
                if(!vm.exists())
                    return "FALHA NA CONVERSÃO (MPG123)";
                new LinuxInfo().executeSox("voicemail"+peer);
                vm=new File("/tmp/"+"voicemail"+peer+".sln");
                if(!vm.exists())
                    return "FALHA NA CONVERSÃO (SOX) mp3";
                break;
            case "wav":
                new LinuxInfo().executeSox("voicemail"+peer);
                vm=new File("/tmp/"+"voicemail"+peer+".sln");
                if(!vm.exists())
                    return "FALHA NA CONVERSÃO (SOX) wav";
                break;
            default:
                return "CONTEÚDO DO ARQUIVO INDETERMINADO";
        }
        
        File dst=new File("/var/spool/asterisk/voicemail/default");
        if(!dst.exists())
            dst.mkdir();
        dst=new File("/var/spool/asterisk/voicemail/default/"+peer);
        if(!dst.exists())
            dst.mkdir();
        dst=new File("/var/spool/asterisk/voicemail/default/"+peer+"/unavail.sln");
        File src=new File("/tmp/voicemail"+peer+".sln");
        src.renameTo(dst);
        return res;
    }
    
}
