package mx.unam.ciencias.icc.igu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Clase para el controlador del diálogo para conectar al servidor.
 */
public class ControladorFormaConectar extends ControladorForma {

    /* El campo verificable para la dirección. */
    @FXML private EntradaVerificable entradaDireccion;
    /* El campo verificable para el puerto. */
    @FXML private EntradaVerificable entradaPuerto;

    /* La dirección. */
    private String direccion;
    /* El puerto. */
    private int puerto;

    /* Inicializa el estado de la forma. */
    @FXML private void initialize() {
        entradaDireccion.setVerificador(e -> verificaDireccion(e));
        entradaPuerto.setVerificador(e -> verificaPuerto(e));

        entradaDireccion.textProperty().addListener(
                (o, v, n) -> conexionValida());
        entradaPuerto.textProperty().addListener(
                (o, v, n) -> conexionValida());
    }

    /* Manejador para cuando se activa el botón conectar. */
    @FXML private void conectar(ActionEvent evento) {
        aceptado = true;
        escenario.close();
    }

    /* Determina si los campos son válidos. */
    private void conexionValida() {
        boolean d = entradaDireccion.esValida();
        boolean p = entradaPuerto.esValida();
        botonAceptar.setDisable(!d || !p);
    }

    /* Verifica que la dirección sea válido. */
    private boolean verificaDireccion(String s) {
        if(s.equals(null) || s.isEmpty())
            return false;
        this.direccion = s;
        return true;
    }

    /* Verifica que el puerto sea válido. */
    private boolean verificaPuerto(String p) {
        if(p.equals(null) || p.isEmpty())
            return false;
        try {
            this.puerto = Integer.parseInt(p);
            if(puerto < 1024 || puerto > 65535)
                return false;
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Regresa la dirección del diálogo.
     * @return la dirección del diálogo.
     */
    public String getDireccion() {
        return entradaDireccion.getText();
    }

    /**
     * Regresa el puerto del diálogo.
     * @return el puerto del diálogo.
     */
    public int getPuerto() {
        return Integer.parseInt(entradaPuerto.getText());
    }

    /**
     * Define el foco incial del diálogo.
     */
    @Override public void defineFoco() {
        entradaDireccion.requestFocus();
    }
}
