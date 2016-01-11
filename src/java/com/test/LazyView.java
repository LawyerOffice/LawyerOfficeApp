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
import org.primefaces.event.SelectEvent;

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
    private List<Abogado> Abogados;
    private Abogado selectedAbogado;
    private List<Caso> Casos;
    private Caso selectedCaso;

    public List<Caso> getCasos() {
        return Casos;
    }

    public void setCasos(List<Caso> Casos) {
        this.Casos = Casos;
    }

    public LazyView() {
        this.Abogados = new ArrayList<Abogado>();
        this.selectedAbogado = new Abogado();
        this.Casos = new ArrayList<Caso>();
        this.selectedCaso = new Caso();
        this.LoadData();
    }

    public void LoadData() {

        
        Casos.add(new Caso("18", "04", "11", "04"));
        Casos.add(new Caso("18", "04", "11", "04"));
        Casos.add(new Caso("18", "04", "11", "04"));
        Casos.add(new Caso("18", "04", "11", "04"));
        Casos.add(new Caso("18", "04", "11", "04"));

        Abogado prueba = new Abogado(new DatosPersonales("LUIS IVAN", "ESPIN VELASCO", "180411", "24241854", "789*456*123"), "12", "12", "12");
        prueba.setCasos(Casos);

        this.Abogados.add(prueba);
        this.Abogados.add(prueba);
        this.Abogados.add(prueba);
        this.Abogados.add(prueba);
        this.Abogados.add(prueba);
        this.Abogados.add(prueba);
        this.Abogados.add(prueba);

        Casos.add(new Caso("05", "01", "82", "78"));
        Casos.add(new Caso("05", "01", "82", "78"));
        Casos.add(new Caso("05", "01", "82", "78"));
        Casos.add(new Caso("05", "01", "82", "78"));
        Casos.add(new Caso("05", "01", "82", "78"));
        Casos.add(new Caso("05", "01", "82", "78"));
        prueba = new Abogado(new DatosPersonales("SANCHO", "PANSA", "050182785", "2421854", "789*456*123"), "12", "12", "12");
        prueba.setCasos(Casos);
        this.Abogados.add(prueba);

    }

    public List<Abogado> getAbogados() {
        return Abogados;
    }

    public void setAbogados(List<Abogado> Abogados) {
        this.Abogados = Abogados;
    }

    public Caso getSelectedCaso() {
        return selectedCaso;
    }

    public void setSelectedCaso(Caso selectedCaso) {
        this.selectedCaso = selectedCaso;
    }

    public Abogado getSelectedAbogado() {
        return selectedAbogado;
    }

    public void setSelectedAbogado(Abogado selectedAbogado) {
        this.selectedAbogado = selectedAbogado;
    }

    public void onRowSelect(SelectEvent event) {
        this.selectedAbogado = (Abogado) event.getObject();
    }

    public void onRowSelectCaso(SelectEvent event) {
        this.selectedCaso = (Caso) event.getObject();
    }
    
    public void deleteCaso() {
        Casos.remove(selectedCaso);
        selectedCaso = null;
    }
}
