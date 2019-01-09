/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.model;

import br.com.jpbx.util.DacSinteticData;
import br.com.jpbx.util.DacSinteticFilter;
import br.com.jpbx.util.RelAgentDisponibilityFilter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class QueueLogDAO {

    private EntityManager em;

    public QueueLogDAO() {
        em=new ConnectionFactory().getEm();
    }
    
    public List<QueueLog> getRelCalls(RelAgentDisponibilityFilter filter){     
        List<String> agents=new ArrayList<>();
        for (Agent a : filter.getAgentSelected()) {
            agents.add(String.valueOf(a.getAgent()));
        }
        String agentsSql="";
        agentsSql=agents.toString().replace("[", "(");
        agentsSql=agentsSql.replace("]", ")");
        agentsSql=" AND q.agent IN "+agentsSql;

        try{
            String sql="SELECT q FROM QueueLog AS q WHERE q.dateLog BETWEEN :startDate AND :endDate"+agentsSql+" AND q.event IN ('ADDMEMBER','REMOVEMEMBER','PAUSE','UNPAUSE')";
            TypedQuery <QueueLog> query=em.createQuery(sql,QueueLog.class);
            query.setParameter("startDate", filter.getStartDate(),TemporalType.TIMESTAMP);
            query.setParameter("endDate", filter.getEndDate(),TemporalType.TIMESTAMP);
            return query.getResultList();
        }finally{em.close();}
  
    }
    public List<DacSinteticData> getDacSintetic(DacSinteticFilter filter){
        try{
            String sql="SELECT q FROM QueueLog AS q WHERE q.dateLog BETWEEN :startDate AND :endDate AND q.queuename = '"+filter.getQueue().getName()+
                    "' AND q.event IN ('ABANDON','ATTENDEDTRANSFER','BLINDTRANSFER','COMPLETEAGENT','COMPLETECALLER','EXITWITHTIMEOUT','TRANSFER','EXITEMPTY')";
            TypedQuery <QueueLog> query=em.createQuery(sql,QueueLog.class);
            query.setParameter("startDate", filter.getStart(),TemporalType.TIMESTAMP);
            query.setParameter("endDate", filter.getEnd(),TemporalType.TIMESTAMP);
            List<DacSinteticData> dsds=new ArrayList<>();
            DacSinteticData dsd;
            List<QueueLog> qls=query.getResultList();
            // PRECISA ANALISAR POR FOR DIA DAI FAZER AS SOMAS
            Calendar cst=Calendar.getInstance();
            Calendar cen=Calendar.getInstance();
            Calendar day=Calendar.getInstance();
            cst.setTime(filter.getStart());
            cen.setTime(filter.getEnd());
            String dayLoop;
            for (Calendar i=cst;i.before(cen);i.add(Calendar.DAY_OF_MONTH, 1)) {
                dsd=new DacSinteticData();
                dsd.setDay(i.getTime());
                dayLoop=i.get(Calendar.DAY_OF_MONTH)+"-"+i.get(Calendar.MONTH);
                for (QueueLog ql : qls) {
                    day.setTime(ql.getDateLog());
                    if(dayLoop.equals(day.get(Calendar.DAY_OF_MONTH)+"-"+day.get(Calendar.MONTH))){
                        dsd.setTotal(dsd.getTotal()+1);
                        switch(ql.getEvent()){
                            case "ABANDON":
                                dsd.setAbandon(dsd.getAbandon()+1);
                                dsd.setTme(dsd.getTme()+Integer.parseInt(ql.getData3()));
                                dsd.setTmc(dsd.getTmc()+Integer.parseInt(ql.getData3()));
                                break;
                            case "EXITWITHTIMEOUT":
                                dsd.setNoAnswer(dsd.getNoAnswer()+1);
                                dsd.setTme(dsd.getTme()+Integer.parseInt(ql.getData3()));
                                dsd.setTmc(dsd.getTmc()+Integer.parseInt(ql.getData3()));
                                break;
                            case "EXITEMPTY":
                                dsd.setNoAnswer(dsd.getNoAnswer()+1);
                                dsd.setTme(dsd.getTme()+Integer.parseInt(ql.getData3()));
                                dsd.setTmc(dsd.getTmc()+Integer.parseInt(ql.getData3()));
                                break;
                            case "ATTENDEDTRANSFER":
                                dsd.setAnswer(dsd.getAnswer()+1);
                                dsd.setTme(dsd.getTme()+Integer.parseInt(ql.getData3()));
                                dsd.setTma(dsd.getTma()+Integer.parseInt(ql.getData4()));
                                dsd.setTmc(dsd.getTmc()+(Integer.parseInt(ql.getData3())+Integer.parseInt(ql.getData4())));
                                break;
                            case "BLINDTRANSFER":
                                dsd.setAnswer(dsd.getAnswer()+1);
                                dsd.setTme(dsd.getTme()+Integer.parseInt(ql.getData3()));
                                dsd.setTma(dsd.getTma()+Integer.parseInt(ql.getData4()));
                                dsd.setTmc(dsd.getTmc()+(Integer.parseInt(ql.getData3())+Integer.parseInt(ql.getData4())));
                                break;
                            case "COMPLETEAGENT":
                                dsd.setAnswer(dsd.getAnswer()+1);
                                dsd.setTme(dsd.getTme()+Integer.parseInt(ql.getData1()));
                                dsd.setTma(dsd.getTma()+Integer.parseInt(ql.getData2()));
                                dsd.setTmc(dsd.getTmc()+(Integer.parseInt(ql.getData1())+Integer.parseInt(ql.getData2())));
                                break;
                            case "COMPLETECALLER":
                                dsd.setAnswer(dsd.getAnswer()+1);
                                dsd.setTme(dsd.getTme()+Integer.parseInt(ql.getData1()));
                                dsd.setTma(dsd.getTma()+Integer.parseInt(ql.getData2()));
                                dsd.setTmc(dsd.getTmc()+(Integer.parseInt(ql.getData1())+Integer.parseInt(ql.getData2())));
                                break;
                            case "TRANSFER":
                                dsd.setAnswer(dsd.getAnswer()+1);
                                dsd.setTme(dsd.getTme()+Integer.parseInt(ql.getData3()));
                                dsd.setTma(dsd.getTma()+Integer.parseInt(ql.getData4()));
                                dsd.setTmc(dsd.getTmc()+(Integer.parseInt(ql.getData3())+Integer.parseInt(ql.getData4())));
                                break;
                        }                       
                    }
                }    
                dsds.add(dsd);
            }
            return dsds;
        }finally{em.close();}
        
    }
}
