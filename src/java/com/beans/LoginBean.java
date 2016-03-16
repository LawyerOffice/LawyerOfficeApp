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
    private String Usuario;
    private String Password;

    HttpServletRequest origRequest
            = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    private String urlRequest;

    public LoginBean() {
        this.urls_LawyerOffice();
        this.init();
    }

    private void init() {
        this.setPassword("");
        this.setUsuario("");
    }

    private void urls_LawyerOffice() {
        this.urlRequest = origRequest.getRequestURL().toString();
        this.setUrlRequest(this.urlRequest = this.urlRequest.replace("faces/views/abogados_procu.xhtml", ""));
        this.setUrlRequest(this.urlRequest = this.urlRequest.replace("faces/views/asignar_permiso.xhtml", ""));
        this.setUrlRequest(this.urlRequest = this.urlRequest.replace("faces/views/reasignar_caso.xhtml", ""));
        this.setUrlRequest(this.urlRequest = this.urlRequest.replace("faces/views/calendario_abo.xhtml", ""));

        this.setUrlRequest(this.urlRequest = this.urlRequest.replace("faces/views/calendario_procu.xhtml", ""));
        this.setUrlRequest(this.urlRequest = this.urlRequest.replace("faces/views/casos_abo.xhtml", ""));
        this.setUrlRequest(this.urlRequest = this.urlRequest.replace("faces/views/casos_procu.xhtml", ""));

        this.setUrlRequest(this.urlRequest = this.urlRequest.replace("faces/views/fases_caso.xhtml", ""));
        this.setUrlRequest(this.urlRequest = this.urlRequest.replace("faces/views/fases_caso_ui.xhtml", ""));
        this.setUrlRequest(this.urlRequest = this.urlRequest.replace("faces/views/generar_caso.xhtml", ""));

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

        Uzatrol usuario = ProcuradoriaMethods.FindByIdFunciByCedFunci(this.getUsuario(), this.getPassword(), BigDecimal.ONE, BigDecimal.ONE);
        if (usuario != null) {

            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
                    put("uzatfuncionarioId", usuario.getUzatfunci().getUzatfuncionarioId());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
                    put("uzatfuncionarioIdbanner", usuario.getUzatfunci().getUzatfuncionarioIdbanner());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
                    put("uzatfuncionarioCedula", usuario.getUzatfunci().getUzatfuncionarioCedula());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
                    put("uzatfuncionarioEmail", usuario.getUzatfunci().getUzatfuncionarioEmail());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                    .put("uzatfuncionarioNombres", usuario.getUzatfunci().getUzatfuncionarioNombres());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
                    put("uzatfuncionarioApellidos", usuario.getUzatfunci().getUzatfuncionarioApellidos());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
                    put("uzattiporolId", usuario.getUzattrol().getUzattiporolId());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
                    put("uzattiporolDescripcion", usuario.getUzattrol().getUzattiporolDescripcion());
            String tipoRol = usuario.getUzattrol().getUzattiporolDescripcion().trim();
            tipoRol = tipoRol.toUpperCase();

            if (tipoRol.equals("PROCURADOR")) {
                ruta = LawyerOfficeUtil.getURL_Login() + "views/resumen_procu.xhtml";
            } else if (tipoRol.equals("ABOGADO")) {
                ruta = LawyerOfficeUtil.getURL_Login() + "views/resumen_abo.xhtml";
            } else if (tipoRol.equals("SECRETARIA")) {
                ruta = LawyerOfficeUtil.getURL_Login() + "views/menu_secre.xhtml";
            }

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
            Object IdBanner = session.getAttribute("uzatfuncionarioId");
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
        this.Usuario = "";
        this.Password = "";
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

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String Usuario) {
        this.Usuario = Usuario;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

}
