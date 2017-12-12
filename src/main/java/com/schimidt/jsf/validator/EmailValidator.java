package com.schimidt.jsf.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.validator.ValidatorException;

public class EmailValidator {
    private final static String EMAIL_ID = "email";
    private final static EmailTester EMAIL_TESTER = new EmailTester();

    public static void validateEmailUsing(UIComponent uiComponent, Object value) throws ValidatorException {

        if (EMAIL_ID.equals(uiComponent.getId()) && value != null) {
            String email = (String) value;

            if (EMAIL_TESTER.isInvalid(email)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "E-mail inválido", "O e-mail deve ter @ com letras e " +
                        "números com até 50 caracteres."));
            }
        }
    }
}
