package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Lista;
import java.util.Random;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/* Clase concreta para manejar una entrada con un Archivo */
public class EntradaLaberinto extends Entrada {

    /* La entrada del programa */
    FileInputStream is;
    /* El archivo a cargar */
    String archivo;

    /* Define el estado inicial de una entrada con archivo. */
    public EntradaLaberinto(String archivo) {
        this.archivo = archivo;
    }

    /* Procesa el laberinto recibido y revisa el formato */
    @Override public void procesaEntrada() throws Exception {
        verificaEntrada();
        byte[] laberintoBytes = is.readAllBytes();
        is.close();
        Integer[] formato = new Integer[4];
        for (int i = 0; i < 4; i++)
            formato[i] = Byte.toUnsignedInt(laberintoBytes[i]);
        VerificaLaberinto.verificaInicioFormato(formato);
        Integer renglones = Byte.toUnsignedInt(laberintoBytes[4]);
        Integer columnas = Byte.toUnsignedInt(laberintoBytes[5]);
        Integer[] casillas = UtilEntrada.casillas(laberintoBytes);
        laberinto = new Laberinto(casillas,renglones,columnas,null);
    }

    /* Metodo privado para verificar el archivo recibido. */
    private void verificaEntrada() throws Exception {
        try {
            is = new FileInputStream(archivo);
        } catch (IOException ioe) {
            UtilEntrada.error("no se pudo cargar el archivo");
        }
    }
}
