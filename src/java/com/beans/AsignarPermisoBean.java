/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import java.math.BigDecimal;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
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
    private Uztasignar NuevaAsiganacion;

    private String patterFuncionario;
    private String patterRoles;

    private ArrayList<SelectItem> ItemsFuncionarios;
    private ArrayList<SelectItem> ItemsRoles;

    private List<Uztrol> rolsAsignandos;
    private Uztrol selectedRol;

    public AsignarPermisoBean() {
        this.init();
        if (this.NuevaAsiganacion == null) {
            this.NuevaAsiganacion = new Uztasignar();
        }
    }

    private void init() {
        this.setNuevaAsiganacion(new Uztasignar());
        this.setItemsFuncionarios(new ArrayList<SelectItem>());
        this.setItemsRoles(new ArrayList<SelectItem>());
        this.setPatterFuncionario("");
        this.setPatterRoles("");
        this.rolsAsignandos = new ArrayList<Uztrol>();
        this.selectedRol = new Uztrol();
        this.loadlistFuncionarios();
        this.loadlistRoles();
        this.loadlistRolesAsignados();
    }

    public void loadlistFuncionarios() {
        ArrayList<Uztfuncionario> list2 = ProcuradoriaMethods.ListFuncionarios(BigDecimal.ONE);
        this.ItemsFuncionarios.clear();
        for (int i = 0; i < list2.size(); i++) {
            this.ItemsFuncionarios.add(new SelectItem(list2.get(i).getUztfuncionarioId(), list2.get(i).getUztfuncionarioApellidos()
                    + " " + list2.get(i).getUztfuncionarioNombres()));
        }

    }

    public void loadlistRoles() {
        ArrayList<Uzttiporol> list1 = ProcuradoriaMethods.ListTipoRol();
        this.ItemsRoles.clear();
        for (int i = 0; i < list1.size(); i++) {
            this.ItemsRoles.add(new SelectItem(list1.get(i).getUzttiporolId(), list1.get(i).getUzttiporolDescripcion()));
        }
    }

    public void loadlistRolesAsignados() {
        this.rolsAsignandos = ProcuradoriaMethods.GetFuncionariosTipoRolByFlag(BigDecimal.ONE);
    }

    private int getIdUserAssigned() {
        return 0;
    }

    public Uztasignar getNuevaAsiganacion() {
        return NuevaAsiganacion;
    }

    public void setNuevaAsiganacion(Uztasignar NuevaAsiganacion) {
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

    public List<Uztrol> getRolsAsignandos() {
        return rolsAsignandos;
    }

    public void setRolsAsignandos(List<Uztrol> rolsAsignandos) {
        this.rolsAsignandos = rolsAsignandos;
    }

    public Uztrol getSelectedRol() {
        return selectedRol;
    }

    public void setSelectedRol(Uztrol selectedRol) {
        this.selectedRol = selectedRol;
    }

    public void onRowSelect(SelectEvent event) {
        this.selectedRol = (Uztrol) event.getObject();
    }

    public void deletePermisoAginado() {
        this.rolsAsignandos.remove(this.selectedRol);
        this.selectedRol.setUztrolFlag(BigDecimal.ZERO);
        ProcuradoriaMethods.UpdateRol(this.selectedRol);
        this.selectedRol = new Uztrol();
    }

    public void EraseItem(ActionEvent event) {
        if (this.selectedRol != null) {
            this.rolsAsignandos.remove(this.selectedRol);
            this.selectedRol.setUztrolFlag(BigDecimal.ZERO);
            ProcuradoriaMethods.UpdateRol(this.selectedRol);
            this.selectedRol = new Uztrol();
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error", "Seleccione una fila."));
        }

    }

}
