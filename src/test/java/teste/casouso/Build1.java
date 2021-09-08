package teste.casouso;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({
        // objetos do sistema
        "conta.sistema",
        // adptadores falsos
        "conta.adaptador"
})
public class Build1 {
    // Buld 1: Adptador Teste -> Sistema <- Adptadores Mock
}
