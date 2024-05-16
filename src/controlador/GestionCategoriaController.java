/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author esteb
 */
public class GestionCategoriaController implements Initializable {

    @FXML
    private TableColumn<?, ?> columnaCategoria;
    @FXML
    private TableColumn<?, ?> columnaDescripcion;
    @FXML
    private VBox contenedorTabla;
    @FXML
    private TableView<?> tablaCategorias;
    @FXML
    private Button añadirButton;
    @FXML
    private Button editarButton;
    @FXML
    private Button eliminarButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        columnaCategoria.setReorderable(false);
        columnaDescripcion.setReorderable(false);
        tablaCategorias.prefWidthProperty().bind(contenedorTabla.widthProperty());
        columnaCategoria.prefWidthProperty().bind(tablaCategorias.widthProperty().multiply(0.3));
        columnaDescripcion.prefWidthProperty().bind(tablaCategorias.widthProperty().multiply(0.7));
    }    

    @FXML
    private void añadirCategoria(MouseEvent event) {
        try {
            // cambia a la opción de añadir categoria            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/AñadirCategoria.fxml"));
            Parent añadirCategoria = loader.load();
            
            // seleccionamos el borderpane del contenedor principal
            BorderPane principal = (BorderPane) añadirButton.getParent().getParent().getParent();
            
            principal.setCenter(añadirCategoria);
            
        } catch (IOException ex) {
            Logger.getLogger(ContenedorPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
