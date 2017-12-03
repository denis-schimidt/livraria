package com.schimidt.jsf.infra;

import com.google.common.base.MoreObjects;
import java.util.function.Function;
import java.util.function.Predicate;

class CondicionalConcatenator<T> {
    private final Predicate<T> condition;
    private final String stringToConcatenate;
    private final Function<T, T> extractorOfNextValueToBeTested;
    private T valueToBeTested;

    private CondicionalConcatenator(Builder<T> builder) {
        this.condition = builder.condition;
        this.stringToConcatenate = builder.stringToConcatenate;
        this.extractorOfNextValueToBeTested = builder.extractorOfNextValueToBeTested;
        this.valueToBeTested = builder.valueToBeTested;
    }

    void concatenateStringDependingOnCondition(final MoreObjects.ToStringHelper resultHelper) {

        valueToBeTested = extractorOfNextValueToBeTested.apply(valueToBeTested);

        if (condition.test(valueToBeTested)) {
            resultHelper.addValue(stringToConcatenate);
        }
    }
    
    static Builder builder(){
        return new Builder();
    }

    static class Builder<T> {
        private Predicate<T> condition;
        private Function<T, T> extractorOfNextValueToBeTested;
        private String stringToConcatenate;
        private T valueToBeTested;

        Builder withCondition(final Predicate<T> condition) {
            this.condition = condition;
            return this;
        }

        Builder withExtractorOfNextValueToBeTested(final Function<T, T> extractorOfNextValueToBeTested) {
            this.extractorOfNextValueToBeTested = extractorOfNextValueToBeTested;
            return this;
        }

        Builder withStringToConcatenate(final String stringToConcatenate) {
            this.stringToConcatenate = stringToConcatenate;
            return this;
        }

        Builder withValueToBeTested(final T valueToBeTested) {
            this.valueToBeTested = valueToBeTested;
            return this;
        }

        CondicionalConcatenator createCondicionalConcatenator() {
            return new CondicionalConcatenator<>(this);
        }
    }
}
