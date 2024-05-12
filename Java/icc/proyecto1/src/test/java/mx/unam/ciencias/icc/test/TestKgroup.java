package mx.unam.ciencias.icc.test;

import java.util.Random;
import mx.unam.ciencias.icc.CampoKgroup;
import mx.unam.ciencias.icc.Kgroup;
import mx.unam.ciencias.icc.ExcepcionLineaInvalida;
import mx.unam.ciencias.icc.Registro;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

/**
 * Clase para pruebas unitarias de la clase {@link Kgroup}.
 */
public class TestKgroup {

    /** Expiracion para que ninguna prueba tarde más de 5 segundos. */
    @Rule public Timeout expiracion = Timeout.seconds(5);

    /* Nombres */
    private static final String[] NOMBRES = {
        "Oh My Girl", "SHINee", "Infinite", "Red Velvet", "IVE",
        "Stray Kids", "WJSN", "TWice", "SNSD", "EXO", "fromis_9",
        "f(x)", "Billlie", "Gfriend", "Kara", "Orange Caramel", "Crayon Pop"
    };

    /* Tipos */
    private static final String[] TIPO = {
        "boy group", "girl group"
    };

    /* Discograficas */
    private static final String[] DISCOGRAFICAS = {
        "WM Entertainment", "SM Entertainment", "Starship Entertainment",
        "JYP", "Woollim", "Hybe", "Cube", "Jellyfish", "Kakao M"
    };

    /* Generos musicales principales */
    private static final String[] GENERO = {
        "Pop", "Hip Hop", "Trap", "Rock", "Contemporary",
        "Dance", "R&B", "Synth", "Rap", "Electro", "Power",
        "City", "Sunshine"
    };

    /* Generos musicales secundarios */
    private static final String[] MUSICAL = {
        "pop", "rap", "folk", "EDM", "rock", "metal",
        "alternative"
    };

    /* Canciones */
    private static final String[] CANCIONES = {
        "Dolphin", "Remember me", "LOVE DIVE", "Umpah Umpah",
        "Atlantis", "Look", "Crayon", "Bungee", "Psycho", "Red Flavor",
        "We Go", "Weather", "Dice", "Silent Night", "Maison", "Happiness",
        "Secret", "I Wish", "Masquerade", "Cantabille", "Eleven", "After Like",
        "Signal", "What is love?", "Likey", "Navillera", "Rough", "View", "Good Evening",
        "Renaissance", "Easy", "Cupid", "Me gustas tu", "Queendom", "Feel Good"
    };

    /* Generador de números aleatorios */
    private static Random random;

    /**
     * Genera un nombre aleatorio.
     * @return un nombre aleatorio.
     */
    public static String nameAleatorio() {
        int r = random.nextInt(NOMBRES.length);
        return NOMBRES[r];
    }
    
    /**
     * Genera un género musical aleatorio.
     * @return un género musical aleatorio.
     */
    public static String genreAleatorio() {
        int p = random.nextInt(GENERO.length);
        int s = random.nextInt(MUSICAL.length);
        return GENERO[p] + " " + MUSICAL[s];
    }

    /**
     * Genera una compañía discografica aleatoria.
     * @return una compañía discografica aleatoria.
     */
    public static String labelAleatorio() {
        int p = random.nextInt(DISCOGRAFICAS.length);
        return DISCOGRAFICAS[p];
    }

    /**
     * Genera un año aleatorio en el intervalo
     * @return un año aleatorio en el intervalo.
     */
    public static int yearAleatorio() {
        int y = random.nextInt(2022 - 1997 + 1) + 1997;
        return y;
    }

    /**
     * Genera una generacion "aleatoria" basada en un año aleatorio.
     * @return una generacion "aleatoria" basada en un año aleatorio.
     */
    public static int generationAleatorio(int year) {
        Kgroup nuevo = new Kgroup(null, year, year, null, 0, null,
                null, null, 0.0);
        return nuevo.getGeneration();
    }

    /**
     * Genera un tipo aleatorio.
     * @return un tipo aleatorio.
     */
    public static String typeAleatorio() {
        int n = random.nextInt(TIPO.length);
        return TIPO[n];
    }

    /**
     * Genera un número de miembros aleatorio.
     * @return un número de miembros aleatorio.
     */
    public static int memberAleatorio() {
        return random.nextInt(25 - 2 + 1) + 2;
    }

    /**
     * Genera una cancion aleatoria.
     * @return una canción aleatoria.
     */
    public static String likesAleatorio() {
        int c = random.nextInt(CANCIONES.length);
        return CANCIONES[c];
    }

    /**
     * Genera una calificación aleatoria.
     * @return una calificación aleatoria,
     */
    public static double reviewAleatorio() {
        return random.nextInt(100) / 10.0;
    }

    /** 
     * Genera un grupo de kpop aleatorio.
     */
    public static Kgroup kgroupAleatorio() {
        return new Kgroup(nameAleatorio(), yearAleatorio(), yearAleatorio(),
                typeAleatorio(), memberAleatorio(), genreAleatorio(), labelAleatorio(),
                likesAleatorio(), reviewAleatorio());
    }

    /**
     * Genera un grupo de kpop aleatorio con un año debut aleatorio dado.
     * @param year el año debut del nuevo grupo de kpop.
     * @return un grupo de kpop aleatorio.
     */
    public static Kgroup kgroupAleatorio(int year) {
        /* Dado que es un año aleatorio, su generación se definirá en 
         * base a este. */
        return new Kgroup(nameAleatorio(), year, year, typeAleatorio(),
                memberAleatorio(), genreAleatorio(), labelAleatorio(),
                likesAleatorio(), reviewAleatorio());
    }

    /* El grupo de kpop */
    private Kgroup kgroup;

    /* Enumeracion espuria. */
    private enum X {
        /* Campo espurio. */
        A;
    }

    /**
     * Prueba unitaria para {@link Kgroup#Kgroup(String, int, int, String,
     * int, String, String, String, double)}.
     */
    @Test public void testConstructor() {
        String name = nameAleatorio();
        int year = yearAleatorio();
        int generation = generationAleatorio(year);
        String type = typeAleatorio();
        int members = memberAleatorio();
        String genre = genreAleatorio();
        String label = labelAleatorio();
        String likes = likesAleatorio();
        double review = reviewAleatorio();
        kgroup = new Kgroup(name, year, year,
                type, members, genre, label, likes, review);
        Assert.assertTrue(kgroup.getName().equals(name));
        Assert.assertTrue(kgroup.getYear() == year);
        Assert.assertTrue(kgroup.getGeneration() == generation);
        Assert.assertTrue(kgroup.getType().equals(type));
        Assert.assertTrue(kgroup.getMembers() == members);
        Assert.assertTrue(kgroup.getGenre().equals(genre));
        Assert.assertTrue(kgroup.getLabel().equals(label));
        Assert.assertTrue(kgroup.getLikes().equals(likes));
        Assert.assertTrue(kgroup.getReview() == review);
    }

    /**
     * Prueba unitaria para {@link Kgroup#getName}.
     */
    @Test public void testGetName() {
        String name = nameAleatorio();
        int year = yearAleatorio();
        String type = typeAleatorio();
        int members = memberAleatorio();
        String genre = genreAleatorio();
        String label = labelAleatorio();
        String likes = likesAleatorio();
        double review = reviewAleatorio();
        kgroup = new Kgroup(name, year, year, type,
                members, genre, label, likes, review);
        Assert.assertTrue(kgroup.getName().equals(name));
        Assert.assertFalse(kgroup.getName().equals(name + " A"));
    }

    /**
     * Prueba unitaria para {@link Kgroup#setName}.
     */
    @Test public void testSetName() {
        String name = nameAleatorio();
        String newName = name + " A";
        int year = yearAleatorio();
        String type = typeAleatorio();
        int members = memberAleatorio();
        String genre = genreAleatorio();
        String label = labelAleatorio();
        String likes = likesAleatorio();
        double review = reviewAleatorio();
        kgroup = new Kgroup(name, year, year, type,
                members, genre, label, likes, review);
        Assert.assertTrue(kgroup.getName().equals(name));
        Assert.assertFalse(kgroup.getName().equals(newName));
        kgroup.setName(newName);
        Assert.assertFalse(kgroup.getName().equals(name));
        Assert.assertTrue(kgroup.getName().equals(newName));
    }

    /**
     * Prueba unitaria para {@link Kgroup#getYear}.
     */
    @Test public void testGetYear() {
        String name = nameAleatorio();
        int year = yearAleatorio();
        String type = typeAleatorio();
        int members = memberAleatorio();
        String genre = genreAleatorio();
        String label = labelAleatorio();
        String likes = likesAleatorio();
        double review = reviewAleatorio();
        kgroup = new Kgroup(name, year, year, type,
                members, genre, label, likes, review);
        Assert.assertTrue(kgroup.getYear() == year);
        Assert.assertFalse(kgroup.getYear() == year + 50);
    }

    /**
     * Prueba unitaria para {@link Kgroup#setYear}.
     */
    @Test public void testSetYear() {
        String name = nameAleatorio();
        int year = yearAleatorio();
        int newYear = year + 50;
        String type = typeAleatorio();
        int members = memberAleatorio();
        String genre = genreAleatorio();
        String label = labelAleatorio();
        String likes = likesAleatorio();
        double review = reviewAleatorio();
        kgroup = new Kgroup(name, year, year, type,
                members, genre, label, likes, review);
        Assert.assertTrue(kgroup.getYear() == year);
        Assert.assertFalse(kgroup.getYear() == year + 50);
        kgroup.setYear(newYear);
        Assert.assertFalse(kgroup.getYear() == year);
        Assert.assertTrue(kgroup.getYear() == newYear);
    }

    /**
     * Prueba unitaria para {@link Kgroup#getGeneration}.
     */
    @Test public void testGetGeneration() {
        String name = nameAleatorio();
        int year = yearAleatorio();
        int generation = generationAleatorio(year);
        String type = typeAleatorio();
        int members = memberAleatorio();
        String genre = genreAleatorio();
        String label = labelAleatorio();
        String likes = likesAleatorio();
        double review = reviewAleatorio();
        kgroup = new Kgroup(name, year, year, type,
                members, genre, label, likes, review);
        Assert.assertTrue(kgroup.getGeneration() == generation);
        Assert.assertFalse(kgroup.getGeneration() == generation + 10);
    }

    /**
     * Prueba unitaria para {@link Kgroup#setGeneration}.
     */
    @Test public void testSetGeneration() {
        String name = nameAleatorio();
        int year = yearAleatorio();
        int newYear = year + 10;
        int generation = generationAleatorio(year);
        int newGeneration = generationAleatorio(newYear);
        String type = typeAleatorio();
        int members = memberAleatorio();
        String genre = genreAleatorio();
        String label = labelAleatorio();
        String likes = likesAleatorio();
        double review = reviewAleatorio();
        kgroup = new Kgroup(name, year, year, type,
                members, genre, label, likes, review);
        Assert.assertTrue(kgroup.getGeneration() == generation);
        Assert.assertFalse(kgroup.getGeneration() == generation + 10);
        kgroup.setGeneration(newYear);
        /* Ya que el atributo de generation solo puede tener 4 valores
         * distintos,es muy probable que se de el caso en el que 
         * sean iguales, aun depues de haber generado un nuevo año */
        Assert.assertTrue(kgroup.getGeneration() == newGeneration);
    }

    /**
     * Prueba unitaria para {@link Kgroup#getType}.
     */
    @Test public void testGetType() {
        String name = nameAleatorio();
        int year = yearAleatorio();
        String type = typeAleatorio();
        int members = memberAleatorio();
        String genre = genreAleatorio();
        String label = labelAleatorio();
        String likes = likesAleatorio();
        double review = reviewAleatorio();
        kgroup = new Kgroup(name, year, year, type,
                members, genre, label, likes, review);
        Assert.assertTrue(kgroup.getType().equals(type));
        Assert.assertFalse(kgroup.getType().equals(type + " B"));
    }

    /**
     * Prueba unitaria para {@link Kgroup#setType}.
     */
    @Test public void testSetType() {
        String name = nameAleatorio();
        int year = yearAleatorio();
        String type = typeAleatorio();
        String newType = type + " B";
        int members = memberAleatorio();
        String genre = genreAleatorio();
        String label = labelAleatorio();
        String likes = likesAleatorio();
        double review = reviewAleatorio();
        kgroup = new Kgroup(name, year, year, type,
                members, genre, label, likes, review);
        Assert.assertTrue(kgroup.getType().equals(type));
        Assert.assertFalse(kgroup.getType().equals(type + " B"));
        kgroup.setType(newType);
        Assert.assertFalse(kgroup.getType().equals(type));
        Assert.assertTrue(kgroup.getType().equals(newType));
    }

    /**
     * Prueba unitaria para {@link Kgroup#getMembers}.
     */
    @Test public void testGetMembers() {
        String name = nameAleatorio();
        int year = yearAleatorio();
        String type = typeAleatorio();
        int members = memberAleatorio();
        String genre = genreAleatorio();
        String label = labelAleatorio();
        String likes = likesAleatorio();
        double review = reviewAleatorio();
        kgroup = new Kgroup(name, year, year, type,
                members, genre, label, likes, review);
        Assert.assertTrue(kgroup.getMembers() == members);
        Assert.assertFalse(kgroup.getMembers() == members + 10);
    }

    /**
     * Prueba unitaria para {@link Kgroup#setMembers}.
     */
    @Test public void testSetMembers() {
        String name = nameAleatorio();
        int year = yearAleatorio();
        String type = typeAleatorio();
        int members = memberAleatorio();
        int newMembers = members + 10;
        String genre = genreAleatorio();
        String label = labelAleatorio();
        String likes = likesAleatorio();
        double review = reviewAleatorio();
        kgroup = new Kgroup(name, year, year, type,
                members, genre, label, likes, review);
        Assert.assertTrue(kgroup.getType().equals(type));
        Assert.assertFalse(kgroup.getType().equals(type + " B"));
        kgroup.setMembers(newMembers);
        Assert.assertFalse(kgroup.getMembers() == members);
        Assert.assertTrue(kgroup.getMembers() == newMembers);
    }

    /**
     * Prueba unitaria para {@link Kgroup#getGenre}.
     */
    @Test public void testGetGenre() {
        String name = nameAleatorio();
        int year = yearAleatorio();
        String type = typeAleatorio();
        int members = memberAleatorio();
        String genre = genreAleatorio();
        String label = labelAleatorio();
        String likes = likesAleatorio();
        double review = reviewAleatorio();
        kgroup = new Kgroup(name, year, year, type,
                members, genre, label, likes, review);
        Assert.assertTrue(kgroup.getGenre().equals(genre));
        Assert.assertFalse(kgroup.getGenre().equals(genre + " B"));
    }

    /**
     * Prueba unitaria para {@link Kgroup#setGenre}.
     */
    @Test public void testSetGenre() {
        String name = nameAleatorio();
        int year = yearAleatorio();
        String type = typeAleatorio();
        int members = memberAleatorio();
        String genre = genreAleatorio();
        String newGenre = genre + " B";
        String label = labelAleatorio();
        String likes = likesAleatorio();
        double review = reviewAleatorio();
        kgroup = new Kgroup(name, year, year, type,
                members, genre, label, likes, review);
        Assert.assertTrue(kgroup.getGenre().equals(genre));
        Assert.assertFalse(kgroup.getGenre().equals(genre + " B"));
        kgroup.setGenre(newGenre);
        Assert.assertFalse(kgroup.getGenre().equals(genre));
        Assert.assertTrue(kgroup.getGenre().equals(newGenre));
    }

    /**
     * Prueba unitaria para {@link Kgroup#getLabel}.
     */
    @Test public void testGetLabel() {
        String name = nameAleatorio();
        int year = yearAleatorio();
        String type = typeAleatorio();
        int members = memberAleatorio();
        String genre = genreAleatorio();
        String label = labelAleatorio();
        String likes = likesAleatorio();
        double review = reviewAleatorio();
        kgroup = new Kgroup(name, year, year, type,
                members, genre, label, likes, review);
        Assert.assertTrue(kgroup.getLabel().equals(label));
        Assert.assertFalse(kgroup.getLabel().equals(label + " B"));
    }

    /**
     * Prueba unitaria para {@link Kgroup#setLabel}.
     */
    @Test public void testSetLabel() {
        String name = nameAleatorio();
        int year = yearAleatorio();
        String type = typeAleatorio();
        int members = memberAleatorio();
        String genre = genreAleatorio();
        String label = labelAleatorio();
        String newLabel = label + " B";
        String likes = likesAleatorio();
        double review = reviewAleatorio();
        kgroup = new Kgroup(name, year, year, type,
                members, genre, label, likes, review);
        Assert.assertTrue(kgroup.getLabel().equals(label));
        Assert.assertFalse(kgroup.getLabel().equals(label + " B"));
        kgroup.setLabel(newLabel);
        Assert.assertFalse(kgroup.getLabel().equals(label));
        Assert.assertTrue(kgroup.getLabel().equals(newLabel));
    }

    /**
     * Prueba unitaria para {@link Kgroup#getLikes}.
     */
    @Test public void testGetLikes() {
        String name = nameAleatorio();
        int year = yearAleatorio();
        String type = typeAleatorio();
        int members = memberAleatorio();
        String genre = genreAleatorio();
        String label = labelAleatorio();
        String likes = likesAleatorio();
        double review = reviewAleatorio();
        kgroup = new Kgroup(name, year, year, type,
                members, genre, label, likes, review);
        Assert.assertTrue(kgroup.getLikes().equals(likes));
        Assert.assertFalse(kgroup.getLikes().equals(likes + " B"));
    }

    /**
     * Prueba unitaria para {@link Kgroup#setLikes}.
     */
    @Test public void testSetLikes() {
        String name = nameAleatorio();
        int year = yearAleatorio();
        String type = typeAleatorio();
        int members = memberAleatorio();
        String genre = genreAleatorio();
        String label = labelAleatorio();
        String likes = likesAleatorio();
        String newLikes = likes + " B";
        double review = reviewAleatorio();
        kgroup = new Kgroup(name, year, year, type,
                members, genre, label, likes, review);
        Assert.assertTrue(kgroup.getLikes().equals(likes));
        Assert.assertFalse(kgroup.getLikes().equals(likes + " B"));
        kgroup.setLikes(newLikes);
        Assert.assertFalse(kgroup.getLikes().equals(likes));
        Assert.assertTrue(kgroup.getLikes().equals(newLikes));
    }

    /**
     * Prueba unitaria para {@link Kgroup#getReview}.
     */
    @Test public void testGetReview() {
        String name = nameAleatorio();
        int year = yearAleatorio();
        String type = typeAleatorio();
        int members = memberAleatorio();
        String genre = genreAleatorio();
        String label = labelAleatorio();
        String likes = likesAleatorio();
        double review = reviewAleatorio();
        kgroup = new Kgroup(name, year, year, type,
                members, genre, label, likes, review);
        Assert.assertTrue(kgroup.getReview() == review);
        Assert.assertFalse(kgroup.getReview() == review + 3.0);
    }

    /**
     * Prueba unitaria para {@link Kgroup#setReview}.
     */
    @Test public void testSetReview() {
        String name = nameAleatorio();
        int year = yearAleatorio();
        String type = typeAleatorio();
        int members = memberAleatorio();
        String genre = genreAleatorio();
        String label = labelAleatorio();
        String likes = likesAleatorio();
        double review = reviewAleatorio();
        double newReview = review + 3.0;
        kgroup = new Kgroup(name, year, year, type,
                members, genre, label, likes, review);
        Assert.assertTrue(kgroup.getReview() == review);
        Assert.assertFalse(kgroup.getReview() == review + 3.0);
        kgroup.setReview(newReview);
        Assert.assertFalse(kgroup.getReview() == review);
        Assert.assertTrue(kgroup.getReview() == newReview);
    }

    /**
     * Prueba unitaria para {@link Kgroup#toString}
     */
    @Test public void testToString() {
        String name = nameAleatorio();
        int year = yearAleatorio();
        int generation = generationAleatorio(year);
        String type = typeAleatorio();
        int members = memberAleatorio();
        String genre = genreAleatorio();
        String label = labelAleatorio();
        String likes = likesAleatorio();
        double review = reviewAleatorio();
        kgroup = new Kgroup(name, year, year,
                type, members, genre, label, likes, review);
        String cadena = String.format("Nombre : %s\n" +
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
        Assert.assertTrue(kgroup.toString().equals(cadena));
        year = 209;
        members = 45;
        review = 10.0;
        /* Uso generationAleatorio para obtener la nueva generacion
        / usando el nuevo year. */
        generation = generationAleatorio(year);
        kgroup.setYear(year);
        kgroup.setGeneration(year);
        kgroup.setMembers(members);
        kgroup.setReview(review);
        cadena = String.format("Nombre : %s\n" +
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
        Assert.assertTrue(kgroup.toString().equals(cadena));
    }

    /**
     * Prueba unitaria para {@link Kgroup#equals}
     */
    @Test public void testEquals() {
        String name = nameAleatorio();
        int year = yearAleatorio();
        String type = typeAleatorio();
        int members = memberAleatorio();
        String genre = genreAleatorio();
        String label = labelAleatorio();
        String likes = likesAleatorio();
        double review = reviewAleatorio();
        kgroup = new Kgroup(name, year, year,
                type, members, genre, label, likes, review);
        Kgroup igual = new Kgroup(new String(name), year, year, new String(type)
                , members, new String(genre), new String(label), new String(likes), review);
        Assert.assertTrue(kgroup.equals(igual));
        String anotherName = name + " Yooa";
        int anotherYear = year + 1;
        String anotherType = type + " kor";
        int anotherMembers = members + 3;
        String anotherGenre = genre + " psychedelic";
        String anotherLabel = label + " FC";
        String anotherLikes = likes + " Fireworks";
        double anotherReview = review + 2.5;
        Kgroup distinto = 
            new Kgroup(anotherName, anotherYear, anotherYear, anotherType,
                       anotherMembers, anotherGenre, anotherLabel,
                       anotherLikes, anotherReview);
        Assert.assertFalse(kgroup.equals(distinto));
        distinto = new Kgroup(name, anotherYear, anotherYear, type,
                              members, genre, label, likes, review);
        Assert.assertFalse(kgroup.equals(distinto));
        distinto = new Kgroup(name, year, year, anotherType, members,
                            genre, label, likes, review);
        Assert.assertFalse(kgroup.equals(distinto));
        distinto = new Kgroup(name, year, year, type, anotherMembers,
                              genre, label, likes, review);
        Assert.assertFalse(kgroup.equals(distinto));
        distinto = new Kgroup(name, year, year, type, members,
                              anotherGenre, label, likes, review);
        Assert.assertFalse(kgroup.equals(distinto));
        distinto = new Kgroup(name, year, year, type, members,
                              genre, anotherLabel, likes, review);
        Assert.assertFalse(kgroup.equals(distinto));
        distinto = new Kgroup(name, year, year, type, members,
                              genre, label, anotherLikes, review);
        Assert.assertFalse(kgroup.equals(distinto));
        distinto = new Kgroup(name, year, year, type, members,
                              genre, label, likes, anotherReview);
        Assert.assertFalse(kgroup.equals(distinto));
        distinto = new Kgroup(anotherName, anotherYear, anotherYear,
                        anotherType, anotherMembers, anotherGenre,
                        anotherLabel, anotherLikes, anotherReview);
        Assert.assertFalse(kgroup.equals(distinto));
        Assert.assertFalse(kgroup.equals("Una cadena"));
        Assert.assertFalse(kgroup.equals(null));
    }

    /**
     * Prueba unitaria para {@link Kgroup#seria}
     */
    @Test public void testSeria() {
        String name = nameAleatorio();
        int year = yearAleatorio();
        int generation = generationAleatorio(year);
        String type = typeAleatorio();
        int members = memberAleatorio();
        String genre = genreAleatorio();
        String label = labelAleatorio();
        String likes = likesAleatorio();
        double review = reviewAleatorio();
        kgroup = new Kgroup(name, year, year,
                type, members, genre, label, likes, review);
        String line = String.format("%s\t%d\t%d\t%s\t%d\t%s\t%s\t%s\t%2.2f\n",
                                    name, year, generation, type, members,
                                    genre, label, likes, review);
        Assert.assertTrue(kgroup.seria().equals(line));
    }

    /**
     * Prueba unitaria para {@link Kgroup#deseria}
     */
    @Test public void testDeseria() {
        kgroup = new Kgroup(null, 0, 0, null, 0, null, null, null, 0.0);

        String name = nameAleatorio();
        int year = yearAleatorio();
        int generation = generationAleatorio(year);
        String type = typeAleatorio();
        int members = memberAleatorio();
        String genre = genreAleatorio();
        String label = labelAleatorio();
        String likes = likesAleatorio();
        double review = reviewAleatorio();

        String line = String.format("%s\t%d\t%d\t%s\t%d\t%s\t%s\t%s\t%2.2f\n",
                                    name, year, generation, type, members,
                                    genre, label, likes, review);

        try {
            kgroup.deseria(line);
        } catch (ExcepcionLineaInvalida eli) {
            Assert.fail();
        }

        Assert.assertTrue(kgroup.getName().equals(name));
        Assert.assertTrue(kgroup.getYear() == year);
        Assert.assertTrue(kgroup.getGeneration() == generation);
        Assert.assertTrue(kgroup.getType().equals(type));
        Assert.assertTrue(kgroup.getMembers() == members);
        Assert.assertTrue(kgroup.getGenre().equals(genre));
        Assert.assertTrue(kgroup.getLabel().equals(label));
        Assert.assertTrue(kgroup.getLikes().equals(likes));
        Assert.assertTrue(kgroup.getReview() == review);

        String[] invalidas = {"", " ", "\t", "  ", "\t\t",
                            " \t", "\t" , "\n", "a\tc\tb",
                            "a\tb\tc\td"};

        for (int i = 0; i < invalidas.length; i++) {
            line = invalidas[i];
            try {
                kgroup.deseria(line);
                Assert.fail();
            } catch (ExcepcionLineaInvalida eli) {}
        }
    }

    /**
     * Prueba unitaria para {@link Kgroup#actualiza}.
     */
    @Test public void testActualiza() {
        kgroup = new Kgroup("Yooa", 1, 1, "es", 1, "mi", "bias", 
                            "favorita", 1);
        Kgroup k = new Kgroup("Winter", 2, 2, "is", 2, "la", "biaswrecker", 
                            "preferida", 2);
        Assert.assertFalse(kgroup == k);
        Assert.assertFalse(kgroup.equals(k));
        kgroup.actualiza(k);
        Assert.assertFalse(kgroup == k);
        Assert.assertTrue(kgroup.equals(k));
        Assert.assertTrue(kgroup.getName().equals("Winter"));
        try {
            kgroup.actualiza(null);
            Assert.fail();
        } catch (IllegalArgumentException iae) {}
        try {
            kgroup.actualiza(new Registro() {
                @Override public String seria() { return null; }
                @Override public void deseria(String line) {}
                @Override public void actualiza(Registro registro) {}
                @Override public boolean casa(Enum campo, Object valor) {
                    return false;
                }
            });
            Assert.fail();
        } catch (IllegalArgumentException iae) {}
    }

    /**
     * Prueba unitaria para {@link Kgroup#casa}
     */
    @Test public void testCasa() {
        String name = nameAleatorio();
        int year = yearAleatorio();
        String type = typeAleatorio();
        int members = memberAleatorio();
        String genre = genreAleatorio();
        String label = labelAleatorio();
        String likes = likesAleatorio();
        double review = reviewAleatorio();
        kgroup = new Kgroup(name, year, year,
                type, members, genre, label, likes, review);

        String n = kgroup.getName();
        int m = kgroup.getName().length();
        Assert.assertTrue(kgroup.casa(CampoKgroup.NAME, n));
        n = kgroup.getName().substring(0, m/2);
        Assert.assertTrue(kgroup.casa(CampoKgroup.NAME, n));
        n = kgroup.getName().substring(m/2, m);
        Assert.assertTrue(kgroup.casa(CampoKgroup.NAME, n));
        n = kgroup.getName().substring(m/3, 2*(m/3));
        Assert.assertTrue(kgroup.casa(CampoKgroup.NAME, n));
        Assert.assertFalse(kgroup.casa(CampoKgroup.NAME, ""));
        Assert.assertFalse(kgroup.casa(CampoKgroup.NAME, "VVV"));
        Assert.assertFalse(kgroup.casa(CampoKgroup.NAME, 
                                       Integer.valueOf(1000)));
        Assert.assertFalse(kgroup.casa(CampoKgroup.NAME, null));

        Integer c = Integer.valueOf(kgroup.getYear());
        Assert.assertTrue(kgroup.casa(CampoKgroup.YEAR, c));
        c = Integer.valueOf(kgroup.getYear() - 10);
        Assert.assertTrue(kgroup.casa(CampoKgroup.YEAR, c));
        c = Integer.valueOf(kgroup.getYear() + 10);
        Assert.assertFalse(kgroup.casa(CampoKgroup.YEAR, c));
        Assert.assertFalse(kgroup.casa(CampoKgroup.YEAR, "VVV"));
        Assert.assertFalse(kgroup.casa(CampoKgroup.YEAR, null));

        /* ambos casos -1 y +1 al valueof deben de ser falsos
         * pues el casa de GENERATION es que sean iguales. */
        Integer g = Integer.valueOf(kgroup.getGeneration());
        Assert.assertTrue(kgroup.casa(CampoKgroup.GENERATION, g));
        g = Integer.valueOf(kgroup.getGeneration() - 1);
        Assert.assertFalse(kgroup.casa(CampoKgroup.GENERATION, g));
        g = Integer.valueOf(kgroup.getGeneration() + 1);
        Assert.assertFalse(kgroup.casa(CampoKgroup.GENERATION, g));
        Assert.assertFalse(kgroup.casa(CampoKgroup.GENERATION, "VVV"));
        Assert.assertFalse(kgroup.casa(CampoKgroup.GENERATION, null));

        String t = kgroup.getType();
        int q = kgroup.getType().length();
        Assert.assertTrue(kgroup.casa(CampoKgroup.TYPE, t));
        t = kgroup.getType().substring(0, q/2);
        Assert.assertTrue(kgroup.casa(CampoKgroup.TYPE, t));
        t = kgroup.getType().substring(q/2, q);
        Assert.assertTrue(kgroup.casa(CampoKgroup.TYPE, t));
        t = kgroup.getType().substring(q/3, 2*(q/3));
        Assert.assertTrue(kgroup.casa(CampoKgroup.TYPE, t));
        Assert.assertFalse(kgroup.casa(CampoKgroup.TYPE, ""));
        Assert.assertFalse(kgroup.casa(CampoKgroup.TYPE, "VVV"));
        Assert.assertFalse(kgroup.casa(CampoKgroup.TYPE, 
                                       Integer.valueOf(1000)));
        Assert.assertFalse(kgroup.casa(CampoKgroup.TYPE, null));

        Integer v = Integer.valueOf(kgroup.getMembers());
        Assert.assertTrue(kgroup.casa(CampoKgroup.MEMBERS, v));
        v = Integer.valueOf(kgroup.getMembers() - 5);
        Assert.assertTrue(kgroup.casa(CampoKgroup.MEMBERS, v));
        v = Integer.valueOf(kgroup.getMembers() + 5);
        Assert.assertFalse(kgroup.casa(CampoKgroup.MEMBERS, v));
        Assert.assertFalse(kgroup.casa(CampoKgroup.MEMBERS, "VVV"));
        Assert.assertFalse(kgroup.casa(CampoKgroup.MEMBERS, null));

        String l = kgroup.getGenre();
        int p = kgroup.getGenre().length();
        Assert.assertTrue(kgroup.casa(CampoKgroup.GENRE, l));
        l = kgroup.getGenre().substring(0, p/2);
        Assert.assertTrue(kgroup.casa(CampoKgroup.GENRE, l));
        l = kgroup.getGenre().substring(p/2, p);
        Assert.assertTrue(kgroup.casa(CampoKgroup.GENRE, l));
        l = kgroup.getGenre().substring(p/3, 2*(p/3));
        Assert.assertTrue(kgroup.casa(CampoKgroup.GENRE, l));
        Assert.assertFalse(kgroup.casa(CampoKgroup.GENRE, ""));
        Assert.assertFalse(kgroup.casa(CampoKgroup.GENRE, "VVV"));
        Assert.assertFalse(kgroup.casa(CampoKgroup.GENRE, 
                                       Integer.valueOf(1000)));
        Assert.assertFalse(kgroup.casa(CampoKgroup.GENRE, null));

        String w = kgroup.getLabel();
        int r = kgroup.getLabel().length();
        Assert.assertTrue(kgroup.casa(CampoKgroup.LABEL, w));
        w = kgroup.getLabel().substring(0, r/2);
        Assert.assertTrue(kgroup.casa(CampoKgroup.LABEL, w));
        w = kgroup.getLabel().substring(r/2, r);
        Assert.assertTrue(kgroup.casa(CampoKgroup.LABEL, w));
        w = kgroup.getLabel().substring(r/3, 2*(r/3));
        Assert.assertTrue(kgroup.casa(CampoKgroup.LABEL, w));
        Assert.assertFalse(kgroup.casa(CampoKgroup.LABEL, ""));
        Assert.assertFalse(kgroup.casa(CampoKgroup.LABEL, "VVV"));
        Assert.assertFalse(kgroup.casa(CampoKgroup.LABEL, 
                                       Integer.valueOf(1000)));
        Assert.assertFalse(kgroup.casa(CampoKgroup.LABEL, null));

        String z = kgroup.getLikes();
        int x = kgroup.getLikes().length();
        Assert.assertTrue(kgroup.casa(CampoKgroup.LIKES, z));
        z = kgroup.getLikes().substring(0, x/2);
        Assert.assertTrue(kgroup.casa(CampoKgroup.LIKES, z));
        z = kgroup.getLikes().substring(x/2, x);
        Assert.assertTrue(kgroup.casa(CampoKgroup.LIKES, z));
        z = kgroup.getLikes().substring(x/3, 2*(x/3));
        Assert.assertTrue(kgroup.casa(CampoKgroup.LIKES, z));
        Assert.assertFalse(kgroup.casa(CampoKgroup.LIKES, ""));
        Assert.assertFalse(kgroup.casa(CampoKgroup.LIKES, "VVV"));
        Assert.assertFalse(kgroup.casa(CampoKgroup.LIKES, 
                                       Integer.valueOf(1000)));
        Assert.assertFalse(kgroup.casa(CampoKgroup.LIKES, null));

        Double s = Double.valueOf(kgroup.getReview());
        Assert.assertTrue(kgroup.casa(CampoKgroup.REVIEW, s));
        s = Double.valueOf(kgroup.getReview() - 3.0);
        Assert.assertTrue(kgroup.casa(CampoKgroup.REVIEW, s));
        s = Double.valueOf(kgroup.getReview() + 3.0);
        Assert.assertFalse(kgroup.casa(CampoKgroup.REVIEW, s));
        Assert.assertFalse(kgroup.casa(CampoKgroup.REVIEW, "VVV"));
        Assert.assertFalse(kgroup.casa(CampoKgroup.REVIEW, null));

        try {
            kgroup.casa(null, null);
            Assert.fail();
        } catch (IllegalArgumentException iae) {}
        try {
            kgroup.casa(X.A, kgroup.getName());
            Assert.fail();
        } catch (IllegalArgumentException iae) {}
        try {
            Object o = Integer.valueOf(kgroup.getYear());
            kgroup.casa(X.A, o);
        } catch (IllegalArgumentException iae) {}
        try {
            Object o = Integer.valueOf(kgroup.getGeneration());
            kgroup.casa(X.A, o);
        } catch (IllegalArgumentException iae) {}
        try {
            kgroup.casa(X.A, kgroup.getType());
            Assert.fail();
        } catch (IllegalArgumentException iae) {}
        try {
            Object o = Integer.valueOf(kgroup.getMembers());
            kgroup.casa(X.A, o);
        } catch (IllegalArgumentException iae) {}
        try {
            kgroup.casa(X.A, kgroup.getGenre());
            Assert.fail();
        } catch (IllegalArgumentException iae) {}
        try {
            kgroup.casa(X.A, kgroup.getLabel());
            Assert.fail();
        } catch (IllegalArgumentException iae) {}
        try {
            kgroup.casa(X.A, kgroup.getLikes());
            Assert.fail();
        } catch (IllegalArgumentException iae) {}
        try {
            Object o = Double.valueOf(kgroup.getReview());
            kgroup.casa(X.A, o);
        } catch (IllegalArgumentException iae) {}
        try {
            Assert.assertFalse(kgroup.casa(X.A, null));
        } catch (IllegalArgumentException iae) {}
    }

    /* Inicializa el generador de números aleatorios */ 
    static {
        random = new Random();
    }
}
