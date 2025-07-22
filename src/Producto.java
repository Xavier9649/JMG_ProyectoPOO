public class Producto {
    private int id_producto;
    private String nombre;
    private String categoria;
    private Double precio;
    private int cantidad;

    //Constructor
    public Producto(int id_producto, String nombre, String categoria, Double precio, int cantidad) {
        this.id_producto = id_producto;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    //Metodos
    //Registra los datos del producto
    public String getInfoProd() {
        return "ID Producto: " + id_producto + ", Nombre: " + nombre + ", Categoria: " + categoria +
               ", Precio: $" + precio + ", Cantidad: " + cantidad;
    }

    //Actualiza el stock del producto
    public void actualizarStock(int cantidad) {
        this.cantidad += cantidad; //Aumenta la cantidad existente
        System.out.println("Stock actualizado. Nueva cantidad: " + this.cantidad);
    }

    //Getters
    public int getIdProducto() {
        return id_producto;
    }
    public String getNombre() {
        return nombre;
    }

    //Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
