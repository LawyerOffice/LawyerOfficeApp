/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import java.math.BigDecimal;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;
import procuradoria.crud.ProcuradoriaMethods;
import procuradoria.map.Uzatcaso;
import procuradoria.map.Uzatcomt;
import procuradoria.map.Uzatfase;

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
    private ArrayList<Uzatfase> ListFases;
    private ArrayList<Uzatcomt> ListComtFasesById;
    private Uzatfase SelectedFase;

    public FasesCasoBean() {
        SelectedFase=new Uzatfase();
        this.setListFases(new ArrayList<Uzatfase>());
        this.setListComtFasesById(new ArrayList<Uzatcomt>());
        this.init();
    }

    private void init() {
        this.ListFases = ProcuradoriaMethods.listFasesByIdCaso(BigDecimal.valueOf(100));
    }

    public void onRowSelectCmt(SelectEvent event) {
        this.SelectedFase = (Uzatfase) event.getObject();
    }

    public ArrayList<Uzatfase> getListFases() {
        return ListFases;
    }

    public void setListFases(ArrayList<Uzatfase> ListComtFases) {
        this.ListFases = ListComtFases;
    }

    public Uzatfase getSelectedFase() {
        return SelectedFase;
    }

    public void setSelectedFase(Uzatfase Selectedfase) {
        this.SelectedFase = Selectedfase;
    }

    public ArrayList<Uzatcomt> getListComtFasesById() {
        return ListComtFasesById;
    }

    public void setListComtFasesById(ArrayList<Uzatcomt> ListComtFasesById) {
        this.ListComtFasesById = ListComtFasesById;
    }
    
    public void onTabChange(TabChangeEvent event) {
        FacesMessage msg = new FacesMessage("Tab Changed", "Active Tab: " + event.getTab().getId()+ event.getTab().getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowToggle(ToggleEvent event) {
        this.SelectedFase = (Uzatfase) event.getData();
    if (event.getVisibility() == Visibility.VISIBLE) {
        this.ListComtFasesById = ProcuradoriaMethods.GetFasesComentByIdCasoAndIdFase(BigDecimal.valueOf(100),SelectedFase.getId().getUzatfaseId());
    }
}
    
}
