/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import procuradoria.crud.ProcuradoriaMethods;
import procuradoria.map.Uzatasign;

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

    private List<Uzatasign> casosAsigandos;

    public AdminCasosBean() {
        this.init();
    }

    private void init() {
        this.casosAsigandos = new ArrayList<Uzatasign>();
        this.loadCasosAsignados();
    }

    public void loadCasosAsignados() {
        this.casosAsigandos = ProcuradoriaMethods.FindCasosAdminLazy(new BigDecimal(112), BigDecimal.ONE, BigDecimal.ONE);
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

    public Boolean StateFlagOnOff(BigDecimal flag) {
        Boolean State = false;
        if (flag.equals(new BigDecimal(1))) {
            State = true;
        }
        return State;
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

}
