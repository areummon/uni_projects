package mx.unam.ciencias.edd.proyecto3;

/* Clase abstracta para manejar la entrada. */
public abstract class Entrada {

    /* El laberinto dado. */
    protected Laberinto laberinto;

    /* Metodo que regresa el laberinto. */
    public Laberinto getLaberinto() {
        return laberinto;
    }

    /* Metodo para procesar la entrada. */
    public abstract void procesaEntrada() throws Exception;
}
