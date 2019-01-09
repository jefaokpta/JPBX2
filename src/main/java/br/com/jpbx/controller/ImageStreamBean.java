/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.User;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
@Named(value = "imageStreamBean")
@RequestScoped
public class ImageStreamBean {


    public ImageStreamBean() {
    }
    public StreamedContent getImg(){
        InputStream stream = null;
        FacesContext fc=FacesContext.getCurrentInstance();
        User user=(User) fc.getExternalContext().getSessionMap().get("user");
        try {
            File img=new File("/etc/asterisk/jpbx/imgs/"+user.getName()+"."+user.getImgType());
            if(img.exists())
                stream=new FileInputStream(img);
            else
                stream=new FileInputStream(new File("/etc/asterisk/jpbx/imgs/default.gif"));
        } catch (FileNotFoundException ex) {
            System.out.println("OPS: "+ex.getMessage());
        }
        return new DefaultStreamedContent(stream);
    }
 
    
}
