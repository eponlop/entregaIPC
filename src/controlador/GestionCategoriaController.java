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
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.Acount;
import model.AcountDAOException;
import model.Category;
/**
 * FXML Controller class
 *
 * @author esteb
 */
public class GestionCategoriaController implements Initializable {

    @FXML
    private TableColumn<Category, String> columnaCategoria;
    @FXML
    private TableColumn<Category, String> columnaDescripcion;
    @FXML
    private VBox contenedorTabla;
    @FXML
    private TableView<Category> tablaCategorias;
    @FXML
    private Button añadirButton;
    @FXML
    private Button editarButton;
    @FXML
    private Button eliminarButton;
    
    private ObservableList<Category> datos;
    
    private List<Category> misDatos;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        url = url;
        rb = rb;
        // TODO
        columnaCategoria.setReorderable(false);
        columnaDescripcion.setReorderable(false);
        tablaCategorias.prefWidthProperty().bind(contenedorTabla.widthProperty());
        columnaCategoria.prefWidthProperty().bind(tablaCategorias.widthProperty().multiply(0.3));
        columnaDescripcion.prefWidthProperty().bind(tablaCategorias.widthProperty().multiply(0.7));
        editarButton.disableProperty().bind(Bindings.equal(tablaCategorias.getSelectionModel().selectedIndexProperty(), -1));
        eliminarButton.disableProperty().bind(Bindings.equal(tablaCategorias.getSelectionModel().selectedIndexProperty(), -1));
        
        try {
            misDatos = Acount.getInstance().getUserCategories();
            datos = FXCollections.observableList(misDatos);
            tablaCategorias.setItems(datos);
            columnaCategoria.setCellValueFactory((categoriaFila) -> {
                return new SimpleStringProperty(categoriaFila.getValue().getName());
            });
            columnaDescripcion.setCellValueFactory((categoriaFila) -> {
                return new SimpleStringProperty(categoriaFila.getValue().getDescription());
            });
        } catch (AcountDAOException ex) {
            Logger.getLogger(GestionCategoriaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GestionCategoriaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void añadirCategoria(MouseEvent event) {
        try {
            // cambia a la opción de añadir categoria            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/AñadirCategoria.fxml"));
            Parent añadirCategoria = loader.load();
            
            // seleccionamos el borderpane del contenedor principal
            BorderPane principal = (BorderPane) añadirButton.getParent().getParent().getParent();
            
            principal.setCenter(añadirCategoria);
            
        } catch (IOException ex) {
            Logger.getLogger(ContenedorPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void editar(MouseEvent event) {
        try {
            // cambia a la opción de añadir categoria            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/AñadirCategoria.fxml"));
            Parent añadirCategoria = loader.load();
            
            // seleccionamos el borderpane del contenedor principal
            BorderPane principal = (BorderPane) añadirButton.getParent().getParent().getParent();
            
            principal.setCenter(añadirCategoria);
            
        } catch (IOException ex) {
            Logger.getLogger(ContenedorPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void eliminar(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar categoría");
        alert.setHeaderText("Vas a eliminar una categoría");
        alert.setContentText("¿Estás seguro de eliminar la categoría? Se eliminarán todos los gastos asociados");
        
        Optional<ButtonType> respuesta = alert.showAndWait();
        if (respuesta.isPresent() && respuesta.get() == ButtonType.OK) {
            try {
                Acount.getInstance().removeCategory(tablaCategorias.getSelectionModel().getSelectedItem());
                
                misDatos = Acount.getInstance().getUserCategories();
                datos = FXCollections.observableList(misDatos);
                tablaCategorias.setItems(datos);
            } catch (AcountDAOException ex) {
                Logger.getLogger(GestionCategoriaController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(GestionCategoriaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
  
    
}
