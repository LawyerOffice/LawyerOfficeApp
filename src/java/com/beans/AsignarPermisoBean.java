/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import java.math.BigDecimal;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.primefaces.model.NestedSelectItem;
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
    private Uztasignar  NuevaAsiganacion;
    
    private String patterFuncionario;
    private String patterRoles;
    
    private ArrayList<SelectItem> ItemsFuncionarios;
    private ArrayList<SelectItem> ItemsRoles;
    
    public AsignarPermisoBean() {
        this.init();
        if(this.NuevaAsiganacion == null){
            this.NuevaAsiganacion = new Uztasignar();
        }
    }
    
    private void init(){
        this.setNuevaAsiganacion(new Uztasignar());
        this.setItemsFuncionarios(new ArrayList<SelectItem>());
        this.setItemsRoles(new ArrayList<SelectItem>());
        this.setPatterFuncionario("");
        this.setPatterRoles("");
        this.loadlistFuncionarios();
        this.loadlistRoles();
    }

    public void loadlistFuncionarios() {
        ArrayList<Uztfuncionario> list2 = ProcuradoriaMethods.ListFuncionarios(BigDecimal.ONE);
        this.ItemsFuncionarios.clear();
        for(int i = 0 ; i < list2.size() ; i++){
            this.ItemsFuncionarios.add(new SelectItem(list2.get(i).getUztfuncionarioId(),list2.get(i).getUztfuncionarioApellidos()+
                    " "+list2.get(i).getUztfuncionarioNombres()));
        }

    }

    public void loadlistRoles() {      
        ArrayList<Uzttiporol>  list1 = ProcuradoriaMethods.ListTipoRol();
        this.ItemsRoles.clear();
        for(int i = 0 ;  i  < list1.size() ; i++){
            this.ItemsRoles.add(new SelectItem(list1.get(i).getUzttiporolId(), list1.get(i).getUzttiporolDescripcion()));
        }
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

}
