package Pokemon;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class VentanaBatalla extends JFrame {

    private Usuario usuario;
    private ListaUsuarios rivalesPosibles;
    private Random aleatorio;

    private Batalla batalla;
    private PanelBatalla panel;
    private JTextArea areaMensajes;

    private JButton botonAtacar;
    private JButton botonCambiar;
    private JButton botonObjetos;

    public VentanaBatalla(Usuario usuario, ListaUsuarios rivalesPosibles, Random aleatorio) {
        this.usuario = usuario;
        this.rivalesPosibles = rivalesPosibles;
        this.aleatorio = aleatorio;

        batalla = new Batalla(usuario.getEntrenador(), elegirRival());
        armar();
        actualizar();
    }

    private Entrenador elegirRival() {
        Usuario elegido = rivalesPosibles.obtenerAleatorio(aleatorio);

        while (rivalesPosibles.contar() > 1 && elegido.getNombre().equals(usuario.getNombre())) {
            elegido = rivalesPosibles.obtenerAleatorio(aleatorio);
        }

        return elegido.getEntrenador();
    }

    private void armar() {
        setTitle("Pokémon Battle  -  " + usuario.getNombre());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        if (Imagenes.cargar("pokebola_32") != null) {
            setIconImage(Imagenes.cargar("pokebola_32"));
        }

        panel = new PanelBatalla(batalla);

        areaMensajes = new JTextArea(5, 20);
        areaMensajes.setFont(new Font("Monospaced", Font.PLAIN, 13));
        areaMensajes.setEditable(false);

        JScrollPane scroll = new JScrollPane(areaMensajes);
        scroll.setBorder(BorderFactory.createTitledBorder("Historial"));

        JPanel abajo = new JPanel(new BorderLayout());
        abajo.add(scroll, BorderLayout.CENTER);
        abajo.add(crearBotones(), BorderLayout.SOUTH);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        add(abajo, BorderLayout.SOUTH);

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private JPanel crearBotones() {
        JPanel botones = new JPanel(new GridLayout(1, 6, 6, 0));
        botones.setBorder(BorderFactory.createEmptyBorder(8, 10, 10, 10));

        botonAtacar = new JButton("ATACAR");
        botonAtacar.addActionListener(e -> atacar());

        botonCambiar = new JButton("CAMBIAR");
        botonCambiar.addActionListener(e -> cambiar());

        botonObjetos = new JButton("OBJETOS");
        botonObjetos.addActionListener(e -> objetos());

        JButton botonEquipo = new JButton("MI EQUIPO");
        botonEquipo.addActionListener(e -> verEquipo());

        JButton botonHistorial = new JButton("HISTORIAL");
        botonHistorial.addActionListener(e -> verHistorial());

        JButton botonReiniciar = new JButton("REINICIAR");
        botonReiniciar.addActionListener(e -> reiniciar());

        botones.add(botonAtacar);
        botones.add(botonCambiar);
        botones.add(botonObjetos);
        botones.add(botonEquipo);
        botones.add(botonHistorial);
        botones.add(botonReiniciar);

        return botones;
    }

    private void atacar() {
        Pokemon activo = batalla.getJugador().getPokemonActivo();
        String[] nombres = new String[activo.contarAtaques()];

        for (int i = 0; i < activo.contarAtaques(); i++) {
            Ataque ataque = activo.obtenerAtaque(i);
            nombres[i] = ataque.getNombre() + "   (" + ataque.getTipo()
                    + ", poder " + ataque.getPoder() + ")";
        }

        Object respuesta = JOptionPane.showInputDialog(this,
                "Elige un ataque de " + activo.getNombre() + ":", "Atacar",
                JOptionPane.QUESTION_MESSAGE, null, nombres, nombres[0]);

        if (respuesta == null) {
            return;
        }

        for (int i = 0; i < nombres.length; i++) {
            if (nombres[i].equals(respuesta.toString())) {
                batalla.atacar(activo.obtenerAtaque(i));
                break;
            }
        }

        actualizar();
        revisarEstado();
    }

    private void cambiar() {
        if (batalla.getJugador().getEquipo().contarDisponibles() < 2) {
            JOptionPane.showMessageDialog(this, "No tienes otro Pokémon disponible");
            return;
        }

        VentanaCambio ventana = new VentanaCambio(this,
                batalla.getJugador().getEquipo(), false);
        ventana.setVisible(true);

        if (ventana.getElegido() == null) {
            return;
        }

        batalla.cambiarPokemon(ventana.getElegido());
        actualizar();
        revisarEstado();
    }

    private void objetos() {
        VentanaObjetos ventana = new VentanaObjetos(this, batalla);
        ventana.setVisible(true);

        if (!ventana.seUsoUnObjeto()) {
            return;
        }

        actualizar();
        revisarEstado();
    }

    private void verEquipo() {
        VentanaEquipo ventana = new VentanaEquipo(this, batalla.getJugador(), true);
        ventana.setVisible(true);
        actualizar();
    }

    private void verHistorial() {
        VentanaHistorial ventana = new VentanaHistorial(this, batalla);
        ventana.setVisible(true);
    }

    private void reiniciar() {
        int confirmar = JOptionPane.showConfirmDialog(this,
                "¿Reiniciar la partida con un rival nuevo?", "Reiniciar",
                JOptionPane.YES_NO_OPTION);

        if (confirmar != JOptionPane.YES_OPTION) {
            return;
        }

        batalla = new Batalla(usuario.getEntrenador(), elegirRival());
        panel.setBatalla(batalla);
        habilitarBotones(true);
        actualizar();
    }

    private void actualizar() {
        panel.repaint();
        areaMensajes.setText(batalla.getHistorial().ultimasLineas(6));
        areaMensajes.setCaretPosition(areaMensajes.getDocument().getLength());
    }

    private void revisarEstado() {
        if (batalla.estaTerminada()) {
            terminar();
            return;
        }

        if (batalla.debeCambiar()) {
            pedirCambioForzado();
        }
    }

    private void pedirCambioForzado() {
        JOptionPane.showMessageDialog(this,
                batalla.getJugador().getPokemonActivo().getNombre()
                        + " ya no puede continuar. Elige otro Pokémon.");

        while (batalla.debeCambiar()) {
            VentanaCambio ventana = new VentanaCambio(this,
                    batalla.getJugador().getEquipo(), true);
            ventana.setVisible(true);

            if (ventana.getElegido() != null) {
                batalla.cambiarPokemonForzado(ventana.getElegido());
            }
        }

        actualizar();
    }

    private void terminar() {
        habilitarBotones(false);

        String titulo;

        if (batalla.hayVictoria()) {
            titulo = "Ganaste el combate";
        } else {
            titulo = "Perdiste el combate";
        }

        JOptionPane.showMessageDialog(this, titulo + "\n\n" + batalla.getEstadisticas(),
                titulo, JOptionPane.INFORMATION_MESSAGE);

        int respuesta = JOptionPane.showConfirmDialog(this, "¿Quieres jugar otra vez?",
                "Fin del combate", JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {
            batalla = new Batalla(usuario.getEntrenador(), elegirRival());
            panel.setBatalla(batalla);
            habilitarBotones(true);
            actualizar();
        }
    }

    private void habilitarBotones(boolean activos) {
        botonAtacar.setEnabled(activos);
        botonCambiar.setEnabled(activos);
        botonObjetos.setEnabled(activos);
    }
}
