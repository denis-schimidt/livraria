package com.schimidt.jsf.infra;

public interface View {
    final static String AMPERSAND = "&";
    final static String QUESTION_MARK = "?";
    final static String EQUAL_SIGN = "=";

    String toString();

    boolean hasQueryParameter();
}
