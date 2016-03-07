/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import java.math.BigDecimal;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;
import procuradoria.crud.ProcuradoriaMethods;
import procuradoria.map.Uzatcaso;
import procuradoria.map.Uzatcomt;

/**
 *
 * @author FANNY
 */
@ManagedBean
@ViewScoped
public class FasesCasoBean {

    /**
     * Creates a new instance of FasesCasoBean
     */
    private Uzatcaso SelectedCaso;
    private ArrayList<Uzatcomt> ListComtFases;

    public FasesCasoBean() {
        this.setSelectedCaso(new Uzatcaso());
        this.setListComtFases(new ArrayList<Uzatcomt>());
        this.init();
    }

    private void init() {
        this.ListComtFases = ProcuradoriaMethods.GetFasesComentByIdCaso(BigDecimal.valueOf(100));
    }

    public void onRowSelectCaso(SelectEvent event) {
        this.SelectedCaso = (Uzatcaso) event.getObject();
        //DETALLES DEL ABOGADO //this.Asignar =  ProcuradoriaMethods.GetActiveAbogadosByIdCaso(this.SelectedCaso.getUzatcasoId());
        //AQUI VIENE EL ID DEL CASO//this.ListComtFases = ProcuradoriaMethods.GetFasesComentByIdCaso(this.SelectedCaso.getUzatcasoId());
    }

    public Uzatcaso getSelectedCaso() {
        return SelectedCaso;
    }

    public void setSelectedCaso(Uzatcaso SelectedCaso) {
        this.SelectedCaso = SelectedCaso;
    }

    public ArrayList<Uzatcomt> getListComtFases() {
        return ListComtFases;
    }

    public void setListComtFases(ArrayList<Uzatcomt> ListComtFases) {
        this.ListComtFases = ListComtFases;
    }

}
