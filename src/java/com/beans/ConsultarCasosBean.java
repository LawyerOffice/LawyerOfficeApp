/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;
import procuradoria.crud.ProcuradoriaMethods;
import procuradoria.map.Uzatasign;
import procuradoria.map.Uzatcaso;
import procuradoria.map.Uzatcomt;
import procuradoria.map.Uzatfunci;

/**
 *
 * @author FANNY
 */
@ManagedBean
@ViewScoped
public class ConsultarCasosBean {

    /**
     * Creates a new instance of ConsultarCasosBean
     */
    private Uzatcaso Caso;
    private Uzatcaso SlectedCaso;
    private ArrayList<Uzatcaso> ListCasos;
    
    private Uzatasign Asignar;
    
    private ArrayList<Uzatcomt> ListComtFases;

    public ConsultarCasosBean() {
        this.init();
    }

    private void init() {
        this.setCaso(new Uzatcaso());
        this.setSlectedCaso ( new Uzatcaso());
        this.setAsignar(new Uzatasign());
        this.setListCasos(new ArrayList<Uzatcaso>());
        this.setListComtFases(new ArrayList<Uzatcomt>());
        this.loadlistCasos();
    }
    
    public void loadlistCasos() {
        this.ListCasos.clear();
        this.ListCasos = ProcuradoriaMethods.GetActiveCasos();
    }

    public void onRowSelectCaso(SelectEvent event) {
        this.SlectedCaso = (Uzatcaso) event.getObject();
        this.Asignar =  ProcuradoriaMethods.GetActiveAbogadosByIdCaso(this.SlectedCaso.getUzatcasoId());
        this.ListComtFases = ProcuradoriaMethods.GetFasesComentByIdCaso(this.SlectedCaso.getUzatcasoId());
    }

    public Uzatcaso getCaso() {
        return Caso;
    }

    public void setCaso(Uzatcaso Caso) {
        this.Caso = Caso;
    }

    public ArrayList<Uzatcaso> getListCasos() {
        return ListCasos;
    }

    public void setListCasos(ArrayList<Uzatcaso> ListCasos) {
        this.ListCasos = ListCasos;
    }

    public Uzatcaso getSlectedCaso() {
        return SlectedCaso;
    }

    public void setSlectedCaso(Uzatcaso SlectedCaso) {
        this.SlectedCaso = SlectedCaso;
    }

    public Uzatasign getAsignar() {
        return Asignar;
    }

    public void setAsignar(Uzatasign Asignar) {
        this.Asignar = Asignar;
    }

    public ArrayList<Uzatcomt> getListComtFases() {
        return ListComtFases;
    }

    public void setListComtFases(ArrayList<Uzatcomt> ListComtFases) {
        this.ListComtFases = ListComtFases;
    }
}
