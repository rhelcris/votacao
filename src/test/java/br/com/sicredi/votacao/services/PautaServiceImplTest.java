package br.com.sicredi.votacao.services;

import br.com.sicredi.votacao.adapter.PautaAdapter;
import br.com.sicredi.votacao.api.requests.PautaRequest;
import br.com.sicredi.votacao.exceptions.PautaNaoExisteException;
import br.com.sicredi.votacao.modelo.Pauta;
import br.com.sicredi.votacao.repositories.PautaRepository;
import br.com.sicredi.votacao.services.impl.PautaServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class PautaServiceImplTest {

    private final PautaRepository pautaRepository = mock(PautaRepository.class);

    private PautaService pautaService;

    @BeforeEach
    public void setUp() {
        this.pautaService = new PautaServiceImpl(pautaRepository);
    }

    @Test
    @DisplayName("DEVE criar a pauta com sucesso")
    public void deveCriarPautaComSucesso() {
        PautaRequest pautaRequest = PautaRequest.builder().descricao("Pauta 1").build();
        Pauta pauta = PautaAdapter.adapter(pautaRequest);

        when(pautaRepository.save(any(Pauta.class))).thenReturn(pauta);
        var resposta = pautaService.criarPauta(pautaRequest);

        verify(pautaRepository, times(1)).save(pauta);
        Assertions.assertEquals(pautaRequest.getDescricao(), resposta.getDescricao());;
    }

    @Test
    @DisplayName("DEVE consultar a pauta dado um ID")
    public void deveConsultarPautaPeloID() {
        Pauta pauta = Pauta.builder().id(1l).descricao("Pauta 1").build();

        when(pautaRepository.findById(1l)).thenReturn(Optional.ofNullable(pauta));
        var resposta = pautaService.buscarPeloCodigo(1l);

        verify(pautaRepository, times(1)).findById(1l);
        Assertions.assertEquals("Pauta 1", resposta.getDescricao());;
    }

    @Test
    @DisplayName("DEVE FALHAR ao consultar a pauta dado um ID")
    public void deveFalharAoConsultarPautaPeloID() {
        PautaNaoExisteException exception = Assertions.assertThrows(
            PautaNaoExisteException.class, () -> pautaService.buscarPeloCodigo(2l)
        );

        Assertions.assertEquals("Pauta nao encontrada com codigo: 2", exception.getMessage());
    }

}
