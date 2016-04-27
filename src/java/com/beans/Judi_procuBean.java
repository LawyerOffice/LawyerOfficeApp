/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.beans;

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
import javax.servlet.http.HttpSession;
import procuradoria.crud.ProcuradoriaMethods;
import procuradoria.map.Uzatjudi;
import procuradoria.map.UzatjudiId;
import procuradoria.map.Uzatmateri;

/**
 *
 * @author FANNY
 */
@ManagedBean
@ViewScoped
public class Judi_procuBean{

    /**
     * Creates a new instance of ResumenProcuBean
     */
    private List<Uzatmateri> materias;
    private List<Uzatjudi> judicatura;
    private Uzatmateri newMateria;
    private Uzatmateri selectedMateria;
    private Uzatjudi newjudicatura;
    
    private Uzatmateri changeMateria;
    private Uzatjudi changejudicatura;
    
    private String materiaSeleccionada;
    private String nuevaMateria;
    private String nuevaJudicatura;
    
    private BigDecimal idmateriaeditar;
    private BigDecimal idjudicaturaeditar;
    
    public Judi_procuBean() {
        
        materias = new ArrayList<Uzatmateri>();
        judicatura = new ArrayList<Uzatjudi>();
        
        selectedMateria = new Uzatmateri();

        newMateria = new Uzatmateri();
        newjudicatura = new Uzatjudi();
        
        changeMateria = new Uzatmateri();
        changejudicatura = new Uzatjudi();
        
        nuevaMateria = "vacio";
        nuevaJudicatura = "vacio";
        
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
        if(this.materias != null){
            Collections.sort(materias, new Comparator<Uzatmateri>() {
            @Override public int compare(Uzatmateri p1, Uzatmateri p2) {
                if (p1.getUzatmateriaId().doubleValue() > p2.getUzatmateriaId().doubleValue()) {
                    return 1;
                } else if (p1.getUzatmateriaId().doubleValue() < p2.getUzatmateriaId().doubleValue()) {
                    return -1;
                }
                return 0;
            }
            });
        }        
        this.materias.remove(0);
    }

    public void loadlistJudi(BigDecimal codMateri) {
        
        this.judicatura = ProcuradoriaMethods.findjudibyMateriId(codMateri);   
        if(this.judicatura != null){
            
            Collections.sort(judicatura, new Comparator<Uzatjudi>() {
            @Override public int compare(Uzatjudi p1, Uzatjudi p2) {
                if (p1.getId().getUzatjudiId().doubleValue() > p2.getId().getUzatjudiId().doubleValue()) {
                    return 1;
                } else if (p1.getId().getUzatjudiId().doubleValue() < p2.getId().getUzatjudiId().doubleValue()) {
                    return -1;
                }
                return 0;
            }
            });
            
            this.judicatura.remove(0);
        }
    }
    
    public int getNumeroCasosporMateria(String materia)
    {
        int temp = 0;
        temp = ProcuradoriaMethods.findNumerosdeCasosbyMateri(materia);  
        return temp;
    }

    public void cargarJudicaturas()
    {
        addMessage(selectedMateria.getUzatmateriaDescripcion());
        loadlistJudi(selectedMateria.getUzatmateriaId());
    }
    
    public void editarMaterias()
    {
        
        if(ProcuradoriaMethods.UpdateMateria(changeMateria))
        {
            addMessage("Se ha actualizado la Materia Correctamente");
            this.loadlistMaterias();
        }else
        {
            addMessage("Ha ocurrido un error");
        }
    }
    
    public void editarJudicaturas()
    {
        if(ProcuradoriaMethods.UpdateJudicatura(changejudicatura))
        {
            addMessage("Se ha actualizado la Especialización Correctamente");
            cargarJudicaturas();
        }else
        {
            addMessage("Ha ocurrido un error");
        }
    }
    
    public void cambiarmateria(BigDecimal ID)
    {
        this.idmateriaeditar = ID;
        changeMateria = ProcuradoriaMethods.FindMateriabyId(this.idmateriaeditar);
        
    }
    
    public void cambiarjudicatura(BigDecimal ID)
    {
        this.idjudicaturaeditar = ID;
        changejudicatura = ProcuradoriaMethods.findjudi(this.selectedMateria.getUzatmateriaId(), ID);
    }
    
    public void inicializarMateria()
    {
      
        this.newMateria = new Uzatmateri(new BigDecimal("120"));
    }
    
    public void inicializarJudicatura()
    {
        this.newjudicatura = new Uzatjudi(new UzatjudiId(selectedMateria.getUzatmateriaId(),new BigDecimal("120")),selectedMateria,"vacio",null);       
    }
    
    public void nuevaMaterias()
    {
        if(ProcuradoriaMethods.insertMateria(newMateria))
        {
            //Uzatmateri temp = ProcuradoriaMethods.findmateribynombre(newMateria.getUzatmateriaDescripcion());
//            if(temp != null)
//            {
//                Uzatjudi judiVacio = new Uzatjudi(new UzatjudiId(temp.getUzatmateriaId(),new BigDecimal("120")),temp,"VACIO",null);
//                ProcuradoriaMethods.insertJudicatura(judiVacio);
//                
//                
//                addMessage("Se ha ingresado la Materia Correctamente");
//                this.loadlistMaterias();
//            }
//            
        }else
        {
            addMessage("Ha ocurrido un error");
        }
    }
    
    public void nuevaJudicaturas()
    {
        if(ProcuradoriaMethods.insertJudicatura(newjudicatura))
        {
            addMessage("Se ha ingresado la Especialización Correctamente");
            cargarJudicaturas();
        }else
        {
            addMessage("Ha ocurrido un error");
        }
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

    public Uzatmateri getSelectedMateria() {
        return selectedMateria;
    }

    public void setSelectedMateria(Uzatmateri selectedMateria) {
        this.selectedMateria = selectedMateria;
    }
    
    public String getNuevaMateria() {
        return nuevaMateria;
    }

    public void setNuevaMateria(String nuevaMateria) {
        this.nuevaMateria = nuevaMateria;
    }

    public String getNuevaJudicatura() {
        return nuevaJudicatura;
    }

    public void setNuevaJudicatura(String nuevaJudicatura) {
        this.nuevaJudicatura = nuevaJudicatura;
    }
    
    public BigDecimal getIdmateriaeditar() {
        return idmateriaeditar;
    }

    public void setIdmateriaeditar(BigDecimal idmateriaeditar) {
        this.idmateriaeditar = idmateriaeditar;
    }

    public BigDecimal getIdjudicaturaeditar() {
        return idjudicaturaeditar;
    }

    public void setIdjudicaturaeditar(BigDecimal idjudicaturaeditar) {
        this.idjudicaturaeditar = idjudicaturaeditar;
    }
    
    public Uzatmateri getChangeMateria() {
        return changeMateria;
    }

    public void setChangeMateria(Uzatmateri changeMateria) {
        this.changeMateria = changeMateria;
    }

    public Uzatjudi getChangejudicatura() {
        return changejudicatura;
    }

    public void setChangejudicatura(Uzatjudi changejudicatura) {
        this.changejudicatura = changejudicatura;
    }

// </editor-fold>
    

    
    
    
   

    

    

    
    
    
}

