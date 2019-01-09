/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.model;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class AgentDAO {
    
    private EntityManager em;

    public AgentDAO() {
        em=new ConnectionFactory().getEm();
    }
    
    public List<Agent> getAllAgents(int company){    
        try{
            String sql="select a from Agent as a WHERE a.company="+company;
            TypedQuery <Agent> query=em.createQuery(sql,Agent.class);
            return query.getResultList();
        }finally{em.close();}
    }
    public String persistNewAgent(Agent a){
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        try{
            tx.begin();
            em.persist(a);
            tx.commit();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            ret="DAO: "+ex.getMessage();
        }finally{
          em.close();
        }       
        return ret;
    }
    public String persistManyAgents(Agent ag,int agStart,int agEnd){
        EntityTransaction tx=em.getTransaction();
        try{
            tx.begin();
            for(int i=agStart;i<=agEnd;i++)
                em.persist(new Agent(i, ag.getName()+" "+i, ag.getPassword(), ag.getCompany()));
            tx.commit();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            return "DAO: "+ex.getMessage();
        }finally{
          em.close();
        } 
        return "ok";
    }
    public String updateAgent(Agent a){
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        try{
            tx.begin();
            em.merge(a);
            tx.commit();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            ret="DAO: "+ex.getMessage();
        }finally{
           em.close();
        }
        return ret;
    }
    public String deleteAgent(int id){
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        try{
            tx.begin();
            Agent end=em.find(Agent.class, id);
            em.remove(end);
            tx.commit();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            ret="DAO: "+ex.getMessage();
        }finally{
            em.close();
        }       
        return ret;
    }
}
