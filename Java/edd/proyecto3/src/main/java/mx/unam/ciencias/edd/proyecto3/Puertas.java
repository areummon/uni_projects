package mx.unam.ciencias.edd.proyecto3;

/* Enumeracion para las puertas del laberinto. */
public enum Puertas {
    
    /* La puerta norte. */
    NORTE(0xD),
    /* La puerta sur. */
    SUR(0x7),
    /* La puerta oeste. */
    OESTE(0xB),
    /* La puerta este. */
    ESTE(0xE);

    /* El valor de la puerta. */
    private final Integer valor;

    /* Constructor privado para el valor. */
    private Puertas(Integer valor) {
        this.valor = valor;
    }

    /* Metodo para obtener el valor de la puerta dada. */
    public Integer valor() {
        switch(this) {
            case NORTE:
                return NORTE.valor;
            case SUR:
                return SUR.valor;
            case OESTE:
                return OESTE.valor;
            case ESTE:
                return ESTE.valor;
            default: return 0;
        }
    }
}
