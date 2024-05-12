package mx.unam.ciencias.icc.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Random;
import mx.unam.ciencias.icc.CampoKgroup;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

/**
 * Clase para las pruebas unitarias de la enumeracion {@link CampoKgroup}.
 */
public class TestCampoKgroup {

    /** Expiracion para que ninguna prueba tarde mas de 5 segundos. */
    @Rule public Timeout expiracion = Timeout.seconds(5);

    /** Prueba unitaria para {@link CampoKgroup#toString}. */
    @Test public void testToString() {
    String test = CampoKgroup.NAME.toString();
    Assert.assertTrue(test.equals("Nombre"));
    test = CampoKgroup.YEAR.toString();
    Assert.assertTrue(test.equals("Año"));
    test = CampoKgroup.GENERATION.toString();
    Assert.assertTrue(test.equals("Generación"));
    test = CampoKgroup.TYPE.toString();
    Assert.assertTrue(test.equals("Tipo(boy group/girl group)"));
    test = CampoKgroup.MEMBERS.toString();
    Assert.assertTrue(test.equals("Número de miembros"));
    test = CampoKgroup.GENRE.toString();
    Assert.assertTrue(test.equals("Género musical principal"));
    test = CampoKgroup.LABEL.toString();
    Assert.assertTrue(test.equals("Discografica"));
    test = CampoKgroup.LIKES.toString();
    Assert.assertTrue(test.equals("Canción con más me gusta en Melon(멜론)"));
    test = CampoKgroup.REVIEW.toString();
    Assert.assertTrue(test.equals("Mayor calificación dada a un album suyo en RYM"));
    }
}
