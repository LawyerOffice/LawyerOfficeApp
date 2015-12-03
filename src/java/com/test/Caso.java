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
public class Caso {
    
    private String NumeroCaso;
    private String FechaInicio;
    private String FechaActual;
    private String Estado;

    public Caso() {
    }

    public Caso(String NumeroCaso, String FechaInicio, String FechaActual, String Estado) {
        this.NumeroCaso = NumeroCaso;
        this.FechaInicio = FechaInicio;
        this.FechaActual = FechaActual;
        this.Estado = Estado;
    }

    public String getNumeroCaso() {
        return NumeroCaso;
    }

    public void setNumeroCaso(String NumeroCaso) {
        this.NumeroCaso = NumeroCaso;
    }

    public String getFechaInicio() {
        return FechaInicio;
    }

    public void setFechaInicio(String FechaInicio) {
        this.FechaInicio = FechaInicio;
    }

    public String getFechaActual() {
        return FechaActual;
    }

    public void setFechaActual(String FechaActual) {
        this.FechaActual = FechaActual;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }
    
}
