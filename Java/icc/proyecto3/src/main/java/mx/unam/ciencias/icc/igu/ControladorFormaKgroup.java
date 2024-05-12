package mx.unam.ciencias.icc.igu;

/**
 * Clase abstracta para controladores del contenido de diálogo con formas con
 * datos de grupos de kpop que se aceptan o rechazan.
 */
public abstract class ControladorFormaKgroup extends ControladorForma {

    /** El valor del nombre. */
    protected String name;
    /** El valor del año debut. */
    protected int year;
    /** El valor de la generación. */
    protected int generation;
    /** El valor del tipo. */
    protected String type;
    /** El valor del número de miembros. */
    protected int members;
    /** El valor del género musical. */
    protected String genre;
    /** El valor de la discografica. */
    protected String label;
    /** El valor de la canción representativa. */
    protected String song;
    /** El valor de la calificación general. */
    protected double review;

    /** 
     * Verifica que el nombre sea válido.
     * @param name el nombre a verificar.
     * @return <code>true</code> si el nombre es válido; <code>false</code>
     * en otro caso.
     */
    protected boolean verificaName(String name) {
        if (name == null || name.isEmpty())
            return false;
        this.name = name;
        return true;
    }

    /** 
     * Verifica que el año debut sea válido.
     * @param year el año debut a verificar.
     * @return <code>true</code> si el año es válido; <code>false</code>
     * en otro caso.
     */
    protected boolean verificaYear(String year) {
        if (year == null || year.isEmpty())
            return false;
        try {
            this.year = Integer.parseInt(year);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /** 
     * Verifica que la generación sea válida.
     * @param generation la generación a verificar.
     * @return <code>true</code> si la generación es válida; <code>false</code>
     * en otro caso.
     */
    protected boolean verificaGeneration(String generation) {
        if (generation == null || generation.isEmpty())
            return false;
        try {
            this.generation = Integer.parseInt(generation);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /** 
     * Verifica que el tipo sea válido.
     * @param type el tipo a verificar.
     * @return <code>true</code> si el tipo es válido;
     * <code>false</code> en otro caso.
     */
    protected boolean verificaType(String type) {
        if (type == null || type.isEmpty())
            return false;
        this.type = type;
        return true;
    }

    /** 
     * Verifica que el número de miembros sea válido.
     * @param members el número de miembros a verificar.
     * @return <code>true</code> si el número de miembros es válido;
     * <code>false</code> en otro caso.
     */
    protected boolean verificaMembers(String members) {
        if (members == null || members.isEmpty())
            return false;
        try {
            this.members = Integer.parseInt(members);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /** 
     * Verifica que el género musical sea válido.
     * @param genre el género musical a verificar.
     * @return <code>true</code> si el género musical es válido; <code>false</code>
     * en otro caso.
     */
    protected boolean verificaGenre(String genre) {
        if (genre == null || genre.isEmpty())
            return false;
        this.genre = genre;
        return true;
    }

    /** 
     * Verifica que la discografica sea válida.
     * @param label la discografica a verificar.
     * @return <code>true</code> si la discografica es válida; <code>false</code>
     * en otro caso.
     */
    protected boolean verificaLabel(String label) {
        if (label == null || label.isEmpty())
            return false;
        this.label = label;
        return true;
    }

    /** 
     * Verifica que la canción representativa sea válida.
     * @param song la canción a verificar.
     * @return <code>true</code> si la canción es válida; <code>false</code>
     * en otro caso.
     */
    protected boolean verificaSong(String song) {
        if (song == null || song.isEmpty())
            return false;
        this.song = song;
        return true;
    }

    /** 
     * Verifica que la calificación general sea válida.
     * @param review la calificación a verificar.
     * @return <code>true</code> si la calificación es válida;
     * <code>false</code> en otro caso.
     */
    protected boolean verificaReview(String review) {
        if (review == null || review.isEmpty())
            return false;
        try {
            this.review = Double.parseDouble(review);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
