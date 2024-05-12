package mx.unam.ciencias.icc.proyecto2;

import java.io.BufferedReader;

/**
 * Proyecto 2: Ordenador Lexicografico
 */
public class Proyecto2 {

    public static void main(String[] args) {
        if (args.length == 0) {
            EntradaEstandar.manejaEntrada();
            return; 
        }

        try {
            App aplicacion = new App();
            aplicacion.ejecuta(args);
        } catch (IllegalArgumentException iae) {
            System.exit(0);
        }
    }
}
