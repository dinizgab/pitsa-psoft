package com.ufcg.psoft.pitsA.service.entregador;

import com.ufcg.psoft.pitsA.exception.CodigoAcessoInvalidoException;
import org.springframework.stereotype.Service;

@Service
public class AutenticaEmpregadoServiceImpl implements AutenticaEmpregadoService {
    @Override
    public void autenticar(String codigoAtual, String codigoAutenticar) throws CodigoAcessoInvalidoException {
        if (!codigoAtual.equals(codigoAutenticar)) {
            throw new CodigoAcessoInvalidoException();
        }
    }
}
