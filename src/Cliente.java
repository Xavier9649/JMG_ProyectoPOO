public class Cliente {
    private int id_cliente;
    private String nombre;
    private String apellido;
    private int telefono;
    private String correo;
    private String direccion;

    //Constructor
    public Cliente (int id_cliente, String nombre, String apellido, int cedulaCliente, int telefono, String correo, String direccion) {
        this.id_cliente = id_cliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion=direccion;
    }

    //Metodos
    //Registrar Cliente
    public void registrarCliente() {
        System.out.println("Cliente Registrado: " + nombre + " " + apellido);
        System.out.println("Cédula: " + id_cliente);
        System.out.println("Teléfono: " + telefono);
        System.out.println("Correo: " + correo);
        System.out.println("Correo: " + direccion);
    }
    //Getters
    public int getIdCliente() {
        return id_cliente;
    }
    public String getNombre() {
        return nombre;
    }
    public String getApellido() {
        return apellido;
    }

    public int getTelefono() { return telefono; }
    public String getCorreo() { return correo; }
}

