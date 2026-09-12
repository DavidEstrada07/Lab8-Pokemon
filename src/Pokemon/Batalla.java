package Pokemon;

import java.util.Random;

public class Batalla {

    private Entrenador jugador;
    private Entrenador rival;
    private ListaHistorial historial;
    private Random aleatorio;

    private int turno;
    private boolean terminada;

    private int ataquesRealizados;
    private int danoCausado;
    private int danoRecibido;
    private int objetosUsados;
    private int cambiosRealizados;

    public Batalla(Entrenador jugador, Entrenador rival) {
        this.jugador = jugador;
        this.rival = rival;
        historial = new ListaHistorial();
        aleatorio = new Random();
        iniciar();
    }

    public void iniciar() {
        jugador.prepararParaBatalla();
        rival.prepararParaBatalla();

        turno = 1;
        terminada = false;
        ataquesRealizados = 0;
        danoCausado = 0;
        danoRecibido = 0;
        objetosUsados = 0;
        cambiosRealizados = 0;

        historial.limpiar();
        anotar("Comienza el combate contra " + rival.getNombre() + ".");
        anotar("¡Adelante, " + jugador.getPokemonActivo().getNombre() + "!");
    }

    // ---------- consultas ----------

    public Entrenador getJugador() {
        return jugador;
    }

    public Entrenador getRival() {
        return rival;
    }

    public ListaHistorial getHistorial() {
        return historial;
    }

    public int getTurno() {
        return turno;
    }

    public boolean estaTerminada() {
        return terminada;
    }

    public boolean hayVictoria() {
        return rival.getEquipo().todosDebilitados();
    }

    public boolean hayDerrota() {
        return jugador.getEquipo().todosDebilitados();
    }

    public boolean debeCambiar() {
        if (terminada) {
            return false;
        }

        return jugador.getPokemonActivo().estaDebilitado();
    }

    public void atacar(Ataque ataque) {
        if (terminada) {
            return;
        }

        Pokemon atacante = jugador.getPokemonActivo();
        Pokemon defensor = rival.getPokemonActivo();

        golpear(atacante, ataque, defensor, true);
        ataquesRealizados++;

        if (revisarDebilitado(defensor, rival)) {
            return;
        }

        turnoDelRival();
    }

    public boolean usarObjeto(Objeto objeto, Pokemon objetivo) {
        if (terminada || objeto == null || objetivo == null) {
            return false;
        }

        if (!objeto.hayDisponibles()) {
            return false;
        }

        if (objeto.revive()) {
            if (!objetivo.estaDebilitado()) {
                return false;
            }

            objetivo.revivir();
            anotar(jugador.getNombre() + " usó " + objeto.getNombre() + " en " + objetivo.getNombre() + ".");
            anotar(objetivo.getNombre() + " volvió al combate con " + objetivo.getVidaActual() + " HP.");

        } else {
            if (objetivo.estaDebilitado()) {
                return false;
            }

            int recuperado = objetivo.curar(objeto.getPuntos());

            if (recuperado == 0) {
                return false;
            }

            anotar(jugador.getNombre() + " usó " + objeto.getNombre() + " en " + objetivo.getNombre() + ".");
            anotar(objetivo.getNombre() + " recuperó " + recuperado + " HP.");
        }

        objeto.descontarUno();
        objetosUsados++;

        turnoDelRival();
        return true;
    }

    public boolean cambiarPokemon(String nombre) {
        if (terminada) {
            return false;
        }

        Pokemon anterior = jugador.getPokemonActivo();

        if (!jugador.getEquipo().cambiarActivo(nombre)) {
            return false;
        }

        cambiosRealizados++;
        anotar(jugador.getNombre() + " retiró a " + anterior.getNombre() + ".");
        anotar("¡Adelante, " + jugador.getPokemonActivo().getNombre() + "!");

        turnoDelRival();
        return true;
    }

    public boolean cambiarPokemonForzado(String nombre) {
        if (!jugador.getEquipo().cambiarActivo(nombre)) {
            return false;
        }

        anotar("¡Adelante, " + jugador.getPokemonActivo().getNombre() + "!");
        return true;
    }

    private void turnoDelRival() {
        if (terminada) {
            return;
        }

        Pokemon atacante = rival.getPokemonActivo();
        Pokemon defensor = jugador.getPokemonActivo();
        Ataque ataque = atacante.obtenerAtaqueAleatorio(aleatorio);

        golpear(atacante, ataque, defensor, false);

        if (revisarDebilitado(defensor, jugador)) {
            return;
        }

        turno++;
    }

    private void golpear(Pokemon atacante, Ataque ataque, Pokemon defensor, boolean esDelJugador) {
        int dano = calcularDano(atacante, ataque, defensor);
        defensor.recibirDano(dano);

        if (esDelJugador) {
            danoCausado = danoCausado + dano;
        } else {
            danoRecibido = danoRecibido + dano;
        }

        anotar(atacante.getNombre() + " utilizó " + ataque.getNombre() + ".");

        String efecto = TablaTipos.describirEfecto(
                TablaTipos.multiplicador(ataque.getTipo(), defensor.getTipo()));

        if (!efecto.isEmpty()) {
            anotar(efecto);
        }

        anotar(defensor.getNombre() + " recibió " + dano + " puntos de daño.");
        anotar(defensor.getNombre() + ": " + defensor.getVidaActual() + "/" + defensor.getVidaMaxima() + " HP");
    }

    public int calcularDano(Pokemon atacante, Ataque ataque, Pokemon defensor) {
        int base = (ataque.getPoder() * atacante.getNivel()) / 25 + 2;
        double multiplicador = TablaTipos.multiplicador(ataque.getTipo(), defensor.getTipo());
        int variacion = aleatorio.nextInt(5) - 2;
        int dano = (int) (base * multiplicador) + variacion;

        if (dano < 1) {
            dano = 1;
        }

        return dano;
    }

    private boolean revisarDebilitado(Pokemon pokemon, Entrenador duenio) {
        if (!pokemon.estaDebilitado()) {
            return false;
        }

        anotar(pokemon.getNombre() + " fue derrotado.");

        if (duenio == rival) {
            if (hayVictoria()) {
                terminada = true;
                anotar("Ganaste el combate contra " + rival.getNombre() + ".");
                return true;
            }

            Pokemon reemplazo = rival.getEquipo().siguienteDisponible();
            rival.getEquipo().cambiarActivo(reemplazo.getNombre());
            anotar(rival.getNombre() + " envió a " + reemplazo.getNombre() + ".");
            return false;
        }

        if (hayDerrota()) {
            terminada = true;
            anotar("Te quedaste sin Pokémon. Perdiste el combate.");
        }

        return true;
    }

    private void anotar(String texto) {
        historial.insertar(turno, texto);
    }

    public String getEstadisticas() {
        String texto = "";
        texto = texto + "Turnos jugados: " + turno + "\n";
        texto = texto + "Ataques realizados: " + ataquesRealizados + "\n";
        texto = texto + "Daño causado: " + danoCausado + "\n";
        texto = texto + "Daño recibido: " + danoRecibido + "\n";
        texto = texto + "Objetos usados: " + objetosUsados + "\n";
        texto = texto + "Cambios de Pokémon: " + cambiosRealizados + "\n";
        texto = texto + "Pokémon disponibles: " + jugador.getEquipo().contarDisponibles()
                + " de " + jugador.getEquipo().contar();
        return texto;
    }
}
