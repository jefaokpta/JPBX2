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
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
public class QueueDAO {

    private EntityManager em;

    public QueueDAO() {
        em=new ConnectionFactory().getEm();
    }
    public List<Queue> getAllQueues(){       
        try{
            String sql="select q from Queue as q WHERE q.type='A'";
            TypedQuery <Queue> query=em.createQuery(sql,Queue.class);
            return query.getResultList();
        }finally{
            em.close();
        }
    }
    public List<Queue> getAllQueues(int company){       
        try{
            String sql="select q from Queue as q WHERE q.type='A' AND q.company="+company;
            TypedQuery <Queue> query=em.createQuery(sql,Queue.class);
            return query.getResultList();
        }finally{
            em.close();
        }
    }
    public List<Queue> getAllCallGrps(){    
        try{
            String sql="select q from Queue as q WHERE q.type='C'";
            TypedQuery <Queue> query=em.createQuery(sql,Queue.class);
            return query.getResultList();
        }finally{
            em.close();
        }
    }
    public List<Queue> getAllCallGrps(int company){    
        try{
            String sql="select q from Queue as q WHERE q.type='C' AND q.company="+company;
            TypedQuery <Queue> query=em.createQuery(sql,Queue.class);
            return query.getResultList();
        }finally{
            em.close();
        }
    }
    public Queue getSingleQueue(int id){
        try{
            return em.find(Queue.class, id);
        }finally{em.close();}
    }
    public String persistNewQueue(Queue q){
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        try{
            tx.begin();           
            em.persist(q);
            q.setName("CallGrp"+q.getId());
            for (QueueMember qm : q.getQueueMembers()) {
                qm.setQueueName(q.getName());
            }
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
    public String persistNewQueueTrue(Queue q){
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        try{
            tx.begin();           
            em.persist(q);
            q.setName("Queue"+q.getId());
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
    public String updateQueue(Queue q){
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        try{
            tx.begin();
            em.merge(q);
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
    public String deleteQueue(Queue q){
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        try{
            tx.begin();
            Queue end=em.find(Queue.class, q.getId());
            em.remove(end);
            if(q.getType().equals("A")){
                String sql="SELECT a FROM QueueAgent as a WHERE a.queue="+q.getId();
                TypedQuery <QueueAgent> query=em.createQuery(sql,QueueAgent.class);
                for (QueueAgent qa : query.getResultList()) {
                    if(qa.getQueue()==q.getId())
                        em.remove(qa);
                }
            }
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
