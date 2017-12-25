package com.schimidt.jsf.bean;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class TemaBean implements Serializable {
    private static final long serialVersionUID = 1782238298631147537L;
    private static final List<String> TODOS_TEMAS = List.of("afterdark","afternoon","afterwork","aristo","black-tie","blitzer","bluesky",
            "bootstrap","casablanca","cupertino","cruze","dark-hive", "delta","dot-luv","eggplant","excite-bike","flick","glass-x","home",
            "hot-sneaks", "humanity","le-frog","midnight","mint-choc","omega","overcast","pepper-grinder","redmond","rocket","sam","smoothness",
            "south-street","start","sunny","swanky-purse","trontastic","ui-darkness","ui-lightness","vader");

    private String temaEscolhido = "bootstrap";

    public String getTemaEscolhido() {
        return temaEscolhido;
    }

    public void setTemaEscolhido(String temaEscolhido) {
        this.temaEscolhido = temaEscolhido;
    }

    public List<String> listarTemas() {
        return TODOS_TEMAS;
    }
}
