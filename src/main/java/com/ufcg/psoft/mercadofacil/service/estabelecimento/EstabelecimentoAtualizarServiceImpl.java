package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.EstabelecimentoPostPutDTO;
import com.ufcg.psoft.mercadofacil.exception.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.mercadofacil.model.Estabelecimento;
import com.ufcg.psoft.mercadofacil.repository.EstabelecimentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoAtualizarServiceImpl implements EstabelecimentoAtualizarService {
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public Estabelecimento atualizar(Long id, EstabelecimentoPostPutDTO putBody) {
        Estabelecimento estabelecimentoAtualizado = estabelecimentoRepository.findById(id).orElseThrow(EstabelecimentoNaoExisteException::new);
        if (estabelecimentoAtualizado.getCodigoAcesso().equals(putBody.getCodigoAcesso())) throw new Exception();


        modelMapper.map(putBody, estabelecimentoAtualizado);

        return estabelecimentoRepository.save(estabelecimentoAtualizado);
    }
}
