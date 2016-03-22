/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.beans;

import com.util.LazyCasoDataModel;
import java.io.Serializable;
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
import org.primefaces.model.LazyDataModel;
import procuradoria.map.Uzatcaso;
import procuradoria.crud.ProcuradoriaMethods;
import procuradoria.map.Uzatactor;
import procuradoria.map.UzatinvCa;
import procuradoria.map.UzatinvCaId;
import procuradoria.map.UzatinvFf;
import procuradoria.map.UzatinvFfId;

/**
 *
 * @author FANNY
 */
@ManagedBean
@ViewScoped
public class ResumenAboBean implements Serializable{

    /**
     * Creates a new instance of ResumenProcuBean
     */
    private String idCaso;
    private String cedulaActor;
    private String tipoActor;
    private String cajaTextoSeleccionarActor="Por favor, Seleccione un actor";
    private String botonAgregarActor = "Agregar Actor";         
    private LazyDataModel<Uzatcaso> lazyModelCasosAsignados;        
    
    private Uzatcaso selectedCaso;
    private Uzatactor selectedActor;
    
    public ResumenAboBean() {
        
        lazyModelCasosAsignados = new LazyCasoDataModel(this.getUserAttribute(), new BigDecimal(2));
        selectedCaso = new Uzatcaso();
        this.selectedActor = new Uzatactor();
        this.idCaso = "vacio";
        this.cedulaActor= "Ingrese número de cédula";
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
    
    public void findCasobyid(String id)
    {
        this.idCaso = id ;
        this.selectedCaso = ProcuradoriaMethods.findCasobyId(new BigDecimal(idCaso));
    }
    
    public void findActorbycedula()
    {
        if(!cedulaActor.equals("Ingrese número de cédula")){
            this.selectedActor = ProcuradoriaMethods.findActorbyCedula(this.cedulaActor);
            this.botonAgregarActor = "Ver Datos Actor";
            this.cajaTextoSeleccionarActor = this.selectedActor.getUzatactorNombres() + " " + this.selectedActor.getUzatactorApellidos();
        }
    }
    
    public void botonActualizarActor()
    {
        updateActor();      
        
    }
    
    public void botonActualizarCaso()
    {
        updateCaso();
        asignarActoraCaso();
    }
    
    public void buttonAction(ActionEvent actionEvent) {
        addMessage("Welcome to Primefaces!!");
    }
     
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    

// <editor-fold defaultstate="collapsed" desc=" Getters and Setters ">
    public Uzatcaso getSelectedCaso() {
        return selectedCaso;
    }
    
    public void setSelectedCaso(Uzatcaso selectedCaso) {
        this.selectedCaso = selectedCaso;
    }
    
    public LazyDataModel<Uzatcaso> getLazyModelCasosAsignados() {
        return lazyModelCasosAsignados;
    }
    
    public void setLazyModelCasosAsignados(LazyDataModel<Uzatcaso> lazyModelCasosAsignados) {
        this.lazyModelCasosAsignados = lazyModelCasosAsignados;
    }
    
    public String getIdCaso() {
        return idCaso;
    }
    
    public void setIdCaso(String idCaso) {
        this.idCaso = idCaso;
    }
    
    public String getTipoActor() {
        return tipoActor;
    }
    
    public void setTipoActor(String tipoActor) {
        this.tipoActor = tipoActor;
    }
    
    public Uzatactor getSelectedActor() {
        return selectedActor;
    }
    
    public void setSelectedActor(Uzatactor selectedActor) {
        this.selectedActor = selectedActor;
    }
    
    public String getCedulaActor() {
        return cedulaActor;
    }
    
    public void setCedulaActor(String cedulaActor) {
        this.cedulaActor = cedulaActor;
    }

    public String getBotonAgregarActor() {
        return botonAgregarActor;
    }

    public void setBotonAgregarActor(String botonAgregarActor) {
        this.botonAgregarActor = botonAgregarActor;
    }
    
    public String getCajaTextoSeleccionarActor() {
        return cajaTextoSeleccionarActor;
    }

    public void setCajaTextoSeleccionarActor(String cajaTextoSeleccionarActor) {
        this.cajaTextoSeleccionarActor = cajaTextoSeleccionarActor;
    }
// </editor-fold>

    private void updateActor() {
        if(ProcuradoriaMethods.UpdateActor(selectedActor))
        {
            addMessage("Se han actualizado los Datos de Actor");
        }
    }

    private void updateCaso() {
        if(ProcuradoriaMethods.UpdateCaso(selectedCaso))
        {
            addMessage("Se han actualizado los Datos del Caso");
        }else
        {
            addMessage("Ha ocurrido un error");
        }
    }

    private void asignarActoraCaso() {
         UzatinvCa involucrado = new UzatinvCa();
         UzatinvCaId idinvolucrado = new UzatinvCaId(this.selectedActor.getUzatactorId(), this.selectedCaso.getUzatcasoId());
         involucrado.setId(idinvolucrado);
         involucrado.setUzatinvTipo(tipoActor);
         involucrado.setUzatinvolCa(getDate());
         involucrado.setUzatcaso(selectedCaso);
         involucrado.setUzatactor(selectedActor);
         System.out.println("");
         if(ProcuradoriaMethods.InsertInvolCa(involucrado))
         {
            addMessage("Se ha asignado corretamente el caso");
         }else
         {
             addMessage("Ha ocurrido un error");
         }
    }

    public static String getDate() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    
    
    
}

