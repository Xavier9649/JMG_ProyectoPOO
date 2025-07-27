import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

class Mascota {
    private String nombre, especie, raza;
    private int edad, id_cliente;

    public Mascota(String nombre, String especie, String raza, int edad, int id_cliente) {
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.edad = edad;
        this.id_cliente = id_cliente;
    }

    public boolean registrar() {
        try (Connection conn = clever_cloud.conectar()) {
            String sql = "INSERT INTO mascota (nombre, especie, raza, edad, id_cliente) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombre);
            stmt.setString(2, especie);
            stmt.setString(3, raza);
            stmt.setInt(4, edad);
            stmt.setInt(5, id_cliente);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static List<Mascota> buscarPorNombre(String nombreBuscado) {
        List<Mascota> lista = new ArrayList<>();
        try (Connection conn = clever_cloud.conectar()) {
            String sql = "SELECT * FROM mascota WHERE nombre = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombreBuscado);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Mascota(
                        rs.getString("nombre"),
                        rs.getString("especie"),
                        rs.getString("raza"),
                        rs.getInt("edad"),
                        rs.getInt("id_cliente")
                ));
            }
        } catch (Exception e) {
            return lista;
        }
        return lista;
    }

    public Object[] toTableRow() {
        return new Object[]{"", nombre, especie, raza, edad, id_cliente};
    }

    public String getNombre() { return nombre; }
    public int getIdCliente() { return id_cliente; }
}
