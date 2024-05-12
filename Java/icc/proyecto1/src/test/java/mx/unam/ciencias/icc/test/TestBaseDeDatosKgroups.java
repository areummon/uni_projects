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
import mx.unam.ciencias.icc.Lista;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

/**
 * Clase para pruebas unitarias de la clase {@link BaseDeDatosKgroups}.
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

    /* Enumeración espuria. */
    private enum X {
        /* Campo espurio. */
        A;
    }
    
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
     * Prueba unitaria para {@link BaseDeDatosKgroups#BaseDeDatosKgroups}
     */
    @Test public void testConstructor() {
        Lista kgroups = bdd.getRegistros();
        Assert.assertFalse(kgroups == null);
        Assert.assertTrue(kgroups.getLongitud() == 0);
        Assert.assertTrue(bdd.getNumRegistros() == 0);
    }

    /**
     * Prueba unitaria para {@link BaseDeDatos#getNumRegistros}
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
        Lista l = bdd.getRegistros();
        Lista r = bdd.getRegistros();
        Assert.assertTrue(l.equals(r));
        Assert.assertFalse(l == r);
        Kgroup[] kgroups = new Kgroup[total];
        for (int i = 0; i < total; i++) {
            kgroups[i] = TestKgroup.kgroupAleatorio();
            bdd.agregaRegistro(kgroups[i]);
        }
        l = bdd.getRegistros();
        int c = 0;
        Lista.Nodo nodo = l.getCabeza();
        while (nodo != null) {
            Assert.assertTrue(kgroups[c++].equals(nodo.get()));
            nodo = nodo.getSiguiente();
        }
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
     * Prueba unitaria para {@link BaseDeDatos#eliminaRegistro).
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
        Lista l = bdd.getRegistros();
        int c = 0;
        Lista.Nodo aux = l.getCabeza();
        while (aux != null) {
            Kgroup k = (Kgroup)aux.get();
            String kg = String.format("%s\t%d\t%d\t%s\t%d\t%s\t%s\t%s\t%2.2f",
                                    k.getName(), k.getYear(), k.getGeneration(),
                                    k.getType(), k.getMembers(), k.getGenre(), k.getLabel(),
                                    k.getLikes(), k.getReview());
            Assert.assertTrue(lines[c++].equals(kg));
            aux = aux.getSiguiente();
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
                                    kgroups[i].getLikes(),
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
        Lista l = bdd.getRegistros();
        Lista.Nodo aux = l.getCabeza();
        while (aux != null) {
            Assert.assertTrue(kgroups[c++].equals(aux.get()));
            aux = aux.getSiguiente();
        }
        input = String.format("%s\t%d\t%d\t%s\t%d\t%s\t%s\t%s\t%2.2f\n",
                                    kgroups[0].getName(),
                                    kgroups[0].getYear(),
                                    kgroups[0].getGeneration(),
                                    kgroups[0].getType(),
                                    kgroups[0].getMembers(), 
                                    kgroups[0].getGenre(), 
                                    kgroups[0].getLabel(),
                                    kgroups[0].getLikes(),
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
                                    kgroups[1].getLikes(),
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
        /* generation == 1 porque es un campo que se evalua en un enum
         * y ya que el año es 0, bajo el constructor de 
         * generation quedara evaluado en 1*/
        Assert.assertTrue(k.getGeneration() == 1);
        Assert.assertTrue(k.getType() == null);
        Assert.assertTrue(k.getMembers() == 0);
        Assert.assertTrue(k.getGenre() == null);
        Assert.assertTrue(k.getLabel() == null);
        Assert.assertTrue(k.getLikes() == null);
        Assert.assertTrue(k.getReview() == 0.0);
    }

    /**
     * Prueba unitaria para {@link BaseDeDatosKgroups#buscaRegistros}
     */
    @Test public void testBuscaRegistros() {
        Kgroup[] kgroups = new Kgroup[total];
        int ini = 1990 + random.nextInt(30);
        for (int i = 0; i < total; i++) {
            Kgroup k = new Kgroup(String.valueOf(ini+i),
                    ini+i, ini+i, String.valueOf(ini+i),
                    i, String.valueOf(ini+i),
                    String.valueOf(ini+i),
                    String.valueOf(ini+i), (i * 10.0) / total);
            kgroups[i] = k;
            bdd.agregaRegistro(k);
        }

        Kgroup kgroup;
        Lista l;
        Lista.Nodo aux;
        int i;

        for (int k = 0; k < total/10 +3; k++) {
            i = random.nextInt(total);
            kgroup = kgroups[i];

            String name = kgroup.getName();
            l = bdd.buscaRegistros(CampoKgroup.NAME, name);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(kgroup));
            aux = l.getCabeza();
            while (aux != null) {
                Kgroup g = (Kgroup)aux.get();
                Assert.assertTrue(g.getName().indexOf(name) > -1);
                aux = aux.getSiguiente();
            }
            int n = name.length();
            String nv = name.substring(random.nextInt(2),
                                       2 + random.nextInt(n-2));
            l = bdd.buscaRegistros(CampoKgroup.NAME, nv);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(kgroup));
            aux = l.getCabeza();
            while (aux != null) {
                Kgroup g = (Kgroup)aux.get();
                Assert.assertTrue(g.getName().indexOf(nv) > -1);
                aux = aux.getSiguiente();
            }

            Integer year = Integer.valueOf(kgroup.getYear());
            l = bdd.buscaRegistros(CampoKgroup.YEAR, year);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(kgroup));
            aux = l.getCabeza();
            while (aux != null) {
                Kgroup g = (Kgroup)aux.get();
                Assert.assertTrue(g.getYear() >= year.intValue());
                aux = aux.getSiguiente();
            }
            Integer bc = Integer.valueOf(year.intValue() - 10);
            l = bdd.buscaRegistros(CampoKgroup.YEAR, bc);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(kgroup));
            aux = l.getCabeza();
            while (aux != null) {
                Kgroup g = (Kgroup)aux.get();
                Assert.assertTrue(g.getYear() >= bc.intValue());
                aux = aux.getSiguiente();
            }

            Integer generation = Integer.valueOf(kgroup.getGeneration());
            l = bdd.buscaRegistros(CampoKgroup.GENERATION, generation);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(kgroup));
            aux = l.getCabeza();
            while (aux != null) {
                Kgroup g = (Kgroup)aux.get();
                Assert.assertTrue(g.getGeneration() >= generation.intValue());
                aux = aux.getSiguiente();
            }
            Integer gen = Integer.valueOf(generation.intValue() - 10);
            l = bdd.buscaRegistros(CampoKgroup.GENERATION, gen);
            /* Las siguientes 2 pruebas serian falsas pues el campo
             * GENERATION casa si son iguales, pero si se modifica el valor
             * entonces no casará y por consiguiente regresará falso
             */
            Assert.assertFalse(l.getLongitud() > 0);
            Assert.assertFalse(l.contiene(kgroup));
            aux = l.getCabeza();
            while (aux != null) {
                Kgroup g = (Kgroup)aux.get();
                Assert.assertTrue(g.getGeneration() >= gen.intValue());
                aux = aux.getSiguiente();
            }

            String type = kgroup.getType();
            l = bdd.buscaRegistros(CampoKgroup.TYPE, type);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(kgroup));
            aux = l.getCabeza();
            while (aux != null) {
                Kgroup g = (Kgroup)aux.get();
                Assert.assertTrue(g.getType().indexOf(type) > -1);
                aux = aux.getSiguiente();
            }
            int t = type.length();
            String typ = type.substring(random.nextInt(2),
                                       2 + random.nextInt(t-2));
            l = bdd.buscaRegistros(CampoKgroup.TYPE, typ);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(kgroup));
            aux = l.getCabeza();
            while (aux != null) {
                Kgroup g = (Kgroup)aux.get();
                Assert.assertTrue(g.getType().indexOf(typ) > -1);
                aux = aux.getSiguiente();
            }

            Integer members = Integer.valueOf(kgroup.getMembers());
            l = bdd.buscaRegistros(CampoKgroup.MEMBERS, members);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(kgroup));
            aux = l.getCabeza();
            while (aux != null) {
                Kgroup g = (Kgroup)aux.get();
                Assert.assertTrue(g.getMembers() >= members.intValue());
                aux = aux.getSiguiente();
            }
            Integer mem = Integer.valueOf(members.intValue() - 10);
            l = bdd.buscaRegistros(CampoKgroup.MEMBERS, mem);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(kgroup));
            aux = l.getCabeza();
            while (aux != null) {
                Kgroup g = (Kgroup)aux.get();
                Assert.assertTrue(g.getMembers() >= mem.intValue());
                aux = aux.getSiguiente();
            }

            String genre = kgroup.getGenre();
            l = bdd.buscaRegistros(CampoKgroup.GENRE, genre);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(kgroup));
            aux = l.getCabeza();
            while (aux != null) {
                Kgroup g = (Kgroup)aux.get();
                Assert.assertTrue(g.getGenre().indexOf(genre) > -1);
                aux = aux.getSiguiente();
            }
            int p = genre.length();
            String genr = genre.substring(random.nextInt(2),
                                       2 + random.nextInt(p-2));
            l = bdd.buscaRegistros(CampoKgroup.GENRE, genr);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(kgroup));
            aux = l.getCabeza();
            while (aux != null) {
                Kgroup g = (Kgroup)aux.get();
                Assert.assertTrue(g.getGenre().indexOf(genr) > -1);
                aux = aux.getSiguiente();
            }

            String label = kgroup.getLabel();
            l = bdd.buscaRegistros(CampoKgroup.LABEL, label);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(kgroup));
            aux = l.getCabeza();
            while (aux != null) {
                Kgroup g = (Kgroup)aux.get();
                Assert.assertTrue(g.getLabel().indexOf(label) > -1);
                aux = aux.getSiguiente();
            }
            int q = label.length();
            String lab = label.substring(random.nextInt(2),
                                       2 + random.nextInt(q-2));
            l = bdd.buscaRegistros(CampoKgroup.LABEL, lab);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(kgroup));
            aux = l.getCabeza();
            while (aux != null) {
                Kgroup g = (Kgroup)aux.get();
                Assert.assertTrue(g.getLabel().indexOf(lab) > -1);
                aux = aux.getSiguiente();
            }

            String likes = kgroup.getLikes();
            l = bdd.buscaRegistros(CampoKgroup.LIKES, likes);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(kgroup));
            aux = l.getCabeza();
            while (aux != null) {
                Kgroup g = (Kgroup)aux.get();
                Assert.assertTrue(g.getLikes().indexOf(likes) > -1);
                aux = aux.getSiguiente();
            }
            int a = likes.length();
            String lik = likes.substring(random.nextInt(2),
                                       2 + random.nextInt(a-2));
            l = bdd.buscaRegistros(CampoKgroup.LIKES, lik);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(kgroup));
            aux = l.getCabeza();
            while (aux != null) {
                Kgroup g = (Kgroup)aux.get();
                Assert.assertTrue(g.getLikes().indexOf(lik) > -1);
                aux = aux.getSiguiente();
            }

            Double review = Double.valueOf(kgroup.getReview());
            l = bdd.buscaRegistros(CampoKgroup.REVIEW, review);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(kgroup));
            aux = l.getCabeza();
            while (aux != null) {
                Kgroup g = (Kgroup)aux.get();
                Assert.assertTrue(g.getReview() >= review.doubleValue());
                aux = aux.getSiguiente();
            }
            Double ps = Double.valueOf(review.doubleValue() - 3.0);
            l = bdd.buscaRegistros(CampoKgroup.REVIEW, ps);
            Assert.assertTrue(l.getLongitud() > 0);
            Assert.assertTrue(l.contiene(kgroup));
            aux = l.getCabeza();
            while (aux != null) {
                Kgroup e = (Kgroup)aux.get();
                Assert.assertTrue(e.getReview() >= ps.doubleValue());
                aux = aux.getSiguiente();
            }
        }

        l = bdd.buscaRegistros(CampoKgroup.NAME, "^^^&@@@-nombre");
        Assert.assertTrue(l.esVacia());
        l = bdd.buscaRegistros(CampoKgroup.YEAR,
                               Integer.valueOf(11110));
        Assert.assertTrue(l.esVacia());
        l = bdd.buscaRegistros(CampoKgroup.GENERATION, 
                               Integer.valueOf(8));
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
        l = bdd.buscaRegistros(CampoKgroup.LIKES, "quantumcomputing");
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
        l = bdd.buscaRegistros(CampoKgroup.LIKES, "");
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
        l = bdd.buscaRegistros(CampoKgroup.LIKES, null);
        Assert.assertTrue(l.esVacia());
        l = bdd.buscaRegistros(CampoKgroup.REVIEW, null);
        Assert.assertTrue(l.esVacia());

        try {
            l = bdd.buscaRegistros(null, null);
            Assert.fail();
        } catch (IllegalArgumentException iae) {}
        try {
            l = bdd.buscaRegistros(X.A, null);
            Assert.fail();
        } catch (IllegalArgumentException iae) {}
    }
}
