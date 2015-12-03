/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.test;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Ivan
 */
@ManagedBean
@ViewScoped
public class LazyView {

    /**
     * Creates a new instance of LazyView
     */
    
    private List<Abogado> mAbogados;
    private Abogado selectedAbogado;
    
    public LazyView() {
        this.mAbogados = new ArrayList<Abogado>();
    }
    
    public void LoadData(){
        
    }

    public List<Abogado> getmAbogados() {
        return mAbogados;
    }

    public void setmAbogados(List<Abogado> mAbogados) {
        this.mAbogados = mAbogados;
    }

    public Abogado getSelectedAbogado() {
        return selectedAbogado;
    }

    public void setSelectedAbogado(Abogado selectedAbogado) {
        this.selectedAbogado = selectedAbogado;
    }
    
}
