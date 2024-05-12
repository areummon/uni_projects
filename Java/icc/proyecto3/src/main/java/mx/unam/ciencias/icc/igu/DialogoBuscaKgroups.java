package mx.unam.ciencias.icc.igu;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.unam.ciencias.icc.CampoKgroup;

/**
 * Clase para diálogos con formas de búsquedas de grupos de kpop.
 */
public class DialogoBuscaKgroups extends Stage {

    /* Vista de la forma para realizar búsquedas de grupos de kpop. */
    private static final String BUSCA_KGROUPS_FXML =
        "fxml/forma-busca-kgroups.fxml";

    /* El controlador. */
    private ControladorFormaBuscaKgroups controlador;

    /**
     * Define el estado inicial de un diálogo para búsquedas de grupos
     * de kpop.
     * @param escenario el escenario al que el diálogo pertenece.
     * @throws IOException si no se puede cargar el archivo FXML.
     */
    public DialogoBuscaKgroups(Stage escenario) throws IOException {
        ClassLoader cl = getClass().getClassLoader();
        FXMLLoader cargador = new FXMLLoader(
                cl.getResource(BUSCA_KGROUPS_FXML));
        AnchorPane pagina = (AnchorPane)cargador.load();
        setTitle("Buscar grupos de kpop");
        initOwner(escenario);
        initModality(Modality.WINDOW_MODAL);
        Scene escena = new Scene(pagina);
        setScene(escena);
        controlador = cargador.getController();
        controlador.setEscenario(this);
        setOnShown(w -> controlador.defineFoco());
        setResizable(false);
    }

    /**
     * Nos dice si el usuario activó el botón de aceptar.
     * @return <code>true</code> si el usuario activó el botón de aceptar,
     *         <code>false</code> en otro caso.
     */
    public boolean isAceptado() {
        return controlador.isAceptado();
    }

    /**
     * Regresa el campo seleccionado.
     * @return el campo seleccionado.
     */
    public CampoKgroup getCampo() {
        return controlador.getCampo();
    }

    /**
     * Regresa el valor ingresado.
     * @return el valor ingresado.
     */
    public Object getValor() {
        return controlador.getValor();
    }
}
