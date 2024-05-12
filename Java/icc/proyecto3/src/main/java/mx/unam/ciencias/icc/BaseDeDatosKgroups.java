package mx.unam.ciencias.icc;

/**
 * Clase para bases de datos de grupos de Kpop.
 */
public class BaseDeDatosKgroups
    extends BaseDeDatos<Kgroup, CampoKgroup> {

    /**
     * Crea un grupo de kpop en blanco.
     * @return un grupo de kpop en blanco.
     */
    @Override public Kgroup creaRegistro() {
        return new Kgroup(null, 0, 0, null, 0, null, null, null, 0.0);
    }
}
