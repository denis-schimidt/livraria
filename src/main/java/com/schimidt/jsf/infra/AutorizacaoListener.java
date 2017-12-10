package com.schimidt.jsf.infra;

import com.schimidt.jsf.modelo.Usuario;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class AutorizacaoListener implements PhaseListener {
    private static final String LOGIN_XHTML = "/login.xhtml";

    @Override
    public void afterPhase(PhaseEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        String viewId = context.getViewRoot().getViewId();

        if (LOGIN_XHTML.equals(viewId)) {
            return;
        }

        Usuario usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");

        if (usuarioLogado == null){
            NavigationHandler navigationHandler = context.getApplication().getNavigationHandler();
            navigationHandler.handleNavigation(context,null,"login?faces-redirect=true");
        }
    }

    @Override
    public void beforePhase(PhaseEvent event) {}

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}
