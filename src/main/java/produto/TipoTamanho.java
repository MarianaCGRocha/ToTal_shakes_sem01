package produto;

public enum TipoTamanho {
    //IMPLEMENTE A LOGICA DO ENUM
    //Cria variáveis enum com os tamanhos de shakes disponíveis para compra
    P(1.0),
    M(1.3),
    G(1.5);

    //Estabelece uma variável descrição que contém as informações dos tamanhos disponíveis

    TipoTamanho(Double multiplicador) {
        this.multiplicador = multiplicador;
    }

    public double getMultiplicador() {
        return multiplicador;
    }

    public final double multiplicador;
}
