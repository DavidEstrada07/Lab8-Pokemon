package Pokemon;

public class Ataque {

    private String nombre;
    private Tipo tipo;
    private int poder;

    public Ataque(String nombre, Tipo tipo, int poder) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.poder = poder;
    }

    public String getNombre() {
        return nombre;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public int getPoder() {
        return poder;
    }

    @Override
    public String toString() {
        return nombre + " (" + tipo + ", poder " + poder + ")";
    }
}
