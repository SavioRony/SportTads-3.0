package br.com.sporttads.enumeration;

import lombok.Data;

public enum StatusEnumeration {

    AGUARDANDO_CONFIRMACAO_PAGAMENTO(1, "Aguardando Confirmação do Pagamento"),
    PAGAMENTO_APROVADO(2, "Pagamento Aprovado"),
    PAGAMENTO_REJEITADO(3, "Pagamento rejeitado"),
    AGUARDANDO_RETIRADA(4, "Aguardando retirada"),
    EM_TRANSPORTE(5, "Em Transporte"),
    ENTREGUE(6, "Entregue");

    private int codigo;

    private String descricao;

    StatusEnumeration(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static String getDescricao(int codigo) {
        String retorno = "";
        for (StatusEnumeration e : StatusEnumeration.values()
        ) {
            if (e.codigo == codigo) {
                retorno = e.descricao;
            }
        }
        return retorno;
    }
}
