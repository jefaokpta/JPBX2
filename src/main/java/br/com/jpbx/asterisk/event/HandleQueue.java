/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.asterisk.event;

import br.com.jpbx.model.QueueDAO;
import java.util.Map;
import org.asteriskjava.manager.event.QueueParamsEvent;
import org.asteriskjava.manager.event.QueueSummaryEvent;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class HandleQueue {
    
    private Map<String,QueueSummaryControl> qscc;
    private Map<String,QueueStatusControl> qstcc;
    
    public HandleQueue() {
        
    }
    
    
    public void handle(QueueSummaryEvent qse){
        qscc=new QueueSummaryCenterControl().getQueueSummary();
        if(qscc.containsKey(qse.getQueue())){
            QueueSummaryControl qsc=qscc.get(qse.getQueue());
            qsc.setAvailable(qse.getAvailable());
            qsc.setTalktime(qse.getTalkTime());
            qsc.setLoggedIn(qse.getLoggedIn());
            qsc.setCallers(qse.getCallers());
            qsc.setLongestHoldTime(qse.getLongestHoldTime());
            qsc.setHoldtime(qse.getHoldTime());
            return;
        }
        if(qse.getQueue().substring(0, 5).equals("Queue"))
            qscc.put(qse.getQueue(), new QueueSummaryControl(qse.getAvailable(), qse.getTalkTime(), 
                qse.getLoggedIn(), qse.getCallers(), qse.getLongestHoldTime(), qse.getHoldTime(), qse.getQueue(), 
                new QueueDAO().getSingleQueue(Integer.parseInt(qse.getQueue().substring(5))).getWebName()));
    }

    public void handle(QueueParamsEvent qpe){
        qstcc=new QueueStatusCenterControl().getQstcc();
        if(qstcc.containsKey(qpe.getQueue())){
            QueueStatusControl qsc=qstcc.get(qpe.getQueue());
            qsc.setServiceLevelPerf(qpe.getServiceLevelPerf());
            qsc.setMax(qpe.getMax());
            qsc.setServiceLevel(qpe.getServiceLevel());
            qsc.setWeight(qpe.getWeight());
            qsc.setCompleted(qpe.getCompleted());
            qsc.setTalkTime(qpe.getTalkTime());
            qsc.setCalls(qpe.getCalls());
            qsc.setHoldTime(qpe.getHoldTime());
            qsc.setStrategy(qpe.getStrategy());
            qsc.setAbandoned(qpe.getAbandoned());
            return;
        }
        if(qpe.getQueue().substring(0, 5).equals("Queue"))
            qstcc.put(qpe.getQueue(), new QueueStatusControl(qpe.getQueue(), qpe.getMax(),
                    qpe.getStrategy(), qpe.getCalls(), qpe.getHoldTime(), qpe.getTalkTime(), qpe.getCompleted(),
                    qpe.getAbandoned(), qpe.getServiceLevel(), qpe.getServiceLevelPerf(), qpe.getWeight()));
    }
}
