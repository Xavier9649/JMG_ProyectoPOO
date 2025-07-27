import javax.swing.*; // importamos todas las clases del paquete swing
import javax.swing.table.DefaultTableModel; // importamos una clase para manejar los datos de las Jtable
// aqui se describen los componentes que forman parte del formulario.
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
// aqui tenemos nuestro metodo constructor
    public Administrador() {
        //aqui empieza la configuración de la ventana
        setTitle("Administrador");
        setContentPane(administradorpanel);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        // inicializamos los modelos de las tablas
        modeloProducto = new DefaultTableModel(new String[]{"ID", "Nombre", "Categoría", "Precio", "Cantidad"}, 0);
        tableproducto.setModel(modeloProducto);
        modeloCliente = new DefaultTableModel(new String[]{"ID", "Nombre", "Apellido", "Teléfono", "Correo", "Dirección"}, 0);
        tablecliente.setModel(modeloCliente);
        modeloMascota = new DefaultTableModel(new String[]{"ID", "Nombre", "Especie", "Raza", "Edad", "ID Cliente"}, 0);
        tablemascota.setModel(modeloMascota);
        // asignación de los eventos a los botones respectivos.
        registrarUsuarioButton.addActionListener(e -> registrarUsuario());
        buscarProductoButton.addActionListener(e -> buscarProducto());
        buscarClienteButton.addActionListener(e -> buscarCliente());
        buscarMascotaButton.addActionListener(e -> buscarMascota());
        addProductoButton.addActionListener(e -> registrarNuevoProducto());
        borrarProductoButton.addActionListener(e -> borrarProducto());
        actualizarProductoButton.addActionListener(e -> actualizarProducto());
    }
// metodos privados
    // llamamos al metodo registrar Usuario
    private void registrarUsuario() {
        Usuario.registrarDesdeAdministrador(txtusuario, txtpassword, comboBoxusuario);
    }
    // llamos a otros metodos
    private void buscarProducto() {
        modeloProducto.setRowCount(0); // limpiamos la tabla antes de mostrar resultados
        Producto producto = Producto.buscarPorID(Integer.parseInt(txtidproducto.getText())); //obtenemos el id
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
        //creamos un nuevo objeto prodcuto con los datos de los campos de texto y usamos el metodo registrar
        Producto nuevo = new Producto(0, textnewproducto.getText(), textnewcategoria.getText(), Double.parseDouble(txtprecionew.getText()), Integer.parseInt(txtcantidadnew.getText()));
        if (nuevo.registrar()) JOptionPane.showMessageDialog(this, "Producto registrado");
    }

    private void borrarProducto() {
        // obtenemos la fila seleccionad apor el usuario en la tabla y llamamos al metodo eliminar
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
    // metodo main

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Administrador()); // permite iniciar de forma correcta una aplicación de swing
    }

}

