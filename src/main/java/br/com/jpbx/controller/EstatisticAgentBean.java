
package br.com.jpbx.controller;

import br.com.jpbx.chart.LineChartFactory;
import br.com.jpbx.model.Agent;
import br.com.jpbx.model.AgentDAO;
import br.com.jpbx.model.RelCallDAO;
import br.com.jpbx.util.FormaterSeconds;
import br.com.jpbx.util.StatistcPeer;
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
@Named(value = "estatisticAgentBean")
@ViewScoped
public class EstatisticAgentBean implements Serializable{

    private List<Agent> agents;
    private Agent agent;
    private int agentId;
    private Date start;
    private Date end;
    private boolean showStatistcs;
    private List<StatistcPeer> statistcs;
    private List<StatistcPeer> statistcsTotals;
    
    public EstatisticAgentBean() {
        int currentCompany=(int) FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().get("currentCompany");
        agents=new AgentDAO().getAllAgents(currentCompany);
        start=new Date();
        end=new Date();
    }
    
    public void showEstatistic(){
        for (Agent a : agents) {
            if(agentId==a.getId()){
                agent=a;
                break;
            }
        }
        showStatistcs=true;
//        FacesContext.getCurrentInstance().addMessage(null,
//                new FacesMessage(FacesMessage.SEVERITY_WARN,
//                        "Ramal ."+peer.getName()+" "+peer.getCallerid(), ""));
        Calendar hour=Calendar.getInstance();
        hour.setTime(end);
        hour.set(Calendar.HOUR_OF_DAY, 23);
        hour.set(Calendar.MINUTE, 59);
        end=hour.getTime();
        statistcs=new RelCallDAO().statistcsAgent(start, end, agent);
        statistcsTotals=new ArrayList<>();
        StatistcPeer inbound=new StatistcPeer(new Date(), "Recebida");
        StatistcPeer outbound=new StatistcPeer(new Date(), "Efetuada");
        inbound.setLabel("Total");
        outbound.setLabel("Total");
        for (StatistcPeer st : statistcs) {
            if(st.getType().equals("Recebida")){
                inbound.setCallTotal(inbound.getCallTotal()+st.getCallTotal());
                inbound.setAnswered(inbound.getAnswered()+st.getAnswered());               
                inbound.setBusy(inbound.getBusy()+st.getBusy());
                inbound.setNotAnswered(inbound.getNotAnswered()+st.getNotAnswered());
                inbound.setCallTimeTotal(inbound.getCallTimeTotal()+st.getCallTimeTotal());
                inbound.setCallTimeSpeak(inbound.getCallTimeSpeak()+st.getCallTimeSpeak());
                inbound.setCallTimeWait(inbound.getCallTimeWait()+st.getCallTimeWait());
                inbound.setMeddleCall(inbound.getMeddleCall()+st.getMeddleCall());
                inbound.setMeddleSpeak(inbound.getMeddleSpeak()+st.getMeddleSpeak());
                inbound.setMeddleWait(inbound.getMeddleWait()+st.getMeddleWait());
                continue;
            }
            outbound.setCallTotal(outbound.getCallTotal()+st.getCallTotal());
            outbound.setAnswered(outbound.getAnswered()+st.getAnswered());               
            outbound.setBusy(outbound.getBusy()+st.getBusy());
            outbound.setNotAnswered(outbound.getNotAnswered()+st.getNotAnswered());
            outbound.setCallTimeTotal(outbound.getCallTimeTotal()+st.getCallTimeTotal());
            outbound.setCallTimeSpeak(outbound.getCallTimeSpeak()+st.getCallTimeSpeak());
            outbound.setCallTimeWait(outbound.getCallTimeWait()+st.getCallTimeWait());
            outbound.setMeddleCall(outbound.getMeddleCall()+st.getMeddleCall());
            outbound.setMeddleSpeak(outbound.getMeddleSpeak()+st.getMeddleSpeak());
            outbound.setMeddleWait(outbound.getMeddleWait()+st.getMeddleWait());
        }
        statistcsTotals.add(outbound);
        statistcsTotals.add(inbound);     
    }
    public LineChartModel getStatistcGraf(){
        return new LineChartFactory().lineCallsStatistcPeer(statistcs);
    }
    public String formatTime(int secs){
        return new FormaterSeconds().secondsToHours(secs);
    }
    
    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public List<Agent> getAgents() {
        return agents;
    }

    public boolean isShowStatistcs() {
        return showStatistcs;
    }

    public List<StatistcPeer> getStatistcs() {
        return statistcs;
    }

    public List<StatistcPeer> getStatistcsTotals() {
        return statistcsTotals;
    }
    
    
}
