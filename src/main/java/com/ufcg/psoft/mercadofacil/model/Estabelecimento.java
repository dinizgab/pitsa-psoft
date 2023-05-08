package com.ufcg.psoft.mercadofacil.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

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

    @JsonProperty("entregadores")
    @ManyToMany(mappedBy = "estabelecimentos",fetch = FetchType.LAZY)
    private List<Entregador> entregadores;
}