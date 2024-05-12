package mx.unam.ciencias.edd.proyecto3;

/**
 * Proyecto 3: Solucionador de Laberintos.
 */
public class Proyecto3 {

    /* Imprime el uso del programa y lo termina. */
    private static void uso() {
        System.err.println("Uso: java -jar -g <|-s <numero>|> -w <numero> -h <numero>");
        System.exit(1);
    }

    public static void main(String[] args) {
        App aplicacion = new App(args);
        try {
            aplicacion.ejecuta();
        } catch (Exception e) {
            uso();
        }
    }
}
