import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
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

    private DefaultTableModel modeloClienteFactura;
    private DefaultTableModel modeloDetalleFactura;


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

        modeloClienteFactura = new DefaultTableModel(new Object[]{"ID", "Nombre", "Apellido", "Correo", "Dirección"}, 0);
        clienteingresadofactura.setModel(modeloClienteFactura);

        modeloDetalleFactura = new DefaultTableModel(new Object[]{"Producto", "Precio Unitario", "Cantidad", "Subtotal"}, 0);
        productservicioingresadofactura.setModel(modeloDetalleFactura);
    }

    private void inicializarListeners() {
        registrarButton.addActionListener(e -> registrarCliente());
        botonbuscarcliente.addActionListener(e -> buscarCliente());
        buscarMascotaButton.addActionListener(e -> buscarMascota());
        registrarMascotaButton.addActionListener(e -> registrarMascota());
        ingresarClienteButton.addActionListener(e -> buscarClienteFactura());
        agregarSubtotalButton.addActionListener(e -> agregarProductoAFactura());
        generarFacturaButton.addActionListener(e -> generarFactura());
        limpiarCamposButton.addActionListener(e -> limpiarCamposFactura());
        buscarProductoServicioButton.addActionListener(e -> buscarProductoServicio());
    }

    private void registrarCliente() {
        Cliente cliente = new Cliente(
                Integer.parseInt(textnuevoidcliente.getText()),
                textnuevonombre.getText(),
                textnuevoapellido.getText(),
                textnuevotelefono.getText(),
                textnuevocorreo.getText(),
                textnuevadireccion.getText()
        );
        if (cliente.registrar()) JOptionPane.showMessageDialog(this, "Cliente registrado");
    }

    private void buscarCliente() {
        Cliente cliente = Cliente.buscarPorID(Integer.parseInt(textidcliente.getText()));
        JOptionPane.showMessageDialog(this, cliente != null ? "Cliente encontrado: " + cliente.getNombre() : "Cliente no encontrado");
    }

    private void buscarProductoServicio() {
        try {
            int id = Integer.parseInt(textidproductoservicio.getText());
            Producto producto = Producto.buscarPorID(id);
            modeloProductoServicio.setRowCount(0); // limpia la tabla
            if (producto != null) {
                modeloProductoServicio.addRow(producto.toTableRow());
            } else {
                JOptionPane.showMessageDialog(this, "Producto no encontrado.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un ID válido.");
        }
    }

    private void buscarMascota() {
        List<Mascota> lista = Mascota.buscarPorNombre(textnombremascota.getText());
        StringBuilder resultado = new StringBuilder();
        for (Mascota m : lista) {
            resultado.append("Mascota: ").append(m.getNombre()).append(" - Cliente: ").append(m.getIdCliente()).append(" ");
        }
        JOptionPane.showMessageDialog(this, resultado.length() > 0 ? resultado.toString() : "No se encontraron mascotas");
    }

    private void registrarMascota() {
        Mascota mascota = new Mascota(
                textnuevonombremascota.getText(),
                textnuevaespecie.getText(),
                textnuevaraza.getText(),
                Integer.parseInt(textnuevaedad.getText()),
                Integer.parseInt(textidclienteregistrado.getText())
        );
        if (mascota.registrar()) JOptionPane.showMessageDialog(this, "Mascota registrada");
    }

    private void buscarClienteFactura() {
        Cliente cliente = Cliente.buscarPorID(Integer.parseInt(textidclientefactura.getText()));
        modeloClienteFactura.setRowCount(0);
        if (cliente != null) modeloClienteFactura.addRow(cliente.toTableRow());
    }

    private void agregarProductoAFactura() {
        String nombre = textproductoserviciofactura.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa el nombre del producto o servicio.");
            return;
        }

        int cantidad = (int) cantidadfactura.getValue();
        Producto p = Producto.buscarPorNombre(nombre);
        if (p != null) {
            double subtotal = p.getPrecio() * cantidad;
            modeloDetalleFactura.addRow(new Object[]{p.getNombre(), p.getPrecio(), cantidad, subtotal});
        } else {
            JOptionPane.showMessageDialog(this, "Producto no encontrado: " + nombre);
        }
    }

    private void generarFactura() {
        if (modeloClienteFactura.getRowCount() == 0 || modeloDetalleFactura.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Debe ingresar cliente y al menos un producto");
            return;
        }

        int idCliente = (int) modeloClienteFactura.getValueAt(0, 0);
        double total = 0;
        List<DetalleServicio> detalles = new ArrayList<>();
        StringBuilder resumen = new StringBuilder();

        resumen.append("Resumen de la factura:\n");
        resumen.append("Cliente ID: ").append(idCliente).append("\n");
        resumen.append("Cliente: ").append(modeloClienteFactura.getValueAt(0, 1)).append(" ")
                .append(modeloClienteFactura.getValueAt(0, 2)).append("\n");
        resumen.append("Fecha: ").append(textfecha.getText()).append("\n\n");
        resumen.append("Productos:\n");

        for (int i = 0; i < modeloDetalleFactura.getRowCount(); i++) {
            String nombreProd = (String) modeloDetalleFactura.getValueAt(i, 0);
            int cantidad = (int) modeloDetalleFactura.getValueAt(i, 2);
            double precio = (double) modeloDetalleFactura.getValueAt(i, 1);
            double subtotal = cantidad * precio;
            Producto p = Producto.buscarPorNombre(nombreProd);
            if (p != null) {
                detalles.add(new DetalleServicio(null, p, cantidad, precio));
                total += subtotal;
                resumen.append("- ").append(nombreProd)
                        .append(" x").append(cantidad)
                        .append(" @ ").append(precio)
                        .append(" = $").append(String.format("%.2f", subtotal)).append("\n");
            }
        }

        resumen.append("\nTotal: $").append(String.format("%.2f", total));

        Factura factura = new Factura(java.sql.Date.valueOf(textfecha.getText()), "Venta", total, idCliente);
        if (factura.registrarConDetalles(detalles)) {
            JOptionPane.showMessageDialog(this, "Factura registrada correctamente.\n\n" + resumen.toString());
            limpiarCamposFactura();
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar factura");
        }
    }



    private void limpiarCamposFactura() {
        textfecha.setText("");
        textidclientefactura.setText("");
        textproductoserviciofactura.setText("");
        cantidadfactura.setValue(1);
        modeloClienteFactura.setRowCount(0);
        modeloDetalleFactura.setRowCount(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Cajero());
    }

}



