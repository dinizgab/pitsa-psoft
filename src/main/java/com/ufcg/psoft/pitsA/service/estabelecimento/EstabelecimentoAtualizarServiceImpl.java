package com.ufcg.psoft.pitsA.service.estabelecimento;

import com.ufcg.psoft.pitsA.dto.EstabelecimentoPutDTO;
import com.ufcg.psoft.pitsA.exception.estabelecimento.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.repository.EstabelecimentoRepository;
import com.ufcg.psoft.pitsA.service.auth.AutenticaCodigoAcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoAtualizarServiceImpl implements EstabelecimentoAtualizarService {
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    AutenticaCodigoAcessoService autenticador;

    @Override
    public Estabelecimento atualizar(Long id, EstabelecimentoPutDTO putBody) {
        Estabelecimento estabelecimentoAtualizado = estabelecimentoRepository.findById(id).orElseThrow(EstabelecimentoNaoExisteException::new);
        autenticador.autenticar(estabelecimentoAtualizado.getCodigoAcesso(), putBody.getCodigoAcesso());

        estabelecimentoAtualizado.setCodigoAcesso(putBody.getCodigoAcessoAlterado());
        return estabelecimentoRepository.save(estabelecimentoAtualizado);
    }
}
