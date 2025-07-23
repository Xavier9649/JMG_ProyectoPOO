import java.sql.Connection;

public class TestConexion {
    public static void main(String[] args) {
        Connection conn = clever_cloud.conectar();
        if (conn != null) {
            System.out.println("¡Conexión exitosa a Clever Cloud! ");
        } else {
            System.out.println("No se pudo conectar.");
        }
    }
}