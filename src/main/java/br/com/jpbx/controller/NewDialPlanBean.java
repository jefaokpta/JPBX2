/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.AcCode;
import br.com.jpbx.model.AcCodeDAO;
import br.com.jpbx.model.Alias;
import br.com.jpbx.model.AliasDAO;
import br.com.jpbx.model.CenterCost;
import br.com.jpbx.model.CenterCostDAO;
import br.com.jpbx.model.Company;
import br.com.jpbx.model.CompanyDAO;
import br.com.jpbx.model.DialPlanAction;
import br.com.jpbx.model.DialPlanRule;
import br.com.jpbx.model.DialPlanRuleDAO;
import br.com.jpbx.model.Moh;
import br.com.jpbx.model.MohDAO;
import br.com.jpbx.model.Peer;
import br.com.jpbx.model.PeerDAO;
import br.com.jpbx.model.PickupGrp;
import br.com.jpbx.model.PickupGrpDAO;
import br.com.jpbx.model.Queue;
import br.com.jpbx.model.QueueDAO;
import br.com.jpbx.model.Route;
import br.com.jpbx.model.RouteDAO;
import br.com.jpbx.model.Trunk;
import br.com.jpbx.model.TrunkDAO;
import br.com.jpbx.model.Ura;
import br.com.jpbx.model.UraDAO;
import br.com.jpbx.model.WebServerBdo;
import br.com.jpbx.model.WebServerBdoDAO;
import br.com.jpbx.util.Translate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author jefaokpta
 */
@Named(value = "newDialPlanBean")
@ViewScoped
public class NewDialPlanBean implements Serializable{

    private DialPlanRule dialplan;
    private List<DialPlanRule> dialplans;
    private boolean srcPeer;
    private boolean srcPick;
    private boolean srcTrunk;
    private boolean srcRegex;
    private boolean srcQlqr;
    private boolean srcFax;
    private boolean srcAlias;
    private boolean dstAlias;
    private boolean valid24;
    private boolean hangup;
    private boolean notImpl;
    private boolean playbacks;
    private boolean answer;
    private boolean dialPeer;
    private boolean dialAgent;
    private boolean editDst;
    private boolean editSrc;
    private boolean rollbackDst;
    private boolean rollbackSrc;
    private boolean command;
    private boolean ccost;
    private boolean trunkRoute;
    private boolean callgrp;
    private boolean queue;
    private boolean ura;
    private boolean voicemail;
    private boolean company;
    private boolean sendFax;
    private boolean receiveFax;
    private boolean accode;
    private boolean pickupGrp;
    private boolean ignoreVM;
    private boolean bdo;
    private boolean  ediction;
    
    private List<String> week;
    private List<Peer> peers;
    private List<PickupGrp> pickupgrps;
    private List<Trunk> trunks;
    private List<Alias> aliases;
    private Date timeStart;
    private Date timeEnd;
    private String action;
    private List<DialPlanAction> actions;
    private DialPlanAction actionItem;
    private List<Moh> mohs;
    private List<CenterCost> centerCosts;
    private List<Route> routes;
    private List<Queue> callGrps;
    private List<Queue> callQueues;
    private List<Ura> uras;
    private List<AcCode> accodes;
    private List<Company> companys;
    private List<WebServerBdo> bdos;
    
    public NewDialPlanBean() {
        dialplan=new DialPlanRule();
        dialplan.setCompany((int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCompany"));
        dialplan.setActive(1);
        dialplans=new ArrayList<>();
        for (DialPlanRule d : new DialPlanRuleDAO().getAllDialplans()) {
            if(d.getCompany()==dialplan.getCompany())
                dialplans.add(d);
        }
        peers=new ArrayList<>();
        for (Peer p : new PeerDAO().getAllPeers()) {
            if(p.getCompany()==dialplan.getCompany())
                peers.add(p);
        }
        pickupgrps=new ArrayList<>();
        for (PickupGrp g : new PickupGrpDAO().getAllGrps()) {
            if(g.getCompany()==dialplan.getCompany())
                pickupgrps.add(g);
        }
        mohs=new ArrayList<>();
        for (Moh m : new MohDAO().getAllMohs()) {
            if(m.getCompany()==dialplan.getCompany())
                mohs.add(m);
        }
        routes=new ArrayList<>();
        for (Route r : new RouteDAO().getAllRoutes()) {
            if(r.getCompany()==dialplan.getCompany())
                routes.add(r);
        }
        callGrps=new ArrayList<>();
        for (Queue c: new QueueDAO().getAllCallGrps()) {
            if(c.getCompany()==dialplan.getCompany())
                callGrps.add(c);
        }
        callQueues=new ArrayList<>();
        for (Queue q: new QueueDAO().getAllQueues(dialplan.getCompany())) {
                callQueues.add(q);
        }
        uras=new ArrayList<>();
        for (Ura u: new UraDAO().getAllUras()) {
            if(u.getCompany()==dialplan.getCompany())
                uras.add(u);
        }
        accodes=new ArrayList<>();
        for (AcCode ac: new AcCodeDAO().getAllAcCodes()) {
            if(ac.getCompany()==dialplan.getCompany())
                accodes.add(ac);
        }
        centerCosts=new ArrayList<>();
        for (CenterCost cc : new CenterCostDAO().getCCosts()) {
            if(cc.getCompany()==dialplan.getCompany())
                centerCosts.add(cc);
        }
        bdos=new WebServerBdoDAO().getAllWebServerBdo(dialplan.getCompany());
        trunks=new TrunkDAO().getAllTrunks();
        aliases=new AliasDAO().getAllAlias();
        companys=new CompanyDAO().getAllCompanys();
        //centerCosts=new CenterCostDAO().getCCosts();
        
        srcPeer=true;
        valid24=true;
        DialPlanRule copy=(DialPlanRule) FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().get("copyDialplan");
        actions=new ArrayList<>();
        if(copy!=null){
            for (DialPlanAction da : copy.getActions()) {
                actions.add(new DialPlanAction(da.getPriority(), da.getAct(), da.getArg1(), da.getArg2(), da.getArg3(), da.getArg4(), da.getArg5()));
            }
            Collections.sort(actions);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("copyDialplan");
        }
        hangup=true;
        actionItem=new DialPlanAction();
        actionItem.setAct("hangup");
    }
    public void addAction(){
        actions.add(new DialPlanAction(actions.size(),actionItem.getAct(), actionItem.getArg1(), 
                actionItem.getArg2(), actionItem.getArg3(), actionItem.getArg4(), actionItem.getArg5()));     
    }
    public void deleteAction(DialPlanAction act){
        actions.remove(act);
        action="hangup";
        showHideActions();
    }
    public void editAction(DialPlanAction act){
        action=act.getAct();
        showHideActions();
        actionItem=act;  
        ediction=true;
    }
    public String translateActions(String act){
        return new Translate().translateDialplanAction(act);
    }
    public String translateActionsParam(String act,String param){
        switch(act){
            case "playbacks":
                for (Moh m:mohs) {
                    if(Integer.parseInt(param)==m.getId())
                        return m.getName();
                }     
                return "Indefinido";
            case "ccost":
                for (CenterCost cc:centerCosts) {
                    if(Integer.parseInt(param)==cc.getId())
                        return cc.getName();
                }
                return "Indefinido";
            case "trunkRoute":
                for (Route r:routes) {
                    if(Integer.parseInt(param)==r.getId())
                        return r.getName();
                }
                return "Indefinido";
            case "callgrp":
                for (Queue call:callGrps) {
                    if(Integer.parseInt(param)==call.getId())
                        return call.getWebName();
                }
                return "Indefinido";
            case "queue":
                for (Queue queue:callQueues) {
                    if(Integer.parseInt(param)==queue.getId())
                        return queue.getWebName();
                }
                return "Indefinido";
            case "ura":
                for (Ura u:uras) {
                    if(Integer.parseInt(param)==u.getId())
                        return u.getWebName();
                }
                return "Indefinido";
            case "company":
                for (Company c:companys) {
                    if(Integer.parseInt(param)==c.getId())
                        return c.getName();
                }
                return "Indefinido";
            case "accode":
                for (AcCode ac:accodes) {
                    if(Integer.parseInt(param)==ac.getId())
                        return ac.getName();
                }
                return "Indefinido";
            case "pickupGrp":
                for (PickupGrp pic:pickupgrps) {
                    if(Integer.parseInt(param)==pic.getId())
                        return pic.getName();
                }
                return "Indefinido";
            case "receiveFax":
                if(param.equals("1"))
                    return "Email Ativo";
                return "Email Desativado";
            case "bdo":
                for (WebServerBdo w : bdos) {
                    if(Integer.parseInt(param)==w.getId())
                        return w.getName();
                }
                return "Indefinido";
            default:
                return param;
        }
    }
    public void saveEdiction(){
        FacesContext.getCurrentInstance().addMessage(null,
                      new FacesMessage(FacesMessage.SEVERITY_INFO,
                              "Edição salva com sucesso.", ""));
    }
    public String persistNewDialplan(){ // MUITA COISA PRA VER
        if(actions.isEmpty()){
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "A Regra deve ter Ações!", ""));
            return null;
        }
        //dialplan.setDst(dialplan.getDst().toUpperCase());
        dialplan.setSrcAlias(0);
        if(srcAlias)
            dialplan.setSrcAlias(Integer.parseInt(dialplan.getSrcAction()));
        dialplan.setDstAlias(0);
        if(dstAlias){
            dialplan.setDstAlias(Integer.parseInt(dialplan.getDst()));
            dialplan.setDst("ALIAS");           
        }     
        dialplan.setPriority(1);
        for (DialPlanRule d : dialplans) {
            if(d.getDst().equals(dialplan.getDst()))
                dialplan.setPriority(dialplan.getPriority()+1);
        }
        
        dialplan.setFulltime(1);
        if(!valid24){
            dialplan.setFulltime(0);
            Calendar day=Calendar.getInstance(); //POE HORA SELECIONADA
            day.setTime(timeStart);
            String hour=day.get(Calendar.HOUR_OF_DAY)<10?"0"+day.get(Calendar.HOUR_OF_DAY):String.valueOf(day.get(Calendar.HOUR_OF_DAY));
            String minute=day.get(Calendar.MINUTE)<10?"0"+day.get(Calendar.MINUTE):String.valueOf(day.get(Calendar.MINUTE));
            dialplan.setTimeStart(hour+":"+minute);
            day.setTime(timeEnd);
            hour=day.get(Calendar.HOUR_OF_DAY)<10?"0"+day.get(Calendar.HOUR_OF_DAY):String.valueOf(day.get(Calendar.HOUR_OF_DAY));
            minute=day.get(Calendar.MINUTE)<10?"0"+day.get(Calendar.MINUTE):String.valueOf(day.get(Calendar.MINUTE));
            dialplan.setTimeEnd(hour+":"+minute);
            for (String str : week) {
                switch(str){
                    case "seg":
                        dialplan.setSeg(2);
                        break;
                    case "ter":
                        dialplan.setTer(3);
                        break;
                    case "qua":
                        dialplan.setQua(4);
                        break;
                    case "qui":
                        dialplan.setQui(5);
                        break;
                    case "sex":
                        dialplan.setSex(6);
                        break;
                    case "sab":
                        dialplan.setSab(7);
                        break;
                    case "dom":
                        dialplan.setDom(1);                      
                }
            }
        }
        if(!dialplans.contains(dialplan)){
            for (int i = 0; i < actions.size(); i++) {
                DialPlanAction da=actions.get(i);
                da.setPriority(i);
            }
            dialplan.setActions(actions);
            String ret=new DialPlanRuleDAO().persistNewDialplan(dialplan);
            if(!ret.equals("ok")){
                FacesContext.getCurrentInstance().addMessage(null,
                      new FacesMessage(FacesMessage.SEVERITY_FATAL,
                              ret, ""));
                return null;
            }
        }
        else
            FacesContext.getCurrentInstance().addMessage(null,
                      new FacesMessage(FacesMessage.SEVERITY_FATAL,
                              "Existe uma regra exatamente igual! A Regra não foi criada.", ""));       
        return "/restricted/dialplans";
    }

    public List<Company> getCompanys() {
        return companys;
    }

    public boolean isEdiction() {
        return ediction;
    }

    public List<Ura> getUras() {
        return uras;
    }

    public List<Queue> getCallGrps() {
        return callGrps;
    }

    public List<Queue> getCallQueues() {
        return callQueues;
    }

    public DialPlanAction getActionItem() {
        return actionItem;
    }

    public List<CenterCost> getCenterCosts() {
        return centerCosts;
    }

    public void setActionItem(DialPlanAction actionItem) {
        this.actionItem = actionItem;
    }

    public List<Moh> getMohs() {
        return mohs;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public List<AcCode> getAccodes() {
        return accodes;
    }

    public List<WebServerBdo> getBdos() {
        return bdos;
    }
    
    public void showHideActions(){
        ediction=false;
        switch(action){
            case "hangup":
                actionItem=new DialPlanAction();
                actionItem.setAct("hangup");
                hangup=true;
                notImpl=false;
                playbacks=false;
                answer=false;
                dialPeer=false;
                dialAgent=false;
                editDst=false;
                editSrc=false;
                rollbackDst=false;
                rollbackSrc=false;
                command=false;
                ccost=false;
                trunkRoute=false;
                callgrp=false;
                queue=false;
                ura=false;
                voicemail=false;
                company=false;
                sendFax=false;
                receiveFax=false;
                accode=false;
                pickupGrp=false;
                ignoreVM=false;
                bdo=false;               
                break;
            case "playbacks":
                actionItem=new DialPlanAction();
                actionItem.setAct("playbacks");
                hangup=false;
                notImpl=false;
                playbacks=true;
                answer=false;
                dialPeer=false;
                dialAgent=false;
                editDst=false;
                editSrc=false;
                rollbackDst=false;
                rollbackSrc=false;
                command=false;
                ccost=false;
                trunkRoute=false;
                callgrp=false;
                queue=false;
                ura=false;
                voicemail=false;
                company=false;
                sendFax=false;
                receiveFax=false;
                accode=false;
                pickupGrp=false;
                ignoreVM=false;
                bdo=false;
                break;
            case "answer":
                actionItem=new DialPlanAction();
                actionItem.setAct("answer");
                hangup=false;
                notImpl=false;
                playbacks=false;
                answer=true;
                dialPeer=false;
                dialAgent=false;
                editDst=false;
                editSrc=false;
                rollbackDst=false;
                rollbackSrc=false;
                command=false;
                ccost=false;
                trunkRoute=false;
                callgrp=false;
                queue=false;
                ura=false;
                voicemail=false;
                company=false;
                sendFax=false;
                receiveFax=false;
                accode=false;
                pickupGrp=false;
                ignoreVM=false;
                bdo=false;
                break;
            case "dialPeer":
                actionItem=new DialPlanAction();
                actionItem.setAct("dialPeer");
                actionItem.setArg2("60");
                actionItem.setArg3("0");
                actionItem.setArg4("t");
                hangup=false;
                notImpl=false;
                playbacks=false;
                answer=false;
                dialPeer=true;
                dialAgent=false;
                editDst=false;
                editSrc=false;
                rollbackDst=false;
                rollbackSrc=false;
                command=false;
                ccost=false;
                trunkRoute=false;
                callgrp=false;
                queue=false;
                ura=false;
                voicemail=false;
                company=false;
                sendFax=false;
                receiveFax=false;
                accode=false;
                pickupGrp=false;
                ignoreVM=false;
                bdo=false;
                break;
            case "dialAgent":
                actionItem=new DialPlanAction();
                actionItem.setAct("dialAgent");
                actionItem.setArg2("60");
                actionItem.setArg3("0");
                actionItem.setArg4("t");
                hangup=false;
                notImpl=false;
                playbacks=false;
                answer=false;
                dialPeer=false;
                dialAgent=true;
                editDst=false;
                editSrc=false;
                rollbackDst=false;
                rollbackSrc=false;
                command=false;
                ccost=false;
                trunkRoute=false;
                callgrp=false;
                queue=false;
                ura=false;
                voicemail=false;
                company=false;
                sendFax=false;
                receiveFax=false;
                accode=false;
                pickupGrp=false;
                ignoreVM=false;
                bdo=false;
                break;
            case "editDst":
                actionItem=new DialPlanAction();
                actionItem.setAct("editDst");
                actionItem.setArg2("0");
                actionItem.setArg3("0");
                actionItem.setArg4("0");
                hangup=false;
                notImpl=false;
                playbacks=false;
                answer=false;
                dialPeer=false;
                dialAgent=false;
                editDst=true;
                editSrc=false;
                rollbackDst=false;
                rollbackSrc=false;
                command=false;
                ccost=false;
                trunkRoute=false;
                callgrp=false;
                queue=false;
                ura=false;
                voicemail=false;
                company=false;
                sendFax=false;
                receiveFax=false;
                accode=false;
                pickupGrp=false;
                ignoreVM=false;
                bdo=false;
                break;
            case "editSrc":
                actionItem=new DialPlanAction();
                actionItem.setAct("editSrc");
                actionItem.setArg2("0");
                actionItem.setArg3("0");
                hangup=false;
                notImpl=false;
                playbacks=false;
                answer=false;
                dialPeer=false;
                dialAgent=false;
                editDst=false;
                editSrc=true;
                rollbackDst=false;
                rollbackSrc=false;
                command=false;
                ccost=false;
                trunkRoute=false;
                callgrp=false;
                queue=false;
                ura=false;
                voicemail=false;
                company=false;
                sendFax=false;
                receiveFax=false;
                accode=false;
                pickupGrp=false;
                ignoreVM=false;
                bdo=false;
                break;
            case "rollbackDst":
                actionItem=new DialPlanAction();
                actionItem.setAct("rollbackDst");
                hangup=false;
                notImpl=false;
                playbacks=false;
                answer=false;
                dialPeer=false;
                dialAgent=false;
                editDst=false;
                editSrc=false;
                rollbackDst=true;
                rollbackSrc=false;
                command=false;
                ccost=false;
                trunkRoute=false;
                callgrp=false;
                queue=false;
                ura=false;
                voicemail=false;
                company=false;
                sendFax=false;
                receiveFax=false;
                accode=false;
                pickupGrp=false;
                ignoreVM=false;
                bdo=false;
                break;
            case "rollbackSrc":
                actionItem=new DialPlanAction();
                actionItem.setAct("rollbackSrc");
                hangup=false;
                notImpl=false;
                playbacks=false;
                answer=false;
                dialPeer=false;
                dialAgent=false;
                editDst=false;
                editSrc=false;
                rollbackDst=false;
                rollbackSrc=true;
                command=false;
                ccost=false;
                trunkRoute=false;
                callgrp=false;
                queue=false;
                ura=false;
                voicemail=false;
                company=false;
                sendFax=false;
                receiveFax=false;
                accode=false;
                pickupGrp=false;
                ignoreVM=false;
                bdo=false;
                break;
            case "command":
                actionItem=new DialPlanAction();
                actionItem.setAct("command");
                hangup=false;
                notImpl=false;
                playbacks=false;
                answer=false;
                dialPeer=false;
                dialAgent=false;
                editDst=false;
                editSrc=false;
                rollbackDst=false;
                rollbackSrc=false;
                command=true;
                ccost=false;
                trunkRoute=false;
                callgrp=false;
                queue=false;
                ura=false;
                voicemail=false;
                company=false;
                sendFax=false;
                receiveFax=false;
                accode=false;
                pickupGrp=false;
                ignoreVM=false;
                bdo=false;
                break;
            case "ccost":
                actionItem=new DialPlanAction();
                actionItem.setAct("ccost");
                hangup=false;
                notImpl=false;
                playbacks=false;
                answer=false;
                dialPeer=false;
                dialAgent=false;
                editDst=false;
                editSrc=false;
                rollbackDst=false;
                rollbackSrc=false;
                command=false;
                ccost=true;
                trunkRoute=false;
                callgrp=false;
                queue=false;
                ura=false;
                voicemail=false;
                company=false;
                sendFax=false;
                receiveFax=false;
                accode=false;
                pickupGrp=false;
                ignoreVM=false;
                bdo=false;
                break;
            case "trunkRoute":
                actionItem=new DialPlanAction();
                actionItem.setAct("trunkRoute");
                hangup=false;
                notImpl=false;
                playbacks=false;
                answer=false;
                dialPeer=false;
                dialAgent=false;
                editDst=false;
                editSrc=false;
                rollbackDst=false;
                rollbackSrc=false;
                command=false;
                ccost=false;
                trunkRoute=true;
                callgrp=false;
                queue=false;
                ura=false;
                voicemail=false;
                company=false;
                sendFax=false;
                receiveFax=false;
                accode=false;
                pickupGrp=false;
                ignoreVM=false;
                bdo=false;
                break;
            case "callgrp":
                actionItem=new DialPlanAction();
                actionItem.setAct("callgrp");
                actionItem.setArg2("120");
                hangup=false;
                notImpl=false;
                playbacks=false;
                answer=false;
                dialPeer=false;
                dialAgent=false;
                editDst=false;
                editSrc=false;
                rollbackDst=false;
                rollbackSrc=false;
                command=false;
                ccost=false;
                trunkRoute=false;
                callgrp=true;
                queue=false;
                ura=false;
                voicemail=false;
                company=false;
                sendFax=false;
                receiveFax=false;
                accode=false;
                pickupGrp=false;
                ignoreVM=false;
                bdo=false;
                break;
            case "queue":
                actionItem=new DialPlanAction();
                actionItem.setAct("queue");
                actionItem.setArg2("125");
                hangup=false;
                notImpl=false;
                playbacks=false;
                answer=false;
                dialPeer=false;
                dialAgent=false;
                editDst=false;
                editSrc=false;
                rollbackDst=false;
                rollbackSrc=false;
                command=false;
                ccost=false;
                trunkRoute=false;
                callgrp=false;
                queue=true;
                ura=false;
                voicemail=false;
                company=false;
                sendFax=false;
                receiveFax=false;
                accode=false;
                pickupGrp=false;
                ignoreVM=false;
                bdo=false;
                break;
            case "ura":
                actionItem=new DialPlanAction();
                actionItem.setAct("ura");
                hangup=false;
                notImpl=false;
                playbacks=false;
                answer=false;
                dialPeer=false;
                dialAgent=false;
                editDst=false;
                editSrc=false;
                rollbackDst=false;
                rollbackSrc=false;
                command=false;
                ccost=false;
                trunkRoute=false;
                callgrp=false;
                queue=false;
                ura=true;
                voicemail=false;
                company=false;
                sendFax=false;
                receiveFax=false;
                accode=false;
                pickupGrp=false;
                ignoreVM=false;
                bdo=false;
                break;
            case "voicemail":
                actionItem=new DialPlanAction();
                actionItem.setAct("voicemail");
                hangup=false;
                notImpl=false;
                playbacks=false;
                answer=false;
                dialPeer=false;
                dialAgent=false;
                editDst=false;
                editSrc=false;
                rollbackDst=false;
                rollbackSrc=false;
                command=false;
                ccost=false;
                trunkRoute=false;
                callgrp=false;
                queue=false;
                ura=false;
                voicemail=true;
                company=false;
                sendFax=false;
                receiveFax=false;
                accode=false;
                pickupGrp=false;
                ignoreVM=false;
                bdo=false;
                break;
            case "company":
                actionItem=new DialPlanAction();
                actionItem.setAct("company");
                hangup=false;
                notImpl=false;
                playbacks=false;
                answer=false;
                dialPeer=false;
                dialAgent=false;
                editDst=false;
                editSrc=false;
                rollbackDst=false;
                rollbackSrc=false;
                command=false;
                ccost=false;
                trunkRoute=false;
                callgrp=false;
                queue=false;
                ura=false;
                voicemail=false;
                company=true;
                sendFax=false;
                receiveFax=false;
                accode=false;
                pickupGrp=false;
                ignoreVM=false;
                bdo=false;
                break;
            case "sendFax":
                actionItem=new DialPlanAction();
                actionItem.setAct("sendFax");
                hangup=false;
                notImpl=false;
                playbacks=false;
                answer=false;
                dialPeer=false;
                dialAgent=false;
                editDst=false;
                editSrc=false;
                rollbackDst=false;
                rollbackSrc=false;
                command=false;
                ccost=false;
                trunkRoute=false;
                callgrp=false;
                queue=false;
                ura=false;
                voicemail=false;
                company=false;
                sendFax=true;
                receiveFax=false;
                accode=false;
                pickupGrp=false;
                ignoreVM=false;
                bdo=false;
                break;
            case "receiveFax":
                actionItem=new DialPlanAction();
                actionItem.setAct("receiveFax");
                actionItem.setArg1("0");
                hangup=false;
                notImpl=false;
                playbacks=false;
                answer=false;
                dialPeer=false;
                dialAgent=false;
                editDst=false;
                editSrc=false;
                rollbackDst=false;
                rollbackSrc=false;
                command=false;
                ccost=false;
                trunkRoute=false;
                callgrp=false;
                queue=false;
                ura=false;
                voicemail=false;
                company=false;
                sendFax=false;
                receiveFax=true;
                accode=false;
                pickupGrp=false;
                ignoreVM=false;
                bdo=false;
                break;
            case "accode":
                actionItem=new DialPlanAction();
                actionItem.setAct("accode");
                actionItem.setArg2("3");
                hangup=false;
                notImpl=false;
                playbacks=false;
                answer=false;
                dialPeer=false;
                dialAgent=false;
                editDst=false;
                editSrc=false;
                rollbackDst=false;
                rollbackSrc=false;
                command=false;
                ccost=false;
                trunkRoute=false;
                callgrp=false;
                queue=false;
                ura=false;
                voicemail=false;
                company=false;
                sendFax=false;
                receiveFax=false;
                accode=true;
                pickupGrp=false;
                ignoreVM=false;
                bdo=false;
                break;
            case "pickupGrp":
                actionItem=new DialPlanAction();
                actionItem.setAct("pickupGrp");
                hangup=false;
                notImpl=false;
                playbacks=false;
                answer=false;
                dialPeer=false;
                dialAgent=false;
                editDst=false;
                editSrc=false;
                rollbackDst=false;
                rollbackSrc=false;
                command=false;
                ccost=false;
                trunkRoute=false;
                callgrp=false;
                queue=false;
                ura=false;
                voicemail=false;
                company=false;
                sendFax=false;
                receiveFax=false;
                accode=false;
                pickupGrp=true;
                ignoreVM=false;
                bdo=false;
                break;
            case "ignoreVM":
                actionItem=new DialPlanAction();
                actionItem.setAct("ignoreVM");
                hangup=false;
                notImpl=false;
                playbacks=false;
                answer=false;
                dialPeer=false;
                dialAgent=false;
                editDst=false;
                editSrc=false;
                rollbackDst=false;
                rollbackSrc=false;
                command=false;
                ccost=false;
                trunkRoute=false;
                callgrp=false;
                queue=false;
                ura=false;
                voicemail=false;
                company=false;
                sendFax=false;
                receiveFax=false;
                accode=false;
                pickupGrp=false;
                ignoreVM=true;
                bdo=false;
                break;
            case "bdo":
                actionItem=new DialPlanAction();
                actionItem.setAct("bdo");
                hangup=false;
                notImpl=false;
                playbacks=false;
                answer=false;
                dialPeer=false;
                dialAgent=false;
                editDst=false;
                editSrc=false;
                rollbackDst=false;
                rollbackSrc=false;
                command=false;
                ccost=false;
                trunkRoute=false;
                callgrp=false;
                queue=false;
                ura=false;
                voicemail=false;
                company=false;
                sendFax=false;
                receiveFax=false;
                accode=false;
                pickupGrp=false;
                ignoreVM=false;
                bdo=true;
                break;
            
            default:
                hangup=false;
                notImpl=true;
                playbacks=false;
                answer=false;
                dialPeer=false;
                dialAgent=false;
                editDst=false;
                editSrc=false;
                rollbackDst=false;
                rollbackSrc=false;
                command=false;
                ccost=false;
                trunkRoute=false;
                callgrp=false;
                queue=false;
                ura=false;
                voicemail=false;
                company=false;
                sendFax=false;
                receiveFax=false;
                accode=false;
                pickupGrp=false;
                ignoreVM=false;
                bdo=false;
                break;           
        }
    }
    public boolean isAnswer() {
        return answer;
    }

    public boolean isDialPeer() {
        return dialPeer;
    }

    public boolean isDialAgent() {
        return dialAgent;
    }

    public boolean isEditDst() {
        return editDst;
    }

    public boolean isEditSrc() {
        return editSrc;
    }

    public boolean isRollbackDst() {
        return rollbackDst;
    }

    public boolean isRollbackSrc() {
        return rollbackSrc;
    }

    public boolean isCommand() {
        return command;
    }

    public boolean isCcost() {
        return ccost;
    }


    public boolean isTrunkRoute() {
        return trunkRoute;
    }

    public boolean isCallgrp() {
        return callgrp;
    }

    public boolean isQueue() {
        return queue;
    }

    public boolean isUra() {
        return ura;
    }

    public boolean isVoicemail() {
        return voicemail;
    }

    public boolean isCompany() {
        return company;
    }

    public boolean isSendFax() {
        return sendFax;
    }

    public boolean isReceiveFax() {
        return receiveFax;
    }

    public boolean isAccode() {
        return accode;
    }

    public boolean isPickupGrp() {
        return pickupGrp;
    }

    public boolean isIgnoreVM() {
        return ignoreVM;
    }

    public boolean isBdo() {
        return bdo;
    }

    public List<DialPlanAction> getActions() {
        return actions;
    }

    public void setActions(List<DialPlanAction> actions) {
        this.actions = actions;
    }

    public boolean isHangup() {
        return hangup;
    }

    public boolean isNotImpl() {
        return notImpl;
    }

    public boolean isPlaybacks() {
        return playbacks;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public Date getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Date timeEnd) {
        this.timeEnd = timeEnd;
    }

    public List<String> getWeek() {
        return week;
    }

    public void setWeek(List<String> week) {
        this.week = week;
    }

    public boolean isValid24() {
        return valid24;
    }

    public void setValid24(boolean valid24) {
        this.valid24 = valid24;
    }

    public void setDstAlias(boolean dstAlias) {
        this.dstAlias = dstAlias;
    }

    public boolean isDstAlias() {
        return dstAlias;
    }

    public List<Alias> getAliases() {
        return aliases;
    }

    public List<Trunk> getTrunks() {
        return trunks;
    }

    public List<PickupGrp> getPickupgrps() {
        return pickupgrps;
    }

    public void changeSrcAction(){
        switch(dialplan.getSrc()){
            case "peer":
                srcPeer=true;
                srcPick=false;
                srcTrunk=false;
                srcRegex=false;
                srcQlqr=false;
                srcFax=false;
                srcAlias=false;
                break;
            case "pickupgrp":
                srcPeer=false;
                srcPick=true;
                srcTrunk=false;
                srcRegex=false;
                srcQlqr=false;
                srcFax=false;
                srcAlias=false;
                break;
            case "trunk":
                srcPeer=false;
                srcPick=false;
                srcTrunk=true;
                srcRegex=false;
                srcQlqr=false;
                srcFax=false;
                srcAlias=false;
                break;
            case "regex":
                srcPeer=false;
                srcPick=false;
                srcTrunk=false;
                srcRegex=true;
                srcQlqr=false;
                srcFax=false;
                srcAlias=false;
                break;
            case "qlqr":
                srcPeer=false;
                srcPick=false;
                srcTrunk=false;
                srcRegex=false;
                srcQlqr=true;
                srcFax=false;
                srcAlias=false;
                break;
            case "fax":
                srcPeer=false;
                srcPick=false;
                srcTrunk=false;
                srcRegex=false;
                srcQlqr=false;
                srcFax=true;
                srcAlias=false;
                break;
            case "alias":
                srcPeer=false;
                srcPick=false;
                srcTrunk=false;
                srcRegex=false;
                srcQlqr=false;
                srcFax=false;
                srcAlias=true;
                break;
        }
    }

    public List<Peer> getPeers() {
        return peers;
    }
    
    public DialPlanRule getDialplan() {
        return dialplan;
    }

    public void setDialplan(DialPlanRule dialplan) {
        this.dialplan = dialplan;
    }

    public boolean isSrcPeer() {
        return srcPeer;
    }

    public boolean isSrcPick() {
        return srcPick;
    }

    public boolean isSrcTrunk() {
        return srcTrunk;
    }

    public boolean isSrcRegex() {
        return srcRegex;
    }

    public boolean isSrcQlqr() {
        return srcQlqr;
    }

    public boolean isSrcFax() {
        return srcFax;
    }

    public boolean isSrcAlias() {
        return srcAlias;
    }
    
    
    
}
