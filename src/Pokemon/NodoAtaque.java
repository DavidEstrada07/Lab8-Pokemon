package Pokemon;
public class NodoAtaque {

    private Ataque ataque;
    private NodoAtaque siguiente;

    public NodoAtaque(Ataque ataque) {
        this.ataque = ataque;
        siguiente = null;
    }

    public Ataque getAtaque() {
        return ataque;
    }

    public NodoAtaque getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoAtaque siguiente) {
        this.siguiente = siguiente;
    }
}
