package mx.unam.ciencias.edd.proyecto1;

/**
 * Proyecto 1: Ordenador Lexicografico
 */
public class Proyecto1 {

    public static void main(String[] args) {
        try {
            App aplicacion = new App();
            aplicacion.ejecuta(args);
        } catch (IllegalArgumentException iae) {
            System.exit(0);
        }
    }
}
