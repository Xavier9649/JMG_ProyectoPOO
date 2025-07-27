// Cliente.java
import java.sql.*;

public class Cliente {
    private int id_cliente;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private String direccion;

    public Cliente(int id_cliente, String nombre, String apellido, String telefono, String correo, String direccion) {
        this.id_cliente = id_cliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
    }

    public boolean registrar() {
        try (Connection conn = clever_cloud.conectar()) {
            String sql = "INSERT INTO cliente (id_cliente, nombre, apellido, telefono, correo, direccion) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id_cliente);
            stmt.setString(2, nombre);
            stmt.setString(3, apellido);
            stmt.setString(4, telefono);
            stmt.setString(5, correo);
            stmt.setString(6, direccion);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al registrar cliente: " + e.getMessage());
            return false;
        }
    }

    public static Cliente buscarPorID(int id) {
        try (Connection conn = clever_cloud.conectar()) {
            String sql = "SELECT * FROM cliente WHERE id_cliente = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Cliente(
                        rs.getInt("id_cliente"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("telefono"),
                        rs.getString("correo"),
                        rs.getString("direccion")
                );
            }
        } catch (Exception e) {
            System.out.println("Error al buscar cliente: " + e.getMessage());
        }
        return null;
    }

    public int getIdCliente() { return id_cliente; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getTelefono() { return telefono; }
    public String getCorreo() { return correo; }
    public String getDireccion() { return direccion; }
}



