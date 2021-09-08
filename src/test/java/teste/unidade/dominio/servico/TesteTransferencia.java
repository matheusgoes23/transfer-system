package teste.unidade.dominio.servico;

import conta.sistema.dominio.modelo.Conta;
import conta.sistema.dominio.modelo.NegocioException;
import conta.sistema.dominio.servico.Transferencia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Regra de Transferência")
public class TesteTransferencia {

    BigDecimal cem = new BigDecimal(100);
    BigDecimal vinte = new BigDecimal(20);
    Transferencia trans;
    Conta contaDebito;
    Conta contaCredito;

    @BeforeEach
    void preparar() {
        contaDebito = new Conta(1, cem, "Fernando");
        contaCredito = new Conta(2, cem, "Rebeca");
        trans = new Transferencia();
    }

    @Test
    @DisplayName("valor nulo como obrigatório")
    void teste1() {
        try {
            trans.transferencia(null, contaDebito, contaCredito);
            fail("valor transferência obrigatório");
        } catch (NegocioException e) {
            assertEquals(e.getMessage(), "Valor da transferência é obrigatório.");
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("conta débito como obrigatório")
    void teste2() {
        try {
            trans.transferencia(vinte, null, contaCredito);
            fail("conta débito obrigatório");
        } catch (NegocioException e) {
            assertEquals(e.getMessage(), "Conta débito é obrigatório.");
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("conta crédito como obrigatório")
    void teste3() {
        try {
            trans.transferencia(vinte, contaDebito, null);
            fail("conta crédito obrigatório");
        } catch (NegocioException e) {
            assertEquals(e.getMessage(), "Conta crédito é obrigatório.");
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("transferir 20 reais")
    void teste4() {
        try {
            trans.transferencia(vinte, contaDebito, contaCredito);
            assertEquals(contaDebito.getSaldo(), cem.subtract(vinte), "Saldo da conta débito deve bater");
            assertEquals(contaCredito.getSaldo(), cem.add(vinte), "Saldo da conta crédito deve bater");

        } catch (NegocioException e) {
            fail("Deve transferir com sucesso");
        }
    }
}
