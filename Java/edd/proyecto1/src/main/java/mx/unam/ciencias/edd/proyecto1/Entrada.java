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
 * Clase para manejar la entrada con banderas y archivo/s de texto
 */
public class Entrada {
   
    /** Constructor privado para evitar instanciacion */
    private Entrada () {}

    /**
     * Metodo para comprobar la ruta de un archivo dado
     * @param ruta la ruta/archivo de entrara.
     * @return la ruta ya verificada.
     */
    public static BufferedReader verificaEntrada(String ruta) throws IOException {
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                        new FileInputStream(ruta)));
            return in;
        } catch (IOException ioe) {
            throw ioe;
        }
    }

    /** 
     * Metodo para guardar con un identificador dado 
     * @param lista la lista a guardar en el identificador.
     * @param identificador el identificador con el cual guardar la entrada.
     */
    public static void guarda(Lista<Linea> lista, String identificador) throws IOException {
        try { 
            BufferedWriter out = new BufferedWriter (
                    new OutputStreamWriter(
                        new FileOutputStream(identificador)));
            for (Linea linea : lista)
                out.write(linea.getLinea() + "\n");
            out.close();
        } catch (IOException ioe) {
            throw ioe;
        }
    }


}
