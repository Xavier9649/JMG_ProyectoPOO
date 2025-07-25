import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class Cajero extends JFrame {
    private JPanel cajeroPanel;
    private JTabbedPane menucontabs;
    private JPanel panelregistrarCliente;
    private JTextField txtclientenombre;
    private JButton registrarClienteButton;
    private JPanel panelinventario;
    private JTextField txtidproducto;
    private JButton buscarProductoButton;
    private JTable tableproducto;
    private JPanel panelclientes;
    private JTextField txtidcliente;
    private JButton buscarClienteButton;
    private JTable tablecliente;
    private JPanel panelmascotas;
    private JTextField txtnombremascota;
    private JButton buscarMascotaButton;
    private JTable tablemascota;
    private JPanel panelfactuiracion;
    private JTextField textclienteapellido;
    private JLabel cedulacliente;
    private JTextField textclientecedula;
    private JLabel nombrecliente;
    private JLabel apellidocliente;
    private JLabel telefonocliente;
    private JTextField textclientetelefono;
    private JLabel correocliente;
    private JTextField texttiposervicio;
    private JButton buscarServicioButton;
    private JTable tableservicios;
    private JTextField textFecha;
    private JTextField textclientenombre;
    private JButton buscarClienteButton1;
    private JTable clientesingresados;
    private JTextField textfacturaservicio;
    private JTextField textnombreproducto;
    private JSpinner cantidadproducto;
    private JButton obtenerValorDelServicioButton;
    private JButton obtenerValorDeProductosButton;
    private JLabel subtotalservicio;
    private JLabel subtotalproductos;
    private JButton agregarsubtotalservicio;
    private JButton agregarsubotalproductos;
    private JTable productosingresados;
    private JButton calcularTotalButton;
    private JButton generarfactura;
    private JButton limpiarRegistrosDeValoresButton;
    private JPanel Cajero;
    private JTextField textclientecorreo;
    private JTextField textnombremascota;
    private JTextField textrazamascota;
    private JTextField textedadmascota;
    private JButton registrarMascotaButton;

    //Ajustes de la ventana
    public Cajero() {
        setTitle("Cajero");
        setContentPane(Cajero);
        setVisible(true);
        setSize(800, 800);
        setContentPane(cajeroPanel);
        setLocationRelativeTo(null);

        //Acciones de cada seccion
        //Registrar Cliente en la base de datos
        registrarClienteButton.addActionListener(e -> {
            String nombre = txtclientenombre.getText();
            String apellido = textclienteapellido.getText();
            int cedula = Integer.parseInt(textclientecedula.getText());
            int telefono = Integer.parseInt(textclientetelefono.getText());
            String correo = textclientecorreo.getText();

            String sql = "INSERT INTO cliente (nombre, apellido, cedula, telefono, correo) VALUES (?, ?, ?, ?, ?)";

            try (Connection connection = clever_cloud.conectar();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, nombre);
                preparedStatement.setString(2, apellido);
                preparedStatement.setInt(3, cedula);
                preparedStatement.setInt(4, telefono);
                preparedStatement.setString(5, correo);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Cliente registrado exitosamente");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al registrar cliente: " + ex.getMessage());
            }
        });

        //Buscar Producto en la base de datos
        buscarProductoButton.addActionListener(e -> {
            String idProducto = txtidproducto.getText();
            String sql = "SELECT * FROM producto WHERE id_producto = ?";

            try (Connection connection = clever_cloud.conectar();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, Integer.parseInt(idProducto));
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String nombreProducto = resultSet.getString("nombre");
                    double precioProducto = resultSet.getDouble("precio");
                    int cantidadProducto = resultSet.getInt("cantidad");

                    // Actualizar la tabla con los datos del producto
                    JOptionPane.showMessageDialog(null, "Producto encontrado: " + nombreProducto +
                            "Precio: " + precioProducto + "Cantidad: " + cantidadProducto);

                } else {
                    JOptionPane.showMessageDialog(null, "Producto no encontrado");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al buscar producto: " + ex.getMessage());
            }
        });

        //Buscar Cliente en la base de datos
        buscarClienteButton.addActionListener(e -> {
            String idCliente = txtidcliente.getText();
            String sql = "SELECT * FROM cliente WHERE id_cliente = ?";

            try (Connection connection = clever_cloud.conectar();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, Integer.parseInt(idCliente));
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String nombreCliente = resultSet.getString("nombre");
                    String apellidoCliente = resultSet.getString("apellido");
                    int cedulaCliente = resultSet.getInt("cedula");
                    int telefonoCliente = resultSet.getInt("telefono");
                    String correoCliente = resultSet.getString("correo");

                    // Actualizar la tabla con los datos del cliente
                    JOptionPane.showMessageDialog(null, "Cliente encontrado: " + nombreCliente +
                            "Apellido: " + apellidoCliente + "Cedula: " + cedulaCliente +
                            "Telefono: " + telefonoCliente + "Correo: " + correoCliente);

                } else {
                    JOptionPane.showMessageDialog(null, "Cliente no encontrado");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al buscar cliente: " + ex.getMessage());
            }
        });

        //Buscar Mascota en la base de datos
        buscarMascotaButton.addActionListener(e -> {
            String nombreMascota = txtnombremascota.getText();
            String sql = "SELECT * FROM mascota WHERE nombre LIKE ?";

            try (Connection connection = clever_cloud.conectar();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, "%" + nombreMascota + "%");
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String nombre = resultSet.getString("nombre");
                    String raza = resultSet.getString("raza");
                    int edad = resultSet.getInt("edad");

                    // Actualizar la tabla con los datos de la mascota
                    JOptionPane.showMessageDialog(null, "Mascota encontrada: " + nombre +
                            "Raza: " + raza + "Edad: " + edad);

                } else {
                    JOptionPane.showMessageDialog(null, "Mascota no encontrada");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al buscar mascota: " + ex.getMessage());
            }
        });

        //Registrar Mascota en la base de datos
        registrarMascotaButton.addActionListener(e -> {
            String nombre = textnombremascota.getText();
            String raza = textrazamascota.getText();
            String edad = textedadmascota.getText();

            String sql = "INSERT INTO mascota (nombre, raza, edad) VALUES (?, ?, ?)";

            try (Connection connection = clever_cloud.conectar();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, nombre);
                preparedStatement.setString(2, raza);
                preparedStatement.setString(3, edad);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Mascota registrada exitosamente");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al registrar mascota: " + ex.getMessage());
            }
        });

        //Buscar Servicio en la base de datos
        buscarServicioButton.addActionListener(e -> {
            String tipoServicio = texttiposervicio.getText();
            String sql = "SELECT * FROM servicio WHERE tipo_servicio LIKE ?";

            try (Connection connection = clever_cloud.conectar();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, "%" + tipoServicio + "%");
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String nombreServicio = resultSet.getString("nombre");
                    double precioServicio = resultSet.getDouble("precio");

                    // Actualizar la tabla con los datos del servicio
                    JOptionPane.showMessageDialog(null, "Servicio encontrado: " + nombreServicio +
                            "Precio: " + precioServicio);

                } else {
                    JOptionPane.showMessageDialog(null, "Servicio no encontrado");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al buscar servicio: " + ex.getMessage());
            }
        });

        //Funciones para calcular valores y generar factura
        //Buscar Cliente para Factura
        buscarClienteButton1.addActionListener(e -> {
            String nombreCliente = textclientenombre.getText();
            String sql = "SELECT nombre, apellido, cedula FROM cliente WHERE nombre LIKE ?";

            try (Connection connection = clever_cloud.conectar();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setString(1, "%" + nombreCliente + "%");
                ResultSet resultSet = preparedStatement.executeQuery();

                // Limpiar la tabla antes de agregar nuevos datos
                DefaultTableModel modeloCliente = (DefaultTableModel) clientesingresados.getModel();

                boolean encontrado = false;

                while (resultSet.next()) {
                    encontrado = true;
                    modeloCliente.addRow(new Object[]{
                            resultSet.getString("nombre"),
                            resultSet.getString("apellido"),
                            resultSet.getInt("cedula")
                    });
                }

                if (encontrado) {
                    JOptionPane.showMessageDialog(null, "Cliente(s) agregado(s) a la tabla.");
                } else {
                    JOptionPane.showMessageDialog(null, "Cliente no encontrado.");
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al buscar cliente: " + ex.getMessage());
            }
        });

        // Obtener valor del servicio
        obtenerValorDelServicioButton.addActionListener(e -> {
            String tipoServicio = texttiposervicio.getText();
            String sql = "SELECT precio FROM servicio WHERE tipo_servicio = ?";

            try (Connection connection = clever_cloud.conectar();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, tipoServicio);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    double precioServicio = resultSet.getDouble("precio");
                    subtotalservicio.setText("Subtotal Servicio: " + precioServicio);
                } else {
                    JOptionPane.showMessageDialog(null, "Servicio no encontrado");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al obtener valor del servicio: " + ex.getMessage());
            }
        });

        //Agregar el servicio y su subtotal a la tabla
        agregarsubtotalservicio.addActionListener(e -> {
            String tipoServicio = texttiposervicio.getText();
            String sql = "SELECT precio FROM servicio WHERE tipo_servicio = ?";

            try (Connection connection = clever_cloud.conectar();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, tipoServicio);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    double precioServicio = resultSet.getDouble("precio");
                    DefaultTableModel modeloServicios = (DefaultTableModel) tableservicios.getModel();
                    modeloServicios.addRow(new Object[]{tipoServicio, precioServicio});
                } else {
                    JOptionPane.showMessageDialog(null, "Servicio no encontrado");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al agregar servicio: " + ex.getMessage());
            }
        });

        // Obtener valor de productos en funcion de la cantidad
        obtenerValorDeProductosButton.addActionListener(e -> {
            String nombreProducto = textnombreproducto.getText();
            int cantidad = (int) cantidadproducto.getValue();
            String sql = "SELECT precio FROM producto WHERE nombre = ?";

            try (Connection connection = clever_cloud.conectar();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, nombreProducto);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    double precioProducto = resultSet.getDouble("precio");
                    double subtotalProducto = precioProducto * cantidad;
                    subtotalproductos.setText("Subtotal Producto: " + subtotalProducto);
                } else {
                    JOptionPane.showMessageDialog(null, "Producto no encontrado");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al obtener valor del producto: " + ex.getMessage());
            }
        });

        //Agregar el producto y su subtotal a la tabla
        agregarsubotalproductos.addActionListener(e -> {
            String nombreProducto = textnombreproducto.getText();
            int cantidad = (int) cantidadproducto.getValue();
            String sql = "SELECT precio FROM producto WHERE nombre = ?";

            try (Connection connection = clever_cloud.conectar();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, nombreProducto);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    double precioProducto = resultSet.getDouble("precio");
                    double subtotalProducto = precioProducto * cantidad;
                    DefaultTableModel modeloProductos = (DefaultTableModel) productosingresados.getModel();
                    modeloProductos.addRow(new Object[]{nombreProducto, cantidad, subtotalProducto});
                } else {
                    JOptionPane.showMessageDialog(null, "Producto no encontrado");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al agregar producto: " + ex.getMessage());
            }
        });

        // Calcular el total de la factura y mostrarlo en un JLabel
        calcularTotalButton.addActionListener(e -> {
            double totalServicio = 0;
            double totalProducto = 0;

            // Calcular total de servicios
            DefaultTableModel modeloServicios = (DefaultTableModel) tableservicios.getModel();
            for (int i = 0; i < modeloServicios.getRowCount(); i++) {
                totalServicio += (double) modeloServicios.getValueAt(i, 1);
            }

            // Calcular total de productos
            DefaultTableModel modeloProductos = (DefaultTableModel) productosingresados.getModel();
            for (int i = 0; i < modeloProductos.getRowCount(); i++) {
                totalProducto += (double) modeloProductos.getValueAt(i, 2);
            }

            double totalFactura = totalServicio + totalProducto;
            JOptionPane.showMessageDialog(null, "Total de la factura: " + totalFactura);
        });

    }
    public static void main(String[] args) {
        new Cajero();
    }
}

