package mx.unam.ciencias.icc.igu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import mx.unam.ciencias.icc.Kgroup;

/**
 * Clase para el controlador del contenido del diálogo para editar y crear
 * grupos de kpop.
 */
public class ControladorFormaEditaKgroup
    extends ControladorFormaKgroup {

    /* La entrada verificable para el nombre. */
    @FXML private EntradaVerificable entradaName;
    /* La entrada verificable para el año debut. */
    @FXML private EntradaVerificable entradaYear;
    /* La entrada verificable para la generación. */
    @FXML private EntradaVerificable entradaGeneration;
    /* La entrada verificable para el tipo. */
    @FXML private EntradaVerificable entradaType;
    /* La entrada verificable para el número de miembros. */
    @FXML private EntradaVerificable entradaMembers;
    /* La entrada verificable para el género musical. */
    @FXML private EntradaVerificable entradaGenre;
    /* La entrada verificable para la discografica. */
    @FXML private EntradaVerificable entradaLabel;
    /* La entrada verificable para la canción representativa. */
    @FXML private EntradaVerificable entradaSong;
    /* La entrada verificable para la calificación general. */
    @FXML private EntradaVerificable entradaReview;

    /* El grupo de kpop creado o editado. */
    private Kgroup kgroup;

    /* Inicializa el estado de la forma. */
    @FXML private void initialize() {
        entradaName.setVerificador(n -> verificaName(n));
        entradaYear.setVerificador(n -> verificaYear(n));
        entradaGeneration.setVerificador(n -> verificaGeneration(n));
        entradaType.setVerificador(n -> verificaType(n));
        entradaMembers.setVerificador(n -> verificaMembers(n));
        entradaGenre.setVerificador(n -> verificaGenre(n));
        entradaLabel.setVerificador(n -> verificaLabel(n));
        entradaSong.setVerificador(n -> verificaSong(n));
        entradaReview.setVerificador(n -> verificaReview(n));

        entradaName.textProperty().addListener(
                (o, v, n) -> verificaKgroup());
        entradaYear.textProperty().addListener(
                (o, v, n) -> verificaKgroup());
        entradaGeneration.textProperty().addListener(
                (o, v, n) -> verificaKgroup());
        entradaType.textProperty().addListener(
                (o, v, n) -> verificaKgroup());
        entradaMembers.textProperty().addListener(
                (o, v, n) -> verificaKgroup());
        entradaGenre.textProperty().addListener(
                (o, v, n) -> verificaKgroup());
        entradaLabel.textProperty().addListener(
                (o, v, n) -> verificaKgroup());
        entradaSong.textProperty().addListener(
                (o, v, n) -> verificaKgroup());
        entradaReview.textProperty().addListener(
                (o, v, n) -> verificaKgroup());
    }

    /* Manejador para cuando se activa el botón de aceptar. */
    @FXML private void aceptar(ActionEvent evento) {
        actualizaKgroup();
        aceptado = true;
        escenario.close();
    }

    /* Actualiza al grupo de kpop, o lo crea si no existe. */
    private void actualizaKgroup() {
        if(kgroup != null) {
            kgroup.setName(name);
            kgroup.setYear(year);
            kgroup.setGeneration(generation);
            kgroup.setType(type);
            kgroup.setMembers(members);
            kgroup.setGenre(genre);
            kgroup.setLabel(label);
            kgroup.setSong(song);
            kgroup.setReview(review);
        } else {
            kgroup = new Kgroup(name, year, generation,
                                type, members, genre,
                                label, song, review);
        }
    }

    /**
     * Define el grupo de kpop del diálogo.
     * @param kgroup el nuevo grupo de kpop del diálogo.
     */
    public void setKgroup(Kgroup kgroup) {
        this.kgroup = kgroup;
        if (kgroup == null)
            return;
        this.kgroup = new Kgroup(null, 0, 0, null, 0, null, null,
                                null, 0);
        this.kgroup.actualiza(kgroup);
        entradaName.setText(kgroup.getName());
        String y = 
            String.format("%d", kgroup.getYear());
        entradaYear.setText(y);
        String g = 
            String.format("%d", kgroup.getGeneration());
        entradaGeneration.setText(g);
        entradaType.setText(kgroup.getType());
        String m = 
            String.format("%d", kgroup.getMembers());
        entradaMembers.setText(m);
        entradaGenre.setText(kgroup.getGenre());
        entradaLabel.setText(kgroup.getLabel());
        entradaSong.setText(kgroup.getSong());
        String r = 
            String.format("%2.2f", kgroup.getReview());
        entradaReview.setText(r);
    }

    /** Regresa el grupo de kpop del diálogo.
     * @return el grupo de kpop del diálogo.
     */
    public Kgroup getKgroup() {
        return kgroup;
    }

    /** 
     * Define el verbo del botón de aceptar.
     * @param verbo el nuevo verbo del botón de aceptar.
     */
    public void setVerbo(String verbo) {
        botonAceptar.setText(verbo);
    }

    /**
     * Define el foco inicial del diálogo.
     */
    @Override public void defineFoco() {
        entradaName.requestFocus();
    }

    /* Verifica que los nueve campos sean válidos. */
    private void verificaKgroup() {
        boolean n = entradaName.esValida();
        boolean y = entradaYear.esValida();
        boolean g = entradaGeneration.esValida();
        boolean t = entradaType.esValida();
        boolean m = entradaMembers.esValida();
        boolean gr = entradaGenre.esValida();
        boolean l = entradaLabel.esValida();
        boolean ls = entradaSong.esValida();
        boolean r = entradaReview.esValida();
        botonAceptar.setDisable(!n || !y || !g || !t || !m || !gr ||
                                !l || !ls || !r);
    }

    /**
     * Verifica que el año debut sea válido.
     * @param year el año debut a verificar.
     * @return <code>true</code> si el año debut es válido;
     *         <code>false</code> en otro caso.
     */
    @Override protected boolean verificaYear(String year) {
        return super.verificaYear(year) &&
            this.year >= 1990 && this.year <= 2023;
    }

    /**
     * Verifica que la generación sea válida.
     * @param generation la generación a verificar.
     * @return <code>true</code> si la generación es válida;
     *         <code>false</code> en otro caso.
     */
    @Override protected boolean verificaGeneration(String generation) {
        return super.verificaGeneration(generation) &&
            this.generation > 0 && this.generation <= 4;
    }

    /**
     * Verifica que el número de miembros sea válido.
     * @param members el número de miembros a verificar.
     * @return <code>true</code> si el número de miembros es válido;
     *         <code>false</code> en otro caso.
     */
    @Override protected boolean verificaMembers(String members) {
        return super.verificaMembers(members) &&
            this.members > 0 && this.members <= 30;
    }

    /**
     * Verifica que la calificación en RYM sea válida.
     * @param review la calificación en RYM a verificar.
     * @return <code>true</code> si la calficación en RYM es válida;
     *         <code>false</code> en otro caso.
     */
    @Override protected boolean verificaReview(String review) {
        return super.verificaReview(review) &&
            this.review >= 0.0 && this.review <= 5.0;
    }
            
}
