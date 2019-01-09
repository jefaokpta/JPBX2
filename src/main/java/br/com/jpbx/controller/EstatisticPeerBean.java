/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.chart.LineChartFactory;
import br.com.jpbx.model.Peer;
import br.com.jpbx.model.PeerDAO;
import br.com.jpbx.model.RelCallDAO;
import br.com.jpbx.util.FormaterSeconds;
import br.com.jpbx.util.StatistcPeer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped; 
import org.primefaces.model.chart.LineChartModel;

/**
 *
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
@Named(value = "estatisticPeerBean")
@ViewScoped
public class EstatisticPeerBean implements Serializable{

    private List<Peer> peers;
    private Peer peer;
    private int peerId;
    private Date start;
    private Date end;
    private boolean showStatistcs;
    private List<StatistcPeer> statistcs;
    private List<StatistcPeer> statistcsTotals;
    
    public EstatisticPeerBean() {
        int currentCompany=(int) FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().get("currentCompany");
        peers=new PeerDAO().getAllPeersOfCompany(currentCompany);
        start=new Date();
        end=new Date();
    }
    
    public void showEstatistic(){
        for (Peer p : peers) {
            if(peerId==p.getId()){
                peer=p;
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
        statistcs=new RelCallDAO().statistcsPeer(start, end, peer);
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

    public List<StatistcPeer> getStatistcs() {
        return statistcs;
    }

    public boolean isShowStatistcs() {
        return showStatistcs;
    }

    public void setShowStatistcs(boolean showStatistcs) {
        this.showStatistcs = showStatistcs;
    }

    public List<Peer> getPeers() {
        return peers;
    }

    public int getPeerId() {
        return peerId;
    }

    public void setPeerId(int peerId) {
        this.peerId = peerId;
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

    public Peer getPeer() {
        return peer;
    }

    public List<StatistcPeer> getStatistcsTotals() {
        return statistcsTotals;
    }

    
    
}
