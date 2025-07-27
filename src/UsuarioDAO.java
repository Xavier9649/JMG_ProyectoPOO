import java.sql.*;

public class UsuarioDAO {
    public boolean validarCredenciales(String nombre, String contraseña, String rol) {
        try (Connection conn = clever_cloud.conectar()) {
            String sql = "SELECT * FROM usuario WHERE nombre = ? AND contraseña = ? AND rol = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, contraseña);
            ps.setString(3, rol);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println("Error al validar credenciales: " + e.getMessage());
            return false;
        }
    }
}