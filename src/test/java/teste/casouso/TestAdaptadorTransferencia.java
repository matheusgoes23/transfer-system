package teste.casouso;

import conta.sistema.casouso.porta.PortaTransferencia;
import conta.sistema.dominio.modelo.NegocioException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Caso de Uso - Serviço de Transferência")
@ContextConfiguration(classes = Build1.class)
@ExtendWith(SpringExtension.class)
public class TestAdaptadorTransferencia {

    Integer contaCredito = 10;
    Integer contaDebito = 20;
    Integer contaInexistente = 30;
    BigDecimal cem = new BigDecimal(100);
    BigDecimal cinquenta = new BigDecimal(50);

    @Inject
    PortaTransferencia porta;

    // negativos de getConta

    @Test
    @DisplayName("pesquisa conta com número nulo")
    void teste1() {
        try {
            var conta = porta.getConta(null);
            assertTrue(conta == null, "Conta dever ser nula");
        } catch (NegocioException e) {
            fail("Deve carregar uma conta nula");
        }
    }

    @Test
    @DisplayName("pesquisa conta com número inexistente")
    void teste2() {
        try {
            var conta = porta.getConta(contaInexistente);
            assertTrue(conta == null, "Conta dever ser nula");
        } catch (NegocioException e) {
            fail("Deve carregar uma conta nula");
        }
    }

    // positivo de getConta

    @Test
    @DisplayName("pesquisa conta com número existente")
    void teste3() {
        try {
            var conta = porta.getConta(contaCredito);
            assertTrue(conta != null, "Conta deve estar preenchida");
        } catch (NegocioException e) {
            fail("Deve carregar uma conta existente");
        }
    }

    // negativos de transferência

    @Test
    @DisplayName("conta débito como obrigatório")
    void teste4() {
        try {
            porta.transferir(null, contaCredito, cinquenta);
            fail("Conta de débito é obrigatório");
        } catch (NegocioException e) {
            assertEquals(e.getMessage(), "Conta débito é obrigatório.");
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("conta crédito como obrigatório")
    void teste5() {
        try {
            porta.transferir(contaDebito, null, cinquenta);
            fail("Conta de crédito é obrigatório");
        } catch (NegocioException e) {
            assertEquals(e.getMessage(), "Conta crédito é obrigatório.");
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("valor como obrigatório")
    void teste6() {
        try {
            porta.transferir(contaDebito, contaCredito, null);
            fail("Valor é obrigatório");
        } catch (NegocioException e) {
            assertEquals(e.getMessage(), "Valor é obrigatório.");
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("conta débito inexistente")
    void teste7() {
        try {
            porta.transferir(contaInexistente, contaCredito, cinquenta);
            fail("Conta débito inexistente");
        } catch (NegocioException e) {
            assertEquals(e.getMessage(), "Conta débito é inexistente.");
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("conta crédito inexistente")
    void teste8() {
        try {
            porta.transferir(contaDebito, contaInexistente, cinquenta);
            fail("Conta crédito inexistente");
        } catch (NegocioException e) {
            assertEquals(e.getMessage(), "Conta crédito é inexistente.");
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("mesma conta débito e crédito")
    void teste9() {
        try {
            porta.transferir(contaDebito, contaDebito, cinquenta);
            fail("Conta crédito e débito devem ser diferentes");
        } catch (NegocioException e) {
            assertEquals(e.getMessage(), "Conta débito e crédito devem ser diferentes.");
            System.out.println(e.getMessage());
        }
    }

    // positivos transferência

    @Test
    @DisplayName("transferência de 50 reais")
    void teste10() {
        try {
            porta.transferir(contaDebito, contaCredito, cinquenta);
        } catch (NegocioException e) {
            fail("Não deve gerar erro de transferência - " + e.getMessage());
        }
        try {
            var credito = porta.getConta(contaCredito);
            var debito = porta.getConta(contaDebito);
            assertEquals(credito.getSaldo(), cem.add(cinquenta), "Saldo de crédito deve bater");
            assertEquals(debito.getSaldo(), cem.subtract(cinquenta), "Saldo débito deve bater");
        }catch (NegocioException e){
            fail("Não deve gerar erro de validação de saldo - " + e.getMessage());
        }
    }
}
