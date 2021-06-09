package br.com.sporttads.service;

import br.com.sporttads.enumeration.StatusEnumeration;
import br.com.sporttads.model.ClienteModel;
import br.com.sporttads.model.PedidoModel;
import br.com.sporttads.model.UsuarioModel;
import br.com.sporttads.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ClienteService clienteService;

    public PedidoModel save(PedidoModel pedido) {
        return pedidoRepository.save(pedido);
    }

    public PedidoModel save(PedidoModel pedido, User user) {
        PedidoModel p =  pedidoRepository.save(pedido);
        try {
            emailService.pedidoFinalizado(user.getUsername(),p.getId());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return p;
    }

    public PedidoModel saveStatus(PedidoModel pedido, User user) {
        PedidoModel p = pedidoRepository.save(pedido);

        try {
            emailService.statusPedido(p.getCliente().getUsuario().getEmail(),p.getId(),p.getStatus());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return pedidoRepository.save(pedido);
    }

    public List<PedidoModel> getAllUser(User user) {
        ClienteModel cliente = clienteService.buscaPorEmailUser(user.getUsername());
        return pedidoRepository.findAllByClienteOrderByIdDesc(cliente).orElse(new ArrayList<>());
    }

    public List<PedidoModel> getAllPedidos() {
        return this.pedidoRepository.findAllByOrderByIdDesc().orElse(new ArrayList<>());
    }

    public List<PedidoModel> getPedidosByStatus(int codigo) {
        String status = StatusEnumeration.getDescricao(codigo);
        return this.pedidoRepository.findByStatusOrderByIdDesc(status).orElse(new ArrayList<>());
    }

    public Optional<PedidoModel> getById(int id) {
        return this.pedidoRepository.findById(id);
    }
}
