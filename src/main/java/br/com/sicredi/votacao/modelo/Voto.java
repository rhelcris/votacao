package br.com.sicredi.votacao.modelo;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "voto")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "sessao_votacao_id")
    private SessaoVotacao sessaoVotacao;

    @Column(name = "associado_id")
    private Long associadoId;

    private String voto;

}
