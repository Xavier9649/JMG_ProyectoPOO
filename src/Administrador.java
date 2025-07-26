import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class Administrador extends JFrame {
    // Componentes existentes
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

    // --- NUEVOS COMPONENTES ---
    private JButton borrarProductoButton; // Botón para borrar en panel de inventario
    private JPanel actualizarproductopanel; // El nuevo panel de pestaña
    private JTextField textidproductoactualizar; // Campo para ID en panel de actualizar
    private JButton buscarProductoParaActualizarButton; // Botón para buscar el producto a actualizar
    private JTextField textnombreactualizado;
    private JTextField textcategoriactualizado;
    private JTextField textprecioactualizado;
    private JTextField textcantidadactualizado;
    private JButton actualizarProductoButton; // Botón para guardar los cambios

    private DefaultTableModel modeloProducto;
    private DefaultTableModel modeloCliente;
    private DefaultTableModel modeloMascota;

    public Administrador() {
        setTitle("Administrador");
        setContentPane(administradorpanel);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Configurar modelos de tabla
        modeloProducto = new DefaultTableModel(new String[]{"ID", "Nombre", "Categoría", "Precio", "Cantidad"}, 0);
        tableproducto.setModel(modeloProducto);

        modeloCliente = new DefaultTableModel(new String[]{"ID", "Nombre", "Apellido", "Teléfono", "Correo", "Dirección"}, 0);
        tablecliente.setModel(modeloCliente);

        modeloMascota = new DefaultTableModel(new String[]{"ID", "Nombre", "Especie", "Raza", "Edad", "ID Cliente"}, 0);
        tablemascota.setModel(modeloMascota);

        // --- LISTENERS DE EVENTOS ---
        registrarUsuarioButton.addActionListener(e -> registrarUsuario());
        buscarProductoButton.addActionListener(e -> buscarProducto());
        buscarClienteButton.addActionListener(e -> buscarCliente());
        buscarMascotaButton.addActionListener(e -> buscarMascota());
        addProductoButton.addActionListener(e -> registrarNuevoProducto());


        borrarProductoButton.addActionListener(e -> borrarProducto());
        actualizarProductoButton.addActionListener(e -> actualizarProducto());


        buscarProductoParaActualizarButton.addActionListener(e -> buscarProductoParaActualizar());
    }

    // metodos
    private void borrarProducto() {
        int selectedRow = tableproducto.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un producto de la tabla para borrar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idProducto = (int) tableproducto.getValueAt(selectedRow, 0);
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que quieres borrar el producto con ID " + idProducto + "?", "Confirmar borrado", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM producto WHERE id_producto = ?";

            try (Connection conn = clever_cloud.conectar();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, idProducto);
                int filasAfectadas = stmt.executeUpdate();

                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(this, "Producto borrado exitosamente.");
                    modeloProducto.removeRow(selectedRow); // Actualizar la tabla en la UI
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró el producto para borrar.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al borrar el producto: " + ex.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void buscarProductoParaActualizar() {
        String idStr = textidproductoactualizar.getText();
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa un ID de producto para buscar.", "Campo vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "SELECT * FROM producto WHERE id_producto = ?";

        try (Connection conn = clever_cloud.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int idProducto = Integer.parseInt(idStr);
            stmt.setInt(1, idProducto);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Llenar los campos con los datos encontrados
                textnombreactualizado.setText(rs.getString("nombre"));
                textcategoriactualizado.setText(rs.getString("categoria"));
                textprecioactualizado.setText(String.valueOf(rs.getDouble("precio")));
                textcantidadactualizado.setText(String.valueOf(rs.getInt("cantidad")));
                JOptionPane.showMessageDialog(this, "Producto encontrado. Ahora puedes editar los datos.");
            } else {
                // Limpiar los campos si no se encuentra
                textnombreactualizado.setText("");
                textcategoriactualizado.setText("");
                textprecioactualizado.setText("");
                textcantidadactualizado.setText("");
                JOptionPane.showMessageDialog(this, "No se encontró ningún producto con ese ID.", "No encontrado", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID del producto debe ser un número.", "Error de formato", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al buscar el producto: " + ex.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void actualizarProducto() {
        String idStr = textidproductoactualizar.getText();
        String nombre = textnombreactualizado.getText();
        String categoria = textcategoriactualizado.getText();
        String precioStr = textprecioactualizado.getText();
        String cantidadStr = textcantidadactualizado.getText();

        if (idStr.isEmpty() || nombre.isEmpty() || categoria.isEmpty() || precioStr.isEmpty() || cantidadStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Asegúrate de buscar un producto y de que todos los campos estén llenos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "UPDATE producto SET nombre = ?, categoria = ?, precio = ?, cantidad = ? WHERE id_producto = ?";

        try (Connection conn = clever_cloud.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            double precio = Double.parseDouble(precioStr);
            int cantidad = Integer.parseInt(cantidadStr);
            int idProducto = Integer.parseInt(idStr);

            stmt.setString(1, nombre);
            stmt.setString(2, categoria);
            stmt.setDouble(3, precio);
            stmt.setInt(4, cantidad);
            stmt.setInt(5, idProducto);

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(this, "Producto actualizado correctamente.");
                // Limpiar campos después de actualizar
                textidproductoactualizar.setText("");
                textnombreactualizado.setText("");
                textcategoriactualizado.setText("");
                textprecioactualizado.setText("");
                textcantidadactualizado.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar el producto. Verifica el ID.", "Error de actualización", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Precio y cantidad deben ser números válidos.", "Error de formato", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar el producto: " + ex.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
        }
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Administrador::new);
    }
}