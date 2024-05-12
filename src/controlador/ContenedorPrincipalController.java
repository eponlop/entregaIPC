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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author esteb
 */ 
public class ContenedorPrincipalController implements Initializable {

    @FXML
    private BorderPane borderPanePrincipal;
    @FXML
    private Button añadirGasto;
    @FXML
    private Button editarGasto;
    @FXML
    private Button visualizarGasto;
    @FXML
    private Button imprimir;
    @FXML
    private Button añadirCategoria;
    @FXML
    private Button editarCategoria;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TO DO
        
        try {
            // inicia la app con la opción de añadir gasto
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/AñadirGasto.fxml"));
            Parent añadirGasto = loader.load();
            borderPanePrincipal.setCenter(añadirGasto);
        } catch (IOException ex) {
            Logger.getLogger(ContenedorPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void añadirGasto(MouseEvent event) {
        try {
            // cambia a la opción añadir gasto
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/AñadirGasto.fxml"));
            Parent añadirGasto = loader.load();
            borderPanePrincipal.setCenter(añadirGasto);
        } catch (IOException ex) {
            Logger.getLogger(ContenedorPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void editarGasto(MouseEvent event) {
        try {
            // cambia a la opción editar gasto
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/EditarGasto.fxml"));
            Parent editarGasto = loader.load();
            borderPanePrincipal.setCenter(editarGasto);
        } catch (IOException ex) {
            Logger.getLogger(ContenedorPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void visualizarGasto(MouseEvent event) {
        try {
            // cambia a la opción de visualizar gasto
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VisualizarGasto.fxml"));
            Parent visualizarGasto = loader.load();
            borderPanePrincipal.setCenter(visualizarGasto);
        } catch (IOException ex) {
            Logger.getLogger(ContenedorPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void imprimir(MouseEvent event) {
        try {
            // cambia a la opción de imprimir
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Imprimir.fxml"));
            Parent imprimir = loader.load();
            borderPanePrincipal.setCenter(imprimir);
        } catch (IOException ex) {
            Logger.getLogger(ContenedorPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void añadirCategoria(MouseEvent event) {
        try {
            // cambia a la opción de añadir categoría
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/AñadirCategoria.fxml"));
            Parent añadirCategoria = loader.load();
            borderPanePrincipal.setCenter(añadirCategoria);
        } catch (IOException ex) {
            Logger.getLogger(ContenedorPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void editarCategoria(MouseEvent event) {
        try {
            // cambia a la opción de editar categoría
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/EditarCategoria.fxml"));
            Parent editarCategoria = loader.load();
            borderPanePrincipal.setCenter(editarCategoria);
        } catch (IOException ex) {
            Logger.getLogger(ContenedorPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void salir(MouseEvent event) {
        // implementar el log out cambiando la escena a la autentificación        
    }

    @FXML
    private void configuracion(MouseEvent event) {
        try {
            // cambia a la opción de configuración
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Configuracion.fxml"));
            Parent configuracion = loader.load();
            borderPanePrincipal.setCenter(configuracion);
        } catch (IOException ex) {
            Logger.getLogger(ContenedorPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
