public class Servicio {
    private int id_servicio;
    private String tipo;
    private double precio;

    //Constructor
    public Servicio(int id_servicio, String tipo, double precio) {
        this.id_servicio = id_servicio;
        this.tipo = tipo;
        this.precio = precio;
    }

    //Metodos
    //Registrar Servicio
    public void registrarServicio() {
        System.out.println("Servicio Registrado: " + tipo);
        System.out.println("ID Servicio: " + id_servicio);
        System.out.println("Precio: $" + precio);
    }

    //Getters
    public String getTipo() {
        return tipo;
    }
}
