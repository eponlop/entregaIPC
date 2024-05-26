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
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;

/**
 * FXML Controller class
 *
 * @author esteb
 */
public class InicioSesiónController implements Initializable {

    @FXML
    private Button entrarButton;
    @FXML
    private TextField loginText;
    @FXML
    private TextField passText;
    @FXML
    private VBox boxUser;
    @FXML
    private VBox boxPass;
    @FXML
    private PasswordField passTextOculto;
    
    private boolean verPass = false;
    @FXML
    private Button botonVisualizar;
    @FXML
    private ImageView imagenVerPassword;
    
    private Stage primaryStage;
    
    Image ojo_normal = new Image(getClass().getResourceAsStream("/resources/images/Ojo_normal.png"));
    Image ojo_selected = new Image(getClass().getResourceAsStream("/resources/images/Ojo_selected.png"));

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Platform.runLater(() -> {
        boxUser.prefWidthProperty().bind(boxPass.widthProperty());
        loginText.maxWidthProperty().bind(passText.widthProperty());
        passText.textProperty().bindBidirectional(passTextOculto.textProperty());

        loginText.getScene().addEventFilter(KeyEvent.KEY_PRESSED, this::handleEnterPressed);
    });
    }    
    
    

    @FXML
    private void registrar(MouseEvent event){
        final boolean isMaximized;
        try {
            // Obtiene la ventana actual
            Stage stage = (Stage) entrarButton.getScene().getWindow();
            isMaximized = stage.isMaximized(); // Verifica si la ventana actual está maximizada

            // Cambia a la opción de registrar usuario           
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/RegistroUsuario.fxml"));
            Parent registroUsuario = loader.load();
            Scene scene = new Scene(registroUsuario);

            Stage newStage = new Stage(); // Crea una nueva ventana
            newStage.setTitle("Registrarse");
            Image logo = new Image("resources/images/Logo.png");
            newStage.getIcons().add(logo);
            newStage.setScene(scene);

            // Aplica el estado de maximización a la nueva ventana
            if (isMaximized) {
                newStage.setMaximized(true);
            }

            // Muestra la nueva ventana
            newStage.show();

            // Cierra la ventana actual si es necesario
            
            stage.close();
            
            
        } catch (IOException ex) {
            Logger.getLogger(ContenedorPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void entrar(MouseEvent event) {
        loginText.getScene().addEventFilter(KeyEvent.KEY_PRESSED, this::handleEnterPressed);
        enter();
    }
    
    @FXML
    private void enter() {
        String login = loginText.getText();
        String password = passText.getText();
        try {
            boolean isOK = Acount.getInstance().logInUserByCredentials(login, password);
            if (isOK) {
                System.out.println("OK");
                
                Stage stage = (Stage) botonVisualizar.getScene().getWindow();
                boolean isMaximized = stage.isMaximized(); // Guarda el estado de maximización
                
                // cambia a la opción de registrar usuario            
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/ContenedorPrincipal.fxml"));
                Parent principal = loader.load();
                Scene scene = new Scene(principal);

                // Muestra la nueva ventana
                Stage newStage = new Stage();
                newStage.setTitle("Budget Buddy");
                Image logo = new Image("resources/images/Logo.png");
                newStage.getIcons().add(logo);
                newStage.setScene(scene);

                // Aplica el estado de maximización a la nueva ventana
                newStage.setMaximized(isMaximized);
                

                // Cierra la ventana actual si es necesario
                stage.close();

                // Muestra la nueva ventana
                newStage.show();
                
                
            } else {
                // login failed
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error login");
                alert.setHeaderText("Error intentando acceder");
                alert.setContentText("El usuario o la contraseña no son correctos");
                alert.showAndWait();
                loginText.setText("");
                passText.setText("");
                loginText.requestFocus();
            }
        } catch (AcountDAOException e) {
            
        } catch (IOException e) {
        
        }
    }


    private void handleEnterPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (loginText.isFocused()) {
                passTextOculto.requestFocus();
            } else {
                enter();
            }
            event.consume(); // Evita que otros nodos manejen este evento también
        }
    }
    
    
    @FXML
    private void ver(MouseEvent event) {
        if (verPass) {
            passTextOculto.toFront();
            botonVisualizar.getStyleClass().removeAll("button-visualizar-selected");
            botonVisualizar.getStyleClass().add("button-visualizar-normal");
            imagenVerPassword.setImage(ojo_normal);
        } else {
            passText.toFront();
            botonVisualizar.getStyleClass().add("button-visualizar-selected");
            imagenVerPassword.setImage(ojo_selected);
        }
        verPass = !verPass;
    }
}
