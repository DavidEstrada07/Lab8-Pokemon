package Pokemon;
public class NodoPokemon {

    private Pokemon pokemon;
    private NodoPokemon siguiente;

    public NodoPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
        siguiente = null;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public NodoPokemon getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoPokemon siguiente) {
        this.siguiente = siguiente;
    }
}
