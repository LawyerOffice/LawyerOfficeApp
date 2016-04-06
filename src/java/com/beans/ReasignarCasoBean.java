/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import procuradoria.map.Uzatcaso;
import procuradoria.crud.ProcuradoriaMethods;
import procuradoria.map.Uzatasign;
import procuradoria.map.UzatasignId;
import procuradoria.map.Uzatfunci;

/**
 *
 * @author FANNY
 */
@ManagedBean
@ViewScoped
public class ReasignarCasoBean {

    /**
     * Creates a new instance of ResumenProcuBean
     */
    private String NumCausa;
    private Uzatcaso selectedCaso;
    private Uzatasign asignold;
    private String cedulaAbo;
    private Uzatfunci nuevofunci;
    private Uzatasign nuevaasign;
    private String motivo;

    public ReasignarCasoBean() {
        this.selectedCaso = new Uzatcaso();
        this.asignold = new Uzatasign();
        this.nuevofunci = new Uzatfunci();
        this.nuevaasign = new Uzatasign();
    }

    public void cargarCaso(ActionEvent event) {
        this.selectedCaso = ProcuradoriaMethods.FindCasobyNumCausa(NumCausa);
        if (this.selectedCaso == null) {
            addMessage("No se ha encontrado ningún caso.");
            this.asignold = null;
        } else {
            this.asignold = ProcuradoriaMethods.GetActiveAbogadosByIdCaso(this.selectedCaso.getUzatcasoId());
        }
    }

    public void findAbobyCedula() {
        this.nuevofunci = ProcuradoriaMethods.FindFuncionarioByCedula(cedulaAbo);
        System.out.println("");
    }

    public void asignarcaso() {
        nuevaasign.setId(new UzatasignId(this.nuevofunci.getUzatfuncionarioId(), this.selectedCaso.getUzatcasoId()));
        nuevaasign.setUzatasignarFechaIn(getDate());
        nuevaasign.setUzatasignarMotivo(motivo);
        nuevaasign.setUzatasignarFlag(BigDecimal.ONE);
        nuevaasign.setUzatasignarId(getUserAttribute());

        if (ProcuradoriaMethods.insertAsign(nuevaasign)) {
            addMessage("Se ha reasignado el caso satisfactoriamente");
        } else {
            addMessage("Ha ocurrido un error");
        }
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

    public static String getDate() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    //oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
    public String getNumCausa() {
        return NumCausa;
    }

    public void setNumCausa(String NumCausa) {
        this.NumCausa = NumCausa;
    }

    public Uzatcaso getSelectedCaso() {
        return selectedCaso;
    }

    public void setSelectedCaso(Uzatcaso selectedCaso) {
        this.selectedCaso = selectedCaso;
    }

    public Uzatasign getAsignold() {
        return asignold;
    }

    public void setAsignold(Uzatasign asignold) {
        this.asignold = asignold;
    }

    public String getCedulaAbo() {
        return cedulaAbo;
    }

    public void setCedulaAbo(String cedulaAbo) {
        this.cedulaAbo = cedulaAbo;
    }

    public Uzatfunci getNuevofunci() {
        return nuevofunci;
    }

    public void setNuevofunci(Uzatfunci nuevofunci) {
        this.nuevofunci = nuevofunci;
    }

    public Uzatasign getNuevaasign() {
        return nuevaasign;
    }

    public void setNuevaasign(Uzatasign nuevaasign) {
        this.nuevaasign = nuevaasign;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

}
