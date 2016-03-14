/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.web;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ivan
 */
public class CacheControlPhaseListener implements PhaseListener {

    @Override
    public void afterPhase(PhaseEvent pe) {
        
    }

    @Override
    public void beforePhase(PhaseEvent pe) {
        FacesContext facesContext = pe.getFacesContext();
        HttpServletResponse response = (HttpServletResponse) facesContext
                .getExternalContext().getResponse();
        response.addHeader("RapidLoansDemo_v1_1", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
        // Stronger according to blog comment below that references HTTP spec
        response.addHeader("Cache-Control", "no-store");
        response.addHeader("Cache-Control", "must-revalidate");
        // some date in the past
        response.addHeader("Expires", "Mon, 8 Aug 2006 10:00:00 GMT");
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }
    
}
