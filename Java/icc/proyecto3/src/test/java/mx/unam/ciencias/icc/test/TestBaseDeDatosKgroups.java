package mx.unam.ciencias.icc.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Random;
import mx.unam.ciencias.icc.BaseDeDatos;
import mx.unam.ciencias.icc.BaseDeDatosKgroups;
import mx.unam.ciencias.icc.CampoKgroup;
import mx.unam.ciencias.icc.Kgroup;
import mx.unam.ciencias.icc.EscuchaBaseDeDatos;
import mx.unam.ciencias.icc.EventoBaseDeDatos;
import mx.unam.ciencias.icc.Lista;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

/**
 * Clase para pruebas unitarias de la clase BaseDeDatosKgroups.
 */
public class TestBaseDeDatosKgroups {

    /** Expiración para que ninguna prueba tarde más de 5 segundos. */
    @Rule public Timeout expiracion = Timeout.seconds(5);

    /* Generador de números aleatorios. */
    private Random random;
    /*Base de datos de grupos de kpop. */
    private BaseDeDatosKgroups bdd;
    /* Número total de grupos de kpop. */
    private int total;

    /**
     * Crea un generador de números aleatorios para cada prueba 
     * y una base de datos de grupos de kpop.
     */
    public TestBaseDeDatosKgroups() {
        random = new Random();
        bdd = new BaseDeDatosKgroups();
        total = 2 + random.nextInt(100);
    }

    /**
     * Prueba unitaria para {@link BaseDeDatosKgroups#BaseDeDatosKgroups}.
     */
    @Test public void testConstructor() {
        Lista kgroups = bdd.getRegistros();
        Assert.assertFalse(kgroups == null);
        Assert.assertTrue(kgroups.getLongitud() == 0);
        Assert.assertTrue(bdd.getNumRegistros() == 0);
    }

    /**
     * Prueba unitaria para {@link BaseDeDatos#getNumRegistros}.
     */
    @Test public void testGetNumRegistros() {
        Assert.assertTrue(bdd.getNumRegistros() == 0);
        for (int i = 0; i < total; i++) {
            Kgroup k = TestKgroup.kgroupAleatorio();
            bdd.agregaRegistro(k);
            Assert.assertTrue(bdd.getNumRegistros() == i+1);
        }
        Assert.assertTrue(bdd.getNumRegistros() == total);
    }

    /**
     * Prueba unitaria para {@link BaseDeDatos#getRegistros}.
     */
    @Test public void testGetRegistros() {
        Lista<Kgroup> l = bdd.getRegistros();
        Lista<Kgroup> r = bdd.getRegistros();
        Assert.assertTrue(l.equals(r));
        Assert.assertFalse(l == r);
        Kgroup[] kgroups = new Kgroup[total];
        for (int i = 0; i < total; i++) {
            kgroups[i] = TestKgroup.kgroupAleatorio();
            bdd.agregaRegistro(kgroups[i]);
        }
        l = bdd.getRegistros();
        int c = 0;
        for (Kgroup kgroup : l)
            Assert.assertTrue(kgroups[c++].equals(kgroup));
        l.elimina(kgroups[0]);
        Assert.assertFalse(l.equals(bdd.getRegistros()));
        Assert.assertFalse(l.getLongitud() == bdd.getNumRegistros());
    }

    /**
     * Prueba unitaria para {@link BaseDeDatos#agregaRegistro}.
     */
    @Test public void testAgregaRegistro() {
        for (int i = 0; i < total; i++) {
            Kgroup k = TestKgroup.kgroupAleatorio();
            Assert.assertFalse(bdd.getRegistros().contiene(k));
            bdd.agregaRegistro(k);
            Assert.assertTrue(bdd.getRegistros().contiene(k));
            Lista l = bdd.getRegistros();
            Assert.assertTrue(l.get(l.getLongitud() -1).equals(k));
        }
    }

    /**
     * Prueba unitaria para {@link BaseDeDatos#eliminaRegistro}.
     */
    @Test public void testEliminaRegistro() {
        int in = random.nextInt(10000);
        for (int i = 0; i < total; i++) {
            Kgroup k = TestKgroup.kgroupAleatorio(in + i);
            bdd.agregaRegistro(k);
        }
        while (bdd.getNumRegistros() > 0) {
            int i = random.nextInt(bdd.getNumRegistros());
            Kgroup k = (Kgroup)bdd.getRegistros().get(i);
            Assert.assertTrue(bdd.getRegistros().contiene(k));
            bdd.eliminaRegistro(k);
            Assert.assertFalse(bdd.getRegistros().contiene(k));
        }
    }

    /**
     * Prueba unitaria para {@link BaseDeDatos#limpia}.
     */
    @Test public void testLimpia() {
        for (int i = 0; i < total; i++) {
            Kgroup k = TestKgroup.kgroupAleatorio();
            bdd.agregaRegistro(k);
        }
        Lista registros = bdd.getRegistros();
        Assert.assertFalse(registros.esVacia());
        Assert.assertFalse(registros.getLongitud() == 0);
        bdd.limpia();
        registros = bdd.getRegistros();
        Assert.assertTrue(registros.esVacia());
        Assert.assertTrue(registros.getLongitud() == 0);
    }

    /**
     * Prueba unitaria para {@link BaseDeDatos#guarda}.
     */
    @Test public void testGuarda() {
        for (int i = 0; i < total; i++) {
            Kgroup k = TestKgroup.kgroupAleatorio();
            bdd.agregaRegistro(k);
        }
        String save = "";
        try {
            StringWriter swOut = new StringWriter();
            BufferedWriter out = new BufferedWriter(swOut);
            bdd.guarda(out);
            out.close();
            save = swOut.toString();
        } catch (IOException ioe) {
            Assert.fail();
        }
        String[] lines = save.split("\n");
        Assert.assertTrue(lines.length == total);
        Lista<Kgroup> l = bdd.getRegistros();
        int c = 0;
        for (Kgroup kgroup : l) {
            Kgroup k = kgroup;
            String kg = String.format("%s\t%d\t%d\t%s\t%d\t%s\t%s\t%s\t%2.2f",
                                    k.getName(), k.getYear(), k.getGeneration(),
                                    k.getType(), k.getMembers(), k.getGenre(), k.getLabel(),
                                    k.getSong(), k.getReview());
            Assert.assertTrue(lines[c++].equals(kg));
        }
    }

    /**
     * Prueba unitaria para {@link BaseDeDatos#carga}.
     */
    @Test public void testCarga() {
        int ini = random.nextInt(100000);
        String input = "";
        Kgroup[] kgroups = new Kgroup[total];
        for (int i = 0; i < total; i++) {
            kgroups[i] = TestKgroup.kgroupAleatorio(ini + i);
            input += String.format("%s\t%d\t%d\t%s\t%d\t%s\t%s\t%s\t%2.2f\n",
                                    kgroups[i].getName(),
                                    kgroups[i].getYear(),
                                    kgroups[i].getGeneration(),
                                    kgroups[i].getType(),
                                    kgroups[i].getMembers(), 
                                    kgroups[i].getGenre(), 
                                    kgroups[i].getLabel(),
                                    kgroups[i].getSong(),
                                    kgroups[i].getReview());
            bdd.agregaRegistro(kgroups[i]);
        }
        try {
            StringReader srInt = new StringReader(input);
            BufferedReader in = new BufferedReader(srInt, 4096);
            bdd.carga(in);
            in.close();
        } catch (IOException ioe) {
            Assert.fail();
        }
        Assert.assertTrue(bdd.getNumRegistros() == total);
        int c = 0;
        Lista<Kgroup> l = bdd.getRegistros();
        for (Kgroup kgroup : l)
            Assert.assertTrue(kgroups[c++].equals(kgroup));
        input = String.format("%s\t%d\t%d\t%s\t%d\t%s\t%s\t%s\t%2.2f\n",
                                    kgroups[0].getName(),
                                    kgroups[0].getYear(),
                                    kgroups[0].getGeneration(),
                                    kgroups[0].getType(),
                                    kgroups[0].getMembers(), 
                                    kgroups[0].getGenre(), 
                                    kgroups[0].getLabel(),
                                    kgroups[0].getSong(),
                                    kgroups[0].getReview());
        input += " \n";
        input += String.format("%s\t%d\t%d\t%s\t%d\t%s\t%s\t%s\t%2.2f\n",
                                    kgroups[1].getName(),
                                    kgroups[1].getYear(),
                                    kgroups[1].getGeneration(),
                                    kgroups[1].getType(),
                                    kgroups[1].getMembers(), 
                                    kgroups[1].getGenre(), 
                                    kgroups[1].getLabel(),
                                    kgroups[1].getSong(),
                                    kgroups[1].getReview());
        try {
            StringReader srInt = new StringReader(input);
            BufferedReader in = new BufferedReader(srInt, 4096);
            bdd.carga(in);
            in.close();
        } catch (IOException ioe) {
            Assert.fail();
        }
        Assert.assertTrue(bdd.getNumRegistros() == 1);
        input = "";
        try {
            StringReader srInt = new StringReader(input);
            BufferedReader in = new BufferedReader(srInt, 4096);
            bdd.carga(in);
            in.close();
        } catch (IOException ioe) {
            Assert.fail();
        }
        Assert.assertTrue(bdd.getNumRegistros() == 0);
    }

    /**
     * Prueba unitaria para {@link BaseDeDatos#creaRegistro}.
     */
    @Test public void testCreaRegistro() {
        Kgroup k = (Kgroup)bdd.creaRegistro();
        Assert.assertTrue(k.getName() == null);
        Assert.assertTrue(k.getYear() == 0);
        Assert.assertTrue(k.getGeneration() == 0);
        Assert.assertTrue(k.getType() == null);
        Assert.assertTrue(k.getMembers() == 0);
        Assert.assertTrue(k.getGenre() == null);
        Assert.assertTrue(k.getLabel() == null);
        Assert.assertTrue(k.getSong() == null);
        Assert.assertTrue(k.getReview() == 0.0);
    }

    /**
     * Prueba unitaria para {@link BaseDeDatosKgroups#buscaRegistros}.
     */
    @Test public void testBuscaRegistros() {
        Kgroup[] kgroups = new Kgroup[total];
        int ini = 1990 + random.nextInt(30);
        for (int i = 0; i < total; i++) {
            Kgroup k = new Kgroup(String.valueOf(ini+i),
                    ini+i, i, String.valueOf(ini+i),
                    i, String.valueOf(ini+i),
                    String.valueOf(ini+i),
                    String.valueOf(ini+i), (i * 10.0) / total);
            kgroups[i] = k;
            bdd.agregaRegistro(k);
        }

        Kgroup kgroup;
        Lista<Kgroup> l;
        int i;

        for (int k = 0; k < total/10 +3; k++) {
            i = random.nextInt(total);
            kgroup = kgroups[i];

            String name = kgroup.getName();
            l = bdd.buscaRegistros(CampoKgroup.NAME, name);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(kgroup));
            for (Kgroup e : l) 
                Assert.assertTrue(e.getName().indexOf(name) > -1);

            int n = name.length();
            String nv = name.substring(random.nextInt(2),
                                       2 + random.nextInt(n-2));
            l = bdd.buscaRegistros(CampoKgroup.NAME, nv);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(kgroup));
            for (Kgroup e : l) 
                Assert.assertTrue(e.getName().indexOf(nv) > -1);

            Integer year = Integer.valueOf(kgroup.getYear());
            l = bdd.buscaRegistros(CampoKgroup.YEAR, year);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(kgroup));
            for (Kgroup e : l)
                Assert.assertTrue(e.getYear() >= year.intValue());

            Integer bc = Integer.valueOf(year.intValue() - 10);
            l = bdd.buscaRegistros(CampoKgroup.YEAR, bc);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(kgroup));
            for (Kgroup e : l)
                Assert.assertTrue(e.getYear() >= bc.intValue());

            Integer generation = Integer.valueOf(kgroup.getGeneration());
            l = bdd.buscaRegistros(CampoKgroup.GENERATION, generation);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(kgroup));
            for (Kgroup e : l)
                Assert.assertTrue(e.getGeneration() >= generation.intValue());

            Integer gen = Integer.valueOf(generation.intValue() - 10);
            l = bdd.buscaRegistros(CampoKgroup.GENERATION, gen);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(kgroup));
            for (Kgroup e : l)
                Assert.assertTrue(e.getGeneration() >= gen.intValue());

            String type = kgroup.getType();
            l = bdd.buscaRegistros(CampoKgroup.TYPE, type);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(kgroup));
            for (Kgroup e : l)
                Assert.assertTrue(e.getType().indexOf(type) > -1);

            int t = type.length();
            String typ = type.substring(random.nextInt(2),
                                       2 + random.nextInt(t-2));
            l = bdd.buscaRegistros(CampoKgroup.TYPE, typ);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(kgroup));
            for (Kgroup e : l)
                Assert.assertTrue(e.getType().indexOf(typ) > -1);

            Integer members = Integer.valueOf(kgroup.getMembers());
            l = bdd.buscaRegistros(CampoKgroup.MEMBERS, members);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(kgroup));
            for (Kgroup e : l)
                Assert.assertTrue(e.getMembers() >= members.intValue());

            Integer mem = Integer.valueOf(members.intValue() - 10);
            l = bdd.buscaRegistros(CampoKgroup.MEMBERS, mem);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(kgroup));
            for (Kgroup e : l)
                Assert.assertTrue(e.getMembers() >= mem.intValue());

            String genre = kgroup.getGenre();
            l = bdd.buscaRegistros(CampoKgroup.GENRE, genre);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(kgroup));
            for (Kgroup e : l)
                Assert.assertTrue(e.getGenre().indexOf(genre) > -1);

            int p = genre.length();
            String genr = genre.substring(random.nextInt(2),
                                       2 + random.nextInt(p-2));
            l = bdd.buscaRegistros(CampoKgroup.GENRE, genr);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(kgroup));
            for (Kgroup e : l)
                Assert.assertTrue(e.getGenre().indexOf(genr) > -1);

            String label = kgroup.getLabel();
            l = bdd.buscaRegistros(CampoKgroup.LABEL, label);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(kgroup));
            for (Kgroup e : l)
                Assert.assertTrue(e.getLabel().indexOf(label) > -1);

            int q = label.length();
            String lab = label.substring(random.nextInt(2),
                                       2 + random.nextInt(q-2));
            l = bdd.buscaRegistros(CampoKgroup.LABEL, lab);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(kgroup));
            for (Kgroup e : l)
                Assert.assertTrue(e.getLabel().indexOf(lab) > -1);

            String likes = kgroup.getSong();
            l = bdd.buscaRegistros(CampoKgroup.SONG, likes);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(kgroup));
            for (Kgroup e : l)
                Assert.assertTrue(e.getSong().indexOf(likes) > -1);

            int a = likes.length();
            String lik = likes.substring(random.nextInt(2),
                                       2 + random.nextInt(a-2));
            l = bdd.buscaRegistros(CampoKgroup.SONG, lik);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(kgroup));
            for (Kgroup e : l)
                Assert.assertTrue(e.getSong().indexOf(lik) > -1);

            Double review = Double.valueOf(kgroup.getReview());
            l = bdd.buscaRegistros(CampoKgroup.REVIEW, review);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(kgroup));
            for (Kgroup e : l)
                Assert.assertTrue(e.getReview() >= review.doubleValue());

            Double ps = Double.valueOf(review.doubleValue() - 3.0);
            l = bdd.buscaRegistros(CampoKgroup.REVIEW, ps);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(kgroup));
            for (Kgroup e : l)
                Assert.assertTrue(e.getReview() >= ps.doubleValue());
        }

        l = bdd.buscaRegistros(CampoKgroup.NAME, "^^^&@@@-nombre");
        Assert.assertTrue(l.esVacia());
        l = bdd.buscaRegistros(CampoKgroup.YEAR,
                               Integer.valueOf(11110));
        Assert.assertTrue(l.esVacia());
        l = bdd.buscaRegistros(CampoKgroup.GENERATION, 
                               Integer.valueOf(10010));
        Assert.assertTrue(l.esVacia());
        l = bdd.buscaRegistros(CampoKgroup.TYPE, "!)~opensuse");
        Assert.assertTrue(l.esVacia());
        l = bdd.buscaRegistros(CampoKgroup.MEMBERS,
                               Integer.valueOf(10010));
        Assert.assertTrue(l.esVacia());
        l = bdd.buscaRegistros(CampoKgroup.GENRE, "123quantum");
        Assert.assertTrue(l.esVacia());
        l = bdd.buscaRegistros(CampoKgroup.LABEL, "~~~!=sdfsa");
        Assert.assertTrue(l.esVacia());
        l = bdd.buscaRegistros(CampoKgroup.SONG, "quantumcomputing");
        Assert.assertTrue(l.esVacia());
        l = bdd.buscaRegistros(CampoKgroup.REVIEW, 
                               Double.valueOf(98.99));
        Assert.assertTrue(l.esVacia());


        l = bdd.buscaRegistros(CampoKgroup.NAME, "");
        Assert.assertTrue(l.esVacia());
        l = bdd.buscaRegistros(CampoKgroup.YEAR,
                               Integer.valueOf(Integer.MAX_VALUE));
        Assert.assertTrue(l.esVacia());
        l = bdd.buscaRegistros(CampoKgroup.GENERATION,
                               Integer.valueOf(Integer.MAX_VALUE));
        Assert.assertTrue(l.esVacia());
        l = bdd.buscaRegistros(CampoKgroup.TYPE, "");
        Assert.assertTrue(l.esVacia());
        l = bdd.buscaRegistros(CampoKgroup.MEMBERS,
                               Integer.valueOf(Integer.MAX_VALUE));
        Assert.assertTrue(l.esVacia());
        l = bdd.buscaRegistros(CampoKgroup.GENRE, "");
        Assert.assertTrue(l.esVacia());
        l = bdd.buscaRegistros(CampoKgroup.LABEL, "");
        Assert.assertTrue(l.esVacia());
        l = bdd.buscaRegistros(CampoKgroup.SONG, "");
        Assert.assertTrue(l.esVacia());
        l = bdd.buscaRegistros(CampoKgroup.REVIEW,
                               Double.valueOf(Double.MAX_VALUE));
        Assert.assertTrue(l.esVacia());

        l = bdd.buscaRegistros(CampoKgroup.NAME, null);
        Assert.assertTrue(l.esVacia());
        l = bdd.buscaRegistros(CampoKgroup.YEAR, null);
        Assert.assertTrue(l.esVacia());
        l = bdd.buscaRegistros(CampoKgroup.GENERATION, null);
        Assert.assertTrue(l.esVacia());
        l = bdd.buscaRegistros(CampoKgroup.TYPE, null);
        Assert.assertTrue(l.esVacia());
        l = bdd.buscaRegistros(CampoKgroup.MEMBERS, null);
        Assert.assertTrue(l.esVacia());
        l = bdd.buscaRegistros(CampoKgroup.GENRE, null);
        Assert.assertTrue(l.esVacia());
        l = bdd.buscaRegistros(CampoKgroup.LABEL, null);
        Assert.assertTrue(l.esVacia());
        l = bdd.buscaRegistros(CampoKgroup.SONG, null);
        Assert.assertTrue(l.esVacia());
        l = bdd.buscaRegistros(CampoKgroup.REVIEW, null);
        Assert.assertTrue(l.esVacia());

        try {
            l = bdd.buscaRegistros(null, null);
            Assert.fail();
        } catch (IllegalArgumentException iae) {}
        }

    /**
     * Prueba unitaria para {@link BaseDeDatos#agregaEscucha}
     */
    @Test public void testAgregaEscucha() {
       int[] c = new int[total];
       for (int i = 0; i < total; i++) {
           final int j = i;
           bdd.agregaEscucha((e, r1, r2) -> c[j]+=2);
       }
       bdd.agregaRegistro(new Kgroup("Perfume", 2004, 2, "girl", 3, "electro-pop",
                                     "Universal-j", "Nee", 5.0));
       for (int i = 0; i < total; i++)
           Assert.assertTrue(c[i] == 2);
    }

    /**
     * Prueba unitaria para {@link BaseDeDatos#eliminaEscucha}
     */
    @Test public void testEliminaEscucha() {
        int[] c = new int[total];
        Lista<EscuchaBaseDeDatos<Kgroup>> escuchas =
            new Lista<>();
        for (int i = 0; i < total; i++) {
            final int j = i;
            EscuchaBaseDeDatos<Kgroup> escucha = (e, r1, r2) -> c[j]+=2;
            bdd.agregaEscucha(escucha);
            escuchas.agregaFinal(escucha);
        }
        int i = 0;
        while (!escuchas.esVacia()) {
            bdd.agregaRegistro(TestKgroup.kgroupAleatorio(i++));
            EscuchaBaseDeDatos<Kgroup> escucha = escuchas.eliminaPrimero();
            bdd.eliminaEscucha(escucha);
        }
        for (i = 0; i < total; i++) 
            Assert.assertTrue(c[i] == 2 + 2*i);
    }
}
