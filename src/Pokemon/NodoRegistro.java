package Pokemon;

public class NodoRegistro {

    private Registro registro;
    private NodoRegistro siguiente;

    public NodoRegistro(Registro registro) {
        this.registro = registro;
        siguiente = null;
    }

    public Registro getRegistro() {
        return registro;
    }

    public NodoRegistro getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoRegistro siguiente) {
        this.siguiente = siguiente;
    }
}
