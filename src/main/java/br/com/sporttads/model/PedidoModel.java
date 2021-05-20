package br.com.sporttads.model;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "tb_pedido")
@Data
public class PedidoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private int id;

    @OneToMany(mappedBy = "pedido")
    private List<ItemPedidoModel> itens;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_cliente")
    private ClienteModel cliente;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_endereco")
    private EnderecoModel endereco;

    @OneToOne(mappedBy = "pedido", fetch = FetchType.EAGER)
    private CartaoModel cartao;

    private double total = 0;

    @NotNull
    private double totalFinal = 0;

    private double frete = 0;

    private String formaPagamento;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dataHora = LocalDateTime.now();

    private int quantidadeTotal = 0;

    private String status = "Aguardando Confirmação do Pagamento";

    public void calcularTotal() {
        this.total = 0;
        for (ItemPedidoModel item : this.itens) {
            this.total += item.getSubtotal();
        }
        quantidadeTotal = this.itens.size();
        this.totalFinal = this.total + this.frete;
    }

}