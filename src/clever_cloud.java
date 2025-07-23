import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class clever_cloud {
    private static final String URL = "jdbc:mysql://blbgize3nkw4xj3pclkb-mysql.services.clever-cloud.com:3306/blbgize3nkw4xj3pclkb?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "udcfwguuamodywko";
    private static final String CONTRASEÑA = "mzgrlepO56f4xthFUq5P";

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);

        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
            return null;
        }
    }
}