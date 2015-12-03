/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.test;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Ivan
 */
@ManagedBean
@ViewScoped
public class DataScrollerView{

    /**
     * Creates a new instance of DataScrollerView
     */
    private NotificacionesService NotiService;
    
    public DataScrollerView() {
        this.NotiService = new NotificacionesService();
    }

    public NotificacionesService getNotiService() {
        return NotiService;
    }

    public void setNotiService(NotificacionesService NotiService) {
        this.NotiService = NotiService;
    }
    
}
