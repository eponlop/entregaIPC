/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;


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


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cancelarButton.setVisible(false);
        guardarButton.setVisible(false);
    } 
    
   
    @FXML
    private void guardarCambiosClick(MouseEvent event) {
        cajaDelStack.toBack();
        guardarButton.setDisable(true);
        guardarButton.setVisible(false);
        cancelarButton.setDisable(true);
        cancelarButton.setVisible(false);
    
        
        editarButton.setVisible(true);
        editarButton.setDisable(false);
        editarButton.toFront();
    }

    @FXML
    private void cancelarClick(MouseEvent event) {
        cajaDelStack.toBack();
        guardarButton.setDisable(true);
        guardarButton.setVisible(false);
        cancelarButton.setDisable(true);
        cancelarButton.setVisible(false);
    
        
        editarButton.setVisible(true);
        editarButton.setDisable(false);
        editarButton.toFront();
    }

    @FXML
    private void editarClick(MouseEvent event) {
        editarButton.toBack();
        editarButton.setVisible(false);
        editarButton.setDisable(true);
        
        guardarButton.setDisable(false);
        guardarButton.setVisible(true);
        cancelarButton.setDisable(false);
        cancelarButton.setVisible(true);
        cajaDelStack.toFront();
    }
    
}
