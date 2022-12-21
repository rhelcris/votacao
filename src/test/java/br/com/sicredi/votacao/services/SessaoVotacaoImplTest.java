package br.com.sicredi.votacao.services;

import br.com.sicredi.votacao.api.requests.SessaoVotacaoRequest;
import br.com.sicredi.votacao.api.requests.VotoRequest;
import br.com.sicredi.votacao.api.responses.ResultadoApuracaoResponse;
import br.com.sicredi.votacao.exceptions.SessaoVotacaoNaoExisteException;
import br.com.sicredi.votacao.modelo.Pauta;
import br.com.sicredi.votacao.modelo.SessaoVotacao;
import br.com.sicredi.votacao.repositories.SessaoVotacaoRepository;
import br.com.sicredi.votacao.services.impl.SessaoVotacaoServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static br.com.sicredi.votacao.utils.PautaUtils.umaPauta;
import static br.com.sicredi.votacao.utils.SessaoVotacaoUtils.umaSessaoDeVotacao;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class SessaoVotacaoImplTest {

    private final SessaoVotacaoRepository sessaoVotacaoRepository = mock(SessaoVotacaoRepository.class);
    private final PautaService pautaService = mock(PautaService.class);
    private final VotoService votoService = mock(VotoService.class);

    private SessaoVotacaoService sessaoVotacaoService;

    @BeforeEach
    public void setUp() {
        this.sessaoVotacaoService = new SessaoVotacaoServiceImpl(votoService,
                pautaService,
                sessaoVotacaoRepository
        );
    }

    @Test
    @DisplayName("DEVE abrir sessão de votação com sucesso")
    public void deveAbrirSessaoDeVotacaoComSucesso() {
        Pauta pauta = umaPauta();
        SessaoVotacaoRequest sessaoVotacaoRequest = SessaoVotacaoRequest.builder()
                .pautaId(1l)
                .prazoEncerramentoEmMinutos(30l)
                .build();

        when(pautaService.buscarPeloCodigo(sessaoVotacaoRequest.getPautaId())).thenReturn(pauta);
        sessaoVotacaoService.abrirSessaoDeVotacao(sessaoVotacaoRequest);

        verify(pautaService, times(1)).buscarPeloCodigo(sessaoVotacaoRequest.getPautaId());
        verify(sessaoVotacaoRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("DEVE abrir sessão de votação com sucesso no prazo default de encerramento")
    public void deveAbrirSessaoDeVotacaoComSucessoComPrazoDefaultDeEncerramento() {
        Pauta pauta = umaPauta();
        SessaoVotacaoRequest sessaoVotacaoRequest = SessaoVotacaoRequest.builder()
                .pautaId(1l)
                .build();

        when(pautaService.buscarPeloCodigo(sessaoVotacaoRequest.getPautaId())).thenReturn(pauta);
        sessaoVotacaoService.abrirSessaoDeVotacao(sessaoVotacaoRequest);

        verify(pautaService, times(1)).buscarPeloCodigo(sessaoVotacaoRequest.getPautaId());
        verify(sessaoVotacaoRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("DEVE registrar voto em uma sessao em uma pauta")
    public void deveRegistrarVotoEmUmaPautaNaSessaoDeVotacao() {
        long sessaoDeVotacao = 1l;
        long pautaId = 1l;
        LocalDateTime dataHoraInicioSessaoVotacao = LocalDateTime.now();
        int quantidadeMinutosParaFimDaSessaoDeVotacao = 30;

        VotoRequest votoRequest = VotoRequest.builder().associadoId(1l).voto("Sim").build();
        SessaoVotacao sessaoVotacao = umaSessaoDeVotacao(dataHoraInicioSessaoVotacao, quantidadeMinutosParaFimDaSessaoDeVotacao);

        when(sessaoVotacaoRepository.findByIdAndPautaId(sessaoDeVotacao, pautaId)).thenReturn(sessaoVotacao);
        sessaoVotacaoService.registrarVoto(sessaoDeVotacao, pautaId, votoRequest);

        verify(sessaoVotacaoRepository, times(1)).findByIdAndPautaId(sessaoDeVotacao, pautaId);
        verify(votoService, times(1)).registrarVoto(any());
    }

    @Test
    @DisplayName("DEVE FALHAR ao registrar voto pois não foi encontrado a sessão de votação")
    public void deveFalharAoConsultarPautaPeloID() {
        long sessaoDeVotacao = 1l;
        long pautaId = 1l;

        VotoRequest votoRequest = VotoRequest.builder().associadoId(1l).voto("Sim").build();
        when(sessaoVotacaoRepository.findByIdAndPautaId(sessaoDeVotacao, pautaId)).thenReturn(null);

        SessaoVotacaoNaoExisteException exception = Assertions.assertThrows(
                SessaoVotacaoNaoExisteException.class, () -> sessaoVotacaoService.registrarVoto(sessaoDeVotacao, pautaId, votoRequest)
        );

        Assertions.assertEquals("A sessao de votacao da pauta: 1 não existe", exception.getMessage());
    }

    @Test
    @DisplayName("DEVE FALHAR ao registrar voto pois não foi encontrado a sessão de votação")
    public void deveFalharAoApurarVotacao() {
        long sessaoDeVotacao = 1l;
        long pautaId = 1l;

        when(sessaoVotacaoRepository.findByIdAndPautaId(sessaoDeVotacao, pautaId)).thenReturn(null);

        SessaoVotacaoNaoExisteException exception = Assertions.assertThrows(
                SessaoVotacaoNaoExisteException.class, () -> sessaoVotacaoService.apurarVotacao(sessaoDeVotacao, pautaId)
        );

        Assertions.assertEquals("A sessao de votacao da pauta: 1 não existe", exception.getMessage());
    }

    @Test
    @DisplayName("DEVE apurar a votação da sessão de votação")
    public void deveApurarVotacaoDaSessaoComSucesso() {
        long sessaoDeVotacao = 1l;
        long pautaId = 1l;
        LocalDateTime dataHoraInicioSessaoVotacao = LocalDateTime.now();
        int quantidadeMinutosParaFimDaSessaoDeVotacao = 30;

        ResultadoApuracaoResponse resultadoApuracaoResponse = ResultadoApuracaoResponse.builder()
                .quantidadeVotosNao(3)
                .quantidadeVotosSim(2)
                .build();

        SessaoVotacao sessaoVotacao = umaSessaoDeVotacao(dataHoraInicioSessaoVotacao, quantidadeMinutosParaFimDaSessaoDeVotacao);

        when(sessaoVotacaoRepository.findByIdAndPautaId(sessaoDeVotacao, pautaId)).thenReturn(sessaoVotacao);
        when(votoService.apurarVotos(sessaoVotacao)).thenReturn(resultadoApuracaoResponse);

        var resultadoApuracao = sessaoVotacaoService.apurarVotacao(sessaoDeVotacao, pautaId);

        verify(sessaoVotacaoRepository, times(1)).findByIdAndPautaId(sessaoDeVotacao, pautaId);
        verify(votoService, times(1)).apurarVotos(sessaoVotacao);
        Assertions.assertEquals(3, resultadoApuracao.getQuantidadeVotosNao());
        Assertions.assertEquals(2, resultadoApuracao.getQuantidadeVotosSim());
    }

}
