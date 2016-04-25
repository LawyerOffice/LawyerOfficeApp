/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.util;

import java.util.ArrayList;

/**
 *
 * @author Ivan
 */
public class Views {
    
    private ArrayList<String> views_abo;
    private ArrayList<String> views_procu;
    private ArrayList<String> views_secre;

    public Views() {
        this.views_abo = new ArrayList<String>();
        this.views_procu = new ArrayList<String>();
        this.views_secre = new ArrayList<String>();
        this.loadLists();
    }
    
    private void loadLists(){
        this.views_abo.add("faces/views/calendario_abo.xhtml");
        this.views_abo.add("faces/views/casos_abo.xhtml");
        this.views_abo.add("faces/views/fases_caso.xhtml");
        this.views_abo.add("faces/views/fases_caso_ui.xhtml");
        this.views_abo.add("faces/views/menu_abo.xhtml");
        this.views_abo.add("faces/views/resumen_abo.xhtml");
        this.views_abo.add("faces/views/ver_caso_abo.xhtml");
        this.views_abo.add("faces/views/ver_caso_procu.xhtml");
        this.views_abo.add("faces/index.xhtml");
        this.views_abo.add("faces/loginPage");
        
        this.views_procu.add("faces/views/abogados_procu.xhtml");
        this.views_procu.add("faces/views/asignar_permiso.xhtml");
        this.views_procu.add("faces/views/reasignar_caso.xhtml");
        this.views_procu.add("faces/views/reasignar_masiva.xhtml");
        this.views_procu.add("faces/views/calendario_procu.xhtml");
        this.views_procu.add("faces/views/casos_procu.xhtml");
        this.views_procu.add("faces/views/menu_procu.xhtml");
        this.views_procu.add("faces/views/resumen_procu.xhtml");
        this.views_procu.add("faces/views/generar_caso.xhtml");
        this.views_procu.add("faces/views/judi_procu.xhtml");
        this.views_procu.add("faces/views/ver_caso_procu.xhtml");
        this.views_procu.add("faces/index.xhtml");
        this.views_procu.add("faces/loginPage");
        
        this.views_secre.add("faces/views/menu_secre.xhtml");
        this.views_secre.add("faces/views/generar_caso_secre.xhtml");
        this.views_secre.add("faces/views/reasignar_caso_secre.xhtml");
        this.views_secre.add("faces/views/reasignar_masiva_secre.xhtml");
        this.views_secre.add("faces/views/calendario_secre.xhtml");
        this.views_secre.add("faces/views/judi_secre.xhtml");
        this.views_secre.add("faces/index.xhtml");
        this.views_secre.add("faces/loginPage");
    }
    public ArrayList<String> getViews_abo() {
        return views_abo;
    }

    public void setViews_abo(ArrayList<String> views_abo) {
        this.views_abo = views_abo;
    }

    public ArrayList<String> getViews_procu() {
        return views_procu;
    }

    public void setViews_procu(ArrayList<String> views_procu) {
        this.views_procu = views_procu;
    }

    public ArrayList<String> getViews_secre() {
        return views_secre;
    }

    public void setViews_secre(ArrayList<String> views_secre) {
        this.views_secre = views_secre;
    }
    
}
