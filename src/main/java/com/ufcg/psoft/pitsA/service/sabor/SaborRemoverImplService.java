package com.ufcg.psoft.pitsA.service.sabor;

import com.ufcg.psoft.pitsA.dto.sabor.SaborDeleteDTO;
import com.ufcg.psoft.pitsA.exception.sabor.SaborNaoExistenteException;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.model.Sabor;
import com.ufcg.psoft.pitsA.repository.SaborRepository;
import com.ufcg.psoft.pitsA.service.auth.AutenticaCodigoAcessoService;
import com.ufcg.psoft.pitsA.service.estabelecimento.EstabelecimentoListarService;
import com.ufcg.psoft.pitsA.service.estabelecimento.EstabelecimentoRemoveSaborService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaborRemoverImplService implements SaborRemoverService{
    @Autowired
    SaborRepository saborRepository;
    @Autowired
    EstabelecimentoListarService estabelecimentoListarService;
    @Autowired
    EstabelecimentoRemoveSaborService estabelecimentoRemoveSaborService;
    @Autowired
    AutenticaCodigoAcessoService autenticador;

    @Override
    public void remover(Long id, SaborDeleteDTO saborDeleteDTO) {
        Sabor sabor = saborRepository.findById(id).orElseThrow(SaborNaoExistenteException::new);
        Long estabelecimentoId = saborDeleteDTO.getEstabelecimentoId();

        Estabelecimento estabelecimento = estabelecimentoListarService.listar(estabelecimentoId).get(0);
        autenticador.autenticar(estabelecimento.getCodigoAcesso(), saborDeleteDTO.getCodigoAcesso());


        estabelecimentoRemoveSaborService.removeSabor(estabelecimentoId, sabor);
        saborRepository.deleteById(id);
    }

}
