/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.beans;

import banner.crud.BannerMethos;
import banner.map.PersonaBanner;
import com.util.LazyCasoDataModel;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.model.LazyDataModel;
import procuradoria.crud.ProcuradoriaMethods;
import procuradoria.map.Uzatasign;
import procuradoria.map.UzatasignId;
import procuradoria.map.Uzatcaso;
import procuradoria.map.Uzatfunci;

/**
 *
 * @author FANNY
 */
@ManagedBean
@ViewScoped
public class ResumenProcuBean{

    /**
     * Creates a new instance of ResumenProcuBean
     */

    private LazyDataModel<Uzatcaso> lazyModelCasosInactivos;
    private LazyDataModel<Uzatcaso> lazyModelCasossinAbogado;
    private Uzatcaso casoSeleccionado;
    
    private String idocedula;
    private String cedulaActor;
    
    private Uzatcaso selectedCaso;
    private Uzatfunci selectedFunci;
    
    public ResumenProcuBean() {
        lazyModelCasosInactivos = new LazyCasoDataModel(new BigDecimal(2));
        lazyModelCasossinAbogado = new LazyCasoDataModel(new BigDecimal(3));
        idocedula = "";
        cedulaActor = "";
        this.casoSeleccionado = new Uzatcaso();
        this.selectedFunci = new Uzatfunci();
        
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
     
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public static String getDate() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }
    
    public void selectionaction()
    {
        this.selectedFunci = new Uzatfunci();
        addMessage(selectedCaso.getUzatcasoNumcausa());
    }
    
    public void findFuncionariobycedula()
    {
        ValidateFuncionario(cedulaActor);   
    }
    
    private Boolean ValidateFuncionario(String claveFuncionario) {
        
        Boolean exito = false;
        PersonaBanner find = null;
        String mdatoCli = claveFuncionario.trim();
        claveFuncionario = mdatoCli.toUpperCase();
        if (!findFuncionarioProcuaradoria(claveFuncionario, idocedula)) {
       
                if(idocedula.equals("0")){
                    find = BannerMethos.FindPersonBannerByCedula(claveFuncionario);
                }else if(idocedula.equals("1"))
                {
                    find = BannerMethos.FindPersonBannerByIdBanner(claveFuncionario);
                }
            
            if (find != null) {
                
                SendDataFuncionario(find);
                addMessage("Se ha ingresado Funcionario en Base de Datos");
                exito = true;
            }
            
        }else
        {
            
            addMessage("Se ha encontrado Funcionario en Base de Datos");
        }
        return exito;
    }
    
     public Boolean findFuncionarioProcuaradoria(String claveFuncionario, String idocedula) {
        Boolean exito = false;
        if(idocedula.equals("0"))
        {
            this.selectedFunci = ProcuradoriaMethods.FindFuncionarioByCedula(claveFuncionario);
        }
        else
        {
            this.selectedFunci = ProcuradoriaMethods.FindFuncionarioByIDBanner(claveFuncionario);
        }

        if (this.selectedFunci != null) {           
            exito = true;
        } else {
            this.selectedFunci = new Uzatfunci();
            addMessage("No se ha encontrado funcionario");
        }
        return exito;
    }

    public void SendDataFuncionario(PersonaBanner Funcionario) {
        this.selectedFunci.setUzatfuncionarioApellidos(Funcionario.getApellidos());
        this.selectedFunci.setUzatfuncionarioCedula(Funcionario.getCedula());
        this.selectedFunci.setUzatfuncionarioEmail(Funcionario.getEmail());
        this.selectedFunci.setUzatfuncionarioNombres(Funcionario.getNombres());
        this.selectedFunci.setUzatfuncionarioIdbanner(Funcionario.getIdBanner());
        this.selectedFunci.setUzatfuncionarioId(new BigDecimal(1111));
        
        Boolean exito= ProcuradoriaMethods.InserFuncionario(this.selectedFunci);
        
        
        if (exito) {
            this.selectedFunci = ProcuradoriaMethods.FindFuncionarioByCedula(this.selectedFunci.getUzatfuncionarioCedula());
        }
        else
        {
            addMessage("A ocurrido un error, no se han Grabado los Datos en el Registro");
        }
        
    }
    
    public void asignarFuncionario()
    {
        ProcuradoriaMethods.UpdateFunci(this.selectedFunci);
        
        this.selectedCaso.setUzatcasoFlag(new BigDecimal("2"));
        ProcuradoriaMethods.UpdateCaso(this.selectedCaso);
        
        Uzatasign newAsignacion = new Uzatasign();
        newAsignacion.setId(new UzatasignId(selectedFunci.getUzatfuncionarioId(),selectedCaso.getUzatcasoId()));
        newAsignacion.setUzatasignarFechaIn(getDate());
        newAsignacion.setUzatasignarFlag(BigDecimal.ONE);
        newAsignacion.setUzatasignarMotivo("Asignación por parte de Procurador");
        newAsignacion.setUzatasignarId(getUserAttribute());
        ProcuradoriaMethods.InsertNuevaAsignacion(newAsignacion);
        
        addMessage("Se ha asignado el caso correctamente");
    }
    
    // <editor-fold defaultstate="collapsed" desc=" GET SET ">
    public Uzatcaso getSelectedCaso() {
        return selectedCaso;
    }
    
    public void setSelectedCaso(Uzatcaso selectedCaso) {
        this.selectedCaso = selectedCaso;
    }
    
    public LazyDataModel<Uzatcaso> getLazyModelCasosInactivos() {
        return lazyModelCasosInactivos;
    }
    
    public void setLazyModelCasosInactivos(LazyDataModel<Uzatcaso> lazyModelCasosInactivos) {
        this.lazyModelCasosInactivos = lazyModelCasosInactivos;
    }
    
    public LazyDataModel<Uzatcaso> getLazyModelCasossinAbogado() {
        return lazyModelCasossinAbogado;
    }
    
    public void setLazyModelCasossinAbogado(LazyDataModel<Uzatcaso> lazyModelCasossinAbogado) {
        this.lazyModelCasossinAbogado = lazyModelCasossinAbogado;
    }
    
    public Uzatcaso getCasoSeleccionado() {
        return casoSeleccionado;
    }
    
    public void setCasoSeleccionado(Uzatcaso casoSeleccionado) {
        this.casoSeleccionado = casoSeleccionado;
    }

    public String getIdocedula() {
        return idocedula;
    }

    public void setIdocedula(String idocedula) {
        this.idocedula = idocedula;
    }

    public String getCedulaActor() {
        return cedulaActor;
    }

    public void setCedulaActor(String cedulaActor) {
        this.cedulaActor = cedulaActor;
    }
    
    public Uzatfunci getSelectedFunci() {
        return selectedFunci;
    }

    public void setSelectedFunci(Uzatfunci selectedFunci) {
        this.selectedFunci = selectedFunci;
    }

// </editor-fold>
    
    

    
    
    
}

