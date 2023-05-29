package com.ufcg.psoft.pitsA.exception.estabelecimento;

import com.ufcg.psoft.pitsA.exception.PitsAException;

public class SaborNaoPertenceCardapio extends PitsAException {
    public SaborNaoPertenceCardapio() {super("O sabor nao pertence ao cardapio desse estabelecimento");}
}
