/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Acount;
import model.AcountDAOException;
import model.Category;
import model.Charge;

/**
 * FXML Controller class
 *
 * @author esteb
 */
public class AñadirGastoController implements Initializable {

    @FXML
    private Button cancelarButton;
    
    private Image scanImage;
    @FXML
    private TextField nombreText;
    @FXML
    private TextField descripcionText;
    @FXML
    private ComboBox<Category> categoriaChooser;
    @FXML
    private TextField costeText;
    @FXML
    private TextField unitsText;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button aceptarButton;
    
    private ObservableList<Charge> datos;
    
    private List<Charge> misDatos;
    private Charge gasto;
    private boolean editado = false;
    
    
    
    
    public void setIndex(int i) {
        try {
            misDatos = Acount.getInstance().getUserCharges();
            datos = FXCollections.observableList(misDatos);
            gasto = datos.get(i);
            nombreText.setText(gasto.getName());
            descripcionText.setText(gasto.getDescription());
            categoriaChooser.setValue(gasto.getCategory());
            costeText.setText(Double.toString(gasto.getCost()));
            unitsText.setText(Integer.toString(gasto.getUnits()));
            datePicker.setValue(gasto.getDate());
            scanImage = gasto.getImageScan();
            // falta el imageview
           
            
            editado = true;
        } catch (AcountDAOException ex) {
            Logger.getLogger(AñadirCategoriaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AñadirCategoriaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            ObservableList<Category> datos = FXCollections.observableList(Acount.getInstance().getUserCategories());
            categoriaChooser.setItems(datos);
            categoriaChooser.setConverter(new StringConverter<Category>() {
                @Override
                public String toString(Category category) {
                    return category != null ? category.getName(): "";
                }

                @Override
                public Category fromString(String string) {
                    return null;
                }
            });
            
        } catch (AcountDAOException ex) {
            Logger.getLogger(AñadirGastoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AñadirGastoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void cancelar(MouseEvent event) {
        try {
            // cambia a la opción de añadir gasto            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/GestionGasto.fxml"));
            Parent gestionGasto = loader.load();

            // seleccionamos el borderpane del contenedor principal
            BorderPane principal = (BorderPane) cancelarButton.getParent().getParent().getParent().getParent();

            principal.setCenter(gestionGasto);
        } catch (IOException ex) {
            Logger.getLogger(AñadirCategoriaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void guardarGasto(MouseEvent event) {
        try {
            String nombre = nombreText.getText();
            String descripcion = descripcionText.getText();
            double cost = Double.parseDouble(costeText.getText());
            int units = Integer.parseInt(unitsText.getText());
            LocalDate date = datePicker.getValue();
            Category category = categoriaChooser.getValue();
            
            if (!editado) {
                boolean isOK = Acount.getInstance().registerCharge(nombre, descripcion, cost, units, scanImage, date, category);
                if (isOK) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Añadir gasto");
                    alert.setHeaderText("Gasto añadido");
                    alert.setContentText("El gasto " + nombre + " ha sido añadido con éxito");

                    Optional<ButtonType> respuesta = alert.showAndWait();
                    if (respuesta.isPresent() && respuesta.get() == ButtonType.OK) {
                        // cambia a la opción de añadir gasto            
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/GestionGasto.fxml"));
                        Parent gestionGasto = loader.load();

                        // seleccionamos el borderpane del contenedor principal
                        BorderPane principal = (BorderPane) cancelarButton.getParent().getParent().getParent().getParent();

                        principal.setCenter(gestionGasto);
                    }
                }
            } else {
                gasto.setName(nombre);
                gasto.setDescription(descripcion);
                gasto.setCost(cost);
                gasto.setUnits(units);
                gasto.setDate(date);
                gasto.setCategory(category);
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Añadir gasto");
                alert.setHeaderText("Gasto editado");
                alert.setContentText("El gasto " + nombre + " ha sido editado con éxito");

                Optional<ButtonType> respuesta = alert.showAndWait();
                if (respuesta.isPresent() && respuesta.get() == ButtonType.OK) {
                    // cambia a la opción de gestion categoria
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/GestionGasto.fxml"));
                    Parent gestionGasto = loader.load();

                    // seleccionamos el borderpane del contenedor principal
                    BorderPane principal = (BorderPane) cancelarButton.getParent().getParent().getParent().getParent();

                    principal.setCenter(gestionGasto);
                }
            }
            
            
            
            
        } catch (AcountDAOException ex) {
            // no se edita y se guarda
        } catch (IOException ex) {
            Logger.getLogger(AñadirCategoriaController.class.getName()).log(Level.SEVERE, null, ex);
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
            scanImage = new Image(file.toURI().toString());
            // poner en el imageview la imagen
        }
    }
    
}
