package Pokemon;

public class ListaObjetos {

    private NodoObjeto cabeza;
    private int cantidad;

    public ListaObjetos() {
        cabeza = null;
        cantidad = 0;
    }

    public void insertar(Objeto objeto) {
        NodoObjeto nuevo = new NodoObjeto(objeto);

        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            NodoObjeto actual = cabeza;

            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }

            actual.setSiguiente(nuevo);
        }

        cantidad++;
    }

    public Objeto buscar(String nombre) {
        NodoObjeto actual = cabeza;

        while (actual != null) {
            if (actual.getObjeto().getNombre().equalsIgnoreCase(nombre)) {
                return actual.getObjeto();
            }

            actual = actual.getSiguiente();
        }

        return null;
    }

    public Objeto obtener(int indice) {
        if (indice < 0 || indice >= cantidad) {
            return null;
        }

        NodoObjeto actual = cabeza;

        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }

        return actual.getObjeto();
    }

    public int contar() {
        return cantidad;
    }

    public int contarDisponibles() {
        int disponibles = 0;
        NodoObjeto actual = cabeza;

        while (actual != null) {
            if (actual.getObjeto().hayDisponibles()) {
                disponibles++;
            }

            actual = actual.getSiguiente();
        }

        return disponibles;
    }

    public void restaurarTodos() {
        NodoObjeto actual = cabeza;

        while (actual != null) {
            actual.getObjeto().restaurar();
            actual = actual.getSiguiente();
        }
    }

    public boolean eliminar(String nombre) {
        if (cabeza == null) {
            return false;
        }

        if (cabeza.getObjeto().getNombre().equalsIgnoreCase(nombre)) {
            cabeza = cabeza.getSiguiente();
            cantidad--;
            return true;
        }

        NodoObjeto anterior = cabeza;

        while (anterior.getSiguiente() != null) {
            NodoObjeto actual = anterior.getSiguiente();

            if (actual.getObjeto().getNombre().equalsIgnoreCase(nombre)) {
                anterior.setSiguiente(actual.getSiguiente());
                cantidad--;
                return true;
            }

            anterior = actual;
        }

        return false;
    }
}
