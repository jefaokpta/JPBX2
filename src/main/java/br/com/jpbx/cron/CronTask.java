/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.cron;

import br.com.jpbx.asterisk.VerifyAsteriskUp;
import br.com.jpbx.asterisk.event.EventListener;
import it.sauronsoftware.cron4j.Scheduler;
import java.io.IOException;
import javax.faces.application.Application;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PostConstructApplicationEvent;
import javax.faces.event.PreDestroyApplicationEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;
import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.TimeoutException;

/**
 * 
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
public class CronTask implements SystemEventListener{

    @Override
    public void processEvent(SystemEvent event) throws AbortProcessingException {
        Scheduler cron=new Scheduler();
        Scheduler cronDebit=new Scheduler();
        Scheduler cronResetCompAndProfile=new Scheduler();
        EventListener eventListener=new EventListener();
        //final WebServerLocation conf=new WebServerLocation();
        
        if(event instanceof PostConstructApplicationEvent){
            System.out.println("O JPBX iniciou! Vai CRON!!!");
            cronResetCompAndProfile.schedule("* 1 * * *", new Runnable() {
                @Override
                public void run() {
                    //System.out.println("CRON LANCOU!!!");
                    new VerifyCompromiseAndProfileDate().verifyBoth();
//                    try {
//                        new URL("http://"+conf.getAddress()+":"+conf.getPort()+"/jpbx/VerifyCompromiseAndProfileDateServlet").openStream();
//                    } catch (MalformedURLException ex) {
//                        System.out.println("DEU RUIM CRON: "+ex.getMessage());
//                    } catch (IOException ex) {
//                        System.out.println("DEU RUIM CRON: "+ex.getMessage());
//                    }
                }
            });  
            cron.schedule("* * * * *", new Runnable() {
                @Override
                public void run() {
                    //System.out.println("CRON LANCOU!!!");
                    new VerifyAsteriskUp().astUp();
                    new RenewDnsProfile().renew();
                    new RenewQueueSummary().renew();
//                    try {
//                        new URL("http://"+conf.getAddress()+":"+conf.getPort()+"/jpbx/AstUpToMemoryServlet").openStream();
//                        new URL("http://"+conf.getAddress()+":"+conf.getPort()+"/jpbx/VerifyAstUpServlet").openStream();                       
//                        new URL("http://"+conf.getAddress()+":"+conf.getPort()+"/jpbx/RenewDnsProfileServlet").openStream();
//                    } catch (MalformedURLException ex) {
//                        System.out.println("DEU RUIM CRON ASTUP: "+ex.getMessage());
//                    } catch (IOException ex) {
//                        System.out.println("DEU RUIM CRON ASTUP: "+ex.getMessage());
//                    }                   
                }
            });           
            cronDebit.schedule("*/5 * * * *", new Runnable() {
                @Override
                public void run() {
                    //System.out.println("CRON LANCOU!!!");
                    new VerifyDebitProfileAndCompromise().verifyBoth();
                    new VerifyInvasions().antiInvasion();
//                    try {
//                        new URL("http://"+conf.getAddress()+":"+conf.getPort()+"/jpbx/VerifyDebitProfileAndCompromiseServlet").openStream();
//                        new URL("http://"+conf.getAddress()+":"+conf.getPort()+"/jpbx/VerifyInvasionsServlet").openStream();
//                    } catch (MalformedURLException ex) {
//                        System.out.println("DEU RUIM CRON: "+ex.getMessage());
//                    } catch (IOException ex) {
//                        System.out.println("DEU RUIM CRON: "+ex.getMessage());
//                    }
                }
            });
            
            System.out.println("INICIANDO LISTENER ASTERISK !!!");
            
            eventListener.getMc().addEventListener(eventListener);
            try {
                eventListener.getMc().login();
            } catch (IllegalStateException | IOException | AuthenticationFailedException | TimeoutException ex) {
                System.out.println("DEU RUIM INICIANDO LISTENER ASTERISK !!!");
            }
            cronResetCompAndProfile.start();
            cronDebit.start();
            cron.start();
        }
        if(event instanceof PreDestroyApplicationEvent){
            System.out.println("O JPBX ESTA FINALIZANDO!");
            cron.stop();
            cronDebit.stop();
            cronResetCompAndProfile.stop();
        }
    }

    @Override
    public boolean isListenerForSource(Object value) {
        return (value instanceof Application);
    }

}
