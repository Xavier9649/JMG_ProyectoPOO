import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Esta clase es parte de un sistema de gestión de productos, donde se pueden insertar, actualizar y consultar productos en una base de datos.
public class ProductoDAO {

    // Insertar un nuevo producto
    public void insertarProducto(Producto producto) {
        String sql = "INSERT INTO producto (nombre, categoria, precio, cantidad) VALUES (?, ?, ?, ?)";

        try (Connection conn = clever_cloud.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getCategoria());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getCantidad());

            stmt.executeUpdate();
            System.out.println("Producto insertado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al insertar producto: " + e.getMessage());
        }
    }

    // Actualizar stock
    public void actualizarStock(int idProducto, int cantidadNueva) {
        String sql = "UPDATE producto SET cantidad = ? WHERE id_producto = ?";

        try (Connection conn = clever_cloud.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cantidadNueva);
            stmt.setInt(2, idProducto);

            stmt.executeUpdate();
            System.out.println("Stock actualizado.");

        } catch (SQLException e) {
            System.out.println("Error al actualizar stock: " + e.getMessage());
        }
    }

    // Consultar producto por ID
    public Producto obtenerProductoPorId(int id) {
        String sql = "SELECT * FROM producto WHERE id_producto = ?";
        Producto producto = null;

        try (Connection conn = clever_cloud.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                producto = new Producto(
                        rs.getInt("id_producto"),
                        rs.getString("nombre"),
                        rs.getString("categoria"),
                        rs.getDouble("precio"),
                        rs.getInt("cantidad")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar producto: " + e.getMessage());
        }

        return producto;
    }
}