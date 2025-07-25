public class Mascota {
    private int id_mascota;
    private String nombre;
    private String especie;
    private String raza;
    private String edad;
    private int id_cliente;

    //Constructor
    public Mascota(int id_mascota, String nombre, String especie,String raza, String edad,int id_cliente) {
        this.id_mascota = id_mascota;
        this.nombre = nombre;
        this.especie= especie;
        this.raza = raza;
        this.edad = edad;
        this.id_cliente=id_cliente;

    }

    //Metodos
    //Registra los datos de la mascota
    public String getInfo() {
        return "ID Mascota: " + id_mascota + ", Nombre: " + nombre + "Especie:"+ especie +", Raza: " + raza + ", Edad: " + edad + "id cliente"+id_cliente;
    }


}
