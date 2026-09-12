package Pokemon;

public class Entrenador {

    private String nombre;
    private ListaPokemon equipo;
    private ListaObjetos inventario;

    public Entrenador(String nombre) {
        this.nombre = nombre;
        equipo = new ListaPokemon();
        inventario = new ListaObjetos();
    }

    public String getNombre() {
        return nombre;
    }

    public ListaPokemon getEquipo() {
        return equipo;
    }

    public ListaObjetos getInventario() {
        return inventario;
    }

    public boolean agregarPokemon(Pokemon pokemon) {
        return equipo.insertar(pokemon);
    }

    public Pokemon getPokemonActivo() {
        return equipo.obtenerActivo();
    }

    public boolean tienePokemonDisponibles() {
        return equipo.contarDisponibles() > 0;
    }

    public void prepararParaBatalla() {
        equipo.restaurarTodos();
        inventario.restaurarTodos();
    }

    @Override
    public String toString() {
        return nombre + " (" + equipo.contar() + " Pokémon)";
    }
}
