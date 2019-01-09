/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.model;

import br.com.jpbx.asterisk.AsteriskActions;
import br.com.jpbx.linux.Writer;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

/**
 * 
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
public class TrunkDAO {

    private EntityManager em;
    
    public TrunkDAO() {
        em=new ConnectionFactory().getEm();
    }
    public Trunk getSingleTrunk(int id){
        try{
            return em.find(Trunk.class, id);
        }finally{em.close();}
    } 
    public Trunk getTrunkByChannel(String chan){
        try{
            String sql="select t from Trunk as t where t.canal='"+chan+"'";
            TypedQuery <Trunk> query=em.createQuery(sql,Trunk.class);
            List<Trunk> trunks=query.getResultList();
            if(trunks.size()>0)
                return trunks.get(0);
        
            String ch=chan.toUpperCase();
            if(ch.split("/")[0].equals("KHOMP")){
                String b=ch.substring(ch.indexOf("B")+1, ch.indexOf("C"));
                String c=ch.substring(ch.indexOf("C")+1);
                sql="select t from Trunk as t where t.techDigital='Khomp' and t.numBoard="+b+" and "+c+" between t.chanRangeStart and t.chanRangeEnd";
                query=em.createQuery(sql,Trunk.class);
                return query.getSingleResult();
            }
            if(ch.split("/")[0].equals("DAHDI")){
                sql="select t from Trunk as t where t.techDigital='DAHDI' and "+ch.split("/")[1]+" between t.chanRangeStart and t.chanRangeEnd";
                query=em.createQuery(sql,Trunk.class);
                return query.getSingleResult();
            }
        }catch(Exception ex){
            System.out.println("DEU RUIM EM VERIFICAR TRONCO ENTRADA: "+ex.getMessage());         
        }finally{
            em.close();
        }     
        Trunk fail= new Trunk();
        fail.setName("ERRO: TRONCO NAO IDENTIFICADO");
        return fail;
    }
    public List<Trunk> getAllTrunks(){  
        try{
            String sql="select t from Trunk as t";
            TypedQuery <Trunk> query=em.createQuery(sql,Trunk.class);
            return query.getResultList();
        }finally{em.close();}
    }

    public String persistNewTrunk(Trunk t){
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        List<Trunk> trunks=null;
        try{
            tx.begin();
            em.persist(t);
            
            tx.commit();
            String sql="select t from Trunk as t";
            TypedQuery <Trunk> query=em.createQuery(sql,Trunk.class);
            trunks=query.getResultList();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            ret="DAO: "+ex.getMessage();
        }finally{
          em.close();
        }
        new TrunkCostDAO().persistTrunkCost(t.getId());
        switch(t.getTecnology()){
            case "SIP":
                ret=new Writer().writeTrunkSIP(trunks);
                new AsteriskActions().reloadSip();
                break;
            case "IAX2":
                ret=new Writer().writeTrunkIAX(trunks);
                new AsteriskActions().reloadIAX(); 
                break;
        }
        return ret;
    }

    public String deleteTrunk(int id){
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        List<Trunk> trunks=null;
        Trunk end = null;
        try{
            tx.begin();
            end=em.find(Trunk.class, id);
            em.remove(end);
            tx.commit();
            if(end.getTecnology().equals("SIP")||end.getTecnology().equals("IAX2")){
                String sql="select t from Trunk as t";
                TypedQuery <Trunk> query=em.createQuery(sql,Trunk.class);
                trunks=query.getResultList();
            }
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            ret="DAO: "+ex.getMessage();
        }finally{
          em.close();
  
        }
        new TrunkCostDAO().deleteTrunkCost(id);
        switch(end.getTecnology()){
            case "SIP":
                ret=new Writer().writeTrunkSIP(trunks);
                new AsteriskActions().reloadSip();
                break;
            case "IAX2":
                ret=new Writer().writeTrunkIAX(trunks);
                new AsteriskActions().reloadIAX(); 
                break;
        }
        return ret;
    }
    public String updateTrunk(Trunk t){
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        List<Trunk> trunks = null;
        try{
            tx.begin();
            em.merge(t);
            tx.commit();
            String sql="select t from Trunk as t";
            TypedQuery <Trunk> query=em.createQuery(sql,Trunk.class);
            trunks=query.getResultList();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            ret="DAO: "+ex.getMessage();
        }finally{
           em.close();
        }
        switch(t.getTecnology()){
            case "SIP":
                ret=new Writer().writeTrunkSIP(trunks);
                new AsteriskActions().reloadSip();
                break;
            case "IAX2":
                ret=new Writer().writeTrunkIAX(trunks);
                new AsteriskActions().reloadIAX(); 
                break;
        }
        return ret;
    }

    
}
