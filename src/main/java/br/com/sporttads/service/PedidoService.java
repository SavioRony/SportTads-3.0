package br.com.sporttads.service;

import br.com.sporttads.model.PedidoModel;
import br.com.sporttads.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public PedidoModel save(PedidoModel pedido) {
        return pedidoRepository.save(pedido);
    }
}
