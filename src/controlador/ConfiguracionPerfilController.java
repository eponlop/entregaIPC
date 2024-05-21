/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;


/**
 *
 * @author esteb
 */
public class ConfiguracionPerfilController implements Initializable {

    @FXML
    private StackPane stackPane;
    @FXML
    private Button guardarButton;
    @FXML
    private Button editarButton;
    @FXML
    private HBox cajaDelStack;
    @FXML
    private Button cancelarButton;
    @FXML
    private TextField nombreText;
    @FXML
    private TextField apellidoText;
    @FXML
    private TextField correoText;
    @FXML
    private TextField repitePassText;
    @FXML
    private ImageView imageView;
    @FXML
    private Button seleccionarButton;
    
    private Acount cuenta;
    @FXML
    private VBox cajaRepitePass;
    @FXML
    private TextField passText;

    private Image image;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
            // TODO
            cancelarButton.setVisible(false);
            guardarButton.setVisible(false);
            
        try {
            nombreText.setText(Acount.getInstance().getLoggedUser().getName());
            apellidoText.setText(Acount.getInstance().getLoggedUser().getSurname());
            correoText.setText(Acount.getInstance().getLoggedUser().getEmail());
            imageView.setImage(Acount.getInstance().getLoggedUser().getImage());
            passText.setText(Acount.getInstance().getLoggedUser().getPassword());
            repitePassText.setText(Acount.getInstance().getLoggedUser().getPassword());
            image = Acount.getInstance().getLoggedUser().getImage();
            
        } catch (AcountDAOException ex) {
            Logger.getLogger(ConfiguracionPerfilController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConfiguracionPerfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    } 
    
   
    @FXML
    private void guardarCambiosClick(MouseEvent event) {
        //Cambios en la interfaz
        if (passText.getText().length() < 6) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error en contraseña");
            alert.setHeaderText("La contraseña no es válida");
            alert.setContentText("La contraseña debe ser un combinación de letras y números de al menos 6 caracteres");
            alert.showAndWait();
            passText.setText("");
            repitePassText.setText("");
            passText.requestFocus();
        } else if (!passText.getText().equals(repitePassText.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error en contraseña");
            alert.setHeaderText("Las contraseñas no coinciden");
            alert.setContentText("Debe especificar la misma contraseña en ambos campos");
            alert.showAndWait();
            passText.setText("");
            repitePassText.setText("");
            passText.requestFocus();
        } else {
            cajaDelStack.toBack();
            guardarButton.setDisable(true);
            guardarButton.setVisible(false);
            cancelarButton.setDisable(true);
            cancelarButton.setVisible(false);
            editarButton.setVisible(true);
            editarButton.setDisable(false);

            nombreText.setEditable(false);
            apellidoText.setEditable(false);
            correoText.setEditable(false);
            passText.setEditable(false);
            repitePassText.setEditable(false);
            seleccionarButton.setDisable(true);
            cajaRepitePass.setVisible(false);
            cajaRepitePass.setDisable(true);
            
            try {
                Acount.getInstance().getLoggedUser().setName(nombreText.getText());
                Acount.getInstance().getLoggedUser().setSurname(apellidoText.getText());
                Acount.getInstance().getLoggedUser().setEmail(correoText.getText());
                Acount.getInstance().getLoggedUser().setPassword(passText.getText());
                Acount.getInstance().getLoggedUser().setImage(image);
                
            } catch (AcountDAOException ex) {
                Logger.getLogger(ConfiguracionPerfilController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ConfiguracionPerfilController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            
        }
    }

    @FXML
    private void cancelarClick(MouseEvent event) {
        
            //Cambios en la interfaz
            cajaDelStack.toBack();
            guardarButton.setDisable(true);
            guardarButton.setVisible(false);
            cancelarButton.setDisable(true);
            cancelarButton.setVisible(false);
            editarButton.setVisible(true);
            editarButton.setDisable(false);
            editarButton.toFront();
            
            nombreText.setEditable(false);
            apellidoText.setEditable(false);
            correoText.setEditable(false);
            passText.setEditable(false);
            repitePassText.setEditable(false);
            seleccionarButton.setDisable(true);
            cajaRepitePass.setVisible(false);
            cajaRepitePass.setDisable(true);
            
            
        try {    
            nombreText.setText(Acount.getInstance().getLoggedUser().getName());
            apellidoText.setText(Acount.getInstance().getLoggedUser().getSurname());
            correoText.setText(Acount.getInstance().getLoggedUser().getEmail());
            imageView.setImage(Acount.getInstance().getLoggedUser().getImage());
            passText.setText(Acount.getInstance().getLoggedUser().getPassword());
            repitePassText.setText(Acount.getInstance().getLoggedUser().getPassword());

        } catch (AcountDAOException ex) {
            Logger.getLogger(ConfiguracionPerfilController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConfiguracionPerfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Cambios en la cuenta");
        alerta.setHeaderText("No se han guardado los cambios");
        alerta.setContentText("Los cambios han sido cancelados");
        alerta.showAndWait();
    }

    @FXML
    private void editarClick(MouseEvent event) {
        //Cambios en la interfaz
        editarButton.toBack();
        editarButton.setVisible(false);
        editarButton.setDisable(true);
        guardarButton.setDisable(false);
        guardarButton.setVisible(true);
        cancelarButton.setDisable(false);
        cancelarButton.setVisible(true);
        
        nombreText.setEditable(true);
        apellidoText.setEditable(true);
        correoText.setEditable(true);
        passText.setEditable(true);
        repitePassText.setEditable(true);
        seleccionarButton.setDisable(false);
        cajaRepitePass.setVisible(true);
        cajaRepitePass.setDisable(false);
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
