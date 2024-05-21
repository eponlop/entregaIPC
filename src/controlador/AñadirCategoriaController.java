/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;

/**
 * FXML Controller class
 *
 * @author esteb
 */
public class AñadirCategoriaController implements Initializable {

    @FXML
    private Button cancelarButton;
    @FXML
    private Button aceptarButton;
    @FXML
    private TextField nombreText;
    @FXML
    private TextField descripcionText;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void cancelar(MouseEvent event) {
        try {
            // cambia a la opción de añadir gasto            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/GestionCategoria.fxml"));
            Parent gestionCategoria = loader.load();
            
            // seleccionamos el borderpane del contenedor principal
            BorderPane principal = (BorderPane) cancelarButton.getParent().getParent().getParent().getParent();
            
            principal.setCenter(gestionCategoria);
            
        } catch (IOException ex) {
            Logger.getLogger(ContenedorPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void guardarCategoria(MouseEvent event) {
        try {
            String nombre = nombreText.getText();
            String descripcion = descripcionText.getText();
            
            boolean isOK = Acount.getInstance().registerCategory(nombre, descripcion);
            if (isOK) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Añadir categoría");
                alert.setHeaderText("Categoría añadida");
                alert.setContentText("La categoría " + nombre + " ha sido añadida con éxito");
                
                Optional<ButtonType> respuesta = alert.showAndWait();
                if (respuesta.isPresent() && respuesta.get() == ButtonType.OK) {
                    // cambia a la opción de gestion categoria
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/GestionCategoria.fxml"));
                    Parent gestionCategoria = loader.load();

                    // seleccionamos el borderpane del contenedor principal
                    BorderPane principal = (BorderPane) cancelarButton.getParent().getParent().getParent().getParent();

                    principal.setCenter(gestionCategoria);
                }
            }
        } catch (AcountDAOException ex) {
            Logger.getLogger(AñadirCategoriaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AñadirCategoriaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
