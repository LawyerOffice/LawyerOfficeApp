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
    private String gotCaso;
    private String tipoActor;
            
    private LazyDataModel<Uzatcaso> lazyModelCasosAsignados;        
    
    private Uzatcaso selectedCaso;
    
    
    public ResumenAboBean() {
        
        lazyModelCasosAsignados = new LazyCasoDataModel(new BigDecimal(2));
        selectedCaso = new Uzatcaso();
        this.gotCaso = "no entro";
    }

    public Uzatcaso getSelectedCaso() {
        return selectedCaso;
    }

    public void setSelectedCaso(Uzatcaso selectedCaso) {
        this.selectedCaso = selectedCaso;
    }

    private BigDecimal getUserSession(){
        BigDecimal id = new BigDecimal(112);
        return id;
    }
    
    public void getCaso(String entro)
    {
        this.gotCaso = entro ;
        this.selectedCaso = ProcuradoriaMethods.findCasobyId(new BigDecimal(gotCaso));
    }
    
    public LazyDataModel<Uzatcaso> getLazyModelCasosAsignados() {
        return lazyModelCasosAsignados;
    }

    public void setLazyModelCasosAsignados(LazyDataModel<Uzatcaso> lazyModelCasosAsignados) {
        this.lazyModelCasosAsignados = lazyModelCasosAsignados;
    }

    public String getGotCaso() {
        return gotCaso;
    }

    public void setGotCaso(String gotCaso) {
        this.gotCaso = gotCaso;
    }
    
    
    public void buttonAction(ActionEvent actionEvent) {
        addMessage("Welcome to Primefaces!!");
    }
     
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String getTipoActor() {
        return tipoActor;
    }

    public void setTipoActor(String tipoActor) {
        this.tipoActor = tipoActor;
    }
    
}

