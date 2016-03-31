/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import com.util.LawyerOfficeUtil;
import com.util.LazyCasoDataModel;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.Visibility;
import procuradoria.crud.ProcuradoriaMethods;
import procuradoria.map.Uzatasign;
import procuradoria.map.Uzatcaso;
import procuradoria.map.Uzatcomt;
import procuradoria.map.Uzatfase;

/**
 *
 * @author FANNY
 */
@ManagedBean
@ViewScoped
public class ConsultarCasosBean {

    /**
     * Creates a new instance of ConsultarCasosBean
     */
    private Uzatcaso Caso;
    private Uzatcaso SlectedCaso;
    private ArrayList<Uzatcaso> ListCasos;
    
    private LazyDataModel<Uzatcaso> lazyModelCasosActivos;
    
    private Uzatasign Asignar;
    
    private ArrayList<Uzatcomt> ListComtFases;
    
    private ArrayList<Uzatfase> ListFases;
    private Uzatfase SelectedFase;
    
    private ArrayList<Uzatcomt> ListComtFasesById;

    public ConsultarCasosBean() {
        this.init();
    }

    private void init() {
        setLazyModelCasosActivos(new LazyCasoDataModel(BigDecimal.ONE));
        this.setCaso(new Uzatcaso());
        this.setSlectedCaso ( new Uzatcaso());
        this.setSelectedFase ( new Uzatfase());
        this.setAsignar(new Uzatasign());
        this.setListCasos(new ArrayList<Uzatcaso>());
        this.setListComtFases(new ArrayList<Uzatcomt>());
        this.setListFases(new ArrayList<Uzatfase>());
        //this.loadlistCasos();
    }
    
    public void loadlistCasos() {
        this.ListCasos.clear();
        this.ListCasos = ProcuradoriaMethods.ListCasosByFlag(BigDecimal.ONE);
    }

    public void onRowSelectCaso(SelectEvent event) {
        this.SlectedCaso = (Uzatcaso) event.getObject();
        this.Asignar =  ProcuradoriaMethods.GetActiveAbogadosByIdCaso(this.SlectedCaso.getUzatcasoId());
        this.ListFases = ProcuradoriaMethods.listFasesByIdCaso(this.SlectedCaso.getUzatcasoId());
    }
    
     public void openCaso(ActionEvent event, BigDecimal uzatcasoId) {
        RequestContext context = RequestContext.getCurrentInstance();
        String ruta = LawyerOfficeUtil.getURL_Login() + "views/ver_caso_procu.xhtml";
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
                put("uzatcasoId", uzatcasoId);
        context.addCallbackParam("loggedIn", true);
        context.addCallbackParam("ruta", ruta);
    }

    public Uzatcaso getCaso() {
        return Caso;
    }

    public void setCaso(Uzatcaso Caso) {
        this.Caso = Caso;
    }

    public ArrayList<Uzatcaso> getListCasos() {
        return ListCasos;
    }

    public void setListCasos(ArrayList<Uzatcaso> ListCasos) {
        this.ListCasos = ListCasos;
    }

    public Uzatcaso getSlectedCaso() {
        return SlectedCaso;
    }

    public void setSlectedCaso(Uzatcaso SlectedCaso) {
        this.SlectedCaso = SlectedCaso;
    }

    public Uzatasign getAsignar() {
        return Asignar;
    }

    public void setAsignar(Uzatasign Asignar) {
        this.Asignar = Asignar;
    }

    public ArrayList<Uzatcomt> getListComtFases() {
        return ListComtFases;
    }

    public void setListComtFases(ArrayList<Uzatcomt> ListComtFases) {
        this.ListComtFases = ListComtFases;
    }

    public LazyDataModel<Uzatcaso> getLazyModelCasosActivos() {
        return lazyModelCasosActivos;
    }

    public void setLazyModelCasosActivos(LazyDataModel<Uzatcaso> lazyModelCasosActivos) {
        this.lazyModelCasosActivos = lazyModelCasosActivos;
    }

    public ArrayList<Uzatfase> getListFases() {
        return ListFases;
    }

    public void setListFases(ArrayList<Uzatfase> ListFases) {
        this.ListFases = ListFases;
    }

    public Uzatfase getSelectedFase() {
        return SelectedFase;
    }

    public void setSelectedFase(Uzatfase SelectedFase) {
        this.SelectedFase = SelectedFase;
    }

    public ArrayList<Uzatcomt> getListComtFasesById() {
        return ListComtFasesById;
    }

    public void setListComtFasesById(ArrayList<Uzatcomt> ListComtFasesById) {
        this.ListComtFasesById = ListComtFasesById;
    }
    
    public void onRowToggle(ToggleEvent event) {
        this.setSelectedFase((Uzatfase) event.getData());
        if (event.getVisibility() == Visibility.VISIBLE) {
            this.setListComtFasesById(ProcuradoriaMethods.GetFasesComentByIdCasoAndIdFase(SlectedCaso.getUzatcasoId(), SelectedFase.getId().getUzatfaseId()));
        }
    }
}
