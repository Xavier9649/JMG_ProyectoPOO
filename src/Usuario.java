import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class Usuario {
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
