/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.model;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * 
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */

public class WebPageDAO {
    private EntityManager em;

    public WebPageDAO() {
        em=new ConnectionFactory().getEm();
    }
    
    public List<WebPage> getAllPages(){       
       try{
            String sql="select p from WebPage as p";
            TypedQuery <WebPage> query=em.createQuery(sql,WebPage.class);
            return query.getResultList();
       }finally{em.close();}
    }
    
}
