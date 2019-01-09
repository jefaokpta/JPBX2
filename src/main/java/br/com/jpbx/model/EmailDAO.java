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
public class EmailDAO {

    private EntityManager em;

    public EmailDAO() {
        em=new ConnectionFactory().getEm();
    }
    
    public Email getEmailConfig(){
        try{
            return em.find(Email.class, 1);
        }finally{em.close();}
    }
    public String updateEmail(Email e){
        EntityTransaction tx=em.getTransaction();
        try{
            tx.begin();
            Email t=em.find(Email.class, 1);
            t=e;
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
}
