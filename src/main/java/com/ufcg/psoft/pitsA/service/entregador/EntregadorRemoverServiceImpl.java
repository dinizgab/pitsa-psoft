package com.ufcg.psoft.pitsA.service.entregador;

import com.ufcg.psoft.pitsA.dto.entregador.EntregadorDeleteDTO;
import com.ufcg.psoft.pitsA.exception.entregador.EntregadorNaoExisteException;
import com.ufcg.psoft.pitsA.model.entregador.Entregador;
import com.ufcg.psoft.pitsA.repository.EntregadorRepository;
import com.ufcg.psoft.pitsA.service.auth.AutenticaCodigoAcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntregadorRemoverServiceImpl implements EntregadorRemoverService {
    @Autowired
    EntregadorRepository entregadorRepository;
    @Autowired
    AutenticaCodigoAcessoService autenticador;

    @Override
    public void remover(Long id, EntregadorDeleteDTO entregadorDeleteBody) {
        String codigoAcesso = entregadorDeleteBody.getCodigoAcesso();

        Entregador entregador = entregadorRepository.findById(id).orElseThrow(EntregadorNaoExisteException::new);
        autenticador.autenticar(entregador.getCodigoAcesso(), codigoAcesso);

        entregadorRepository.deleteById(id);
    }
}
