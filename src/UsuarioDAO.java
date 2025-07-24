import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    // Validar credenciales y obtener rol
    public String loginUsuario(String nombre, String contraseña) {
        String sql = "SELECT rol FROM usuario WHERE nombre = ? AND contraseña = ?";
        String rol = null;

        try (Connection conn = clever_cloud.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            stmt.setString(2, contraseña);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                rol = rs.getString("rol");
                System.out.println("Inicio de sesión exitoso. Rol: " + rol);
            } else {
                System.out.println("Error: credenciales inválidas.");
            }

        } catch (SQLException e) {
            System.out.println("Error en login: " + e.getMessage());
        }

        return rol;
    }

    // Insertar nuevo usuario
    public void insertarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario (nombre, contraseña, rol) VALUES (?, ?, ?)";

        try (Connection conn = clever_cloud.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.nombre);
            stmt.setString(2, usuario.contraseña); // Considerar cifrado
            stmt.setString(3, usuario.rol);

            stmt.executeUpdate();
            System.out.println("Usuario registrado con éxito.");

        } catch (SQLException e) {
            System.out.println("Error al insertar usuario: " + e.getMessage());
        }
    }
}