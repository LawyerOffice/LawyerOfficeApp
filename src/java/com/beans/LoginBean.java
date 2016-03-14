/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import com.util.LawyerOfficeUtil;
import java.math.BigDecimal;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import procuradoria.crud.ProcuradoriaMethods;
import procuradoria.map.Uzatfunci;
import procuradoria.map.Uzatrol;

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
    private String mUsuario;
    private String mPassword;
    
    HttpServletRequest origRequest
            = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    private String urlRequest;

    public LoginBean() {
        this.urls_LawyerOffice();
        this.init();
    }
    
    private void init(){
        this.mPassword = "";
        this.mUsuario = "";
    }
    
    private void urls_LawyerOffice(){
        this.urlRequest = origRequest.getRequestURL().toString();
        this.setUrlRequest(this.urlRequest = this.urlRequest.replace("faces/views/abogados_procu.xhtml", ""));
        this.setUrlRequest(this.urlRequest = this.urlRequest.replace("faces/views/asignar_permiso.xhtml", ""));
        this.setUrlRequest(this.urlRequest = this.urlRequest.replace("faces/views/reasignar_caso.xhtml", ""));
        this.setUrlRequest(this.urlRequest = this.urlRequest.replace("faces/views/calendario_abo.xhtml", ""));

        this.setUrlRequest(this.urlRequest = this.urlRequest.replace("faces/views/calendario_procu.xhtml", ""));
        this.setUrlRequest(this.urlRequest = this.urlRequest.replace("faces/views/casos_abo.xhtml", ""));
        this.setUrlRequest(this.urlRequest = this.urlRequest.replace("faces/views/casos_procu.xhtml", ""));

        this.setUrlRequest(this.urlRequest = this.urlRequest.replace("faces/views/faces_caso.xhtml", ""));
        this.setUrlRequest(this.urlRequest = this.urlRequest.replace("faces/views/faces_caso_ui.xhtml", ""));
        this.setUrlRequest(this.urlRequest = this.urlRequest.replace("faces/views/generar_caso.xhtml", ""));

        this.setUrlRequest(this.urlRequest = this.urlRequest.replace("faces/views/inicio_abo.xhtml", ""));
        this.setUrlRequest(this.urlRequest = this.urlRequest.replace("faces/views/inicio_procu.xhtml", ""));
        this.setUrlRequest(this.urlRequest = this.urlRequest.replace("faces/views/menu_abo.xhtml", ""));

        this.setUrlRequest(this.urlRequest = this.urlRequest.replace("faces/views/menu_procu.xhtml", ""));
        this.setUrlRequest(this.urlRequest = this.urlRequest.replace("faces/views/resumen_abo.xhtml", ""));
        this.setUrlRequest(this.urlRequest = this.urlRequest.replace("faces/views/resumen_procu.xhtml", ""));

        this.setUrlRequest(this.urlRequest = this.urlRequest.replace("faces/resources/js/jquery.jsresources/", ""));
        this.setUrlRequest(this.urlRequest = this.urlRequest.replace("faces/index.xhtml", ""));
        this.setUrlRequest(this.urlRequest = this.urlRequest.replace("faces/loginPage", ""));
    }

    public void login(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        boolean LoggedIn = false;
        String ruta = "";
              
        Uzatrol usuario  = ProcuradoriaMethods.FindByIdFunciByCedFunci(this.mUsuario, this.mPassword, BigDecimal.ONE,BigDecimal.ONE);
        if(usuario != null){
            
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("uzatfuncionarioId",usuario.getUzatfunci().getUzatfuncionarioId());
            
            ruta = LawyerOfficeUtil.getURL_Login() + "views/resumen_abo.xhtml";
            
        }
        
        
        LoggedIn = true;
        context.addCallbackParam("loggedIn", LoggedIn);
        context.addCallbackParam("ruta", ruta);
    }

    private String getUserAttribute() {
        String UserAttribute = "";
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        if (session == null) {
        } else {
            Object IdBanner = session.getAttribute("IdBanner");
            UserAttribute = IdBanner.toString();
        }
        return UserAttribute;
    }

    public void logout(java.awt.event.ActionEvent event) {
        String ruta = LawyerOfficeUtil.getURL_Login() + "index.xhtml";
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        session.removeAttribute(this.getUserAttribute());
        session.invalidate();
        context.addCallbackParam("loggerOut", true);
        context.addCallbackParam("ruta", ruta);
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

    public String getmUsuario() {
        return mUsuario;
    }

    public void setmUsuario(String mUsuario) {
        this.mUsuario = mUsuario;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

}
