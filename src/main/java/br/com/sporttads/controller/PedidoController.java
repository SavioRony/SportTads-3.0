package br.com.sporttads.controller;

import br.com.sporttads.model.CarrinhoModel;
import br.com.sporttads.model.EnderecoModel;
import br.com.sporttads.model.FreteModel;
import br.com.sporttads.service.CarrinhoService;
import br.com.sporttads.service.EnderecoService;
import br.com.sporttads.service.FreteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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

}
