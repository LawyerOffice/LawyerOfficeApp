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
import java.util.List;
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

    private String valueFindCasos;
    private List<Uzatasign> casosAsigandos;
    private List<Uzatasign> casosSeleccionados;

    public ReasignarCasoBean() {
        this.selectedCaso = null;
        this.asignold = null;
        this.nuevofunci = null;
        this.nuevaasign = new Uzatasign();
    }

    public void ReasignarValidacion(ActionEvent event) {
        if (this.casosSeleccionados.isEmpty()) {
            addMessage("Seleccione los casos que desea reasignar.");
        } else {
            RequestContext.getCurrentInstance().execute("PF('multiCarDialog').show()");
        }
    }

    public void cargarCaso() {
        if (NumCausa.equals("")) {
            addMessage("Ingrese el número de causa que desea buscar.");
            this.selectedCaso = null;
            this.asignold = null;
        } else {
            this.selectedCaso = ProcuradoriaMethods.FindCasobyNumCausa(NumCausa);
            if (this.selectedCaso == null) {
                addMessage("No se ha encontrado ningún caso.");
                this.asignold = null;
            } else {
                this.asignold = ProcuradoriaMethods.GetActiveAbogadosByIdCaso(this.selectedCaso.getUzatcasoId());
            }
        }
    }

    public void findAbobyCedula() {
        if(cedulaAbo.equals("")){
            addMessage("Ingrese el número de cédula del abogado que desea buscar.");
            this.nuevofunci=null;
        }else{
            this.nuevofunci = ProcuradoriaMethods.FindFuncionarioByCedula(cedulaAbo);
            if(this.nuevofunci==null)
                addMessage("No se ha encontrado ningún abogado.");
        }
        
    }

    public void loadCasosAsignados() {
        if (this.valueFindCasos.equals("")) {
            this.casosAsigandos = null;
            addMessage("Ingrese la cédula del abogado que desea buscar sus casos.");
        } else {
            this.casosAsigandos = ProcuradoriaMethods.FindCasosReasignar(valueFindCasos);
            if (this.casosAsigandos.isEmpty()) {
                this.casosAsigandos = null;
                addMessage("No se han encontrado casos relacionados con dicha cédula.");
            }
        }
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

    public void asignarcasoMasivo() {
        if (this.nuevofunci != null) {
            nuevaasign.setUzatasignarMotivo(motivo);
            nuevaasign.setUzatasignarFlag(BigDecimal.ONE);
            nuevaasign.setUzatasignarId(getUserAttribute());

            for (Uzatasign obj : this.casosSeleccionados) {
                nuevaasign.setId(new UzatasignId(this.nuevofunci.getUzatfuncionarioId(), obj.getUzatcaso().getUzatcasoId()));
                nuevaasign.setUzatasignarFechaIn(getDate());
                ProcuradoriaMethods.insertAsign(nuevaasign);
            }
            addMessage("Se han reasignado los casos satisfactoriamente.");
        }else
        {
            this.nuevofunci=null;
            addMessage("Seleccione algún abogado para realizar la acción.");
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

    public String getValueFindCasos() {
        return valueFindCasos;
    }

    public void setValueFindCasos(String valueFindCasos) {
        this.valueFindCasos = valueFindCasos;
    }

    public List<Uzatasign> getCasosAsigandos() {
        return casosAsigandos;
    }

    public void setCasosAsigandos(List<Uzatasign> casosAsigandos) {
        this.casosAsigandos = casosAsigandos;
    }

    public List<Uzatasign> getCasosSeleccionados() {
        return casosSeleccionados;
    }

    public void setCasosSeleccionados(List<Uzatasign> casosSeleccionados) {
        this.casosSeleccionados = casosSeleccionados;
    }

}
