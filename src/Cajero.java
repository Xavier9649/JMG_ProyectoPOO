import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

//Definimos la clase Cajero que extiende JFrame para crear una interfaz gráfica de usuario
public class Cajero extends JFrame {
    // Componentes de la interfaz gráfica
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

    //Constructor de la clase Cajero
    // Aquí se inicializan los componentes de la interfaz gráfica y se configuran sus propiedades
    public Cajero() {
        setContentPane(tabbedPane1);
        setTitle("Panel Cajero");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        //Inicializamos las tablas y los listeners aqui
        // para que se configuren al iniciar la aplicación
        inicializarTablas();
        inicializarListeners();
    }


    //Para optimizar la legibilidad y mantenimiento del código
    //hemos separado las inicializaciones de tablas y listeners en métodos distintos.
    private void inicializarTablas() {
        // Inicializamos los modelos de las tablas para cada sección
        // para que se muestren los datos correctamente
        //Básicamente se crea un modelo de tabla para cada sección mediante objetos DefaultTableModel
        modeloProductoServicio = new DefaultTableModel(new Object[]{"ID", "Nombre", "Categoría", "Precio", "Cantidad"}, 0);
        productoservicioregistro.setModel(modeloProductoServicio);

        clienteingresadofactura.setModel(new DefaultTableModel(new Object[]{"ID", "Nombre", "Apellido", "Correo", "Dirección"}, 0));
        productservicioingresadofactura.setModel(new DefaultTableModel(new Object[]{"Producto", "Precio Unitario", "Cantidad", "Subtotal"}, 0));

        modeloClienteFactura = new DefaultTableModel(new Object[]{"ID", "Nombre", "Apellido", "Correo", "Dirección"}, 0);
        clienteingresadofactura.setModel(modeloClienteFactura);

        modeloDetalleFactura = new DefaultTableModel(new Object[]{"Producto", "Precio Unitario", "Cantidad", "Subtotal"}, 0);
        productservicioingresadofactura.setModel(modeloDetalleFactura);
    }

    // Este método inicializa los listeners de los botones para manejar eventos de clic
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

    //Pestaña Cliente
    //Obtenemos los datos de los campos de texto y creamos un objeto Cliente
    private void registrarCliente() {
        Cliente cliente = new Cliente(
                Integer.parseInt(textnuevoidcliente.getText()),
                textnuevonombre.getText(),
                textnuevoapellido.getText(),
                textnuevotelefono.getText(),
                textnuevocorreo.getText(),
                textnuevadireccion.getText()
        );
        //Si el registro es exitoso, mostramos un mensaje de éxito
        if (cliente.registrar()) JOptionPane.showMessageDialog(this, "Cliente registrado");
    }

    private void buscarCliente() {
        //Aqui llamamos al método buscarPorID de la clase Cliente
        Cliente cliente = Cliente.buscarPorID(Integer.parseInt(textidcliente.getText()));
        //Se mostrará un mensaje de diálogo con el nombre del cliente encontrado o un mensaje de error si no se encuentra
        //Si cliente es != null, mostramos su nombre, de lo contrario, mostramos un mensaje de error
        JOptionPane.showMessageDialog(this, cliente != null ? "Cliente encontrado: " + cliente.getNombre() : "Cliente no encontrado");

    }

    private void buscarProductoServicio() {
        try {
            int id = Integer.parseInt(textidproductoservicio.getText());
            //Llamamos al método buscarPorID de la clase Producto para buscar el producto por su ID
            Producto producto = Producto.buscarPorID(id);
            modeloProductoServicio.setRowCount(0); // limpia la tabla antes de agregar el nuevo producto
            //Si el producto es encontrado, se agrega una fila a la tabla de productos con sus datos
            //Si el producto es null, mostramos un mensaje de error
            if (producto != null) {
                //Agregamos una fila a la tabla con los datos del producto encontrado
                modeloProductoServicio.addRow(producto.toTableRow());
            } else {
                //Si el producto no es encontrado, mostramos un mensaje de error
                JOptionPane.showMessageDialog(this, "Producto no encontrado.");
            }
            //NumberFormatException es una excepción que se lanza cuando se intenta convertir
            // una cadena a un número y la cadena no es un número válido
        } catch (NumberFormatException e) {
            //Si ocurre una excepción, mostramos un mensaje de error
            JOptionPane.showMessageDialog(this, "Ingrese un ID válido.");
        }
    }

    private void buscarMascota() {
        //Lista<Mascota> es una lista de objetos Mascota que se obtiene al llamar al método buscarPorNombre
        List<Mascota> lista = Mascota.buscarPorNombre(textnombremascota.getText());
        //Creamos un onjeto StringBuilder para construir el resultado
        StringBuilder resultado = new StringBuilder();
        //Iteramos sobre la lista de mascotas y agregamos sus nombres y el ID del cliente al resultado
        for (Mascota m : lista) {
            resultado.append("Mascota: ").append(m.getNombre()).append(" - Cliente: ").append(m.getIdCliente()).append(" ");
        }
        JOptionPane.showMessageDialog(this, resultado.length() > 0 ? resultado.toString() : "No se encontraron mascotas");
    }

    private void registrarMascota() {
        //Obtenemos los datos de los campos de texto y creamos un objeto Mascota
        Mascota mascota = new Mascota(
                textnuevonombremascota.getText(),
                textnuevaespecie.getText(),
                textnuevaraza.getText(),
                Integer.parseInt(textnuevaedad.getText()),
                Integer.parseInt(textidclienteregistrado.getText())
        );
        //Llamamos al método registrar de la clase Mascota para registrar la mascota
        if (mascota.registrar()) JOptionPane.showMessageDialog(this, "Mascota registrada");
    }

    private void buscarClienteFactura() {
        //Creamos un objeto Cliente al llamar al método buscarPorID de la clase Cliente
        //Obtenemos el ID del cliente desde el campo de texto textidclientefact
        Cliente cliente = Cliente.buscarPorID(Integer.parseInt(textidclientefactura.getText()));
        modeloClienteFactura.setRowCount(0);
        //Si el cliente es encontrado, se agrega una fila a la tabla de clientes con sus datos
        if (cliente != null) modeloClienteFactura.addRow(cliente.toTableRow());
    }

    private void agregarProductoAFactura() {
        // Verificamos que se haya ingresado un producto o servicio en el campo de texto
        String nombre = textproductoserviciofactura.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa el nombre del producto o servicio.");
            return;
        }
        //Declaramos una variable cantidad para obtener el valor del spinner cantidadfactura
        int cantidad = (int) cantidadfactura.getValue();
        //Buscamos el producto por su nombre utilizando el método buscarPorNombre de la clase Producto
        Producto p = Producto.buscarPorNombre(nombre);
        if (p != null) {
            //Si el producto es encontrado, agregamos una fila a la tabla de detalle de factura
            double subtotal = p.getPrecio() * cantidad;
            modeloDetalleFactura.addRow(new Object[]{p.getNombre(), p.getPrecio(), cantidad, subtotal});
        } else {
            JOptionPane.showMessageDialog(this, "Producto no encontrado: " + nombre);
        }
    }

    private void generarFactura() {
        //Primero verificamos que se haya ingresado un cliente y al menos un producto
        if (modeloClienteFactura.getRowCount() == 0 || modeloDetalleFactura.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Debe ingresar cliente y al menos un producto");
            return;
        }

        //Obtenemos el ID del cliente desde la primera fila de la tabla modeloClienteFactura
        // y calculamos el total de la factura
        int idCliente = (int) modeloClienteFactura.getValueAt(0, 0);
        double total = 0;
        //Creamos una lista de detalles de servicio para almacenar los productos y sus cantidades
        List<DetalleServicio> detalles = new ArrayList<>();
        //Con StringBuilder construimos un resumen de la factura
        //StringBuilder es una clase que permite construir cadenas de texto de manera eficiente
        //Es más eficiente que concatenar cadenas con el operador +
        //porque evita crear múltiples objetos String intermedios
        StringBuilder resumen = new StringBuilder();

        //Agregamos los datos del cliente y la fecha al resumen
        //Con append agregamos texto al StringBuilder
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

            //Buscamos el producto por su nombre
            Producto p = Producto.buscarPorNombre(nombreProd);

            //Si el producto es encontrado, lo agregamos a la lista de detalles y al resumen
            if (p != null) {
                detalles.add(new DetalleServicio(null, p, cantidad, precio));
                total += subtotal;
                resumen.append("- ").append(nombreProd)
                        .append(" x").append(cantidad)
                        .append(" @ ").append(precio)
                        .append(" = $").append(String.format("%.2f", subtotal)).append("\n");
            }
        }

        //Agregamos el total al resumen
        // usamos String.format para formatear el total a dos decimales
        resumen.append("\nTotal: $").append(String.format("%.2f", total));

        //Creamos un objeto Factura con la fecha, tipo, total y ID del cliente
        // y llamamos al método registrarConDetalles para registrar la factura con sus detalles
        Factura factura = new Factura(java.sql.Date.valueOf(textfecha.getText()), "Venta", total, idCliente);
        if (factura.registrarConDetalles(detalles)) {
            JOptionPane.showMessageDialog(this, "Factura registrada correctamente.\n\n" + resumen.toString());
            limpiarCamposFactura();
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar factura");
        }
    }


    // Método para limpiar los campos de la factura
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



