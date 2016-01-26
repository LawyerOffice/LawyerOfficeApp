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
import org.primefaces.event.FlowEvent;
import java.util.Date;
import procuradoria.crud.ProcuradoriaMethods;
import procuradoria.map.Uztcaso;
import procuradoria.map.Uztfuncionario;

/**
 *
 * @author FANNY
 */
@ManagedBean
@ViewScoped
public class NuevoCasoBean implements Serializable {

    /**
     * Creates a new instance of NuevoCasoBean
     */
    private Uztcaso NuevoCaso;
    private Date date;

    public NuevoCasoBean() {
        this.init();
        if (this.NuevoCaso == null) {
            this.NuevoCaso = new Uztcaso();
        }
        if (this.date == null) {
            this.date = new Date();
        }
    }
    
        private void init() {
        this.setNuevoCaso(new Uztcaso());
        this.setDate(new Date());
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Uztcaso getNuevoCaso() {
        return NuevoCaso;
    }

    public void setNuevoCaso(Uztcaso NuevoCaso) {
        this.NuevoCaso = NuevoCaso;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    private boolean skip;

    public void save() {
        FacesMessage msg = new FacesMessage("Éxito", "Datos guardados correctamente del caso con Número de Causa : " + NuevoCaso.getUztcasoNumcausa());
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

    /////////////////////////////////////////////////////
    
//        public void loadlistActiveAbogados() {
//        ArrayList<Uztfuncionario> list2 = ProcuradoriaMethods.ListFuncionarios(BigDecimal.ONE);
//        this.ItemsFuncionarios.clear();
//        for(int i = 0 ; i < list2.size() ; i++){
//            this.ItemsFuncionarios.add(new SelectItem(list2.get(i).getUztfuncionarioId(),list2.get(i).getUztfuncionarioApellidos()+
//                    " "+list2.get(i).getUztfuncionarioNombres()));
//        }
//
//    }
    
}
