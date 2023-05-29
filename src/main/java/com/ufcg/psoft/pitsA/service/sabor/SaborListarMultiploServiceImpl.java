package com.ufcg.psoft.pitsA.service.sabor;

import com.ufcg.psoft.pitsA.model.sabor.Sabor;
import com.ufcg.psoft.pitsA.repository.SaborRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaborListarMultiploServiceImpl implements SaborListarMultiploService {
    @Autowired
    SaborRepository saborRepository;

    @Override
    public List<Sabor> listarMultiplos(List<Long> saboresId) {
        return saborRepository.findAllById(saboresId);
    }
}
