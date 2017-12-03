package com.schimidt.jsf.infra;

import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import static com.google.common.base.MoreObjects.toStringHelper;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class ForwardView {
    private final Function<Integer, Integer> NEXT_VALUE_FUNCTION = counter -> counter + 1;
    private String viewName;
    private Map<String, String> urlParams;
    private CondicionalConcatenator<Integer> condicionalUrlConcatenator;

    public ForwardView(final String viewName) {
        this(viewName, Collections.EMPTY_MAP);
    }

    public ForwardView(final String viewName, final Map<String, String> urlParams) {
        this.viewName = viewName;
        this.urlParams = urlParams;

        final Predicate<Integer> conditionToConcatenate = counter -> urlParams.keySet().size() >= counter && counter > 1;

        this.condicionalUrlConcatenator = CondicionalConcatenator.builder()
                .withCondition(conditionToConcatenate)
                .withExtractorOfNextValueToBeTested(NEXT_VALUE_FUNCTION)
                .withStringToConcatenate("&")
                .withValueToBeTested(0)
                .createCondicionalConcatenator();
    }

    @Override
    public String toString() {

        final MoreObjects.ToStringHelper urlBuilder = toStringHelper(this.viewName);

        urlParams.entrySet()
            .forEach(entry -> {
                condicionalUrlConcatenator.concatenateStringDependingOnCondition(urlBuilder);
                urlBuilder.add(entry.getKey(), entry.getValue());
            });

        return urlBuilder.toString();
    }
}
