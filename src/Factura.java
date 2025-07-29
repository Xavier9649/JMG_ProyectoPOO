import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

class Factura {
    private int id;
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

    //Este metodo nos permite obtener el id de la factura
    // es utilizado para registrar los detalles de la factura
    public int getId() { return id; }

    // Este metodo nos permite registrar una nueva factura en la base de datos
    // recibe los datos de la factura y los inserta en la tabla factura
    // retorna true si se registró correctamente, false si hubo un error
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
            e.printStackTrace();
            return false;
        }
    }

    // Este metodo nos permite registrar una factura con detalles
    // recibe una lista de detalles de servicio y los registra junto con la factura
    // list<DetalleServicio> detalles es una lista de objetos DetalleServicio que contienen
    // los detalles de los productos o servicios asociados a la factura
    public boolean registrarConDetalles(List<DetalleServicio> detalles) {
        if (!this.registrar()) return false;
        for (DetalleServicio d : detalles) {
            d.setFactura(this);
            if (!d.registrar()) return false;
        }
        return true;
    }
}

