/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import model.Acount;
import model.AcountDAOException;
import model.Category;
import model.Charge;

/**
 * FXML Controller class
 *
 * @author jomar
 */
public class VisualizarController implements Initializable {

    @FXML
    private BarChart<String, Number> grafica;
    @FXML
    private TableView<Charge> tablaFiltrada;
    @FXML
    private ComboBox<String> categoriaChooser;
    @FXML
    private ComboBox<String> periodoChooser;
    @FXML
    private DatePicker fechaChooser;
    @FXML
    private TableColumn<Charge, String> columnaNombre;
    @FXML
    private TableColumn<Charge, String> columnaDescripcion;
    @FXML
    private TableColumn<Charge, String> columnaCategoria;
    @FXML
    private TableColumn<Charge, String> columnaFecha;
    @FXML
    private TableColumn<Charge, String> columnaCoste;
    
    private ObservableList<Charge> datos;
    
    private List<Charge> misDatos;
    
    private XYChart.Series<String, Number> datosGrafica;
    
    private ObservableList<Charge> datosfiltrados;
    @FXML
    private Button atrasBoton;
    @FXML
    private TableColumn<Charge, String> columnaUnidades;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO 
        try {
            // TODO
            // tabla de gastos
            columnaNombre.setReorderable(false);
            columnaDescripcion.setReorderable(false);
            columnaCategoria.setReorderable(false);
            columnaCoste.setReorderable(false);
            columnaFecha.setReorderable(false);
            columnaUnidades.setReorderable(false);
            columnaUnidades.prefWidthProperty().bind(tablaFiltrada.widthProperty().multiply(0.1));
            columnaNombre.prefWidthProperty().bind(tablaFiltrada.widthProperty().multiply(0.2));
            columnaDescripcion.prefWidthProperty().bind(tablaFiltrada.widthProperty().multiply(0.2));
            columnaCategoria.prefWidthProperty().bind(tablaFiltrada.widthProperty().multiply(0.2));
            columnaCoste.prefWidthProperty().bind(tablaFiltrada.widthProperty().multiply(0.1));
            columnaFecha.prefWidthProperty().bind(tablaFiltrada.widthProperty().multiply(0.2));

            misDatos = Acount.getInstance().getUserCharges();
            datos = FXCollections.observableList(misDatos);
            
            fechaChooser.setValue(LocalDate.now());

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
            columnaUnidades.setCellValueFactory((gastoFila) -> {
                return new SimpleStringProperty(Integer.toString(gastoFila.getValue().getUnits()));
            });
            
            
            // creamos la lista de categorias a mostrar en el combobox
            ObservableList<Category> listaCategorias = FXCollections.observableList(Acount.getInstance().getUserCategories());
            ObservableList<String> listaCategoriasString = FXCollections.observableArrayList();
            listaCategoriasString.add("Todasㅤㅤㅤ");
            
            if (listaCategorias != null) {
                for (int i = 0; i < listaCategorias.size(); i++) {
                    listaCategoriasString.add(listaCategorias.get(i).getName());
                }
                categoriaChooser.setItems(listaCategoriasString);
                categoriaChooser.setValue("Todasㅤㅤㅤ");
            }
            
            ObservableList<String> listaPeriodo = FXCollections.observableArrayList();
            listaPeriodo.addAll("Semana", "Mes", "Año");
            periodoChooser.setItems(listaPeriodo);
            periodoChooser.setValue("Semana");
            
            datosGrafica = new XYChart.Series<String, Number>();
            
            if (!misDatos.isEmpty()) {
                filtrar();
                grafica.getData().clear();
                datosGrafica.getData().clear();
            }
            if (!datosfiltrados.isEmpty()) {
                graficaSet();
            }
            
        } catch (AcountDAOException ex) {
            Logger.getLogger(AñadirGastoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AñadirGastoController.class.getName()).log(Level.SEVERE, null, ex);
      }
    }

    public void filtrar() {
        String periodo = periodoChooser.getValue();
        List<Charge> filteredDates;
        switch (periodo) {
            case "Semana":
                filteredDates = filterDatesInSameWeek(misDatos, fechaChooser.getValue());
                break;
            case "Mes":
                filteredDates = filterDatesInSameMonth(misDatos, fechaChooser.getValue());
                break;
            case "Año":
                filteredDates = filterDatesInSameYear(misDatos, fechaChooser.getValue());
                break;
            default:
                throw new AssertionError();
        }
        if (!categoriaChooser.getValue().equals("Todasㅤㅤㅤ")) {
            List<Charge> filteredDateCategory;
            filteredDateCategory = filterCategory(filteredDates, categoriaChooser.getValue());
            datosfiltrados = FXCollections.observableArrayList(filteredDateCategory);
        } else {
            datosfiltrados = FXCollections.observableArrayList(filteredDates);
        }
        tablaFiltrada.setItems(datosfiltrados);
    } 
    
    public static List<Charge> filterDatesInSameWeek(List<Charge> cargos, LocalDate referenceDate) {
        // Obtener el número de semana del año de la fecha de referencia
        List<Charge> filteredDates = new ArrayList<Charge>();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int referenceWeek = referenceDate.get(weekFields.weekOfWeekBasedYear());
        int referenceYear = referenceDate.get(weekFields.weekBasedYear());
        
        for (int i = 0; i < cargos.size(); i++) {
            Charge gastoAux = cargos.get(i);
            int semanaAFiltrar = gastoAux.getDate().get(weekFields.weekOfWeekBasedYear());
            int añoAFiltrar = gastoAux.getDate().get(weekFields.weekBasedYear());
            if (semanaAFiltrar == referenceWeek && añoAFiltrar == referenceYear) {
                filteredDates.add(gastoAux);
            }
            }
        return filteredDates;
    }
    
    public static List<Charge> filterDatesInSameMonth(List<Charge> cargos, LocalDate referenceDate) {
        // Obtener el número de semana del año de la fecha de referencia
        List<Charge> filteredDates = new ArrayList<Charge>();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int referenceMonth = referenceDate.getMonthValue();
        int referenceYear = referenceDate.get(weekFields.weekBasedYear());

        for (int i = 0; i < cargos.size() ;i++) {  
            int semanaAFiltrar = cargos.get(i).getDate().getMonthValue();
            int añoAFiltrar = cargos.get(i).getDate().get(weekFields.weekBasedYear());
            if (semanaAFiltrar == referenceMonth && añoAFiltrar == referenceYear) {
                filteredDates.add(cargos.get(i));
            }
        }
        return filteredDates;
    }
    
        
   public static List<Charge> filterDatesInSameYear(List<Charge> cargos, LocalDate referenceDate) {
        // Obtener el número de semana del año de la fecha de referencia
        List<Charge> filteredDates = new ArrayList<Charge>();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int referenceYear = referenceDate.get(weekFields.weekBasedYear());

        for (int i = 0; i < cargos.size() ;i++) {
            int añoAFiltrar = cargos.get(i).getDate().get(weekFields.weekBasedYear());
            if (añoAFiltrar == referenceYear) {
                filteredDates.add(cargos.get(i));
            }
        }
       return filteredDates;
    }
   
   public static List<Charge> filterCategory(List<Charge> cargos, String referenceCategory) {
        // Obtener el número de semana del año de la fecha de referencia
        List<Charge> filteredCat = new ArrayList<Charge>();

        for (int i = 0; i < cargos.size() ;i++) {
            String catAFiltrar = cargos.get(i).getCategory().getName();
            if (catAFiltrar.equals(referenceCategory)) {
                filteredCat.add(cargos.get(i));
            }
        }
       return filteredCat;
    }

    @FXML
    private void categoriaElegida(ActionEvent event) {
        if (!misDatos.isEmpty()) {
                filtrar();
                grafica.getData().clear();
                datosGrafica.getData().clear();
            }
        if (!datosfiltrados.isEmpty()) {
            graficaSet();
        }
    }

    @FXML
    private void fechaElegida(ActionEvent event) {
        if (!misDatos.isEmpty()) {
                filtrar();
                grafica.getData().clear();
                datosGrafica.getData().clear();
            }
        if (!datosfiltrados.isEmpty()) {
            graficaSet();
        }
    }
    
    @FXML
    private void periodoElegido(ActionEvent event) {
        if (!misDatos.isEmpty()) {
                filtrar();
                grafica.getData().clear();
                datosGrafica.getData().clear();
            }
        if (!datosfiltrados.isEmpty()) {
            graficaSet();
        }
    }
    
    
    private void graficaSet() {
        datosGrafica.setName(categoriaChooser.getValue());

        double sumaGasto;
        LocalDate fecha = fechaChooser.getValue();
        ObservableList<Charge> cargos = tablaFiltrada.getItems();

        switch (periodoChooser.getValue()) {
            case "Semana":
                for (int i = 1; i <= 7; i++) {
                    sumaGasto = 0;
                    for (int j = 0; j < cargos.size(); j++) {
                        if (cargos.get(j).getDate().getDayOfWeek().getValue() == i) {
                            sumaGasto += cargos.get(j).getCost();
                        }
                    }
                    datosGrafica.getData().add(new XYChart.Data<String, Number>(getDayOfWeekAbbreviation(i), sumaGasto));
                }
                break;
                
            case "Mes":
                int diasMes = fecha.lengthOfMonth();
                for (int i = 1;i <= diasMes;i++) {
                    sumaGasto = 0;
                    for (int j = 0; j < cargos.size(); j++) {
                        if (cargos.get(j).getDate().getDayOfMonth() == i) {
                            sumaGasto += cargos.get(j).getCost();
                        }
                    }
                    datosGrafica.getData().add(new XYChart.Data<String, Number>(Integer.toString(i), sumaGasto));
                }
                break;
                
            case "Año":
                for (int i = 1;i <= 12;i++) {
                    sumaGasto = 0;
                    for (int j = 0; j < cargos.size(); j++) {
                        if (cargos.get(j).getDate().getMonthValue() == i) {
                            sumaGasto += cargos.get(j).getCost();
                        }
                    }
                    datosGrafica.getData().add(new XYChart.Data<String, Number>(getMonthAbbreviation(i), sumaGasto));
                }
                break;
                
            default:
                throw new AssertionError();
        }
        grafica.getData().addAll(datosGrafica);

    }

    public static String getMonthAbbreviation(int month) {
        Month monthEnum = Month.of(month);
        return monthEnum.getDisplayName(TextStyle.SHORT, new Locale("es"));
    }
  
    public static String getDayOfWeekAbbreviation(int day) {
        DayOfWeek dayOfWeek = DayOfWeek.of(day);
        return dayOfWeek.getDisplayName(TextStyle.SHORT, new Locale("es"));
    }

    @FXML
    private void atras(ActionEvent event) {
        try {
            // cambia a la opción de añadir gasto            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/GestionGasto.fxml"));
            Parent gestionGasto = loader.load();

            // seleccionamos el borderpane del contenedor principal
            BorderPane principal = (BorderPane) atrasBoton.getParent().getParent().getParent().getParent();

            principal.setCenter(gestionGasto);
        } catch (IOException ex) {
            Logger.getLogger(AñadirCategoriaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}