/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
@Entity @Table(name = "pages_users")
public class LinkUser implements Serializable{

    public LinkUser(int pageId) {

        this.pageId=pageId;
    }

    public LinkUser() {
    }
    
    
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idpages_users")
    private int id;
    @Column(name = "idusers")
    private int userId;
    @Column(name = "id_page")
    private int pageId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }
    
}
