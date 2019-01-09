/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jpbx.util;

/**
 * 
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
public class SelectMany {

    private String label;
    private String labelAux;
    private String value;

    public SelectMany() {
    }

    public SelectMany(String label, String labelAux, String value) {
        this.label = label;
        this.labelAux = labelAux;
        this.value = value;
    }
    

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabelAux() {
        return labelAux;
    }

    public void setLabelAux(String labelAux) {
        this.labelAux = labelAux;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
}
