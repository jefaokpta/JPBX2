/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.model;

import br.com.jpbx.linux.HandleIptable;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

/**
 * 
 * @author jefaokpta <jefferson@jpbx.com.br>
 */
public class InvasionDAO {
    
    private EntityManager em;

    public InvasionDAO() {
        em=new ConnectionFactory().getEm();
    }
    
    public List<Invasion> getallInvasions(){    
        try{
            String sql="select i from Invasion as i order by datetime desc";
            TypedQuery <Invasion> query=em.createQuery(sql,Invasion.class);
            return query.getResultList();
        }finally{em.close();}
    }
    public String persistNewInvasion(Invasion i){
        EntityTransaction tx=em.getTransaction();
        try{
            tx.begin();
            em.persist(i);
            tx.commit();
            new HandleIptable().blockIpTable(i.getIp(), i.getMask());
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            return  "DAO: "+ex.getMessage();
        }finally{
          em.close();
        }            
        return "ok";
    }
    public void persistNewInvasion(List<String> ips,Date today){
        EntityTransaction tx=em.getTransaction();
        try{
            tx.begin();
            for (String ip : ips) {
                em.persist(new Invasion(ip, "255.255.255.255", today));
            }
            tx.commit();
            HandleIptable handler = new HandleIptable();
            for (String ip : ips) {
                handler.blockIpTable(ip, "255.255.255.255");
            }
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            System.out.println("DAO: "+ex.getMessage());
        }finally{
          em.close();
        }            
    }
    public String deleteInvasion(Invasion i){
        EntityTransaction tx=em.getTransaction();
        try{
            tx.begin();
            Invasion end=em.find(Invasion.class, i.getId());
            em.remove(end);
            tx.commit();
            new HandleIptable().clearIpTable();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            return "DAO: "+ex.getMessage();
        }finally{
            em.close();
        }       
        return "ok";
    }
    public void writeAllIpTable(){
        HandleIptable iptable=new HandleIptable();
        String sql="select i from Invasion as i";
        TypedQuery <Invasion> query=em.createQuery(sql,Invasion.class);
        //iptable.clearIpTable();
        for (Invasion i : query.getResultList()) {
            iptable.blockIpTable(i.getIp(), i.getMask());
        }
    }
    
    public void clearAllIpTable(){
        HandleIptable iptable=new HandleIptable();
        String sql="select i from Invasion as i";
        TypedQuery <Invasion> query=em.createQuery(sql,Invasion.class);
        //iptable.clearIpTable();
        for (Invasion i : query.getResultList()) {
            iptable.releaseIp(i.getIp());
        }
    }
}
