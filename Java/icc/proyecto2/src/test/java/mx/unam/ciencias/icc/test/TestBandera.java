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
 * Clase para las pruebas unitarias de la clase {@link
 * Bandera}.
 */
public class TestBandera {

    /** Expiración para que ninguna prueba tarde más de 5 segundos. */
    @Rule public Timeout expiracion = Timeout.seconds(5);
    /** Directorio para archivos temporales. */
    @Rule public TemporaryFolder directorio = new TemporaryFolder();

    /** cadena con orden en específico */
    private String texto = "dada una Máquina de Turing M" + "\n" + 
                            "y una palabra w," + "\n" +
                            "determinar si M" + "\n" + 
                            "terminará en un número finito de pasos" + "\n" +
                            "cuando es ejecutada usando w" + "\n" + 
                            "como dato de entrada.";

    /** Otra cadena con orden específico */
    private String texto2 = "árbol bonito" + "\n" +
                            "arriba en el cielo" + "\n" +
                            "acercandose al azul" + "\n" +
                            "convirtiendose en cian" + "\n" +
                            "como el color menta" + "\n" +
                            "que en mis sueños soñé" + "\n" +
                            "tan bonito anocheser";

    /* El archivo temporal de texto Turing */
    private String archivo;
    /* Lista para las lineas del texto de Turing */
    Lista<Linea> lista1 = new Lista<>();
    /* El archivo temporal de texto inventado */
    private String archivo2;
    /* Lista para las lineas del texto inventado */
    Lista<Linea> lista2 = new Lista<>();

    /**
     * Metodo que se ejcuta antes de cada prueba unitaria; crea
     * los archivos con los textos.
     */
    @Before public void arma() {
        File t = null;
        File f = null;
        try {
            t = directorio.newFile("texto.txt");
            f = directorio.newFile("texto2.txt");
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

    /**
     * Prueba unitaria para {@link Bandera#revisaBanderas} y
     * para {@link Bandera#verificaBanderas}.
     */
    @Test public void testRevisaVerificaBanderas() {
        String[] entradaTuring = { "-r" , "-o", archivo+"Turing.txt", archivo };
        String[] entradaTuringVolteada = { "-o" , archivo+"Turing2.txt", archivo , "-r" };
        String[] entradaInventada= { "-o" , archivo+"Inventada.txt", archivo2 , "-r" };
        String[] entradaInventadaVolteada = { "-r", archivo2, "-o", archivo+"Inventada2.txt" };
        String[] entradaTuring2 = { "-o" , archivo+"ejemploTuring.txt", archivo};
        String[] entradaInventada2 = { "-o" , archivo+"ejemploInventada.txt", archivo2};
        Bandera b = new Bandera();
        Bandera b2 = new Bandera();
        Bandera b3 = new Bandera();
        Bandera b4 = new Bandera();
        Bandera b5 = new Bandera();
        Bandera b6 = new Bandera();
        b.revisaBanderas(entradaTuring);
        b2.revisaBanderas(entradaTuringVolteada);
        b3.revisaBanderas(entradaTuring2);
        b4.revisaBanderas(entradaInventada);
        b5.revisaBanderas(entradaInventadaVolteada);
        b6.revisaBanderas(entradaInventada2);
        b.verificaBanderas();
        b2.verificaBanderas();
        b3.verificaBanderas();
        b4.verificaBanderas();
        b5.verificaBanderas();
        b6.verificaBanderas();
        Lista<Linea> p = new Lista<>();
        Lista<Linea> p1 = new Lista<>();
        Lista<Linea> p2 = new Lista<>();
        Lista<Linea> p3 = new Lista<>();
        Lista<Linea> p4 = new Lista<>();
        Lista<Linea> p5 = new Lista<>();
        Lista<Linea> p6 = new Lista<>();
        try {
            BufferedReader in =
                new BufferedReader(
                    new InputStreamReader(
                        new FileInputStream(archivo+"Turing.txt")));
            BufferedReader in2 =
                new BufferedReader(
                    new InputStreamReader(
                        new FileInputStream(archivo+"Turing2.txt")));
            BufferedReader in3 =
                new BufferedReader(
                    new InputStreamReader(
                        new FileInputStream(archivo+"Inventada.txt")));
            BufferedReader in4 =
                new BufferedReader(
                    new InputStreamReader(
                        new FileInputStream(archivo+"Inventada2.txt")));
            BufferedReader in5 =
                new BufferedReader(
                    new InputStreamReader(
                        new FileInputStream(archivo+"ejemploTuring.txt")));
            BufferedReader in6 =
                new BufferedReader(
                    new InputStreamReader(
                        new FileInputStream(archivo+"ejemploInventada.txt")));
            UtilOrden.leeTexto(in,p);
            UtilOrden.leeTexto(in2,p2);
            UtilOrden.leeTexto(in3,p3);
            UtilOrden.leeTexto(in4,p4);
            UtilOrden.leeTexto(in5,p5);
            UtilOrden.leeTexto(in6,p6);
        } catch (IOException ioe) {
            Assert.fail();
        }
        Assert.assertFalse(UtilOrden.verifica(p,p5));
        Assert.assertFalse(UtilOrden.verifica(p5,p6));
        Assert.assertFalse(UtilOrden.verifica(p4,p6));
        Assert.assertFalse(UtilOrden.verifica(p5,lista1));
        Assert.assertFalse(UtilOrden.verifica(p6,lista2));
        lista1 = Lista.mergeSort(lista1).reversa();
        lista2 = Lista.mergeSort(lista2).reversa();
        Assert.assertTrue(UtilOrden.verifica(p5,lista1));
        Assert.assertTrue(UtilOrden.verifica(p6,lista2));
        Assert.assertTrue(UtilOrden.verifica(p3,p4));
        Assert.assertTrue(UtilOrden.verifica(p,p2));
    }
}

