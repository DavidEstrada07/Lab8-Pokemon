package Pokemon;

import java.util.Random;

public class BancoDatos {

    public static final String[] NOMBRES_POKEMON = {
        "Charizard", "Arcanine", "Blastoise", "Gyarados",
        "Venusaur", "Bulbasaur", "Pikachu", "Raichu",
        "Onix", "Golem", "Alakazam", "Mewtwo",
        "Gengar", "Haunter", "Machamp", "Hitmonlee"
    };

    private static final String[] NOMBRES_RIVALES = {
        "Rojo", "Azul", "Verde", "Amarillo", "Oro",
        "Plata", "Cristal", "Rubí", "Zafiro", "Esmeralda"
    };

    public static Pokemon crearPokemon(String nombre) {
        Pokemon pokemon;

        if (nombre.equalsIgnoreCase("Charizard")) {
            pokemon = new Pokemon("Charizard", Tipo.FUEGO, 18, 150);
            pokemon.agregarAtaque(new Ataque("Lanzallamas", Tipo.FUEGO, 60));
            pokemon.agregarAtaque(new Ataque("Golpe Cuerpo", Tipo.LUCHA, 45));

        } else if (nombre.equalsIgnoreCase("Arcanine")) {
            pokemon = new Pokemon("Arcanine", Tipo.FUEGO, 17, 145);
            pokemon.agregarAtaque(new Ataque("Rueda Fuego", Tipo.FUEGO, 55));
            pokemon.agregarAtaque(new Ataque("Excavar", Tipo.TIERRA, 45));

        } else if (nombre.equalsIgnoreCase("Blastoise")) {
            pokemon = new Pokemon("Blastoise", Tipo.AGUA, 18, 150);
            pokemon.agregarAtaque(new Ataque("Hidrobomba", Tipo.AGUA, 65));
            pokemon.agregarAtaque(new Ataque("Cabezazo", Tipo.LUCHA, 45));

        } else if (nombre.equalsIgnoreCase("Gyarados")) {
            pokemon = new Pokemon("Gyarados", Tipo.AGUA, 17, 155);
            pokemon.agregarAtaque(new Ataque("Surf", Tipo.AGUA, 60));
            pokemon.agregarAtaque(new Ataque("Mordisco", Tipo.FANTASMA, 40));

        } else if (nombre.equalsIgnoreCase("Venusaur")) {
            pokemon = new Pokemon("Venusaur", Tipo.PLANTA, 18, 150);
            pokemon.agregarAtaque(new Ataque("Látigo Cepa", Tipo.PLANTA, 60));
            pokemon.agregarAtaque(new Ataque("Bomba Lodo", Tipo.TIERRA, 50));

        } else if (nombre.equalsIgnoreCase("Bulbasaur")) {
            pokemon = new Pokemon("Bulbasaur", Tipo.PLANTA, 12, 100);
            pokemon.agregarAtaque(new Ataque("Hoja Afilada", Tipo.PLANTA, 50));
            pokemon.agregarAtaque(new Ataque("Placaje", Tipo.LUCHA, 35));

        } else if (nombre.equalsIgnoreCase("Pikachu")) {
            pokemon = new Pokemon("Pikachu", Tipo.ELECTRICO, 14, 105);
            pokemon.agregarAtaque(new Ataque("Impactrueno", Tipo.ELECTRICO, 50));
            pokemon.agregarAtaque(new Ataque("Ataque Rápido", Tipo.LUCHA, 35));

        } else if (nombre.equalsIgnoreCase("Raichu")) {
            pokemon = new Pokemon("Raichu", Tipo.ELECTRICO, 17, 130);
            pokemon.agregarAtaque(new Ataque("Rayo", Tipo.ELECTRICO, 60));
            pokemon.agregarAtaque(new Ataque("Puño Trueno", Tipo.ELECTRICO, 45));

        } else if (nombre.equalsIgnoreCase("Onix")) {
            pokemon = new Pokemon("Onix", Tipo.TIERRA, 15, 140);
            pokemon.agregarAtaque(new Ataque("Avalancha", Tipo.TIERRA, 55));
            pokemon.agregarAtaque(new Ataque("Placaje", Tipo.LUCHA, 40));

        } else if (nombre.equalsIgnoreCase("Golem")) {
            pokemon = new Pokemon("Golem", Tipo.TIERRA, 17, 150);
            pokemon.agregarAtaque(new Ataque("Terremoto", Tipo.TIERRA, 60));
            pokemon.agregarAtaque(new Ataque("Puño Dinámico", Tipo.LUCHA, 45));

        } else if (nombre.equalsIgnoreCase("Alakazam")) {
            pokemon = new Pokemon("Alakazam", Tipo.PSIQUICO, 18, 120);
            pokemon.agregarAtaque(new Ataque("Psíquico", Tipo.PSIQUICO, 65));
            pokemon.agregarAtaque(new Ataque("Confusion", Tipo.PSIQUICO, 45));

        } else if (nombre.equalsIgnoreCase("Mewtwo")) {
            pokemon = new Pokemon("Mewtwo", Tipo.PSIQUICO, 20, 170);
            pokemon.agregarAtaque(new Ataque("Psicorrayo", Tipo.PSIQUICO, 70));
            pokemon.agregarAtaque(new Ataque("Bola Sombra", Tipo.FANTASMA, 55));

        } else if (nombre.equalsIgnoreCase("Gengar")) {
            pokemon = new Pokemon("Gengar", Tipo.FANTASMA, 17, 130);
            pokemon.agregarAtaque(new Ataque("Bola Sombra", Tipo.FANTASMA, 60));
            pokemon.agregarAtaque(new Ataque("Lengüetazo", Tipo.FANTASMA, 40));

        } else if (nombre.equalsIgnoreCase("Haunter")) {
            pokemon = new Pokemon("Haunter", Tipo.FANTASMA, 14, 110);
            pokemon.agregarAtaque(new Ataque("Lengüetazo", Tipo.FANTASMA, 45));
            pokemon.agregarAtaque(new Ataque("Puño Sombra", Tipo.FANTASMA, 40));

        } else if (nombre.equalsIgnoreCase("Machamp")) {
            pokemon = new Pokemon("Machamp", Tipo.LUCHA, 18, 155);
            pokemon.agregarAtaque(new Ataque("Puño Dinámico", Tipo.LUCHA, 60));
            pokemon.agregarAtaque(new Ataque("Golpe Karate", Tipo.LUCHA, 45));

        } else if (nombre.equalsIgnoreCase("Hitmonlee")) {
            pokemon = new Pokemon("Hitmonlee", Tipo.LUCHA, 16, 120);
            pokemon.agregarAtaque(new Ataque("Patada Salto", Tipo.LUCHA, 55));
            pokemon.agregarAtaque(new Ataque("Patada Baja", Tipo.LUCHA, 40));

        } else {
            throw new IllegalArgumentException("No existe el Pokémon: " + nombre);
        }

        return pokemon;
    }

    public static void llenarInventarioInicial(Entrenador entrenador) {
        ListaObjetos inventario = entrenador.getInventario();
        inventario.insertar(new Objeto("Pocion", "Recupera 20 HP", 20, false, 3));
        inventario.insertar(new Objeto("Superpocion", "Recupera 50 HP", 50, false, 2));
        inventario.insertar(new Objeto("Revivir", "Revive a un Pokémon debilitado", 0, true, 1));
    }

    public static void llenarEquipoAleatorio(Entrenador entrenador, Random aleatorio) {
        while (!entrenador.getEquipo().estaLlena()) {
            String nombre = NOMBRES_POKEMON[aleatorio.nextInt(NOMBRES_POKEMON.length)];
            entrenador.agregarPokemon(crearPokemon(nombre));
        }
    }

    public static ListaUsuarios crearUsuariosRivales(Random aleatorio) {
        ListaUsuarios usuarios = new ListaUsuarios();

        for (int i = 0; i < NOMBRES_RIVALES.length; i++) {
            Usuario usuario = new Usuario(NOMBRES_RIVALES[i], "1234");
            llenarEquipoAleatorio(usuario.getEntrenador(), aleatorio);
            usuarios.insertar(usuario);
        }

        return usuarios;
    }

    public static Entrenador crearRivalAleatorio(Random aleatorio) {
        ListaUsuarios rivales = crearUsuariosRivales(aleatorio);
        Usuario elegido = rivales.obtenerAleatorio(aleatorio);
        return elegido.getEntrenador();
    }
}
