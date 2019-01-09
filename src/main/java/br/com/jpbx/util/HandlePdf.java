/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.primefaces.model.UploadedFile;

/**
 * 
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class HandlePdf {

    public String savePdf(UploadedFile pdf,String name){
        try {
            FileOutputStream out = new FileOutputStream(new File("/etc/asterisk/jpbx/pdfs/"+name+".pdf"));
            out.write(pdf.getContents(), 0, (int) pdf.getSize());
        } catch (FileNotFoundException ex) {
            return "DEU RUIM NO PDF: "+ex.getMessage();
        } catch (IOException ex) {
            return "DEU RUIM NA ESCRITA PDF: "+ex.getMessage();
        }
        return "ok";
    }
}
