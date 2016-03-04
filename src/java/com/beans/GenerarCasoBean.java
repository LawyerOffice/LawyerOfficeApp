/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.beans;

import banner.crud.BannerMethos;
import banner.map.PersonaBanner;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import procuradoria.crud.ProcuradoriaMethods;
import procuradoria.map.Uzatasign;
import procuradoria.map.UzatasignId;
import procuradoria.map.Uzatcaso;
import procuradoria.map.Uzatfunci;
import procuradoria.map.Uzatjudi;
import procuradoria.map.UzatjudiId;
import procuradoria.map.Uzatmateri;

/**
 *
 * @author Ivan
 */
@ManagedBean
@ViewScoped
public class GenerarCasoBean {

    /**
     * Creates a new instance of GenerarCasoBean
     */
    private String claveFuncionario;
    
    private ArrayList<SelectItem> ItemsMaterias;
    private ArrayList<SelectItem> ItemsJudicaturas;
    private ArrayList<SelectItem> ItemsFuncionarios;
    
    private String idMateria = "100";
    private String idJudicatura = "100";
    private String patterFuncionario = "Vacio";
    private BigDecimal idAsignador;
    private String numCausa = "";
    private String tipoCaso = "";
    public BigDecimal idFunci;
    public BigDecimal idCaso;
    public String motivo;
    
    private Uzatcaso newCaso;
    private Uzatfunci newFuncionario;
    private Uzatjudi newJuzgado;
    
    public GenerarCasoBean() {
        this.init();
    }
    
    public void init(){        
        this.ItemsJudicaturas = new ArrayList<SelectItem>();
        this.ItemsMaterias = new ArrayList<SelectItem>();
        this.ItemsFuncionarios = new ArrayList<SelectItem>();
        
        newCaso = new Uzatcaso();
        newFuncionario = new Uzatfunci();
        idAsignador = getAsignadorSesion();
        newJuzgado = new Uzatjudi();
        
        this.loadlistMaterias();
        this.loadlistFuncionarios();
        
    }

    
     public void loadlistFuncionarios() {
        ArrayList<String> selectItemsCli = new ArrayList<String>();
        selectItemsCli.add("Id Banner");
        selectItemsCli.add("Cedula");
        this.ItemsFuncionarios.clear();
        for (String Item : selectItemsCli) {
            this.ItemsFuncionarios.add(new SelectItem(Item, Item));
        }
    }   
     
     
   public void loadlistMaterias() {
       //Dennis Santamaria
       
        ArrayList<Uzatmateri> selectItemsMat = ProcuradoriaMethods.ListMaterias();
        this.ItemsMaterias.clear();
        SelectItem  si;
        for (int i = 0; i < selectItemsMat.size(); i++) {
            if(selectItemsMat.get(i).getUzatmateriaId() != new BigDecimal(100))
            {
                si = new SelectItem(selectItemsMat.get(i).getUzatmateriaId(),selectItemsMat.get(i).getUzatmateriaDescripcion());
                    this.ItemsMaterias.add(si);   
            }
        }
    }
   
      public void loadlistJudi() {
       //Dennis Santamaria
        BigDecimal idmateri = new BigDecimal(idMateria);
        ArrayList<Uzatjudi> selectItemsJud = ProcuradoriaMethods.findjudibyMateriId(idmateri);
        this.ItemsJudicaturas.clear();
        SelectItem  si;
        for (int i = 0; i < selectItemsJud.size(); i++) {
            if(selectItemsJud.get(i).getId().getUzatjudiId() != new BigDecimal(100))
            {
                si = new SelectItem(selectItemsJud.get(i).getId().getUzatjudiId(),selectItemsJud.get(i).getUzatjudiDescripcion());
                this.ItemsJudicaturas.add(si);   
            }
        }
    }
        
    public void findFuncionario(ActionEvent event) {

        if (this.patterFuncionario.equals("Id Banner")) {
            if (!ValidateFuncionario(this.claveFuncionario, 0)) {
            }
        } else if (patterFuncionario.equals("Cedula")) {
            if (!ValidateFuncionario(this.claveFuncionario, 1)) {
            }
        } else {
            generateMessage(FacesMessage.SEVERITY_INFO, "Por favor", "Seleciona un campo.");
        }

    }
    
    private Boolean ValidateFuncionario(String claveFuncionario, int type) {
        Boolean exito = false;
        PersonaBanner find = null;
        String mdatoCli = claveFuncionario.trim();
        claveFuncionario = mdatoCli.toUpperCase();
        if (!findFuncionarioProcuaradoria(claveFuncionario)) {
            if (type == 0) {
                find = BannerMethos.FindPersonBannerByIdBanner(claveFuncionario);
            } else if (type == 1) {
                find = BannerMethos.FindPersonBannerByCedula(claveFuncionario);
            }
            if (find != null) {
                SendDataFuncionario(find);
                exito = true;
            }
        }
        return exito;
    }

    public Boolean findFuncionarioProcuaradoria(String claveFuncionario) {
        Boolean exito = false;
        this.newFuncionario = ProcuradoriaMethods.FindFuncionarioByCedulaOrIdBanner(claveFuncionario);
        if (this.newFuncionario != null) {
            exito = true;
        } else {
            this.newFuncionario = new Uzatfunci();
        }
        return exito;
    }
    
    public void SendDataFuncionario(PersonaBanner Funcionario) {
        this.newFuncionario.setUzatfuncionarioApellidos(Funcionario.getApellidos());
        this.newFuncionario.setUzatfuncionarioCedula(Funcionario.getCedula());
        this.newFuncionario.setUzatfuncionarioEmail(Funcionario.getEmail());
        this.newFuncionario.setUzatfuncionarioFlag(BigDecimal.ONE);
        this.newFuncionario.setUzatfuncionarioNombres(Funcionario.getNombres());
        this.newFuncionario.setUzatfuncionarioIdbanner(Funcionario.getIdBanner());
    }
    
    //Generar mensaje de no encontrado
    public void generateMessage(FacesMessage.Severity Tipo, String Header, String Mensaje) {
        FacesMessage message = new FacesMessage(Tipo, Header, Mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    
    //Obtener el funcionario que en ese momento esta asignando
    public BigDecimal getAsignadorSesion()
    {
        return new BigDecimal(122);
    }
    
    public void grabarFuncionarios()
    {
        Uzatfunci temp = ProcuradoriaMethods.FindFuncionarioByCedula(newFuncionario.getUzatfuncionarioCedula());
        if(temp == null)
        {
            newFuncionario.setUzatfuncionarioId(new BigDecimal(1));
            ProcuradoriaMethods.InserFuncionario(newFuncionario);
            idFunci = ProcuradoriaMethods.FindFuncionarioByCedula(newFuncionario.getUzatfuncionarioCedula()).getUzatfuncionarioId();
        }
        else
        {
            idFunci = temp.getUzatfuncionarioId();
        }
    }
    
    public void grabarCaso()
    {
        BigDecimal idmateri = new BigDecimal(idMateria);
        BigDecimal idjudi = new BigDecimal(idJudicatura);
        newJuzgado.setId(new UzatjudiId(idmateri, idjudi));
        newCaso.setUzatjudi(newJuzgado);
        newCaso.setUzatcasoTipo(tipoCaso);
        newCaso.setUzatcasoNumcausa(numCausa);
        newCaso.setUzatcasoFechaIn(getDate());
        newCaso.setUzatcasoMotivo("Por asignar");
        ProcuradoriaMethods.InsertCaso(newCaso);
    }
    
    public static String getDate() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }
    
    public void AsignarCasoAbogado()
    {
        Uzatasign asign = new Uzatasign();
        asign.setId(new UzatasignId(idFunci,ProcuradoriaMethods.FindCasobyNumCausa(numCausa).getUzatcasoId()));
        asign.setUzatasignarFlag(BigDecimal.ONE);
        asign.setUzatasignarMotivo(motivo);
        asign.setUzatasignarFechaIn(getDate());
        
        ProcuradoriaMethods.InsertNuevaAsignacion(asign);
    }
    
    public void grabarnuevoCaso(ActionEvent event)
    {
        try{
            grabarFuncionarios();
            grabarCaso();
            AsignarCasoAbogado();
            generateMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Se ha generado un nuevo caso satisfactoriamente");
        }catch(Exception ex)
        {
            generateMessage(FacesMessage.SEVERITY_FATAL, "ERROR no se ha grabado nuevo caso", ex.getMessage());
        }
        
    }
    
    // <editor-fold defaultstate="collapsed" desc=" Getters and Setters ">
    public ArrayList<SelectItem> getItemsMaterias() {
        return ItemsMaterias;
    }
    
    public void setItemsMaterias(ArrayList<SelectItem> ItemsMaterias) {
        this.ItemsMaterias = ItemsMaterias;
    }
    
    public ArrayList<SelectItem> getItemsJudicaturas() {
        return ItemsJudicaturas;
    }
    
    public Uzatcaso getNewCaso() {
        return newCaso;
    }
    
    public void setNewCaso(Uzatcaso newCaso) {
        this.newCaso = newCaso;
    }
    
    public void setItemsJudicaturas(ArrayList<SelectItem> ItemsJudicaturas) {
        this.ItemsJudicaturas = ItemsJudicaturas;
    }
    
    public String getIdMateria() {
        return idMateria;
    }
    
    public BigDecimal getBigIdMateria(String id) {
        return new BigDecimal(id);
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
    
    public String getPatterFuncionario() {
        return patterFuncionario;
    }
    
    public void setPatterFuncionario(String patterFuncionario) {
        this.patterFuncionario = patterFuncionario;
    }
    
    public ArrayList<SelectItem> getItemsFuncionarios() {
        return ItemsFuncionarios;
    }
    
    public void setItemsFuncionarios(ArrayList<SelectItem> ItemsFuncionarios) {
        this.ItemsFuncionarios = ItemsFuncionarios;
    }
    
    public String getClaveFuncionario() {
        return claveFuncionario;
    }
    
    public void setClaveFuncionario(String claveFuncionario) {
        this.claveFuncionario = claveFuncionario;
    }
    
    public Uzatfunci getNewFuncionario() {
        return newFuncionario;
    }
    
    public void setNewFuncionario(Uzatfunci newFuncionario) {
        this.newFuncionario = newFuncionario;
    }

    public String getNumCausa() {
        return numCausa;
    }

    public void setNumCausa(String numCausa) {
        this.numCausa = numCausa;
    }

    public String getTipoCaso() {
        return tipoCaso;
    }

    public void setTipoCaso(String tipoCaso) {
        this.tipoCaso = tipoCaso;
    }
    
    
// </editor-fold>

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }


}