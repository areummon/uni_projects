package mx.unam.ciencias.icc.igu;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.unam.ciencias.icc.Kgroup;

/**
 * Clase para diálogos con formas para editar grupos de kpop.
 */
public class DialogoEditaKgroup extends Stage {

    /* Vista de la forma para agregar/editar grupos de kpop. */
    private static final String EDITA_KGROUP_FXML =
        "fxml/forma-edita-kgroup.fxml";

    /* El controlador. */
    private ControladorFormaEditaKgroup controlador;

    /**
     * Define el estado inicial de un diálogo para un grupo de kpop.
     * @param escenario el escenario al que el diálogo pertenece.
     * @param kgroup el grupo de kpop; puede ser <code>null</code>
     * para agregar un grupo de kpop.
     * @throws IOException si no se puede cargar el archivo FXML.
     */
    public DialogoEditaKgroup(Stage escenario,
                                  Kgroup kgroup) throws IOException {
        ClassLoader cl = getClass().getClassLoader();
        FXMLLoader cargador = 
            new FXMLLoader(cl.getResource(EDITA_KGROUP_FXML));
        AnchorPane pagina = (AnchorPane)cargador.load();
        initOwner(escenario);
        initModality(Modality.WINDOW_MODAL);
        Scene escena = new Scene(pagina);
        setScene(escena);
        controlador = cargador.getController();
        controlador.setEscenario(this);
        controlador.setKgroup(kgroup);
        if (kgroup == null) {
            setTitle("Agregar grupo de kpop");
            controlador.setVerbo("Agregar");
        } else {
            setTitle("Editar grupo de kpop");
            controlador.setVerbo("Actualizar");
        }

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
     * Regresa el grupo de kpop del diálogo.
     * @return el grupo de kpop del diálogo.
     */
    public Kgroup getKgroup() {
        return controlador.getKgroup();
    }
}
