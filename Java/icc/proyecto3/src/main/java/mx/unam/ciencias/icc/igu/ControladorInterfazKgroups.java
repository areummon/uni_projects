package mx.unam.ciencias.icc.igu;

import java.io.IOException;
import java.net.Socket;
import java.util.Optional;
import javafx.application.Platform;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.unam.ciencias.icc.BaseDeDatosKgroups;
import mx.unam.ciencias.icc.Kgroup;
import mx.unam.ciencias.icc.EventoBaseDeDatos;
import mx.unam.ciencias.icc.Lista;
import mx.unam.ciencias.icc.red.Conexion;
import mx.unam.ciencias.icc.red.Mensaje;

/**
 * Clase para el controlador de la ventana principal de la aplicación.
 */
public class ControladorInterfazKgroups {

    /* Opción de menu para cambiar el estado de la conexión. */
    @FXML private MenuItem menuConexion;
    /* Opción de menu para agregar. */
    @FXML private MenuItem menuAgregar;
    /* Opción de menu para editar. */
    @FXML private MenuItem menuEditar;
    /* Opción de menu para eliminar. */
    @FXML private MenuItem menuEliminar;
    /* Opción de menu para buscar. */
    @FXML private MenuItem menuBuscar;
    /* El botón de agregar. */
    @FXML private Button botonAgregar;
    /* El botón de editar. */
    @FXML private Button botonEditar;
    /* El botón de eliminar. */
    @FXML private Button botonEliminar;
    /* El botón de buscar. */
    @FXML private Button botonBuscar;

    /* La tabla. */
    @FXML private TableView<Kgroup> tabla;

    /* La ventana. */
    private Stage escenario;
    /* El modelo de la selección. */
    private TableView.TableViewSelectionModel<Kgroup> modeloSeleccion;
    /* La selección. */
    private ObservableList<TablePosition> seleccion;
    /* Los renglones en la tabla. */
    private ObservableList<Kgroup> renglones;

    /* La base de datos. */
    private BaseDeDatosKgroups bdd;
    /* La conexión del cliente. */
    private Conexion<Kgroup> conexion;
    /* Si hay o no conexión. */
    private boolean conectado;

    /* Inicializa el controlador. */
    @FXML private void initialize() {
        renglones = tabla.getItems();
        modeloSeleccion = tabla.getSelectionModel();
        modeloSeleccion.setSelectionMode(SelectionMode.MULTIPLE);
        seleccion = modeloSeleccion.getSelectedCells();
        ListChangeListener<TablePosition> lcl = c -> cambioSeleccion();
        seleccion.addListener(lcl);
        cambioSeleccion();
        setConectado(false);
        bdd = new BaseDeDatosKgroups();
        bdd.agregaEscucha((e, r, s) -> eventoBaseDeDatos(e, r, s));
    }

    /* Cambioa el estado de la conexión. */
    @FXML private void cambiaConexion(ActionEvent evento) {
        if (!conectado)
            conectar();
        else 
            desconectar();
    }

    /**
     * Termina el programa.
     * @param evento el evento que generó la acción.
     */
    @FXML public void salir(Event evento) {
        desconectar();
        Platform.exit();
    }

    /* Agrega un nuevo grupo de kpop. */
    @FXML private void agregaKgroup(ActionEvent evento) {
        DialogoEditaKgroup dialogo;
        try {
            dialogo = new DialogoEditaKgroup(escenario, null);
        } catch (IOException ioe) {
            String mensaje = ("Ocurrió un error al tratar de " +
                              "cargar el diálogo del grupo de kpop.");
            dialogoError("Error al cargar interfaz", mensaje);
            return;
        }
        dialogo.showAndWait();
        tabla.requestFocus();
        if (!dialogo.isAceptado())
            return;
        bdd.agregaRegistro(dialogo.getKgroup());
        try {
            conexion.enviaMensaje(Mensaje.REGISTRO_AGREGADO);
            conexion.enviaRegistro(dialogo.getKgroup());
        } catch (IOException ioe) {
            String mensaje = ("Ocurrió un error al tratar de " +
                              "enviar el mensaje.");
            dialogoError("Error al enviar el mensaje",mensaje);
        }
    }

    /* Edita un grupo de kpop. */
    @FXML private void editaKgroup(ActionEvent evento) {
        int r = seleccion.get(0).getRow();
        Kgroup kgroup = renglones.get(r);
        DialogoEditaKgroup dialogo;
        try {
            dialogo = new DialogoEditaKgroup(escenario,
                                             kgroup);
        } catch (IOException ioe) {
            String mensaje = ("Ocurrió un error al tratar de " +
                              "cargar el diálogo del grupo de kpop .");
            dialogoError("Error al cargar interfaz", mensaje);
            return;
        }
        dialogo.showAndWait();
        tabla.requestFocus();
        if (!dialogo.isAceptado())
            return;
        try {
            conexion.enviaMensaje(Mensaje.REGISTRO_MODIFICADO);
            conexion.enviaRegistro(kgroup);
            conexion.enviaRegistro(dialogo.getKgroup());
        } catch (IOException ioe) {
            String mensaje = ("Ocurrió un error al tratar de " +
                              "enviar el registro del grupo de kpop.");
            dialogoError("Error al enviar registro", mensaje);
        }
        bdd.modificaRegistro(kgroup, dialogo.getKgroup());
    }

    /* Elimina uno o varios grupos de kpop. */
    @FXML private void eliminaKgroups(ActionEvent evento) {
        int s = seleccion.size();
        String titulo = (s > 1) ?
            "Eliminar grupos de kpop" : "Eliminar grupo de kpop";
        String mensaje = (s > 1) ?
            "Esto eliminará a los grupos de kpop seleccionados" :
            "Esto eliminará al grupo de kpop seleccionado";
        String aceptar = titulo;
        String cancelar = (s > 1) ?
            "Conservar grupos de kpop" : "Conservar grupo de kpop";
        if (!dialogoDeConfirmacion(
                    titulo, mensaje, "¿Está seguro?",
                    aceptar, cancelar))
            return;

        Lista<Kgroup> aEliminar = new Lista<Kgroup>();
        for (TablePosition tp : seleccion)
            aEliminar.agregaFinal(renglones.get(tp.getRow()));
        modeloSeleccion.clearSelection();
        for (Kgroup kgroup : aEliminar){
            bdd.eliminaRegistro(kgroup);
            try {
                conexion.enviaMensaje(Mensaje.REGISTRO_ELIMINADO);
                conexion.enviaRegistro(kgroup);
            } catch (IOException ioe) {
                String error = ("Ocurrió un error al tratar de " +
                                  "eliminar el registro del grupo de kpop.");
                dialogoError("Error al eliminar registro", error);
                return;
            }
        }
    }

    /* Busca grupos de kpop. */
    @FXML private void buscaKgroups(ActionEvent evento) {
        DialogoBuscaKgroups dialogo;
        try {
            dialogo = new DialogoBuscaKgroups(escenario);
        } catch (IOException ioe) {
            String mensaje = ("Ocurrió un error al tratar de " +
                              "cargar el diálogo de búsqueda.");
            dialogoError("Error al cargar interfaz", mensaje);
            return;
        }
        dialogo.showAndWait();
        tabla.requestFocus();
        if (!dialogo.isAceptado())
            return;
        Lista<Kgroup> resultados =
            bdd.buscaRegistros(dialogo.getCampo(),
                               dialogo.getValor());
        modeloSeleccion.clearSelection();
        for (Kgroup kgroup: resultados)
            modeloSeleccion.select(kgroup);
    }

    /* Muestra un diálogo con información del programa. */
    @FXML private void acercaDe(ActionEvent evento) {
        Alert dialogo = new Alert(AlertType.INFORMATION);
        dialogo.initOwner(escenario);
        dialogo.initModality(Modality.WINDOW_MODAL);
        dialogo.setTitle("Acerca de Administrador " +
                         "de Grupos de Kpop"); 
        dialogo.setHeaderText(null);
        dialogo.setContentText("Aplicación para administrar " +
                               "grupos de kpop.\n" +
                               "Copyright © 2022 Facultad " +
                               "de Ciencias, UNAM.");
        dialogo.showAndWait();
        tabla.requestFocus();
    }

    /**
     * Define el escenario.
     * @param escenario el escenario.
     */
    public void setEscenario(Stage escenario) {
        this.escenario = escenario;
    }

    /* Conecta el cliente con el servidor. */
    private void conectar() {
        DialogoConectar dialogo;
        try {
            dialogo = new DialogoConectar(escenario); 
        } catch (IOException ioe) {
            String mensaje = ("Ocurrió un error al tratar de " +
                              "cargar el diálogo de conectar.");
            dialogoError("Error al cargar interfaz", mensaje);
            return;
        }
        dialogo.showAndWait();
        tabla.requestFocus();
        if (!(dialogo.isAceptado()))
            return;
        try {
            Socket enchufe = new Socket(dialogo.getDireccion(),dialogo.getPuerto());
            conexion = new Conexion<>(bdd,enchufe);
            new Thread(() -> conexion.recibeMensajes()).start();
            conexion.agregaEscucha((c,m) -> mensajeRecibido(c,m));
            conexion.enviaMensaje(Mensaje.BASE_DE_DATOS);
        } catch (IOException ioe) {
            String mensaje = ("Ocurrió un error al tratar de " +
                              "conectar con el servidor.");
            dialogoError("Error al conectar", mensaje);
            return;
        }
        setConectado(true);
    }

    /* Desconecta el cliente del servidor. */
    private void desconectar() {
        if(!conectado)
            return;
        setConectado(false);
        conexion.desconecta();
        conexion = null;
        bdd.limpia();
    }

    /* Cambia la interfaz gráfica dependiendo si estamos o no conectados. */
    private void setConectado(boolean conectado) {
        if (conectado)
            menuConexion.setText("Desconectar...");
        else menuConexion.setText("Conectar...");
        this.conectado = conectado;
        menuAgregar.setDisable(!conectado);
        botonAgregar.setDisable(!conectado);
        menuBuscar.setDisable(!conectado);
        botonBuscar.setDisable(!conectado);
    }

    /* Maneja un evento de cambio en la base de datos. */
    private void eventoBaseDeDatos(EventoBaseDeDatos evento,
                                   Kgroup kgroup1,
                                   Kgroup kgroup2) {
        switch (evento) {
            case BASE_LIMPIADA:
                Platform.runLater(() -> renglones.clear());
                break;
            case REGISTRO_AGREGADO:
                Platform.runLater(() -> agregaKgroup(kgroup1));
                break;
            case REGISTRO_ELIMINADO:
                Platform.runLater(() -> eliminaKgroup(kgroup1));
                break;
            case REGISTRO_MODIFICADO:
                Platform.runLater(() -> tabla.sort());
                break;
        }
    }

    /* Actualiza la interfaz dependiendo del número de renglones
     * seleccionados. */
    private void cambioSeleccion() {
        int s = seleccion.size();
        menuEliminar.setDisable(s == 0);
        menuEditar.setDisable(s != 1);
        botonEliminar.setDisable(s == 0);
        botonEditar.setDisable(s != 1);
    }

    /* Crea un diálogo con una pregunta que hay que confirmar. */
    private boolean dialogoDeConfirmacion(String titulo,
                                          String mensaje, String pregunta,
                                          String aceptar, String cancelar) {
        Alert dialogo = new Alert(AlertType.CONFIRMATION);
        dialogo.setTitle(titulo);
        dialogo.setHeaderText(mensaje);
        dialogo.setContentText(pregunta);

        ButtonType si = new ButtonType(aceptar);
        ButtonType no = new ButtonType(cancelar, 
                                       ButtonData.CANCEL_CLOSE);
        dialogo.getButtonTypes().setAll(si, no);

        Optional<ButtonType> resultado = dialogo.showAndWait();
        tabla.requestFocus();
        return resultado.get() == si;
    }

    /* Recibe los mensajes de la conexión. */
    private void mensajeRecibido(Conexion<Kgroup> conexion, Mensaje mensaje) {
        switch(mensaje) {
            case BASE_DE_DATOS:
                baseDeDatos(conexion);
                break;
            case REGISTRO_AGREGADO:
                registroAlterado(conexion,mensaje);
                break;
            case REGISTRO_ELIMINADO:
                registroAlterado(conexion,mensaje);
                break;
            case REGISTRO_MODIFICADO:
                registroModificado(conexion);
                break;
            case DESCONECTAR:
                Platform.runLater(() -> desconectar());
                break;
            case GUARDA: break; //se ignora
            case DETENER_SERVICIO: break; //se ignora
            case ECO: break; //se ignora
            case INVALIDO:
                Platform.runLater(
                        () -> dialogoError("Error con el servidor",
                                           "Mensaje inválido recibido. " +
                                           "Se finalizará la conexión."));
                break;
        }
    }

    /* Maneja el mensaje BASE_DE_DATOS. */
    private void baseDeDatos(Conexion<Kgroup> conexion) {
        try {
            conexion.recibeBaseDeDatos();
        } catch (IOException ioe) {
            Platform.runLater(
                    () -> dialogoError("Error con el servidor",
                                       "Error al recibir base de datos."));
        }
    }

    /* Maneja los mensajes REGISTRO_AGREGADO y REGISTRO_MODIFICADO. */
    private void registroAlterado(Conexion<Kgroup> conexion,
                                  Mensaje mensaje) {
        Kgroup registro = null;
        try {
            registro = conexion.recibeRegistro();
        } catch (IOException ioe) {
            Platform.runLater(
                    () -> dialogoError("Error con el servidor",
                                       "Error al recibir registro."));
        }

        if (mensaje.equals(Mensaje.REGISTRO_AGREGADO)){
            bdd.agregaRegistro(registro);
        } else {
            bdd.eliminaRegistro(registro);
        }
    }

    /* Maneja el mensaje REGISTRO_MODIFICADO. */
    private void registroModificado(Conexion<Kgroup> conexion) {
        Kgroup registro1 = null,registro2 = null;
        try {
            registro1 = conexion.recibeRegistro();
            registro2 = conexion.recibeRegistro();
            bdd.modificaRegistro(registro1,registro2);
        } catch (IOException ioe) {
            Platform.runLater(
                    () -> dialogoError("Error con el servidor",
                                       "Error al recibir registros."));
        }
    }

    /* Muestra un diálogo de error. */
    private void dialogoError(String titulo, String mensaje) {
        if (conectado)
            conexion.desconecta();
        Alert dialogo = new Alert(AlertType.ERROR);
        dialogo.setTitle(titulo);
        dialogo.setHeaderText(null);
        dialogo.setContentText(mensaje);
        dialogo.setOnCloseRequest(e -> renglones.clear());
        dialogo.showAndWait();
        tabla.requestFocus();
    }

    /* Agrega un grupo de kpop a la tabla. */
    private void agregaKgroup(Kgroup kgroup) {
        renglones.add(kgroup);
        tabla.sort();
    }

    /* Elimina un grupo de kpop de la tabla. */
    private void eliminaKgroup(Kgroup kgroup) {
        renglones.remove(kgroup);
        tabla.sort();
    }
}
