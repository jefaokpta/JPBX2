/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * 
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
public class CallsOfMonthDAO {
    private EntityManager em;
    public CallsOfMonthDAO() {
        em=new ConnectionFactory().getEm();
    }

    public List callsOfMonth(int company){ // ANTIGO
        Calendar dayEnd=Calendar.getInstance();
        SimpleDateFormat fm=new SimpleDateFormat("yyyy-MM-dd");
        Calendar dayStart = Calendar.getInstance();
        dayStart.setTime(dayEnd.getTime());
        dayStart.set(Calendar.DAY_OF_MONTH, 1);
        List res=new ArrayList();
        EntityTransaction tx=em.getTransaction();
        String yMonth=dayStart.get(Calendar.YEAR)+"-"+((dayStart.get(Calendar.MONTH)+1)<10?"0"+(dayStart.get(Calendar.MONTH)+1):(dayStart.get(Calendar.MONTH)+1));
        try{
            tx.begin();
            String sql="select accountcode from relcalls where calldate like '"+yMonth+"%' and company="+company+" group by accountcode";
            Query query=em.createNativeQuery(sql);
            for (Object obj : query.getResultList()) {
                dayStart.set(Calendar.DAY_OF_MONTH, 1);              
                for(Calendar d=dayStart;!d.after(dayEnd);d.add(Calendar.DAY_OF_MONTH, 1)){
                    sql="SELECT count(*),calldate,accountcode " +
                    "FROM relcalls where calldate like "
                            + "'"+d.get(Calendar.YEAR)+"-"+((d.get(Calendar.MONTH)+1)<10?"0"+(d.get(Calendar.MONTH)+1):(d.get(Calendar.MONTH)+1))+"-"
                            +(d.get(Calendar.DAY_OF_MONTH)<10?"0"+d.get(Calendar.DAY_OF_MONTH):d.get(Calendar.DAY_OF_MONTH))+"%'"
                            + " and company="+company+" and accountcode="+obj.toString();
                    Query query2=em.createNativeQuery(sql);
                    for (Object rs : query2.getResultList()) {
                        Object[] line=(Object[]) rs;
                        if(line[0].toString().equals("0")){
                            line[1]=d.get(Calendar.YEAR)+"-"+((d.get(Calendar.MONTH)+1)<10?"0"+(d.get(Calendar.MONTH)+1):(d.get(Calendar.MONTH)+1))+"-"
                            +(d.get(Calendar.DAY_OF_MONTH)<10?"0"+d.get(Calendar.DAY_OF_MONTH):d.get(Calendar.DAY_OF_MONTH)+" 00:00:00");
                            line[2]=obj.toString();
                            res.add(line);
                            continue;
                        }
                        res.add(rs);
                    }
                }
            }            
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            System.out.println("DAO: "+ex.getMessage());
        } finally{
            em.close();       
        }
        return res;
    }
    public List callsOfMonthInMemory(int company){ // ATUAL
        try{
            Calendar dayEnd=Calendar.getInstance();
            Calendar dayStart = Calendar.getInstance();
            List res=new ArrayList();
            int monthAjust=(dayStart.get(Calendar.MONTH)+1);
            String month=dayStart.get(Calendar.YEAR)+"-"+(monthAjust<10?"0"+monthAjust:monthAjust);
            String sql="select accountcode from relcalls where calldate like '"+month+"%' and company="+company+" group by accountcode";
            Query nativeQuery=em.createNativeQuery(sql);
            String sql2="SELECT r  FROM RelCallNoHistory AS r WHERE r.calldate LIKE '"+month+"%' and r.company="+company;
            TypedQuery query=em.createQuery(sql2,RelCallNoHistory.class);
            List<RelCallNoHistory> calls= query.getResultList();

            for (Object obj : nativeQuery.getResultList()) {
                dayStart.set(Calendar.DAY_OF_MONTH, 1);
                dayStart.set(Calendar.MONTH, dayEnd.get(Calendar.MONTH));
               // System.out.println("::::::::::::::::::::::: "+new Timestamp(dayStart.getTimeInMillis()));
                for(Calendar d=dayStart;!d.after(dayEnd);d.add(Calendar.DAY_OF_MONTH, 1)){
                  //  System.out.println("::::::::: ANALISE "+obj.toString()+" ::::::::::::::::: "+new Timestamp(d.getTimeInMillis()).toString().split(" ")[0]);
                    int num=0;
                    String currentDay=new Timestamp(d.getTimeInMillis()).toString().split(" ")[0];
                    Object[] line=new Object[3];
                    for (RelCallNoHistory r : calls) {
                        if(currentDay.equals(new Timestamp(r.getCalldate().getTime()).toString().split(" ")[0]))
                            if(String.valueOf(r.getAccountcode()).equals(obj.toString()))
                                num++;
                    }
                    line[0]=String.valueOf(num);
                    line[1]=new Timestamp(d.getTime().getTime());
                    line[2]=obj;
                    res.add(line);
                }
            }
            return res;
        }finally{em.close();}
    }
}
