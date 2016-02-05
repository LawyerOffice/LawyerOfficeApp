/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.FlowEvent;
import procuradoria.map.Uzatactor;
import procuradoria.map.Uzatcaso;

/**
 *
 * @author Daniel
 */
@ManagedBean
@ViewScoped
public class NuevoCasoBean_v1 implements Serializable {

    /**
     * Creates a new instance of NuevoCasoBean
     */
    private Uzatcaso NuevoCaso;
    private Uzatactor NuevoActor;

    private ArrayList<Uzatactor> NuevoDemandante;
    private ArrayList<Uzatactor> NuevoDemandado;
    //TIPOS DE BANDERAS PARA DEFINIR ACTOR
    //0-DEMANDANTE
    //1-DEMANDADO

    public NuevoCasoBean_v1() {
        this.init();
        if (this.NuevoCaso == null) {
            this.NuevoCaso = new Uzatcaso();
        }
        if (this.NuevoActor == null) {
            this.NuevoActor = new Uzatactor();
        }
    }

    private void init() {
        this.setNuevoCaso(new Uzatcaso());
        this.setNuevoActor(new Uzatactor());
        this.setNuevoDemandado(new ArrayList<Uzatactor>());
        this.setNuevoDemandante(new ArrayList<Uzatactor>());
    }

    public Uzatcaso getNuevoCaso() {
        return NuevoCaso;
    }

    public void setNuevoCaso(Uzatcaso NuevoCaso) {
        this.NuevoCaso = NuevoCaso;
    }

    public Uzatactor getNuevoActor() {
        return NuevoActor;
    }

    public void setNuevoActor(Uzatactor NuevoActor) {
        this.NuevoActor = NuevoActor;
    }

    public ArrayList<Uzatactor> getNuevoDemandante() {
        return NuevoDemandante;
    }

    public void setNuevoDemandante(ArrayList<Uzatactor> NuevoDemandante) {
        this.NuevoDemandante = NuevoDemandante;
    }

    public ArrayList<Uzatactor> getNuevoDemandado() {
        return NuevoDemandado;
    }

    public void setNuevoDemandado(ArrayList<Uzatactor> NuevoDemandado) {
        this.NuevoDemandado = NuevoDemandado;
    }

    ////////////////////////////////////////////////////////////////////////////
    private boolean skip;

    public void save() {
        FacesMessage msg = new FacesMessage("Successful", "Welcome :" + NuevoActor.getUzatactorId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }

    public void AddItem(ActionEvent event) {
        if (NuevoActor != null) {
            NuevoActor.setUzatactorCedula(String.valueOf(NuevoActor.getUzatactorId()));
            NuevoDemandante.add(NuevoActor);
            NuevoActor = new Uzatactor();

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Éxito", "Demandante añadido correctamente."));
        } else {

        }
    }

    public void deleteCaso() {
        NuevoDemandante.remove(NuevoActor);
        NuevoActor = null;
    }

}
