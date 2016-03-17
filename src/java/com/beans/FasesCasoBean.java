/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;
import procuradoria.crud.ProcuradoriaMethods;
import procuradoria.map.Uzatcaso;
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
    private Uzatcaso SelectedCaso;
    private BigDecimal CodCaso;
    private Boolean StateFaseDisabled;

    private Uzatfase NewFase;
    private Uzatcomt NewComentario;

    public FasesCasoBean() {
        CodCaso = this.getCasoIdAttribute();
        this.setSelectedCaso(ProcuradoriaMethods.CasoByIdCaso(CodCaso));
        this.setStateFaseDisabled(false);
        this.setSelectedFase(new Uzatfase());
        this.setNewFase(new Uzatfase());
        this.setListFases(new ArrayList<Uzatfase>());
        this.setListComtFasesById(new ArrayList<Uzatcomt>());
        this.setListDocsFasesById(new ArrayList<Uzatdocs>());
        this.setListCitaFasesById(new ArrayList<Uzatcita>());
        this.setNewComentario(new Uzatcomt());
        this.init();
    }
    
    private String getUserAttribute() {
        String UserAttribute = "";
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        if (session == null) {
        } else {
            Object IdBanner = session.getAttribute("uzatfuncionarioId");
            UserAttribute = IdBanner.toString();
        }
        return UserAttribute;
    }

    private BigDecimal getCasoIdAttribute() {
        String UserAttribute = "";
        BigDecimal id = new BigDecimal(BigInteger.ZERO);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        if (session == null) {
        } else {
            Object IdBanner = session.getAttribute("uzatcasoId");
            UserAttribute = IdBanner.toString();
            id = new BigDecimal(UserAttribute);
        }
        return id;
    }

    private void init() {
        this.ListFases = ProcuradoriaMethods.listFasesByIdCaso(SelectedCaso.getUzatcasoId());
    }

    private void initComentarios() {
        this.ListComtFasesById = ProcuradoriaMethods.GetFasesComentByIdCasoAndIdFase(SelectedCaso.getUzatcasoId(), SelectedFase.getId().getUzatfaseId());
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

    public Uzatfase getNewFase() {
        return NewFase;
    }

    public void setNewFase(Uzatfase NewFase) {
        this.NewFase = NewFase;
    }

    public Uzatcaso getSelectedCaso() {
        return SelectedCaso;
    }

    public void setSelectedCaso(Uzatcaso SelectedCaso) {
        this.SelectedCaso = SelectedCaso;
    }

    public Uzatcomt getNewComentario() {
        return NewComentario;
    }

    public void setNewComentario(Uzatcomt NewComentario) {
        this.NewComentario = NewComentario;
    }

    public void onTabChange(TabChangeEvent event) {
        if (event.getTab().getId().equals("TabDocumentos")) {
            this.ListDocsFasesById = ProcuradoriaMethods.FindDocsbyCaso_Fase(SelectedCaso.getUzatcasoId(), SelectedFase.getId().getUzatfaseId());
        } else {
            if (event.getTab().getId().equals("TabCitas")) {
                this.ListCitaFasesById = ProcuradoriaMethods.FindCitasbyCaso_Fase(SelectedCaso.getUzatcasoId(), SelectedFase.getId().getUzatfaseId());
            }
        }
    }

    public void onRowToggle(ToggleEvent event) {
        this.SelectedFase = (Uzatfase) event.getData();
        if (event.getVisibility() == Visibility.VISIBLE) {
            this.initComentarios();
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

    public String FechaHoraActual() {
        GregorianCalendar g1 = new GregorianCalendar();
        SimpleDateFormat s1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return s1.format(g1.getTime());
    }
    
    public void genratedFase(ActionEvent event) {
        this.NewFase.getId().setUzatcasoId(SelectedCaso.getUzatcasoId());
        this.NewFase.setUzatfaseFechaIn(FechaHoraActual());
        this.NewFase.setUzatfaseFlag(BigDecimal.ONE);

        Boolean exito = ProcuradoriaMethods.InsertFase(this.NewFase);
        if (exito) {
            RequestContext.getCurrentInstance().execute("PF('dlgNewFaseMSG').show();");
            this.init();
        }
    }
    
    public void genratedComentario(ActionEvent event) {
        this.NewComentario.getId().setUzatcasoId(SelectedCaso.getUzatcasoId());
        this.NewComentario.getId().setUzatfaseId(SelectedFase.getId().getUzatfaseId());
        this.NewComentario.setUzatcomtFecha(FechaHoraActual());

        Boolean exito = ProcuradoriaMethods.InsertComentario(this.NewComentario);
        if (exito) {
            RequestContext.getCurrentInstance().execute("PF('dlgNewComentarioMSG').show();");
            this.initComentarios();
        }
    }

}
