package mx.unam.ciencias.icc;

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
public class Kgroup implements Registro {

    /* Nombre del grupo de Kpop. */
    private String name;
    /* Año debut */
    private int year;
    /* Generacion */
    private int generation;
    /* Tipo de grupo */
    private String type;
    /* Numero de integrantes */
    private int members;
    /* Genero musical principal */
    private String genre;
    /* Compañia discografica */
    private String label;
    /* Cancion con mas me gusta en Melon(멜론) */
    private String likes;
    /* Mejor calificacion de alguno de sus albumes o EP en Rate Your Music */
    private double review;

    /**
     * Define el estado inicial de un grupo de kpop.
     * @param name el nombre del grupo de kpop.
     * @param year el año debut del grupo.
     * @param generation la generación del grupo.
     * @param type el tipo del grupo (boy group/girl group).
     * @param members la cantidad de integrantes del grupo.
     * @param genre el genero musical principal del grupo.
     * @param label la compañia discografica del grupo.
     * @param likes la cancion con mas me gusta en Melon(멜론) del grupo.
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
                  String likes,
                  double review) {
        this.name = name;
        this.year = year;
        this.generation = CampoGeneration.GENERATION1.getCampoGeneration(generation);
        this.type = type;
        this.members = members;
        this.genre = genre;
        this.label = label;
        this.likes = likes;
        this.review = review;
    }

    /**
     * Regresa el nombre del grupo de kpop.
     * @return el nombre(name) del grupo de kpop.
     */
    public String getName() {
        return name;
    }

    /**
     * Define el nombre del grupo de kpop.
     * @param name el nuevo nombre del grupo de kpop
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Regresa el año debut del grupo de kpop.
     * @return el año(year) debut del grupo de kpop
     */
    public int getYear() {
        return year;
    }

    /**
     * Define el año debut del grupo de kpop.
     * @param year el nuevo año debut del grupo de kpop.
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Regresa la generación del grupo de kpop.
     * @return la generación del grupo de kpop.
     */
    public int getGeneration() {
        return generation;
    }

    /** 
     * Define la generación del grupo de kpop.
     * @return la generación del grupo de kpop.
     */
    public void setGeneration(int year) {
        // Define la generación del grupo de kpop
        // llamando al metodo publico de la clase enum CampoGeneration
        // para definir la generacion sin que el usuario lo ponga manualmente.
        this.generation = CampoGeneration.GENERATION1.getCampoGeneration(year);
    }

    /**
     * Regresa el tipo del grupo de kpop(boy group/girl group).
     * @return el tipo del grupo de kpop(boy group/girl group)
     */
    public String getType() {
        return type;
    }

    /**
     * Define el tipo del grupo de kpop(boy group/girl group).
     * @param type el nuevo tipo del grupo de kpop(boy group/girl group)
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Regresa el numero de integrantes del grupo de kpop.
     * @return el numero de integrantes del grupo de kpop.
     */
    public int getMembers() {
        return members;
    }

    /**
     * Define el numero de integrantes del grupo de kpop.
     * @param members el nuevo numero de integrantes del grupo de kpop.
     */
    public void setMembers(int members) {
        this.members = members;
    }

    /**
     * Regresa el genero musical principal del grupo de kpop.
     * @return el genero musical principal del grupo de kpop.
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Define el genero musical principal del grupo de kpop.
     * @param genre el nuevo genero musical principal del grupo de kpop.
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Regresa la compañia discografica del grupo de kpop.
     * @return la compañía discografica del grupo de kpop.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Define la compañia discografica del grupo de kpop.
     * @param label la nueva compañía discografica del grupo de kpop.
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Regresa la cancion con mas me gusta en Melon(멜론) del grupo de kpop.
     * @return la cancion con mas me gusta en Melon(멜론) del grupo de kpop.
     */
    public String getLikes() {
        return likes;
    }

    /**
     * Define la cancion con mas me gusta en Melon(멜론) del grupo de kpop.
     * @param likes la nueva canción con más me gusta en Melon(멜론) del grupo de kpop.
     */
    public void setLikes(String likes) {
        this.likes = likes;
    }

    /**
     * Regresa la mayor calificación de alguno de sus albumes o EP en Rate Your Music.
     * @return la mayor calificación de alguno de sus albumes o EP en Rate Your Music
     */
    public double getReview() {
        return review;
    }

    /**
     * Define la mayor calificación de alguno de sus albumes o EP en Rate Your Music.
     * @param review la nueva nueva mayor calificación de alguno de sus albumes o EP en Rate Your Music.
     */
    public void setReview(double review) {
        this.review = review;
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
                             "Canción con más me gusta en Melon(멜론) : %s\n" +
                             "Calificacion mas alta de album o EP del grupo en Rate Your Music: %2.2f",
                             name, year, generation, type, members, genre, label, likes,
                             review);
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

        return ((this.name.equals(grupo.name))
                && (this.year == grupo.year)
                && (this.generation == grupo.generation)
                && (this.type.equals(grupo.type))
                && (this.members == grupo.members)
                && (this.genre.equals(grupo.genre))
                && (this.label.equals(grupo.label))
                && (this.likes.equals(grupo.likes))
                && (this.review == grupo.review));
    }

    /**
     * Regresa el grupo de kpop seriado en una línea de texto. La línea de
     * texto que este método regresa debe ser aceptada por el método {@link
     * Kgroup#deseria}.
     * @return la seriación del grupo de kpop en una línea de texto.
     */
    @Override public String seria() {
        return String.format("%s\t%d\t%d\t%s\t%d\t%s\t%s\t%s\t%2.2f\n",
                             name, year, generation, type, members, genre, label,
                             likes, review);
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
            name = cadena[0];
            type = cadena[3];
            genre = cadena[5];
            label = cadena[6];
            likes = cadena[7];
            try {
                year = Integer.parseInt(cadena[1]);
                generation = Integer.parseInt(cadena[2]);
                members = Integer.parseInt(cadena[4]);
                review = Double.parseDouble(cadena[8]);
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
    @Override public void actualiza(Registro registro) {
        if(!(registro instanceof Kgroup))
            throw new IllegalArgumentException("Registro no es instancia de Kgroup");

        Kgroup recibido = (Kgroup)registro;
        this.name = recibido.getName();
        this.year = recibido.getYear();
        this.generation = recibido.getGeneration();
        this.type = recibido.getType();
        this.members = recibido.getMembers();
        this.genre = recibido.getGenre();
        this.label = recibido.getLabel();
        this.likes = recibido.getLikes();
        this.review = recibido.getReview();
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
     *              entero es la generación del grupo de kpop. <\li>
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
     *           <li><code>campo</code> es {@link CampoKgroup#LIKES} y
     *              <code>valor</code> es instancia de {@link String} y es una
     *              subcadena de la cancion con mas me gusta en Melon(멜론) 
     *              del grupo de kpop.</li>
     *           <li><code>campo</code> es {@link CampoKgroup#REVIEW} y
     *              <code>valor</code> es instancia de {@link Double} y su
     *              valor doble es menor o igual a la mayor calificacion 
     *              de alguno de sus albumes o EP en Rate Your Music .</li>
     *         </ul>
     *         <code>false</code> en otro caso.
     * @throws IllegalArgumentException si el campo no es instancia de {@link
     *         CampoKgroup}.
     */
    @Override public boolean casa(Enum campo, Object valor) {
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
                       if (this.name.toUpperCase().contains(name.toUpperCase())) {
                           return true;
                       } else return false;
            case YEAR: if (!(valor instanceof Integer))
                           return false;

                       Integer year = (Integer)valor;
                       if (year <= this.year) {
                           return true;
                       } else return false;
            case GENERATION: if (!(valor instanceof Integer))
                                 return false;

                             Integer generation = (Integer)valor;
                             if (generation == this.generation) {
                                 return true;
                             } else return false;
            case TYPE: if (!(valor instanceof String))
                           return false;

                       String tipo = (String)valor;
                       if (tipo.equals(""))
                           return false;

                       if (this.type.toUpperCase().contains(tipo.toUpperCase())) {
                           return true;
                       } else return false;
            case MEMBERS: if (!(valor instanceof Integer))
                              return false;

                          Integer members = (Integer)valor;
                          if (members <= this.members) {
                              return true;
                          } else return false;
            case GENRE: if (!(valor instanceof String))
                            return false;

                        String genre = (String)valor;
                        if (genre.equals(""))
                            return false;

                        if (this.genre.toUpperCase().contains(genre.toUpperCase())) {
                            return true; 
                        } else return false;
            case LABEL: if (!(valor instanceof String))
                            return false;

                        String label = (String)valor;
                        if (label.equals(""))
                            return false;

                        if (this.label.toUpperCase().contains(label.toUpperCase())) {
                            return true;
                        } else return false;
            case LIKES: if (!(valor instanceof String))
                            return false;

                        String likes = (String)valor;
                        if (likes.equals(""))
                            return false;

                        if (this.likes.toUpperCase().contains(likes.toUpperCase())) {
                            return true;
                        } else return false;
            case REVIEW: if (!(valor instanceof Double))
                             return false;

                         Double review = (Double)valor;
                         if (review <= this.review) {
                             return true;
                         } else return false;
            default: return false;
        }
    }
}
