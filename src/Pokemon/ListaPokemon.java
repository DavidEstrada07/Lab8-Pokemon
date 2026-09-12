package Pokemon;

public class ListaPokemon {

    public static final int MAX_POKEMON = 4;

    private NodoPokemon cabeza;
    private NodoPokemon activo;
    private int cantidad; 

    public ListaPokemon() {
        cabeza = null;
        activo = null;
        cantidad = 0;
    }

    public boolean estaVacia() {
        return cabeza == null;
    }

    public boolean estaLlena() {
        return cantidad >= MAX_POKEMON;
    }

    public int contar() {
        return cantidad;
    }

    public boolean insertar(Pokemon pokemon) {
        if (pokemon == null) {
            throw new IllegalArgumentException("El Pokémon no puede ser nulo");
        }

        if (estaLlena()) {
            return false;
        }

        if (buscar(pokemon.getNombre()) != null) {
            return false;
        }

        NodoPokemon nuevo = new NodoPokemon(pokemon);

        if (cabeza == null) {
            cabeza = nuevo;
            activo = nuevo;
        } else {
            NodoPokemon actual = cabeza;

            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }

            actual.setSiguiente(nuevo);
        }

        cantidad++;
        return true;
    }

    public Pokemon buscar(String nombre) {
        NodoPokemon actual = cabeza;

        while (actual != null) {
            if (actual.getPokemon().getNombre().equalsIgnoreCase(nombre)) {
                return actual.getPokemon();
            }

            actual = actual.getSiguiente();
        }

        return null;
    }

    public Pokemon obtener(int indice) {
        if (indice < 0 || indice >= cantidad) {
            return null;
        }

        NodoPokemon actual = cabeza;

        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }

        return actual.getPokemon();
    }

    public int contarDisponibles() {
        int disponibles = 0;
        NodoPokemon actual = cabeza;

        while (actual != null) {
            if (!actual.getPokemon().estaDebilitado()) {
                disponibles++;
            }

            actual = actual.getSiguiente();
        }

        return disponibles;
    }

    public boolean todosDebilitados() {
        return contarDisponibles() == 0;
    }

    public boolean eliminar(String nombre) {
        if (cabeza == null) {
            return false;
        }

        if (cabeza.getPokemon().getNombre().equalsIgnoreCase(nombre)) {
            NodoPokemon eliminado = cabeza;
            cabeza = cabeza.getSiguiente();
            cantidad--;

            if (activo == eliminado) {
                activo = cabeza;
            }

            return true;
        }

        NodoPokemon anterior = cabeza;

        while (anterior.getSiguiente() != null) {
            NodoPokemon actual = anterior.getSiguiente();

            if (actual.getPokemon().getNombre().equalsIgnoreCase(nombre)) {
                anterior.setSiguiente(actual.getSiguiente());
                cantidad--;

                if (activo == actual) {
                    activo = cabeza;
                }

                return true;
            }

            anterior = actual;
        }

        return false;
    }


    public Pokemon obtenerActivo() {
        if (activo == null) {
            return null;
        }

        return activo.getPokemon();
    }

    public boolean cambiarActivo(String nombre) {
        NodoPokemon actual = cabeza;

        while (actual != null) {
            if (actual.getPokemon().getNombre().equalsIgnoreCase(nombre)) {
                if (actual.getPokemon().estaDebilitado()) {
                    return false;
                }

                activo = actual;
                return true;
            }

            actual = actual.getSiguiente();
        }

        return false;
    }

    public Pokemon siguienteDisponible() {
        if (cabeza == null) {
            return null;
        }

        NodoPokemon actual = activo;

        for (int i = 0; i < cantidad; i++) {
            if (actual == null || actual.getSiguiente() == null) {
                actual = cabeza;
            } else {
                actual = actual.getSiguiente();
            }

            if (!actual.getPokemon().estaDebilitado()) {
                return actual.getPokemon();
            }
        }

        return null;
    }


    public boolean modificar(String nombre, int nivel, int vidaMaxima) {
        Pokemon pokemon = buscar(nombre);

        if (pokemon == null) {
            return false;
        }

        pokemon.setNivel(nivel);
        pokemon.setVidaMaxima(vidaMaxima);
        return true;
    }

    public boolean moverAlPrimerLugar(String nombre) {
        if (cabeza == null) {
            return false;
        }

        if (cabeza.getPokemon().getNombre().equalsIgnoreCase(nombre)) {
            return true;
        }

        NodoPokemon anterior = cabeza;

        while (anterior.getSiguiente() != null) {
            NodoPokemon actual = anterior.getSiguiente();

            if (actual.getPokemon().getNombre().equalsIgnoreCase(nombre)) {
                anterior.setSiguiente(actual.getSiguiente());
                actual.setSiguiente(cabeza);
                cabeza = actual;
                return true;
            }

            anterior = actual;
        }

        return false;
    }

    public void restaurarTodos() {
        NodoPokemon actual = cabeza;

        while (actual != null) {
            actual.getPokemon().restaurar();
            actual = actual.getSiguiente();
        }

        activo = cabeza;
    }
}