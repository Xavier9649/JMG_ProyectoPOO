// DetalleServicio.java
import java.sql.*;

public class DetalleServicio {
    private int id_detalle;
    private Factura factura;
    private Producto servicio;
    private int cantidad;
    private double precio_unitario;

    public DetalleServicio(Factura factura, Producto servicio, int cantidad, double precio_unitario) {
        this.factura = factura;
        this.servicio = servicio;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
    }

    public boolean registrar() {
        String sql = "INSERT INTO detalle_servicio (id_factura, id_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
        try (Connection conn = clever_cloud.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, factura.getId());
            stmt.setInt(2, servicio.getId());
            stmt.setInt(3, cantidad);
            stmt.setDouble(4, precio_unitario);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al registrar detalle de servicio: " + e.getMessage());
            return false;
        }
    }
}
