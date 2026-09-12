package Pokemon;
import java.util.Random;

public class ListaAtaques {

    private NodoAtaque cabeza;
    private int cantidad;

    public ListaAtaques() {
        cabeza = null;
        cantidad = 0;
    }

    public void insertar(Ataque ataque) {
        NodoAtaque nuevo = new NodoAtaque(ataque);

        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            NodoAtaque actual = cabeza;

            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }

            actual.setSiguiente(nuevo);
        }

        cantidad++;
    }

    public Ataque obtener(int indice) {
        if (indice < 0 || indice >= cantidad) {
            return null;
        }

        NodoAtaque actual = cabeza;

        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }

        return actual.getAtaque();
    }

    public Ataque obtenerAleatorio(Random aleatorio) {
        if (cantidad == 0) {
            return null;
        }

        return obtener(aleatorio.nextInt(cantidad));
    }

    public int contar() {
        return cantidad;
    }
}
