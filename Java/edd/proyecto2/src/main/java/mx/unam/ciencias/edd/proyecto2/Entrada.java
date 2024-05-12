package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/** Clase abstracta para manejar la entrada el programa */
public abstract class Entrada {

    /* La entrada del programa */
    BufferedReader in;
    /* La estructura dada */
    private Estructuras estructura;
    /* Lista de cadenas para guardar las lineas de la entrada*/
    private Lista<String> entrada = new Lista<>();
    /* El conjunto de elementos a agregar a la estructura */
    private Lista<Integer> elementos = new Lista<>();
    /* La cadena con los elementos procesados */
    private String procesada = "";

    /**
     * Metodo abstracto para verificar la entrada.
     */
    public void verificaEntrada() {}

    /**
     * Metodo para obtener la lista de elementos
     */
    public Lista<Integer> getElementos() {
        return elementos;
    }

    /**
     * Metodo para obtener la estructura.
     */
    public Estructuras getEstructura() {
        return estructura;
    }

    /**
     * Metodo para manejar la entrada.
     */
    public void manejaEntrada() {
        verificaEntrada();
        try {
            leeTexto();
        } catch (IOException ioe) {
            System.out.printf("Error al leer de la entrada");
            System.exit(0);
        }
        if (entrada.getElementos() ==  0) {
            System.out.printf("Error no hay elementos");
            System.exit(0);
        }
        procesaEntrada();
    }

    /**
     * Metodo para leer de la entrada del programa
     */
    private void leeTexto() throws IOException {
        String linea = null;
        String aux;
        while ((linea = in.readLine()) != null) {
            if (linea.contains("#")) {
                aux = linea.trim().split("#")[0];
                if (aux.equals(""))
                    continue;
                else 
                    entrada.agregaFinal(aux.replaceAll("\\s+"," "));
            }
            else
                entrada.agregaFinal(linea.trim().replaceAll("\\s+"," "));
        }
        in.close();
    }

    /**
     * Metodo para procesar las lineas de la entrada.
     */
    private void procesaEntrada() {
        String estructuraDada;
        estructuraDada = entrada.eliminaPrimero();
        verificaEstructura(estructuraDada.split(" "));
        for (String elemento : entrada) {
            procesada += elemento + " ";
        }
        convierteArreglo(procesada.split("\\s+")); 
    }

    /**
     * Metodo privado auxiliar para checar la estructura dada.
     * @param primera arreglo de la primera linea donde se supone
     * debe estar la estructura.
     */
    private void verificaEstructura(String[] primera) {
        String estructura = "";
        for (int i = 0; i < primera.length; i++) {
            try {
                Integer.parseInt(primera[i]);
                procesada += primera[i] + " ";
            } catch (NumberFormatException nfe) {
                if (i > 0) {
                    System.out.printf("Elemento: %s no es un entero",primera[i]);
                    System.exit(0);
                }
                estructura += primera[i];
            }
        }
        this.estructura = Estructuras.identificaEstructura(estructura);
        if (this.estructura == null) {
            System.out.printf("Estructura no identificada");
            System.exit(0);
        }
    }

    /**
     * Metodo privado auxiliar para convertir un arreglo de cadenas 
     * y guardarlo en nuestra lista de elementos
     */
    private void convierteArreglo(String[] arreglo) {
        for (int i = 0; i < arreglo.length; i++) {
            try {
                elementos.agregaFinal(Integer.parseInt(arreglo[i]));
            } catch (NumberFormatException nfe) {
                System.out.printf("Elemento %s no es un entero",arreglo[i]);
                System.exit(0);
            }
        }
    }
}

