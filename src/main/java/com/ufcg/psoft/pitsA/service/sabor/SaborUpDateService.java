package com.ufcg.psoft.pitsA.service.sabor;

import com.ufcg.psoft.pitsA.dto.SaborDTO;
import com.ufcg.psoft.pitsA.model.Sabor;
@FunctionalInterface
public interface SaborUpDateService {
    SaborDTO update(SaborDTO saborDTO);
}
