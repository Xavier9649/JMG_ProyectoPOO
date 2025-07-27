// Producto.java
import java.sql.*;

public class Producto {
    private int id_producto;
    private String nombre, categoria;
    private double precio;
    private int cantidad;

    public Producto(int id_producto, String nombre, String categoria, double precio, int cantidad) {
        this.id_producto = id_producto;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public int getId() { return id_producto; }
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public double getPrecio() { return precio; }
    public int getCantidad() { return cantidad; }

    public static Producto buscarPorID(int id) {
        try (Connection conn = clever_cloud.conectar()) {
            String sql = "SELECT * FROM producto WHERE id_producto = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Producto(
                        rs.getInt("id_producto"),
                        rs.getString("nombre"),
                        rs.getString("categoria"),
                        rs.getDouble("precio"),
                        rs.getInt("cantidad")
                );
            }
        } catch (Exception e) {
            System.out.println("Error al buscar producto: " + e.getMessage());
        }
        return null;
    }

    public static Producto buscarPorNombre(String nombre) {
        try (Connection conn = clever_cloud.conectar()) {
            String sql = "SELECT * FROM producto WHERE nombre = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Producto(
                        rs.getInt("id_producto"),
                        rs.getString("nombre"),
                        rs.getString("categoria"),
                        rs.getDouble("precio"),
                        rs.getInt("cantidad")
                );
            }
        } catch (Exception e) {
            System.out.println("Error al buscar producto: " + e.getMessage());
        }
        return null;
    }

    public static boolean eliminarReferenciasDetalleServicio(int id_producto) {
        try (Connection conn = clever_cloud.conectar()) {
            String sql = "DELETE FROM detalle_servicio WHERE id_producto = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id_producto);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar detalle_servicio: " + e.getMessage());
            return false;
        }
    }

    public static boolean eliminar(int id) {
        try (Connection conn = clever_cloud.conectar()) {
            String sql = "DELETE FROM producto WHERE id_producto = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int filas = stmt.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }

    public static boolean actualizar(int id, String nombre, String categoria, double precio, int cantidad) {
        try (Connection conn = clever_cloud.conectar()) {
            String sql = "UPDATE producto SET nombre = ?, categoria = ?, precio = ?, cantidad = ? WHERE id_producto = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombre);
            stmt.setString(2, categoria);
            stmt.setDouble(3, precio);
            stmt.setInt(4, cantidad);
            stmt.setInt(5, id);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error al actualizar producto: " + e.getMessage());
            return false;
        }
    }
}



