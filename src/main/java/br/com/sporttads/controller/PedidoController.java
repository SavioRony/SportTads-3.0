package br.com.sporttads.controller;

import br.com.sporttads.enumeration.StatusEnumeration;
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

    public CarrinhoModel carrinho = CarrinhoController.getCarrinho();

    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private FreteService freteService;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ItemPedidoService itemPedidoService;

    @Autowired
    private ItemCarrinhoService itemCarrinhoService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CartaoService cartaoService;

    @Autowired
    private ClienteService clienteService;

    /**
     * Metodo para abrir a tela do pedido
     * @param user
     * @return ModelAndView - Tela do pedido
     */
    @GetMapping()
    public ModelAndView mostrarTela(@AuthenticationPrincipal User user) {
        Endereco(user);
        //Se não possui frete selecionado no carrinho e adicionado o primeiro
        if(CarrinhoController.getCarrinho().getFrete() == null){
            CarrinhoController.getCarrinho().setFrete(freteService.findOne(1));
        }
        CarrinhoController.setFretes(this.freteService.findAll());
        for (FreteModel frete : CarrinhoController.getFretes()) {
            frete.setValorFrete(this.carrinho.getTotal() * frete.getTaxa());
        }
        return new ModelAndView("Pedido/Pedido");
    }

    /**
     * Alterar o endereço da sessão
     * @param id
     * @return String - tela do pedido com o novo endereço
     */
    @GetMapping("/endereco/{id}")
    public String alterarEndereco(@PathVariable int id) {
        this.endereco = enderecoService.getById(id);
        return "redirect:/pedido";
    }

    /**
     * Metodo para atualizar o frete e atualizar os dados da sessão conforme o novo frete
     * @param id
     * @param user
     * @return String - Tela do pedido
     */
    @GetMapping("/frete/{id}")
    public String frete(@PathVariable int id, @AuthenticationPrincipal User user) {
        FreteModel frete = freteService.findOne(id);
        this.carrinho.setFrete(frete);
        this.carrinho.setValorFrete(this.carrinho.getTotal() * this.carrinho.getFrete().getTaxa());
        CarrinhoController.setCarrinho(carrinhoService.salvaCarrinho(user, this.carrinho));
        CarrinhoController.setTotal(this.carrinho.getTotal() + this.carrinho.getValorFrete());
        return "redirect:/pedido";
    }

    /**
     * Metodo para abrir a tela de resumo do pedido
     * @return ModelAndView - Tela de resumo do pedido
     */
    @GetMapping("/resumo-pedido")
    public ModelAndView resumoPedido(){
        return new ModelAndView("Pedido/ResumoPedido");
    }

    /**
     * Metodo para finalizar o pedido por cartão de credito
     * @param cartao
     * @param user
     * @return ModelAndView - Tela de pedido finalizado
     */
    @PostMapping("/pag-cartao")
    public ModelAndView finalizarCartao(CartaoModel cartao, @AuthenticationPrincipal User user) {

        //Feito o processo de salvar no banco de dados o pedido e deletar o carrinho e os itens do carrinho do banco.
        ClienteModel cliente = clienteService.buscaPorEmailUser(user.getUsername());
        PedidoModel pedido = getPedido();
        pedido.setCliente(cliente);
        cartao.setPedido(pedido);
        cartaoService.save(cartao);
        pedido.setFormaPagamento(cartao.getFormaPagamento());
        pedidoService.save(pedido);
        for (ItemCarrinhoModel itemCarrinho : carrinho.getItens()){
            itemCarrinhoService.delete(itemCarrinho);
        }
        carrinhoService.delete(carrinho.getId());
        CarrinhoController.setFretes(new ArrayList<>());
        return new ModelAndView("Pedido/PedidoFinalizado", "pedido", pedido);
    }


    /**
     * Metodo para finalizar o pedido pelo pix ou boleto.
     * @param formaPagamento
     * @param user
     * @return ModelAndView - Tela de pedido finalizado
     */
    @PostMapping("/pag-pix-boleto")
    public ModelAndView finalizarPixBoleto(String formaPagamento, @AuthenticationPrincipal User user) {

        //Feito o processo de salvar no banco de dados o pedido e deletar o carrinho e os itens do carrinho do banco.
        ClienteModel cliente = clienteService.buscaPorEmailUser(user.getUsername());
        PedidoModel pedido = getPedido();
        pedido.setCliente(cliente);
        pedido.setFormaPagamento(formaPagamento);
        pedidoService.save(pedido);
        for (ItemCarrinhoModel itemCarrinho : carrinho.getItens()){
           itemCarrinhoService.delete(itemCarrinho);
        }
        carrinhoService.delete(carrinho.getId());
        CarrinhoController.setFretes(new ArrayList<>());
        return new ModelAndView("Pedido/PedidoFinalizado", "pedido", pedido);
    }

    /**
     * Metodo para abrir todos os pedidos do cliente logado.
     * @param user
     * @return ModelAndView - tela de meus pedidos
     */
    @GetMapping("/meus-pedidos")
    public ModelAndView meusPedidos(@AuthenticationPrincipal User user) {

        //Verifica se o cliente possui os dados cadastrados.
        if(user != null){
            ClienteModel cliente = clienteService.buscaPorEmailUser(user.getUsername());
            //Caso não tenho registro redireciona para tela de cadastro de cliente
            if(cliente.getId() == null){
                ModelAndView andView = new ModelAndView("cliente/cadastroCliente", "cliente", cliente);
                andView.addObject("email", user.getUsername());
                return andView;
            }
        }

        List<PedidoModel> pedidos = pedidoService.getAllUser(user);
        for (PedidoModel pedido : pedidos) {
            List<ItemPedidoModel> itens = itemPedidoService.getPedido(pedido);
            pedido.setItens(itens);
        }
        return new ModelAndView("Pedido/MeusPedidos", "pedidos", pedidos);
    }

    /**
     * Metodo para abrir todos os pedidos para o usuario estoquista gerenciar.
     * @return  ModelAndView - Tela de gerenciamento do pedido.
     */
    @GetMapping("estoquista/gerenciar-pedidos")
    public ModelAndView gerenciarPedidos() {
        List<PedidoModel> pedidos = this.pedidoService.getAllPedidos();
        return new ModelAndView("Pedido/GerenciarPedidos", "pedidos", pedidos);
    }

    /**
     * Metodo para filtra os pedidos por categoria.
     * @param statusId
     * @return ModelAndView - Tela de gerenciamento de pedidos.
     */
    @GetMapping("estoquista/gerenciar-pedidos/{statusId}")
    public ModelAndView gerenciarPedidosPorCategoria(@PathVariable int statusId) {
        List<PedidoModel> pedidos = this.pedidoService.getPedidosByStatus(statusId);
        return new ModelAndView("Pedido/GerenciarPedidos", "pedidos", pedidos);
    }

    /**
     * Metodo para salvar o novo status
     * @param idPedido
     * @param statusId
     * @return String - Tela de gerencia status
     */
    @GetMapping("estoquista/{idPedido}/alterar-status/{statusId}")
    public String gerenciarPedidos(@PathVariable int idPedido, @PathVariable int statusId) {
        PedidoModel pedido = this.pedidoService.getById(idPedido).get();
        pedido.setStatus(StatusEnumeration.getDescricao(statusId));
        this.pedidoService.save(pedido);
        return "redirect:/pedido/estoquista/gerenciar-pedidos";
    }

    /**
     * Metodo para abrir a tela de alterar o status
     * @param idPedido
     * @return ModelAndView - Alterar status
     */
    @GetMapping("estoquista/{idPedido}")
    public ModelAndView gerenciarPedidos(@PathVariable int idPedido) {
        PedidoModel pedido = this.pedidoService.getById(idPedido).get();
        return new ModelAndView("Pedido/AlterarStatus", "pedido", pedido);
    }

    /**
     * Metodo para caso não tenho nenhum endereço e setato o primeiro como padrão na sessão
     * e tambem quando e adicionado um novo endereço e adionado na lista da sessão.
     * @param user
     */
    public void Endereco(User user) {
        //Primeiro acesso
        if (endereco == null || endereco.getId() == null) {
            this.enderecos = enderecoService.getByClienteId(user);
            this.endereco = enderecos.get(0);
            EnderecoModel end = new EnderecoModel();
            end.setId(0);
            end.setNome("Selecione um endereço");
            this.enderecos.add(end);
        }

        //Novo endereço
        if (enderecoService.getByClienteId(user).size() >= enderecos.size()) {
            this.enderecos = enderecoService.getByClienteId(user);
            EnderecoModel end = new EnderecoModel();
            end.setId(0);
            end.setNome("Selecione um endereço");
            this.enderecos.add(end);
        }
    }

    /**
     * Metodo para preencher a informações do pedido antes de salvar no banco de dados.
     * @return PedidoModel
     */
    public PedidoModel getPedido() {
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
            //Alterar quantidade do estoque do produto
            ProdutoModel produto = itemCarrinho.getProduto();
            produto.setQuantidade(produto.getQuantidade() - itemCarrinho.getQuantidade());
            produtoService.save(produto);
        }
        itensPedido = itemPedidoService.saveAll(itensPedido);
        pedido.setItens(itensPedido);
        pedido.calcularTotal();
        return pedido;
    }
}
