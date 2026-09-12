package Pokemon;

public class Usuario {

    private String nombre;
    private String contrasena;
    private Entrenador entrenador;

    public Usuario(String nombre, String contrasena) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario no puede estar vacío");
        }

        if (contrasena == null || contrasena.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }

        this.nombre = nombre;
        this.contrasena = contrasena;
        entrenador = new Entrenador(nombre);
    }

    public String getNombre() {
        return nombre;
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    public boolean contrasenaCorrecta(String intento) {
        return contrasena.equals(intento);
    }

    @Override
    public String toString() {
        return nombre + " - " + entrenador.getEquipo().contar() + " Pokémon";
    }
}