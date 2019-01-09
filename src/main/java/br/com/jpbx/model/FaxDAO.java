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
public class FaxDAO {
    private EntityManager em;
    
    public FaxDAO() {
        em=new ConnectionFactory().getEm();
    }
    public Fax getSingleFax(int id){
        try{
            return em.find(Fax.class, id);
        }finally{em.close();}
    } 
    public List<Fax> getAllFaxes(int company){     
        try{
            String sql="select f from Fax as f where company="+company+" order by datetime desc";
            TypedQuery <Fax> query=em.createQuery(sql,Fax.class);
            return query.getResultList();
        }finally{em.close();}
    }
    public String persistNewFax(Fax f){
        EntityTransaction tx=em.getTransaction();
        try{
            tx.begin();
            em.persist(f);
            tx.commit();            
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            return "DAO FAX: "+ex.getMessage();
        }finally{
          em.close();
        }  
        return "ok";
    }
    public String deleteFax(Fax f){
        EntityTransaction tx=em.getTransaction();
        try{
            tx.begin();
            Fax end=em.find(Fax.class, f.getId());
            em.remove(end);
            tx.commit();            
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            return "DAO FAX: "+ex.getMessage();
        }finally{
          em.close();
        }  
        return "ok";
    }
}
