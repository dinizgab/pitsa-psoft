package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.EstabelecimentoDeleteDTO;
import com.ufcg.psoft.mercadofacil.exception.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoRemoverServiceImpl implements EstabelecimentoRemoverService {
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Override
    public void remover(Long id, EstabelecimentoDeleteDTO deleteDTO) throws Exception {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id).orElseThrow(EstabelecimentoNaoExisteException::new);

        if (deleteDTO.getCodigoAcesso().equals(estabelecimento.getCodigoAcesso())) throw new Exception();

        estabelecimentoRepository.deleteById(id);
    }
}
