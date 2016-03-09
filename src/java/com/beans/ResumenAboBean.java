/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.beans;

import com.util.LazyCasoDataModel;
import java.io.Serializable;
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
public class ResumenAboBean implements Serializable{

    /**
     * Creates a new instance of ResumenProcuBean
     */
    
    private LazyDataModel<Uzatcaso> lazyModelCasosActivos;
    private LazyDataModel<Uzatcaso> lazyModelCasosInactivos;
    private LazyDataModel<Uzatcaso> lazyModelCasosAsignados;        
     
    private Uzatcaso selectedCaso;
    
    public ResumenAboBean() {
        lazyModelCasosActivos = new LazyCasoDataModel(BigDecimal.ONE);
        lazyModelCasosInactivos = new LazyCasoDataModel(BigDecimal.ZERO);
        lazyModelCasosAsignados = new LazyCasoDataModel(new BigDecimal(2));
    }

    public Uzatcaso getSelectedCaso() {
        return selectedCaso;
    }

    public void setSelectedCaso(Uzatcaso selectedCaso) {
        this.selectedCaso = selectedCaso;
    }

    public LazyDataModel<Uzatcaso> getLazyModelCasosActivos() {
        return lazyModelCasosActivos;
    }

    public void setLazyModelCasosActivos(LazyDataModel<Uzatcaso> lazyModelCasosActivos) {
        this.lazyModelCasosActivos = lazyModelCasosActivos;
    }

    public LazyDataModel<Uzatcaso> getLazyModelCasosInactivos() {
        return lazyModelCasosInactivos;
    }

    public void setLazyModelCasosInactivos(LazyDataModel<Uzatcaso> lazyModelCasosInactivos) {
        this.lazyModelCasosInactivos = lazyModelCasosInactivos;
    }

    public LazyDataModel<Uzatcaso> getLazyModelCasosAsignados() {
        return lazyModelCasosAsignados;
    }

    public void setLazyModelCasosAsignados(LazyDataModel<Uzatcaso> lazyModelCasosAsignados) {
        this.lazyModelCasosAsignados = lazyModelCasosAsignados;
    }
    
}
