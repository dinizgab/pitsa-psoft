package com.ufcg.psoft.pitsA.service.entregador;

import com.ufcg.psoft.pitsA.dto.entregador.EntregadorPostPutDTO;
import com.ufcg.psoft.pitsA.dto.entregador.EntregadorReadDTO;
import com.ufcg.psoft.pitsA.exception.auth.CodigoAcessoInvalidoException;
import com.ufcg.psoft.pitsA.exception.entregador.EntregadorNaoExisteException;
import com.ufcg.psoft.pitsA.model.entregador.Entregador;
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
