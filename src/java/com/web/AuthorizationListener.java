/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web;

import com.util.Views;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author LUISIVAN
 */
public class AuthorizationListener implements PhaseListener {

    @Override
    public void afterPhase(PhaseEvent event) {
        FacesContext facesContext = event.getFacesContext();
        String currentPage = facesContext.getViewRoot().getViewId();

        boolean isLoginPage = (currentPage.lastIndexOf("index.xhtml") > -1);
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

        if (session == null) {
            NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
            nh.handleNavigation(facesContext, null, "loginPage");
        } else {
            Object currentUser = session.getAttribute("uzatfuncionarioId");
            Object currentUserRol = session.getAttribute("uzattiporolDescripcion");

            if (!isLoginPage && (currentUser == null || currentUser == "")) {
                NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
                nh.handleNavigation(facesContext, null, "loginPage");
            } else if (!isLoginPage && currentUserRol != null) {

                String UserRol = currentUserRol.toString();
                UserRol = UserRol.toUpperCase();
                UserRol = UserRol.trim();
                HttpServletRequest origRequest
                        = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                String urlRequest = origRequest.getRequestURL().toString();
                urlRequest = urlRequest.replace("http://localhost:8081/LawyerOfficeApp/", "");
                urlRequest = urlRequest.replace("http://localhost:8080/LawyerOfficeApp/", "");
                urlRequest = urlRequest.replace("http://localhost:8084/LawyerOfficeApp/", "");

                if (currentUser != null && UserRol.equals("ABOGADO")
                        && !views_abo(urlRequest)) {
                    NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
                    nh.handleNavigation(facesContext, null, "menuAbo");
                }

                if (currentUser != null && UserRol.equals("PROCURADOR")
                        && !views_procu(urlRequest)) {
                    NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
                    nh.handleNavigation(facesContext, null, "menuProcu");
                }

                if (currentUser != null && UserRol.equals("SECRETARIA")
                        && !views_secretaria(urlRequest)) {
                    NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
                    nh.handleNavigation(facesContext, null, "menuSecre");
                }
            }
        }
    }

    public Boolean views_abo(String url) {
        Boolean exito = false;
        Views abo = new Views();
        for(String view_abo : abo.getViews_abo()){
            if(view_abo.equals(url)){
                exito = true;
                break;
            }
        }
        return exito;
    }

    public Boolean views_procu(String url) {
        Boolean exito = false;
        Views procu = new Views();
        for(String view_procu : procu.getViews_procu()){
            if(view_procu.equals(url)){
                exito = true;
                break;
            }
        }
        return exito;
    }

    public Boolean views_secretaria(String url) {
        Boolean exito = false;
        Views secre = new Views();
        for(String view_secre : secre.getViews_secre()){
            if(view_secre.equals(url)){
                exito = true;
                break;
            }
        }
        return exito;
    }

    @Override
    public void beforePhase(PhaseEvent pe) {

    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

}
