
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.event.FileUploadEvent;
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
        this.CodCaso = this.getCasoIdAttribute();
        this.FechaCita = new Date();
        this.DirecURLDoc = "";
        this.EnableNewFase = true;
        this.setSelectedCaso(ProcuradoriaMethods.CasoByIdCaso(this.CodCaso));
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
            this.NewDocumento.getId().setUzatcasoId(SelectedCaso.getUzatcasoId());
            this.NewDocumento.getId().setUzatfaseId(SelectedFase.getId().getUzatfaseId());
            this.NewDocumento.setUzatdocsFecha(FechaHoraActual());
            this.NewDocumento.setUzatfuncionarioId(this.getUserAttribute());
            try {
                //            Boolean exito = DocumentsPdf.SaveDocument(this.NewDocumento, FiletoByteArray(this.file));
                // write the inputStream to a FileOutputStream
                InputStream in = this.file.getInputstream();
                OutputStream out = new FileOutputStream(new File("D:\\tmp\\"+this.file.getFileName()));

                int read = 0;
                byte[] bytes = new byte[1024];

                while ((read = in.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }

                in.close();
                out.flush();
                out.close();
                Boolean exito = DocumentsPdf.SaveDocument(this.NewDocumento, bytes);
                if (exito) {
                    generateMessage(FacesMessage.SEVERITY_INFO, "Nuevo Documento", "Guardado exitosamente.");
                    this.initDocumentos();
                }
            } catch (IOException ex) {
                //Logger.getLogger(FasesCasoBean.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void copyFile(InputStream in) {
        try {

            // write the inputStream to a FileOutputStream
            OutputStream out = new FileOutputStream("temp");

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            in.close();
            out.flush();
            out.close();
            DocumentsPdf.SaveDocument(this.NewDocumento, bytes);

            //System.out.println("New file created!");
        } catch (IOException e) {
            //System.out.println(e.getMessage());
        }
    }

    public byte[] FiletoByteArray(UploadedFile document) {
        byte[] file = new byte[1024];
        try {
            file = document.getContents();
            long size = document.getSize();
            InputStream stream = document.getInputstream();
            byte[] buffer = new byte[(int) size];
            stream.read(buffer, 0, (int) size);
            stream.close();
            file = buffer;
        } catch (IOException ex) {

        }

        return file;
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

    public String getDirecURLDoc() {
        return DirecURLDoc;
    }

    public void setDirecURLDoc(String DirecURLDoc) {
        this.DirecURLDoc = DirecURLDoc;
    }

}
