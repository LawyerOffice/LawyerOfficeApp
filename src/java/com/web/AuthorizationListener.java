/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web;

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

        HttpServletRequest origRequest
                = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String urlRequest = origRequest.getRequestURL().toString();
        if (session == null) {
            NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
            nh.handleNavigation(facesContext, null, "loginPage");
        } else {
            Object currentUser = session.getAttribute("uzatfuncionarioId");
            Object currentUserRol = session.getAttribute("uzatfuncionarioId");
            String UserRol = currentUserRol.toString().trim();
            UserRol = UserRol.toUpperCase();

            if (!isLoginPage && (currentUser == null || currentUser == "")) {
                NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
                nh.handleNavigation(facesContext, null, "loginPage");
            }

            if (isLoginPage && currentUser != null && UserRol.equals("ABOGADO")) {
                NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
                nh.handleNavigation(facesContext, null, "loginPage");
            }
            
            if (isLoginPage && currentUser != null && UserRol.equals("PROCURADOR")) {
                NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
                nh.handleNavigation(facesContext, null, "loginPage");
            }
            
            if (isLoginPage && currentUser != null && UserRol.equals("SECRETARIA")) {
                NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
                nh.handleNavigation(facesContext, null, "loginPage");
            }
        }
    }

    public Boolean views_abo() {
        Boolean exito = false;
        return exito;
    }
    
    public Boolean views_procu() {
        Boolean exito = false;
        return exito;
    }
    
    public Boolean views_secretaria() {
        Boolean exito = false;
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
