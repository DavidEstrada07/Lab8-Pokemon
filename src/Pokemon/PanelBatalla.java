package Pokemon;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class PanelBatalla extends JPanel {

    public static final int ANCHO = 800;
    public static final int ALTO = 476;

    private static final int RIVAL_X = 529;
    private static final int RIVAL_Y = 152;
    private static final int RIVAL_TAMANIO = 150;

    private static final int JUGADOR_X = 135;
    private static final int JUGADOR_Y = 222;
    private static final int JUGADOR_TAMANIO = 210;

    private Batalla batalla;
    private Image fondo;

    public PanelBatalla(Batalla batalla) {
        this.batalla = batalla;
        fondo = Imagenes.cargar("fondo_batalla");
        setPreferredSize(new java.awt.Dimension(ANCHO, ALTO));
    }

    public void setBatalla(Batalla batalla) {
        this.batalla = batalla;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        dibujarFondo(g2);

        Pokemon rival = batalla.getRival().getPokemonActivo();
        Pokemon jugador = batalla.getJugador().getPokemonActivo();

        dibujarPokemon(g2, rival, RIVAL_X, RIVAL_Y, RIVAL_TAMANIO);
        dibujarPokemon(g2, jugador, JUGADOR_X, JUGADOR_Y, JUGADOR_TAMANIO);

        dibujarRecuadro(g2, rival, 20, 20, batalla.getRival().getNombre());
        dibujarRecuadro(g2, jugador, 480, 360, batalla.getJugador().getNombre());
    }

    private void dibujarFondo(Graphics2D g2) {
        if (fondo != null) {
            g2.drawImage(fondo, 0, 0, ANCHO, ALTO, this);
        } else {
            g2.setColor(new Color(120, 190, 120));
            g2.fillRect(0, 0, ANCHO, ALTO);
        }
    }

    private void dibujarPokemon(Graphics2D g2, Pokemon pokemon, int x, int y, int tamanio) {
        if (pokemon == null) {
            return;
        }

        Image imagen = Imagenes.cargar(pokemon.getImagen());

        if (imagen == null) {
            g2.setColor(new Color(0, 0, 0, 60));
            g2.fillRoundRect(x, y, tamanio, tamanio, 20, 20);
            return;
        }

        g2.drawImage(imagen, x, y, tamanio, tamanio, this);
    }

    private void dibujarRecuadro(Graphics2D g2, Pokemon pokemon, int x, int y, String entrenador) {
        if (pokemon == null) {
            return;
        }

        int ancho = 300;
        int alto = 86;

        g2.setColor(new Color(250, 250, 240, 230));
        g2.fillRoundRect(x, y, ancho, alto, 16, 16);
        g2.setColor(new Color(60, 60, 60));
        g2.drawRoundRect(x, y, ancho, alto, 16, 16);

        g2.setFont(new Font("SansSerif", Font.BOLD, 17));
        g2.drawString(pokemon.getNombre(), x + 14, y + 24);

        g2.setFont(new Font("SansSerif", Font.PLAIN, 13));
        g2.drawString("Nv " + pokemon.getNivel(), x + ancho - 60, y + 24);
        g2.drawString(entrenador + "  -  " + pokemon.getTipo(), x + 14, y + 42);

        dibujarBarraVida(g2, pokemon, x + 14, y + 52, ancho - 28);
    }

    private void dibujarBarraVida(Graphics2D g2, Pokemon pokemon, int x, int y, int ancho) {
        int alto = 12;

        g2.setColor(new Color(70, 70, 70));
        g2.fillRoundRect(x, y, ancho, alto, 8, 8);

        double proporcion = (double) pokemon.getVidaActual() / pokemon.getVidaMaxima();
        int relleno = (int) (ancho * proporcion);

        g2.setColor(colorDeVida(proporcion));
        g2.fillRoundRect(x, y, relleno, alto, 8, 8);

        g2.setColor(Color.DARK_GRAY);
        g2.setFont(new Font("SansSerif", Font.BOLD, 12));
        g2.drawString(pokemon.getVidaActual() + " / " + pokemon.getVidaMaxima(), x, y + 26);

        if (pokemon.estaDebilitado()) {
            g2.setColor(new Color(180, 40, 40));
            g2.drawString("DERROTADO", x + ancho - 90, y + 26);
        }
    }

    private Color colorDeVida(double proporcion) {
        if (proporcion > 0.5) {
            return new Color(70, 180, 80);
        }

        if (proporcion > 0.2) {
            return new Color(230, 180, 50);
        }

        return new Color(200, 60, 60);
    }
}
