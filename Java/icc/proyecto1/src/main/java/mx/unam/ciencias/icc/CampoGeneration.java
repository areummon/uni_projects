package mx.unam.ciencias.icc;

/**
 * Enumeración para la generación de un grupo de Kpop.
 */
public enum CampoGeneration {

    /** Primera generación(1997-2002) */
    GENERATION1(1997),
    /** Segunda generación(2003-2011) */
    GENERATION2(2003),
    /** Tercera generación(2012-2017) */
    GENERATION3(2012),
    /** Cuarta generación(Desde 2018) */
    GENERATION4(2018);

    private int generation;

    private CampoGeneration(int generation) {
        this.generation = generation;
    }

    /** Método auxiliar para determinar la generación del grupo de kpop. */
    private CampoGeneration getGeneration(int year) {
        CampoGeneration encontrado = GENERATION1;
        for (CampoGeneration e : values()) {
            if (e.generation <= year)
                encontrado = e;
        }
        return encontrado; 
    }
    
    /** Método que regresa la generación del grupo de kpop.
     * @param year el año debut del grupo de kpop. */
    public int getCampoGeneration(int year) {
        CampoGeneration generacion = getGeneration(year);
        switch(generacion) {
            case GENERATION1: return 1;
            case GENERATION2: return 2;
            case GENERATION3: return 3;
            case GENERATION4: return 4;
            default: return 0;
        }
    }
}
