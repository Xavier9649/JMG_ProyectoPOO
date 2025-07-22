public class DetalleServicio {
    private int id_factura;
    private int id_servicio;
    private int cantidad;
    private double subtotalServicio;

    //Constructor
    public DetalleServicio(int id_factura, int id_servicio, int cantidad, double subtotalServicio) {
        this.id_factura = id_factura;
        this.id_servicio = id_servicio;
        this.cantidad = cantidad;
        this.subtotalServicio = subtotalServicio;
    }

    //Metodos
    //Calcular Subtotal
    public double calcularSubtotal() {
        System.out.println("ID del producto: " + id_servicio +
                ", Cantidad: " + cantidad +
                ", Subtotal por producto: $" + subtotalServicio);
        return cantidad * subtotalServicio;
    }
}