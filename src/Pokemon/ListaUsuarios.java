package Pokemon;

import java.util.Random;

public class ListaUsuarios {

    private NodoUsuario cabeza;
    private int cantidad;

    public ListaUsuarios() {
        cabeza = null;
        cantidad = 0;
    }

    public boolean insertar(Usuario usuario) {
        if (buscar(usuario.getNombre()) != null) {
            return false;
        }

        NodoUsuario nuevo = new NodoUsuario(usuario);

        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            NodoUsuario actual = cabeza;

            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }

            actual.setSiguiente(nuevo);
        }

        cantidad++;
        return true;
    }

    public Usuario buscar(String nombre) {
        NodoUsuario actual = cabeza;

        while (actual != null) {
            if (actual.getUsuario().getNombre().equalsIgnoreCase(nombre)) {
                return actual.getUsuario();
            }

            actual = actual.getSiguiente();
        }

        return null;
    }

    public Usuario obtener(int indice) {
        if (indice < 0 || indice >= cantidad) {
            return null;
        }

        NodoUsuario actual = cabeza;

        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }

        return actual.getUsuario();
    }

    public Usuario obtenerAleatorio(Random aleatorio) {
        if (cantidad == 0) {
            return null;
        }

        return obtener(aleatorio.nextInt(cantidad));
    }

    public int contar() {
        return cantidad;
    }
}
