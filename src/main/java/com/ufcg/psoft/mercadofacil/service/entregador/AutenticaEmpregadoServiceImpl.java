package com.ufcg.psoft.mercadofacil.service.entregador;

import com.ufcg.psoft.mercadofacil.exception.CodigoAcessoInvalidoException;
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
