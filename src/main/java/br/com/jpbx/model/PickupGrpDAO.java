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
public class PickupGrpDAO {
    private EntityManager em;

    public PickupGrpDAO() {
        em=new ConnectionFactory().getEm();
    }
    public PickupGrp getSingleGrp(int id){
        try{
            return em.find(PickupGrp.class, id);
        }finally{em.close();}
    }
    public List<PickupGrp> getAllGrps(){    
        try{
            String sql="select g from PickupGrp as g";
            TypedQuery <PickupGrp> query=em.createQuery(sql,PickupGrp.class);
            return query.getResultList();
        }finally{em.close();}
    }
    public String persistNewGrp(PickupGrp p){
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
    public String updateGrp(PickupGrp p){
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        try{
            tx.begin();
            em.merge(p);
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
    public String deleteGrp(PickupGrp p){
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        try{
            tx.begin();
            PickupGrp end=em.find(PickupGrp.class, p.getId());
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
