package mx.unam.ciencias.icc.test;

import mx.unam.ciencias.icc.Arreglos;
import mx.unam.ciencias.icc.Lista;
import mx.unam.ciencias.icc.proyecto2.Linea;
import mx.unam.ciencias.icc.proyecto2.Entrada;
import mx.unam.ciencias.icc.proyecto2.Bandera;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.InputMismatchException;
import java.util.Scanner;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.Timeout;
import java.util.Random;
import java.util.Iterator;

/**
 * Clase con métodos estáticos utilitarios para las pruebas del programa.
 */
public class UtilOrden {

    /* Evitar instanciación */
    private UtilOrden() {}

    /**
     * Metodo para leer de algun archivo. 
     * @param in la entrada de la cual leer.
     * @param textos la lista a la cual agregar las lineas de la entrada.
     */
    public static void leeTexto(BufferedReader in, Lista<Linea> textos) throws IOException { 
        String linea = null;
        while ((linea = in.readLine()) != null) {
            Linea line = new Linea(linea);
            textos.agregaInicio(line);
        } 
    }

    /**
     * Metodo para escribir en un archivo
     * @param out la salida a la cual escribir.
     * @param cadena la cadena a escribir.
     */
    public static void escribe(BufferedWriter out, String cadena) throws IOException {
        try { 
            out.write(cadena);
            out.close();
        } catch (IOException ioe) {
            throw ioe;
        }
    }

    /**
     * Metodo para verificar si dos listas
     * de lineas son iguales
     * @param lista1 la primera lista a comparar
     * @param lista2 la segunda lista a comparar
     */
    public static Boolean verifica(Lista<Linea> lista1, Lista<Linea> lista2) {
        Iterator i1 = lista1.iterator();
        Iterator i2 = lista2.iterator();
        while(i1.hasNext() && i2.hasNext()){
            if (i1.next().toString().equals(i2.next().toString()))
                continue;
            else return false;
        }
        return true;
    }

    /**
     * Metodo para escribir las lineas de un texto en una lista.
     * @param lista la lista de lineas que se guardara en una cadena.
     */
    public static String guardaCadena(Lista<Linea> lista){
        String orden = "";
        for (Linea elemento : lista) 
            orden += elemento.toString() + "\n";
        return orden;
    }
}

