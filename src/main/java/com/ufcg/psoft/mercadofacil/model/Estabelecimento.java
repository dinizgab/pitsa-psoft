package com.ufcg.psoft.mercadofacil.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.mercadofacil.dto.SaborDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "estabelecimentos")
public class Estabelecimento {
    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonProperty("codigoAcesso")
    @Column(nullable = false)
    private String codigoAcesso;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Sabor> cardapio = new ArrayList<>();

    public boolean addSabor(Sabor sabor){
        return this.cardapio.add(sabor);
    }

    public void removeSabor(Sabor sabor){
        cardapio.remove(sabor);
    }

    public boolean updateSabor(Sabor sabor){
        return this.cardapio.add(sabor);
    }

    public List<Sabor> getAllSabor(){
        return cardapio;
    }
}
