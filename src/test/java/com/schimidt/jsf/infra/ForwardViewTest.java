package com.schimidt.jsf.infra;

import java.util.LinkedHashMap;
import java.util.Map;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class ForwardViewTest {

    private View view;

    @Before
    public void setUp(){
        Map<String, String> urlParameters = new LinkedHashMap();
        urlParameters.put("id", "1");
        urlParameters.put("autor", "fulano");

        view = new ForwardView("livro", urlParameters);
    }

    @Test
    public void shouldGenerateUrlWithParameters () throws Exception {
        final String url = view.toString();

        assertThat(url, equalTo("livro?id=1&autor=fulano"));
    }
}