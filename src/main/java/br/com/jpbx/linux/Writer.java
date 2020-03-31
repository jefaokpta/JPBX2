/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.linux;

import br.com.jpbx.model.Acl;
import br.com.jpbx.model.Moh;
import br.com.jpbx.model.Peer;
import br.com.jpbx.model.Profile;
import br.com.jpbx.model.Trunk;
import br.com.jpbx.model.Ura;
import br.com.jpbx.model.UraOption;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * 
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
public class Writer {
    
    public String writeTrunkSIP(List<Trunk> trunks){
        String res="ok";
        File makeFile=new File("/etc/asterisk/jpbx/sipTrunk.conf");       
        FileWriter fwrite = null;
        try {   
            if(!makeFile.exists())
                makeFile.createNewFile();
            fwrite=new FileWriter(makeFile);
            fwrite.write(";Arquivo escrito automaticamente pelo sistema JPBX\n\n");
            for(Trunk t: trunks) {
                if(t.getTecnology().equals("SIP")){
                    fwrite.write("["+t.getUsername()+"]\n");
                    fwrite.write("type="+t.getType()+"\n");
                    fwrite.write("defaultusername="+t.getUsername()+"\n");
                    fwrite.write("context=Jpbx-Trunks\n");
                    fwrite.write("host="+t.getHost()+"\n");
                    fwrite.write("secret="+t.getSecret()+"\n");
                    fwrite.write("language="+t.getLanguage()+"\n");
                    fwrite.write("canreinvite="+t.getCanReinvite()+"\n");
                    fwrite.write("reinvite="+t.getReinvite()+"\n");
                    fwrite.write("dtmfmode="+t.getDtmfMode()+"\n");
                    fwrite.write("nat="+t.getNat()+"\n");
                    fwrite.write("qualify="+t.getQualify()+"\n");
                    fwrite.write("insecure="+t.getInsecure()+"\n");
                    fwrite.write("disallow=all\n");
                    fwrite.write("allow="+t.getAllow()+"\n");
                    if(!t.getAllowVideo().equals(""))
                        fwrite.write("allow="+t.getAllowVideo()+"\n");
                    if(!t.getFromUser().equals(""))
                        fwrite.write("fromuser="+t.getFromUser()+"\n");
                    if(!t.getFromDomain().equals(""))
                        fwrite.write("fromdomain="+t.getFromDomain()+"\n");
                    fwrite.write("call-limit="+t.getCallLimit()+"\n");
                    fwrite.write(t.getAdvancedText()+"\n");
                    fwrite.write("\n");
                }
            }           
        }catch(IOException ex){
            res="Falha no WRITE Trunk SIP: "+ex.getMessage();
            return res;
        }finally{
            try {
                fwrite.flush();
                fwrite.close();
            } catch (IOException ex) {
                res="Falha no WRITE Trunk SIP: "+ex.getMessage();
                return res;
            }    
        }
        writeSIPRegisters(trunks);
        return res;
    }
    void writeSIPRegisters(List<Trunk> trunks){
        File makeFile=new File("/etc/asterisk/jpbx/sipRegisters.conf");       
        FileWriter fwrite = null;
        try {   
            if(!makeFile.exists())
                makeFile.createNewFile();
            fwrite=new FileWriter(makeFile);
            fwrite.write(";Arquivo escrito automaticamente pelo sistema JPBX\n\n");
            for(Trunk t: trunks) {
                if(t.getTecnology().equals("SIP")&&t.getReception()>0){
                    fwrite.write("register => "+t.getUsername()+":"+t.getSecret()+"@"+t.getHost()+
                            (t.getRegister().equals("")?"":"/"+t.getRegister()));
                    fwrite.write("\n");
                }
            }           
        }catch(IOException ex){
            System.out.println("Falha no WRITE Trunk SIP REGISTERS: "+ex.getMessage());
        }finally{
            try {
                fwrite.flush();
                fwrite.close();
            } catch (IOException ex) {
                System.out.println("Falha no WRITE Trunk SIP REGISTERS: "+ex.getMessage());
            }    
        }
    }
    public String writeTrunkIAX(List<Trunk> trunks){
        String res="ok";
        File makeFile=new File("/etc/asterisk/jpbx/iaxTrunk.conf");       
        FileWriter fwrite = null;
        try {   
            if(!makeFile.exists())
                makeFile.createNewFile();
            fwrite=new FileWriter(makeFile);
            fwrite.write(";Arquivo escrito automaticamente pelo sistema JPBX\n\n");
            for(Trunk t: trunks) {
                if(t.getTecnology().equals("IAX2")){
                    fwrite.write("["+t.getUsername()+"]\n");
                    fwrite.write("type="+t.getType()+"\n");
                    fwrite.write("username="+t.getUsername()+"\n");
                    fwrite.write("context=Jpbx-Trunks\n");
                    fwrite.write("host="+t.getHost()+"\n");
                    fwrite.write("secret="+t.getSecret()+"\n");
                    fwrite.write("language="+t.getLanguage()+"\n");
                    fwrite.write("dtmfmode="+t.getDtmfMode()+"\n");
                    fwrite.write("qualify="+t.getQualify()+"\n");
                    fwrite.write("disallow=all\n");
                    fwrite.write("allow="+t.getAllow()+"\n");
                    if(t.getIaxTrunk()>0)
                        fwrite.write("trunk=yes\n");
                    fwrite.write(t.getAdvancedText()+"\n");
                    fwrite.write("\n");
                }
            }           
        }catch(IOException ex){
            res="Falha no WRITE Trunk IAX: "+ex.getMessage();
            return res;
        }finally{
            try {
                fwrite.flush();
                fwrite.close();
            } catch (IOException ex) {
                res="Falha no WRITE Trunk IAX: "+ex.getMessage();
                return res;
            }    
        }
        return res;
    }
    public String writeProfile(List<Profile> profiles){
        String res="ok";
        File makeFile=new File("/etc/asterisk/jpbx/sipSecurity.conf");       
        FileWriter fwrite = null;
        try{
            if(!makeFile.exists())
                makeFile.createNewFile();
            fwrite=new FileWriter(makeFile); 
            fwrite.write(";Arquivo escrito automaticamente pelo sistema JPBX\n\n");
            for(Profile p : profiles) {
                fwrite.write("[security_"+p.getId()+"](!)\n");
                if(p.getAcls().size()>0){
                    fwrite.write("deny=0.0.0.0/0.0.0.0\n");
                    for(Acl a:p.getAcls())
                        fwrite.write("permit="+a.getIp()+"/"+a.getMask()+"\n");
                }
            }
        }catch(IOException ex){
            res= "FALHA NO WRITE Profiles: "+ex.getMessage();
        }finally{
            try{
                fwrite.flush();
                fwrite.close();
            }catch(IOException ex){
                res= "FALHA NO WRITE Profiles: "+ex.getMessage();
            }
        }
        return res;
    }
    public String writeVoicemail(List<Peer> peers){
        
        File makeFile=new File("/etc/asterisk/jpbx/voicemail.conf");       
        FileWriter fwrite = null;
        try{
            if(!makeFile.exists())
                makeFile.createNewFile();
            fwrite=new FileWriter(makeFile); 
            fwrite.write(";Arquivo escrito automaticamente pelo sistema JPBX\n\n");
            for(Peer p : peers) {
                if(p.getMailbox()>0)
                    fwrite.write(p.getName()+" => "+p.getMailbox()+","+p.getCallerid()+","+p.getEmail()+"\n");
            }
        }catch(IOException ex){
            return "FALHA NO WRITE Voicemail: "+ex.getMessage();
        }finally{
            try{
                fwrite.flush();
                fwrite.close();
            }catch(IOException ex){
                return "FALHA NO WRITE Voicemail: "+ex.getMessage();
            }
        }
        return "ok";
    }
    public String writeMohs(List<Moh> mohs){
        
        File makeFile=new File("/etc/asterisk/jpbx/mohClasses.conf");       
        FileWriter fwrite = null;
        try{
            if(!makeFile.exists())
                makeFile.createNewFile();
            fwrite=new FileWriter(makeFile); 
            fwrite.write(";Arquivo escrito automaticamente pelo sistema JPBX\n\n");
            for (Moh m : mohs) {
                fwrite.write("["+m.getMoh()+"]\n");
                fwrite.write("mode=files\n");
                fwrite.write("directory=moh/"+m.getMoh()+"\n\n");
            }
        }catch(IOException ex){
            return "FALHA NO WRITE MOH: "+ex.getMessage();
        }finally{
            try{
                fwrite.flush();
                fwrite.close();
            }catch(IOException ex){
                return "FALHA NO WRITE MOH: "+ex.getMessage();
            }
        }
        return "ok";
    }
    public String writeURA(List<Ura> uras){
        
        File makeFile=new File("/etc/asterisk/jpbx/ura.conf");       
        FileWriter fwrite = null;
        try{
            if(!makeFile.exists())
                makeFile.createNewFile();
            fwrite=new FileWriter(makeFile); 
            fwrite.write(";Arquivo escrito automaticamente pelo sistema JPBX\n\n");
            for (Ura u:uras) {
                fwrite.write("["+u.getName()+"]\n");
                fwrite.write("exten => s,1,Answer\n");
                fwrite.write("exten => s,n,Set(CDR(company)="+u.getCompany()+")\n");
                fwrite.write("exten => s,n,Set(CHANNEL(language)="+u.getLanguage()+")\n");
                fwrite.write("exten => s,n,Set(TIMEOUT(response)="+u.getInteractTimeout()+")\n");
                fwrite.write("exten => s,n,Set(TIMEOUT(digit)="+u.getDigitTimeout()+")\n");
                fwrite.write("exten => s,n(play),Background(/etc/asterisk/jpbx/moh/"+u.getBackground()+"/"+u.getBackground()+")\n");
                fwrite.write("exten => s,n,WaitExten("+u.getInteractTimeout()+")\n");
                fwrite.write("exten => s,n,Goto(t,1)\n");
                
                fwrite.write("exten => i,1,Playback(vm-sorry)\n");
                fwrite.write(verifyUraOptions(u.getInvalidAction(), u.getInvalidParam(), "i", "n",u.getCompany())+"\n");
                
                fwrite.write(verifyUraOptions(u.getTimeoutAction(), u.getTimeoutParam(), "t", "1", u.getCompany())+"\n");
                
                fwrite.write("exten => h,1,Noop(DESLIGADO URA "+u.getWebName()+" ${CUT(CHANNEL,,1)} EMPRESA ${CDR(company)})\n");
                if(u.getDialPeers()>0){
                    fwrite.write("exten => _ZXXX,1,AelSub(dialPeer,${EXTEN},60,0,t)\n");
                    fwrite.write("exten => _ZXX,1,AelSub(dialPeer,${EXTEN},60,0,t)\n");
                    fwrite.write("exten => _ZX,1,AelSub(dialPeer,${EXTEN},60,0,t)\n");
                }
                for (UraOption op : u.getUraOptions()) {
                    fwrite.write(verifyUraOptions(op.getAction(), op.getParam(), 
                            String.valueOf(op.getOption()), "1", u.getCompany())+"\n");
                }
                fwrite.write("\n");
            }
        }catch(IOException ex){
            return "FALHA NO WRITE URA: "+ex.getMessage();
        }finally{
            try{
                fwrite.flush();
                fwrite.close();
            }catch(IOException ex){
                return "FALHA NO WRITE URA: "+ex.getMessage();
            }
        }
        return "ok";
    }
    String verifyUraOptions(String op,String opValue,String exten,String prio,int company){
        switch(op){
            case "hangup":
                return "exten => "+exten+","+prio+",Hangup";
            case "begin":
                return "exten => "+exten+","+prio+",Goto(s,play)";
            case "peer":
                return "exten => "+exten+","+prio+",AelSub(dialPeer,"+opValue+",60,0,t)";
            case "callGrp":
                return "exten => "+exten+","+prio+",AelSub(callGrp,"+opValue+",120)";
            case "subUra":
                return "exten => "+exten+","+prio+",Goto("+opValue+",s,1)";
            case "rule":
                return "exten => "+exten+","+prio+",Set(DST="+opValue+")\n" +
                "same => n,GotoIf($[${DIALPLAN_EXISTS(jpbxRoute"+company+","+opValue+",1)}]?:$[${PRIORITY}+2])\n" +
                "same => n,Goto(jpbxRoute"+company+","+opValue+",1)\n" +
                "same => n,Goto(jpbxRoute"+company+",ALIAS,1)";
                //return "exten => "+exten+","+prio+",Goto(jpbxRoute"+company+","+opValue+",1)";
            case "queue":
                return "exten => "+exten+","+prio+",AelSub(callQueue,"+opValue+",120)";
            default:
                return "exten => "+exten+","+prio+",Noop(:::: FALHA NA URA ::::)";
        }
    }
    void writePeersHints(List<Peer> peers){
        File makeFile=new File("/etc/asterisk/jpbx/sipHints.conf");       
        FileWriter fwrite = null;
        
        try{
            if(!makeFile.exists())
                makeFile.createNewFile();
            fwrite=new FileWriter(makeFile);   
            fwrite.write(";Arquivo escrito automaticamente pelo sistema JPBX\n\n");
            fwrite.write("[Jpbx-Peers]\n");
            for(Peer p : peers) {
                if(!p.getPeerType().equals("VIRTUAL"))
                    fwrite.write("exten = "+p.getName()+",hint,"+p.getCanal()+"\n");
            }
        }catch(IOException ex){
            System.out.println("FALHA NO WRITE Hints: "+ex.getMessage());
        }finally{
            try{
                fwrite.flush();
                fwrite.close();
            }catch(IOException ex){
                System.out.println("FALHA NO WRITE Hints: "+ex.getMessage());
            }
        }
    }
    public String writePeersSIP(List<Peer> peers){
        String res="ok";
        File makeFile=new File("/etc/asterisk/jpbx/sip.conf");       
        FileWriter fwrite = null;
        try {   
            if(!makeFile.exists())
                makeFile.createNewFile();
            fwrite=new FileWriter(makeFile);
            fwrite.write(";Arquivo escrito automaticamente pelo sistema JPBX\n\n");
            for(Peer p : peers) {
                if(p.getPeerType().equals("SIP")){
                    fwrite.write("["+p.getName() +"]"+(p.getAuthorization()>0?"(security_"+p.getAuthorization()+")":"")+"\n");
                    fwrite.write("type="+p.getType()+"\n");
                    fwrite.write("context=Jpbx-Peers\n");
                    fwrite.write("host="+p.getHost()+"\n");
                    fwrite.write("md5secret="+p.getSecret()+"\n");
                    fwrite.write("language="+p.getLanguage()+"\n");
                    fwrite.write("canreinvite="+(p.getCanreinvite()>0?"yes":"no")+"\n");
                    fwrite.write("dtmfmode="+p.getDtmfmode()+"\n");
                    fwrite.write("nat="+p.getNat()+"\n");
                    fwrite.write("qualify="+p.getQualify()+"\n");
                    fwrite.write("callerid="+p.getCallerid()+" <"+p.getName()+">"+"\n");
                    fwrite.write("disallow=all\n");
                    fwrite.write("allow="+p.getAllow()+"\n");
                    if(!p.getAllowVideo().equals(""))
                        fwrite.write("allow="+p.getAllowVideo()+"\n");
                    fwrite.write("call-limit="+p.getCallLimit()+"\n");
                    if(p.getMailbox()!=0)
                        fwrite.write("mailbox="+p.getName() +"@default\n");
                    fwrite.write("\n");
                }
            }           
        }catch(IOException ex){
            res="Falha no WRITE Peers: "+ex.getMessage();
            return res;
        }finally{
            try {
                fwrite.flush();
                fwrite.close();
            } catch (IOException ex) {
                res="Falha no WRITE Peers: "+ex.getMessage();
                return res;
            }    
            writePeersHints(peers);
        }
        return res;
    }
    public String writePeersWEB(List<Peer> peers){
        String res="ok";
        File makeFile=new File("/etc/asterisk/jpbx/sipWeb.conf");       
        FileWriter fwrite = null;
        try {   
            if(!makeFile.exists())
                makeFile.createNewFile();
            fwrite=new FileWriter(makeFile);
            fwrite.write(";Arquivo escrito automaticamente pelo sistema JPBX\n\n");
            for(Peer p : peers) {
                if(p.getPeerType().equals("WEB")){
                    fwrite.write("["+p.getName() +"](WebPhones"+(p.getAuthorization()>0?",security_"+p.getAuthorization()+")":")")+"\n");
                    fwrite.write("type="+p.getType()+"\n");
                    fwrite.write("context=Jpbx-Peers\n");
                    fwrite.write("host="+p.getHost()+"\n");
                    fwrite.write("md5secret="+p.getSecret()+"\n");
                    fwrite.write("language="+p.getLanguage()+"\n");
                    fwrite.write("canreinvite="+(p.getCanreinvite()>0?"yes":"no")+"\n");
                    fwrite.write("dtmfmode="+p.getDtmfmode()+"\n");
                    fwrite.write("nat="+p.getNat()+"\n");
                    fwrite.write("qualify="+p.getQualify()+"\n");
                    fwrite.write("callerid="+p.getCallerid()+" <"+p.getName()+">"+"\n");
                    fwrite.write("disallow=all\n");
                    fwrite.write("allow="+p.getAllow()+"\n");
                    if(!p.getAllowVideo().equals(""))
                        fwrite.write("allow="+p.getAllowVideo()+"\n");
                    fwrite.write("call-limit="+p.getCallLimit()+"\n");
                    if(p.getMailbox()!=0)
                        fwrite.write("mailbox="+p.getName() +"@default\n");
                    //fwrite.write("rtcp_mux="+p.getRtcpMux()+"\n");
                    fwrite.write("\n");
                }
            }           
        }catch(IOException ex){
            res="Falha no WRITE Peers: "+ex.getMessage();
            return res;
        }finally{
            try {
                fwrite.flush();
                fwrite.close();
            } catch (IOException ex) {
                res="Falha no WRITE Peers: "+ex.getMessage();
                return res;
            }    
            writePeersHints(peers);
        }
        return res;
    }
}
