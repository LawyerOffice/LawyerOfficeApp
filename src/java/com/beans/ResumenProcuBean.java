/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.beans;

import com.util.LazyCasoDataModel;
import java.math.BigDecimal;
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
public class ResumenProcuBean{

    /**
     * Creates a new instance of ResumenProcuBean
     */

    private LazyDataModel<Uzatcaso> lazyModelCasosInactivos;
     
    private Uzatcaso selectedCaso;
    
    public ResumenProcuBean() {
        lazyModelCasosInactivos = new LazyCasoDataModel(new BigDecimal(2));
    }

    public Uzatcaso getSelectedCaso() {
        return selectedCaso;
    }

    public void setSelectedCaso(Uzatcaso selectedCaso) {
        this.selectedCaso = selectedCaso;
    }

    public LazyDataModel<Uzatcaso> getLazyModelCasosInactivos() {
        return lazyModelCasosInactivos;
    }

    public void setLazyModelCasosInactivos(LazyDataModel<Uzatcaso> lazyModelCasosInactivos) {
        this.lazyModelCasosInactivos = lazyModelCasosInactivos;
    }
    
}

