package com.ufcg.psoft.pitsA.service.sabor;

import com.ufcg.psoft.pitsA.dto.sabor.SaborDTO;
import com.ufcg.psoft.pitsA.dto.sabor.SaborPostDTO;
import com.ufcg.psoft.pitsA.dto.sabor.SaborPutDTO;
import com.ufcg.psoft.pitsA.model.Estabelecimento;
import com.ufcg.psoft.pitsA.model.Sabor;
import com.ufcg.psoft.pitsA.repository.SaborRepository;
import com.ufcg.psoft.pitsA.service.auth.AutenticaCodigoAcessoService;
import com.ufcg.psoft.pitsA.service.estabelecimento.EstabelecimentoListarService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaborUpdateImplService implements SaborUpdateService {

   @Autowired
   SaborRepository saborRepository;
   @Autowired
   EstabelecimentoListarService estabelecimentoListarService;
   @Autowired
   AutenticaCodigoAcessoService autenticador;
   @Autowired
   private ModelMapper modelMapper;

    @Override
    public SaborDTO update(Long saborId, SaborPutDTO saborDTO) {
        Estabelecimento estabelecimento = estabelecimentoListarService.listar(saborDTO.getEstabelecimentoId()).get(0);
        autenticador.autenticar(estabelecimento.getCodigoAcesso(), saborDTO.getCodigoAcesso());

        Sabor sabor = saborRepository.findById(saborId).get();

        modelMapper.map(saborDTO, sabor);
        Sabor saborSalvo = saborRepository.save(sabor);

        return modelMapper.map(saborSalvo, SaborDTO.class);
    }
}
