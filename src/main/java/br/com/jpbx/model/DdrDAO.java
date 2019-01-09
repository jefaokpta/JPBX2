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
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
public class DdrDAO {

    private EntityManager em;

    public DdrDAO() {
        em=new ConnectionFactory().getEm();
    }
    
    public Ddr getSingleDdr(int id){
        try{
            return em.find(Ddr.class, id);
        }finally{em.close();}
    } 
    public int getDDRByExten(String exten){
        try{
            String sql="select d from Ddr as d";
            TypedQuery <Ddr> query=em.createQuery(sql,Ddr.class);
            for (Ddr d : query.getResultList()) {
                if(d.getDdr().equals(exten)){
                    return d.getCompany();
                }
            }
            return 0;
        }finally{em.close();}
    }
    public List<Ddr> getAllDdrs(){     
        try{
            String sql="select d from Ddr as d";
            TypedQuery <Ddr> query=em.createQuery(sql,Ddr.class);
            return query.getResultList();
        }finally{em.close();}
    }
    public String persistNewDdr(Ddr d){
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        try{
            tx.begin();
            em.persist(d);
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
    public String deleteDdr(Ddr d){
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        try{
            tx.begin();
            Ddr end=em.find(Ddr.class, d.getId());
            em.remove(end);
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
