/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ivan
 */
public class Abogado {

    private DatosPersonales abogado;
    private String CasosAsigando;
    private String UltimaActividad;
    private String CasosFinalizados;
    private String UltimoCaso;
    private List<Caso> Casos;

    public Abogado() {
        this.Casos = new ArrayList<Caso>();
        this.UltimoCaso = "";
    }

    public Abogado(DatosPersonales abogado, String CasosAsigando, String UltimaActividad, String CasosFinalizados) {
        this.abogado = abogado;
        this.CasosAsigando = CasosAsigando;
        this.UltimaActividad = UltimaActividad;
        this.CasosFinalizados = CasosFinalizados;
    }

    public DatosPersonales getAbogado() {
        return abogado;
    }

    public void setAbogado(DatosPersonales abogado) {
        this.abogado = abogado;
    }

    public String getCasosAsigando() {
        return CasosAsigando;
    }

    public void setCasosAsigando(String CasosAsigando) {
        this.CasosAsigando = CasosAsigando;
    }

    public String getUltimaActividad() {
        return UltimaActividad;
    }

    public void setUltimaActividad(String UltimaActividad) {
        this.UltimaActividad = UltimaActividad;
    }

    public String getCasosFinalizados() {
        return CasosFinalizados;
    }

    public void setCasosFinalizados(String CasosFinalizados) {
        this.CasosFinalizados = CasosFinalizados;
    }

    public List<Caso> getCasos() {
        return Casos;
    }

    public void setCasos(List<Caso> Casos) {
        this.Casos = Casos;
    }

    public String getUltimoCaso() {
        if (!this.Casos.isEmpty()) {
            int num = this.Casos.size() - 1;
            Caso ultimo = this.Casos.get(num);
            UltimoCaso = ultimo.getEstado() + " " + ultimo.getFechaActual() + " " + ultimo.getFechaInicio() + " " + ultimo.getNumeroCaso();
        }
        return UltimoCaso;
    }

    public void setUltimoCaso(String UltimoCaso) {
        this.UltimoCaso = UltimoCaso;
    }

}
