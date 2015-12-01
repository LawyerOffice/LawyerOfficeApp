/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.beans;

import com.util.LawyerOfficeUtil;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;

/**
 *
 * @author IVAN
 */
@ManagedBean
@ApplicationScoped
public class AppBean {

    /**
     * Creates a new instance of AppBean
     */
    public AppBean() {
    }
    
    public String getURL_Menu(){
        return LawyerOfficeUtil.getURL_Menu();
    }
    
}
