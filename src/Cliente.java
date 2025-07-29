import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    // Este metodo nos permite registrar un nuevo cliente en la base de datos
    // recibe los datos del cliente y los inserta en la tabla cliente
    // retorna true si se registro correctamente, false si hubo un error
    public boolean registrar() {
        // Verificamos que los campos no esten vacios
        try (Connection conn = clever_cloud.conectar()) {
            //Creación de la sentencia SQL para insertar un nuevo cliente
            // usamos un PreparedStatement para evitar inyecciones SQL
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
            // Si ocurre un error, mostramos un mensaje de error
            return false;
        }
    }

    // Este metodo nos permite actualizar un cliente en la base de datos
    // recibe los nuevos datos del cliente y los actualiza en la tabla cliente
    // retorna true si se actualizo correctamente, false si hubo un error
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
            return null;
        }
        return null;
    }

    // Este metodo nos permite eliminar un cliente de la base de datos
    // recibe el id del cliente y lo elimina de la tabla cliente
    // retorna true si se elimino correctamente, false si hubo un error
    public static List<Cliente> buscarTodos() {
        List<Cliente> lista = new ArrayList<>();
        try (Connection conn = clever_cloud.conectar()) {
            String sql = "SELECT * FROM cliente";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Cliente(
                        rs.getInt("id_cliente"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("telefono"),
                        rs.getString("correo"),
                        rs.getString("direccion")
                ));
            }
        } catch (Exception e) {
            return lista;
        }
        return lista;
    }

    //toTableRow es un metodo que devuelve un array de objetos que representa una fila de la tabla
    //esto nos sirve para mostrar los datos en una tabla de la interfaz gráfica
    //cada objeto del array representa una columna de la tabla
    //Object[] es un array de objetos que representa una fila de la tabla
    public Object[] toTableRow() {
        return new Object[]{id_cliente, nombre, apellido, telefono, correo, direccion};
    }

    public int getIdCliente() { return id_cliente; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getTelefono() { return telefono; }
    public String getCorreo() { return correo; }
    public String getDireccion() { return direccion; }
}



