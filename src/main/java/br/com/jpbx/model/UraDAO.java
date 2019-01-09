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
 * @author jefaokpta
 */
public class UraDAO {
    private EntityManager em;
    public UraDAO() {
        em=new ConnectionFactory().getEm();
    }
    public List<Ura> getAllUras(){  
        try{
            String sql="select u from Ura as u";
            TypedQuery <Ura> query=em.createQuery(sql,Ura.class);
            return query.getResultList();
        }finally{em.close();}
    }
    public Ura getSingleUra(int id){
        try{
            return em.find(Ura.class, id);
        }finally{em.close();}
    }
    public String persistNewUra(Ura u){
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        List<Ura> uras = null;
        try{
            tx.begin();
            em.persist(u);
            u.setName("URA_"+u.getId());
            tx.commit();
            String sql="select u from Ura as u";
            TypedQuery <Ura> querySelect=em.createQuery(sql,Ura.class);
            uras=querySelect.getResultList();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            ret="DAO: "+ex.getMessage();
        }finally{
          em.close();
        }     
        if(ret.equals("ok"))
            ret=new Writer().writeURA(uras);
        if(ret.equals("ok"))
            ret=new AsteriskActions().dialplanReload();
        return ret;
    }
    public String updateUra(Ura u){
        EntityTransaction tx=em.getTransaction();
        String sql;
        String ret="ok";
        List<Ura> uras = null;
        try{
            tx.begin();
            em.merge(u);
            tx.commit();
            sql="select u from Ura as u";
            TypedQuery <Ura> querySelect=em.createQuery(sql,Ura.class);
            uras=querySelect.getResultList();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            ret="DAO: "+ex.getMessage();
        }finally{
           em.close();
  
        }
        if(ret.equals("ok"))
            ret=new Writer().writeURA(uras);
        if(ret.equals("ok"))
            ret=new AsteriskActions().dialplanReload();
        return ret;
    }
    public String deleteUra(Ura u){
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        List<Ura> uras = null;
        try{
            tx.begin();
            //Ura end=em.find(Ura.class, u.getId());
            em.remove(em.find(Ura.class, u.getId()));
            tx.commit();
            String sql="select u from Ura as u";
            TypedQuery <Ura> querySelect=em.createQuery(sql,Ura.class);
            uras=querySelect.getResultList();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            ret="DAO: "+ex.getMessage();
        }finally{
          em.close();
  
        }
        if(ret.equals("ok"))
            ret=new Writer().writeURA(uras);
        if(ret.equals("ok"))
            ret=new AsteriskActions().dialplanReload();
        return ret;
    }
}
