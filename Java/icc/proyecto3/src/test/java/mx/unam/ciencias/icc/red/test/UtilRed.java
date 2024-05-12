package mx.unam.ciencias.icc.red.test;

import mx.unam.ciencias.icc.BaseDeDatosKgroups;
import mx.unam.ciencias.icc.Kgroup;
import mx.unam.ciencias.icc.test.TestKgroup;

/**
 * Clase con métodos estáticos utilitarios para las pruebas unitarias de red.
 */
public class UtilRed {

    /* Evitamos instanciación. */
    private UtilRed() {}

    /* Año debut inicial. */
    public static final int BEGINNING_YEAR = 1950;

    /* Contador de años debut. */
    private static int contador;

    /**
     * Espera el número de milisegundos de forma segura.
     * @param milisegundos el número de milisegundos a esperar.
     */
    public static void espera(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException ie) {}
    }

    /**
     * Llena una base de datos de grupos de kpop.
     * @param bdd la base de datos a llenar.
     * @param total el total de grupos de kpop.
     */
    public static void llenaBaseDeDatos(BaseDeDatosKgroups bdd, int total) {
        for (int i = 0; i < total; i++) {
            int a = BEGINNING_YEAR + i;
            bdd.agregaRegistro(TestKgroup.kgroupAleatorio(a));
        }
    }

    /**
     * Crea un grupo de kpop aleatorio.
     * @param total el total de grupos de kpop.
     * @return un grupo de kpop con un año debut único.
     */
    public static Kgroup kgroupAleatorio(int total) {
        int nc = BEGINNING_YEAR + total*2 + contador++;
        return TestKgroup.kgroupAleatorio(nc);
    }

}
