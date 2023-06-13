package com.ufcg.psoft.pitsA.model.cliente;

import com.ufcg.psoft.pitsA.dto.entregador.EntregadorVeiculoDTO;
import com.ufcg.psoft.pitsA.model.pedido.Pedido;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Cliente extends InteressadoAdapter {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String endereco;

    @Column(nullable = false)
    private String codigoAcesso;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Pedido> pedidos = new ArrayList<>();

    @Override
    public void recebeNotificacaoDisponibilidade() {
        System.out.print("\nO sabor que voce demonstrou interesse esta disponivel novamente: ");
        System.out.println(this.nome);
    }

    public void recebeNotificacaoPedidoPronto() {
        System.out.println("\nHey " + this.nome + "! seu pedido esta pronto, mas ainda nao tem nenhum entregador disponivel");
        System.out.println("Quando algum entregador estiver disponivel novamente nos enviaremos ele\n");
    }

    @Override
    public void recebeNotificacaoPedidoEmRota(String nomeEntregador, EntregadorVeiculoDTO veiculo) {
        String tipo = veiculo.getTipoVeiculo();
        String placa = veiculo.getPlacaVeiculo();
        String cor = veiculo.getCorVeiculo();

        System.out.println("\nHey! " + this.nome + " O pedido que voce fez esta em rota!!!");
        System.out.println("Entregador responsavel: " + nomeEntregador);
        System.out.println("Detalhes do veiculo:");
        System.out.println("Tipo do veiculo: " + tipo);
        System.out.println("Placa do veiculo: " + placa);
        System.out.println("Cor do veiculo: " + cor + "\n");
    }
}
