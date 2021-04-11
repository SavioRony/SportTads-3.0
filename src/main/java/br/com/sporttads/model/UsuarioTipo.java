package br.com.sporttads.model;

public enum UsuarioTipo {
    Administrador("Administrador"), Estoquista("Estoquista"), CLIENTE("Cliente");


    private String desc;

    private UsuarioTipo( String desc) {

        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
