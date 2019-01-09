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
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class AgentPauseActionDAO {

    private EntityManager em;
    
    public AgentPauseActionDAO() {
        em=new ConnectionFactory().getEm();
    }
    public String persistPauseAction(AgentPauseAction p){
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

}
