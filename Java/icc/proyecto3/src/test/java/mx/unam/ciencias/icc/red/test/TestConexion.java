package mx.unam.ciencias.icc.red.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import mx.unam.ciencias.icc.BaseDeDatosKgroups;
import mx.unam.ciencias.icc.Kgroup;
import mx.unam.ciencias.icc.red.Conexion;
import mx.unam.ciencias.icc.red.Mensaje;
import mx.unam.ciencias.icc.test.TestKgroup;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

/**
 * Clase para pruebas unitarias de la clase {@link Conexion}.
 */
public class TestConexion {

    /** Expiración para que ninguna prueba tarde más de 5 segundos. */
    @Rule public Timeout expiracion = Timeout.seconds(5);

    /* El total de grupos de kpop. */
    private int total;
    /* La base de datos. */
    private BaseDeDatosKgroups bdd;
    /* El puerto. */
    private int puerto;
    /* El enchufe. */
    private Socket enchufe;
    /* La entrada. */
    private BufferedReader in;
    /* La salida. */
    private BufferedWriter out;
    /* El servidor. */
    private ServerSocket servidor;
    /* Generador de números aleatorios. */
    private Random random;

    /**
     * Inicializa las pruebas unitarias para {@link Conexion}.
     */
    public TestConexion() {
        random = new Random();
        total = 5 + random.nextInt(10);
        bdd = new BaseDeDatosKgroups();
        UtilRed.llenaBaseDeDatos(bdd, total);
        creaServidor();
        puerto = servidor.getLocalPort();
        iniciaServidor();
    }

    /* Crea el servidor. */
    private void creaServidor() {
        while (servidor == null) {
            try {
                int p = 1024 + random.nextInt(64500);
                servidor = new ServerSocket(p);
            } catch (BindException be) {
                UtilRed.espera(10);
            } catch (IOException ioe) {
                Assert.fail();
            }
        }
    }

    /* Inicia el servidor. */
    private void iniciaServidor() {
        new Thread(() -> {
                try {
                    enchufe = servidor.accept();
                    in = new BufferedReader(
                        new InputStreamReader(
                            enchufe.getInputStream()));
                    out = new BufferedWriter(
                        new OutputStreamWriter(
                            enchufe.getOutputStream()));
                } catch (IOException ioe) {
                    Assert.fail();
                }
        }).start();
        UtilRed.espera(10);
    }

    /**
     * Prueba unitaria para {@link Conexion#recibeMensajes}.
     */
    @Test public void testRecibeMensajes() {
        Mensaje[] mensaje = { null };

        try {
            Socket enchufe = new Socket("localhost", puerto);
            Conexion<Kgroup> conexion =
                new Conexion<Kgroup>(bdd, enchufe);
            conexion.agregaEscucha((c, m) -> mensaje[0] = m);
            UtilRed.espera(10);

            new Thread(() -> conexion.recibeMensajes()).start();
            UtilRed.espera(10);

            for (Mensaje m : Mensaje.class.getEnumConstants()) {
                out.write(m.toString());
                out.newLine();
                out.flush();
                UtilRed.espera(10);
                Assert.assertTrue(mensaje[0] == m);
            }

            out.write("ㅠㅠ");
            out.newLine();
            out.flush();
            UtilRed.espera(10);
            Assert.assertTrue(mensaje[0] == Mensaje.INVALIDO);

            this.enchufe.close();
            UtilRed.espera(10);
            Assert.assertTrue(mensaje[0] == Mensaje.DESCONECTAR);
        } catch (IOException ioe) {
            Assert.fail();
        }
    }

    /**
     * Prueba unitaria para {@link Conexion#recibeBaseDeDatos}.
     */
    @Test public void testRecibeBaseDeDatos() {
        try {
            BaseDeDatosKgroups bdd = new BaseDeDatosKgroups();
            Socket enchufe = new Socket("localhost", puerto);
            Conexion<Kgroup> conexion =
                new Conexion<Kgroup>(bdd, enchufe);
            UtilRed.espera(10);

            this.bdd.guarda(out);
            out.newLine();
            out.flush();
            UtilRed.espera(10);

            conexion.recibeBaseDeDatos();
            Assert.assertTrue(this.bdd.getRegistros().equals(bdd.getRegistros()));
        } catch (IOException ioe) {
            Assert.fail();
        }
    }

    /**
     * Prueba unitaria para {@link Conexion#enviaBaseDeDatos}.
     */
    @Test public void testEnviaBaseDeDatos() {
        try {
            BaseDeDatosKgroups bdd = new BaseDeDatosKgroups();
            UtilRed.llenaBaseDeDatos(bdd, total);
            Socket enchufe = new Socket("localhost", puerto);
            Conexion<Kgroup> conexion =
                new Conexion<Kgroup>(bdd, enchufe);
            UtilRed.espera(10);

            this.bdd.limpia();
            conexion.enviaBaseDeDatos();
            UtilRed.espera(10);
            this.bdd.carga(in);

            Assert.assertTrue(this.bdd.getRegistros().equals(bdd.getRegistros()));
        } catch (IOException ioe) {
            Assert.fail();
        }
    }

    /**
     * Prueba unitaria para {@link Conexion#recibeRegistro}.
     */
    @Test public void testRecibeRegistro() {
        try {
            BaseDeDatosKgroups bdd = new BaseDeDatosKgroups();
            Socket enchufe = new Socket("localhost", puerto);
            Conexion<Kgroup> conexion =
                new Conexion<Kgroup>(bdd, enchufe);
            UtilRed.espera(10);

            Kgroup k = TestKgroup.kgroupAleatorio(1234);
            out.write(k.seria());
            out.flush();
            UtilRed.espera(10);

            Kgroup f = conexion.recibeRegistro();
            Assert.assertTrue(k != f);
            Assert.assertTrue(k.equals(f));
        } catch (IOException ioe) {
            Assert.fail();
        }
    }

    /**
     * Prueba unitaria para {@link Conexion#enviaRegistro}.
     */
    @Test public void testEnviaRegistro() {
        try {
            BaseDeDatosKgroups bdd = new BaseDeDatosKgroups();
            UtilRed.llenaBaseDeDatos(bdd, total);
            Socket enchufe = new Socket("localhost", puerto);
            Conexion<Kgroup> conexion =
                new Conexion<Kgroup>(bdd, enchufe);
            UtilRed.espera(10);

            Kgroup k = TestKgroup.kgroupAleatorio(1234);
            conexion.enviaRegistro(k);
            UtilRed.espera(10);

            String linea = in.readLine();
            Assert.assertTrue(k.seria().equals(linea + "\n"));
        } catch (IOException ioe) {
            Assert.fail();
        }
    }

    /**
     * Prueba unitaria para {@link Conexion#enviaMensaje}.
     */
    @Test public void testEnviaMensaje() {
        try {
            BaseDeDatosKgroups bdd = new BaseDeDatosKgroups();
            UtilRed.llenaBaseDeDatos(bdd, total);
            Socket enchufe = new Socket("localhost", puerto);
            Conexion<Kgroup> conexion =
                new Conexion<Kgroup>(bdd, enchufe);
            UtilRed.espera(10);

            String linea;

            for (Mensaje mensaje : Mensaje.class.getEnumConstants()) {
                conexion.enviaMensaje(mensaje);
                UtilRed.espera(10);
                linea = in.readLine();
                Assert.assertTrue(mensaje.toString().equals(linea));
            }
        } catch (IOException ioe) {
            Assert.fail();
        }
    }

    /**
     * Prueba unitaria para {@link Conexion#getSerie}.
     */
    @Test public void testGetSerie() {
        try {
            BaseDeDatosKgroups bdd = new BaseDeDatosKgroups();
            UtilRed.llenaBaseDeDatos(bdd, total);
            Socket enchufe = new Socket("localhost", puerto);
            Conexion<Kgroup> conexion =
                new Conexion<Kgroup>(bdd, enchufe);
            int serie = conexion.getSerie();

            for (int i = 0; i < total; i++) {
                iniciaServidor();
                enchufe = new Socket("localhost", puerto);
                conexion = new Conexion<Kgroup>(bdd, enchufe);
                Assert.assertTrue(conexion.getSerie() == serie + i + 1);
            }
        } catch (IOException ioe) {
            Assert.fail();
        }
    }

    /**
     * Prueba unitaria para {@link Conexion#desconecta}.
     */
    @Test public void testDesconecta() {
        try {
            BaseDeDatosKgroups bdd = new BaseDeDatosKgroups();
            UtilRed.llenaBaseDeDatos(bdd, total);
            Socket enchufe = new Socket("localhost", puerto);
            Conexion<Kgroup> conexion =
                new Conexion<Kgroup>(bdd, enchufe);
            UtilRed.espera(10);

            conexion.desconecta();
            Assert.assertFalse(conexion.isActiva());
            String linea = in.readLine();
            Assert.assertTrue(linea == null);
        } catch (IOException ioe) {
            Assert.fail();
        }
    }

    /**
     * Prueba unitaria para {@link Conexion#isActiva}.
     */
    @Test public void testIsActiva() {
        try {
            BaseDeDatosKgroups bdd = new BaseDeDatosKgroups();
            UtilRed.llenaBaseDeDatos(bdd, total);
            Socket enchufe = new Socket("localhost", puerto);
            Conexion<Kgroup> conexion =
                new Conexion<Kgroup>(bdd, enchufe);
            UtilRed.espera(10);

            Assert.assertTrue(conexion.isActiva());
            conexion.desconecta();
            Assert.assertFalse(conexion.isActiva());

            try {
                conexion.enviaMensaje(Mensaje.ECO);
                Assert.fail();
            } catch (IOException ioe) {}
        } catch (IOException ioe) {
            Assert.fail();
        }
    }


    /**
     * Prueba unitaria para {@link Conexion#agregaEscucha}.
     */
    @Test public void testAgregaEscucha() {
        Mensaje[] mensajes = new Mensaje[total];

        try {
            Socket enchufe = new Socket("localhost", puerto);
            Conexion<Kgroup> conexion =
                new Conexion<Kgroup>(bdd, enchufe);
            for (int i = 0; i < mensajes.length; i++) {
                int j = i;
                conexion.agregaEscucha((c, m) -> mensajes[j] = m);
            }
            UtilRed.espera(10);

            new Thread(() -> conexion.recibeMensajes()).start();
            UtilRed.espera(10);

            for (Mensaje m : Mensaje.class.getEnumConstants()) {
                out.write(m.toString());
                out.newLine();
                out.flush();
                UtilRed.espera(10);
                for (int i = 0; i < mensajes.length; i++)
                    Assert.assertTrue(mensajes[i] == m);
            }

            out.write("ㅠㅠ");
            out.newLine();
            out.flush();
            UtilRed.espera(10);
            for (int i = 0; i < mensajes.length; i++)
                Assert.assertTrue(mensajes[i] == Mensaje.INVALIDO);

            this.enchufe.close();
            UtilRed.espera(10);
            for (int i = 0; i < mensajes.length; i++)
                Assert.assertTrue(mensajes[i] == Mensaje.DESCONECTAR);
        } catch (IOException ioe) {
            Assert.fail();
        }
    }
}
