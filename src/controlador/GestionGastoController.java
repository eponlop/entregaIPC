/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;



import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            columnaFecha.setCellValueFactory((gastoFila) -> {
                return new SimpleStringProperty(gastoFila.getValue().getDate().format(formatter));
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
    private void imprimir(MouseEvent event) throws BadElementException, IOException {
            generatePDF();
    }
    
    private void generatePDF() throws BadElementException, IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog((Stage) añadirButton.getScene().getWindow());
        if (file != null) {
               writePDF(file, tablaGastos.getItems());
        }
    }
    
    private void writePDF(File file, List<Charge> data) throws BadElementException, IOException {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            
            Image img = Image.getInstance(getClass().getResource("/resources/images/Logo.png"));


            img.scaleToFit(60, 60); // Escalar la imagen al tamaño deseado
            img.setAbsolutePosition(document.getPageSize().getWidth() - 90, document.getPageSize().getHeight() - 70); // Posicionar la imagen en la esquina superior derecha
            document.add(img);
            
            BaseColor customColorAzul = new BaseColor(0x48, 0x9f, 0xea); // RGB color #489fea
            Font titleFont = FontFactory.getFont("YuGothic-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 30, Font.NORMAL, customColorAzul);
            
            Paragraph title = new Paragraph("Resumen de todos los gastos", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            
            document.add(new Paragraph("\n")); // Párrafo vacío
            
            LineSeparator line = new LineSeparator();
            line.setLineColor(customColorAzul); // Color #489fea
            line.setLineWidth(1); // Grosor de la línea
            
            document.add(line); // Párrafo vacío
            
            document.add(new Paragraph("\n")); // Párrafo vacío
            
            PdfPTable table = new PdfPTable(5); // Number of columns
            
            Font headFont = FontFactory.getFont("YuGothic-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 12, Font.BOLD, BaseColor.WHITE);
            
            BaseColor customColorGris = new BaseColor(155, 155, 155);
            BaseColor customColorAzulClaro = new BaseColor(128, 196, 255);
            
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);
            
            PdfPCell cell1 = new PdfPCell(new Phrase("Nombre",headFont));
            cell1.setFixedHeight(20);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell1.setBackgroundColor(customColorAzul);
            cell1.setBorderColor(customColorAzulClaro);
            table.addCell(cell1);
            
            PdfPCell cell2 = new PdfPCell(new Phrase("Descripción",headFont));
            cell2.setFixedHeight(20);
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell2.setBackgroundColor(customColorAzul);
            cell2.setBorderColor(customColorAzulClaro);
            table.addCell(cell2);
            
            PdfPCell cell3 = new PdfPCell(new Phrase("Categoría",headFont));
            cell3.setFixedHeight(20);
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell3.setBackgroundColor(customColorAzul);
            cell3.setBorderColor(customColorAzulClaro);
            table.addCell(cell3);
            
            PdfPCell cell4 = new PdfPCell(new Phrase("Coste",headFont));
            cell4.setFixedHeight(20);
            cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell4.setBackgroundColor(customColorAzul);
            cell4.setBorderColor(customColorAzulClaro);
            table.addCell(cell4);
            
            PdfPCell cell5 = new PdfPCell(new Phrase("Fecha",headFont));
            cell5.setFixedHeight(20);
            cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell5.setBackgroundColor(customColorAzul);
            cell5.setBorderColor(customColorAzulClaro);
            table.addCell(cell5);
            
            Font fontText = FontFactory.getFont("YuGothic-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 12, Font.NORMAL, BaseColor.BLACK);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            for (Charge charge : data) {
                
                PdfPCell cellr1 = new PdfPCell();
                cellr1.setFixedHeight(20);
                cellr1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellr1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellr1.setBorderColor(customColorGris);
                cellr1.setPhrase(new Phrase(charge.getName(), fontText));
                
                PdfPCell cellr2 = new PdfPCell();
                cellr2.setFixedHeight(20);
                cellr2.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellr2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellr2.setBorderColor(customColorGris);
                cellr2.setPhrase(new Phrase(charge.getDescription(), fontText));
                
                PdfPCell cellr3 = new PdfPCell();
                cellr3.setFixedHeight(20);
                cellr3.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellr3.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellr3.setBorderColor(customColorGris);
                cellr3.setPhrase(new Phrase(charge.getCategory().getName(), fontText));
                
                PdfPCell cellr4 = new PdfPCell();
                cellr4.setFixedHeight(20);
                cellr4.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellr4.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellr4.setBorderColor(customColorGris);
                cellr4.setPhrase(new Phrase(Double.toString(charge.getCost()), fontText));
                
                PdfPCell cellr5 = new PdfPCell();
                cellr5.setFixedHeight(20);
                cellr5.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellr5.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellr5.setBorderColor(customColorGris);
                cellr5.setPhrase(new Phrase(charge.getDate().format(formatter), fontText));
                
                table.addCell(cellr1);
                table.addCell(cellr2);
                table.addCell(cellr3);
                table.addCell(cellr4);
                table.addCell(cellr5);
            }
            
            document.add(table);
            document.close();
        } catch (DocumentException ex) {
            Logger.getLogger(GestionGastoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
         //JIJI
        }
    }

    @FXML
    private void visualizar(MouseEvent event) {
        try {
            // cambia a la opción de visualizar            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Visualizar.fxml"));
            Parent añadirGasto = loader.load();
            
            // seleccionamos el borderpane del contenedor principal
            BorderPane principal = (BorderPane) añadirButton.getParent().getParent().getParent();
            
            principal.setCenter(añadirGasto);
            
        } catch (IOException ex) {
            Logger.getLogger(ContenedorPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }
}
