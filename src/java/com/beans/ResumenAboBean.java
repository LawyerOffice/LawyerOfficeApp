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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import procuradoria.map.Uzatcaso;
import procuradoria.crud.ProcuradoriaMethods;
import procuradoria.map.Uzatactor;
import procuradoria.map.Uzatasign;
import procuradoria.map.UzatinvCa;
import procuradoria.map.UzatinvCaId;
import procuradoria.map.Uzatjudi;
import procuradoria.map.UzatjudiId;
import procuradoria.map.Uzatmateri;

/**
 *
 * @author FANNY
 */
@ManagedBean
@ViewScoped
public class ResumenAboBean {

    /**
     * Creates a new instance of ResumenProcuBean
     */
    private String idCaso;
    private String cedulaActor;
    private String tipoActor;
    private String cajaTextoSeleccionarActor = "Por favor, Seleccione un actor";
    private String botonAgregarActor = "Agregar Actor";
    private LazyDataModel<Uzatcaso> lazyModelCasosAsignados;
    private String idocedula;
    private String numeroocedula;
    private String valorbusqueda;

    private Uzatcaso selectedCaso;
    private Uzatactor selectedActor;

    private ArrayList<SelectItem> ItemsMaterias;
    private ArrayList<SelectItem> ItemsJudicaturas;

    private BigDecimal idMateria;
    private BigDecimal idJudicatura;
    private String textoBotonVincular;
    private String textohabilitarvinculación;
    private String valueFindCaso3 ="";
    private String valueFindCaso4 ="";
    private Uzatcaso findCaso;
    private String nombreActorAnterior;
    private String cedulaActorAnterior;
    
    private Uzatjudi vincuJudi;
    private Uzatmateri vincuMateria;

    private List<Uzatasign> casosAsigandos;
    
    private boolean tieneActor = false;
         
    public ResumenAboBean() {
        lazyModelCasosAsignados = new LazyCasoDataModel(this.getUserAttribute(), new BigDecimal(2));
        selectedCaso = new Uzatcaso();
        findCaso = new Uzatcaso();
        idMateria = new BigDecimal("100");
        idJudicatura = new BigDecimal("100");
        
        
        this.casosAsigandos = new ArrayList<Uzatasign>();
        this.selectedActor = new Uzatactor();
        this.idCaso = "vacio";
        this.idocedula = "vacio";
        this.cedulaActor = "";
        this.ItemsJudicaturas = new ArrayList<SelectItem>();
        this.ItemsMaterias = new ArrayList<SelectItem>();
        this.textoBotonVincular = "Buscar";
        this.ItemsJudicaturas.clear();
        this.textohabilitarvinculación = "";
        this.loadlistMaterias();
        
        vincuMateria = new Uzatmateri();
        vincuJudi = new Uzatjudi();
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

    public void findCasobyid(String id) {
        this.idCaso = id;
        this.selectedCaso = ProcuradoriaMethods.findCasobyId(new BigDecimal(idCaso));
        
        limpiarDetalle();
    }
    
    public void limpiarDetalle()
    {
        this.cedulaActor = "";
        this.tipoActor = "";
        this.cajaTextoSeleccionarActor = "Por favor, Seleccione un actor";
        this.botonAgregarActor = "Agregar Actor";
        this.selectedActor = new Uzatactor();
        this.textoBotonVincular = "Buscar";
        this.textohabilitarvinculación = "";
        this.tieneActor = false;
        this.textohabilitarvinculación = "";
    }

    public void findActorbycedula() {
        this.tieneActor = false;
        if(idocedula.equals(""))
        {
            addMessage("Seleccione ID o Cedula");
            return;
        }
        if (!cedulaActor.equals("Ingrese número de cédula")) {
            if(ValidateFuncionario(cedulaActor))
            {/*Aqui parece que nunca entra */
                this.botonAgregarActor = "Ver Datos Actor";
                this.cajaTextoSeleccionarActor = this.selectedActor.getUzatactorNombres() + " " + this.selectedActor.getUzatactorApellidos();
                this.tieneActor = true;
            }else
            {
                addMessage("No se ha encontrado Actor. Por favor, Ingrese los Datos del Actor o realice otra busqueda");
                this.tieneActor = false;
            }
        }
    }

    public void botonActualizarActor() {      
        if(this.tieneActor)
        {
            updateActor();
        }else
        {
            addActor();
        }

    }

    public void addActor()
    {
        String cedula = this.selectedActor.getUzatactorCedula();
        
        if(!(!cedula.equals("") && 
                !this.selectedActor.getUzatactorNombres().equals("") && 
                !this.selectedActor.getUzatactorApellidos().equals("")))
        {
            addMessage("Por favor Ingrese Cédula y Nombres");
            return;
        }
        
        if(!validadorDeCedula(cedula)){
            addMessage("La cédula es incorrecta");
            return;}
            
        if(ProcuradoriaMethods.findActorbyCedula(cedula)!= null){
            this.selectedActor = ProcuradoriaMethods.findActorbyCedula(cedula);
            addMessage("Se han registrado los Datos de Actor");
            this.botonAgregarActor = "Ver Datos Actor";
            this.cajaTextoSeleccionarActor = this.selectedActor.getUzatactorNombres() + " " + this.selectedActor.getUzatactorApellidos();
            RequestContext.getCurrentInstance().update("principal:selectactor");
        }else if (ProcuradoriaMethods.insertActor(selectedActor)) {
            this.selectedActor = ProcuradoriaMethods.findActorbyCedula(cedula);
            addMessage("Se han registrado los Datos de Actor");
            this.botonAgregarActor = "Ver Datos Actor";
            this.cajaTextoSeleccionarActor = this.selectedActor.getUzatactorNombres() + " " + this.selectedActor.getUzatactorApellidos();
            RequestContext.getCurrentInstance().update("principal:selectactor");                
        }else{
            addMessage("Ha ocurrido un error");}
    }
    
    public boolean validadorDeCedula(String cedula) {
        boolean cedulaCorrecta = false;

        try {

            if (cedula.length() == 10) // ConstantesApp.LongitudCedula
            {
                int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
                if (tercerDigito < 6) {
                    // Coeficientes de validación cédula
                    // El decimo digito se lo considera dígito verificador
                    int[] coefValCedula = { 2, 1, 2, 1, 2, 1, 2, 1, 2 };
                    int verificador = Integer.parseInt(cedula.substring(9,10));
                    int suma = 0;
                    int digito = 0;
                    
                    for (int i = 0; i < (cedula.length() - 1); i++) {
                        digito = Integer.parseInt(cedula.substring(i, i + 1))* coefValCedula[i];
                        suma += ((digito % 10) + (digito / 10));
                    }

                    if ((suma % 10 == 0) && (suma % 10 == verificador)) {
                        cedulaCorrecta = true;
                    }
                    else if ((10 - (suma % 10)) == verificador) {
                        cedulaCorrecta = true;
                    } else {
                        cedulaCorrecta = false;
                    }
                } else {
                cedulaCorrecta = false;
                }
            } else {
            cedulaCorrecta = false;
            }
        } catch (NumberFormatException nfe) {
            addMessage("La cédula solo debe contener numeros");
            cedulaCorrecta = false;
        } catch (Exception err) {
            addMessage("Ocurrio un erro: " + err.getMessage());
            cedulaCorrecta = false;
        }

        if (!cedulaCorrecta) {
            System.out.println("La Cédula ingresada es Incorrecta");
        }
        return cedulaCorrecta;
    }
    
    public void botonActualizarCaso() {
        if(this.idMateria.equals(new BigDecimal("100")) || this.idMateria.equals(new BigDecimal("0")))
        {
            addMessage("No se han asignado la materia, No se ha iniciado el caso");
            return;
        }
        
        if(this.tipoActor.equals(""))
        {
            addMessage("No se han asignado el tipo de actor, No se ha iniciado el caso");
            return;
        }
        
        if(!this.tieneActor){
            addMessage("No existe Actor asignado. No se ha iniciado el caso");
            return;
        }
        
        
        
        BigDecimal cero = new BigDecimal(0);       
        if (this.selectedCaso.getUzatcasoVincu().equals(cero) || this.selectedCaso.getUzatcasoVincu() == null) {            
            this.selectedCaso.setUzatcasoVincu(this.selectedCaso.getUzatcasoId());           
        }  
        System.out.println("");
        updateCaso();
        asignarActoraCaso();
        RequestContext.getCurrentInstance().update("principal:dabogados");
    }

    public void buttonAction(ActionEvent actionEvent) {
        addMessage("Welcome to Primefaces!!");
    }

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    private void updateActor() {
        if (ProcuradoriaMethods.UpdateActor(selectedActor)) {
            addMessage("Se han actualizado los Datos de Actor");
        }
        RequestContext.getCurrentInstance().update("principal:selectactor");     
    }

    private void updateCaso() {
        if(idJudicatura.equals(new BigDecimal("0")))
        {
            idJudicatura = new BigDecimal("100");
        }
        
        this.selectedCaso.setUzatjudi(new Uzatjudi(new UzatjudiId(idMateria, idJudicatura), new Uzatmateri(idMateria)));
        this.selectedCaso.setUzatcasoFlag(new BigDecimal(1));
        this.selectedCaso.setUzatcasoFechaIn(getDate());

        if (ProcuradoriaMethods.UpdateCaso(selectedCaso)) {
            addMessage("Se han actualizado los Datos del Caso");
        } else {
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
        if (ProcuradoriaMethods.InsertInvolCa(involucrado)) {
            addMessage("Se ha asignado corretamente el caso");
        } else {
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

            if (idocedula.equals("0")) {
                find = BannerMethos.FindPersonBannerByCedula(claveFuncionario);
            } else if (idocedula.equals("1")) {
                find = BannerMethos.FindPersonBannerByIdBanner(claveFuncionario);
            }
            if (find != null) {

                SendDataFuncionario(find);
                addMessage("Se ha ingresado Actor en Base de Datos");
                exito = true;
            }

        } else {
            addMessage("Se ha encontrado Actor en Base de Datos");
            exito = true;
        }
        return exito;
    }

    public Boolean findFuncionarioProcuaradoria(String claveFuncionario, String idocedula) {
        Boolean exito = false;
        if (idocedula.equals("0")) {
            this.selectedActor = ProcuradoriaMethods.findActorbyCedula(claveFuncionario);
        } else {
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
        } else {
            addMessage("A ocurrido un error, no se han Grabado los Datos en el Registro");
        }

    }

    public void loadlistMaterias() {
        ArrayList<Uzatmateri> selectItemsMat = ProcuradoriaMethods.ListMaterias();
        
        Collections.sort(selectItemsMat, new Comparator<Uzatmateri>() {
            @Override public int compare(Uzatmateri p1, Uzatmateri p2) {
                if (p1.getUzatmateriaId().doubleValue() > p2.getUzatmateriaId().doubleValue()) {
                    return 1;
                } else if (p1.getUzatmateriaId().doubleValue() < p2.getUzatmateriaId().doubleValue()) {
                    return -1;
                }
                return 0;
            }
            });
        
        this.ItemsMaterias.clear();
        SelectItem si;
        for (int i = 0; i < selectItemsMat.size(); i++) {
            si = new SelectItem(selectItemsMat.get(i).getUzatmateriaId(), selectItemsMat.get(i).getUzatmateriaDescripcion());
            this.ItemsMaterias.add(si);
        }
        
        this.ItemsMaterias.remove(0);
    }

    public void loadlistJudi() {
        BigDecimal idmateri = this.getIdMateria();
        ArrayList<Uzatjudi> selectItemsJud = ProcuradoriaMethods.findjudibyMateriId(idmateri);

        Collections.sort(selectItemsJud, new Comparator<Uzatjudi>() {
                @Override public int compare(Uzatjudi p1, Uzatjudi p2) {
                    if (p1.getId().getUzatjudiId().doubleValue() > p2.getId().getUzatjudiId().doubleValue()) {
                        return 1;
                    } else if (p1.getId().getUzatjudiId().doubleValue() < p2.getId().getUzatjudiId().doubleValue()) {
                        return -1;
                    }
                    return 0;
                }
                });
        
        if (!(selectItemsJud == null)) {
            this.ItemsJudicaturas.clear();
            SelectItem si;
            for (int i = 0; i < selectItemsJud.size(); i++) {
                si = new SelectItem(selectItemsJud.get(i).getId().getUzatjudiId(), selectItemsJud.get(i).getUzatjudiDescripcion());
                this.ItemsJudicaturas.add(si);
            }
            this.ItemsJudicaturas.remove(0);
        } else {
            this.ItemsJudicaturas.clear();
            SelectItem si;
            si = new SelectItem("100", "No existe Judicatura");
            this.ItemsJudicaturas.add(si);
        }
        
    }

    public void findCasos() {
        this.findCaso = ProcuradoriaMethods.FindCasobyNumCausa2(this.valorbusqueda);
        if (this.findCaso != null) {
            this.vincuJudi = this.findCaso.getUzatjudi();
            this.vincuMateria = ProcuradoriaMethods.findMateribyJudiId(this.vincuJudi.getId().getUzatjudiId());           
            addMessage("Se ha encontrado el Caso Solicitado");

        } else {
            addMessage("No se ha encontrado Caso con el número de Causa ingresado");
        }
    }

    public void vincular() {
        this.textoBotonVincular = "Cambiar";
        String codigoCaso = this.casosAsigandos.get(0).getUzatcaso().getUzatcasoNumcausa();
        Uzatcaso temp = ProcuradoriaMethods.FindCasobyNumCausa(codigoCaso);
        BigDecimal codigoVincu = temp.getUzatcasoVincu();
        this.selectedCaso.setUzatcasoVincu(codigoVincu);
        this.textohabilitarvinculación = temp.getUzatcasoNumcausa();
        addMessage("Se ha vinculado el caso");
    }
    
    public void buscarCasoByVinculacion(ActionEvent actionEvent) {
        this.valueFindCaso3 = this.selectedActor.getUzatactorCedula();
            
        if (!this.valueFindCaso4.equals("")) {
            this.casosAsigandos = ProcuradoriaMethods.FindCasosAdminLazyByVinculacion(this.valueFindCaso3,this.valueFindCaso4);
            if (this.casosAsigandos.isEmpty()) {
                addMessage("Error No se encuentran casos relacionados con el mismo actor.");
            }
        } else {
            addMessage("Error Ingrese el número de causa a ser buscado.");
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

    public String getTextoBotonVincular() {
        return textoBotonVincular;
    }

    public void setTextoBotonVincular(String textoBotonVincular) {
        this.textoBotonVincular = textoBotonVincular;
    }

    public String getNumeroocedula() {
        return numeroocedula;
    }

    public void setNumeroocedula(String numeroocedula) {
        this.numeroocedula = numeroocedula;
    }

    public String getValorbusqueda() {
        return valorbusqueda;
    }

    public void setValorbusqueda(String valorbusqueda) {
        this.valorbusqueda = valorbusqueda;
    }

    public Uzatcaso getFindCaso() {
        return findCaso;
    }

    public void setFindCaso(Uzatcaso findCaso) {
        this.findCaso = findCaso;
    }

    public String getNombreActorAnterior() {
        return nombreActorAnterior;
    }

    public void setNombreActorAnterior(String nombreActorAnterior) {
        this.nombreActorAnterior = nombreActorAnterior;
    }

    public String getCedulaActorAnterior() {
        return cedulaActorAnterior;
    }

    public void setCedulaActorAnterior(String cedulaActorAnterior) {
        this.cedulaActorAnterior = cedulaActorAnterior;
    }

    public Uzatjudi getVincuJudi() {
        return vincuJudi;
    }

    public void setVincuJudi(Uzatjudi vincuJudi) {
        this.vincuJudi = vincuJudi;
    }

    public Uzatmateri getVincuMateria() {
        return vincuMateria;
    }

    public void setVincuMateria(Uzatmateri vincuMateria) {
        this.vincuMateria = vincuMateria;
    }
    
    public BigDecimal getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(BigDecimal idMateria) {
        this.idMateria = idMateria;
    }

    public BigDecimal getIdJudicatura() {
        return idJudicatura;
    }

    public void setIdJudicatura(BigDecimal idJudicatura) {
        this.idJudicatura = idJudicatura;
    }

    public String getTextohabilitarvinculación() {
        return textohabilitarvinculación;
    }

    public void setTextohabilitarvinculación(String textohabilitarvinculación) {
        this.textohabilitarvinculación = textohabilitarvinculación;
    }  

    public String getValueFindCaso3() {
        return valueFindCaso3;
    }

    public void setValueFindCaso3(String valueFindCaso3) {
        this.valueFindCaso3 = valueFindCaso3;
    }

    public String getValueFindCaso4() {
        return valueFindCaso4;
    }

    public void setValueFindCaso4(String valueFindCaso4) {
        this.valueFindCaso4 = valueFindCaso4;
    }

    public List<Uzatasign> getCasosAsigandos() {
        return casosAsigandos;
    }

    public void setCasosAsigandos(List<Uzatasign> casosAsigandos) {
        this.casosAsigandos = casosAsigandos;
    }

    public boolean isTieneActor() {
        return tieneActor;
    }

    public void setTieneActor(boolean tieneActor) {
        this.tieneActor = tieneActor;
    }   
    
// </editor-fold>

    public ArrayList<Uzatjudi> loadlistJudi2(BigDecimal idMateria)
    {
        ArrayList<Uzatjudi> selectItemsJud = ProcuradoriaMethods.findjudibyMateriId(idMateria);
        return selectItemsJud;
    }
}
