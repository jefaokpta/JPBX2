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
 * @author jefaokpta
 */
public class AliasDAO {
    
    private EntityManager em;

    public AliasDAO() {
        em=new ConnectionFactory().getEm();
    }
    public Alias getSingleAlias(int id){
        try{
            return em.find(Alias.class, id);
        }finally{em.close();}
    } 
    public List<Alias> getAllAlias(){
        try{
            String sql="select a from Alias a";
            TypedQuery query=em.createQuery(sql, Alias.class);
            return query.getResultList();
        }finally{em.close();}
    }
    public String persistNewAlias(Alias a){
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
    public String updateAlias(Alias a){
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
        } finally{
            em.close();      
        }
        return ret;
    }
    public String deleteAlias(Alias a){
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        try{
            tx.begin();
            Alias endAlias=em.find(Alias.class, a.getId());
            em.remove(endAlias);
            tx.commit();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            ret="DAO: "+ex.getMessage();
        } finally{
            em.close();  
        }
        return ret;
    }
}
