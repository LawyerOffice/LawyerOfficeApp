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
import procuradoria.map.Uzatcaso;

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
    private ArrayList<Uzatcaso> ListCasos;

    public ConsultarCasosBean() {
        this.init();
    }

    private void init() {
        this.setCaso(new Uzatcaso());
        this.setListCasos(new ArrayList<Uzatcaso>());
        this.loadlistCasos();
    }
    
    public void loadlistCasos() {
        this.ListCasos.clear();
        this.ListCasos = ProcuradoriaMethods.GetCasosActivos();
    }

    public void onRowSelectCaso(SelectEvent event) {
        this.Caso = (Uzatcaso) event.getObject();
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
}
