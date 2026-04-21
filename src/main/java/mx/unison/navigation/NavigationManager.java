package mx.unison.navigation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class NavigationManager {
    private static Stage mainStage;

    public static void setStage(Stage stage) { mainStage = stage; }

    public static void switchScene(String fxmlPath, String title) {
        try {
            // Ajuste de ruta para carpeta "mx.unison" literal
            URL fxmlUrl = NavigationManager.class.getResource("/mx.unison/views/" + fxmlPath);
            if (fxmlUrl == null) {
                throw new RuntimeException("No se encontró el archivo FXML en: /mx.unison/views/" + fxmlPath);
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Corrección de la ruta del CSS (No debe llevar 'src/main/resources')
            URL cssUrl = NavigationManager.class.getResource("/mx.unison/views/styles.css");
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            }

            mainStage.setTitle(title);
            mainStage.setScene(scene);
            mainStage.centerOnScreen();
            mainStage.show();
        } catch (IOException e) {
            System.err.println("Error al cargar la escena: " + e.getMessage());
            e.printStackTrace();
        }
    }
}