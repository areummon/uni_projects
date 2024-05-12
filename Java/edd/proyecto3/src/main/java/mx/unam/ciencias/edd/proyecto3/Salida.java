package mx.unam.ciencias.edd.proyecto3;

/* Clase para manejar la salida del programa. */
public class Salida {

    /* Constructor privado para evitar instanciacion. */
    private Salida() {}

    /* Metodo para escribir un laberinto con el formato
     * requerido.
     * @param formato los primeros 6 bytes de cada laberinto.
     * @param laberinto el laberinto dado. */
    public static void escribeLaberinto(int[] formato, Laberinto laberinto) {
        for (Integer elemento : formato)
            System.out.write(elemento);
        for (Integer casilla : laberinto) {
            System.out.write(casilla);
        }
        System.out.close();
    }

    /* Metodo para graficar un laberinto con su solucion.
     * @param laberinto el laberinto a graficar. */
    public static void graficaLaberinto(Laberinto laberinto) {
        GraficaLaberinto graficado = new GraficaLaberinto(laberinto);
        graficado.grafica();
    }
}
