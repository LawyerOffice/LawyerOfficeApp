/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Daniel
 */
@ManagedBean
@RequestScoped
public class capNewCaso {

    private String nombreDemandante;
    private String numeroCausa;
    private String tribunal;

    public capNewCaso() {
    }

    public String getNombreDemandante() {
        return nombreDemandante;
    }

    public void setNombreDemandante(String nombreDemandante) {
        this.nombreDemandante = nombreDemandante;
    }

    public String getNumeroCausa() {
        return numeroCausa;
    }

    public void setNumeroCausa(String numeroCausa) {
        this.numeroCausa = numeroCausa;
    }

    public String getTribunal() {
        return tribunal;
    }

    public void setTribunal(String tribunal) {
        this.tribunal = tribunal;
    }

}
