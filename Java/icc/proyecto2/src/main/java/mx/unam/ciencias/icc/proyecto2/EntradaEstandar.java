package mx.unam.ciencias.icc.proyecto2;

import mx.unam.ciencias.icc.Arreglos;
import mx.unam.ciencias.icc.Lista;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/** Clase para manejar la entrada estandar */
public class EntradaEstandar {

    /** Constructor privado para evitar instanciacion */
    private EntradaEstandar() {}

    /** Metodo para manejar una entrada estandar */
    public static void manejaEntrada () {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in));
        Lista<Linea> texto = new Lista<>();
        String line = null;
        try {
            while ((line = in.readLine()) != null){
                Linea linea = new Linea(line);
                texto.agregaFinal(linea);
            }
            Lista<Linea> ordenada = Lista.mergeSort(texto);
            for (Linea elemento : ordenada)
                System.out.println(elemento.getLinea());
        } catch (IOException ioe) {
            System.out.printf("Error de entrada");
            System.exit(0);
        }
    }

    /** Metodo para manejar una entrada con una lista dada.
     * @param lista la lista a la cual agregarle el texto de
     * la entrada estandar.
     */
    public static void manejaEntrada (Lista<Linea> lista) {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in));
        String line = null;
        try {
            while ((line = in.readLine()) != null){
                Linea linea = new Linea(line);
                lista.agregaFinal(linea);
            }
        } catch (IOException ioe) {
            System.out.printf("Error de entrada");
            System.exit(0);
        }
    }

}
