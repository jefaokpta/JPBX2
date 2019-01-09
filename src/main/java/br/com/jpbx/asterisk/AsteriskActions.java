/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.asterisk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
public class AsteriskActions {
    
    public String reloadSip(){
        String res="ok";
        //try {
            new Asterisk().getInfos("sip reload");
//        } catch (IOException ex) {
//            res="FALHA CONEXAO ASTERISK: "+ex.getMessage();
//        }   
        return res;
    }
    public String reloadMoh(){
        String res="ok";
       // try {
            new Asterisk().getInfos("moh reload");
//        } catch (IOException ex) {
//            res="FALHA CONEXAO ASTERISK: "+ex.getMessage();
//        }   
        return res;
    }
    public String reloadSipVoicemailDialplan(){
        String res="ok";
        //try {
            Asterisk ast=new Asterisk();
            //ast.AstConnect();
            ast.getInfos("sip reload");
            ast.getInfos("voicemail reload");
            ast.getInfos("dialplan reload");
           // ast.AstDisconnect();
//        } catch (IOException ex) {
//            res="FALHA NO RELOAD S V D: "+ex.getMessage();
//        }      
        return res;
    }
    public String reloadVoicemail(){
        String res="ok";
        //try {
            Asterisk ast=new Asterisk();
            ast.getInfos("voicemail reload");
           // ast.AstDisconnect();
//        } catch (IOException ex) {
//            res="FALHA NO RELOAD VOICEMAIL: "+ex.getMessage();
//        }      
        return res;
    }
    public List<ServerStatus> getAstUptime(){
        List<String> astList = null;
        List<ServerStatus> retList=new ArrayList<>();
       // try {
            
            Asterisk ast=new Asterisk();
            //ast.AstConnect();
            astList=ast.getInfos("core show uptime");
            ServerStatus serv=new ServerStatus();
            serv.setStatus("Tempo em Atividade");
            serv.setValue(astList.get(0).substring(astList.get(0).indexOf(":")+1));
            retList.add(serv);
            astList=ast.getInfos("core show channels count");
            serv=new ServerStatus();
            serv.setStatus("Canais Ativos");
            serv.setValue(astList.get(0).split(" ")[0]);
            retList.add(serv);
            serv=new ServerStatus();
            serv.setStatus("Em Conversação");
            serv.setValue(astList.get(1).split(" ")[0]);
            retList.add(serv);
            serv=new ServerStatus();
            serv.setStatus("Chamadas Processadas");
            serv.setValue(astList.get(2).split(" ")[0]);
            retList.add(serv);
            //ast.AstDisconnect();
//        } catch (IOException ex) {
//            System.out.println(ex.getMessage());
//        }
        return retList;
    }

    public String reloadIAX() {
        String res="ok";
       // try {
            new Asterisk().getInfos("iax2 reload");
//        } catch (IOException ex) {
//            res="FALHA CONEXAO ASTERISK: "+ex.getMessage();
//        }
        return res;
    }
    public String dialplanReload() {
        String res="ok";
       // try {
            new Asterisk().getInfos("dialplan reload");
//        } catch (IOException ex) {
//            res="FALHA CONEXAO ASTERISK: "+ex.getMessage();
//        }
        return res;
    }
}
