/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.model;

import br.com.jpbx.asterisk.AsteriskActions;
import br.com.jpbx.linux.WriteExtensions;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

/**
 *
 * @author jefaokpta
 */
public class DialPlanRuleDAO {
    
    private EntityManager em;

    public DialPlanRuleDAO() {
        em=new ConnectionFactory().getEm();
    }
    public List<DialPlanRule> getAllDialplans(){
        try{
            String sql="select d from DialPlanRule d order by d.dst,d.priority";
            TypedQuery query=em.createQuery(sql, DialPlanRule.class);
            return query.getResultList();
        }finally{em.close();}
    }
    public List<DialPlanRule> getAllDialplans(int company){
        try{
            String sql="select d from DialPlanRule d WHERE d.company="+company+" order by d.dst,d.priority";
            TypedQuery query=em.createQuery(sql, DialPlanRule.class);
            return query.getResultList();
        }finally{em.close();}
    }
    public int getRuleActive(int id){
        try{
            return em.find(DialPlanRule.class, id).getActive();
        }finally{em.close();}
    }
    public String verifyDialPlanUses(String action,String arg1,int company){
        String sql="select d from DialPlanRule d where d.company="+company;
        TypedQuery query=em.createQuery(sql, DialPlanRule.class);
        List<DialPlanRule> ret=query.getResultList();
        for (DialPlanRule d : ret) {
            for (DialPlanAction a : d.getActions()) {
                if(a.getAct().equals(action)&&a.getArg1().equals(arg1))
                    return "Componente sendo usado na Regra "+d.getId()+" - "+d.getName()+". Não pode ser apagado.";
            }
        }
        return "ok";
    }
    public String persistNewDialplan(DialPlanRule d){
        EntityTransaction tx=em.getTransaction();
        List<DialPlanRule> rules = null;
        try{
            tx.begin();
            em.persist(d);
            tx.commit();
            String sql="select d from DialPlanRule d where d.company="+d.getCompany()+" order by d.dst,d.priority";
            TypedQuery query=em.createQuery(sql, DialPlanRule.class);
            rules=query.getResultList();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            return "DAO: "+ex.getMessage();
        }finally{
            em.close();      
        }
        String ret=new WriteExtensions().writeContext(rules);
        if(ret.equals("ok"))
            ret=new AsteriskActions().dialplanReload();
        return ret;
    }
    public String updateDialplan(DialPlanRule d){
        EntityTransaction tx=em.getTransaction();
        List<DialPlanRule> rules = null;
        try{
            tx.begin();          
            em.merge(d);
            tx.commit();
            String sql="select d from DialPlanRule d where d.company="+d.getCompany()+" order by d.dst,d.priority";
            TypedQuery query=em.createQuery(sql, DialPlanRule.class);
            rules=query.getResultList();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            return "DAO: "+ex.getMessage();
        } finally{
            em.close();      
        }
        String ret=new WriteExtensions().writeContext(rules);
        if(ret.equals("ok"))
            ret=new AsteriskActions().dialplanReload();
        return ret;
    }
    public String updateDialplanActive(DialPlanRule d){
        EntityTransaction tx=em.getTransaction();
        try{
            tx.begin();          
            em.merge(d);
            tx.commit();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            return "DAO: "+ex.getMessage();
        } finally{
           em.close();      
        }
        return "ok";
    }
    public String changePriority(DialPlanRule da, DialPlanRule db){
        EntityTransaction tx=em.getTransaction();
        List<DialPlanRule> rules=null;
        try{
            tx.begin();          
            em.merge(da);
            em.merge(db);
            tx.commit();
            String sql="select d from DialPlanRule d where d.company="+da.getCompany()+" order by d.dst,d.priority";
            TypedQuery query=em.createQuery(sql, DialPlanRule.class);
            rules=query.getResultList();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            return "DAO: "+ex.getMessage();
        } finally{
            em.close();      
        }
        String ret=new WriteExtensions().writeContext(rules);
        if(ret.equals("ok"))
            ret=new AsteriskActions().dialplanReload();
        return ret;
    }
    public String deleteDialplan(DialPlanRule d){
        EntityTransaction tx=em.getTransaction();
        List<DialPlanRule> rules=null;
        try{
            tx.begin();
            DialPlanRule endDialplan=em.find(DialPlanRule.class, d.getId());
            em.remove(endDialplan);
            tx.commit();
            String sql="select d from DialPlanRule d where d.company="+d.getCompany()+" order by d.dst,d.priority";
            TypedQuery query=em.createQuery(sql, DialPlanRule.class);
            rules=query.getResultList();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            return "DAO: "+ex.getMessage();
        } finally{
            em.close();  
        }
        String ret=new WriteExtensions().writeContext(rules);
        if(ret.equals("ok"))
            ret=new AsteriskActions().dialplanReload();
        return ret;
    }
    
}
