/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;

/**
 * FXML Controller class
 *
 * @author jomar
 */
public class RegistroUsuarioController implements Initializable {

    @FXML
    private Button cancelarButton;
    @FXML
    private TextField nombreText;
    @FXML
    private TextField apellidoText;
    @FXML
    private TextField emailText;
    @FXML
    private TextField loginText;
    @FXML
    private TextField passwordText;

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
            // cambia a la opción de registrar usuario            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/InicioSesión.fxml"));
            Parent iniciarSesion = loader.load();
            Scene scene = new Scene(iniciarSesion);

            Stage stage = (Stage) cancelarButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(ContenedorPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void aceptar(MouseEvent event) {
        String name = nombreText.getText();
        String surname = apellidoText.getText();
        String email = emailText.getText();
        String login = loginText.getText();
        String password = passwordText.getText();
        InputStream input = getClass().getResourceAsStream("/avatars/default.png");
        Image image; 
        LocalDate date = LocalDate.now();
        boolean isOK;
        try {
            image = new Image(input);   // falta la opción de elegir foto del usuario si selecciona una
            isOK = Acount.getInstance().registerUser(name, surname, email, login, password, image, date);
            if (isOK) {
                System.out.println("OK");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Registro Correcto");
                
                Optional<ButtonType> respuesta = alert.showAndWait();
                if (respuesta.isPresent() && respuesta.get() == ButtonType.OK) {
                    // cambia a la opción de registrar usuario 
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/InicioSesión.fxml"));
                    Parent iniciarSesion = loader.load();
                    Scene scene = new Scene(iniciarSesion);

                    Stage stage = (Stage) cancelarButton.getScene().getWindow();
                    stage.setScene(scene);
                }      
            }
        } catch (AcountDAOException e) {
            // este es el sitio dnd se indica que el nickname es el mismo
            System.out.println("NOT OK");
        } catch (IOException e) {
        }
        
    }
    
}
