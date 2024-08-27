
package br.com.jpbx.controller;

import br.com.jpbx.chart.LineChartFactory;
import br.com.jpbx.model.Queue;
import br.com.jpbx.model.QueueDAO;
import br.com.jpbx.model.QueueLogDAO;
import br.com.jpbx.util.DacSinteticData;
import br.com.jpbx.util.DacSinteticFilter;
import br.com.jpbx.util.FormaterSeconds;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.model.chart.LineChartModel;

/**
 *
 * @author jefaokpta
 */
@Named(value = "dacSinteticBean")
@ViewScoped
public class DacSinteticBean implements Serializable{

    private Date start;
    private Date end;
    private List<Queue> queues;
    private int queueId;
    private Queue queue;
    private boolean showStatistcs;
    private List<DacSinteticData> dacs;
    private List<DacSinteticData> dacTotals;
    
    public DacSinteticBean() {
        int currentCompany=(int) FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().get("currentCompany");
        queues=new QueueDAO().getAllQueues(currentCompany);
        Calendar first=Calendar.getInstance();
        first.set(Calendar.DAY_OF_MONTH, 1);
        start=first.getTime();
        end=new Date();
    }
// CONVERTEER TEMPO AQUI E ENTREGAR!!!!!
    public String hours(int secs){
        return new FormaterSeconds().secondsToHours(secs);
    }
    public void showRel(){
        for (Queue q : queues) {
            if(q.getId()==queueId){
                queue=q;
                break;
            }
        }
        showStatistcs=true;
        Calendar last=Calendar.getInstance();
        last.setTime(end);
        last.add(Calendar.DAY_OF_MONTH, 1);
        dacs=new QueueLogDAO().getDacSintetic(new DacSinteticFilter(start, last.getTime(), queue));
        dacTotals=new ArrayList<>();
        DacSinteticData total=new DacSinteticData();
        for (DacSinteticData d : dacs) {
            total.setTotal(total.getTotal()+d.getTotal());
            total.setAbandon(total.getAbandon()+d.getAbandon());
            total.setAnswer(total.getAnswer()+d.getAnswer());
            total.setNoAnswer(total.getNoAnswer()+d.getNoAnswer());
            total.setTma(total.getTma()+d.getTma());
            total.setTmc(total.getTmc()+d.getTmc());
            total.setTme(total.getTme()+d.getTme());
        }
        dacTotals.add(total);
               
    }

    public LineChartModel getStatistcGraf(){
        return new LineChartFactory().lineCallsStatistcDAC(dacs);
    }
    public List<DacSinteticData> getDacTotals() {
        return dacTotals;
    }

    public List<DacSinteticData> getDacs() {
        return dacs;
    }

    public Queue getQueue() {
        return queue;
    }
    
    
    public List<Queue> getQueues() {
        return queues;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public int getQueueId() {
        return queueId;
    }

    public void setQueueId(int queueId) {
        this.queueId = queueId;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public boolean isShowStatistcs() {
        return showStatistcs;
    }

    
    
    
}
