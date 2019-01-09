/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.asterisk;

import java.io.IOException;
import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionFactory;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.manager.action.OriginateAction;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class AsteriskDial {
    
    private ManagerConnection managerConnection;

    public AsteriskDial() {
        System.out.println("::::: NOVA CONEXAO ASTERISK PARA DISCAR :::::");
        AsteriskAuthentication auth=new AsteriskAuthentication();
        ManagerConnectionFactory factory=new
                            ManagerConnectionFactory(
                                    auth.getIp(),
                                    auth.getUser(),
                                    auth.getPassword());
        managerConnection=factory.createManagerConnection();
        try {
            managerConnection.login();
        } catch (IllegalStateException | IOException | AuthenticationFailedException | TimeoutException ex) {
            System.out.println("ERRO AO CONECTAR ASTERISK: "+ex.getMessage());
        }
    }
    
    public String callFromMTO(String chan,int src,String dst){
        OriginateAction or=new OriginateAction();
        or.setChannel(chan);
        or.setCallerId("JPBX <"+dst+">");
        or.setContext("Jpbx-Peers");
        or.setExten(dst);     
        or.setTimeout(60000);
        or.setPriority(1);
        try {
            return managerConnection.sendAction(or,30000).getResponse();
        } catch (IOException | TimeoutException | IllegalArgumentException | IllegalStateException ex) {
            return "Atenda o seu Ramal: "+ ex.getMessage();
        }finally{
            managerConnection.logoff();
        }
    }
    public String spyCall(String chanSupervisor,String chanSpyed){
        OriginateAction or=new OriginateAction();
        or.setChannel(chanSupervisor);
        or.setCallerId("MONITORAR  "+chanSpyed.split("/")[1]+" <"+chanSpyed.split("/")[1]+">");
        or.setApplication("ChanSpy");
        or.setData(chanSpyed+",bdsS");
        try {
            return managerConnection.sendAction(or,30000).getResponse();
        } catch (IOException | TimeoutException | IllegalArgumentException | IllegalStateException ex) {
            return ex.getMessage();
        }finally{
            managerConnection.logoff();
        }
    }
    public String callFromAgent(String chan,String src,String dst){
        OriginateAction or=new OriginateAction();
        or.setChannel("Local/"+dst+"@Jpbx-Peers");
        or.setCallerId("JPBX <"+src+">");
        or.setApplication("Dial");
        or.setData(chan+",60,t");
        or.setVariable("__TRANSFER_CONTEXT", "TRANSFERER");
        or.setTimeout(60000);
        try {
            return managerConnection.sendAction(or,55000).getResponse();
        } catch (IOException | TimeoutException | IllegalArgumentException | IllegalStateException ex) {
            return "Efetuando chamada: "+ ex.getMessage();
        }finally{
            managerConnection.logoff();
        }
    }
    public String callFax(String src,String dst,String file,String company){
        OriginateAction or=new OriginateAction();
        or.setChannel("Local/"+dst+"@sendFaxCall");
        or.setCallerId(src+" <"+src+">");
        //or.setTimeout(50000);
        or.setContext("sendFax");
        or.setExten(dst);
        or.setPriority(1);
        or.setVariable("ORG", src);
        or.setVariable("DST", dst);
        or.setVariable("EMP", company);
        or.setVariable("DPLAN", "jpbxRoute"+company);
        or.setVariable("FAX_FILE", file);
        try {
            return managerConnection.sendAction(or, 50000).getResponse();
        } catch (IOException | TimeoutException | IllegalArgumentException | IllegalStateException ex) {
            return "DEU RUIM NA LIGACAO FAX "+ ex.getMessage();
        }finally{
            managerConnection.logoff();
        }
    }
    public String sendFax(String file,int company,int dst,int stationId){
        OriginateAction or=new OriginateAction();
        or.setChannel("Local/"+dst+"@sendFaxCall");
        or.setCallerId("JPBX FAX <"+stationId+">");
        or.setContext("sendFax");
        or.setExten(String.valueOf(dst));
        or.setPriority(1);
        or.setVariable("FAX_FILE", file);
        or.setVariable("EMP", String.valueOf(company));
        try {
            return managerConnection.sendAction(or).getResponse();
        } catch (IOException | TimeoutException | IllegalArgumentException | IllegalStateException ex) {
            return "DEU RUIM NO FAX "+ ex.getMessage();
        }finally{
            managerConnection.logoff();
        }
    }

}
