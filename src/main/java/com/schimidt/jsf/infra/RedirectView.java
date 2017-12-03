package com.schimidt.jsf.infra;

public class RedirectView {
    private String viewName;

    public RedirectView(final String viewName) {
        this.viewName = viewName;
    }

    @Override
    public String toString() {
        return viewName + "?faces-redirect=true";
    }
}
