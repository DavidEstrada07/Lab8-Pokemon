package Pokemon;

public class Objeto {

    private String nombre;
    private String descripcion;
    private int puntos;
    private boolean revive;
    private int cantidad;
    private int cantidadInicial;

    public Objeto(String nombre, String descripcion, int puntos, boolean revive, int cantidad) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.puntos = puntos;
        this.revive = revive;
        this.cantidad = cantidad;
        cantidadInicial = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getPuntos() {
        return puntos;
    }

    public boolean revive() {
        return revive;
    }

    public int getCantidad() {
        return cantidad;
    }

    public boolean hayDisponibles() {
        return cantidad > 0;
    }

    public void descontarUno() {
        if (cantidad > 0) {
            cantidad--;
        }
    }

    public void restaurar() {
        cantidad = cantidadInicial;
    }

    public void agregarUnidades(int unidades) {
        if (unidades < 0) {
            throw new IllegalArgumentException("Las unidades no pueden ser negativas");
        }

        cantidad = cantidad + unidades;
    }

    public String getImagen() {
        return nombre.toLowerCase();
    }

    @Override
    public String toString() {
        return nombre + " x" + cantidad + "  -  " + descripcion;
    }
}
