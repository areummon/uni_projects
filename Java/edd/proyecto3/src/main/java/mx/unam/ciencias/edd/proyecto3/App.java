package mx.unam.ciencias.edd.proyecto3;

/** Clase para manejar una aplicación. */
public class App {

    /** La entrada del programa. */
    Entrada entrada;

    /* Define el estado inicial de una entrada.
     * @param entrada el arreglo de la entrada. 
     */
    public App(String[] entrada) {
        if (entrada.length == 0)
            this.entrada = new EntradaEstandar();
        else if (entrada.length == 1)
            this.entrada = new EntradaLaberinto(entrada[0]);
        else
            this.entrada = new EntradaArgumentos(entrada);
    }

    /* Ejecuta la aplicacion. */
    public void ejecuta() throws Exception {
        entrada.procesaEntrada();
        if (entrada instanceof EntradaArgumentos) {
            EntradaArgumentos nuevaEntrada = (EntradaArgumentos)entrada;
            Salida.escribeLaberinto(nuevaEntrada.getFormato(),nuevaEntrada.getLaberinto());
        }
        else {
            VerificaLaberinto.verifica(entrada.getLaberinto());
            Salida.graficaLaberinto(entrada.getLaberinto());
        }
    }
}
