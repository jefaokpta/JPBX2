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
public class AgentPauseDAO {

    private EntityManager em;
    
    public AgentPauseDAO() {
        em=new ConnectionFactory().getEm();
    }
    public List<AgentPause> getAllPauses(int company){     
        try{
            String sql="select p from AgentPause as p WHERE p.company="+company;
            TypedQuery <AgentPause> query=em.createQuery(sql,AgentPause.class);
            return query.getResultList();
        }finally{em.close();}
    }
    public String persistNewPause(AgentPause p){
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        try{
            tx.begin();
            em.persist(p);
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
    public String deletePause(AgentPause p){
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        try{
            tx.begin();
            AgentPause end=em.find(AgentPause.class, p.getId());
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
