/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.cron;

import br.com.jpbx.asterisk.event.EventListener;
import java.io.IOException;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.manager.action.QueueStatusAction;
import org.asteriskjava.manager.action.QueueSummaryAction;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class RenewQueueSummary {

    public void renew(){
        for (int vzs = 0; vzs < 12; vzs++) {
            try {
                new EventListener().getMc().sendAction(new QueueSummaryAction());
                new EventListener().getMc().sendAction(new QueueStatusAction());
                Thread.sleep(5000);
            } catch (InterruptedException | IOException | TimeoutException | IllegalArgumentException | IllegalStateException ex) {
                System.out.println("DEU RUIM RENEW QUEUE SUMMARY");
            }
        }
    }
}
