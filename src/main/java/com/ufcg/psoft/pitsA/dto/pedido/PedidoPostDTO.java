package com.ufcg.psoft.pitsA.dto.pedido;

import com.ufcg.psoft.pitsA.model.pedido.PizzaPedidoTamanho;
import com.ufcg.psoft.pitsA.model.pedido.PizzaPedidoTipo;
import com.ufcg.psoft.pitsA.model.sabor.SaborPedido;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoPostDTO {
    private String codigoAcesso;

    @NotNull(message = "O id do estabelecimento nao pode ser vazio")
    private Long idEstabelecimento;

    @NotNull(message = "O endereco de entrega nao pode ser nulo")
    private String endereco;

    @NotNull(message = "O tamanho da pizza eh obrigatorio")
    private PizzaPedidoTamanho tamanho;

    @NotNull(message = "O tipo da pizza eh obrigatorio")
    private PizzaPedidoTipo tipo;

    @Builder.Default
    @NotEmpty(message = "Escolha pelo menos um sabor")
    private List<SaborPedido> sabores = new ArrayList<>();
}
