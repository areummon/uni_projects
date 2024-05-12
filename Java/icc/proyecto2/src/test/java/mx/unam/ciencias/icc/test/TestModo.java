package mx.unam.ciencias.icc.test;

import mx.unam.ciencias.icc.Arreglos;
import mx.unam.ciencias.icc.Lista;
import mx.unam.ciencias.icc.proyecto2.Linea;
import mx.unam.ciencias.icc.proyecto2.Entrada;
import mx.unam.ciencias.icc.proyecto2.Bandera;
import mx.unam.ciencias.icc.proyecto2.Modo;
import mx.unam.ciencias.icc.proyecto2.Opciones;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;
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
 * Clase para las pruebas unitarias de la clase {@link
 * Modo}.
 */
public class TestModo {
    
    /** Expiración para que ninguna prueba tarde más de 5 segundos. */
    @Rule public Timeout expiracion = Timeout.seconds(5);
    /** Directorio para archivos temporales. */
    @Rule public TemporaryFolder directorio = new TemporaryFolder();

    /** Cadena con orden en específico. */
    private String texto = "Quedarme en el medio" + "\n" +
                           "me gustas un poco, no quiero acertijos" + "\n" +
                           "dilo también" + "\n" +
                           "oh, di lo mismo" + "\n" +
                           "no puedo esperar hasta mañana" + "\n" +
                           "así que dilo también" + "\n" +
                           "No quiero" + "\n" +
                           "caminar en este laberinto" + "\n" +
                           "no es lo que sepa todo, pero" + "\n" +
                           "solo quiero que lo digas también" + "\n" +
                           "oh, dilo también" + "\n" +
                           "te quiero tanto, te quiero" + "\n" +
                           "Dilo también" + "\n";

    /** Otra cadena con orden específico. */
    private String texto2 = "Aquél cuaderno de cuadros" + "\n" +
                            "que en sus líneas azules" + "\n" +
                            "formaban una nube blanca" + "\n" +
                            "en cuyas lineas y círculos grises" + "\n" +
                            "yacía un árbol" + "\n" +
                            "esperando pacientemente" + "\n" +
                            "a ser demostrado" + "\n";

    /* El archivo temporal del texto ditto */
    private String archivo;
    /* Lista para las lineas del texto ditto */
    Lista<Linea> lista1 = new Lista<>();
    /* El archivo temporal de texto inventado */
    private String archivo2;
    /* Lista para las lineas del texto inventado */
    Lista<Linea> lista2 = new Lista<>();
    /* El contenido de la salida de la terminal */
    private final ByteArrayOutputStream contenidoSalida = new ByteArrayOutputStream();
    /* El contenido de la salida original de la terminal */
    private final PrintStream salidaOriginal = System.out;
    
    /**
     * Metodo que se ejcuta antes de cada prueba unitaria; crea
     * los archivos con los textos e inicializa la entrada de la terminal.
     */
    @Before public void arma() {
        System.setOut(new PrintStream(contenidoSalida));
        File t = null;
        File f = null;
        try {
            t = directorio.newFile("ditto.txt");
            f = directorio.newFile("texto.txt");
            archivo = t.getAbsolutePath();
            archivo2 = f.getAbsolutePath();
            BufferedWriter out =
                new BufferedWriter(
                        new OutputStreamWriter(
                            new FileOutputStream(archivo)));
            BufferedWriter out2 =
                new BufferedWriter(
                        new OutputStreamWriter(
                            new FileOutputStream(archivo2)));
            UtilOrden.escribe(out,texto);
            UtilOrden.escribe(out2,texto2);
        } catch (IOException ioe) {
            Assert.fail();
        }
        try {
            BufferedReader in =
                new BufferedReader(
                    new InputStreamReader(
                        new FileInputStream(archivo)));
            BufferedReader in2 =
                new BufferedReader(
                    new InputStreamReader(
                        new FileInputStream(archivo2)));
            UtilOrden.leeTexto(in,lista1);
            UtilOrden.leeTexto(in2,lista2);
        } catch (IOException ioe) {
            Assert.fail();
        }
    }
   
    /* Metodo que se ejecuta despues para restaurar 
     * lo que se imprimio en la terminal y poder comprobar la salida.
     */
    @After
    public void restauraFlujo() {
        System.setOut(salidaOriginal);
    }

    /**
     * Prueba unitaria para {@link Modo#ejecutaModo()}
     */
    @Test public void testEjecutaModo() {
        Modo modo1 = new Modo(lista1, archivo+"ejemploDitto.txt");
        Modo modo2 = new Modo(lista1, archivo+"ejemploDitto2.txt");
        Modo modo3 = new Modo(lista2, archivo2+"ejemploNube.txt");
        Modo modo4 = new Modo(lista2, archivo2+"ejemploNube2.txt");
        modo1.ejecutaModo(Opciones.REVERSA_CON_IDENTIFICADOR);
        modo2.ejecutaModo(Opciones.IDENTIFICADOR);
        modo3.ejecutaModo(Opciones.REVERSA_CON_IDENTIFICADOR);
        modo4.ejecutaModo(Opciones.IDENTIFICADOR);
        Lista<Linea> listaModo1 = new Lista<>();
        Lista<Linea> listaModo2 = new Lista<>();
        Lista<Linea> listaModo3 = new Lista<>();
        Lista<Linea> listaModo4 = new Lista<>();
        try {
            BufferedReader in =
                new BufferedReader(
                    new InputStreamReader(
                        new FileInputStream(archivo+"ejemploDitto.txt")));
            BufferedReader in2 =
                new BufferedReader(
                    new InputStreamReader(
                        new FileInputStream(archivo+"ejemploDitto2.txt")));
            BufferedReader in3 =
                new BufferedReader(
                    new InputStreamReader(
                        new FileInputStream(archivo2+"ejemploNube.txt")));
            BufferedReader in4 =
                new BufferedReader(
                    new InputStreamReader(
                        new FileInputStream(archivo2+"ejemploNube2.txt")));
            UtilOrden.leeTexto(in,listaModo1);
            UtilOrden.leeTexto(in2,listaModo2);
            UtilOrden.leeTexto(in3,listaModo3);
            UtilOrden.leeTexto(in4,listaModo4);
        } catch (IOException ioe) {
            Assert.fail();
        }
        Assert.assertFalse(UtilOrden.verifica(lista1,listaModo2));
        Assert.assertFalse(UtilOrden.verifica(listaModo1,listaModo2));
        Assert.assertFalse(UtilOrden.verifica(lista2,listaModo4));
        Assert.assertFalse(UtilOrden.verifica(listaModo3,listaModo4));
        lista1 = Lista.mergeSort(lista1);
        lista2 = Lista.mergeSort(lista2);
        Assert.assertTrue(UtilOrden.verifica(lista1,listaModo1));
        Assert.assertTrue(UtilOrden.verifica(lista1.reversa(),listaModo2));
        Assert.assertTrue(UtilOrden.verifica(lista2,listaModo3));
        Assert.assertTrue(UtilOrden.verifica(lista2.reversa(),listaModo4));
        
        String dittoOrdenado = UtilOrden.guardaCadena(lista1);
        String dittoReves = UtilOrden.guardaCadena(lista1.reversa());
        String nubeOrdenado = UtilOrden.guardaCadena(lista2);
        String nubeReves = UtilOrden.guardaCadena(lista2.reversa());
        modo1.ejecutaModo(Opciones.IMPRIME);
        Assert.assertEquals(dittoOrdenado, contenidoSalida.toString());
        modo1.ejecutaModo(Opciones.REVERSA_SIN_IDENTIFICADOR);
        Assert.assertEquals(dittoOrdenado+dittoReves, contenidoSalida.toString());
        modo3.ejecutaModo(Opciones.IMPRIME);
        Assert.assertEquals(dittoOrdenado+dittoReves+
                            nubeOrdenado, contenidoSalida.toString());
        modo3.ejecutaModo(Opciones.REVERSA_SIN_IDENTIFICADOR);
        Assert.assertEquals(dittoOrdenado+dittoReves+
                            nubeOrdenado+nubeReves, contenidoSalida.toString());
    }
}
