package com.ufcg.psoft.pitsA.service.entregador;

import com.ufcg.psoft.pitsA.dto.EntregadorPostPutDTO;
import com.ufcg.psoft.pitsA.dto.EntregadorReadDTO;
import com.ufcg.psoft.pitsA.exception.CodigoAcessoInvalidoException;
import com.ufcg.psoft.pitsA.exception.EntregadorNaoExisteException;
import com.ufcg.psoft.pitsA.model.Entregador;
import com.ufcg.psoft.pitsA.repository.EntregadorRepository;
import com.ufcg.psoft.pitsA.service.auth.AutenticaCodigoAcessoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntregadorAtualizarServiceImpl implements EntregadorAtualizarService {
    @Autowired
    EntregadorRepository entregadorRepository;
    @Autowired
    AutenticaCodigoAcessoService autenticaEmpregadoService;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public EntregadorReadDTO atualizar(Long id, EntregadorPostPutDTO entregadorAtualizado) throws CodigoAcessoInvalidoException {
        Entregador entregador = entregadorRepository.findById(id).orElseThrow(EntregadorNaoExisteException::new);

        autenticaEmpregadoService.autenticar(entregador.getCodigoAcesso(), entregadorAtualizado.getCodigoAcesso());

        modelMapper.map(entregadorAtualizado, entregador);
        Entregador resultado = entregadorRepository.save(entregador);

        return modelMapper.map(resultado, EntregadorReadDTO.class);
    }
}
