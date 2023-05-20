package com.ufcg.psoft.pitsA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PitsAApplication {
    public static void main(String[] args) {
        SpringApplication.run(PitsAApplication.class, args);
    }
}

// Feats
// TODO - Criar testes para Sabor

// Refactoring
// TODO - Criar um EstabelecimentoReadDTO para as operacoes de leitura de um estabelecimento
// TODO - Trocar os valores dos bodies que usam IDs como pesquisa por RequestParams

// Fixes
// TODO - Corrigir o erro nesses testes de listar Entregador -- Modelmapper nao consegue fazer o mapeamento
// TODO - Corrigir teste de atualizar um sabor, erro de troca de ID
