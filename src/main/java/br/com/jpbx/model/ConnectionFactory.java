/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * 
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
public class ConnectionFactory {

    private static EntityManagerFactory emf;

    public ConnectionFactory() {
        if(emf==null||!emf.isOpen()){
            System.out.println("::::: EMF NULO OU FECHADO :::::");
            //emf=Persistence.createEntityManagerFactory("jpbx");
            try{
                emf=Persistence.createEntityManagerFactory("jpbx");
            }catch(Exception ex){
                emf=Persistence.createEntityManagerFactory("jpbxTEST");
            }
        }
        
    }

    public EntityManager getEm() {
//        if(em==null||!em.isOpen()){
//            System.out.println("::::: CRIANDO EM :::::");
//            em=emf.createEntityManager();
//        }
        return emf.createEntityManager();
    }
  
    
}
