package mx.unison.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import mx.unison.data.GenericDao;
import mx.unison.models.Almacen;
import mx.unison.models.Producto;
import mx.unison.navigation.NavigationManager;
import java.sql.SQLException;

public class InventarioController {
    @FXML private TableView<Object> tablaGeneral;
    @FXML private TableColumn<Object, Integer> colId;
    @FXML private TableColumn<Object, String> col1;
    @FXML private TableColumn<Object, Object> col2;
    @FXML private TableColumn<Object, Double> col3;
    @FXML private TableColumn<Object, String> colAlmacen;

    private boolean viendoProductos = true;

    @FXML
    public void initialize() {
        configurarColumnasProductos();
        cargarDatos();
    }

    private void configurarColumnasProductos() {
        viendoProductos = true;
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        col1.setText("Producto");
        col1.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        col2.setText("Stock");
        col2.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        col3.setText("Precio");
        col3.setCellValueFactory(new PropertyValueFactory<>("precio"));
        col3.setVisible(true);
        colAlmacen.setText("Almacén");
        colAlmacen.setCellValueFactory(new PropertyValueFactory<>("almacen"));
        colAlmacen.setVisible(true);
    }

    private void configurarColumnasAlmacenes() {
        viendoProductos = false;
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        col1.setText("Almacén");
        col1.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        col2.setText("Ubicación");
        col2.setCellValueFactory(new PropertyValueFactory<>("ubicacion"));
        col3.setVisible(false);
        colAlmacen.setVisible(false);
    }

    @FXML private void handleVerProductos() { configurarColumnasProductos(); cargarDatos(); }
    @FXML private void handleVerAlmacenes() { configurarColumnasAlmacenes(); cargarDatos(); }

    private void cargarDatos() {
        try {
            if (viendoProductos) {
                tablaGeneral.setItems(FXCollections.observableArrayList(new GenericDao<>(Producto.class).queryForAll()));
            } else {
                tablaGeneral.setItems(FXCollections.observableArrayList(new GenericDao<>(Almacen.class).queryForAll()));
            }
        } catch (SQLException e) { mostrarAlerta("Error", "No se cargaron los datos."); }
    }

    @FXML
    private void handleNuevo() {
        if (viendoProductos) mostrarDialogoProducto(null); else mostrarDialogoAlmacen(null);
    }

    @FXML
    private void handleEditar() {
        Object sel = tablaGeneral.getSelectionModel().getSelectedItem();
        if (sel == null) { mostrarAlerta("Info", "Selecciona una fila."); return; }
        if (viendoProductos) mostrarDialogoProducto((Producto) sel); else mostrarDialogoAlmacen((Almacen) sel);
    }

    @FXML
    private void handleEliminar() {
        Object sel = tablaGeneral.getSelectionModel().getSelectedItem();
        if (sel == null) { mostrarAlerta("Info", "Selecciona una fila."); return; }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Eliminar este registro?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(tipo -> {
            if (tipo == ButtonType.YES) {
                try {
                    if (viendoProductos) new GenericDao<>(Producto.class).delete((Producto) sel);
                    else new GenericDao<>(Almacen.class).delete((Almacen) sel);
                    cargarDatos();
                } catch (SQLException e) { mostrarAlerta("Error", "No se pudo eliminar."); }
            }
        });
    }

    private void mostrarDialogoProducto(Producto p) {
        Dialog<Producto> dialog = new Dialog<>();
        dialog.setTitle(p == null ? "Nuevo Producto" : "Editar");
        ButtonType b = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(b, ButtonType.CANCEL);
        GridPane grid = new GridPane(); grid.setHgap(10); grid.setVgap(10);
        TextField n = new TextField(p != null ? p.getNombre() : "");
        TextField s = new TextField(p != null ? String.valueOf(p.getCantidad()) : "0");
        TextField pr = new TextField(p != null ? String.valueOf(p.getPrecio()) : "0.0");
        ComboBox<Almacen> combo = new ComboBox<>();
        try { combo.setItems(FXCollections.observableArrayList(new GenericDao<>(Almacen.class).queryForAll()));
            if(p != null) combo.setValue(p.getAlmacen()); else combo.getSelectionModel().selectFirst();
        } catch (SQLException e) {}
        grid.add(new Label("Nombre:"), 0, 0); grid.add(n, 1, 0);
        grid.add(new Label("Stock:"), 0, 1); grid.add(s, 1, 1);
        grid.add(new Label("Precio:"), 0, 2); grid.add(pr, 1, 2);
        grid.add(new Label("Almacén:"), 0, 3); grid.add(combo, 1, 3);
        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(btn -> {
            if (btn == b) {
                Producto res = (p == null) ? new Producto() : p;
                res.setNombre(n.getText()); res.setCantidad(Integer.parseInt(s.getText()));
                res.setPrecio(Double.parseDouble(pr.getText())); res.setAlmacen(combo.getValue());
                return res;
            } return null;
        });
        dialog.showAndWait().ifPresent(res -> {
            try { GenericDao<Producto, Integer> d = new GenericDao<>(Producto.class);
                if (p == null) d.create(res); else d.update(res); cargarDatos();
            } catch (SQLException e) {}
        });
    }

    private void mostrarDialogoAlmacen(Almacen a) {
        Dialog<Almacen> dialog = new Dialog<>();
        dialog.setTitle(a == null ? "Nuevo" : "Editar");
        ButtonType b = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(b, ButtonType.CANCEL);
        GridPane grid = new GridPane(); grid.setHgap(10); grid.setVgap(10);
        TextField n = new TextField(a != null ? a.getNombre() : "");
        TextField u = new TextField(a != null ? a.getUbicacion() : "");
        grid.add(new Label("Nombre:"), 0, 0); grid.add(n, 1, 0);
        grid.add(new Label("Ubicación:"), 0, 1); grid.add(u, 1, 1);
        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(btn -> {
            if (btn == b) {
                Almacen res = (a == null) ? new Almacen() : a;
                res.setNombre(n.getText()); res.setUbicacion(u.getText());
                return res;
            } return null;
        });
        dialog.showAndWait().ifPresent(res -> {
            try { GenericDao<Almacen, Integer> d = new GenericDao<>(Almacen.class);
                if (a == null) d.create(res); else d.update(res); cargarDatos();
            } catch (SQLException e) {}
        });
    }

    @FXML private void handleLogout() { NavigationManager.switchScene("login-view.fxml", "Login"); }
    private void mostrarAlerta(String t, String m) { Alert a = new Alert(Alert.AlertType.INFORMATION); a.setTitle(t); a.setContentText(m); a.showAndWait(); }
}