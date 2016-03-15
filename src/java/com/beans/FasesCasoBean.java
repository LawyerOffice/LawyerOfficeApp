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
import org.jdom.Document;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;
import procuradoria.crud.ProcuradoriaMethods;
import procuradoria.map.Uzatcita;
import procuradoria.map.Uzatcomt;
import procuradoria.map.Uzatdocs;
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
    private ArrayList<Uzatdocs> ListDocsFasesById;
    private ArrayList<Uzatcita> ListCitaFasesById;
    private Uzatfase SelectedFase;
    private BigDecimal CodCaso;
    private Boolean StateFaseDisabled;

    public FasesCasoBean() {
        CodCaso = BigDecimal.valueOf(100);
        this.setStateFaseDisabled(false);
        this.setSelectedFase(new Uzatfase());
        this.setListFases(new ArrayList<Uzatfase>());
        this.setListComtFasesById(new ArrayList<Uzatcomt>());
        this.setListDocsFasesById(new ArrayList<Uzatdocs>());
        this.setListCitaFasesById(new ArrayList<Uzatcita>());
        this.init();
    }

    private void init() {
        this.ListFases = ProcuradoriaMethods.listFasesByIdCaso(CodCaso);
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

    public ArrayList<Uzatdocs> getListDocsFasesById() {
        return ListDocsFasesById;
    }

    public void setListDocsFasesById(ArrayList<Uzatdocs> ListDocsFasesById) {
        this.ListDocsFasesById = ListDocsFasesById;
    }

    public ArrayList<Uzatcita> getListCitaFasesById() {
        return ListCitaFasesById;
    }

    public void setListCitaFasesById(ArrayList<Uzatcita> ListCitaFasesById) {
        this.ListCitaFasesById = ListCitaFasesById;
    }

    public Boolean getStateFaseDisabled() {
        return StateFaseDisabled;
    }

    public void setStateFaseDisabled(Boolean StateFaseDisabled) {
        this.StateFaseDisabled = StateFaseDisabled;
    }

    public void onTabChange(TabChangeEvent event) {
        if (event.getTab().getId().equals("TabDocumentos")) {
            this.ListDocsFasesById = ProcuradoriaMethods.FindDocsbyCaso_Fase(CodCaso, SelectedFase.getId().getUzatfaseId());
        } else {
            if (event.getTab().getId().equals("TabCitas")) {
                this.ListCitaFasesById = ProcuradoriaMethods.FindCitasbyCaso_Fase(CodCaso, SelectedFase.getId().getUzatfaseId());
            }
        }
    }

    public void onRowToggle(ToggleEvent event) {
        this.SelectedFase = (Uzatfase) event.getData();
        if (event.getVisibility() == Visibility.VISIBLE) {
            this.ListComtFasesById = ProcuradoriaMethods.GetFasesComentByIdCasoAndIdFase(CodCaso, SelectedFase.getId().getUzatfaseId());
        }
    }

    public Boolean estadoFaseDisabled() {
        if (SelectedFase.getUzatfaseFlag() == BigDecimal.ZERO) {
            setStateFaseDisabled(true);
        } else {
            setStateFaseDisabled(false);
        }
        return getStateFaseDisabled();
    }

}
