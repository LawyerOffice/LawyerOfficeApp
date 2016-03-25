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
import java.util.Date;
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
import org.primefaces.model.UploadedFile;
import org.primefaces.model.Visibility;
import procuradoria.crud.ProcuradoriaMethods;
import procuradoria.map.*;
import procuradoria.pdf.util.DocumentsPdf;

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

    private Uzatcaso SelectedCaso;
    private BigDecimal CodCaso;

    private Uzatfase SelectedFase;
    private Boolean StateFaseDisabled;

    private Uzatfase NewFase;
    private Uzatcomt NewComentario;

    private Uzatdocs NewDocumento;
    private String DirecURLDoc;
    private UploadedFile file;

    private Uzatcita NewCita;
    private Date FechaCita;

    private Boolean EnableNewFase;

    public FasesCasoBean() {
        CodCaso = this.getCasoIdAttribute();
        FechaCita = new Date();
        DirecURLDoc = "";
        this.EnableNewFase = true;
        this.setSelectedCaso(ProcuradoriaMethods.CasoByIdCaso(CodCaso));
        this.setStateFaseDisabled(false);
        this.setSelectedFase(new Uzatfase());
        this.setNewFase(new Uzatfase());
        this.setListFases(new ArrayList<Uzatfase>());
        this.setListComtFasesById(new ArrayList<Uzatcomt>());
        this.setListDocsFasesById(new ArrayList<Uzatdocs>());
        this.setListCitaFasesById(new ArrayList<Uzatcita>());
        this.setNewComentario(new Uzatcomt());
        this.setNewCita(new Uzatcita());
        this.setNewDocumento(new Uzatdocs());
        this.init();
    }

    private BigDecimal getUserAttribute() {
        String UserAttribute = "";
        BigDecimal id = new BigDecimal(BigInteger.ZERO);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        if (session == null) {
        } else {
            Object IdBanner = session.getAttribute("uzatfuncionarioId");
            UserAttribute = IdBanner.toString();
            id = new BigDecimal(UserAttribute);
        }
        return id;
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
    
    public void closeFaseTrigger(ActionEvent event){
        this.EnableNewFase = false;
    }

    private void init() {
        this.ListFases = ProcuradoriaMethods.listFasesByIdCaso(SelectedCaso.getUzatcasoId());
    }

    private void initComentarios() {
        this.ListComtFasesById = ProcuradoriaMethods.GetFasesComentByIdCasoAndIdFase(SelectedCaso.getUzatcasoId(), SelectedFase.getId().getUzatfaseId());
    }

    private void initCitas() {
        this.ListCitaFasesById = ProcuradoriaMethods.FindCitasbyCaso_Fase(SelectedCaso.getUzatcasoId(), SelectedFase.getId().getUzatfaseId());
    }

    private void initDocumentos() {
        this.ListDocsFasesById = ProcuradoriaMethods.FindDocsbyCaso_Fase(SelectedCaso.getUzatcasoId(), SelectedFase.getId().getUzatfaseId());
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

    public Uzatcita getNewCita() {
        return NewCita;
    }

    public void setNewCita(Uzatcita NewCita) {
        this.NewCita = NewCita;
    }

    public Date getFechaCita() {
        return FechaCita;
    }

    public Uzatdocs getNewDocumento() {
        return NewDocumento;
    }

    public void setNewDocumento(Uzatdocs NewDocumento) {
        this.NewDocumento = NewDocumento;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void onTabChange(TabChangeEvent event) {
        if (event.getTab().getId().equals("TabDocumentos")) {

        } else {
            if (event.getTab().getId().equals("TabCitas")) {
                this.initCitas();
            }
        }
    }

    public void onRowToggle(ToggleEvent event) {
        this.SelectedFase = (Uzatfase) event.getData();
        if (event.getVisibility() == Visibility.VISIBLE) {
            this.initComentarios();
        }
    }

    public Boolean estadoFaseDisabled(Uzatfase fasesCaso) {
        if (fasesCaso.getUzatfaseFlag() == BigDecimal.ZERO) {
            setStateFaseDisabled(true);
        } else {
            setStateFaseDisabled(false);
        }
        return getStateFaseDisabled();
    }

    public Boolean disableFase(ActionEvent event, Uzatfase faseClose) {
        Boolean disable = true;
        faseClose.setUzatfaseFechaOut(FechaHoraActual());
        faseClose.setUzatfaseFlag(BigDecimal.ZERO);
        disable = ProcuradoriaMethods.UpdateFase(faseClose);
        if (disable) {
            disable = false;
            this.EnableNewFase = false;
        }
        return disable;
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
        ///INSERTAR DATOS EN LA TABLA FF CUADO CRE AUNA FASE

        Boolean exito = ProcuradoriaMethods.InsertFase(this.NewFase);
        if (exito) {
            //RequestContext.getCurrentInstance().execute("PF('dlgNewFaseMSG').show();");
            this.init();
        }
    }

    public void genratedComentario(ActionEvent event) {
        this.NewComentario.getId().setUzatcasoId(SelectedCaso.getUzatcasoId());
        this.NewComentario.getId().setUzatfaseId(SelectedFase.getId().getUzatfaseId());
        this.NewComentario.setUzatcomtFecha(FechaHoraActual());
        this.NewComentario.setUzatfuncionarioId(this.getUserAttribute());

        Boolean exito = ProcuradoriaMethods.InsertComentario(this.NewComentario);
        if (exito) {
            //RequestContext.getCurrentInstance().execute("PF('dlgNewComentarioMSG').show();");
            this.initComentarios();
        }
    }

    public void genratedCita(ActionEvent event) {
        this.NewCita.getId().setUzatcasoId(SelectedCaso.getUzatcasoId());
        this.NewCita.getId().setUzatfaseId(SelectedFase.getId().getUzatfaseId());
        this.NewCita.setUzatcitaFlag(BigDecimal.ONE);
        this.NewCita.setUzatcitaFecha(getFechaCita().toString());
        this.NewCita.setUzatfuncionarioId(this.getUserAttribute());

        Boolean exito = ProcuradoriaMethods.InsertCita(this.NewCita);
        if (exito) {
            RequestContext.getCurrentInstance().execute("PF('dlgNewCitaMSG').show();");
            this.initCitas();
        }
    }

    public void genratedDocumento(ActionEvent event) {
        this.NewDocumento.getId().setUzatcasoId(SelectedCaso.getUzatcasoId());
        this.NewDocumento.getId().setUzatfaseId(SelectedFase.getId().getUzatfaseId());
        this.NewDocumento.setUzatdocsFecha(FechaHoraActual());
        this.NewDocumento.setUzatfuncionarioId(this.getUserAttribute());

        Boolean exito = DocumentsPdf.CovertPdfToByteArray(NewDocumento, DirecURLDoc, "");
        if (exito) {
            RequestContext.getCurrentInstance().execute("PF('dlgNewDocumentoMSG').show();");
            this.initDocumentos();
        }
    }

    public Boolean getEnableNewFase() {
        return EnableNewFase;
    }

    public void setEnableNewFase(Boolean EnableNewFase) {
        this.EnableNewFase = EnableNewFase;
    }

}
