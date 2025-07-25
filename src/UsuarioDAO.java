import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {

    // Método que valida si el usuario existe en la base de datos con las credenciales correctas
    public boolean validarCredenciales(String nombre, String contraseña, String rol) {
        boolean credencialesValidas = false;

        try (Connection conn = clever_cloud.conectar()) {
            String sql = "SELECT * FROM usuario WHERE nombre = ? AND contraseña = ? AND rol = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, contraseña);
            ps.setString(3, rol);

            ResultSet rs = ps.executeQuery();
            credencialesValidas = rs.next(); // Si hay resultado, las credenciales son correctas

        } catch (Exception e) {
            System.out.println("Error al validar credenciales: " + e.getMessage());
        }

        return credencialesValidas;
    }
}