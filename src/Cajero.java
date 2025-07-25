import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Cajero extends JFrame {
    // Pestaña Cliente
    private JTabbedPane tabbedPane1;
    private JTextField textidcliente;
    private JButton botonbuscarcliente;
    private JTextField textnuevoidcliente;
    private JTextField textnuevonombre;
    private JTextField textnuevoapellido;
    private JTextField textnuevotelefono;
    private JTextField textnuevocorreo;
    private JTextField textnuevadireccion;
    private JButton registrarButton;

    // Pestaña Producto/Servicio
    private JTextField textidproductoservicio;
    private JButton buscarProductoServicioButton;
    private JTable productoservicioregistro;

    // Pestaña Mascota
    private JTextField textnombremascota;
    private JButton buscarMascotaButton;
    private JTextField textnuevoidmascota;
    private JTextField textnuevonombremascota;
    private JTextField textnuevaespecie;
    private JTextField textnuevaraza;
    private JTextField textnuevaedad;
    private JTextField textidclienteregistrado;
    private JButton registrarMascotaButton;

    // Pestaña Factura
    private JTextField textfecha;
    private JTextField textidclientefactura;
    private JButton ingresarClienteButton;
    private JTable clienteingresadofactura;
    private JTextField textproductoserviciofactura;
    private JSpinner cantidadfactura;
    private JButton agregarSubtotalButton;
    private JTable productservicioingresadofactura;
    private JButton generarFacturaButton;
    private JButton limpiarCamposButton;
    private DefaultTableModel modeloProductoServicio;
    private DefaultTableModel modeloMascotas;



    public Cajero() {
        setTitle("Cajero");
        setSize(800, 600);
        setContentPane(tabbedPane1);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        inicializarTablas(); //Creamos un método para inicializar las tablas
        inicializarListeners(); //Creamos un método para inicializar los listeners de los botones
    }

    //Listeners
    //Creamos un método para inicializar los listeners de los todos los botones
    private void inicializarListeners() {
        // Cliente
        registrarButton.addActionListener(e -> registrarCliente());
        botonbuscarcliente.addActionListener(e -> buscarClientePorID());

        // Producto/Servicio
        buscarProductoServicioButton.addActionListener(e -> buscarProductoServicio());

        // Mascota
        buscarMascotaButton.addActionListener(e -> buscarMascotaPorNombre());
        registrarMascotaButton.addActionListener(e -> registrarMascota());

        // Factura
        ingresarClienteButton.addActionListener(e -> buscarClienteFactura());
        agregarSubtotalButton.addActionListener(e -> agregarProductoAFactura());
        generarFacturaButton.addActionListener(e -> generarFactura());
        limpiarCamposButton.addActionListener(e -> limpiarCamposFactura());
    }

    // Métodos para inicializar las tablas
    private void inicializarTablas() {
        // Pestaña Producto/Servicio
        modeloProductoServicio = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Categoría", "Precio", "Cantidad"}, 0
        );
        productoservicioregistro.setModel(modeloProductoServicio);

        // Pestaña Factura - Cliente ingresado
        clienteingresadofactura.setModel(new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Apellido", "Correo", "Dirección"}, 0
        ));

        // Pestaña Factura - Productos/Servicios agregados
        productservicioingresadofactura.setModel(new DefaultTableModel(
                new Object[]{"Producto", "Precio Unitario", "Cantidad", "Subtotal"}, 0
        ));
    }

    //-------------------------------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------------------
    // Pestaña Cliente
    // Método para registrar un nuevo cliente
    private void registrarCliente() {
        String idCliente = textnuevoidcliente.getText().trim();
        String nombre = textnuevonombre.getText().trim();
        String apellido = textnuevoapellido.getText().trim();
        String telefono = textnuevotelefono.getText().trim();
        String correo = textnuevocorreo.getText().trim();
        String direccion = textnuevadireccion.getText().trim();

        if (idCliente.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() || correo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos obligatorios.");
            return;
        }

        try (Connection conn = clever_cloud.conectar()) {
            String sql = "INSERT INTO cliente (id_cliente, nombre, apellido, telefono, correo, direccion) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(idCliente));
            stmt.setString(2, nombre);
            stmt.setString(3, apellido);
            stmt.setString(4, telefono);
            stmt.setString(5, correo);
            stmt.setString(6, direccion);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Cliente registrado con éxito.");

            // Limpiamos los campos de entrada
            textnuevoidcliente.setText("");
            textnuevonombre.setText("");
            textnuevoapellido.setText("");
            textnuevotelefono.setText("");
            textnuevocorreo.setText("");
            textnuevadireccion.setText("");

        } catch (SQLException ex) { //Mostramos un mensaje de error si ocurre una excepción
            JOptionPane.showMessageDialog(this, "Error al registrar cliente: " + ex.getMessage());
        // Mensaje de error si el ID no es un número válido
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
        }
    }

    // Método para buscar cliente por ID
    private void buscarClientePorID() {
        String id = textidcliente.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa el ID del cliente.");
            return;
        }

        try (Connection conn = clever_cloud.conectar()) {
            String sql = "SELECT * FROM cliente WHERE id_cliente = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(id));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String info = "Nombre: " + rs.getString("nombre") +
                        "\nApellido: " + rs.getString("apellido") +
                        "\nTeléfono: " + rs.getString("telefono") +
                        "\nCorreo: " + rs.getString("correo") +
                        "\nDirección: " + rs.getString("direccion");
                JOptionPane.showMessageDialog(this, info);
            } else {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado.");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al buscar cliente: " + ex.getMessage());
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
        }
    }
    //-------------------------------------------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------------------------------
    // Pestaña Producto/Servicio
    //Buscar producto o servicio por id
    private void buscarProductoServicio() {
        modeloProductoServicio.setRowCount(0); // Limpiar tabla
        String id = textidproductoservicio.getText().trim();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa el ID del producto o servicio.");
            return;
        }

        try (Connection conn = clever_cloud.conectar()) {
            String sql = "SELECT * FROM producto WHERE id_producto = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(id));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                modeloProductoServicio.addRow(new Object[]{
                        rs.getInt("id_producto"),
                        rs.getString("nombre"),
                        rs.getString("categoria"),
                        rs.getDouble("precio"),
                        rs.getInt("cantidad")
                });
            } else {
                JOptionPane.showMessageDialog(this, "Producto o servicio no encontrado.");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al buscar producto: " + ex.getMessage());
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
        }
    }

    //-----------------------------------------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------------------------------
    // Pestaña Mascota
    //Buscar mascota por nombre
    private void buscarMascotaPorNombre() {
        String nombre = textnombremascota.getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingresa un nombre de mascota.");
            return;
        }

        try (Connection conn = clever_cloud.conectar()) {
            String sql = "SELECT * FROM mascota WHERE nombre LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + nombre + "%");
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Si hay coincidencia, mostrar los datos
                String info = "ID Mascota: " + rs.getInt("id_mascota") +
                        "\nNombre: " + rs.getString("nombre") +
                        "\nEspecie: " + rs.getString("especie") +
                        "\nRaza: " + rs.getString("raza") +
                        "\nEdad: " + rs.getInt("edad") +
                        "\nID Cliente: " + rs.getInt("id_cliente");

                JOptionPane.showMessageDialog(this, info, "Mascota encontrada", JOptionPane.INFORMATION_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(this, "No se encontró ninguna mascota con ese nombre.");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al buscar mascota: " + ex.getMessage());
        }
    }

    // Registrar nueva mascota de un cliente ya registrado
    private void registrarMascota() {
        String nombre = textnuevonombremascota.getText().trim();
        String especie = textnuevaespecie.getText().trim();
        String raza = textnuevaraza.getText().trim();
        String edadStr = textnuevaedad.getText().trim();
        String idCliente = textidclienteregistrado.getText().trim();

        // Validación
        if (nombre.isEmpty() || especie.isEmpty() || raza.isEmpty() || edadStr.isEmpty() || idCliente.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos.");
            return;
        }

        try (Connection conn = clever_cloud.conectar()) {
            // Verificar si el cliente existe
            String checkSql = "SELECT 1 FROM cliente WHERE id_cliente = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, Integer.parseInt(idCliente));
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(this, "El ID del cliente no existe.");
                return;
            }

            // Insertar mascota (sin id_mascota)
            String insertSql = "INSERT INTO mascota (nombre, especie, raza, edad, id_cliente) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setString(1, nombre);
            insertStmt.setString(2, especie);
            insertStmt.setString(3, raza);
            insertStmt.setInt(4, Integer.parseInt(edadStr));
            insertStmt.setInt(5, Integer.parseInt(idCliente));
            insertStmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Mascota registrada con éxito.");

            // Limpiar campos
            textnuevonombremascota.setText("");
            textnuevaespecie.setText("");
            textnuevaraza.setText("");
            textnuevaedad.setText("");
            textidclienteregistrado.setText("");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar mascota: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Edad o ID de cliente inválido.");
        }
    }

    //-----------------------------------------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------------------------------
    // Pestaña Factura
    private void buscarClienteFactura() {
        String id = textidclientefactura.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa un ID de cliente.");
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) clienteingresadofactura.getModel(); //Modificamos el modelo de la tabla clienteingresadofactura
        modelo.setRowCount(0); // Limpiar antes de insertar

        try (Connection conn = clever_cloud.conectar()) {
            String sql = "SELECT * FROM cliente WHERE id_cliente = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(id));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                modelo.addRow(new Object[]{
                        rs.getInt("id_cliente"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("correo"),
                        rs.getString("direccion")
                });
            } else {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado.");
            }

        } catch (SQLException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void agregarProductoAFactura() {
        String nombreProducto = textproductoserviciofactura.getText().trim();
        int cantidad = (int) cantidadfactura.getValue();

        if (nombreProducto.isEmpty() || cantidad <= 0) {
            JOptionPane.showMessageDialog(this, "Ingresa un producto válido y cantidad mayor a cero.");
            return;
        }

        try (Connection conn = clever_cloud.conectar()) {
            String sql = "SELECT precio FROM producto WHERE nombre = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombreProducto);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                double precio = rs.getDouble("precio");
                double subtotal = precio * cantidad;

                DefaultTableModel modelo = (DefaultTableModel) productservicioingresadofactura.getModel(); // Modificamos el modelo de la tabla productservicioingresadofactura
                modelo.addRow(new Object[]{nombreProducto, precio, cantidad, subtotal});
            } else {
                JOptionPane.showMessageDialog(this, "Producto o servicio no encontrado.");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al buscar producto: " + ex.getMessage());
        }
    }
    //Metodo para generar la factura al hacer clic en el botón "Generar Factura"
    private void generarFactura() {
        String fecha = textfecha.getText().trim(); //Obtener la fecha del campo de texto fecha
        if (fecha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa una fecha.");
            return;
        }

        DefaultTableModel modeloCliente = (DefaultTableModel) clienteingresadofactura.getModel();
        DefaultTableModel modeloItems = (DefaultTableModel) productservicioingresadofactura.getModel();

        //Establecemos que debe haber al menos un cliente y un producto/servicio para generar la factura
        if (modeloCliente.getRowCount() == 0 || modeloItems.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Debe haber un cliente y al menos un producto/servicio.");
            return;
        }


        StringBuilder factura = new StringBuilder();
        factura.append("Fecha: ").append(fecha).append("\n\n");

        factura.append("Cliente:\n");
        factura.append("ID: ").append(modeloCliente.getValueAt(0, 0)).append("\n");
        factura.append("Nombre: ").append(modeloCliente.getValueAt(0, 1)).append(" ").append(modeloCliente.getValueAt(0, 2)).append("\n");
        factura.append("Correo: ").append(modeloCliente.getValueAt(0, 3)).append("\n");
        factura.append("Dirección: ").append(modeloCliente.getValueAt(0, 4)).append("\n\n");

        factura.append("Detalle:\n");
        double total = 0.0;
        for (int i = 0; i < modeloItems.getRowCount(); i++) {
            String prod = (String) modeloItems.getValueAt(i, 0);
            double precio = (double) modeloItems.getValueAt(i, 1);
            int cantidad = (int) modeloItems.getValueAt(i, 2);
            double subtotal = (double) modeloItems.getValueAt(i, 3);

            factura.append("- ").append(prod).append(" | $").append(precio).append(" x ").append(cantidad).append(" = $").append(subtotal).append("\n");
            total += subtotal;
        }

        factura.append("\nTotal: $").append(String.format("%.2f", total));

        JOptionPane.showMessageDialog(this, factura.toString(), "Factura generada", JOptionPane.INFORMATION_MESSAGE);
    }
    // Limpiar campos de entrada en factura
    private void limpiarCamposFactura() {
        // Limpiar campos de texto
        textfecha.setText("");
        textidclientefactura.setText("");
        textproductoserviciofactura.setText("");

        // Reiniciar el spinner
        cantidadfactura.setValue(1); // O 0, dependiendo de tu lógica

        // Limpiar tablas
        DefaultTableModel modeloCliente = (DefaultTableModel) clienteingresadofactura.getModel();
        DefaultTableModel modeloItems = (DefaultTableModel) productservicioingresadofactura.getModel();
        modeloCliente.setRowCount(0);
        modeloItems.setRowCount(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Cajero::new);
    }

}

