package com.schimidt.jsf.infra;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

class JsfProducer {

    @Produces
    @RequestScoped
    public FacesContext getFacesContext(){
        return FacesContext.getCurrentInstance();
    }

    @Produces
    @RequestScoped
    public ExternalContext getExternalContext(){
        return FacesContext.getCurrentInstance().getExternalContext();
    }
}
