/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.test;

/**
 *
 * @author Ivan
 */
public class Notificaciones {
    
    private String Abogado;
    private String Caso;
    private String Detalle;

    public Notificaciones() {
    }

    public Notificaciones(String Abogado, String Caso, String Detalle) {
        this.Abogado = Abogado;
        this.Caso = Caso;
        this.Detalle = Detalle;
    }
    

    public String getAbogado() {
        return Abogado;
    }

    public void setAbogado(String Abogado) {
        this.Abogado = Abogado;
    }

    public String getCaso() {
        return Caso;
    }

    public void setCaso(String Caso) {
        this.Caso = Caso;
    }

    public String getDetalle() {
        return Detalle;
    }

    public void setDetalle(String Detalle) {
        this.Detalle = Detalle;
    }
    
}
