package com.ufcg.psoft.pitsA.service.sabor;

import com.ufcg.psoft.pitsA.dto.sabor.SaborReadDTO;
import com.ufcg.psoft.pitsA.dto.sabor.SaborPutDTO;
import com.ufcg.psoft.pitsA.exception.sabor.SaborNaoExistenteException;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.model.Sabor;
import com.ufcg.psoft.pitsA.repository.SaborRepository;
import com.ufcg.psoft.pitsA.service.auth.AutenticaCodigoAcessoService;
import com.ufcg.psoft.pitsA.service.estabelecimento.EstabelecimentoAtualizaSaborService;
import com.ufcg.psoft.pitsA.service.estabelecimento.EstabelecimentoListarService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SaborUpdateImplService implements SaborUpdateService {

   @Autowired
   SaborRepository saborRepository;
   @Autowired
   EstabelecimentoListarService estabelecimentoListarService;
   @Autowired
   EstabelecimentoAtualizaSaborService estabelecimentoAtualizaSaborService;
   @Autowired
   AutenticaCodigoAcessoService autenticador;
   @Autowired
   ModelMapper modelMapper;

    @Override
    public SaborReadDTO update(Long saborId, SaborPutDTO saborDTO) {
        Estabelecimento estabelecimento = estabelecimentoListarService.listar(saborDTO.getEstabelecimentoId()).get(0);
        autenticador.autenticar(estabelecimento.getCodigoAcesso(), saborDTO.getCodigoAcesso());

        Optional<Sabor> maybeSabor = saborRepository.findById(saborId);
        if (!maybeSabor.isPresent()) throw new SaborNaoExistenteException();
        Sabor sabor = maybeSabor.get();

        estabelecimentoAtualizaSaborService.atualizaSabor(saborDTO.getEstabelecimentoId(), sabor);

        modelMapper.map(saborDTO, sabor);
        Sabor saborSalvo = saborRepository.save(sabor);

        return modelMapper.map(saborSalvo, SaborReadDTO.class);
    }
}
