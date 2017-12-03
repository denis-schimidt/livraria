package com.schimidt.jsf.infra;

import java.util.Map;

public class RedirectView implements View {
    private static final String FACES_REDIRECT_ACTIVATED = "faces-redirect=true";
    private View view;

    public RedirectView(final String viewName) {
        this.view = new ForwardView(viewName);
    }

    public RedirectView(final String viewName, final Map<String, String> urlParams) {
        this.view = new ForwardView(viewName, urlParams);
    }

    @Override
    public String toString() {
        return new StringBuilder(view.toString())
                .append(view.hasQueryParameter() ? AMPERSAND : QUESTION_MARK)
                .append(FACES_REDIRECT_ACTIVATED)
                .toString();
    }

    @Override
    public boolean hasQueryParameter() {
        return view.hasQueryParameter();
    }
}
