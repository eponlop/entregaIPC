/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import model.Acount;
import model.AcountDAOException;
import model.Category;

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
    
    private ObservableList<Category> datos;
    
    private List<Category> misDatos;
    private boolean editado = false;
    
    private Category categoria;
    /**
     * Initializes the controller class.
     */
    
    public void setIndex(int i) {
        try {
            misDatos = Acount.getInstance().getUserCategories();
            datos = FXCollections.observableList(misDatos);
            categoria = datos.get(i);
            nombreText.setText(categoria.getName());
            descripcionText.setText(categoria.getDescription());
            editado = true;
        } catch (AcountDAOException ex) {
            Logger.getLogger(AñadirCategoriaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AñadirCategoriaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
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
            
            if (nombre.equals("") || descripcion.equals("")) {throw new IllegalArgumentException();}
            if (!editado) {
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
            } else {
                categoria.setName(nombre);
                categoria.setDescription(descripcion);
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Añadir categoría");
                alert.setHeaderText("Categoría editada");
                alert.setContentText("La categoría " + nombre + " ha sido editada con éxito");

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
        } catch (IllegalArgumentException ex) {
            // La categoria no tiene nombre
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Añadir categoría");
            alerta.setHeaderText("No se ha podido añadir la categoría");
            alerta.setContentText("Por favor rellena todos los campos");
            alerta.showAndWait();
        } catch (AcountDAOException ex) {
            // la categoria ya existe
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Añadir categoría");
            alerta.setHeaderText("No se ha podido añadir la categoría");
            alerta.setContentText("Se ha intentado añadir una categoría ya existente");
            alerta.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(AñadirCategoriaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
