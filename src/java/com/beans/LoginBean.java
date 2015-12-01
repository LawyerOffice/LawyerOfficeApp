/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import com.util.LawyerOfficeUtil;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author IVAN
 */
@ManagedBean
@SessionScoped
public class LoginBean {

    /**
     * Creates a new instance of LoginBean
     */
    HttpServletRequest origRequest
            = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    private String urlRequest;

    public LoginBean() {
        this.urlRequest = origRequest.getRequestURL().toString();
//        this.setUrlRequest(this.urlRequest = this.urlRequest.replace("faces/views/u_cliente.xhtml", ""));
//        this.setUrlRequest(this.urlRequest = this.urlRequest.replace("faces/views/r_cliente.xhtml", ""));
//        this.setUrlRequest(this.urlRequest = this.urlRequest.replace("faces/views/inicio.xhtml", ""));
//        this.setUrlRequest(this.urlRequest = this.urlRequest.replace("faces/resources/js/jquery.jsresources/", ""));
//        this.setUrlRequest(this.urlRequest = this.urlRequest.replace("faces/index.xhtml", ""));
//        this.setUrlRequest(this.urlRequest = this.urlRequest.replace("faces/loginPage", ""));
    }

    public void login(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean loggedIn =false;
        String ruta = "";
        String rutaDemo = LawyerOfficeUtil.getURL_Login()+"views/inicio_procu.xhtml";
        loggedIn = true;
        context.addCallbackParam("loggedIn", loggedIn);
        context.addCallbackParam("ruta", rutaDemo);
    }

    public void logout(java.awt.event.ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        session.invalidate();
        context.addCallbackParam("loggerOut", true);
    }

    public void generateMessage(FacesMessage.Severity Tipo, String Header, String Mensaje) {
        FacesMessage message = new FacesMessage(Tipo, Header, Mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String getUrlRequest() {
        return urlRequest;
    }

    public void setUrlRequest(String urlRequest) {
        this.urlRequest = urlRequest;
    }

}
