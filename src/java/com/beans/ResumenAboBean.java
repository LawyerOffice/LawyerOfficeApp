/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.beans;

import banner.crud.BannerMethos;
import banner.map.PersonaBanner;
import com.util.LazyCasoDataModel;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import org.primefaces.model.LazyDataModel;
import procuradoria.map.Uzatcaso;
import procuradoria.crud.ProcuradoriaMethods;
import procuradoria.map.Uzatactor;
import procuradoria.map.UzatinvCa;
import procuradoria.map.UzatinvCaId;
import procuradoria.map.UzatinvFf;
import procuradoria.map.UzatinvFfId;
import procuradoria.map.Uzatjudi;
import procuradoria.map.Uzatmateri;

/**
 *
 * @author FANNY
 */
@ManagedBean
@ViewScoped
public class ResumenAboBean{

    /**
     * Creates a new instance of ResumenProcuBean
     */
    private String idCaso;
    private String cedulaActor;
    private String tipoActor;
    private String cajaTextoSeleccionarActor="Por favor, Seleccione un actor";
    private String botonAgregarActor = "Agregar Actor";         
    private LazyDataModel<Uzatcaso> lazyModelCasosAsignados;   
    private String idocedula;
    
    private Uzatcaso selectedCaso;
    private Uzatactor selectedActor;
 
    private ArrayList<SelectItem> ItemsMaterias;
    private ArrayList<SelectItem> ItemsJudicaturas;

    private String idMateria = "100";
    private String idJudicatura = "100";    
    
    public ResumenAboBean() {
        
        lazyModelCasosAsignados = new LazyCasoDataModel(this.getUserAttribute(), new BigDecimal(2));
        selectedCaso = new Uzatcaso();
        this.selectedActor = new Uzatactor();
        this.idCaso = "vacio";
        this.idocedula = "vacio";
        this.cedulaActor= "";
        this.ItemsJudicaturas = new ArrayList<SelectItem>();
        this.ItemsMaterias = new ArrayList<SelectItem>(); 

        this.loadlistMaterias();
        this.ItemsJudicaturas.clear();
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
            ValidateFuncionario(cedulaActor);
            System.out.println(this.selectedActor.getUzatactorNombres());
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
    
    private void updateActor() {
        if(ProcuradoriaMethods.UpdateActor(selectedActor))
        {
            addMessage("Se han actualizado los Datos de Actor");
        }
    }

    private void updateCaso() {
        this.selectedCaso.setUzatcasoFlag(new BigDecimal(1));
        
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

    
    //Busqueda de actor en banner
    
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
                addMessage("Se ha ingresado Actor en Base de Datos");
                exito = true;
            }
            
        }else
        {
            addMessage("Se ha encontrado Actor en Base de Datos");
        }
        return exito;
    }

    public Boolean findFuncionarioProcuaradoria(String claveFuncionario, String idocedula) {
        Boolean exito = false;
        if(idocedula.equals("0"))
        {
            this.selectedActor = ProcuradoriaMethods.findActorbyCedula(claveFuncionario);
        }
        else
        {
            this.selectedActor = ProcuradoriaMethods.findActorbyIDBanner(claveFuncionario);
        }

        if (this.selectedActor != null) {           
            exito = true;
        } else {
            this.selectedActor = new Uzatactor();
        }
        return exito;
    }

    public void SendDataFuncionario(PersonaBanner Funcionario) {
        this.selectedActor.setUzatactorApellidos(Funcionario.getApellidos());
        this.selectedActor.setUzatactorCedula(Funcionario.getCedula());
        this.selectedActor.setUzatactorEmail(Funcionario.getEmail());
        this.selectedActor.setUzatactorNombres(Funcionario.getNombres());
        this.selectedActor.setUzatactorIdbanner(Funcionario.getIdBanner());
        this.selectedActor.setUzatactorId(new BigDecimal(1111));
        
        Boolean exito = ProcuradoriaMethods.insertActor(this.selectedActor);
        if (exito) {
            this.selectedActor = ProcuradoriaMethods.findActorbyCedula(this.selectedActor.getUzatactorCedula());
        }
        else
        {
            addMessage("A ocurrido un error, no se han Grabado los Datos en el Registro");
        }
        
    }
    
    public void loadlistMaterias() {     
        ArrayList<Uzatmateri> selectItemsMat = ProcuradoriaMethods.ListMaterias();
        this.ItemsMaterias.clear();
        SelectItem  si;
        for (int i = 0; i < selectItemsMat.size(); i++) {
            si = new SelectItem(selectItemsMat.get(i).getUzatmateriaId(),selectItemsMat.get(i).getUzatmateriaDescripcion());
            this.ItemsMaterias.add(si);
        }
        this.ItemsMaterias.remove(0);
    }

    public void loadlistJudi() {
        BigDecimal idmateri = new BigDecimal(idMateria);
        ArrayList<Uzatjudi> selectItemsJud = ProcuradoriaMethods.findjudibyMateriId(idmateri);
        
        if(!(selectItemsJud == null))
        {
            this.ItemsJudicaturas.clear();
            SelectItem  si;
            for (int i = 0; i < selectItemsJud.size(); i++) {
                si = new SelectItem(selectItemsJud.get(i).getId().getUzatjudiId(),selectItemsJud.get(i).getUzatjudiDescripcion());
                this.ItemsJudicaturas.add(si);
             
            }
        }
        else
        {
            this.ItemsJudicaturas.clear();
            SelectItem  si;
            si = new SelectItem("100","No existe Judicatura");
            this.ItemsJudicaturas.add(si); 
        }
       
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

    public String getIdocedula() {
        return idocedula;
    }

    public void setIdocedula(String idocedula) {
        this.idocedula = idocedula;
    }
    
    public ArrayList<SelectItem> getItemsMaterias() {
        return ItemsMaterias;
    }

    public void setItemsMaterias(ArrayList<SelectItem> ItemsMaterias) {
        this.ItemsMaterias = ItemsMaterias;
    }

    public ArrayList<SelectItem> getItemsJudicaturas() {
        return ItemsJudicaturas;
    }

    public void setItemsJudicaturas(ArrayList<SelectItem> ItemsJudicaturas) {
        this.ItemsJudicaturas = ItemsJudicaturas;
    }

    public String getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(String idMateria) {
        this.idMateria = idMateria;
    }

    public String getIdJudicatura() {
        return idJudicatura;
    }

    public void setIdJudicatura(String idJudicatura) {
        this.idJudicatura = idJudicatura;
    }
    
// </editor-fold>

    

}

