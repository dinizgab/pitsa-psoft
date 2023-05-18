package com.ufcg.psoft.pitsA.service.sabor;

import com.ufcg.psoft.pitsA.dto.sabor.SaborDeleteDTO;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.repository.SaborRepository;
import com.ufcg.psoft.pitsA.service.auth.AutenticaCodigoAcessoService;
import com.ufcg.psoft.pitsA.service.estabelecimento.EstabelecimentoListarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaborRemoverImplService implements SaborRemoverService{
    @Autowired
    SaborRepository saborRepository;
    @Autowired
    EstabelecimentoListarService estabelecimentoListarService;
    @Autowired
    AutenticaCodigoAcessoService autenticador;

    @Override
    public void remover(Long id, SaborDeleteDTO saborDeleteDTO) {
        Estabelecimento estabelecimento = estabelecimentoListarService.listar(saborDeleteDTO.getEstabelecimentoId()).get(0);
        autenticador.autenticar(estabelecimento.getCodigoAcesso(), saborDeleteDTO.getCodigoAcesso());

        saborRepository.deleteById(id);
    }

}
