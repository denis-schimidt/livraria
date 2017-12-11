package com.schimidt.jsf.infra;

import com.schimidt.jsf.modelo.Usuario;

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
        Usuario usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");

        if (LOGIN_XHTML.equals(viewId)) {
            if (usuarioLogado == null) {
                return;
            } else {
                redirectoTo("livro", context);
            }
        }

        if (usuarioLogado == null) {
            redirectoTo("login", context);
        }
    }

    private void redirectoTo(String outcome, FacesContext context) {
        context.getApplication().getNavigationHandler()
                .handleNavigation(context, null, outcome + "?faces-redirect=true");
        context.renderResponse();
    }

    @Override
    public void beforePhase(PhaseEvent event) {
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}
