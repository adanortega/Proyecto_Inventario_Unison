package mx.unison.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import mx.unison.data.GenericDao;
import mx.unison.models.Usuario;
import mx.unison.navigation.NavigationManager;
import mx.unison.utils.CryptoUtils;
import java.sql.SQLException;

public class LoginController {
    @FXML private TextField txtUser;
    @FXML private PasswordField txtPass;
    @FXML private Label lblError;

    @FXML
    private void handleLogin() {
        try {
            GenericDao<Usuario, String> userDao = new GenericDao<>(Usuario.class);
            Usuario user = userDao.queryForId(txtUser.getText());

            if (user != null && user.getPassword().equals(CryptoUtils.md5(txtPass.getText()))) {
                NavigationManager.switchScene("main-view.fxml", "Sistema de Inventario Pro");
            } else {
                lblError.setText("Usuario o contraseña incorrectos");
            }
        } catch (SQLException e) {
            lblError.setText("Error de conexión a la base de datos");
        }
    }
}