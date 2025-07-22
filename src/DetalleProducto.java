public class DetalleProducto {
    private int id_factura;
    private int id_producto;
    private int cantidad;
    private double subtotalProducto;

    //Constructor
    public DetalleProducto(int id_factura, int id_producto, int cantidad, double subtotalProducto) {
        this.id_factura = id_factura;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.subtotalProducto = subtotalProducto;
    }

    //Metodos
    //Calcular Subtotal
    public double calcularSubtotal() {
        System.out.println("ID del producto: " + id_producto +
                           ", Cantidad: " + cantidad +
                           ", Subtotal por producto: $" + subtotalProducto);
        return cantidad * subtotalProducto;
    }
}
