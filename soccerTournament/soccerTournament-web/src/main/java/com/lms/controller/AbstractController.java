package com.lms.controller;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

public abstract class AbstractController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    protected transient FacesContext facesContext;

    protected String handleException(Exception e, String context, FacesContext facesContext) {
        String errorMessage = getRootErrorMessage(e);
        getLogger().log(Level.SEVERE, errorMessage, e);
        FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_FATAL, context, errorMessage);
        facesContext.addMessage(null, m);
        return "";
    }

    protected String getRootErrorMessage(Exception e) {
        String errorMessage = "Exception occurred.";
        if (e == null) {
            return errorMessage;
        }

        Throwable t = e;
        while (t != null) {
            errorMessage = t.getLocalizedMessage();
            t = t.getCause();
        }
        return errorMessage;
    }

    public void setFacesContext(FacesContext facesContext) {
        this.facesContext = facesContext;
    }

    public abstract Logger getLogger();

}
