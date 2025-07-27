// Factura.java
import java.sql.*;
import java.util.Date;

public class Factura {
    private int id; // Se requiere para enlazar con detalle_servicio
    private Date fecha;
    private String tipoVenta;
    private double total;
    private int idCliente;

    public Factura(Date fecha, String tipoVenta, double total, int idCliente) {
        this.fecha = fecha;
        this.tipoVenta = tipoVenta;
        this.total = total;
        this.idCliente = idCliente;
    }

    public int getId() { return id; } // Agregado para enlazar detalles

    public boolean registrar() {
        try (Connection conn = clever_cloud.conectar()) {
            String sql = "INSERT INTO factura (fecha, tipo_venta, total, id_cliente) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setDate(1, new java.sql.Date(fecha.getTime()));
            stmt.setString(2, tipoVenta);
            stmt.setDouble(3, total);
            stmt.setInt(4, idCliente);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }

            return true;
        } catch (Exception e) {
            System.out.println("Error al registrar factura: " + e.getMessage());
            return false;
        }
    }
}
