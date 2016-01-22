/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.FlowEvent;
import procuradoria.map.Uztactor;
import procuradoria.map.Uztcaso;

/**
 *
 * @author Daniel
 */
@ManagedBean
@ViewScoped
public class NuevoCasoBean implements Serializable {

    /**
     * Creates a new instance of NuevoCasoBean
     */
    private Uztcaso NuevoCaso;
    private Uztactor NuevoActor;

    private ArrayList<Uztactor> NuevoDemandante;
    private ArrayList<Uztactor> NuevoDemandado;
    //TIPOS DE BANDERAS PARA DEFINIR ACTOR
    //0-DEMANDANTE
    //1-DEMANDADO

    public NuevoCasoBean() {
        this.init();
        if (this.NuevoCaso == null) {
            this.NuevoCaso = new Uztcaso();
        }
        if (this.NuevoActor == null) {
            this.NuevoActor = new Uztactor();
        }
    }

    private void init() {
        this.setNuevoCaso(new Uztcaso());
        this.setNuevoActor(new Uztactor());
        this.setNuevoDemandado(new ArrayList<Uztactor>());
        this.setNuevoDemandante(new ArrayList<Uztactor>());
    }

    public Uztcaso getNuevoCaso() {
        return NuevoCaso;
    }

    public void setNuevoCaso(Uztcaso NuevoCaso) {
        this.NuevoCaso = NuevoCaso;
    }

    public Uztactor getNuevoActor() {
        return NuevoActor;
    }

    public void setNuevoActor(Uztactor NuevoActor) {
        this.NuevoActor = NuevoActor;
    }

    public ArrayList<Uztactor> getNuevoDemandante() {
        return NuevoDemandante;
    }

    public void setNuevoDemandante(ArrayList<Uztactor> NuevoDemandante) {
        this.NuevoDemandante = NuevoDemandante;
    }

    public ArrayList<Uztactor> getNuevoDemandado() {
        return NuevoDemandado;
    }

    public void setNuevoDemandado(ArrayList<Uztactor> NuevoDemandado) {
        this.NuevoDemandado = NuevoDemandado;
    }

    ////////////////////////////////////////////////////////////////////////////
    private boolean skip;

    public void save() {
        FacesMessage msg = new FacesMessage("Successful", "Welcome :" + NuevoActor.getUztactorId());
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
            NuevoDemandante.add(NuevoActor);
            NuevoActor = new Uztactor();

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
