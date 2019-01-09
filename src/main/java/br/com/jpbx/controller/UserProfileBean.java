/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.User;
import br.com.jpbx.model.UserDAO;
import br.com.jpbx.util.HandleUserImg;
import br.com.jpbx.util.VerifyPassword;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CaptureEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.CroppedImage;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
@Named(value = "userProfileBean")
@ViewScoped
public class UserProfileBean implements Serializable{

    private User user;
    private FacesContext fc;
    private boolean submitCtrl;
    private UploadedFile upFile;
    private CroppedImage cropImg;
    private String tempTypeImg;
    private boolean changeImg;
    private boolean editPass;
    private String pass;
    private int cropX;
    private int cropY;
    private int cropW;
    private int cropH;
    
    public UserProfileBean() {
        fc=FacesContext.getCurrentInstance();
        user=(User) fc.getExternalContext().getSessionMap().get("user");
        submitCtrl=true;
        changeImg=false;
    }
    public void crop(){
        changeImg=true;
        new HandleUserImg().crop(cropX,cropY,cropW,cropH,user.getName(),tempTypeImg);
    }
    public void fileUploadListener(FileUploadEvent e){
        // Get uploaded file from the FileUploadEvent
        this.upFile = e.getFile();
        // Print out the information of the file       
        new HandleUserImg().saveUploadedToCrop(upFile, user.getName());
        tempTypeImg=upFile.getFileName().split("\\.")[1];
        RequestContext.getCurrentInstance().execute("PF('crop').show()"); 
        //System.out.println("Uploaded File Name Is :: "+upFile.getFileName()+" :: Uploaded File Size :: "+upFile.getSize());
    }
    public void capture(CaptureEvent ev){
        new HandleUserImg().saveCaptureToCrop(ev.getData(), user.getName());
        tempTypeImg="png";
        RequestContext.getCurrentInstance().execute("PF('crop').show()");
    }
    public void verifyPassword(){
        submitCtrl=true;    
        if(!user.getPassword().equals("")){
            if(!new VerifyPassword().verifyPassword(user.getPassword())){
                FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                    "A senha deve ter ao menos 2 letras e 2 números.", ""));
                submitCtrl=false;
            }
        }       
    }
    public String returnDefaultImg(){
        new HandleUserImg().createUserImg(user.getName());
        user.setImgType("gif");
        new UserDAO().updateUser(user,false);
        return "/restricted/home";
    }
    
    public String updateUser(){
        if(submitCtrl){
            if(changeImg){
                new HandleUserImg().changeUserImg(user.getName(), tempTypeImg);
                user.setImgType(tempTypeImg);
            }
            user.setTimeout(30);
            if(editPass)
                user.setPassword(pass);
            String ret=new UserDAO().updateUser(user,editPass);
            if(ret.equals("ok"))
                return "/restricted/home";
            FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                ret, ""));           
        }
        else
            FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Senha não autorizada.", "")); 
        return null;
    }

    public int getCropX() {
        return cropX;
    }

    public void setCropX(int cropX) {
        this.cropX = cropX;
    }

    public int getCropY() {
        return cropY;
    }

    public void setCropY(int cropY) {
        this.cropY = cropY;
    }

    public int getCropW() {
        return cropW;
    }

    public void setCropW(int cropW) {
        this.cropW = cropW;
    }

    public int getCropH() {
        return cropH;
    }

    public void setCropH(int cropH) {
        this.cropH = cropH;
    }

    public String getTempTypeImg() {
        return tempTypeImg;
    }

    public boolean isEditPass() {
        return editPass;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setEditPass(boolean editPass) {
        this.editPass = editPass;
    }

    public void setTempTypeImg(String tempTypeImg) {
        this.tempTypeImg = tempTypeImg;
    }

    public CroppedImage getCropImg() {
        return cropImg;
    }

    public void setCropImg(CroppedImage cropImg) {
        this.cropImg = cropImg;
    }

    public UploadedFile getUpFile() {
        return upFile;
    }

    public void setUpFile(UploadedFile upFile) {
        this.upFile = upFile;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}
