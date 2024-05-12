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
    /** El tipo de grupo de kpop. */
    TYPE,
    /** El numero de integrantes del grupo de kpop. */
    MEMBERS,
    /** El genero principal del grupo de kpop. */
    GENRE,
    /** La compañia discografica a la que pertenece el grupo de kpop. */
    LABEL,
    /** La canción representativa del grupo de kpop. */
    SONG,
    /** La calificación general del grupo de kpop. */
    REVIEW;

    /**
     * Regresa una representacion en cadena del campo para ser usada en 
     * interfaces graficas.
     * @return una representacion en cadena del campo.
     */
    @Override public String toString() {
        switch (this) {
            case NAME: return "Nombre";
            case YEAR: return "Año Debut";
            case GENERATION: return "Generación";
            case TYPE: return "Tipo";
            case MEMBERS: return "# Miembros";
            case GENRE: return "Género Musical";
            case LABEL: return "Discográfica";
            case SONG: return "Canción Representativa";
            case REVIEW: return "Calificación General";
            default: return "";
        }
    }
}
