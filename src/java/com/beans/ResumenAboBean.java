/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.beans;

import com.util.LazyCasoDataModel;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.model.LazyDataModel;
import procuradoria.map.Uzatcaso;
import procuradoria.crud.ProcuradoriaMethods;
import procuradoria.map.Uzatactor;

/**
 *
 * @author FANNY
 */
@ManagedBean
@ViewScoped
public class ResumenAboBean implements Serializable{

    /**
     * Creates a new instance of ResumenProcuBean
     */
    private String idCaso;
    private String cedulaActor;
    private String tipoActor;
            
    private LazyDataModel<Uzatcaso> lazyModelCasosAsignados;        
    
    private Uzatcaso selectedCaso;
    private Uzatactor selectedActor;
    
    public ResumenAboBean() {
        
        lazyModelCasosAsignados = new LazyCasoDataModel(new BigDecimal(2));
        selectedCaso = new Uzatcaso();
        this.selectedActor = new Uzatactor();
        this.idCaso = "vacio";
        this.cedulaActor= "vacio";
    }

    private BigDecimal getUserSession(){
        BigDecimal id = new BigDecimal(112);
        return id;
    }
    
    public void findCasobyid(String id)
    {
        this.idCaso = id ;
        this.selectedCaso = ProcuradoriaMethods.findCasobyId(new BigDecimal(idCaso));
    }
    
    public void findActorbycedula()
    {
        this.selectedActor = ProcuradoriaMethods.findActorbyCedula(this.cedulaActor);
    }
       
    public void buttonAction(ActionEvent actionEvent) {
        addMessage("Welcome to Primefaces!!");
    }
     
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

// <editor-fold defaultstate="collapsed" desc=" Getters and Setters ">
    public Uzatcaso getSelectedCaso() {
        return selectedCaso;
    }
    
    public void setSelectedCaso(Uzatcaso selectedCaso) {
        this.selectedCaso = selectedCaso;
    }
    
    public LazyDataModel<Uzatcaso> getLazyModelCasosAsignados() {
        return lazyModelCasosAsignados;
    }
    
    public void setLazyModelCasosAsignados(LazyDataModel<Uzatcaso> lazyModelCasosAsignados) {
        this.lazyModelCasosAsignados = lazyModelCasosAsignados;
    }
    
    public String getIdCaso() {
        return idCaso;
    }
    
    public void setIdCaso(String idCaso) {
        this.idCaso = idCaso;
    }
    
    public String getTipoActor() {
        return tipoActor;
    }
    
    public void setTipoActor(String tipoActor) {
        this.tipoActor = tipoActor;
    }
    
    public Uzatactor getSelectedActor() {
        return selectedActor;
    }
    
    public void setSelectedActor(Uzatactor selectedActor) {
        this.selectedActor = selectedActor;
    }
    
    public String getCedulaActor() {
        return cedulaActor;
    }
    
    public void setCedulaActor(String cedulaActor) {
        this.cedulaActor = cedulaActor;
    }

// </editor-fold>
    
    
}

