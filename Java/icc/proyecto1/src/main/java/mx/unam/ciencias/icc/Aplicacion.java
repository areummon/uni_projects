package mx.unam.ciencias.icc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase para aplicaciones de la base de datos grupos de Kpop.
 */
public class Aplicacion {

    /* Modo de la aplicación. */
    private enum Modo {
        /* Modo para guardar. */
        GUARDA(1),
        /* Modo para cargar. */
        CARGA(2);

        /* Código de terminación. */
        private int codigo;

        /* Constructor. */
        private Modo(int codigo) {
            this.codigo = codigo;
        }

        /* Regresa el código. */
        public int getCodigo() {
            return codigo;
        }

        /* Regresa el modo de la bandera. */
        public static Modo getModo(String bandera) {
            switch (bandera) {
            case "-g": return GUARDA;
            case "-c": return CARGA;
            default: throw new IllegalArgumentException(
                "Bandera inválida: " + bandera);
            }
        }
    }

    /* La base de datos. */
    private BaseDeDatosKgroups bdd;
    /* La ruta del archivo. */
    private String ruta;
    /* El modo de la aplicación. */
    private Modo modo;

    /**
     * Define el estado inicial de la aplicación.
     * @param bandera la bandera de modo.
     * @param ruta la ruta del archivo.
     * @throws IllegalArgumentException si la bandera no es "-c" o "-g".
     */
    public Aplicacion(String bandera, String ruta) {
        modo = Modo.getModo(bandera);
        this.ruta = ruta;
        bdd = new BaseDeDatosKgroups();
    }

    /**
     * Ejecuta la aplicación.
     */
    public void ejecuta() {
        switch (modo) {
        case GUARDA:
            guarda();
            break;
        case CARGA:
            carga();
            break;
        }
    }

    /* Modo de guarda de la aplicación. */
    private void guarda() {
        Scanner sc = new Scanner(System.in);
        sc.useDelimiter("\n");
        verificaSalida(sc);
        agregaKgroups(sc);
        sc.close();
        try {
            BufferedWriter out =
                new BufferedWriter(
                    new OutputStreamWriter(
                        new FileOutputStream(ruta)));
            bdd.guarda(out);
            out.close();
        } catch (IOException ioe) {
            System.err.printf("No pude guardar en el archivo \"%s\".\n",
                              ruta);
            System.exit(modo.getCodigo());
        }
    }

    /* Verifica que la salida no exista o le permite salir al usuario. */
    private void verificaSalida(Scanner sc) {
        File archivo = new File(ruta);
        if (archivo.exists()) {
            System.out.printf("El archivo \"%s\" ya existe y será " +
                              "reescrito.\n¿Desea continuar? (s/n): ", ruta);
            String r = sc.next();
            if (!r.equals("s")) {
                sc.close();
                System.exit(0);
            }
        }
    }

    /* Agrega grupos de kpop a la base de datos mientras el usuario lo desee. */
    private void agregaKgroups(Scanner sc) {
         System.out.println("\nDeje el nombre en blanco para " +
                            "parar la captura de grupos de kpop.");
        Kgroup e = null;
        do {
            try {
                e = getKgroup(sc);
                if (e != null)
                    bdd.agregaRegistro(e);
            } catch (InputMismatchException ime) {
                System.err.printf("\nNúmero inválido. Se descartará " +
                                  "este grupo de kpop.\n");
                sc.next(); // Purgamos la última entrada del usuario.
                continue;
            }
        } while (e != null);
    }

    /* Obtiene un grupo de kpop de la línea de comandos. */
    private Kgroup getKgroup(Scanner sc) {
        System.out.printf("\nNombre   : ");
        String name = sc.next();
        if (name.equals(""))
            return null;
        System.out.printf("\nAño debut(1997-2022)   : ");
        int year = sc.nextInt();
        if (year < 1997) {
            throw new InputMismatchException();
        }
        System.out.printf("La generación se definirá a partir del año.\n");
        // La generación no necesita ser puesta por el usuario
        // pues se define automaticamente con el año recibido.
        System.out.printf("\nTipo(boy group/girl group)   : ");
        String type = sc.next();
        if (type.equals(""))
            return null;
        System.out.printf("\nNúmero de miembros(1-30)   : ");
        int members = sc.nextInt();
        if (members <= 0 || members > 30) {
            throw new InputMismatchException();
        }
        System.out.printf("\nGénero musical principal   : ");
        String genre = sc.next();
        if (genre.equals(""))
            return null;
        System.out.printf("\nCompañía discografica   : ");
        String label = sc.next();
        if (label.equals(""))
            return null;
        System.out.printf("\nCanción con más likes en Melon(멜론)   : ");
        String likes = sc.next();
        if (likes.equals(""))
            return null;
        System.out.printf("\nMejor calificación de album o EP en Rate Your Music(0.1-5.0)   : ");
        double review = sc.nextDouble();
        if (review <= 0.0 || review > 5.0) {
            throw new InputMismatchException();
        }
        // Se manda 2 veces como parametro year, el primero para la variable de clase year
        // del grupo de kpop y la segunda para calcular la generación del grupo.
        return new Kgroup(name, year, year, type, members, genre, label, likes, review);
    }

    /* Modo de carga de la aplicación. */
    private void carga() {
        try {
            BufferedReader in =
                new BufferedReader(
                    new InputStreamReader(
                        new FileInputStream(ruta)));
            bdd.carga(in);
            in.close();
        } catch (IOException ioe) {
            System.err.printf("No pude cargar del archivo \"%s\".\n",
                              ruta);
            System.exit(modo.getCodigo());
        }
        Scanner sc = new Scanner(System.in);
        sc.useDelimiter("\n");
        busca(sc);
        sc.close();
    }

    /* Hace la búsqueda. */
    private void busca(Scanner sc) {
        System.out.println("\nDeje el campo en blanco para " +
                           "parar la búsqueda de grupos de Kpop.");
        String c = "X";
        while (!(c = getCampo(sc)).equals("")) {
            Lista l;
            try {
                l = getResultados(c, sc);
            } catch (ExcepcionOpcionInvalida epi) {
                System.out.printf("%s\n", epi.getMessage());
                continue;
            } catch (InputMismatchException ime) {
                System.out.printf("\nValor inválido. Búsqueda descartada.\n");
                sc.next(); // Purgamos la entrada.
                continue;
            }
            Lista.Nodo nodo = l.getCabeza();
            String m = nodo != null ? "" :
                "\nCero registros casan la búsqueda.";
            System.out.println(m);
            while (nodo != null) {
                System.out.printf("%s\n\n", nodo.get().toString());
                nodo = nodo.getSiguiente();
            }
        }
    }

    /* Regresa el campo del grupo de kpop. */
    private String getCampo(Scanner sc) {
        System.out.printf("\n¿Por qué campo quiere buscar? (n/a/gn/t/m/gm/d/c/r): ");
        return sc.next();
    }

    /* Regresa los resultados de la búsqueda del grupo de kpop. */
    private Lista getResultados(String c, Scanner sc) {
        System.out.println();
        switch (c) {
        case "n": return bdd.buscaRegistros(CampoKgroup.NAME,
                                            getValorName(sc));
        case "a": return bdd.buscaRegistros(CampoKgroup.YEAR,
                                            getValorYear(sc));
        case "gn": return bdd.buscaRegistros(CampoKgroup.GENERATION,
                                            getValorGeneration(sc));
        case "t": return bdd.buscaRegistros(CampoKgroup.TYPE,
                                            getValorType(sc));
        case "m": return bdd.buscaRegistros(CampoKgroup.MEMBERS,
                                            getValorMembers(sc));
        case "gm": return bdd.buscaRegistros(CampoKgroup.GENRE,
                                            getValorGenre(sc));
        case "d": return bdd.buscaRegistros(CampoKgroup.LABEL,
                                            getValorLabel(sc));
        case "c": return bdd.buscaRegistros(CampoKgroup.LIKES,
                                            getValorLikes(sc));
        case "r": return bdd.buscaRegistros(CampoKgroup.REVIEW,
                                            getValorReview(sc));
        default:
            String m = String.format("El campo '%s' es inválido.", c);
            throw new ExcepcionOpcionInvalida(m);
        }
    }

    /* Regresa el valor a buscar para nombre. */
    private String getValorName(Scanner sc) {
        System.out.printf("El nombre debe contener: ");
        return sc.next();
    }

    /* Regresa el valor a buscar para el año debut. */
    private Integer getValorYear(Scanner sc) {
        System.out.printf("El año debut debe ser mayor o igual a: ");
        return Integer.valueOf(sc.nextInt());
    }

    /* Regresa el valor a buscar para la generación. */
    private Integer getValorGeneration(Scanner sc) {
        System.out.printf("La generación deber ser igual a: ");
        return Integer.valueOf(sc.nextInt());
    }

    /* Regresa el valor a buscar para el tipo de grupo(boy group/girl group). */
    private String getValorType(Scanner sc) {
        System.out.printf("El tipo(girl group/boy group) debe ser: ");
        return sc.next();
    }

    /* Regresa el valor a buscar el número de miembros. */
    private Integer getValorMembers(Scanner sc) {
        System.out.printf("El número de miembros debe ser mayor o igual a: ");
        return Integer.valueOf(sc.nextInt());
    }

    /* Regresa el valor a buscar para el género musical principal. */
    private String getValorGenre(Scanner sc) {
        System.out.printf("El género musical principal debe contener: ");
        return sc.next();
    }

    /* Regresa el valor a buscar para la compañía discografica. */
    private String getValorLabel(Scanner sc) {
        System.out.printf("La compañía discografica debe contener: ");
        return sc.next();
    }

    /* Regresa el valor a buscar para la cancion con mas likes de Melon(멜론) */
    private String getValorLikes(Scanner sc) {
        System.out.printf("La canción con más me gusta en Melon(멜론) debe contener: ");
        return sc.next();
    }

    /* Regresa el valor a buscar para el álbum o EP con mejor calificación de Rate Your Music. */
    private Double getValorReview(Scanner sc) {
        System.out.printf("La mayor calificación de album o EP en RYM debe ser mayor o igual a: ");
        return Double.valueOf(sc.nextDouble());
    }
}
