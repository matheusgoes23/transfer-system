module conta.sistema {

    requires javax.inject;
    requires spring.tx;

    // expondo porta de entrada (driver)
    exports conta.sistema.casouso.porta;
    exports conta.sistema.casouso.impl;

    // expondo sistema negocio
    exports conta.sistema.dominio.modelo;
    exports conta.sistema.dominio.servico;

    // expondo adaptadores de saídas (driven)
    exports conta.sistema.porta;
    exports conta.adaptador;

    // abre reflexão spring
    opens conta.sistema.casouso.porta;
    opens conta.sistema.casouso.impl;
    opens conta.sistema.dominio.servico;
    opens conta.adaptador;
}