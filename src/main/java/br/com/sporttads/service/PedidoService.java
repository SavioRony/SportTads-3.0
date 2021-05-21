package br.com.sporttads.service;

import br.com.sporttads.model.ClienteModel;
import br.com.sporttads.model.PedidoModel;
import br.com.sporttads.repository.ClienteRepository;
import br.com.sporttads.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteService clienteService;

    public PedidoModel save(PedidoModel pedido) {
        return pedidoRepository.save(pedido);
    }

    public List<PedidoModel> getAll(User user) {
        ClienteModel cliente = clienteService.buscaPorEmailUser(user.getUsername());
        return pedidoRepository.findAllByClienteOrderByIdDesc(cliente).orElse(new ArrayList<>());
    }
}
