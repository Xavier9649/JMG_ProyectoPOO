import java.sql.Connection;
import java.sql.PreparedStatement;

class DetalleServicio {
    private int id;
    private Factura factura;
    private Producto producto;
    private int cantidad;
    private double precioUnitario;

    public DetalleServicio(Factura factura, Producto producto, int cantidad, double precioUnitario) {
        this.factura = factura;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    // Este metodo nos permite registrar los detalles de un servicio/producto en la base de datos
    public boolean registrar() {
        try (Connection conn = clever_cloud.conectar()) {
            String sql = "INSERT INTO detalle_servicio (id_factura, id_servicio, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, factura.getId());
            stmt.setInt(2, producto.getId());
            stmt.setInt(3, cantidad);
            stmt.setDouble(4, precioUnitario);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            // Si ocurre un error, mostramos un mensaje de error
            // y retornamos false
            e.printStackTrace();
            return false;
        }
    }
}


