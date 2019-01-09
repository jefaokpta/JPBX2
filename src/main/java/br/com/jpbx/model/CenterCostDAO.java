/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.model;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * 
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
public class CenterCostDAO {

    private EntityManager em;
    
    public CenterCostDAO() {
        em=new ConnectionFactory().getEm();
    }
    public List<CenterCost> getCCosts(){
        try{
            String sql="select c from CenterCost c order by c.ccost";
            TypedQuery query=em.createQuery(sql, CenterCost.class);
            return query.getResultList();
        }finally{em.close();}
    }
    public List<CenterCost> getCCosts(int company){
        try{
            String sql="select c from CenterCost c where c.company="+company+" order by c.ccost";
            TypedQuery query=em.createQuery(sql, CenterCost.class);
            return query.getResultList();
        }finally{em.close();}
    }
    public CenterCost getSingleCCost(int id){
        try{
            return em.find(CenterCost.class, id);
        }finally{em.close();}
    }
    public String updateCCost(CenterCost ccost){
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        try{
            tx.begin();
            em.merge(ccost);
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
    public String persistNewCCost(CenterCost ccost,Float cod){
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        try{
            tx.begin();
            String sql="SELECT max(ccost)+0.01 FROM jpbx.ccosts where ccost like "
                    + "'"+String.valueOf(cod).substring(0, 1)+"%'";
            Query query=em.createNativeQuery(sql);
            ccost.setCcost(Float.parseFloat(query.getSingleResult().toString()));
            em.persist(ccost);
            tx.commit();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            ret="DAO persist: "+ex.getMessage();
        }finally{
            em.close();      
        }
        return ret;
    }
    
    public String deleteCCost(CenterCost ccost){
        String ret=new DialPlanRuleDAO().verifyDialPlanUses("ccost", String.valueOf(ccost.getId()), ccost.getCompany());
        if(!ret.equals("ok"))
            return ret;
        EntityTransaction tx=em.getTransaction();       
        try{
            tx.begin();
            CenterCost endCcost=em.find(CenterCost.class, ccost.getId());
            em.remove(endCcost);
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
