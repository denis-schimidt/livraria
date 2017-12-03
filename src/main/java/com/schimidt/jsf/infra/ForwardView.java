package com.schimidt.jsf.infra;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

import java.util.Collections;
import java.util.Map;

public class ForwardView implements View {
    private static final int INITIAL_VALUE_TO_BE_TESTED = 0;
    private static final Function<Integer, Integer> NEXT_VALUE_FUNCTION = counter -> counter + 1;
    private String viewName;
    private Map<String, String> urlParams;
    private CondicionalConcatenator<Integer> condicionalUrlConcatenator;

    public ForwardView(final String viewName) {
        this(viewName, Collections.EMPTY_MAP);
    }

    public ForwardView(final String viewName, final Map<String, String> urlParams) {
        this.viewName = viewName;
        this.urlParams = urlParams;

        Predicate<Integer> conditionToConcatenate = counter -> urlParams.keySet().size() >= counter && counter > 1;

        this.condicionalUrlConcatenator = CondicionalConcatenator.builder()
                .onCondition(conditionToConcatenate)
                .getNextValueToBeTested(NEXT_VALUE_FUNCTION)
                .concatenatingWith(AMPERSAND)
                .havingInitialValueToBeTested(INITIAL_VALUE_TO_BE_TESTED)
                .build();
    }

    @Override
    public String toString() {
        final StringBuilder urlBuilder = new StringBuilder(this.viewName);

        if(hasQueryParameter()){
            urlBuilder.append(QUESTION_MARK);
        }

        urlParams.entrySet()
            .forEach(entry -> {
                condicionalUrlConcatenator.concatenateStringDependingOnCondition(urlBuilder);
                urlBuilder.append(entry.getKey())
                    .append(EQUAL_SIGN)
                    .append(entry.getValue());
            });

        return urlBuilder.toString();
    }

    @Override
    public boolean hasQueryParameter() {
        return !urlParams.isEmpty();
    }
}
