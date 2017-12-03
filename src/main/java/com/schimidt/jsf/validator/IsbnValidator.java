package com.schimidt.jsf.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

public class IsbnValidator {

    public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException{

        if (value instanceof String && value != null) {
            String valor = (String) value;
            if (!valor.startsWith("1")) {

                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "ISBN inválido", "O ISBN deve iniciar com 1"));
            }
        }
    }
}
