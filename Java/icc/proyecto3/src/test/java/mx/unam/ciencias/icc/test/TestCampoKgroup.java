package mx.unam.ciencias.icc.test;

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
    Assert.assertTrue(test.equals("Año Debut"));
    test = CampoKgroup.GENERATION.toString();
    Assert.assertTrue(test.equals("Generación"));
    test = CampoKgroup.TYPE.toString();
    Assert.assertTrue(test.equals("Tipo"));
    test = CampoKgroup.MEMBERS.toString();
    Assert.assertTrue(test.equals("# Miembros"));
    test = CampoKgroup.GENRE.toString();
    Assert.assertTrue(test.equals("Género Musical"));
    test = CampoKgroup.LABEL.toString();
    Assert.assertTrue(test.equals("Discográfica"));
    test = CampoKgroup.SONG.toString();
    Assert.assertTrue(test.equals("Canción Representativa"));
    test = CampoKgroup.REVIEW.toString();
    Assert.assertTrue(test.equals("Calificación General"));
    }
}
