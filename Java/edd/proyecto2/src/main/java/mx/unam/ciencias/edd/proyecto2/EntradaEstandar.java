package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/* Clase concreta para manejar la entrada estandar. */
public class EntradaEstandar extends Entrada {

    /* Define el estado inicial de una entrada estandar */
    public EntradaEstandar() {}

    /**
     * Implementacion concreta del metodo abstracto
     * para verificar la entrada dada.
     */
    public void verificaEntrada() {
        in = new BufferedReader(
                new InputStreamReader(System.in));
    }
}
