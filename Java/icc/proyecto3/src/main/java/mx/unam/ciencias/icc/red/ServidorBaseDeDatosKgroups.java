package mx.unam.ciencias.icc.red;

import java.io.IOException;
import mx.unam.ciencias.icc.BaseDeDatos;
import mx.unam.ciencias.icc.BaseDeDatosKgroups;
import mx.unam.ciencias.icc.CampoKgroup;
import mx.unam.ciencias.icc.Kgroup;

/**
 * Clase para servidores de bases de datos de grupos de kpop.
 */
public class ServidorBaseDeDatosKgroups
    extends ServidorBaseDeDatos<Kgroup> {

    /**
     * Construye un servidor de base de datos de grupos de kpop.
     * @param puerto el puerto dónde escuchar por conexiones.
     * @param archivo el archivo en el disco del cual cargar/guardar la base de
     * datos.
     * @throws IOException si ocurre un error de entrada o salida.
     */
    public ServidorBaseDeDatosKgroups(int puerto, String archivo)
        throws IOException {
        super(puerto, archivo);
    }

    /**
     * Crea una base de datos de grupos de kpop.
     * @return una base de datos de grupos de kpop.
     */
    @Override public
    BaseDeDatos<Kgroup, CampoKgroup> creaBaseDeDatos() {
        return new BaseDeDatosKgroups();
    }
}
