
package br.com.jpbx.controller;

import br.com.jpbx.linux.LinuxInfo;
import br.com.jpbx.util.MD5Factory;
import java.io.FileWriter;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author jefaokpta
 */
@Named(value = "licencingBean")
@RequestScoped
public class LicencingBean {

    private String key;

    public LicencingBean() {
    }

    public String doLicencing(){
        if(key.equals(new MD5Factory().md5(new LinuxInfo().hwKey()+":JPBX"))){
            try {
                FileWriter fw=new FileWriter("/opt/HARDKEY");
                fw.write(key);
                fw.flush();
                fw.close();
            } catch (IOException ex) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Houve um erro no processo: "+ex.getMessage(),null));
            }
            FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Sistema Licenciado com Sucesso!",null));
            return "index";
        }
        FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Esta cópia do JPBX não está licenciada! Os serviços continuam funcionando mas o controle ficará bloqueado. Entre em contato com seu mantenedor.",null));
        return null;
    }

    public String getSerial() {        
        return new LinuxInfo().hwKey();
    }
    
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    
}
