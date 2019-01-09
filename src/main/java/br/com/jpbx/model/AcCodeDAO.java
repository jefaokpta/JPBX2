/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.model;

import br.com.jpbx.asterisk.Asterisk;
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

/**
 *
 * @author jefaokpta
 */
public class AcCodeDAO {
    
    private EntityManager em;

    public AcCodeDAO() {
        em=new ConnectionFactory().getEm();
    }
    
    public List<AcCode> getAllAcCodes(){
        try{
            String sql="select a from AcCode as a";
            TypedQuery <AcCode> query=em.createQuery(sql,AcCode.class);
            return query.getResultList();
        }finally{em.close();}
    }
    public AcCode getSingleAcCode(int id){
        try{
            return em.find(AcCode.class, id);
        }finally{em.close();}
    }
    public AcCode getSingleAcCodeByCompany(int company){
        try{
            String sql="select a from AcCode as a where a.company="+company;
            TypedQuery <AcCode> query=em.createQuery(sql,AcCode.class);
            return query.getSingleResult();
        }finally{em.close();}
    }
    public String persistNewAcCode(AcCode a){
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        try{
            tx.begin();
            em.persist(a);
            tx.commit();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            ret="DAO: "+ex.getMessage();
        }finally{
            em.close();
        }     
        if(ret.equals("ok")){
           // try {
                Asterisk ast=new Asterisk();
                for (AcCodePin pi : a.getAcCodePins()) {
                    ast.astDBAdd("pin"+a.getId(), String.valueOf(pi.getPin()), "0");
                }
//            } catch (IOException ex) {
//                System.out.println("DEU RUIM PUT NO DAO ACCODE: "+ex.getMessage());
//            }
        }           
        return ret;
    }
    public String updateAcCode(AcCode a){
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        try{
            tx.begin();
            em.merge(a);
            tx.commit();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            ret="DAO: "+ex.getMessage();
        }finally{
            em.close();  
        }
        if(ret.equals("ok")){
           // try {
                Asterisk ast=new Asterisk();
                ast.astDBDELTREE("pin"+a.getId());
                for (AcCodePin pi : a.getAcCodePins()) {
                    ast.astDBAdd("pin"+a.getId(), String.valueOf(pi.getPin()), "0");
                }
//            } catch (IOException ex) {
//                System.out.println("DEU RUIM UPDATE DAO ACCODE: "+ex.getMessage());
//            }
        }
        return ret;
    }
    public String deleteAcCode(AcCode a){
        EntityTransaction tx=em.getTransaction();
        String ret="ok";
        try{
            tx.begin();
            AcCode end=em.find(AcCode.class, a.getId());
            em.remove(end);
            tx.commit();
        }catch(Exception ex){
            if(tx.isActive())
                tx.rollback();
            ret="DAO: "+ex.getMessage();
        }finally{
            em.close();  
        }
        if(ret.equals("ok"))
          //  try {
                new Asterisk().astDBDELTREE("pin"+a.getId());
//        } catch (IOException ex) {
//            System.out.println("DEU RUIM DELETE DAO ACCODE: "+ex.getMessage());
//        }
        return ret;
    }
}
