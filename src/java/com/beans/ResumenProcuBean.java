/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.beans;

import com.util.LazyCasoDataModel;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.LazyDataModel;
import procuradoria.map.Uzatcaso;

/**
 *
 * @author FANNY
 */
@ManagedBean
@ViewScoped
public class ResumenProcuBean implements Serializable{

    /**
     * Creates a new instance of ResumenProcuBean
     */
    
    private LazyDataModel<Uzatcaso> lazyModel;
     
    private Uzatcaso selectedCaso;
    
    public ResumenProcuBean() {
        lazyModel = new LazyCasoDataModel();
    }

    public LazyDataModel<Uzatcaso> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<Uzatcaso> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public Uzatcaso getSelectedCaso() {
        return selectedCaso;
    }

    public void setSelectedCaso(Uzatcaso selectedCaso) {
        this.selectedCaso = selectedCaso;
    }
    
}
