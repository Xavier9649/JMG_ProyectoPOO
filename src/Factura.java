import java.util.Date;

public class Factura {
    private int id_factura;
    private Date fecha;
    private Cliente cliente; //Debido a que Cliente es una clase, se puede usar como tipo de dato
    private String tipoVenta; // Producto o servicio, o ambos
    private double total;

    // Constructor
    public Factura (int id_factura, Date fecha, Cliente cliente, String tipoVenta, double total) {
        this.id_factura = id_factura;
        this.fecha = fecha;
        this.cliente = cliente;
        this.tipoVenta = tipoVenta;
        this.total = total;
    }

    //Metodos
    //Generar Factura void
    public void generarFactura() {
        System.out.println("Factura ID: " + id_factura);
        System.out.println("Fecha: " + fecha);
        System.out.println("Cliente: " + cliente.getNombre() + " " + cliente.getApellido()); // Usamos los getters de Cliente para obtener el nombre y apellido
        System.out.println("Tipo de Venta: " + tipoVenta);
        System.out.println("Total: $" + total);
    }
    //Getters
    public int getIdFactura() {
        return id_factura;
    }
}
