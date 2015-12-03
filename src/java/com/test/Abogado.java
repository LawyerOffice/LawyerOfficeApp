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
    private List<Caso> mCasos;

    public Abogado() {
        this.mCasos = new ArrayList<Caso>();
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

    public List<Caso> getmCasos() {
        return mCasos;
    }

    public void setmCasos(List<Caso> mCasos) {
        this.mCasos = mCasos;
    }
            
}
