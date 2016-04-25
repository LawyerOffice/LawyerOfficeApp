/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import com.util.LawyerOfficeUtil;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;
import procuradoria.crud.ProcuradoriaMethods;
import procuradoria.map.Uzatasign;
import procuradoria.map.Uzatcaso;
import procuradoria.map.Uzatmateri;

/**
 *
 * @author Ivan
 */
@ManagedBean
@ViewScoped
public class AdminCasosBean {

    /**
     * Creates a new instance of AdminCasosBean
     */
    private String patterFindCaso;
    private String valueFindCaso;
    private String valueFindCaso2;
    private String valueFindCaso3;
    private String valueFindCaso4;

    private List<Uzatasign> casosAsigandos;

    private ArrayList<SelectItem> ItemsMaterias;
    private BigDecimal idMateria;

    public AdminCasosBean() {

        HttpServletRequest origRequest
                = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String urlRequest = origRequest.getRequestURI().toString();
        urlRequest = urlRequest.replace("/LawyerOfficeApp/faces/views/", "");

        if (!urlRequest.equals("ver_caso_abo.xhtml")) {
            this.init();
        } else {
            this.casosAsigandos = null;
            this.ItemsMaterias = new ArrayList<SelectItem>();
            this.loadlistMaterias();
        }

    }

    private void init() {
        this.casosAsigandos = new ArrayList<Uzatasign>();
        this.loadCasosAsignados();
    }

    public void loadCasosAsignados() {
        this.casosAsigandos = ProcuradoriaMethods.FindCasosAdminLazy(this.getUserIdAttribute(), BigDecimal.ONE, BigDecimal.ONE);
    }

    public void loadlistMaterias() {
        ArrayList<Uzatmateri> selectItemsMat = ProcuradoriaMethods.ListMaterias();
        this.getItemsMaterias().clear();
        SelectItem si;
        for (int i = 0; i < selectItemsMat.size(); i++) {
            si = new SelectItem(selectItemsMat.get(i).getUzatmateriaId(), selectItemsMat.get(i).getUzatmateriaDescripcion());
            this.getItemsMaterias().add(si);
        }
        this.getItemsMaterias().remove(0);
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

    private BigDecimal getUserIdAttribute() {
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

    public void openfase(ActionEvent event, BigDecimal uzatcasoId) {
        RequestContext context = RequestContext.getCurrentInstance();
        String ruta = LawyerOfficeUtil.getURL_Login() + "views/fases_caso.xhtml";
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
                put("uzatcasoId", uzatcasoId);
        context.addCallbackParam("loggedIn", true);
        context.addCallbackParam("ruta", ruta);
    }

    public void openfaseOnlySee(ActionEvent event, BigDecimal uzatcasoId) {
        RequestContext context = RequestContext.getCurrentInstance();
        String ruta = LawyerOfficeUtil.getURL_Login() + "views/ver_caso_busq.xhtml";
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
                put("uzatcasoId", uzatcasoId);
        context.addCallbackParam("loggedIn", true);
        context.addCallbackParam("ruta", ruta);
    }

    public Boolean StateFlagOnOff(BigDecimal flag) {
        Boolean State = false;
        if (flag.equals(new BigDecimal(1))) {
            State = true;
        }
        return State;
    }

    public void buscarCasoByNumCausa(ActionEvent actionEvent) {
        if (valueFindCaso.equals("")) {
            this.loadCasosAsignados();
        } else {
            this.casosAsigandos = ProcuradoriaMethods.FindCasosAdminLazyByNumCausa(this.getUserIdAttribute(), BigDecimal.ONE, BigDecimal.ONE, valueFindCaso);
            if (this.casosAsigandos == null) {
                generateMessage(FacesMessage.SEVERITY_INFO, "Error", "No se encuentran casos relacionados con dicho número de causa.");
            }
        }
    }

    public void buscarCasoByNumCausaGeneral(ActionEvent actionEvent) {
        if (this.valueFindCaso.equals("")) {
            generateMessage(FacesMessage.SEVERITY_INFO, "Error", "Ingrese el número de causa a ser buscado.");
            this.casosAsigandos = null;
        } else {
            this.casosAsigandos = ProcuradoriaMethods.FindCasosAdminLazyByNumCausaGen(BigDecimal.ONE, valueFindCaso);
            if (this.casosAsigandos.size() == 0) {
                generateMessage(FacesMessage.SEVERITY_INFO, "Error", "No se encuentran casos relacionados con dicho número de causa.");
            }
        }
    }

    public void buscarCasoByNumCausaMateria(ActionEvent actionEvent) {

        if (this.valueFindCaso2.equals("")) {
            generateMessage(FacesMessage.SEVERITY_INFO, "Error", "Ingrese el número de causa a ser buscado.");
            this.casosAsigandos = null;
        } else {
            if (!this.getIdMateria().equals(new BigDecimal(BigInteger.ZERO))) {
                this.casosAsigandos = ProcuradoriaMethods.FindCasosAdminLazyByNumCausaMateria(BigDecimal.ONE, this.valueFindCaso2,this.idMateria);
                if (this.casosAsigandos.size() == 0) {
                    generateMessage(FacesMessage.SEVERITY_INFO, "Error", "No se encuentran casos relacionados con dicho número de causa y materia.");
                }
            } else {
                generateMessage(FacesMessage.SEVERITY_INFO, "Error", "Seleccione la materia.");
            }
        }
    }
    
    public void buscarCasoByVinculacion(ActionEvent actionEvent) {

        if (this.getValueFindCaso3().equals("")) {
            generateMessage(FacesMessage.SEVERITY_INFO, "Error", "Ingrese la cédula del abogado.");
            this.casosAsigandos = null;
        } else {
            if (!this.valueFindCaso4.equals("")) {
                this.casosAsigandos = ProcuradoriaMethods.FindCasosAdminLazyByVinculacion(this.valueFindCaso3,this.valueFindCaso4);
                if (this.casosAsigandos.size() == 0) {
                    generateMessage(FacesMessage.SEVERITY_INFO, "Error", "No se encuentran casos relacionados con dicho número de causa y cédula.");
                }
            } else {
                generateMessage(FacesMessage.SEVERITY_INFO, "Error", "Ingrese el número de causa a ser buscado.");
            }
        }
    }

    public void onTabChange(TabChangeEvent event) {
        this.patterFindCaso = "";
        this.valueFindCaso = "";
        this.valueFindCaso2 = "";
        this.setIdMateria(new BigDecimal(BigInteger.ZERO));
    }

    public void generateMessage(FacesMessage.Severity Tipo, String Header, String Mensaje) {
        FacesMessage message = new FacesMessage(Tipo, Header, Mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public List<Uzatasign> getCasosAsigandos() {
        return casosAsigandos;
    }

    public void setCasosAsigandos(List<Uzatasign> casosAsigandos) {
        this.casosAsigandos = casosAsigandos;
    }

    public String getPatterFindCaso() {
        return patterFindCaso;
    }

    public void setPatterFindCaso(String patterFindCaso) {
        this.patterFindCaso = patterFindCaso;
    }

    public String getValueFindCaso() {
        return valueFindCaso;
    }

    public void setValueFindCaso(String valueFindCaso) {
        this.valueFindCaso = valueFindCaso;
    }

    public ArrayList<SelectItem> getItemsMaterias() {
        return ItemsMaterias;
    }

    public void setItemsMaterias(ArrayList<SelectItem> ItemsMaterias) {
        this.ItemsMaterias = ItemsMaterias;
    }

    public BigDecimal getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(BigDecimal idMateria) {
        this.idMateria = idMateria;
    }

    public String getValueFindCaso2() {
        return valueFindCaso2;
    }

    public void setValueFindCaso2(String valueFindCaso2) {
        this.valueFindCaso2 = valueFindCaso2;
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

}
