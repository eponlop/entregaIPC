/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;



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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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
    
    @FXML
    private void imprimir(MouseEvent event) {
            generatePDF();
    }
    
    private void generatePDF() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog((Stage) añadirButton.getScene().getWindow());
        if (file != null) {
               writePDF(file, tablaGastos.getItems());
        }
    }
    
    private void writePDF(File file, List<Charge> data) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            document.add(new Paragraph("Resumen de todos los gastos"));
            
            PdfPTable table = new PdfPTable(5); // Number of columns
            Font headFont = new Font(Font.FontFamily.HELVETICA,16,Font.BOLD);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);
            
            PdfPCell cell1 = new PdfPCell(new Phrase("Nombre",headFont));
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell1);
            
            PdfPCell cell2 = new PdfPCell(new Phrase("Descripción",headFont));
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell2);
            
            PdfPCell cell3 = new PdfPCell(new Phrase("Categoria",headFont));
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell3);
            
            PdfPCell cell4 = new PdfPCell(new Phrase("Coste",headFont));
            cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell4);
            
            PdfPCell cell5 = new PdfPCell(new Phrase("Fecha",headFont));
            cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell5);
            
            for (Charge charge : data) {
                table.addCell(charge.getName());
                table.addCell(charge.getDescription());
                table.addCell(charge.getCategory().getName());
                table.addCell(Double.toString(charge.getCost()));
                table.addCell(charge.getDate().toString());
            }
            
            document.add(table);
            document.close();
        } catch (DocumentException ex) {
            Logger.getLogger(GestionGastoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
         //JIJI
        }
    }
}
