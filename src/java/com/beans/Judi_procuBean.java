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
import java.util.List;
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
import procuradoria.map.UzatjudiId;
import procuradoria.map.Uzatmateri;

/**
 *
 * @author FANNY
 */
@ManagedBean
@ViewScoped
public class Judi_procuBean implements Serializable{

    /**
     * Creates a new instance of ResumenProcuBean
     */
    private List<Uzatmateri> materias;
    private List<Uzatjudi> judicatura;
    private Uzatmateri newMateria;
    private Uzatjudi newjudicatura;
    private String materiaSeleccionada;
    
    public Judi_procuBean() {
        
        materias = new ArrayList<Uzatmateri>();
        judicatura = new ArrayList<Uzatjudi>();
        newMateria = new Uzatmateri();
        newjudicatura = new Uzatjudi();
        
        this.loadlistMaterias(); 
        
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
    
    public void loadlistMaterias() {    
        this.materias = ProcuradoriaMethods.ListMaterias(); 
       // this.materias.remove(0);
    }

    public void loadlistJudi(String codMateri) {
        BigDecimal idMateri = new BigDecimal(codMateri);
        this.judicatura = ProcuradoriaMethods.findjudibyMateriId(idMateri);         
    }
    
    public int getNumeroCasosporMateria(String materia)
    {
        int temp = 0;
        temp = ProcuradoriaMethods.findNumerosdeCasosbyMateri(materia);  
        return temp;
    }

    public void cargarJudicaturas()
    {
        System.out.println();
        loadlistJudi(materiaSeleccionada);
    }
    
    
// <editor-fold defaultstate="collapsed" desc=" GETTERS ANS SETTERS ">
    public List<Uzatmateri> getMaterias() {
        return materias;
    }
    
    public void setMaterias(List<Uzatmateri> materias) {
        this.materias = materias;
    }
    
    public List<Uzatjudi> getJudicatura() {
        return judicatura;
    }
    
    public void setJudicatura(List<Uzatjudi> judicatura) {
        this.judicatura = judicatura;
    }
    
    public Uzatmateri getNewMateria() {
        return newMateria;
    }
    
    public void setNewMateria(Uzatmateri newMateria) {
        this.newMateria = newMateria;
    }
    
    public Uzatjudi getNewjudicatura() {
        return newjudicatura;
    }
    
    public void setNewjudicatura(Uzatjudi newjudicatura) {
        this.newjudicatura = newjudicatura;
    }
    
    public String getMateriaSeleccionada() {
        return materiaSeleccionada;
    }

    public void setMateriaSeleccionada(String materiaSeleccionada) {
        this.materiaSeleccionada = materiaSeleccionada;
    }

// </editor-fold>

    
    
    
}

