package mx.unam.ciencias.icc.igu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import mx.unam.ciencias.icc.CampoKgroup;

/**
 * Clase para el controlador del contenido del diálogo para buscar grupos de
 * kpop.
 */
public class ControladorFormaBuscaKgroups
    extends ControladorFormaKgroup {

    /* El combo del campo. */
    @FXML private ComboBox<CampoKgroup> opcionesCampo;
    /* El campo de texto para el valor. */
    @FXML private EntradaVerificable entradaValor;

    /* Inicializa el estado de la forma. */
    @FXML private void initialize() {
        entradaValor.setVerificador(e -> verificaValor(e));

        entradaValor.textProperty().addListener(
                (o, v, n) -> revisaValor(null));
    }

    /* Revisa el valor después de un cambio. */
    @FXML private void revisaValor(ActionEvent evento) {
        Tooltip.install(entradaValor, getTooltip());
        botonAceptar.setDisable(!entradaValor.esValida());
    }

    /* Manejador para cuando se activa el botón aceptar. */
    @FXML private void aceptar(ActionEvent evento) {
        aceptado = true;
        escenario.close();
    }

    /* Verifica el valor. */
    private boolean verificaValor(String valor) {
        switch (opcionesCampo.getValue()) {
            case NAME: return verificaName(valor);
            case YEAR: return verificaYear(valor);
            case GENERATION: return verificaGeneration(valor);
            case TYPE: return verificaType(valor);
            case MEMBERS: return verificaMembers(valor);
            case GENRE: return verificaGenre(valor);
            case LABEL: return verificaLabel(valor);
            case SONG: return verificaSong(valor);
            case REVIEW: return verificaReview(valor);
            default: return false; //No puede ocurrir.
        }
    }

    /* Obtiene la pista. */
    private Tooltip getTooltip() {
        String m = "";
        switch (opcionesCampo.getValue()) {
            case NAME: 
                m = "Buscar por nombre necesita al menos un carácter";
                break;
            case YEAR:
                m = "Buscar por año debut necesita un número entre " +
                    "1990 y 2023";
                break;
            case GENERATION: 
                m = "Buscar por generación necesita un número entre " + 
                    "1 y 4";
                break;
            case TYPE:
                m = "Buscar por tipo necesita al menos un carácter";
                break;
            case MEMBERS:
                m = "Buscar por miembros necesita un número entre " + 
                    "1 y 30";
                break;
            case GENRE: 
                m = "Buscar por género necesita al menos un carácter";
                break;
            case LABEL: 
                m = "Buscar por discografica necesita al menos un carácter";
                break;
            case SONG: 
                m = "Buscar por cancion necesita al menos un carácter";
                break;
            case REVIEW:
                m = "Buscar por calificación necesita un número entre " +
                    "0.0 y 5.0";
                break;
        }
        return new Tooltip(m);
    }

    /**
     * Regresa el valor ingresado.
     * @return el valor ingresado.
     */
    public Object getValor() {
        switch (opcionesCampo.getValue()) {
            case NAME:
                return entradaValor.getText();
            case YEAR: 
                return Integer.parseInt(entradaValor.getText());
            case GENERATION: 
                return Integer.parseInt(entradaValor.getText());
            case TYPE: 
                return entradaValor.getText();
            case MEMBERS: 
                return Integer.parseInt(entradaValor.getText());
            case GENRE: 
                return entradaValor.getText();
            case LABEL: 
                return entradaValor.getText();
            case SONG: 
                return entradaValor.getText();
            case REVIEW:
                return Double.parseDouble(entradaValor.getText());
            default:
                return entradaValor.getText(); //No puede ocurrir.
        }
    }

    /**
     * Regresa el campo seleccionado
     * @return el campo seleccionado
     */
    public CampoKgroup getCampo() {
        return opcionesCampo.getValue();
    }

    /**
     * Define el foco inicial del diálogo
     */
    @Override public void defineFoco() {
        entradaValor.requestFocus();
    }
}
