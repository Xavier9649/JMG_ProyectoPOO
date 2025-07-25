import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        // Configura comboBox con roles disponibles
        comboBox1rol.removeAllItems();
        comboBox1rol.addItem("Administrador");
        comboBox1rol.addItem("Cajero");

        // Acciones de los botones
        INGRESARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarSesion(); // Llamada al método de login
            }
        });

        verificarConexiónButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (java.sql.Connection conn = clever_cloud.conectar()) {
                    if (conn != null) {
                        JOptionPane.showMessageDialog(null, "Conexión exitosa a la base de datos.");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo conectar a la base de datos.");
                    }
                } catch (Exception excp) {
                    JOptionPane.showMessageDialog(null, "Error de conexión: " + excp.getMessage());
                }
            }
        });
    }

    private void iniciarSesion() {
        String usuario = txtusuario.getText().trim();
        String contraseña = new String(txtpassword.getPassword());
        String rolSeleccionado = (String) comboBox1rol.getSelectedItem();

        // Crea instancia del DAO para validar credenciales
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        if (usuarioDAO.validarCredenciales(usuario, contraseña, rolSeleccionado)) {
            JOptionPane.showMessageDialog(this, "Bienvenido " + rolSeleccionado + ".");

            if (rolSeleccionado.equals("Administrador")) {
                new Administrador();
            } else if (rolSeleccionado.equals("Cajero")) {
                new Cajero();
            }

            dispose(); // Cierra el login

        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas o rol no coincide.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LOGIN::new);
    }
}

