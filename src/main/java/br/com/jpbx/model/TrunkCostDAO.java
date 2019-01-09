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
public class TrunkCostDAO {

    private EntityManager em;

    public TrunkCostDAO() {
        em=new ConnectionFactory().getEm();
    }
    
    public List<TrunkCost> getAllTrunkCosts(){  
        try{
            String sql="select t from TrunkCost as t";
            TypedQuery <TrunkCost> query=em.createQuery(sql,TrunkCost.class);
            return query.getResultList();
        }finally{em.close();}
    }
    public String updateTrunkCost(TrunkCost t){
        EntityTransaction tx=em.getTransaction();
        try{
            tx.begin();
            em.merge(t);
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
    public void persistTrunkCost(int trunkId){
        EntityTransaction tx=em.getTransaction();
        try{
            tx.begin();
            em.persist(new TrunkCost(trunkId, 1));
            em.persist(new TrunkCost(trunkId, 2));
            em.persist(new TrunkCost(trunkId, 3));
            em.persist(new TrunkCost(trunkId, 4));
            em.persist(new TrunkCost(trunkId, 5));
            em.persist(new TrunkCost(trunkId, 6));
            em.persist(new TrunkCost(trunkId, 7));
            em.persist(new TrunkCost(trunkId, 8));
            tx.commit();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            System.out.println("DAO: "+ex.getMessage());
        }finally{
           em.close();
        }
    }
    public void deleteTrunkCost(int id){
        EntityTransaction tx=em.getTransaction();
        try{
            tx.begin();
            String sql="select t from TrunkCost as t WHERE t.trunk="+id;
            TypedQuery <TrunkCost> query=em.createQuery(sql,TrunkCost.class);
            for (TrunkCost tc : query.getResultList()) {
                em.remove(tc);
            }
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
