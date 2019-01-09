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
public class WebServerBdoDAO {

    private EntityManager em;

    public WebServerBdoDAO() {
        em=new ConnectionFactory().getEm();
    }
    
    public List<WebServerBdo> getAllWebServerBdo(){
        try{
            String sql="select w from WebServerBdo as w";
            TypedQuery <WebServerBdo> query=em.createQuery(sql,WebServerBdo.class);
            return query.getResultList();
        }finally{em.close();}
    }
    public List<WebServerBdo> getAllWebServerBdo(int company){
        try{
            String sql="select w from WebServerBdo as w WHERE w.company="+company;
            TypedQuery <WebServerBdo> query=em.createQuery(sql,WebServerBdo.class);
            return query.getResultList();
        }finally{em.close();}
    }
    public String persistNewBdo(WebServerBdo w){
        EntityTransaction tx=em.getTransaction();
        try{
            tx.begin();
            em.persist(w);
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
    public String updateBdo(WebServerBdo w){
        EntityTransaction tx=em.getTransaction();
        try{
            tx.begin();
            em.merge(w);
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
    public String deleteBdo(int id){
        EntityTransaction tx=em.getTransaction();
        try{
            tx.begin();
            WebServerBdo end=em.find(WebServerBdo.class, id);
            em.remove(end);
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
