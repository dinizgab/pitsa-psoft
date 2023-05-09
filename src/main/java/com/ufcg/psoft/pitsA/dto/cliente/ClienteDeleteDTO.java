package com.ufcg.psoft.pitsA.dto.cliente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDeleteDTO {
    private String codigoAcesso;
}
