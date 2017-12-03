package com.schimidt.jsf.infra;

import java.util.Map;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class ForwardViewTest {

    private ForwardView forwardView = new ForwardView("livro", Map.of("id", "1", "autor", "fulano"));

    //@Test
    public void shouldGenerateUrlWithParameters () throws Exception {
        final String url = forwardView.toString();

        assertThat(url, equalTo("livro?id=1&autor=fulano"));
    }
}