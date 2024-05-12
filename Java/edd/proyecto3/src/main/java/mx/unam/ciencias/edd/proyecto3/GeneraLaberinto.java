package mx.unam.ciencias.edd.proyecto3;

import java.util.Random;

/* Clase para generar los laberintos. */
public class GeneraLaberinto {

    /* El inicio de cada laberinto. */
    int[] maze = {0x4d, 0x41, 0x5a, 0x45};
    /* El numero de renglones del laberinto. */
    private int renglones;
    /* El numero de columnas del laberinto. */
    private int columnas;
    /* El generador de numeros aleatorios. */
    private Random random;

    /* Define el estado inicial de un generador de laberintos.
     * @param renglones el numero de renglones del laberinto.
     * @param columnas el numero de columnas del laberinto.
     * @param semilla la semilla para le generador de numeros aleatorios.
     */
    public GeneraLaberinto(int renglones, int columnas, long semilla) {
        this.renglones = renglones;
        this.columnas = columnas;
        random = new Random(semilla);
    }

    /**
     * Metodo para generar un laberinto con el formato
     * requerido y regresar. 
     */
    public int[] inicioFormato() {
        int[] salida = new int[6];
        salida[0] = maze[0];
        salida[1] = maze[1];
        salida[2] = maze[2];
        salida[3] = maze[3];
        salida[4] = renglones;
        salida[5] = columnas;
        return salida;
    }

    /* Metodo para generar un laberinto y regresarlo.*/
    public Laberinto generaLaberinto() {
        return new Laberinto(null, renglones, columnas, random);
    }
}
