package mx.unam.ciencias.edd.proyecto3;

import java.util.Random;

/* Clase para manejar la entrada del programa */
public class EntradaArgumentos extends Entrada {

    /* El arreglo de entrada. */
    private String[] entrada;
    /* Las banderas del programa. */
    private Banderas banderas;
    /* El laberinto generado */
    private GeneraLaberinto laberintoGenerado;

    /* Define el estado inicial de una entrada
     * con argumentos.
     * @param entrada el arreglo de entrada.
     */
    public EntradaArgumentos(String[] entrada) {
        this.entrada = entrada;
        banderas = new Banderas(this.entrada);
    }

    /* Metodo para leer y procesar la entrada
     * del programa. */
    @Override public void procesaEntrada() throws Exception {
        banderas.leeArgumentos();
        generaLaberinto(banderas.getRenglones(),banderas.getColumnas(),
                        banderas.getSemilla());
    }

    /* Metodo para generar el laberinto con 
     * la entrada dada.
     * @param renglones el numero de renglones del laberinto.
     * @param columnas el numero de columnas del laberinto.
     * @param semilla la semilla del programa.
     */
    private void generaLaberinto(int renglones, int columnas, long semilla) {
        laberintoGenerado = new GeneraLaberinto(renglones,columnas,semilla);
        this.laberinto = laberintoGenerado.generaLaberinto();
    }

    /* Metodo para regresar un arreglo con los bytes
     * requeridos para un archivo .mze */
    public int[] getFormato() {
        return laberintoGenerado.inicioFormato();
    }
    
}
