/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.model;

import br.com.jpbx.util.Biller;
import br.com.jpbx.util.FormaterSeconds;
import br.com.jpbx.util.HangupCauseFilter;
import br.com.jpbx.util.RelCallFilter;
import br.com.jpbx.util.StatistcPeer;
import br.com.jpbx.util.TrunkDetraf;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import org.primefaces.model.SortOrder;

/**
 *
 * @author jefaokpta
 */
public class RelCallDAO {
    
    private EntityManager em;

    public RelCallDAO() {
        em=new ConnectionFactory().getEm();
    }
    
    public List<RelCall> getRelCalls(int company){       
        try{
            String sql="select r from RelCall as r where r.company="+company;
            TypedQuery <RelCall> query=em.createQuery(sql,RelCall.class);
            return query.getResultList();
        }finally{em.close();}
  
    }
    public List<RelCall> getRelCalls(int company,int firstReg, int qtdeReg,String orderField,SortOrder sortOrder,Map<String, Object> filters,RelCallFilter form){       
        try{
            String order=""; // ORDENACAO
            if(orderField!=null){
                order=" ORDER BY r."+orderField+" DESC";
                if(SortOrder.ASCENDING.equals(sortOrder))
                    order=" ORDER BY r."+orderField+" ASC";
            }
            // AGORA FILTROS DA TABELA
            String filter="";
            for (Map.Entry<String,Object> f :filters.entrySet()) {
                //System.out.println(":::::::::::::::: FILTROS: "+f.getKey()+" - "+f.getValue()+" ::::::::::::::::::::::::::::::::");
                filter+=" AND r."+f.getKey()+" LIKE '%"+f.getValue()+"%'";
            }
            // FILTROS DO FORMULARIO CENTER COST
            String formSql="";
            if(!form.getAccTarget().isEmpty()){
                formSql=form.getAccTarget().toString().replace("[", "(");
                formSql=formSql.replace("]", ")");
                formSql=" AND r.accountcode IN "+formSql;
            }
            // RAMAIS
            String peers="";
            if(!form.getPeers().isEmpty())
                peers=" AND r.src IN ("+form.getPeers()+")";
            // FILTROS DO FORMULARIO AC CODE
            String formAcCode="";
            if(!form.getAcCodeTarget().isEmpty()){
                formAcCode=form.getAcCodeTarget().toString().replace("[", "(");
                formAcCode=formAcCode.replace("]", ")");
                formAcCode=" AND r.accode IN "+formAcCode;
            }
            String status=(form.getStatus().equals("ALL")?"":" AND r.disposition='"+form.getStatus()+"'"); // FILTRO STATUS
            
            String sql="SELECT r FROM RelCall as r WHERE r.calldate BETWEEN :startDate AND :endDate AND r.company="+company+status+formSql+peers+formAcCode+filter+order;
            TypedQuery <RelCall> query=em.createQuery(sql,RelCall.class);
            query.setParameter("startDate", form.getStartDate(),TemporalType.TIMESTAMP);
            query.setParameter("endDate", form.getEndDate(),TemporalType.TIMESTAMP);
            query.setFirstResult(firstReg);
            query.setMaxResults(qtdeReg);
            
            return query.getResultList();
        }finally{em.close();}
  
    }
    public int getRelCallsCount(int company,Map<String,Object> filters,RelCallFilter form){   
        try{
            // AGORA FILTROS DA TABELA
            String filter="";
            for (Map.Entry<String,Object> f :filters.entrySet()) {
                filter+=" AND r."+f.getKey()+" LIKE '%"+f.getValue()+"%'";
            }
            // FILTROS DO FORMULARIO
            String formSql="";
            if(!form.getAccTarget().isEmpty()){
                formSql=form.getAccTarget().toString().replace("[", "(");
                formSql=formSql.replace("]", ")");
                formSql=" AND r.accountcode IN "+formSql;
            }
            // RAMAIS
            String peers="";
            if(!form.getPeers().isEmpty())
                peers=" AND r.src IN ("+form.getPeers()+")";
            // FILTROS DO FORMULARIO AC CODE
            String formAcCode="";
            if(!form.getAcCodeTarget().isEmpty()){
                formAcCode=form.getAcCodeTarget().toString().replace("[", "(");
                formAcCode=formAcCode.replace("]", ")");
                formAcCode=" AND r.accode IN "+formAcCode;
            }
            String status=(form.getStatus().equals("ALL")?"":" AND r.disposition='"+form.getStatus()+"'"); // FILTRO STATUS
            String sql="SELECT COUNT(r.id) FROM  RelCall as r WHERE r.calldate  BETWEEN :startDate AND :endDate AND r.company="+company+status+formSql+peers+formAcCode+filter;
            Query query=em.createQuery(sql);
            query.setParameter("startDate", form.getStartDate(),TemporalType.TIMESTAMP);
            query.setParameter("endDate", form.getEndDate(),TemporalType.TIMESTAMP);
            return Integer.parseInt(query.getSingleResult().toString());
        }finally{em.close();}
    }
    public String[] getRelCallsTotals(RelCallFilter form,List<CenterCost> ccosts){   
        try{
            // FILTROS DO FORMULARIO
            String formSql="";
            if(!form.getAccTarget().isEmpty()){
                formSql=form.getAccTarget().toString().replace("[", "(");
                formSql=formSql.replace("]", ")");
                formSql=" AND accountcode IN "+formSql;
            }
            // RAMAIS
            String peers="";
            if(!form.getPeers().isEmpty())
                peers=" AND src IN ("+form.getPeers()+")";
            // FILTROS DO FORMULARIO AC CODE
            String formAcCode="";
            if(!form.getAcCodeTarget().isEmpty()){
                formAcCode=form.getAcCodeTarget().toString().replace("[", "(");
                formAcCode=formAcCode.replace("]", ")");
                formAcCode=" AND accode IN "+formAcCode;
            }
            String status=(form.getStatus().equals("ALL")?"":" AND disposition='"+form.getStatus()+"'"); // FILTRO STATUS

            String startDate=new Timestamp(form.getStartDate().getTime()).toString().split("\\.")[0];
            String endDate=new Timestamp(form.getEndDate().getTime()).toString().split("\\.")[0];
            String sql="SELECT disposition,accountcode,billsec FROM relcalls WHERE calldate BETWEEN '"+startDate+"' AND '"+endDate+"' AND company="+form.getCompany()+" "
                    + status+formSql+peers+formAcCode;
            Query query=em.createNativeQuery(sql);
            List<Object[]> ret = query.getResultList();
            int answered = 0;
            int calls = 0;
            int noAnswered = 0;
            int busy = 0;
            int other = 0;
            float total=0;
            int seconds=0;
            
            for (Object[] obj : ret) {
                switch(obj[0].toString()){
                    case "ANSWERED":
                        answered++;
                        break;
                    case "NO ANSWER":
                        noAnswered++;
                        break;
                    case "BUSY":
                        busy++;
                        break;
                    default:
                        other++;
                }  
                calls++;
                seconds+=Integer.parseInt(obj[2].toString());
                if(!obj[1].toString().equals("0")&&!obj[2].toString().equals("0")){
                    for (CenterCost cc :ccosts) {
                        if(cc.getId()==Integer.parseInt(obj[1].toString())){
                            total+=new Biller().accountCall(Integer.parseInt(obj[2].toString()), cc);
                            break;
                        }
                    }
                }
            }
            return new String[]{String.valueOf(answered),String.valueOf(noAnswered),String.valueOf(busy),String.valueOf(other),String.valueOf(calls),new DecimalFormat("0.00").format(total).replace(".", ","),
                String.valueOf(seconds/60)};
        }finally{em.close();}
    }
    public List getRelCallsToGrafic(RelCallFilter form){
        // FILTROS DO FORMULARIO
        try{
            String formSql="";
            if(!form.getAccTarget().isEmpty()){
                formSql=form.getAccTarget().toString().replace("[", "(");
                formSql=formSql.replace("]", ")");
                formSql=" AND accountcode IN "+formSql;
            }
            // RAMAIS
            String peers="";
            if(!form.getPeers().isEmpty())
                peers=" AND src IN ("+form.getPeers()+")";
            // FILTROS DO FORMULARIO AC CODE
            String formAcCode="";
            if(!form.getAcCodeTarget().isEmpty()){
                formAcCode=form.getAcCodeTarget().toString().replace("[", "(");
                formAcCode=formAcCode.replace("]", ")");
                formAcCode=" AND accode IN "+formAcCode;
            }
            String status=(form.getStatus().equals("ALL")?"":" AND disposition='"+form.getStatus()+"'"); // FILTRO STATUS

            String startDate=new Timestamp(form.getStartDate().getTime()).toString().split("\\.")[0];
            String endDate=new Timestamp(form.getEndDate().getTime()).toString().split("\\.")[0];
            String sql="SELECT accountcode FROM relcalls WHERE calldate BETWEEN '"+startDate+"' AND '"+endDate+"' AND company="+form.getCompany()+
                    status+formSql+peers+formAcCode+" group by accountcode";
            Query query=em.createNativeQuery(sql);
            List res=new ArrayList();
            String sql2;
            Calendar dayStart=Calendar.getInstance();         
            Calendar dayEnd=Calendar.getInstance();
            dayEnd.setTime(form.getEndDate());
            String currentDay;
            for (Object obj : query.getResultList()) {
                dayStart.setTime(form.getStartDate());
                for(Calendar d=dayStart;!d.after(dayEnd);d.add(Calendar.DAY_OF_MONTH, 1)){
                    currentDay=new Timestamp(d.getTime().getTime()).toString().split(" ")[0];
                    sql2="SELECT count(*),calldate,accountcode " +
                        "FROM relcalls WHERE calldate BETWEEN '"+currentDay+" 00:00:00' AND '"+currentDay+" 23:59:59' AND company="+form.getCompany()+" AND accountcode="+obj.toString()+
                            status+formAcCode;
                    Query query2=em.createNativeQuery(sql2);
                    Object[] line=(Object[])query2.getSingleResult();
                    if(line[0].toString().equals("0")){
                        line[1]=currentDay+" 12:00:00";
                        line[2]=obj.toString();                      
                    }
                    res.add(line);
                }                             
            }
            return res;
        }finally{em.close();}
    }
    
    
    public List<RelCall> getCallsRouteToDebit(int route){
        try{
            String sql="select r from RelCall as r where r.calldate like concat(substr(now(),1,10),'%') and r.route="+route+" and r.debitRoute=0";
            TypedQuery <RelCall> query=em.createQuery(sql,RelCall.class);
            return query.getResultList();
        }finally{em.close();}
    }
    public List<RelCall> getCallsProfileToDebit(int profile){
        try{
            String sql="select r from RelCall as r where r.calldate like concat(substr(now(),1,10),'%') and r.profile="+profile+" and r.debitProfile=0";
            TypedQuery <RelCall> query=em.createQuery(sql,RelCall.class);
            return query.getResultList();
        }finally{em.close();}
    }
    public String hideHistory(RelCall r){
        EntityTransaction tx=em.getTransaction();
        try{
            tx.begin();
            em.merge(r);
            tx.commit();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            return "DAO: "+ex.getMessage();
        }finally{em.close();}
        return "ok";
    }
    public List<TrunkDetraf> getRelDetraf(HangupCauseFilter form,List<TrunkCost> trunkCosts,List<CenterCost> centerCosts){       
        try{         
            // FILTROS DO FORMULARIO TRUNKS
            String formSql="";
            if(!form.getTrunks().isEmpty()){
                List<String> trunks=new ArrayList<>();
                for (String s : form.getTrunks()) {
                    trunks.add(s.split("-")[0].trim());
                }
                formSql=trunks.toString().replace("[", "(");
                formSql=formSql.replace("]", ")");
                formSql=" AND r.trunkId IN "+formSql;
            }          
            String sql="SELECT r FROM RelCallNoHistory as r WHERE r.calldate BETWEEN :startDate AND :endDate AND r.trunkId > 0 AND r.billsec >0"+formSql;
            TypedQuery <RelCallNoHistory> query=em.createQuery(sql,RelCallNoHistory.class);
            query.setParameter("startDate", form.getStart(),TemporalType.TIMESTAMP);
            query.setParameter("endDate", form.getEnd(),TemporalType.TIMESTAMP);
            
            List<TrunkDetraf> res=new ArrayList<>();
            Biller biller=new Biller();
            FormaterSeconds fs=new FormaterSeconds();
            for (RelCallNoHistory rc : query.getResultList()) {
                float cost=0;
                int accodeFromCall = 0;
                for (CenterCost cc : centerCosts) {
                    if(cc.getId()==rc.getAccountcode()){
                        accodeFromCall=(int) cc.getCcost();
                        break;
                    }
                }
                for (TrunkCost tc : trunkCosts) {
                    if(tc.getCcost()==accodeFromCall){
                        cost=biller.accountCall(rc.getBillsec(), tc);
                        break;
                    }
                }              
                float sale=0;
                String acc="Indefinido";
                for (CenterCost cc : centerCosts) {
                    if(cc.getId()==rc.getAccountcode()){
                        acc=cc.getName();
                        sale=biller.accountCall(rc.getBillsec(), cc);
                        break;
                    }
                }             
                String trunkName="Indefinido";
                for (String s : form.getTrunks()) {
                    if(s.split("-")[0].trim().equals(String.valueOf(rc.getTrunkId()))){
                        trunkName=s.split("-")[1];
                        break;
                    }
                }
                res.add(new TrunkDetraf(rc.getCalldate(), rc.getSrc(), rc.getDstfinal(),
                        fs.secondsToHours(rc.getDuration()),
                        fs.secondsToHours(rc.getBillsec()),
                        acc,
                        cost,
                        sale,
                        (sale-cost),
                        trunkName));
            }
            return res;
        }finally{em.close();}
  
    }
    public List<StatistcPeer> statistcsPeer(Date start, Date end, Peer peer){
        try{
            String sql="SELECT r FROM RelCall as r WHERE r.calldate BETWEEN :startDate AND :endDate";
            TypedQuery <RelCall> query=em.createQuery(sql,RelCall.class);
            query.setParameter("startDate", start,TemporalType.TIMESTAMP);
            query.setParameter("endDate", end,TemporalType.TIMESTAMP);
            List<StatistcPeer> res=new ArrayList<>();
            List<RelCall> calls=new ArrayList<>();
            String p=String.valueOf(peer.getName());
            for (RelCall r : query.getResultList()) {
                if(r.getSrc().equals(p)){
                    calls.add(r);
                    continue;
                }
                if(!r.getCallHistory().isEmpty()){
                    if(r.getCallHistory().get(0).getData2().equals(p))
                        calls.add(r);
                }
            }
            Calendar startDate=Calendar.getInstance();
            Calendar endDate=Calendar.getInstance();
            startDate.setTime(start);
            endDate.setTime(end);
            //endDate.add(Calendar.DAY_OF_MONTH, 2);
            int idControl = 0;
            for (Calendar i=startDate;i.before(endDate) ;i.add(Calendar.DAY_OF_MONTH, 1)) {
                StatistcPeer inbound=new StatistcPeer(i.getTime(), "Recebida");
                StatistcPeer outbound=new StatistcPeer(i.getTime(), "Efetuada");
                idControl++;
                inbound.setId(idControl);
                outbound.setId(idControl);
                for (RelCall call : calls) {
                    Calendar day=Calendar.getInstance();
                    day.setTime(call.getCalldate());
                    //if(String.valueOf(i.getTimeInMillis()).substring(0, 5).equals(String.valueOf(call.getCalldate().getTime()).substring(0, 5))){
                    String dayCall=day.get(Calendar.DAY_OF_MONTH)+"-"+day.get(Calendar.MONTH);
                    String dayLoop=i.get(Calendar.DAY_OF_MONTH)+"-"+i.get(Calendar.MONTH);
                    if(dayCall.equals(dayLoop)){
                        if(call.getSrc().equals(p)){
                            outbound.setId(call.getId());
                            outbound.setCallTotal(outbound.getCallTotal()+1);
                            outbound.setCallTimeTotal(outbound.getCallTimeTotal()+call.getDuration());
                            outbound.setCallTimeSpeak(outbound.getCallTimeSpeak()+call.getBillsec());
                            outbound.setCallTimeWait(outbound.getCallTimeWait()+(call.getDuration()-call.getBillsec()));
                            switch(call.getDisposition()){
                                case "ANSWERED":
                                    outbound.setAnswered(outbound.getAnswered()+1);
                                    break;
                                case "NO ANSWER":
                                    outbound.setNotAnswered(outbound.getNotAnswered()+1);
                                    break;
                                default:
                                    outbound.setBusy(outbound.getBusy()+1);
                            }
                            continue;
                        }
                        inbound.setId(call.getId());
                        inbound.setCallTotal(inbound.getCallTotal()+1);
                        inbound.setCallTimeTotal(inbound.getCallTimeTotal()+call.getDuration());
                        inbound.setCallTimeSpeak(inbound.getCallTimeSpeak()+call.getBillsec());
                        inbound.setCallTimeWait(inbound.getCallTimeWait()+(call.getDuration()-call.getBillsec()));
                        switch(call.getDisposition()){
                                case "ANSWERED":
                                    inbound.setAnswered(inbound.getAnswered()+1);
                                    break;
                                case "NO ANSWER":
                                    inbound.setNotAnswered(inbound.getNotAnswered()+1);
                                    break;
                                default:
                                    inbound.setBusy(inbound.getBusy()+1);
                            }
                    }
                }
                if(outbound.getCallTotal()>0){
                    outbound.setMeddleCall(outbound.getCallTimeTotal()/outbound.getCallTotal());
                    outbound.setMeddleSpeak(outbound.getCallTimeSpeak()/outbound.getCallTotal());
                    outbound.setMeddleWait(outbound.getCallTimeWait()/outbound.getCallTotal());     
                    res.add(outbound);
                    res.add(inbound);
                }
                if(inbound.getCallTotal()>0){
                    inbound.setMeddleCall(inbound.getCallTimeTotal()/inbound.getCallTotal());
                    inbound.setMeddleSpeak(inbound.getCallTimeSpeak()/inbound.getCallTotal());
                    inbound.setMeddleWait(inbound.getCallTimeWait()/inbound.getCallTotal()); 
                    res.add(outbound);
                    res.add(inbound);
                }               
            }
            return res;
        }finally{em.close();}
    }
    public List<StatistcPeer> statistcsAgent(Date start, Date end, Agent agent){
        try{
            String sql="SELECT r FROM RelCall as r WHERE r.calldate BETWEEN :startDate AND :endDate";
            TypedQuery <RelCall> query=em.createQuery(sql,RelCall.class);
            query.setParameter("startDate", start,TemporalType.TIMESTAMP);
            query.setParameter("endDate", end,TemporalType.TIMESTAMP);
            List<StatistcPeer> res=new ArrayList<>();
            List<RelCall> calls=new ArrayList<>();
            String p=String.valueOf(agent.getAgent());
            for (RelCall r : query.getResultList()) {
                if(r.getSrc().equals(p)){
                    calls.add(r);
                    continue;
                }
                if(!r.getCallHistory().isEmpty()){
                    if(r.getCallHistory().get(0).getData2().equals(p))
                        calls.add(r);
                }
            }
            Calendar startDate=Calendar.getInstance();
            Calendar endDate=Calendar.getInstance();
            startDate.setTime(start);
            endDate.setTime(end);
            //endDate.add(Calendar.DAY_OF_MONTH, 2);
            int idControl = 0;
            for (Calendar i=startDate;i.before(endDate) ;i.add(Calendar.DAY_OF_MONTH, 1)) {
                StatistcPeer inbound=new StatistcPeer(i.getTime(), "Recebida");
                StatistcPeer outbound=new StatistcPeer(i.getTime(), "Efetuada");
                idControl++;
                inbound.setId(idControl);
                outbound.setId(idControl);
                for (RelCall call : calls) {
                    Calendar day=Calendar.getInstance();
                    day.setTime(call.getCalldate());
                    //if(String.valueOf(i.getTimeInMillis()).substring(0, 5).equals(String.valueOf(call.getCalldate().getTime()).substring(0, 5))){
                    String dayCall=day.get(Calendar.DAY_OF_MONTH)+"-"+day.get(Calendar.MONTH);
                    String dayLoop=i.get(Calendar.DAY_OF_MONTH)+"-"+i.get(Calendar.MONTH);
                    if(dayCall.equals(dayLoop)){
                        if(call.getSrc().equals(p)){
                            outbound.setId(call.getId());
                            outbound.setCallTotal(outbound.getCallTotal()+1);
                            outbound.setCallTimeTotal(outbound.getCallTimeTotal()+call.getDuration());
                            outbound.setCallTimeSpeak(outbound.getCallTimeSpeak()+call.getBillsec());
                            outbound.setCallTimeWait(outbound.getCallTimeWait()+(call.getDuration()-call.getBillsec()));
                            switch(call.getDisposition()){
                                case "ANSWERED":
                                    outbound.setAnswered(outbound.getAnswered()+1);
                                    break;
                                case "NO ANSWER":
                                    outbound.setNotAnswered(outbound.getNotAnswered()+1);
                                    break;
                                default:
                                    outbound.setBusy(outbound.getBusy()+1);
                            }
                            continue;
                        }
                        inbound.setId(call.getId());
                        inbound.setCallTotal(inbound.getCallTotal()+1);
                        inbound.setCallTimeTotal(inbound.getCallTimeTotal()+call.getDuration());
                        inbound.setCallTimeSpeak(inbound.getCallTimeSpeak()+call.getBillsec());
                        inbound.setCallTimeWait(inbound.getCallTimeWait()+(call.getDuration()-call.getBillsec()));
                        switch(call.getDisposition()){
                                case "ANSWERED":
                                    inbound.setAnswered(inbound.getAnswered()+1);
                                    break;
                                case "NO ANSWER":
                                    inbound.setNotAnswered(inbound.getNotAnswered()+1);
                                    break;
                                default:
                                    inbound.setBusy(inbound.getBusy()+1);
                            }
                    }
                }
                if(outbound.getCallTotal()>0){
                    outbound.setMeddleCall(outbound.getCallTimeTotal()/outbound.getCallTotal());
                    outbound.setMeddleSpeak(outbound.getCallTimeSpeak()/outbound.getCallTotal());
                    outbound.setMeddleWait(outbound.getCallTimeWait()/outbound.getCallTotal());     
                    res.add(outbound);
                    res.add(inbound);
                }
                if(inbound.getCallTotal()>0){
                    inbound.setMeddleCall(inbound.getCallTimeTotal()/inbound.getCallTotal());
                    inbound.setMeddleSpeak(inbound.getCallTimeSpeak()/inbound.getCallTotal());
                    inbound.setMeddleWait(inbound.getCallTimeWait()/inbound.getCallTotal()); 
                    res.add(outbound);
                    res.add(inbound);
                }               
            }
            return res;
        }finally{em.close();}
    }
}