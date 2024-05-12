package mx.unam.ciencias.icc;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Clase para representar grupos de kpop. Un grupo de kpop tiene nombre,
 * año debut, generacion, tipo de grupo, numero de integrantes, genero musical principal 
 * compañia discografica, cancion con mas me gusta en Melon(멜론) y
 * mayor calificación de algún album o EP suyo en Rate Your Music.
 * La clase implementa {@link Registro}, por lo que puede
 * seriarse en una sola linea de texto; ademas de determinar si sus 
 * campos casan valores arbitrarios y actualizarse con los valores
 * de otro grupo.
 */
public class Kgroup implements Registro<Kgroup, CampoKgroup> {

    /* Nombre del grupo de Kpop. */
    private final StringProperty name;
    /* Año debut */
    private final IntegerProperty year;
    /* Generacion */
    private final IntegerProperty generation;
    /* Tipo de grupo */
    private final StringProperty type;
    /* Numero de integrantes */
    private final IntegerProperty members;
    /* Genero musical principal */
    private final StringProperty genre;
    /* Compañia discografica */
    private final StringProperty label;
    /* Cancion con mas me gusta en Melon(멜론) */
    private final StringProperty song;
    /* Mejor calificacion de alguno de sus albumes o EP en Rate Your Music */
    private final DoubleProperty review;

    /**
     * Define el estado inicial de un grupo de kpop.
     * @param name el nombre del grupo de kpop.
     * @param year el año debut del grupo.
     * @param generation la generación del grupo.
     * @param type el tipo del grupo (boy group/girl group).
     * @param members la cantidad de integrantes del grupo.
     * @param genre el genero musical principal del grupo.
     * @param label la compañia discografica del grupo.
     * @param song la cancion con mas me gusta en Melon(멜론) del grupo.
     * @param review la mejor calificacion en alguno de sus albumes o EP 
     * en Rate Your Music
     */
    public Kgroup(String name,
                  int year,
                  int generation,
                  String type,
                  int members,
                  String genre,
                  String label,
                  String song,
                  double review) {
        this.name = new SimpleStringProperty(name);
        this.year = new SimpleIntegerProperty(year);
        this.generation = new SimpleIntegerProperty(generation);
        this.type = new SimpleStringProperty(type);
        this.members = new SimpleIntegerProperty(members);
        this.genre = new SimpleStringProperty(genre);
        this.label = new SimpleStringProperty(label);
        this.song = new SimpleStringProperty(song);
        this.review = new SimpleDoubleProperty(review);
    }

    /**
     * Regresa el nombre del grupo de kpop.
     * @return el nombre(name) del grupo de kpop.
     */
    public String getName() {
        return name.get();
    }

    /**
     * Define el nombre del grupo de kpop.
     * @param name el nuevo nombre del grupo de kpop
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * Regresa la propiedad del nombre.
     * @return la propiedad del nombre.
     */
    public StringProperty nameProperty() {
        return name;
    }

    /**
     * Regresa el año debut del grupo de kpop.
     * @return el año(year) debut del grupo de kpop
     */
    public int getYear() {
        return year.get();
    }

    /**
     * Define el año debut del grupo de kpop.
     * @param year el nuevo año debut del grupo de kpop.
     */
    public void setYear(int year) {
        this.year.set(year);
    }

    /**
     * Regresa la propiedad año debut.
     * @return la propiedad del año debut.
     */
    public IntegerProperty yearProperty() {
        return year;
    }

    /**
     * Regresa la generación del grupo de kpop.
     * @return la generación del grupo de kpop.
     */
    public int getGeneration() {
        return generation.get();
    }

    /** 
     * Define la generación del grupo de kpop.
     * @return la generación del grupo de kpop.
     */
    public void setGeneration(int generation) {
        this.generation.set(generation);
    }

    /**
     * Regresa la propiedad año debut.
     * @return la propiedad del año debut.
     */
    public IntegerProperty generationProperty() {
        return generation;
    }

    /**
     * Regresa el tipo del grupo de kpop(boy group/girl group).
     * @return el tipo del grupo de kpop(boy group/girl group)
     */
    public String getType() {
        return type.get();
    }

    /**
     * Define el tipo del grupo de kpop(boy group/girl group).
     * @param type el nuevo tipo del grupo de kpop(boy group/girl group)
     */
    public void setType(String type) {
        this.type.set(type);
    }

    /**
     * Regresa la propiedad del tipo.
     * @return la propiedad del tipo.
     */
    public StringProperty typeProperty() {
        return type;
    }

    /**
     * Regresa el numero de integrantes del grupo de kpop.
     * @return el numero de integrantes del grupo de kpop.
     */
    public int getMembers() {
        return members.get();
    }

    /**
     * Define el numero de integrantes del grupo de kpop.
     * @param members el nuevo numero de integrantes del grupo de kpop.
     */
    public void setMembers(int members) {
        this.members.set(members);
    }

    /**
     * Regresa la propiedad del número de miembros.
     * @return la propiedad del número de miembros.
     */
    public IntegerProperty membersProperty() {
        return members;
    }

    /**
     * Regresa el genero musical principal del grupo de kpop.
     * @return el genero musical principal del grupo de kpop.
     */
    public String getGenre() {
        return genre.get();
    }

    /**
     * Define el genero musical principal del grupo de kpop.
     * @param genre el nuevo genero musical principal del grupo de kpop.
     */
    public void setGenre(String genre) {
        this.genre.set(genre);
    }

    /**
     * Regresa la propiedad del género musical.
     * @return la propiedad del género musical.
     */
    public StringProperty genreProperty() {
        return genre;
    }

    /**
     * Regresa la compañia discografica del grupo de kpop.
     * @return la compañía discografica del grupo de kpop.
     */
    public String getLabel() {
        return label.get();
    }

    /**
     * Define la compañia discografica del grupo de kpop.
     * @param label la nueva compañía discografica del grupo de kpop.
     */
    public void setLabel(String label) {
        this.label.set(label);
    }
    
    /**
     * Regresa la propiedad del género musical.
     * @return la propiedad del género musical.
     */
    public StringProperty labelProperty() {
        return label;
    }

    /**
     * Regresa la canción representativa del grupo de kpop.
     * @return la canción representativa del grupo de kpop.
     */
    public String getSong() {
        return song.get();
    }

    /**
     * Define la cancion representativa del grupo de kpop.
     * @param song la nueva canción representativa del grupo de kpop.
     */
    public void setSong(String song) {
        this.song.set(song);
    }
    
    /**
     * Regresa la propiedad de la canción representativa.
     * @return la propiedad de la canción representativa.
     */
    public StringProperty songProperty() {
        return song;
    }

    /**
     * Regresa la calificación general dada del grupo de kpop.
     * @return la calificación general dada del grupo de kpop
     */
    public double getReview() {
        return review.get();
    }

    /**
     * Define la calificación general dada del grupo de kpop.
     * @param review la nueva calificación general dada del grupo de kpop.
     */
    public void setReview(double review) {
        this.review.set(review);
    }

    /**
     * Regresa la calificación general.
     * @return la calificación general.
     */
    public DoubleProperty reviewProperty() {
        return review;
    }

    /**
     * Regresa una representacion en cadena del grupo de kpop.
     * @return una representacion en cadena del grupo de kpop.
     */
    @Override public String toString() {
        return String.format("Nombre : %s\n" +
                             "Año debut: %d\n" +
                             "Generación: %d\n" +
                             "Tipo(girl group/boy group) : %s\n" +
                             "Miembros : %d\n" +
                             "Género musical principal : %s\n" +
                             "Discografica : %s\n" +
                             "Canción representativa : %s\n" +
                             "Calificacion general : %2.2f",
                             name.get(), year.get(), generation.get(), type.get(), members.get(),
                             genre.get(), label.get(), song.get(), review.get());
    }

    /**
     * Nos dice si el objeto recibido es un grupo de kpop igual al que manda llamar el método.
     * @param objeto el objeto con el que el grupo se comparará.
     * @return <code>true</code> si el objeto recibido es un grupo de kpop con las
     * mismas propiedades que el objeto que manda llamar al método,
     * <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (!(objeto instanceof Kgroup))
            return false;

        Kgroup grupo = (Kgroup)objeto;

        return ((this.name.get().equals(grupo.name.get()))
                && (this.year.get() == grupo.year.get())
                && (this.generation.get() == grupo.generation.get())
                && (this.type.get().equals(grupo.type.get()))
                && (this.members.get() == grupo.members.get())
                && (this.genre.get().equals(grupo.genre.get()))
                && (this.label.get().equals(grupo.label.get()))
                && (this.song.get().equals(grupo.song.get()))
                && (this.review.get() == grupo.review.get()));
    }

    /**
     * Regresa el grupo de kpop seriado en una línea de texto. La línea de
     * texto que este método regresa debe ser aceptada por el método {@link
     * Kgroup#deseria}.
     * @return la seriación del grupo de kpop en una línea de texto.
     */
    @Override public String seria() {
        return String.format("%s\t%d\t%d\t%s\t%d\t%s\t%s\t%s\t%2.2f\n",
                             name.get(), year.get(), generation.get(),
                             type.get(), members.get(), genre.get(),
                             label.get(), song.get(), review.get());
    }

    /**
     * Deseria una línea de texto en las propiedades del grupo de kpop. La
     * seriación producida por el método {@link Kgroup#seria} debe
     * ser aceptada por este método.
     * @param linea la línea a deseriar.
     * @throws ExcepcionLineaInvalida si la línea recibida es nula, vacía o no
     *         es una seriación válida de un grupo de kpop.
     */
    @Override public void deseria(String linea) {
        if (linea.trim().equals("") || linea == null)
            throw new ExcepcionLineaInvalida("La linea es nula, vacia o invalida");

        String[] cadena = linea.trim().split("\t");
        if (cadena.length != 9) {
            throw new ExcepcionLineaInvalida("La linea es nula, vacia o invalida");
        } else {
            name.set(cadena[0]);
            type.set(cadena[3]);
            genre.set(cadena[5]);
            label.set(cadena[6]);
            song.set(cadena[7]);
            try {
                year.set(Integer.parseInt(cadena[1]));
                generation.set(Integer.parseInt(cadena[2]));
                members.set(Integer.parseInt(cadena[4]));
                review.set(Double.parseDouble(cadena[8]));
            } catch (NumberFormatException eli) {
                throw new ExcepcionLineaInvalida("La linea es nula, vacia o invalida");
            }
        }
    }

    /**
     * Actualiza los valores del grupo de kpop con los del registro recibido.
     * @param registro el registro con el cual actualizar los valores.
     * @throws IllegalArgumentException si el registro no es instancia de {@link
     *         Kgroup}.
     */
    @Override public void actualiza(Kgroup kgroup) {
        if(kgroup == null)
            throw new IllegalArgumentException("El grupo de kpop es null");

        this.name.set(kgroup.getName());
        this.year.set(kgroup.getYear());
        this.generation.set(kgroup.getGeneration());
        this.type.set(kgroup.getType());
        this.members.set(kgroup.getMembers());
        this.genre.set(kgroup.getGenre());
        this.label.set(kgroup.getLabel());
        this.song.set(kgroup.getSong());
        this.review.set(kgroup.getReview());
    }

    /**
     * Nos dice si el grupo de kpop casa el valor dado en el campo especificado.
     * @param campo el campo que hay que casar.
     * @param valor el valor con el que debe casar el campo del registro.
     * @return <code>true</code> si:
     *         <ul>
     *           <li><code>campo</code> es {@link CampoKgroup#NOMBRE} y
     *              <code>valor</code> es instancia de {@link String} y es una
     *              subcadena del nombre del grupo de kpop.</li>
     *           <li><code>campo</code> es {@link CampoKgroup#YEAR} y 
     *              <code>valor</code> es instancia de {@link Integer} y su 
     *              valor entero es menor o igual al año debut del grupo de 
     *              kpop. </li>
     *           <li>code</code> es {@link CampoKgroup#GENERATION} y 
     *              <code>valor</code> es instancia de {@link Integer} y su valor 
     *              entero es menor o igual a la generación del grupo de kpop. <\li>
     *           <li><code>campo</code> es {@link CampoKgroup#TYPE} y
     *              <code>valor</code> es instancia de {@link String} y es una
     *              subcadena del tipo del grupo de kpop(boy group/girl group).</li>
     *           <li><code>campo</code> es {@link CampoKgroup#MEMBERS} y
     *              <code>valor</code> es instancia de {@link Integer} y su 
     *              valor entero es menor o igual al numero de miembros del grupo de 
     *              kpop.</li>
     *           <li><code>campo</code> es {@link CampoKgroup#GENRE} y
     *              <code>valor</code> es instancia de {@link String} y es una
     *              subcadena del genero musical principal del grupo de kpop.</li>
     *           <li><code>campo</code> es {@link CampoKgroup#LABEL} y
     *              <code>valor</code> es instancia de {@link String} y es una
     *              subcadena de la discografica del grupo de kpop.</li>
     *           <li><code>campo</code> es {@link CampoKgroup#SONG} y
     *              <code>valor</code> es instancia de {@link String} y es una
     *              subcadena de la canción más representativa. del grupo de kpop.</li>
     *           <li><code>campo</code> es {@link CampoKgroup#REVIEW} y
     *              <code>valor</code> es instancia de {@link Double} y su
     *              valor doble es menor o igual a la calificación general dada
     *              del grupo de kpop.</li>
     *         </ul>
     *         <code>false</code> en otro caso.
     * @throws IllegalArgumentException si el campo no es instancia de {@link
     *         CampoKgroup}.
     */
    @Override public boolean casa(CampoKgroup campo, Object valor) {
        if (!(campo instanceof CampoKgroup))
            throw new IllegalArgumentException("El campo no es instancia de CampoKgroup");

        switch((CampoKgroup)campo) {
            case NAME: if (!(valor instanceof String))
                           return false;

                       String name = (String)valor;
                       // Checa si el string recibido es vacio
                       // si lo es, regresa falso.
                       if (name.equals(""))
                           return false;
                       // Checa si el string recibido es substring del nombre
                       // usando los metodos de String toUpperCase para asegurarse
                       // de que no haya ningun error y contains.
                       if (this.name.get().toUpperCase().contains(name.toUpperCase())) {
                           return true;
                       } else return false;
            case YEAR: if (!(valor instanceof Integer))
                           return false;

                       Integer year = (Integer)valor;
                       if (year <= this.year.get()) {
                           return true;
                       } else return false;
            case GENERATION: if (!(valor instanceof Integer))
                                 return false;

                             Integer generation = (Integer)valor;
                             if (generation <= this.generation.get()) {
                                 return true;
                             } else return false;
            case TYPE: if (!(valor instanceof String))
                           return false;

                       String tipo = (String)valor;
                       if (tipo.equals(""))
                           return false;

                       if (this.type.get().toUpperCase().contains(tipo.toUpperCase())) {
                           return true;
                       } else return false;
            case MEMBERS: if (!(valor instanceof Integer))
                              return false;

                          Integer members = (Integer)valor;
                          if (members <= this.members.get()) {
                              return true;
                          } else return false;
            case GENRE: if (!(valor instanceof String))
                            return false;

                        String genre = (String)valor;
                        if (genre.equals(""))
                            return false;

                        if (this.genre.get().toUpperCase().contains(genre.toUpperCase())) {
                            return true; 
                        } else return false;
            case LABEL: if (!(valor instanceof String))
                            return false;

                        String label = (String)valor;
                        if (label.equals(""))
                            return false;

                        if (this.label.get().toUpperCase().contains(label.toUpperCase())) {
                            return true;
                        } else return false;
            case SONG: if (!(valor instanceof String))
                            return false;

                        String song = (String)valor;
                        if (song.equals(""))
                            return false;

                        if (this.song.get().toUpperCase().contains(song.toUpperCase())) {
                            return true;
                        } else return false;
            case REVIEW: if (!(valor instanceof Double))
                             return false;

                         Double review = (Double)valor;
                         if (review <= this.review.get()) {
                             return true;
                         } else return false;
            default: return false;
        }
    }
}
