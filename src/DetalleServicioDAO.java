import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// Esta clase es parte de un sistema de gestión de servicios, donde se pueden insertar detalles de servicios en una factura.
public class DetalleServicioDAO {

    // Insertar detalle de servicio
    public void insertarDetalle(DetalleServicio detalle) {
        String sql = "INSERT INTO detalle_servicio (id_factura, id_servicio, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";

        try (Connection conn = clever_cloud.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, detalle.getid_factura());
            stmt.setInt(2, detalle.getid_servicio());
            stmt.setInt(3, detalle.getcantidad());

            stmt.executeUpdate();
            System.out.println("Detalle de servicio insertado con éxito.");

        } catch (SQLException e) {
            System.out.println("Error al insertar detalle de servicio: " + e.getMessage());
        }
    }
}