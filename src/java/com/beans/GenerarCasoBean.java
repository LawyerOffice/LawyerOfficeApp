/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import banner.crud.BannerMethos;
import banner.map.PersonaBanner;
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
    public String motivo = "";

    public String txtaboSelecionado="";
    public String txtbotonasignarabo="Seleccionar";
    
    private Uzatcaso newCaso;
    private Uzatfunci newFuncionario;
    private Uzatjudi newJuzgado;
    
    private boolean existFunci=false;

    public GenerarCasoBean() {
        this.init();
    }

    public void init() {
        this.ItemsJudicaturas = new ArrayList<SelectItem>();
        this.ItemsMaterias = new ArrayList<SelectItem>();
        this.ItemsFuncionarios = new ArrayList<SelectItem>();

        newCaso = new Uzatcaso();
        newFuncionario = new Uzatfunci();
        idAsignador = getUserAttribute();
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
        SelectItem si;
        for (int i = 0; i < selectItemsMat.size(); i++) {
            if (selectItemsMat.get(i).getUzatmateriaId() != new BigDecimal(100)) {
                si = new SelectItem(selectItemsMat.get(i).getUzatmateriaId(), selectItemsMat.get(i).getUzatmateriaDescripcion());
                this.ItemsMaterias.add(si);
            }
        }
    }

    public void loadlistJudi() {
        //Dennis Santamaria
        BigDecimal idmateri = new BigDecimal(idMateria);
        ArrayList<Uzatjudi> selectItemsJud = ProcuradoriaMethods.findjudibyMateriId(idmateri);
        this.ItemsJudicaturas.clear();
        SelectItem si;
        for (int i = 0; i < selectItemsJud.size(); i++) {
            if (selectItemsJud.get(i).getId().getUzatjudiId() != new BigDecimal(100)) {
                si = new SelectItem(selectItemsJud.get(i).getId().getUzatjudiId(), selectItemsJud.get(i).getUzatjudiDescripcion());
                this.ItemsJudicaturas.add(si);
            }
        }
    }

    public void findFuncionario(ActionEvent event) {
        switch (this.patterFuncionario) {
            case "Id Banner":
                ValidateFuncionario(this.claveFuncionario, 0);
                break;
            case "Cedula":
                ValidateFuncionario(this.claveFuncionario, 1);
                break;
            default:
                generateMessage(FacesMessage.SEVERITY_INFO, "Por favor", "Seleciona un campo.");
                break;
        }

    }

    private void ValidateFuncionario(String claveFuncionario, int type) {     
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
            }else
            {
                generateMessage(FacesMessage.SEVERITY_INFO, "Ningún Resultado", "No se ha encontrado a ningún funcionario");
                this.newFuncionario = new Uzatfunci();
                existFunci = false;
                this.txtaboSelecionado = "";
                this.txtbotonasignarabo="Seleccionar";
                return;
            }
        }
        existFunci = true;
        this.txtbotonasignarabo="Cambiar";
        txtaboSelecionado = this.newFuncionario.getUzatfuncionarioApellidos() 
                +" "+ this.newFuncionario.getUzatfuncionarioNombres();      
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

    public Boolean grabarFuncionarios() {
        
        Boolean exito = false;
        Uzatfunci temp = ProcuradoriaMethods.FindFuncionarioByCedula(newFuncionario.getUzatfuncionarioCedula());
        if (temp == null) {
            newFuncionario.setUzatfuncionarioId(new BigDecimal(1));
            ProcuradoriaMethods.InserFuncionario(newFuncionario);
            idFunci = ProcuradoriaMethods.FindFuncionarioByCedula(newFuncionario.getUzatfuncionarioCedula()).getUzatfuncionarioId();
        } else {
            idFunci = temp.getUzatfuncionarioId(); 
            ProcuradoriaMethods.UpdateFunci(newFuncionario);
        }
        if (idFunci != null) {
            exito = true;
        }
        return exito;
    }

    public Boolean grabarCaso() {
        Boolean Exito = false;
        BigDecimal idmateri = new BigDecimal(idMateria);
        BigDecimal idjudi = new BigDecimal(idJudicatura);
        newJuzgado.setId(new UzatjudiId(idmateri, idjudi));
        newCaso.setUzatjudi(newJuzgado);
        newCaso.setUzatcasoTipo(tipoCaso);
        newCaso.setUzatcasoNumcausa(numCausa);
        newCaso.setUzatcasoFechaIn(getDate());
        newCaso.setUzatcasoMotivo(motivo);
        newCaso.setUzatcasoFlag(new BigDecimal(2));
        Exito = ProcuradoriaMethods.InsertCaso(newCaso);
        return Exito;
    }

    public Boolean grabarCaso2() {
        BigDecimal idmateri = new BigDecimal(idMateria);
        BigDecimal idjudi = new BigDecimal(idJudicatura);
        newJuzgado.setId(new UzatjudiId(idmateri, idjudi));
        newCaso.setUzatjudi(newJuzgado);
        newCaso.setUzatcasoTipo(tipoCaso);
        newCaso.setUzatcasoNumcausa(numCausa);
        newCaso.setUzatcasoFechaIn(getDate());
        newCaso.setUzatcasoMotivo(motivo);
        newCaso.setUzatcasoFlag(new BigDecimal(3));
        Boolean exito = ProcuradoriaMethods.InsertCaso(newCaso);
        return exito;
    }

    public static String getDate() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    public Boolean AsignarCasoAbogado() {
        Boolean exito = true;
        Uzatasign asign = new Uzatasign();
        asign.setId(new UzatasignId(idFunci, ProcuradoriaMethods.FindCasobyNumCausa(numCausa).getUzatcasoId()));
        asign.setUzatasignarFlag(BigDecimal.ONE);
        asign.setUzatasignarId(idAsignador);
        asign.setUzatasignarMotivo(motivo);
        asign.setUzatasignarFechaIn(getDate());
        exito = ProcuradoriaMethods.InsertNuevaAsignacion(asign);
        return exito;
    }

    public void grabarnuevoCaso(ActionEvent event) {
        if(!informacionCompleta())
        {
            return;
        }
        
        if (grabarFuncionarios()) {
            if (grabarCaso()) {
                if (AsignarCasoAbogado()) {
                    generateMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Se ha generado un nuevo caso satisfactoriamente");
                    this.numCausa = "";
                    this.motivo = "";
                    this.tipoCaso = "";
                    this.txtaboSelecionado = "";
                    this.existFunci = false;
                    this.init();
                } else {
                    generateMessage(FacesMessage.SEVERITY_FATAL, "ERROR no se ha grabado nuevo caso", "");
                }
            }
        }
    }

    public Boolean informacionCompleta()
    {
        Boolean exito = false;
        
        if("".equals(this.numCausa.trim())){
            generateMessage(FacesMessage.SEVERITY_INFO, "Campos Incompletos", "Ingrese el Número de Causa");
        }else if("".equals(this.motivo.trim())){
            generateMessage(FacesMessage.SEVERITY_INFO, "Campos Incompletos", "Ingrese el Motivo");
        }else if("".equals(this.tipoCaso.trim())){
            generateMessage(FacesMessage.SEVERITY_INFO, "Campos Incompletos", "Ingrese el Delito/Acción");
        }else if(existFunci == false){
            generateMessage(FacesMessage.SEVERITY_INFO, "Campos Incompletos", "Seleccione a un abogado responsable del caso");
        }else
        {
            exito = true;
        }
        
        return exito;
    }
    
    public void grabarnuevoCasoSecretaria(ActionEvent event) {

        if (grabarCaso2()) {
            this.numCausa = "";
            this.motivo = "";
            this.tipoCaso = "";
            this.txtaboSelecionado = "";
            this.init();
            generateMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Se ha generado un nuevo caso satisfactoriamente");
        } else {
            generateMessage(FacesMessage.SEVERITY_FATAL, "ERROR no se ha grabado nuevo caso", "");
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

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getTxtaboSelecionado() {
        return txtaboSelecionado;
    }

    public void setTxtaboSelecionado(String txtaboSelecionado) {
        this.txtaboSelecionado = txtaboSelecionado;
    }
    
    public String getTxtbotonasignarabo() {
        return txtbotonasignarabo;
    }

    public void setTxtbotonasignarabo(String txtbotonasignarabo) {
        this.txtbotonasignarabo = txtbotonasignarabo;
    }
    
// </editor-fold>
}
