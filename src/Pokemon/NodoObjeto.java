package Pokemon;

public class NodoObjeto {

    private Objeto objeto;
    private NodoObjeto siguiente;

    public NodoObjeto(Objeto objeto) {
        this.objeto = objeto;
        siguiente = null;
    }

    public Objeto getObjeto() {
        return objeto;
    }

    public NodoObjeto getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoObjeto siguiente) {
        this.siguiente = siguiente;
    }
}
