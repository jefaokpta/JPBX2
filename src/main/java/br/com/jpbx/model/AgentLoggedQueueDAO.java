/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.model;

import br.com.jpbx.asterisk.Asterisk;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class AgentLoggedQueueDAO {
    
    private EntityManager em;

    public AgentLoggedQueueDAO() {
        em=new ConnectionFactory().getEm();
    }
    
    public List<AgentLoggedQueue> getQueuesByAgent(String agent,int company){
        try{
            String sql="select a from AgentLoggedQueue as a WHERE a.company="+company+" AND a.agent="+agent;
            TypedQuery <AgentLoggedQueue> query=em.createQuery(sql,AgentLoggedQueue.class);
            return query.getResultList();
        }finally{
            em.close();
        }
    }
    public String addAgent(AgentLoggedQueue a){
        EntityTransaction tx=em.getTransaction();
        try{
            tx.begin();     
            a.setCreated(new Date()); // NA TROCA PRA MYSQL 5.7 O NULL PASSOU A NAO PREENCHER AUTOMATICO
            em.persist(a);
            tx.commit();
            if(a.getIface().contains("Agent"))
                new Asterisk().queueAdd(a.getQueue(),a.getIface(),a.getAgent());
            else
                new Asterisk().queueAdd(a.getQueue(),a.getIface(),a.getAgent(),a.getIface());
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            return "DAO OU AST: "+ex.getMessage();
        }finally{
          em.close();
        }  
        return "ok";
    }
    public String delAgent(int id){
        EntityTransaction tx=em.getTransaction();
        try{
            tx.begin();           
            AgentLoggedQueue a=em.find(AgentLoggedQueue.class, id);
            em.remove(a);
            tx.commit();
            new Asterisk().queueRemove(a.getQueue(), a.getIface(),a.getAgent());
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            return "DAO OU AST: "+ex.getMessage();
        }finally{
          em.close();
        }  
        return "ok";
    }
    public String delAgent(String agent,String queue){
        EntityTransaction tx=em.getTransaction();
        try{
            tx.begin();   
            AgentLoggedQueue a = null;
            String sql="select a from AgentLoggedQueue as a WHERE a.queue='"+queue+"' AND a.agent="+agent;
            TypedQuery <AgentLoggedQueue> query=em.createQuery(sql,AgentLoggedQueue.class);
            for (AgentLoggedQueue ag : query.getResultList()) {
                a=ag;
            }
            em.remove(a);
            tx.commit();
            new Asterisk().queueRemove(a.getQueue(), a.getIface(),a.getAgent());
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            return "DAO OU AST: "+ex.getMessage();
        }finally{
          em.close();
        }  
        return "ok";
    }
    public void updateAgentPause(int id,String pause){
        EntityTransaction tx=em.getTransaction();
        try{
            tx.begin();      
            String sql="UPDATE agent_logged SET pause='"+pause+"' WHERE idagent_logged="+id;
            Query q=em.createNativeQuery(sql);
            q.executeUpdate();
            tx.commit();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            System.out.println("DAO: "+ex.getMessage());
        }finally{
          em.close();
        } 
    }

}
