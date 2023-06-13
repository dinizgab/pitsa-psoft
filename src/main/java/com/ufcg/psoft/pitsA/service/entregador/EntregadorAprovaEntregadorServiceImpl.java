package com.ufcg.psoft.pitsA.service.entregador;

import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.model.entregador.Entregador;
import com.ufcg.psoft.pitsA.repository.EntregadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntregadorAprovaEntregadorServiceImpl implements EntregadorAprovaEntregadorService {
    @Autowired
    EntregadorRepository entregadorRepository;

    public void aprovar(Entregador entregador, Estabelecimento estabelecimento) {
        entregador.setEstabelecimento(estabelecimento);


        this.entregadorRepository.save(entregador);
    }
}
