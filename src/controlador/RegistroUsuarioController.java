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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jomar
 */
public class RegistroUsuarioController implements Initializable {

    @FXML
    private Button cancelarButton;

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
            if (cancelarButton.getParent().getParent().getParent() instanceof BorderPane) {
                // cambia a la opción de gestión gasto            
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/GestionGasto.fxml"));
                Parent gestionGasto = loader.load();

                // seleccionamos el borderpane del contenedor principal
                BorderPane principal = (BorderPane) cancelarButton.getParent().getParent().getParent();

                principal.setCenter(gestionGasto);
            } else {
                // cambia a la opción de registrar usuario            
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/InicioSesión.fxml"));
                Parent iniciarSesion = loader.load();
                Scene scene = new Scene(iniciarSesion);

                Stage stage = (Stage) cancelarButton.getScene().getWindow();
                stage.setScene(scene);
            }
        } catch (IOException ex) {
            Logger.getLogger(ContenedorPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
