package mx.unam.ciencias.icc.test;

import mx.unam.ciencias.icc.Arreglos;
import mx.unam.ciencias.icc.Lista;
import mx.unam.ciencias.icc.proyecto2.Linea;
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

/**
 * Clase para las pruebas unitarias de la clase {@link
 * Linea}.
 */
public class TestLinea {

    /** Expiración para que ninguna prueba tarde más de 5 segundos. */
    @Rule public Timeout expiracion = Timeout.seconds(5);

    /** Prueba unitaria para el constructor de la linea {@link Linea}. */
    @Test public void testConstructorLinea() {
        Linea prueba = new Linea("cadena vacía");
        Linea nueva = new Linea("cadena vacía");
        Assert.assertFalse(prueba.toString().equals("cadena"));
        Assert.assertFalse(prueba.equals(nueva));
        Assert.assertTrue(prueba.toString().equals("cadena vacía"));
        Assert.assertTrue(prueba.toString().equals(nueva.toString()));
    }

    /** Prueba unitaria para {@link Linea#getLinea()}. */
    @Test public void testGetLinea() {
        String cadena = "Minji";
        String cadena2 = "Nakyoung";
        Linea prueba = new Linea("Minji");
        Linea nueva = new Linea("");
        Linea pruebaNueva = new Linea("Nakyoung");
        Assert.assertFalse(prueba.getLinea().equals(cadena2));
        Assert.assertFalse(prueba.getLinea().equals(nueva.getLinea()));
        Assert.assertTrue(prueba.getLinea().equals(cadena));
        Assert.assertTrue(pruebaNueva.getLinea().equals(cadena2));
    }

    /**
     * Prueba unitaria para {@link Linea#toString()}.
     */
    @Test public void testToString() {
        String cadena = "bias favorita";
        Linea prueba = new Linea("bias favorita");
        Linea nueva = new Linea(cadena);
        Assert.assertFalse(prueba.toString().equals("Stayc"));
        Assert.assertTrue(prueba.toString().equals(cadena));
        Assert.assertTrue(prueba.toString().equals(nueva.toString()));
    }

    /**
     * Prueba unitaria para {@link Linea#compareTo(Linea n)}.
     */
    @Test public void compareTo() {
        String cadena = "cabana";
        String cadenaRara = "Ćábáñá";
        String bienEscrita = "Cabaña";
        String cadena2 = "grafica";
        String cadenaRara2 = "ǴŕáFícA";
        String bienEscrita2 = "Gráfica";
        Linea linea = new Linea(cadena);
        Linea lineaRara = new Linea(cadenaRara);
        Linea lineaBienEscrita = new Linea(bienEscrita);
        Linea linea2 = new Linea(cadena2);
        Linea lineaRara2 = new Linea(cadenaRara2);
        Linea lineaBienEscrita2 = new Linea(bienEscrita2);
        Assert.assertFalse(linea.compareTo(linea2) == 0);
        Assert.assertTrue(linea.compareTo(lineaRara) == 0);
        Assert.assertTrue(lineaRara.compareTo(lineaBienEscrita) == 0);
        Assert.assertTrue(lineaBienEscrita.compareTo(linea) == 0);
        Assert.assertFalse(linea2.compareTo(lineaRara) == 0);
        Assert.assertTrue(linea2.compareTo(lineaRara2) == 0);
        Assert.assertTrue(lineaRara2.compareTo(lineaBienEscrita2) == 0);
        Assert.assertTrue(lineaBienEscrita2.compareTo(linea2) == 0);
    }
}
