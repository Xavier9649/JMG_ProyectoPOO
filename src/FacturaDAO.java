import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Esta clase es parte de un sistema de gestión de facturas, donde se pueden insertar y consultar facturas en una base de datos.
public class FacturaDAO {

    // Insertar factura en la base de datos
    public int insertarFactura(Factura factura) {
        String sql = "INSERT INTO factura (fecha, tipo_venta, total, id_cliente) VALUES (?, ?, ?, ?)";
        int idGenerado = -1;

        try (Connection conn = clever_cloud.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Convertir java.util.Date a java.sql.Date
            java.sql.Date fechaSQL = new java.sql.Date(factura.getFecha().getTime());

            stmt.setDate(1, fechaSQL);
            stmt.setString(2, factura.getTipoVenta());
            stmt.setDouble(3, factura.getTotal());
            stmt.setInt(4, factura.getCliente().getIdCliente());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                idGenerado = rs.getInt(1);
                System.out.println("Factura insertada con ID: " + idGenerado);
            }

        } catch (SQLException e) {
            System.out.println(" Error al insertar factura: " + e.getMessage());
        }

        return idGenerado;
    }

    // Obtener factura por ID (incluye reconstrucción del Cliente)
    public Factura obtenerFacturaPorId(int id) {
        String sql = "SELECT * FROM factura WHERE id_factura = ?";
        Factura factura = null;

        try (Connection conn = clever_cloud.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Cliente cliente = new ClienteDAO().obtenerClientePorId(rs.getInt("id_cliente"));

                factura = new Factura(
                        rs.getInt("id_factura"),
                        rs.getDate("fecha"),
                        cliente,
                        rs.getString("tipo_venta"),
                        rs.getDouble("total")
                );
            }

        } catch (SQLException e) {
            System.out.println(" Error al consultar factura: " + e.getMessage());
        }

        return factura;
    }
}