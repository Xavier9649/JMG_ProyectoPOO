import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class Producto {
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

    // Este metodo nos permite registrar un nuevo producto en la base de datos
    // recibe los datos del producto y los inserta en la tabla producto
    public boolean registrar() {
        try (Connection conn = clever_cloud.conectar()) {
            String sql = "INSERT INTO producto (nombre, categoria, precio, cantidad) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombre);
            stmt.setString(2, categoria);
            stmt.setDouble(3, precio);
            stmt.setInt(4, cantidad);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Este metodo nos permite buscar un producto por su ID
    // recibe el ID del producto y retorna un objeto Producto con los datos encontrados
    // si no se encuentra el producto, retorna un objeto Producto con valores nulos
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
            return null;
        }
        return null;
    }

    // Este metodo nos permite buscar un producto por su nombre
    // recibe el nombre del producto y retorna un objeto Producto con los datos encontrados
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
            return null;
        }
        return null;
    }

    // Este metodo nos permite eliminar un producto de la base de datos
    // recibe el id del producto y lo elimina de la tabla producto
    public boolean eliminar() {
        try (Connection conn = clever_cloud.conectar()) {
            String sql = "DELETE FROM producto WHERE id_producto = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id_producto);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // Este metodo nos permite actualizar un producto en la base de datos
    // recibe los nuevos datos del producto y los actualiza en la tabla producto
    public boolean actualizar(String nuevoNombre, String nuevaCategoria, double nuevoPrecio, int nuevaCantidad) {
        try (Connection conn = clever_cloud.conectar()) {
            String sql = "UPDATE producto SET nombre = ?, categoria = ?, precio = ?, cantidad = ? WHERE id_producto = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nuevoNombre);
            stmt.setString(2, nuevaCategoria);
            stmt.setDouble(3, nuevoPrecio);
            stmt.setInt(4, nuevaCantidad);
            stmt.setInt(5, id_producto);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // Este metodo nos permite obtener los datos del producto en un formato de fila de tabla
    // toTableRow() retorna un arreglo de objetos con los datos del producto
    // este metodo es utilizado para mostrar los datos del producto en una tabla
    // en la interfaz de usuario
    public Object[] toTableRow() {
        return new Object[]{id_producto, nombre, categoria, precio, cantidad};
    }

    // Getters para acceder a los atributos del producto
    public int getId() { return id_producto; }
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public double getPrecio() { return precio; }
    public int getCantidad() { return cantidad; }
}



