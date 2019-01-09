/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * 
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
public class ConfigDAO {
    
    private EntityManager em;

    public ConfigDAO() {
        em=new ConnectionFactory().getEm();
    }
    public Config getConfig(){
        try{
            return em.find(Config.class, 1);
        }finally{em.close();}
    }
    public String updateConfig(Config config){
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        try{
            tx.begin();
           // Config c=em.find(Config.class, 1);
           // c=config;
            em.merge(config);
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
    public String updateConfig(int invasion){
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        try{
            tx.begin();
            Config c=em.find(Config.class, 1);
            c.setInvasion(invasion);
            em.merge(c);
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
