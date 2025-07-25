import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class LOGIN extends JFrame {
    private JTextField txtusuario;
    private JPasswordField txtpassword;
    private JComboBox<String> comboBox1rol;
    private JButton INGRESARButton;
    private JPanel panelLogin;
    private JButton verificarConexiónButton;

    //Constructor
    public LOGIN() {
        setTitle("Inicio de Sesión");
        setContentPane(panelLogin);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        // Opciones del comboBox
        comboBox1rol.removeAllItems(); // Borra duplicados anteriores
        comboBox1rol.addItem("Administrador");
        comboBox1rol.addItem("Cajero");

        //Boton para iniciar sesión
        INGRESARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarSesion();
            }
        });

        //Test de conexión a la base de datos
        verificarConexiónButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (Connection conn = clever_cloud.conectar()) { //Recordemos que conn es el objeto de conexión a la base de datos
                    // Intentamos conectar a la base de datos usando la clase clever_cloud
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

    // Método para iniciar sesión según el rol seleccionado
    // y las credenciales ingresadas.
    private void iniciarSesion() {
        String usuario = txtusuario.getText().trim(); // Declaración de variable usuario para almacenar el nombre de usuario
        String contraseña = new String(txtpassword.getPassword()); // Declaración de variable contraseña para almacenar la contraseña ingresada
        String rolSeleccionado = (String) comboBox1rol.getSelectedItem(); // Declaración de variable rolSeleccionado para almacenar el rol seleccionado

        // Validación
        if (rolSeleccionado.equals("Administrador")) { //Si el rol seleccionado es Administrador
            if (usuario.equalsIgnoreCase("Miguel") && contraseña.equals("Admin1234")) { // Y las credenciales son correctas
                JOptionPane.showMessageDialog(this, "Bienvenido Administrador."); //Entonces muestra mensaje de bienvenida
                new Administrador(); // Abre el formulario Administrador
                dispose(); // La función dispose() cierra la ventana de login
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales de administrador incorrectas.");
            }
        } else if (rolSeleccionado.equals("Cajero")) {
            if (usuario.equalsIgnoreCase("Jorge") && contraseña.equals("Caj1234")) {
                JOptionPane.showMessageDialog(this, "Bienvenido Cajero.");
                new Cajero(); // Abre el formulario Cajero
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Contraseña de cajero incorrecta.");
            }
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(LOGIN::new);
    }
}

