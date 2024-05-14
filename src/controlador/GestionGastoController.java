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
import model.Charge;

/**
 * FXML Controller class
 *
 * @author esteb
 */
public class GestionGastoController implements Initializable {

    private Button añadirButton;
    @FXML
    private BorderPane borderPanePrincipal;
    @FXML
    private TableView<Charge> tablaGastos;
    @FXML
    private Button añadirGasto;
    @FXML
    private TableColumn<?, ?> columnaNombre;
    @FXML
    private TableColumn<?, ?> columnaDescripcion;
    @FXML
    private TableColumn<?, ?> columnaCategoria;
    @FXML
    private TableColumn<?, ?> columnaCoste;
    @FXML
    private TableColumn<?, ?> columnaFecha;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        columnaNombre.setReorderable(false);
        columnaDescripcion.setReorderable(false);
        columnaCategoria.setReorderable(false);
        columnaCoste.setReorderable(false);
        columnaFecha.setReorderable(false);
    }    

    @FXML
    private void añadirGasto(MouseEvent event) {
        try {
            // cambia a la opción de añadir gasto            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/AñadirGasto.fxml"));
            Parent añadirGasto = loader.load();
            
            // seleccionamos el borderpane del contenedor principal
            BorderPane principal = (BorderPane) añadirButton.getParent().getParent().getParent();
            
            principal.setCenter(añadirGasto);
            
        } catch (IOException ex) {
            Logger.getLogger(ContenedorPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }
    
//    private BorderPane findParentBorderPane(Button button) {
//        Parent parent = button.getParent();
//        while (parent != null) {
//            if (parent instanceof BorderPane) {
//                return (BorderPane) parent;
//            }
//            parent = parent.getParent();
//        }
//        return null;
//    }
    
}
