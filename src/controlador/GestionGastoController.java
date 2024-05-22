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
import model.Charge;

/**
 * FXML Controller class
 *
 * @author esteb
 */
public class GestionGastoController implements Initializable {
    @FXML
    private TableView<Charge> tablaGastos;
    @FXML
    private TableColumn<Charge, String> columnaNombre;
    @FXML
    private TableColumn<Charge, String> columnaDescripcion;
    @FXML
    private TableColumn<Charge, String> columnaCategoria;
    @FXML
    private TableColumn<Charge, String> columnaCoste;
    @FXML
    private TableColumn<Charge, String> columnaFecha;
    @FXML
    private Button añadirButton;
    @FXML
    private VBox contenedorTabla;
    
    
    private ObservableList<Charge> datos;
    
    private List<Charge> misDatos;
    @FXML
    private BorderPane borderPanePrincipal;
    @FXML
    private Button editarButton;
    @FXML
    private Button eliminarButton;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        editarButton.disableProperty().bind(Bindings.equal(tablaGastos.getSelectionModel().selectedIndexProperty(), -1));
        eliminarButton.disableProperty().bind(Bindings.equal(tablaGastos.getSelectionModel().selectedIndexProperty(), -1));
        
        
        // TODO
        columnaNombre.setReorderable(false);
        columnaDescripcion.setReorderable(false);
        columnaCategoria.setReorderable(false);
        columnaCoste.setReorderable(false);
        columnaFecha.setReorderable(false);
        columnaNombre.prefWidthProperty().bind(tablaGastos.widthProperty().multiply(0.2));
        columnaDescripcion.prefWidthProperty().bind(tablaGastos.widthProperty().multiply(0.2));
        columnaCategoria.prefWidthProperty().bind(tablaGastos.widthProperty().multiply(0.2));
        columnaCoste.prefWidthProperty().bind(tablaGastos.widthProperty().multiply(0.2));
        columnaFecha.prefWidthProperty().bind(tablaGastos.widthProperty().multiply(0.2));
        tablaGastos.prefWidthProperty().bind(contenedorTabla.widthProperty());
        
        try {
            misDatos = Acount.getInstance().getUserCharges();
            datos = FXCollections.observableList(misDatos);
            tablaGastos.setItems(datos);
            columnaNombre.setCellValueFactory((gastoFila) -> {
                return new SimpleStringProperty(gastoFila.getValue().getName());
            });
            columnaCategoria.setCellValueFactory((gastoFila) -> {
                return new SimpleStringProperty(gastoFila.getValue().getCategory().getName());
            });
            columnaDescripcion.setCellValueFactory((gastoFila) -> {
                return new SimpleStringProperty(gastoFila.getValue().getDescription());
            });
            columnaFecha.setCellValueFactory((gastoFila) -> {
                return new SimpleStringProperty(gastoFila.getValue().getDate().toString());
            });
            columnaCoste.setCellValueFactory((gastoFila) -> {
                return new SimpleStringProperty(Double.toString(gastoFila.getValue().getCost()));
            });
        } catch (AcountDAOException ex) {
            Logger.getLogger(GestionCategoriaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GestionCategoriaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void añadirGasto(MouseEvent event) {
        try {
            // cambia a la opción de añadir gasto            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/AñadirGasto.fxml"));
            Parent añadirGasto = loader.load();
            
            // seleccionamos el borderpane del contenedor principal
            BorderPane principal = (BorderPane) añadirButton.getParent().getParent().getParent();
            
            principal.setCenter(añadirGasto);
            
        } catch (IOException ex) {
            Logger.getLogger(ContenedorPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }

    @FXML
    private void editar(MouseEvent event) {
        try {
            // cambia a la opción de añadir gasto            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/AñadirGasto.fxml"));
            Parent root = loader.load();
            
            AñadirGastoController añadirGasto = loader.getController();
            
            añadirGasto.setIndex(tablaGastos.getSelectionModel().getSelectedIndex());
            
            // seleccionamos el borderpane del contenedor principal
            BorderPane principal = (BorderPane) añadirButton.getParent().getParent().getParent();
            
            principal.setCenter(root);
            
        } catch (IOException ex) {
            Logger.getLogger(ContenedorPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void eliminar(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar gasto");
        alert.setHeaderText("Vas a eliminar un gasto");
        alert.setContentText("¿Estás seguro de eliminar el gasto?");
        
        Optional<ButtonType> respuesta = alert.showAndWait();
        if (respuesta.isPresent() && respuesta.get() == ButtonType.OK) {
            try {
                Acount.getInstance().removeCharge(tablaGastos.getSelectionModel().getSelectedItem());
                
                misDatos = Acount.getInstance().getUserCharges();
                datos = FXCollections.observableList(misDatos);
                tablaGastos.setItems(datos);
            } catch (AcountDAOException ex) {
                Logger.getLogger(GestionCategoriaController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(GestionCategoriaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
