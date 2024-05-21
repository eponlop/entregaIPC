/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.io.File;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
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
    @FXML
    private TextField repeatPassText;
    
    private Image image = null;
    @FXML
    private ImageView imageView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loginText.textProperty().addListener((a, b, c) -> {
            if (c.contains(" ")) {
                loginText.setText(b);
            }
        });
        
        imageView.setImage(new Image(getClass().getResourceAsStream("/avatars/default.png")));
        
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
        LocalDate date = LocalDate.now();
        boolean isOK;
        
        if (password.length() < 6) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error en contraseña");
            alert.setHeaderText("La contraseña no es válida");
            alert.setContentText("La contraseña debe ser un combinación de letras y números de al menos 6 caracteres");
            alert.showAndWait();
            passwordText.setText("");
            repeatPassText.setText("");
            passwordText.requestFocus();
            return;
        } else if (!password.equals(repeatPassText.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error en contraseña");
            alert.setHeaderText("Las contraseñas no coinciden");
            alert.setContentText("Debe especificar la misma contraseña en ambos campos");
            alert.showAndWait();
            passwordText.setText("");
            repeatPassText.setText("");
            passwordText.requestFocus();
            return;
        }
        try {
            if (image == null) {
                image = new Image(input);
            }
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
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error en el nickname");
            alert.setHeaderText("El nickname no está dispobible");
            alert.setContentText("Introduzca otro nickname por favor");
            alert.showAndWait();
            loginText.setText("");
            loginText.requestFocus();
        } catch (IOException e) {
        }
        
    }

    @FXML
    private void seleccionarArchivo(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona una imagen");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Archivos de imagen", "*.png", "*.jpg", "*.jpeg")
        );
        File file = fileChooser.showOpenDialog((Stage) cancelarButton.getScene().getWindow());
        if (file != null) {
            image = new Image(file.toURI().toString());
            imageView.setImage(image);
        }
    }
    
}
