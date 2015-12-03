/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.test;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ivan
 */
public class NotificacionesService {
    
    private List<Notificaciones> Notifs;
    private String Detalle = "En los seguros de personas el asegurador, aun después de pagada la indemnización, no puede subrogarse en los derechos que en su caso correspondan al asegurado contra un tercero como consecuencia del siniestro. Se exceptúa de lo dispuesto en el párrafo anterior lo relativo a los gastos de asistencia sanitaria.";

    public NotificacionesService() {
        this.Notifs = new ArrayList<Notificaciones>();
        this.LoadData();
    }
    
    private void LoadData(){
        this.getNotifs().add(new Notificaciones("Esteban A", "Adminstrativo", Detalle));
        this.getNotifs().add(new Notificaciones("Juan B", "Losas", Detalle));
        this.getNotifs().add(new Notificaciones("Pedro C", "Civil", Detalle));
        this.getNotifs().add(new Notificaciones("Andres D", "Penal", Detalle));
        this.getNotifs().add(new Notificaciones("Carlos E", "Otros", Detalle));
        
        this.getNotifs().add(new Notificaciones("Esteban F", "Adminstrativo", Detalle));
        this.getNotifs().add(new Notificaciones("Juan G", "Losas", Detalle));
        this.getNotifs().add(new Notificaciones("Pedro H", "Civil", Detalle));
        this.getNotifs().add(new Notificaciones("Andres I", "Penal", Detalle));
        this.getNotifs().add(new Notificaciones("Carlos J", "Otros", Detalle));
        
        this.getNotifs().add(new Notificaciones("Esteban K", "Adminstrativo", Detalle));
        this.getNotifs().add(new Notificaciones("Juan L", "Losas", Detalle));
        this.getNotifs().add(new Notificaciones("Pedro M ", "Civil", Detalle));
        this.getNotifs().add(new Notificaciones("Andres N", "Penal", Detalle));
        this.getNotifs().add(new Notificaciones("Carlos O", "Otros", Detalle));
        
        this.getNotifs().add(new Notificaciones("Esteban A", "Adminstrativo", Detalle));
        this.getNotifs().add(new Notificaciones("Juan B", "Losas", Detalle));
        this.getNotifs().add(new Notificaciones("Pedro C", "Civil", Detalle));
        this.getNotifs().add(new Notificaciones("Andres D", "Penal", Detalle));
        this.getNotifs().add(new Notificaciones("Carlos E", "Otros", Detalle));
        
        this.getNotifs().add(new Notificaciones("Esteban F", "Adminstrativo", Detalle));
        this.getNotifs().add(new Notificaciones("Juan G", "Losas", Detalle));
        this.getNotifs().add(new Notificaciones("Pedro H", "Civil", Detalle));
        this.getNotifs().add(new Notificaciones("Andres I", "Penal", Detalle));
        this.getNotifs().add(new Notificaciones("Carlos J", "Otros", Detalle));
        
        this.getNotifs().add(new Notificaciones("Esteban K", "Adminstrativo", Detalle));
        this.getNotifs().add(new Notificaciones("Juan L", "Losas", Detalle));
        this.getNotifs().add(new Notificaciones("Pedro M ", "Civil", Detalle));
        this.getNotifs().add(new Notificaciones("Andres N", "Penal", Detalle));
        this.getNotifs().add(new Notificaciones("Carlos O", "Otros", Detalle));
    }

    public List<Notificaciones> getNotifs() {
        return Notifs;
    }

    public void setNotifs(List<Notificaciones> Notifs) {
        this.Notifs = Notifs;
    }
    
    
    
}
