import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//Esta clase es parte de un sistema de gestión de ventas, donde se pueden insertar detalles de productos en una factura.
public class DetalleProductoDAO {

    public void insertarDetalle(DetalleProducto detalle) {
        String sql = "INSERT INTO detalle_producto (id_factura, id_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";

        try (Connection conn = clever_cloud.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, detalle.getid_factura());
            stmt.setInt(2, detalle.getid_producto());
            stmt.setInt(3, detalle.getcantidad());


            stmt.executeUpdate();
            System.out.println(" Detalle de producto registrado correctamente.");

        } catch (SQLException e) {
            System.out.println(" Error al insertar detalle de producto: " + e.getMessage());
        }
    }
}