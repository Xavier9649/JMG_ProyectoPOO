public class Mascota {
    private int id_mascota;
    private String nombre;
    private String raza;
    private String edad;

    //Constructor
    public Mascota(int id_mascota, String nombre, String raza, String edad) {
        this.id_mascota = id_mascota;
        this.nombre = nombre;
        this.raza = raza;
        this.edad = edad;
    }

    //Metodos
    //Registra los datos de la mascota
    public String getInfo() {
        return "ID Mascota: " + id_mascota + ", Nombre: " + nombre + ", Raza: " + raza + ", Edad: " + edad;
    }

    //Getters
    public int getIdMascota() {
        return id_mascota;
    }
    public String getNombre() {
        return nombre;
    }
    public String getRaza() {
        return raza;
    }

}
