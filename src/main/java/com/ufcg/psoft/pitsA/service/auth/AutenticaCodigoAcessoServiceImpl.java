package com.ufcg.psoft.pitsA.service.auth;

import com.ufcg.psoft.pitsA.exception.auth.CodigoAcessoInvalidoException;
import org.springframework.stereotype.Service;

@Service
public class AutenticaCodigoAcessoServiceImpl implements AutenticaCodigoAcessoService {
    @Override
    public void autenticar(String codigoAtual, String codigoAutenticar) throws CodigoAcessoInvalidoException {
        if (!codigoAtual.equals(codigoAutenticar)) {
            throw new CodigoAcessoInvalidoException();
        }
    }
}
