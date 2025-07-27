// Clase Cajero.java (interfaz gráfica mejorada y corregida)
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

public class Cajero extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;

    // Cliente
    private JTextField textidcliente, textnuevoidcliente, textnuevonombre, textnuevoapellido, textnuevotelefono, textnuevocorreo, textnuevadireccion;
    private JButton botonbuscarcliente, registrarButton;

    // Producto/Servicio
    private JTextField textidproductoservicio;
    private JButton buscarProductoServicioButton;
    private JTable productoservicioregistro;
    private DefaultTableModel modeloProductoServicio;

    // Mascota
    private JTextField textnombremascota, textnuevonombremascota, textnuevaespecie, textnuevaraza, textnuevaedad, textidclienteregistrado;
    private JButton buscarMascotaButton, registrarMascotaButton;

    // Factura
    private JTextField textfecha, textidclientefactura, textproductoserviciofactura;
    private JButton ingresarClienteButton, agregarSubtotalButton, generarFacturaButton, limpiarCamposButton;
    private JTable clienteingresadofactura, productservicioingresadofactura;
    private JSpinner cantidadfactura;

    public Cajero() {
        setContentPane(tabbedPane1);
        setTitle("Panel Cajero");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        inicializarTablas();
        inicializarListeners();
    }

    private void inicializarTablas() {
        modeloProductoServicio = new DefaultTableModel(new Object[]{"ID", "Nombre", "Categoría", "Precio", "Cantidad"}, 0);
        productoservicioregistro.setModel(modeloProductoServicio);

        clienteingresadofactura.setModel(new DefaultTableModel(new Object[]{"ID", "Nombre", "Apellido", "Correo", "Dirección"}, 0));
        productservicioingresadofactura.setModel(new DefaultTableModel(new Object[]{"Producto", "Precio Unitario", "Cantidad", "Subtotal"}, 0));
    }

    private void inicializarListeners() {
        registrarButton.addActionListener(e -> registrarCliente());
        botonbuscarcliente.addActionListener(e -> buscarCliente());
        buscarProductoServicioButton.addActionListener(e -> buscarProductoServicio());
        buscarMascotaButton.addActionListener(e -> buscarMascota());
        registrarMascotaButton.addActionListener(e -> registrarMascota());
        ingresarClienteButton.addActionListener(e -> buscarClienteFactura());
        agregarSubtotalButton.addActionListener(e -> agregarProductoAFactura());
        generarFacturaButton.addActionListener(e -> generarFactura());
        limpiarCamposButton.addActionListener(e -> limpiarCamposFactura());
    }

    private void registrarCliente() {
        try {
            int id = Integer.parseInt(textnuevoidcliente.getText());
            Cliente cliente = new Cliente(
                    id,
                    textnuevonombre.getText(),
                    textnuevoapellido.getText(),
                    textnuevotelefono.getText(),
                    textnuevocorreo.getText(),
                    textnuevadireccion.getText()
            );
            cliente.registrar();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID de cliente inválido.");
        }
    }

    private void buscarCliente() {
        try {
            int id = Integer.parseInt(textidcliente.getText());
            Cliente cliente = Cliente.buscarPorID(id);
            if (cliente != null) {
                JOptionPane.showMessageDialog(this, "Cliente encontrado: " + cliente.getNombre());
            } else {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
        }
    }

    private void buscarProductoServicio() {
        try {
            int id = Integer.parseInt(textidproductoservicio.getText());
            Producto producto = Producto.buscarPorID(id);
            modeloProductoServicio.setRowCount(0);
            if (producto != null) {
                modeloProductoServicio.addRow(new Object[]{producto.getId(), producto.getNombre(), producto.getCategoria(), producto.getPrecio(), producto.getCantidad()});
            } else {
                JOptionPane.showMessageDialog(this, "Producto no encontrado.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID de producto inválido.");
        }
    }

    private void buscarMascota() {
        List<Mascota> lista = Mascota.buscarPorNombre(textnombremascota.getText());
        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mascota no encontrada.");
        } else {
            StringBuilder resultado = new StringBuilder();
            for (Mascota m : lista) {
                resultado.append("Mascota: ").append(m.getNombre()).append(" ID Cliente: ").append(m.getIdCliente()).append("\n");
            }
            JOptionPane.showMessageDialog(this, resultado.toString());
        }
    }

    private void registrarMascota() {
        try {
            int edad = Integer.parseInt(textnuevaedad.getText());
            int id_cliente = Integer.parseInt(textidclienteregistrado.getText());
            Mascota mascota = new Mascota(
                    textnuevonombremascota.getText(),
                    textnuevaespecie.getText(),
                    textnuevaraza.getText(),
                    edad,
                    id_cliente
            );
            mascota.registrar();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Edad o ID del cliente inválido.");
        }
    }

    private void buscarClienteFactura() {
        try {
            int id = Integer.parseInt(textidclientefactura.getText());
            Cliente cliente = Cliente.buscarPorID(id);
            DefaultTableModel modelo = (DefaultTableModel) clienteingresadofactura.getModel();
            modelo.setRowCount(0);
            if (cliente != null) {
                modelo.addRow(new Object[]{cliente.getIdCliente(), cliente.getNombre(), cliente.getApellido(), cliente.getCorreo(), cliente.getDireccion()});
            } else {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado para factura.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
        }
    }

    private void agregarProductoAFactura() {
        String nombreProducto = textproductoserviciofactura.getText();
        int cantidad = (int) cantidadfactura.getValue();
        Producto producto = Producto.buscarPorNombre(nombreProducto);

        if (producto != null) {
            double subtotal = producto.getPrecio() * cantidad;
            DefaultTableModel modelo = (DefaultTableModel) productservicioingresadofactura.getModel();
            modelo.addRow(new Object[]{producto.getNombre(), producto.getPrecio(), cantidad, subtotal});
        } else {
            JOptionPane.showMessageDialog(this, "Producto no encontrado para factura.");
        }
    }

    private void generarFactura() {
        DefaultTableModel modeloCliente = (DefaultTableModel) clienteingresadofactura.getModel();
        DefaultTableModel modeloItems = (DefaultTableModel) productservicioingresadofactura.getModel();

        if (modeloCliente.getRowCount() == 0 || modeloItems.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Faltan datos para generar factura.");
            return;
        }

        try {
            int idCliente = (int) modeloCliente.getValueAt(0, 0);
            Cliente cliente = Cliente.buscarPorID(idCliente);
            String tipoVenta = "";
            double total = 0;

            for (int i = 0; i < modeloItems.getRowCount(); i++) {
                tipoVenta += modeloItems.getValueAt(i, 0) + ", ";
                total += (double) modeloItems.getValueAt(i, 3);
            }
            tipoVenta = tipoVenta.substring(0, tipoVenta.length() - 2);

            Factura factura = new Factura(java.sql.Date.valueOf(textfecha.getText()), tipoVenta, total, cliente.getIdCliente());
            factura.registrar();

            JOptionPane.showMessageDialog(this, "Factura registrada correctamente.");
            limpiarCamposFactura();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al generar factura: " + e.getMessage());
        }
    }

    private void limpiarCamposFactura() {
        textfecha.setText("");
        textidclientefactura.setText("");
        textproductoserviciofactura.setText("");
        cantidadfactura.setValue(1);
        ((DefaultTableModel) clienteingresadofactura.getModel()).setRowCount(0);
        ((DefaultTableModel) productservicioingresadofactura.getModel()).setRowCount(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Cajero::new);
    }
}



