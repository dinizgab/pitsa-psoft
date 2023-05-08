package com.ufcg.psoft.pitsA.service.sabor;

import com.ufcg.psoft.pitsA.dto.SaborDTO;
import com.ufcg.psoft.pitsA.model.Sabor;
import org.springframework.beans.factory.annotation.Autowired;

@FunctionalInterface
public interface SaborCreateService {
    SaborDTO create(SaborDTO saborDTO);
}