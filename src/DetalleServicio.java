public class DetalleServicio {
    private int id_detalle;
    private int id_factura;
    private int id_servicio;
    private int cantidad;
    private double preciounitario;


    //Constructor
    public DetalleServicio(int id_detalle,int id_factura, int id_servicio, int cantidad, double preciounitario) {
        this.id_detalle= id_detalle;
        this.id_factura = id_factura;
        this.id_servicio = id_servicio;
        this.cantidad = cantidad;
        this.preciounitario = preciounitario;
    }

    //Metodos
    //Calcular Subtotal
    public double calcularSubtotal() {
        System.out.println("ID del producto: " + id_servicio +
                ", Cantidad: " + cantidad +
                ", Precio por producto: $" + preciounitario);
        return cantidad * preciounitario;
    }
    //Getters
    public int getid_factura() {
        return id_factura;
    }
    public int getid_servicio() {
        return id_servicio;
    }
    public int getcantidad() {
        return cantidad;
    }

}