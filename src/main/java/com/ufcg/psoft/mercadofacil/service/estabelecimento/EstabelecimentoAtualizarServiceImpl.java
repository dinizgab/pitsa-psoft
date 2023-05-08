package com.ufcg.psoft.mercadofacil.service.estabelecimento;

import com.ufcg.psoft.mercadofacil.dto.EstabelecimentoPutPostDTO;
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
    public Estabelecimento atualizar(Long id, EstabelecimentoPutPostDTO estabelecimentoPutPostDTO) {
        Estabelecimento estabelecimentoAtualizado = estabelecimentoRepository.findById(id).orElseThrow(EstabelecimentoNaoExisteException::new);

        modelMapper.map(estabelecimentoPutPostDTO, estabelecimentoAtualizado);

        return estabelecimentoRepository.save(estabelecimentoAtualizado);
    }
}
