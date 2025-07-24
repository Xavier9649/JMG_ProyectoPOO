import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//Esta clase es parte de un sistema de gestión de clientes, donde se pueden insertar nuevos clientes y consultar clientes existentes por su ID.
//Esta clase utiliza JDBC para interactuar con una base de datos MySQL, y se conecta a través de la clase clever_cloud.
public class ClienteDAO {

    // Insertar nuevo cliente
    public void insertarCliente(Cliente cliente) {
        String sql = "INSERT INTO cliente (nombre, apellido, cedula_cliente, telefono, correo) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = clever_cloud.conectar(); // Conectar a la base de datos
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNombre()); // Asegúrate que sea String si el campo es VARCHAR
            stmt.setString(2, cliente.getApellido());
            stmt.setInt(3, cliente.getCedulaCliente());
            stmt.setString(4, String.valueOf(cliente.getTelefono())); // Asegúrate que sea String si el campo es VARCHAR
            stmt.setString(5, cliente.getCorreo());

            stmt.executeUpdate(); // Ejecutar la inserción
            System.out.println("Cliente insertado exitosamente.");

        } catch (SQLException e) {
            System.out.println("Error al insertar cliente: " + e.getMessage());
        }
    }

    // Consultar cliente por ID
    public Cliente obtenerClientePorId(int id) { //
        String sql = "SELECT * FROM cliente WHERE id_cliente = ?";
        Cliente cliente = null;

        try (Connection conn = clever_cloud.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);// Establecer el ID del cliente a consultar
            ResultSet rs = stmt.executeQuery(); // Ejecutar la consulta

            if (rs.next()) { // Si se encuentra un cliente con ese ID
                cliente = new Cliente(
                        rs.getInt("id_cliente"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getInt("cedula_cliente"),
                        Integer.parseInt(rs.getString("telefono")),
                        rs.getString("correo")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar cliente: " + e.getMessage());
        }

        return cliente;
    }
}
