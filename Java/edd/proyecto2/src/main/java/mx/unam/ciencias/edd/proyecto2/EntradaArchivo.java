package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/* Clase concreta para manejar una entrada con un Archivo */
public class EntradaArchivo extends Entrada {

    /* El archivo a cargar */
    String archivo;

    /* Define el estado inicial de una entrada con archivo */
    public EntradaArchivo(String archivo) {
        this.archivo = archivo;
    }

    /* Implementación del método abstracto para verificar 
     * un archivo de entrada.
     */
    public void verificaEntrada() {
        try {
            in = new BufferedReader(
                    new InputStreamReader(
                        new FileInputStream(archivo)));
        } catch (IOException ioe) {
            System.out.printf("Error al cargar archivo");
            System.exit(0);
        }
    }
}
