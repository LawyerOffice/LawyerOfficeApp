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
import procuradoria.crud.ProcuradoriaMethods;
import procuradoria.map.Uzatasign;
import procuradoria.map.Uzatcaso;
import procuradoria.map.Uzatmateri;

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
    private Uzatasign newAsignador;
    private Uzatcaso newCaso;
    private int materia;
    private int judi;
    
    
    private ArrayList<SelectItem> ItemsMaterias;
    private ArrayList<SelectItem> ItemsJudicaturas;
    
    public GenerarCasoBean() {
    }
    
    public void init(){
        this.ItemsJudicaturas = new ArrayList<SelectItem>();
        this.ItemsMaterias = new ArrayList<SelectItem>();
        this.loadlistMaterias();
    }

    
    
    
   public void loadlistMaterias() {
       //Dennis Santamaria
       
        ArrayList<Uzatmateri> selectItemsMat = ProcuradoriaMethods.ListMaterias();
        this.ItemsMaterias.clear();
        SelectItem  si;
        for (int i = 0; i < selectItemsMat.size(); i++) {
            si = new SelectItem(selectItemsMat.get(i).getUzatmateriaId(),selectItemsMat.get(i).getUzatmateriaDescripcion());
                this.ItemsMaterias.add(si);
            
        }
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

    public Uzatasign getNewAsignador() {
        return newAsignador;
    }

    public void setNewAsignador(Uzatasign newAsignador) {
        this.newAsignador = newAsignador;
    }

    public Uzatcaso getNewCaso() {
        return newCaso;
    }

    public void setNewCaso(Uzatcaso newCaso) {
        this.newCaso = newCaso;
    }

    public void setItemsJudicaturas(ArrayList<SelectItem> ItemsJudicaturas) {
        this.ItemsJudicaturas = ItemsJudicaturas;
    }
    
}
