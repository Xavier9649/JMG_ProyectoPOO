import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class Administrador extends JFrame {
    private JPanel administradorpanel;
    private JTabbedPane menucontabs;
    private JPanel panelregistrarusuario, panelinventario, panelclientes, panelmascotas;
    private JTextField txtusuario, txtidproducto, txtidcliente, txtnombremascota;
    private JPasswordField txtpassword;
    private JComboBox comboBoxusuario;
    private JButton registrarUsuarioButton, buscarProductoButton, buscarClienteButton, buscarMascotaButton;
    private JTable tableproducto, tablecliente, tablemascota;
    private JTextField textnewproducto, textnewcategoria, txtprecionew, txtcantidadnew;
    private JButton addProductoButton, borrarProductoButton;
    private JPanel actualizarproductopanel;
    private JTextField idproductoparaactualizar, nuevonombreproducto, nuevacategoriaproducto, nuevoprecioproducto, nuevacantidadproducto;
    private JButton actualizarProductoButton;
    private DefaultTableModel modeloProducto, modeloCliente, modeloMascota;
    private JPanel addproductopanel;

    public Administrador() {
        setTitle("Administrador");
        setContentPane(administradorpanel);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        modeloProducto = new DefaultTableModel(new String[]{"ID", "Nombre", "Categoría", "Precio", "Cantidad"}, 0);
        tableproducto.setModel(modeloProducto);
        modeloCliente = new DefaultTableModel(new String[]{"ID", "Nombre", "Apellido", "Teléfono", "Correo", "Dirección"}, 0);
        tablecliente.setModel(modeloCliente);
        modeloMascota = new DefaultTableModel(new String[]{"ID", "Nombre", "Especie", "Raza", "Edad", "ID Cliente"}, 0);
        tablemascota.setModel(modeloMascota);

        registrarUsuarioButton.addActionListener(e -> registrarUsuario());
        buscarProductoButton.addActionListener(e -> buscarProducto());
        buscarClienteButton.addActionListener(e -> buscarCliente());
        buscarMascotaButton.addActionListener(e -> buscarMascota());
        addProductoButton.addActionListener(e -> registrarNuevoProducto());
        borrarProductoButton.addActionListener(e -> borrarProducto());
        actualizarProductoButton.addActionListener(e -> actualizarProducto());
    }

    private void registrarUsuario() {
        String nombre = txtusuario.getText();
        String pass = new String(txtpassword.getPassword());
        String rol = comboBoxusuario.getSelectedItem().toString();
        try (Connection conn = clever_cloud.conectar()) {
            String sql = "INSERT INTO usuario (nombre, contraseña, rol) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombre);
            stmt.setString(2, pass);
            stmt.setString(3, rol);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Usuario registrado.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar usuario: " + ex.getMessage());
        }
    }

    private void buscarProducto() {
        modeloProducto.setRowCount(0);
        Producto producto = Producto.buscarPorID(Integer.parseInt(txtidproducto.getText()));
        if (producto != null) {
            modeloProducto.addRow(new Object[]{producto.getId(), producto.getNombre(), producto.getCategoria(), producto.getPrecio(), producto.getCantidad()});
        }
    }

    private void buscarCliente() {
        modeloCliente.setRowCount(0);
        Cliente cliente = Cliente.buscarPorID(Integer.parseInt(txtidcliente.getText()));
        if (cliente != null) {
            modeloCliente.addRow(new Object[]{cliente.getIdCliente(), cliente.getNombre(), cliente.getApellido(), cliente.getTelefono(), cliente.getCorreo(), cliente.getDireccion()});
        }
    }

    private void buscarMascota() {
        modeloMascota.setRowCount(0);
        for (Mascota m : Mascota.buscarPorNombre(txtnombremascota.getText())) {
            modeloMascota.addRow(new Object[]{"", m.getNombre(), "", "", "", m.getIdCliente()});
        }
    }

    private void registrarNuevoProducto() {
        try {
            double precio = Double.parseDouble(txtprecionew.getText());
            int cantidad = Integer.parseInt(txtcantidadnew.getText());
            try (Connection conn = clever_cloud.conectar()) {
                String sql = "INSERT INTO producto (nombre, categoria, precio, cantidad) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, textnewproducto.getText());
                stmt.setString(2, textnewcategoria.getText());
                stmt.setDouble(3, precio);
                stmt.setInt(4, cantidad);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Producto registrado.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void borrarProducto() {
        int fila = tableproducto.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un producto para eliminar.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas eliminar este producto?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        int id = (int) tableproducto.getValueAt(fila, 0);
        if (Producto.eliminar(id)) {
            modeloProducto.removeRow(fila);
            JOptionPane.showMessageDialog(this, "Producto eliminado.");
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo eliminar el producto.");
        }
    }

    private void buscarProductoParaActualizar() {
        Producto producto = Producto.buscarPorID(Integer.parseInt(idproductoparaactualizar.getText()));
        if (producto != null) {
            nuevonombreproducto.setText(producto.getNombre());
            nuevacategoriaproducto.setText(producto.getCategoria());
            nuevoprecioproducto.setText(String.valueOf(producto.getPrecio()));
            nuevacantidadproducto.setText(String.valueOf(producto.getCantidad()));
        }
    }

    private void actualizarProducto() {
        try {
            int id = Integer.parseInt(idproductoparaactualizar.getText());
            String nombre = nuevonombreproducto.getText();
            String categoria = nuevacategoriaproducto.getText();
            double precio = Double.parseDouble(nuevoprecioproducto.getText());
            int cantidad = Integer.parseInt(nuevacantidadproducto.getText());
            if (Producto.actualizar(id, nombre, categoria, precio, cantidad)) {
                JOptionPane.showMessageDialog(this, "Producto actualizado correctamente.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        new Administrador();
    }

}

