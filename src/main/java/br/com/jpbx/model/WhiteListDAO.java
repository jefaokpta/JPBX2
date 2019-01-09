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
 * @author Jefferson@jpbx.com.br < https://jpbx.com.br >
 */
public class WhiteListDAO {
    
    private EntityManager em;

    public WhiteListDAO() {
        em=new ConnectionFactory().getEm();
    }
    
    public List<WhiteList> getAllWhitelists(){
        try{
            String sql="select w from WhiteList as w";
            TypedQuery <WhiteList> query=em.createQuery(sql,WhiteList.class);
            return query.getResultList();
        }finally{em.close();}
    }
    public String persistNewWhitelist(WhiteList w){
        EntityTransaction tx=em.getTransaction();
        try{
            tx.begin();
            em.persist(w);
            tx.commit();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            return  "DAO: "+ex.getMessage();
        }finally{
          em.close();
        }            
        return "ok";
    }
    public String deleteWhitelist(WhiteList w){
        EntityTransaction tx=em.getTransaction();
        try{
            tx.begin();
            WhiteList end=em.find(WhiteList.class, w.getId());
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
    public String updateObs(WhiteList w){
        EntityTransaction tx=em.getTransaction();
        String ret="Atualizado com sucesso.";
        try{
            tx.begin();
            WhiteList upd=em.find(WhiteList.class, w.getId());
            upd.setObs(w.getObs());
            em.merge(upd);         
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
