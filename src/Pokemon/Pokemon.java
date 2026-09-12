package Pokemon;
import java.util.Random;

public class Pokemon {

    private String nombre;
    private Tipo tipo;
    private int nivel;
    private int vidaMaxima;
    private int vidaActual;
    private ListaAtaques ataques;

    public Pokemon(String nombre, Tipo tipo, int nivel, int vidaMaxima) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.nivel = nivel;
        this.vidaMaxima = vidaMaxima;
        vidaActual = vidaMaxima;
        ataques = new ListaAtaques();
    }

    public String getNombre() {
        return nombre;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        if (nivel < 1) {
            throw new IllegalArgumentException("El nivel debe ser mayor que cero");
        }

        this.nivel = nivel;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public void setVidaMaxima(int vidaMaxima) {
        if (vidaMaxima < 1) {
            throw new IllegalArgumentException("La vida máxima debe ser mayor que cero");
        }

        this.vidaMaxima = vidaMaxima;

        if (vidaActual > vidaMaxima) {
            vidaActual = vidaMaxima;
        }
    }

    public int getVidaActual() {
        return vidaActual;
    }

    public boolean estaDebilitado() {
        return vidaActual <= 0;
    }

    public void recibirDano(int dano) {
        vidaActual = vidaActual - dano;

        if (vidaActual < 0) {
            vidaActual = 0;
        }
    }

    public int curar(int puntos) {
        int antes = vidaActual;
        vidaActual = vidaActual + puntos;

        if (vidaActual > vidaMaxima) {
            vidaActual = vidaMaxima;
        }

        return vidaActual - antes;
    }

    public void revivir() {
        vidaActual = vidaMaxima / 2;
    }

    public void restaurar() {
        vidaActual = vidaMaxima;
    }

    public void agregarAtaque(Ataque ataque) {
        ataques.insertar(ataque);
    }

    public Ataque obtenerAtaque(int indice) {
        return ataques.obtener(indice);
    }

    public Ataque obtenerAtaqueAleatorio(Random aleatorio) {
        return ataques.obtenerAleatorio(aleatorio);
    }

    public int contarAtaques() {
        return ataques.contar();
    }

    public String getImagen() {
        return nombre.toLowerCase();
    }

    @Override
    public String toString() {
        return nombre + "  Nivel " + nivel + "  " + vidaActual + "/" + vidaMaxima + "  " + tipo;
    }
}
