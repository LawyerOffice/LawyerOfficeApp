
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
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
    private Uzatdocs SelectedDocument;
    private BigDecimal CodCaso;

    private Uzatfase SelectedFase;
    private Boolean StateFaseDisabled;

    private Uzatfase NewFase;
    private Uzatcomt NewComentario;

    private Uzatdocs NewDocumento;

    private UploadedFile file;
    private StreamedContent pdfFile;
    private String downloadFileName;

    private Uzatcita NewCita;
    private Date FechaCita;

    private Boolean EnableNewFase;

    private String valueFindCaso;

    public FasesCasoBean() {

        HttpServletRequest origRequest
                = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String urlRequest = origRequest.getRequestURI().toString();
        urlRequest = urlRequest.replace("/LawyerOfficeApp/faces/views/", "");

        if (!this.getCasoIdAttribute().equals(new BigDecimal(BigInteger.ZERO))) {
            if (!urlRequest.equals("ver_caso_abo.xhtml")) {
                this.CodCaso = this.getCasoIdAttribute();
                this.setSelectedCaso(ProcuradoriaMethods.CasoByIdCaso(this.CodCaso));
            } else {
                this.CodCaso = BigDecimal.ONE;
                Uzatcaso SelectedCasoAux = new Uzatcaso(this.CodCaso, null);
                this.setSelectedCaso(SelectedCasoAux);
            }
        } else {
            this.CodCaso = BigDecimal.ONE;
            Uzatcaso SelectedCasoAux = new Uzatcaso(this.CodCaso, null);
            this.setSelectedCaso(SelectedCasoAux);
        }

        this.FechaCita = new Date();
        this.EnableNewFase = true;
        this.downloadFileName = "";
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
            if (IdBanner != null) {
                UserAttribute = IdBanner.toString();
                id = new BigDecimal(UserAttribute);
            }
        }
        return id;
    }

    public void closeFaseTrigger(ActionEvent event) {
        this.EnableNewFase = false;
    }

    private void init() {
        this.ListFases = ProcuradoriaMethods.listFasesByIdCaso(SelectedCaso.getUzatcasoId());
    }

    private void initComentarios() {
        this.NewComentario = new Uzatcomt();
        this.ListComtFasesById = ProcuradoriaMethods.GetFasesComentByIdCasoAndIdFase(SelectedCaso.getUzatcasoId(), SelectedFase.getId().getUzatfaseId());
    }

    private void initCitas() {
        this.FechaCita = new Date();
        this.NewCita = new Uzatcita();
        this.ListCitaFasesById = ProcuradoriaMethods.FindCitasbyCaso_Fase(SelectedCaso.getUzatcasoId(), SelectedFase.getId().getUzatfaseId());
    }

    private void initDocumentos() {
        this.NewDocumento = new Uzatdocs();
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
            this.initDocumentos();
        } else if (event.getTab().getId().equals("TabCitas")) {
            this.initCitas();
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

    public void buscarCasoByNumCausa(ActionEvent actionEvent) {
        if (!valueFindCaso.equals("")) {
            this.setSelectedCaso(ProcuradoriaMethods.CasoByNumCausaFlagVisible(this.valueFindCaso, BigDecimal.valueOf(1)));
            this.init();
        } else {
            this.setSelectedCaso(null);
            this.setListFases(null);
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

    public String FechaHora(Date fecha) {
        SimpleDateFormat s1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return s1.format(fecha);
    }

    public void genratedFase(ActionEvent event) {
        this.NewFase.getId().setUzatcasoId(SelectedCaso.getUzatcasoId());
        this.NewFase.setUzatfaseFechaIn(FechaHoraActual());
        this.NewFase.setUzatfaseFlag(BigDecimal.ONE);
        ///INSERTAR DATOS EN LA TABLA FF CUADO CRE AUNA FASE

        Boolean exito = ProcuradoriaMethods.InsertFase(this.NewFase);
        if (exito) {
            UzatinvFfId invffId = new UzatinvFfId();
            invffId.setUzatfuncionarioId(this.getCasoIdAttribute());
            invffId.setUzatcasoId(this.CodCaso);
            SelectedFase.getId().getUzatfaseId();
            invffId.setUzatfaseId(ProcuradoriaMethods.GetUltimaFaseIdByCaso(CodCaso, new BigDecimal(BigInteger.ONE)));
            UzatinvFf invff = new UzatinvFf();
            invff.setId(invffId);
            invff.getUzatfunci().setUzatfuncionarioId(this.getUserAttribute());
            invff.getUzatfase().setId(new UzatfaseId(CodCaso, invffId.getUzatfaseId()));
            invff.setUzatinvolFfFIn(FechaHoraActual());
            Boolean sucsse = ProcuradoriaMethods.insertInvFf(invff);
            if (sucsse) {
                generateMessage(FacesMessage.SEVERITY_INFO, "Nueva fase", "Creada exitosamente.");
                this.init();
            }
        }
    }

    public void generateMessage(FacesMessage.Severity Tipo, String Header, String Mensaje) {
        FacesMessage message = new FacesMessage(Tipo, Header, Mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void genratedComentario(ActionEvent event) {
        this.NewComentario.getId().setUzatcasoId(SelectedCaso.getUzatcasoId());
        this.NewComentario.getId().setUzatfaseId(SelectedFase.getId().getUzatfaseId());
        this.NewComentario.setUzatcomtFecha(FechaHoraActual());
        this.NewComentario.setUzatfuncionarioId(this.getUserAttribute());

        Boolean exito = ProcuradoriaMethods.InsertComentario(this.NewComentario);
        if (exito) {
            generateMessage(FacesMessage.SEVERITY_INFO, "Nuevo comentario", "Registrado exitosamente.");
            this.initComentarios();
        }
    }

    public void genratedCita(ActionEvent event) {
        this.NewCita.getId().setUzatcasoId(SelectedCaso.getUzatcasoId());
        this.NewCita.getId().setUzatfaseId(SelectedFase.getId().getUzatfaseId());
        this.NewCita.setUzatcitaFecha(FechaHora(this.getFechaCita()));
        this.NewCita.setUzatcitaFlag(BigDecimal.ONE);
        this.NewCita.setUzatfuncionarioId(this.getUserAttribute());

        Boolean exito = ProcuradoriaMethods.InsertCita(this.NewCita);
        if (exito) {
            generateMessage(FacesMessage.SEVERITY_INFO, "Nueva cita", "Registrada exitosamente.");
            this.initCitas();
        }
    }

    public void genratedDocumento(ActionEvent event) {
        if (this.file != null) {
            if ((file.getFileName().endsWith(".pdf") || file.getFileName().endsWith(".PDF"))) {
                try {
                    this.NewDocumento.getId().setUzatcasoId(SelectedCaso.getUzatcasoId());
                    this.NewDocumento.getId().setUzatfaseId(SelectedFase.getId().getUzatfaseId());
                    this.NewDocumento.setUzatdocsFecha(FechaHoraActual());
                    this.NewDocumento.setUzatdocsPdf(this.file.getInputstream());
                    this.NewDocumento.setUzatfuncionarioId(this.getUserAttribute());
                    this.NewDocumento.setUzatdocsPdfSize(this.file.getSize());
                    String filename = this.file.getFileName();
                    ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
                            .getExternalContext().getContext();
                    String serverPath = ctx.getRealPath("/");
                    String filepath = "WEB-INF/docs/";
                    Boolean exito = DocumentsPdf.CovertPdfToByteArray(this.NewDocumento, serverPath + filepath + filename);

                } catch (IOException ex) {
                    // Logger.getLogger(FasesCasoBean.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                generateMessage(FacesMessage.SEVERITY_WARN, "El archivo escogido es muy grande o no esta en el formato, recuerda subir archivos .png", "");

            }
        } else {
            generateMessage(FacesMessage.SEVERITY_ERROR, "No has escogido un logo ", "");
        }
    }

    public void GeneratePDF(ActionEvent event, Uzatdocs SelectedDocumet) {
        this.SelectedDocument = SelectedDocumet;
        if (this.getSelectedDocument() != null) {
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
                    .getExternalContext().getContext();
            String serverPath = ctx.getRealPath("/");
            String filepath = "WEB-INF/docs/";
            Boolean exito = DocumentsPdf.CreateFilePDF(this.SelectedDocument, serverPath + filepath);
            if (exito) {
                this.downloadFileName = SelectedDocument.getId().getUzatdocsId()+".pdf";
                downloadDocsPDF(this.downloadFileName);
                //DocumentsPdf.RemoveFilePDF(this.downloadFileName);
            }
        }
    }

    public void downloadDocsPDF(String fileName) {
        if (this.getSelectedDocument() != null) {
            InputStream CmpPDF = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream("WEB-INF/docs/" + fileName);
            this.setPdfFile(new DefaultStreamedContent(CmpPDF, "application/pdf", fileName));

        }
    }

    public Boolean getEnableNewFase() {
        return EnableNewFase;
    }

    public void setEnableNewFase(Boolean EnableNewFase) {
        this.EnableNewFase = EnableNewFase;
    }

    public Date getFechaCita() {
        return FechaCita;
    }

    public void setFechaCita(Date FechaCita) {
        this.FechaCita = FechaCita;
    }

    public String getValueFindCaso() {
        return valueFindCaso;
    }

    public void setValueFindCaso(String valueFindCaso) {
        this.valueFindCaso = valueFindCaso;
    }

    public StreamedContent getPdfFile() {
        return pdfFile;
    }

    public void setPdfFile(StreamedContent pdfFile) {
        this.pdfFile = pdfFile;
    }

    public Uzatdocs getSelectedDocument() {
        return SelectedDocument;
    }

    public void setSelectedDocument(Uzatdocs SelectedDocument) {
        this.SelectedDocument = SelectedDocument;
    }

    public String getDownloadFileName() {
        return downloadFileName;
    }

    public void setDownloadFileName(String downloadFileName) {
        this.downloadFileName = downloadFileName;
    }

}
