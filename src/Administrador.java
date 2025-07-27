import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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
        Usuario.registrarDesdeAdministrador(txtusuario, txtpassword, comboBoxusuario);
    }

    private void buscarProducto() {
        modeloProducto.setRowCount(0);
        Producto producto = Producto.buscarPorID(Integer.parseInt(txtidproducto.getText()));
        if (producto != null) modeloProducto.addRow(producto.toTableRow());
    }

    private void buscarCliente() {
        modeloCliente.setRowCount(0);
        Cliente cliente = Cliente.buscarPorID(Integer.parseInt(txtidcliente.getText()));
        if (cliente != null) modeloCliente.addRow(cliente.toTableRow());
    }

    private void buscarMascota() {
        modeloMascota.setRowCount(0);
        for (Mascota m : Mascota.buscarPorNombre(txtnombremascota.getText())) {
            modeloMascota.addRow(m.toTableRow());
        }
    }

    private void registrarNuevoProducto() {
        Producto nuevo = new Producto(0, textnewproducto.getText(), textnewcategoria.getText(), Double.parseDouble(txtprecionew.getText()), Integer.parseInt(txtcantidadnew.getText()));
        if (nuevo.registrar()) JOptionPane.showMessageDialog(this, "Producto registrado");
    }

    private void borrarProducto() {
        int fila = tableproducto.getSelectedRow();
        if (fila < 0) return;
        int id = (int) tableproducto.getValueAt(fila, 0);
        Producto producto = Producto.buscarPorID(id);
        if (producto != null && producto.eliminar()) {
            modeloProducto.removeRow(fila);
            JOptionPane.showMessageDialog(this, "Producto eliminado");
        }
    }

    private void actualizarProducto() {
        int id = Integer.parseInt(idproductoparaactualizar.getText());
        Producto producto = Producto.buscarPorID(id);
        if (producto != null) {
            boolean actualizado = producto.actualizar(
                    nuevonombreproducto.getText(),
                    nuevacategoriaproducto.getText(),
                    Double.parseDouble(nuevoprecioproducto.getText()),
                    Integer.parseInt(nuevacantidadproducto.getText())
            );
            if (actualizado) JOptionPane.showMessageDialog(this, "Producto actualizado");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Administrador());
    }

}

