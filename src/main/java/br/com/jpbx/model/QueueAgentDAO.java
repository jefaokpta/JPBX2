/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class QueueAgentDAO {
    private EntityManager em;

    public QueueAgentDAO() {
        em=new ConnectionFactory().getEm();
    }
    
    public List<QueueAgent> getAgentFromQueue(int queue){       
        try{
            String sql="SELECT a FROM QueueAgent as a WHERE a.queue="+queue;
            TypedQuery <QueueAgent> query=em.createQuery(sql,QueueAgent.class);
            return query.getResultList();
        }finally{
            em.close();
        }
    }
    public List<QueueAgent> getQueuesFromAgents(int agent,int company){       
        try{
            String sql="SELECT q FROM QueueAgent as q WHERE q.agent="+agent+" AND q.company="+company;
            TypedQuery <QueueAgent> query=em.createQuery(sql,QueueAgent.class);
            return query.getResultList();
        }finally{
            em.close();
        }
    }
    public String updateQueue(List<QueueAgent> agents,int queue){
        EntityTransaction tx=em.getTransaction();
        try{
            tx.begin();
            String sql="SELECT a FROM QueueAgent as a WHERE a.queue="+queue;
            TypedQuery <QueueAgent> query=em.createQuery(sql,QueueAgent.class);
            List<QueueAgent> updater=new ArrayList<>();
            for (QueueAgent agentPresent : query.getResultList()) {
                if(agents.contains(agentPresent))
                    updater.add(agentPresent);
                else
                    em.remove(agentPresent);
            }
            for (QueueAgent agentNews : agents) {
                if(!updater.contains(agentNews))
                    updater.add(agentNews);
            }
            for (QueueAgent a : updater) {
                em.merge(a);
            }
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
}
