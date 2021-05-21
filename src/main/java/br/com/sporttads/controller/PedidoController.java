package br.com.sporttads.controller;

import br.com.sporttads.model.*;
import br.com.sporttads.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/pedido")
@Scope("session")
public class PedidoController {

    @Autowired
    private EnderecoService enderecoService;

    public List<EnderecoModel> enderecos = new ArrayList<>();

    public EnderecoModel endereco = new EnderecoModel();

    public  CarrinhoModel carrinho = CarrinhoController.getCarrinho();

    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private FreteService freteService;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ItemPedidoService itemPedidoService;

    @Autowired
    private CartaoService cartaoService;

    @Autowired
    private ClienteService clienteService;

    @GetMapping()
    public ModelAndView mostrarTela(@AuthenticationPrincipal User user) {
        getEndereco(user);
        return new ModelAndView("Pedido/ResumoCompra");
    }

    @GetMapping("/endereco/{id}")
    public String endereco(@PathVariable int id){
        this.endereco = enderecoService.getById(id);
        return "redirect:/pedido";
    }

    @GetMapping("/frete/{id}")
    public String frete(@PathVariable int id, @AuthenticationPrincipal User user){
        FreteModel frete = freteService.findOne(id);
        this.carrinho.setFrete(frete);
        this.carrinho.setValorFrete(this.carrinho.getTotal() * this.carrinho.getFrete().getTaxa());
        CarrinhoController.setCarrinho(carrinhoService.salvaCarrinho(user, this.carrinho));
        CarrinhoController.setTotal(this.carrinho.getTotal() + this.carrinho.getValorFrete());
        return "redirect:/pedido";
    }

    @PostMapping("/pag-cartao")
    public ModelAndView finalizarCartao(CartaoModel cartao,  @AuthenticationPrincipal User user){
        ClienteModel cliente = clienteService.buscaPorEmailUser(user.getUsername());
        PedidoModel pedido = getPedido();
        pedido.setCliente(cliente);
        cartao.setPedido(pedido);
        cartaoService.save(cartao);
        pedido.setFormaPagamento(cartao.getFormaPagamento());
        pedidoService.save(pedido);
        carrinhoService.deleteAll();
        return new ModelAndView("Pedido/FinalizarPedido", "pedido", pedido);
    }

    @PostMapping("/pag-pix-boleto")
    public ModelAndView finalizarPixBoleto(String formaPagamento, @AuthenticationPrincipal User user){
        ClienteModel cliente = clienteService.buscaPorEmailUser(user.getUsername());
        PedidoModel pedido = getPedido();
        pedido.setCliente(cliente);
        pedido.setFormaPagamento(formaPagamento);
        pedidoService.save(pedido);
        carrinhoService.deleteAll();
        return new ModelAndView("Pedido/FinalizarPedido", "pedido", pedido);
    }

    @GetMapping("/meus-pedidos")
    public ModelAndView meusPedidos(@AuthenticationPrincipal User user){
        List<PedidoModel> pedidos = pedidoService.getAll(user);
        return new ModelAndView("Pedido/PedidoCompra", "pedidos", pedidos);
    }


    public void getEndereco(User user){
        if(endereco == null || endereco.getId() == null){
            this.enderecos = enderecoService.getByClienteId(user);
            this.endereco = enderecos.get(0);
            EnderecoModel end = new EnderecoModel();
            end.setId(0);
            end.setNome("Selecione um endereço");
            this.enderecos.add(end);
        }

        //Novo endereço
        if(enderecoService.getByClienteId(user).size() >= enderecos.size()){
            this.enderecos = enderecoService.getByClienteId(user);
            EnderecoModel end = new EnderecoModel();
            end.setId(0);
            end.setNome("Selecione um endereço");
            this.enderecos.add(end);
        }
    }

    public PedidoModel getPedido(){
        CarrinhoModel carrinho = CarrinhoController.getCarrinho();
        PedidoModel pedido = new PedidoModel();
        pedido.setTotal(carrinho.getTotal());
        pedido.setQuantidadeTotal(carrinho.getQuantidadeTotal());
        pedido.setEndereco(endereco);
        pedido.setFrete(carrinho.getValorFrete());
        pedido = pedidoService.save(pedido);
        List<ItemPedidoModel> itensPedido = new ArrayList<>();
        for(ItemCarrinhoModel itemCarrinho : carrinho.getItens()){
            ItemPedidoModel itemPedido = new ItemPedidoModel();
            itemPedido.setProduto(itemCarrinho.getProduto());
            itemPedido.setQuantidade(itemCarrinho.getQuantidade());
            itemPedido.setSubtotal(itemCarrinho.getSubtotal());
            itemPedido.setPedido(pedido);
            itemPedido.setValorUnitario(itemCarrinho.getProduto().getPreco());
            itensPedido.add(itemPedido);
        }
        itensPedido = itemPedidoService.saveAll(itensPedido);
        pedido.setItens(itensPedido);
        pedido.calcularTotal();
        return pedido;
    }
}
