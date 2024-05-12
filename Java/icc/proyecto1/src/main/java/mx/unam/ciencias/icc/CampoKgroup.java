package mx.unam.ciencias.icc;

/**
 * Enumeracion para los campos de un {@link Kgroup}.
 */
public enum CampoKgroup {

    /** El nombre del grupo de kpop. */
    NAME,
    /** El año debut del grupo de kpop. */
    YEAR,
    /** La generación del grupo de kpop. */
    GENERATION,
    /** El tipo de grupo de kpop(hombres/mujeres). */
    TYPE,
    /** El numero de integrantes del grupo de kpop. */
    MEMBERS,
    /** El genero principal del grupo de kpop. */
    GENRE,
    /** La compañia discografica a la que pertenece el grupo de kpop. */
    LABEL,
    /** La cancion con mas likes en Melon(멜론) del grupo de kpop. */
    LIKES,
    /** Mayor calificacion dada a uno de los albumes o EP del grupo de kpop en RYM. */
    REVIEW;

    /**
     * Regresa una representacion en cadena del campo para ser usada en 
     * interfaces graficas.
     * @return una representacion en cadena del campo.
     */
    @Override public String toString() {
        switch (this) {
            case NAME: return "Nombre";
            case YEAR: return "Año";
            case GENERATION: return "Generación";
            case TYPE: return "Tipo(boy group/girl group)";
            case MEMBERS: return "Número de miembros";
            case GENRE: return "Género musical principal";
            case LABEL: return "Discografica";
            case LIKES: return "Canción con más me gusta en Melon(멜론)";
            case REVIEW: return "Mayor calificación dada a un album suyo en RYM";
            default: return "";
        }
    }
}
