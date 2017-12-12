package com.schimidt.jsf.validator;

import org.hibernate.validator.internal.constraintvalidators.AbstractEmailValidator;

class EmailTester extends AbstractEmailValidator {

    boolean isInvalid(String email){
        return !isValid(email, null);
    }
}
