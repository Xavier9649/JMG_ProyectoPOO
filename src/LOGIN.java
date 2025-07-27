import javax.swing.*;

import java.sql.Connection;



public class LOGIN extends JFrame {
    private JTextField txtusuario;
    private JPasswordField txtpassword;
    private JComboBox<String> comboBox1rol;
    private JButton INGRESARButton;
    private JPanel panelLogin;
    private JButton verificarConexiónButton;

    public LOGIN() {
        setTitle("Inicio de Sesión");
        setContentPane(panelLogin);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        comboBox1rol.removeAllItems();
        comboBox1rol.addItem("Administrador");
        comboBox1rol.addItem("Cajero");

        INGRESARButton.addActionListener(e -> iniciarSesion());
        verificarConexiónButton.addActionListener(e -> {
            try (java.sql.Connection conn = clever_cloud.conectar()) {
                if (conn != null) {
                    JOptionPane.showMessageDialog(null, "Conexión exitosa a la base de datos.");
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo conectar a la base de datos.");
                }
            } catch (Exception excp) {
                JOptionPane.showMessageDialog(null, "Error de conexión: " + excp.getMessage());
            }
        });
    }

    private void iniciarSesion() {
        String usuario = txtusuario.getText();
        String password = new String(txtpassword.getPassword());
        String rol = comboBox1rol.getSelectedItem().toString();
        if (Usuario.validarCredenciales(usuario, password, rol)) {
            JOptionPane.showMessageDialog(this, "Bienvenido " + rol);
            if (rol.equals("Administrador")) new Administrador();
            else new Cajero();
            dispose();//dispose cierra la ventana de login
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LOGIN());
    }

}
