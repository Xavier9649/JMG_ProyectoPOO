import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class Administrador extends JFrame {
    private JPanel administradorpanel;
    private JTabbedPane menucontabs;
    private JPanel panelregistrarusuario;
    private JPanel panelinventario;
    private JPanel panelclientes;
    private JPanel panelmascotas;
    private JTextField txtusuario;
    private JPasswordField txtpassword;
    private JComboBox comboBoxusuario;
    private JButton registrarUsuarioButton;
    private JTextField txtidproducto;
    private JButton buscarProductoButton;
    private JTable tableproducto;
    private JTextField txtidcliente;
    private JButton buscarClienteButton;
    private JTable tablecliente;
    private JTextField txtnombremascota;
    private JButton buscarMascotaButton;
    private JTable tablemascota;
    private JPanel addproductopanel;
    private JTextField textnewproducto;
    private JTextField textnewcategoria;
    private JTextField txtprecionew;
    private JTextField txtcantidadnew;
    private JButton addProductoButton;

    private DefaultTableModel modeloProducto;
    private DefaultTableModel modeloCliente;
    private DefaultTableModel modeloMascota;

    public Administrador() {
        setTitle("Administrador");
        setContentPane(administradorpanel);
        setSize(800, 600);
        setLocationRelativeTo(null); // Centrado
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Configurar modelos de tabla
        modeloProducto = new DefaultTableModel(new String[]{"ID", "Nombre", "Categoría", "Precio", "Cantidad"}, 0);
        tableproducto.setModel(modeloProducto);

        modeloCliente = new DefaultTableModel(new String[]{"ID", "Nombre", "Apellido", "Teléfono", "Correo", "Dirección"}, 0);
        tablecliente.setModel(modeloCliente);

        modeloMascota = new DefaultTableModel(new String[]{"ID", "Nombre", "Especie", "Raza", "Edad", "ID Cliente"}, 0);
        tablemascota.setModel(modeloMascota);

        // Eventos
        registrarUsuarioButton.addActionListener(e -> registrarUsuario());
        buscarProductoButton.addActionListener(e -> buscarProducto());
        buscarClienteButton.addActionListener(e -> buscarCliente());
        buscarMascotaButton.addActionListener(e -> buscarMascota());
        addProductoButton.addActionListener(e -> registrarNuevoProducto());
    }

    private void registrarUsuario() {
        String nombre = txtusuario.getText();
        String pass = new String(txtpassword.getPassword());
        String rol = comboBoxusuario.getSelectedItem().toString();

        String sql = "INSERT INTO usuario (nombre, contraseña, rol) VALUES (?, ?, ?)";

        try (Connection conn = clever_cloud.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            stmt.setString(2, pass);
            stmt.setString(3, rol);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, " Usuario registrado.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar usuario: " + ex.getMessage());
        }
    }

    private void buscarProducto() {
        modeloProducto.setRowCount(0);
        String id = txtidproducto.getText();

        String sql = "SELECT * FROM producto WHERE id_producto = ?";

        try (Connection conn = clever_cloud.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(id));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                modeloProducto.addRow(new Object[]{
                        rs.getInt("id_producto"),
                        rs.getString("nombre"),
                        rs.getString("categoria"),
                        rs.getDouble("precio"),
                        rs.getInt("cantidad")
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, " Error al buscar producto: " + ex.getMessage());
        }
    }

    private void buscarCliente() {
        modeloCliente.setRowCount(0);
        String id = txtidcliente.getText();

        String sql = "SELECT * FROM cliente WHERE id_cliente = ?";

        try (Connection conn = clever_cloud.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(id));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                modeloCliente.addRow(new Object[]{
                        rs.getInt("id_cliente"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("telefono"),
                        rs.getString("correo"),
                        rs.getString("direccion")
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, " Error al buscar cliente: " + ex.getMessage());
        }
    }

    private void buscarMascota() {
        modeloMascota.setRowCount(0);
        String nombre = txtnombremascota.getText();

        String sql = "SELECT * FROM mascota WHERE nombre LIKE ?";

        try (Connection conn = clever_cloud.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nombre + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                modeloMascota.addRow(new Object[]{
                        rs.getInt("id_mascota"),
                        rs.getString("nombre"),
                        rs.getString("especie"),
                        rs.getString("raza"),
                        rs.getInt("edad"),
                        rs.getInt("id_cliente")
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, " Error al buscar mascota: " + ex.getMessage());
        }
    }
    private void registrarNuevoProducto() {
        String nombre = textnewproducto.getText();
        String categoria = textnewcategoria.getText();
        String precioStr = txtprecionew.getText();
        String cantidadStr = txtcantidadnew.getText();

        // Validar valores básicos
        if (nombre.isEmpty() || categoria.isEmpty() || precioStr.isEmpty() || cantidadStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos.");
            return;
        }

        try {
            double precio = Double.parseDouble(precioStr);
            int cantidad = Integer.parseInt(cantidadStr);

            String sql = "INSERT INTO producto (nombre, categoria, precio, cantidad) VALUES (?, ?, ?, ?)";

            try (Connection conn = clever_cloud.conectar();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, nombre);
                stmt.setString(2, categoria);
                stmt.setDouble(3, precio);
                stmt.setInt(4, cantidad);

                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Producto añadido correctamente.");

                // Limpiar campos
                textnewproducto.setText("");
                textnewcategoria.setText("");
                txtprecionew.setText("");
                txtcantidadnew.setText("");

            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Precio o cantidad inválidos.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar producto: " + ex.getMessage());
        }
    }

    // Main para ejecutarlo
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Administrador::new);
    }
}