import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAO {

    // Insertar nuevo cliente
    public void insertarCliente(Cliente cliente) {
        String sql = "INSERT INTO cliente (nombre, apellido, cedula_cliente, telefono, correo) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = clever_cloud.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.setInt(3, cliente.getCedulaCliente());
            stmt.setString(4, String.valueOf(cliente.getTelefono())); // Asegúrate que sea String si el campo es VARCHAR
            stmt.setString(5, cliente.getCorreo());

            stmt.executeUpdate();
            System.out.println("Cliente insertado exitosamente.");

        } catch (SQLException e) {
            System.out.println("Error al insertar cliente: " + e.getMessage());
        }
    }

    // Consultar cliente por ID
    public Cliente obtenerClientePorId(int id) {
        String sql = "SELECT * FROM cliente WHERE id_cliente = ?";
        Cliente cliente = null;

        try (Connection conn = clever_cloud.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
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
