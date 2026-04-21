package mx.unison;

import javafx.application.Application;
import javafx.stage.Stage;
import mx.unison.data.GenericDao;
import mx.unison.models.Almacen;
import mx.unison.models.Producto;
import mx.unison.models.Usuario;
import mx.unison.navigation.NavigationManager;
import mx.unison.utils.CryptoUtils;

import java.util.List;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        NavigationManager.setStage(stage);

        try {
            // 1. Inicializar Usuario ADMIN
            GenericDao<Usuario, String> userDao = new GenericDao<>(Usuario.class);
            if (userDao.queryForId("ADMIN") == null) {
                Usuario admin = new Usuario();
                admin.setNombre("ADMIN");
                admin.setPassword(CryptoUtils.md5("admin123"));
                admin.setRol("ADMIN");
                userDao.create(admin);
                System.out.println("✅ Usuario ADMIN configurado.");
            }

            // 2. Inicializar Almacenes Default
            GenericDao<Almacen, Integer> almacenDao = new GenericDao<>(Almacen.class);
            if (almacenDao.queryForAll().isEmpty()) {
                Almacen a1 = new Almacen();
                a1.setNombre("Bodega Central HMO");
                a1.setUbicacion("Blvd. Colosio Final");
                almacenDao.create(a1);

                Almacen a2 = new Almacen();
                a2.setNombre("Sucursal Centro");
                a2.setUbicacion("Calle Matamoros");
                almacenDao.create(a2);
                System.out.println("✅ Almacenes default creados.");
            }

            // 3. Inicializar Productos Default
            GenericDao<Producto, Integer> productoDao = new GenericDao<>(Producto.class);
            if (productoDao.queryForAll().isEmpty()) {
                List<Almacen> almacenes = almacenDao.queryForAll();
                Almacen principal = almacenes.get(0);

                Producto p1 = new Producto();
                p1.setNombre("Laptop Gamer Pro");
                p1.setDescripcion("RTX 4070, 32GB RAM");
                p1.setCantidad(15);
                p1.setPrecio(25500.00);
                p1.setAlmacen(principal);
                productoDao.create(p1);

                Producto p2 = new Producto();
                p2.setNombre("Monitor 4K 27\"");
                p2.setDescripcion("Panel IPS 144Hz");
                p2.setCantidad(8);
                p2.setPrecio(8500.50);
                p2.setAlmacen(principal);
                productoDao.create(p2);

                System.out.println("✅ Productos default añadidos al inventario.");
            }

        } catch (Exception e) {
            System.err.println("❌ Error al inicializar datos: " + e.getMessage());
        }

        NavigationManager.switchScene("login-view.fxml", "Login - Sistema de Inventarios Pro");
    }

    public static void main(String[] args) {
        launch(args);
    }
}