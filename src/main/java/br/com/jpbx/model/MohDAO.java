/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.model;

import br.com.jpbx.asterisk.AsteriskActions;
import br.com.jpbx.linux.Writer;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

/**
 * 
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
public class MohDAO {

    private EntityManager em;

    public MohDAO() {
        em=new ConnectionFactory().getEm();
    }
    public Moh getSingleMoh(int id){
        try{
            return em.find(Moh.class, id);
        }finally{em.close();}
    } 
    public List<Moh> getAllMohs(){   
        try{
            String sql="select m from Moh as m";
            TypedQuery <Moh> query=em.createQuery(sql,Moh.class);
            return query.getResultList();
        }finally{em.close();}
    }
    public Moh getCompanyMoh(int company){   
        try{
            String sql="select m from Moh as m WHERE m.company = "+company;
            TypedQuery <Moh> query=em.createQuery(sql,Moh.class);
            return query.getSingleResult();
        }finally{em.close();}
    }
    public String persistNewMoh(Moh m){
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        List<Moh> mohs = null;
        try{
            tx.begin();
            em.persist(m);
            tx.commit();
            String sql="select m from Moh as m";
            TypedQuery <Moh> query=em.createQuery(sql,Moh.class);
            mohs=query.getResultList();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            ret="DAO: "+ex.getMessage();
        }finally{
            em.close();
        }
        ret=new Writer().writeMohs(mohs);
        new AsteriskActions().reloadMoh();
        return ret;
    }
    public String updateMoh(List<Moh> mohs){
        EntityTransaction tx=em.getTransaction();
        String ret="Atualizado com sucesso.";
        try{
            tx.begin();
            for (Moh m : mohs)
                em.merge(m);         
            tx.commit();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            ret="DAO: "+ex.getMessage();
        }finally{
           em.close();
        }
        new Writer().writeMohs(mohs);
        new AsteriskActions().reloadMoh();
        return ret;
    }
    public String deleteMoh(Moh m){
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        List<Moh> mohs = null;
        try{
            tx.begin();
            Moh end=em.find(Moh.class, m.getId());
            em.remove(end);
            tx.commit();
            String sql="select m from Moh as m";
            TypedQuery <Moh> query=em.createQuery(sql,Moh.class);
            mohs=query.getResultList();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            ret="DAO: "+ex.getMessage();
        }finally{
           em.close();  
        }
        ret=new Writer().writeMohs(mohs);
        new AsteriskActions().reloadMoh();
        return ret;
    }
}
