/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.beans;

import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import procuradoria.map.Uztasignar;
import procuradoria.map.Uztcaso;

/**
 *
 * @author Ivan
 */
@ManagedBean
@ViewScoped
public class GenerarCasoBean {

    /**
     * Creates a new instance of GenerarCasoBean
     */
    private Uztasignar newAsignador;
    private Uztcaso newCaso;
            
    private ArrayList<SelectItem> ItemsMaterias;
    private ArrayList<SelectItem> ItemsJudicaturas;
    
    public GenerarCasoBean() {
    }
    
    public void init(){
        this.ItemsJudicaturas = new ArrayList<SelectItem>();
        this.ItemsMaterias = new ArrayList<SelectItem>();
    }

    public ArrayList<SelectItem> getItemsMaterias() {
        return ItemsMaterias;
    }

    public void setItemsMaterias(ArrayList<SelectItem> ItemsMaterias) {
        this.ItemsMaterias = ItemsMaterias;
    }

    public ArrayList<SelectItem> getItemsJudicaturas() {
        return ItemsJudicaturas;
    }

    public Uztasignar getNewAsignador() {
        return newAsignador;
    }

    public void setNewAsignador(Uztasignar newAsignador) {
        this.newAsignador = newAsignador;
    }

    public Uztcaso getNewCaso() {
        return newCaso;
    }

    public void setNewCaso(Uztcaso newCaso) {
        this.newCaso = newCaso;
    }

    public void setItemsJudicaturas(ArrayList<SelectItem> ItemsJudicaturas) {
        this.ItemsJudicaturas = ItemsJudicaturas;
    }
    
}
