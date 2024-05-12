package mx.unam.ciencias.edd.proyecto1;

import mx.unam.ciencias.edd.Arreglos;
import mx.unam.ciencias.edd.Lista;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Clase para manejar las banderas del ordenador lexicografico. 
 */
public class Bandera {

    /* La lista de las lineas de los archivos*/
    private Lista<Linea> archivos;
    /* La ruta del archivo/s que se leera */
    private String ruta;
    /* Bandera para el modo reversa del/los archivo/s */
    private Boolean reversa;
    /* Bandera para guardar la salida con un identificador */
    private Boolean salida;
    /* EL identificador del archivo */
    private String identificador;
    /* El modo del programa */
    private Modo modo;

    /** 
     * Define el estado inicial de Bandera. 
     */
    public Bandera() {
        archivos = new Lista<>();
        reversa = false;
        salida = false;
    }

    public void revisaBanderas(String[] entrada){
        for (int i = 0; i < entrada.length; i++){
            switch (entrada[i]){
                case "-r": 
                    reversa = true;
                    break;
                case "-o":
                    if (entrada.length < i + 2){
                        System.out.printf("Error: -o sin identificador. \n");
                        throw new IllegalArgumentException();
                    }
                    else if (identificador != null) {
                        System.out.printf("Error: multiples archivos de salida especificados. \n");
                        throw new IllegalArgumentException();
                    }
                    salida = true;
                    identificador = entrada[i+1];
                    i++;
                    break;
                default:
                    try {
                        ruta = entrada[i];
                        BufferedReader in = Entrada.verificaEntrada(ruta);
                        leeTexto(in);
                        in.close();
                    } catch (IOException ioe) {
                        System.out.printf("No se pudo cargar el archivo \"%s\".\n",
                                          ruta);
                        System.exit(0);
                    }
            }
        }
    }

    /**
     * Metodo para leer y guardar todas las lineas del archivo en una lista
     * @param in la entrada del programa.
     */
    private void leeTexto(BufferedReader in) throws IOException { 
        String linea = null;
        while ((linea = in.readLine()) != null) {
            Linea line = new Linea(linea);
            archivos.agregaFinal(line);
        } 
    }

    /**
     * Metodo para comprobar y ejecutar las modos del programa. 
     */
    public void verificaBanderas() {
        if (ruta == null)
            EntradaEstandar.manejaEntrada(archivos);
        modo = new Modo(archivos,identificador);
        if (reversa == true && salida == false) 
            modo.ejecutaModo(Opciones.REVERSA_SIN_IDENTIFICADOR);
        else if (reversa == true && salida == true) 
            modo.ejecutaModo(Opciones.REVERSA_CON_IDENTIFICADOR);
        else if (salida == true && reversa == false)
            modo.ejecutaModo(Opciones.IDENTIFICADOR);
        else 
            modo.ejecutaModo(Opciones.IMPRIME);
    }
}
