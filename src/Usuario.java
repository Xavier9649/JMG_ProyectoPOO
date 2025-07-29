import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class Usuario {
    //Con este metodo podemos validar las credenciales de un usuario
    // recibe el nombre, contraseña y rol del usuario y retorna true si las credenciales son válidas,
    // o false si no lo son
    public static boolean validarCredenciales(String nombre, String contraseña, String rol) {
        try (Connection conn = clever_cloud.conectar()) {
            String sql = "SELECT * FROM usuario WHERE nombre = ? AND contraseña = ? AND rol = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, contraseña);
            ps.setString(3, rol);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            return false;
        }
    }

    // Este método permite registrar un nuevo usuario en la base de datos
    // recibe el nombre, contraseña y rol del usuario y retorna true si se registró correctamente
    // o false si hubo un error
    public static boolean registrarNuevo(String nombre, String contraseña, String rol) {
        try (Connection conn = clever_cloud.conectar()) {
            String sql = "INSERT INTO usuario (nombre, contraseña, rol) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, contraseña);
            ps.setString(3, rol);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Este método permite buscar un usuario por su nombre
    // recibe el nombre del usuario y retorna un objeto Usuario con los datos encontrados
    public static void registrarDesdeFormulario(JTextField txtusuario, JPasswordField txtpassword, JComboBox<String> comboBoxusuario) {
        String nombre = txtusuario.getText();
        String clave = new String(txtpassword.getPassword());
        String rol = comboBoxusuario.getSelectedItem().toString();

        if (Usuario.registrarNuevo(nombre, clave, rol)) {
            JOptionPane.showMessageDialog(null, "Usuario registrado correctamente");
        } else {
            JOptionPane.showMessageDialog(null, "Error al registrar usuario");
        }
    }

    // Método para usar desde la clase Administrador
    public static void registrarDesdeAdministrador(JTextField txtusuario, JPasswordField txtpassword, JComboBox<String> comboBoxusuario) {
        registrarDesdeFormulario(txtusuario, txtpassword, comboBoxusuario);
    }
}
