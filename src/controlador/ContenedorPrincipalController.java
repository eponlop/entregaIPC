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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;

/**
 * FXML Controller class
 *
 * @author esteb
 */ 
public class ContenedorPrincipalController implements Initializable {

    @FXML
    private BorderPane borderPanePrincipal;
    @FXML
    private Button salirButton;
    @FXML
    private Button gestionGastoButton;
    @FXML
    private Button gestionCategoriaButton;
    @FXML
    private Button configuracionButton;
    @FXML
    private ImageView imageView;
    @FXML
    private Text bienvenidaText;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gestionGastoButton.getStyleClass().add("button-bold-hover");
        try {
            imageView.setImage(Acount.getInstance().getLoggedUser().getImage());
            bienvenidaText.setText("Bienvenido " + Acount.getInstance().getLoggedUser().getNickName() + "!");
        } catch (AcountDAOException ex) {
            Logger.getLogger(ContenedorPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ContenedorPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            // inicia la app con la opción de gestión de gasto
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/GestionGasto.fxml"));
            Parent añadirGasto = loader.load();
            borderPanePrincipal.setCenter(añadirGasto);
        } catch (IOException ex) {
            Logger.getLogger(ContenedorPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void salir(MouseEvent event) {
        try {
            // cambia a la opción de registrar usuario            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/InicioSesión.fxml"));
            Parent inicioSesion = loader.load();
            Scene scene = new Scene(inicioSesion);
            
            Stage stage = (Stage) salirButton.getScene().getWindow();
            stage.setScene(scene);
            
            
        } catch (IOException ex) {
            Logger.getLogger(ContenedorPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }

    @FXML
    private void configuracion(MouseEvent event) {
        // resaltar el seleccionado
        gestionGastoButton.getStyleClass().removeAll("button-bold-hover");
        gestionCategoriaButton.getStyleClass().removeAll("button-bold-hover");
        configuracionButton.getStyleClass().add("button-bold-hover");
        try {
            // cambia a la opción de configuración
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/ConfiguracionPerfil.fxml"));
            Parent configuracion = loader.load();
            borderPanePrincipal.setCenter(configuracion);
        } catch (IOException ex) {
            Logger.getLogger(ContenedorPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void gestionGasto(MouseEvent event) {
        // resaltar el seleccionado
        gestionGastoButton.getStyleClass().add("button-bold-hover");
        gestionCategoriaButton.getStyleClass().removeAll("button-bold-hover");
        configuracionButton.getStyleClass().removeAll("button-bold-hover");
        
        try {
            // cambia a la opción gestión gasto
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/GestionGasto.fxml"));
            Parent editarGasto = loader.load();
            borderPanePrincipal.setCenter(editarGasto);
        } catch (IOException ex) {
            Logger.getLogger(ContenedorPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void gestionCategoria(MouseEvent event) {
        // resaltar el seleccionado
        gestionGastoButton.getStyleClass().removeAll("button-bold-hover");
        gestionCategoriaButton.getStyleClass().add("button-bold-hover");
        configuracionButton.getStyleClass().removeAll("button-bold-hover");
        try {
            // cambia a la opción de gestión categoría
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/GestionCategoria.fxml"));
            Parent editarCategoria = loader.load();
            borderPanePrincipal.setCenter(editarCategoria);
        } catch (IOException ex) {
            Logger.getLogger(ContenedorPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//    public void setImage() {
//        System.out.println("HASTA AQUI LLEGA");
//        try {
//            imageView = new ImageView(Acount.getInstance().getLoggedUser().getImage());
//        } catch (AcountDAOException ex) {
//            Logger.getLogger(ContenedorPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(ContenedorPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
}
