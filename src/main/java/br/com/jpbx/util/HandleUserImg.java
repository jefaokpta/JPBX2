/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import org.primefaces.model.UploadedFile;

/**
 * 
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
public class HandleUserImg {
    public void crop(int x,int y,int w,int h,String userName,String type){
        try {
            BufferedImage originalImgage = ImageIO.read(new File("/tmp/"+userName+"Temp."+type));
            System.out.println(x+" "+y +" "+w+" "+h);
            BufferedImage SubImgage = originalImgage.getSubimage(x, y, w, h);
            File outputfile = new File("/tmp/"+userName+"Cropped."+type);
            ImageIO.write(SubImgage, type, outputfile);
        } catch (IOException ex) {
            System.out.println("DEU RUIM CROPPAR: "+ex.getMessage());
        }
    }
    public void changeUserImg(String userName,String type){
        File img=new File("/tmp/"+userName+"Cropped."+type);
        img.renameTo(new File("/etc/asterisk/jpbx/imgs/"+userName+"."+type));
        //deleteUserImgCrop(userName);
        //deleteUserImgCropped(userName);
    }
    public void saveUploadedToCrop(UploadedFile img,String userName){
        File imgDst=new File("/tmp/"+userName+"Temp."+img.getFileName().split("\\.")[1]);
        try {
            InputStream in=img.getInputstream();
            FileOutputStream out=new FileOutputStream(imgDst);
            int i=0;
            byte[] bytes=new byte[1024];
            while ((i=in.read(bytes))>=0) {                
                out.write(bytes, 0, i);
            }
            out.flush();
            out.close();
        } catch (FileNotFoundException ex) {
            System.out.println("::::: FALHA AO SALVAR IMG EM CROP: "+ex.getMessage()+" :::::");
        } catch (IOException ex) {
            System.out.println("::::: FALHA AO SALVAR UPLOADED IMG EM CROP: "+ex.getMessage()+" :::::");
        }
    }
    public void saveCaptureToCrop(byte[] camData,String userName){
        File imgDst=new File("/tmp/"+userName+"Temp.png");
        FileImageOutputStream out;
        try{
            out=new FileImageOutputStream(imgDst);
            out.write(camData,0,camData.length);
            out.close();
        }catch(IOException ex){
            System.out.println("::::: FALHA AO SALVAR CAPTURED IMG EM CROP: "+ex.getMessage()+" :::::");
        }
    }
    public void createUserImg(String userName){
        File imgSrc=new File("/etc/asterisk/jpbx/imgs/default.gif");
        File imgDst=new File("/etc/asterisk/jpbx/imgs/"+userName+".gif");
        try {
            FileInputStream in=new FileInputStream(imgSrc);
            FileOutputStream out=new FileOutputStream(imgDst);
            int i=0;
            byte[] bytes=new byte[1024];
            while ((i=in.read(bytes))>=0) {                
                out.write(bytes, 0, i);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("::::: FALHA NA CRIACAO DA IMAGEM DO USUARIO "+
                    userName+": "+ex.getMessage()+" :::::");
        } catch (IOException ex) {
            System.out.println("::::: FALHA NA CRIACAO DA IMAGEM DO USUARIO "+
                    userName+": "+ex.getMessage()+" :::::");
        }
    }
    public void deleteUserImg(String userName){
        File img=new File("/etc/asterisk/jpbx/imgs/"+userName+".gif");
        if(img.exists())
            img.delete();
        img=new File("/etc/asterisk/jpbx/imgs/"+userName+".jpg");
        if(img.exists())
            img.delete();
        img=new File("/etc/asterisk/jpbx/imgs/"+userName+".png");
        if(img.exists())
            img.delete();
    }
   
}
