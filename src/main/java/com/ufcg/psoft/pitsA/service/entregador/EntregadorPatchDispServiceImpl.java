package com.ufcg.psoft.pitsA.service.entregador;

import com.ufcg.psoft.pitsA.dto.entregador.EntregadorDispDTO;
import com.ufcg.psoft.pitsA.dto.entregador.EntregadorReadDTO;
import com.ufcg.psoft.pitsA.exception.entregador.EntregadorNaoExisteException;
import com.ufcg.psoft.pitsA.model.entregador.Entregador;
import com.ufcg.psoft.pitsA.repository.EntregadorRepository;
import com.ufcg.psoft.pitsA.service.auth.AutenticaCodigoAcessoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntregadorPatchDispServiceImpl implements EntregadorPatchDispService {
    @Autowired
    EntregadorRepository entregadorRepository;
    @Autowired
    AutenticaCodigoAcessoService autenticador;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public EntregadorReadDTO alteraDisponibilidadeEntrega(Long entregadorId, EntregadorDispDTO patchDTO) {
        String codigoAcesso = patchDTO.getCodigoAcesso();
        Entregador resultado = entregadorRepository.findById(entregadorId).orElseThrow(EntregadorNaoExisteException::new);

        autenticador.autenticar(resultado.getCodigoAcesso(), codigoAcesso);
        resultado.alteraDisponibilidade();

        System.out.println(resultado);
        return modelMapper.map(entregadorRepository.save(resultado), EntregadorReadDTO.class);
    }
}
