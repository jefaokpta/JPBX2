/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.asterisk;

import br.com.jpbx.asterisk.event.EventListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.asteriskjava.manager.response.GetVarResponse;
import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionFactory;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.manager.action.CommandAction;
import org.asteriskjava.manager.action.DbDelAction;
import org.asteriskjava.manager.action.DbDelTreeAction;
import org.asteriskjava.manager.action.DbPutAction;
import org.asteriskjava.manager.action.GetVarAction;

import org.asteriskjava.manager.action.QueueAddAction;
import org.asteriskjava.manager.action.QueueLogAction;
import org.asteriskjava.manager.action.QueuePauseAction;
import org.asteriskjava.manager.action.QueuePenaltyAction;
import org.asteriskjava.manager.action.QueueRemoveAction;
import org.asteriskjava.manager.action.QueueResetAction;
import org.asteriskjava.manager.action.SetVarAction;
import org.asteriskjava.manager.response.CommandResponse;

/**
 * 
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
public class Asterisk {
    private static ManagerConnection managerConnection;
    
    public Asterisk(){
        
        if(managerConnection==null){
            System.out.println("::::: NOVA CONEXAO ASTERISK :::::");
            AsteriskAuthentication auth=new AsteriskAuthentication();
            ManagerConnectionFactory factory=new
                            ManagerConnectionFactory(
                                    auth.getIp(),
                                    auth.getUser(),
                                    auth.getPassword());
            managerConnection=factory.createManagerConnection();
            AstConnect();
        }
    }
//    public Asterisk(String host,String user,String pass) throws IOException{ 
//        if(managerConnection==null){
//            System.out.println("::::: NOVA CONEXAO ASTERISK");
//            ManagerConnectionFactory factory=new
//                            ManagerConnectionFactory(
//                                    host,
//                                    user,
//                                    pass);
//            managerConnection=factory.createManagerConnection();
//            AstConnect();
//        }
//    }
    public static void AstConnect(){
        try {
            managerConnection.login();
        } catch (IllegalStateException | IOException | AuthenticationFailedException | TimeoutException ex) {
            System.out.println("ERRO AO CONECTAR ASTERISK: "+ex.getMessage());
        }
    }
    public static void AstDisconnect(){
        managerConnection.logoff();
    }
    public void changePenalty(String iface,String queue,int penalty){
        try {
            managerConnection.sendAction(new QueuePenaltyAction(iface, penalty, queue));
        } catch (IOException | TimeoutException | IllegalArgumentException | IllegalStateException ex) {
            System.out.println("ERRO AO SETAR PENALTY PARA "+iface+": "+ex.getMessage());
        }
    }
    public void astDBAdd(String family,String key,String value){
        try {
            managerConnection.sendAction(new DbPutAction(family, key, value));
        } catch (IOException | TimeoutException | IllegalArgumentException | IllegalStateException ex) {
            System.out.println("ERRO PUT DO ASTDB: "+ex.getMessage());
        }       
    }

    public void setVar(String channel,String var,String value){
        try {
            managerConnection.sendAction(new SetVarAction(channel, var, value));
        } catch (IOException | TimeoutException | IllegalArgumentException | IllegalStateException ex) {
            System.out.println("ERRO SET VAR: "+ex.getMessage());
        }
    }
    public String getVar(String channel,String var){
        GetVarResponse gv = null;
        try {
                   gv=(GetVarResponse) managerConnection.sendAction(new GetVarAction(channel,var));   
        } catch (IOException | TimeoutException | IllegalArgumentException | IllegalStateException ex) {
            System.out.println("ERRO GET VAR: "+ex.getMessage());
        }
        return gv.getValue();
    }
    public String getVar(String var){
        GetVarResponse gv = null;
        try {
            gv=(GetVarResponse) managerConnection.sendAction(new GetVarAction(var));
        } catch (IOException | TimeoutException | IllegalArgumentException | IllegalStateException ex) {
            System.out.println("ERRO GET VAR : "+ex.getMessage());
        }
        return gv.getValue();
    }
    public void astDBDEL(String family,String key){
        try {
            managerConnection.sendAction(new DbDelAction(family, key));
        } catch (IOException | TimeoutException | IllegalArgumentException | IllegalStateException ex) {
            System.out.println("ERRO DEL DO ASTDB: "+ex.getMessage());
        }
    }
    public void astDBDELTREE(String family){
        try {
            managerConnection.sendAction(new DbDelTreeAction(family,""));
        } catch (IOException | TimeoutException | IllegalArgumentException | IllegalStateException ex) {
            System.out.println("ERRO DELTREE DO ASTDB: "+ex.getMessage());
        }
    }
    public void queueAdd(String queue,String iface,String member,String stateIface){
        try {
            QueueAddAction q=new QueueAddAction(queue, iface);
            q.setMemberName(member);
            q.setStateInterface(stateIface);
            managerConnection.sendAction(q);
            managerConnection.sendAction(new QueueLogAction(queue, "ADDMEMBER", iface, member, "123465"));
        } catch (IOException | TimeoutException | IllegalArgumentException | IllegalStateException ex) {
            System.out.println("ERRO QUEUE ADD: "+ex.getMessage());
        }      
    }
    public void queueAdd(String queue,String iface,String member){
        try {
            QueueAddAction q=new QueueAddAction(queue, iface);
            q.setMemberName(member);
            managerConnection.sendAction(q);
            managerConnection.sendAction(new QueueLogAction(queue, "ADDMEMBER", iface, member, "123465"));
        } catch (IOException | TimeoutException | IllegalArgumentException | IllegalStateException ex) {
            System.out.println("ERRO QUEUE ADD: "+ex.getMessage());
        }      
    }
    public void queuePause(String iface, boolean pause,String queue,String reason){
        try {
            QueuePauseAction p=new QueuePauseAction(iface, queue, pause);
            p.setReason(reason);
            //managerConnection.sendAction(p);
            new EventListener().getMc().sendAction(p);  //USANDO OUTRA  CONEXAO PRA RECEBER EVENTOS  // NAO DEU CERTO PQ NAO CHAMA O EVENT
        } catch (IOException | TimeoutException | IllegalArgumentException | IllegalStateException ex) {
            System.out.println("ERRO QUEUE REMOVE: "+ex.getMessage());
        }
    }
    public void queueRemove(String queue,String iface,String member){
        try {
            managerConnection.sendAction(new QueueRemoveAction(queue, iface));
            managerConnection.sendAction(new QueueLogAction(queue, "REMOVEMEMBER", iface, member, "123465"));
        } catch (IOException | TimeoutException | IllegalArgumentException | IllegalStateException ex) {
            System.out.println("ERRO QUEUE REMOVE: "+ex.getMessage());
        }
    }
    public void queueReset(String queueName){
        try {
            managerConnection.sendAction(new QueueResetAction(queueName));
        } catch (IOException | TimeoutException | IllegalArgumentException | IllegalStateException ex) {
            System.out.println("ERRO QUEUE RESET "+queueName+": "+ex.getMessage());
        }
    }
    public List<String> getInfos(String command){
    
        CommandAction commandAction;
        CommandResponse commandResponse;
        
        commandAction=new CommandAction(command);
        List<String> res = new ArrayList<>();
        try {
            commandResponse=(CommandResponse) managerConnection.sendAction(commandAction,3000);
            res=commandResponse.getResult();
        } catch (IOException | TimeoutException | IllegalArgumentException | IllegalStateException ex) {
            System.out.println("ERRO AO PEGAR INFOS ASTERISK: "+ex.getMessage());
            AstConnect();
        }
       
        return res;
    }
    
    
}
