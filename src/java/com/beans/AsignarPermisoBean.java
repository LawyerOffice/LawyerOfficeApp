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
import java.util.GregorianCalendar;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import procuradoria.crud.ProcuradoriaMethods;
import procuradoria.map.*;

/**
 *
 * @author Ivan
 */
@ManagedBean
@ViewScoped
public class AsignarPermisoBean {

    /**
     * Creates a new instance of AsignarPermisoBean
     */
    private Uzatasign NuevaAsiganacion;

    private String patterFuncionario;
    private String patterRoles;

    private String claveFuncionario;

    private ArrayList<SelectItem> ItemsFuncionarios;
    private ArrayList<SelectItem> ItemsRoles;

    private List<Uzatrol> rolsAsignandos;
    private Uzatrol selectedRol;
    private Uzatrol newRol;

    private Uzatfunci newFuncionario;

    public AsignarPermisoBean() {
        this.init();
        if (this.NuevaAsiganacion == null) {
            this.NuevaAsiganacion = new Uzatasign();
        }
    }

    private void init() {
        this.setNuevaAsiganacion(new Uzatasign());
        this.setItemsFuncionarios(new ArrayList<SelectItem>());
        this.setItemsRoles(new ArrayList<SelectItem>());
        this.setPatterFuncionario("");
        this.setPatterRoles("");
        this.newFuncionario = new Uzatfunci();
        this.rolsAsignandos = new ArrayList<Uzatrol>();
        this.selectedRol = new Uzatrol();
        this.newRol = new Uzatrol();
        this.setNewRol(new Uzatrol());
        this.loadlistFuncionarios();
        this.loadlistRoles();
        this.loadlistRolesAsignados();
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

    public void loadlistRoles() {
        ArrayList<Uzattrol> list1 = ProcuradoriaMethods.ListTipoRol();
        this.ItemsRoles.clear();
        for (int i = 0; i < list1.size(); i++) {
            this.ItemsRoles.add(new SelectItem(list1.get(i).getUzattiporolId(), list1.get(i).getUzattiporolDescripcion()));
        }
    }

    public void genratedPermiso(ActionEvent event) {

        GregorianCalendar g1 = new GregorianCalendar();
        SimpleDateFormat s1 = new SimpleDateFormat("dd/MM/yyyy");
        this.newRol.setUzatrolFechaIn(s1.format(g1.getTime()));
        this.newRol.setUzatrolFlag(BigDecimal.ONE);
        this.newRol.getId().setUzatfuncionarioId(this.newFuncionario.getUzatfuncionarioId());
        this.newRol.getUzatfunci().setUzatfuncionarioId(this.newFuncionario.getUzatfuncionarioId());
        this.newRol.getUzattrol().setUzattiporolId(this.newRol.getId().getUzattiporolId());
        Boolean exito = ProcuradoriaMethods.InsertRol(this.newRol);
        if (exito) {
            RequestContext.getCurrentInstance().execute("PF('dlgNewRespMSG').show();");
            this.init();
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
        processAsignarPermiso();
    }

    public void processAsignarPermiso() {
        Boolean exito = ProcuradoriaMethods.InserFuncionario(this.newFuncionario);
        if (exito) {
            findFuncionarioProcuaradoria(this.claveFuncionario);
        }
    }

    public void generateMessage(FacesMessage.Severity Tipo, String Header, String Mensaje) {
        FacesMessage message = new FacesMessage(Tipo, Header, Mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    private void loadlistRolesAsignados() {
        this.rolsAsignandos = ProcuradoriaMethods.GetFuncionariosTipoRolByFlag(BigDecimal.ONE);
    }

    private int getIdUserAssigned() {
        return 0;
    }

    public Uzatasign getNuevaAsiganacion() {
        return NuevaAsiganacion;
    }

    public void setNuevaAsiganacion(Uzatasign NuevaAsiganacion) {
        this.NuevaAsiganacion = NuevaAsiganacion;
    }

    public String getPatterFuncionario() {
        return patterFuncionario;
    }

    public void setPatterFuncionario(String patterFuncionario) {
        this.patterFuncionario = patterFuncionario;
    }

    public String getPatterRoles() {
        return patterRoles;
    }

    public void setPatterRoles(String patterRoles) {
        this.patterRoles = patterRoles;
    }

    public ArrayList<SelectItem> getItemsFuncionarios() {
        return ItemsFuncionarios;
    }

    public void setItemsFuncionarios(ArrayList<SelectItem> ItemsFuncionarios) {
        this.ItemsFuncionarios = ItemsFuncionarios;
    }

    public ArrayList<SelectItem> getItemsRoles() {
        return ItemsRoles;
    }

    public void setItemsRoles(ArrayList<SelectItem> ItemsRoles) {
        this.ItemsRoles = ItemsRoles;
    }

    public List<Uzatrol> getRolsAsignandos() {
        return rolsAsignandos;
    }

    public void setRolsAsignandos(List<Uzatrol> rolsAsignandos) {
        this.rolsAsignandos = rolsAsignandos;
    }

    public Uzatrol getSelectedRol() {
        return selectedRol;
    }

    public void setSelectedRol(Uzatrol selectedRol) {
        this.selectedRol = selectedRol;
    }

    public void onRowSelect(SelectEvent event) {
        this.selectedRol = (Uzatrol) event.getObject();
    }

    public void deletePermisoAginado() {
        this.rolsAsignandos.remove(this.selectedRol);
        this.selectedRol.setUzatrolFlag(BigDecimal.ZERO);
        ProcuradoriaMethods.UpdateRol(this.selectedRol);
        this.selectedRol = new Uzatrol();
    }

    public void EraseItem(ActionEvent event) {
        if (this.selectedRol != null) {
            this.rolsAsignandos.remove(this.selectedRol);
            this.selectedRol.setUzatrolFlag(BigDecimal.ZERO);
            ProcuradoriaMethods.UpdateRol(this.selectedRol);
            this.selectedRol = new Uzatrol();
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error", "Seleccione una fila."));
        }

    }

    public Uzatrol getNewRol() {
        return newRol;
    }

    public void setNewRol(Uzatrol newRol) {
        this.newRol = newRol;
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

}
