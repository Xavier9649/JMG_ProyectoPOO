public class Usuario {
    public int id_usuario;
    public String nombre;
    public String contraseña;
    public String rol; // "Administrador", "Cajero"

    //Contructor
    public Usuario(int id_usuario, String nombre, String contraseña, String rol) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    //Metodos
    //Login del usuario al sistema segun rol
    public boolean login (String nombre, String contraseña) {
        if (this.nombre.equals(nombre) && this.contraseña.equals(contraseña)) {// Verifica las credenciales
            //Usamos this porque estamos dentro de la clase Usuario
            System.out.println("Bienvenido " + nombre + ", has iniciado sesión correctamente.");
            return true;
        } else {
            System.out.println("Error: Credenciales incorrectas o rol no autorizado.");
            return false;
        }
    }
}

