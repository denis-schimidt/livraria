package com.schimidt.jsf.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.List;

@ManagedBean
@SessionScoped
public class TemaBean {
    private final static List<String> TODOS_TEMAS = List.of("afterdark","afternoon","afterwork","aristo","black-tie","blitzer","bluesky",
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
