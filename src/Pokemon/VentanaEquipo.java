package Pokemon;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class VentanaEquipo extends JDialog {

    private Entrenador entrenador;
    private ListaPokemon equipo;
    private JList<String> listaVisual;
    private JLabel etiquetaResumen;
    private boolean enBatalla;

    public VentanaEquipo(JFrame duenio, Entrenador entrenador, boolean enBatalla) {
        super(duenio, "Mi equipo", true);
        this.entrenador = entrenador;
        this.enBatalla = enBatalla;
        equipo = entrenador.getEquipo();
        armar();
        actualizar();
    }

    private void armar() {
        listaVisual = new JList<String>();
        listaVisual.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scroll = new JScrollPane(listaVisual);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 14, 6, 14));

        etiquetaResumen = new JLabel("", JLabel.CENTER);
        etiquetaResumen.setFont(new Font("SansSerif", Font.BOLD, 14));
        etiquetaResumen.setBorder(BorderFactory.createEmptyBorder(10, 0, 4, 0));

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 6));
        botones.setBorder(BorderFactory.createEmptyBorder(4, 14, 12, 14));

        JButton botonAgregar = new JButton("Agregar");
        botonAgregar.addActionListener(e -> agregar());

        JButton botonBuscar = new JButton("Buscar");
        botonBuscar.addActionListener(e -> buscar());

        JButton botonEliminar = new JButton("Eliminar");
        botonEliminar.addActionListener(e -> eliminar());

        JButton botonMover = new JButton("Mover al primer lugar");
        botonMover.addActionListener(e -> moverAlPrimero());

        JButton botonCerrar = new JButton(enBatalla ? "Cerrar" : "Iniciar batalla");
        botonCerrar.addActionListener(e -> dispose());

        botones.add(botonAgregar);
        botones.add(botonBuscar);
        botones.add(botonEliminar);
        botones.add(botonMover);
        botones.add(botonCerrar);

        setLayout(new BorderLayout());
        add(etiquetaResumen, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);

        setSize(760, 320);
        setLocationRelativeTo(getOwner());
    }

    private void actualizar() {
        String[] filas = new String[equipo.contar()];

        for (int i = 0; i < equipo.contar(); i++) {
            Pokemon pokemon = equipo.obtener(i);
            String estado;

            if (pokemon.estaDebilitado()) {
                estado = "DERROTADO";
            } else if (pokemon == equipo.obtenerActivo()) {
                estado = "EN COMBATE";
            } else {
                estado = "";
            }

            filas[i] = String.format("%-12s Nv %-4d %4d/%-4d  %-10s %s",
                    pokemon.getNombre(), pokemon.getNivel(), pokemon.getVidaActual(),
                    pokemon.getVidaMaxima(), pokemon.getTipo(), estado);
        }

        listaVisual.setListData(filas);
        etiquetaResumen.setText("Pokémon disponibles: " + equipo.contarDisponibles()
                + "  de  " + equipo.contar());
    }

    private void agregar() {
        if (enBatalla) {
            JOptionPane.showMessageDialog(this, "No se puede cambiar el equipo durante el combate");
            return;
        }

        if (equipo.estaLlena()) {
            JOptionPane.showMessageDialog(this,
                    "El equipo ya tiene " + ListaPokemon.MAX_POKEMON + " Pokémon");
            return;
        }

        Object respuesta = JOptionPane.showInputDialog(this, "Elige el Pokémon a agregar:",
                "Agregar Pokémon", JOptionPane.QUESTION_MESSAGE, null,
                BancoDatos.NOMBRES_POKEMON, BancoDatos.NOMBRES_POKEMON[0]);

        if (respuesta == null) {
            return;
        }

        Pokemon nuevo = BancoDatos.crearPokemon(respuesta.toString());

        if (!entrenador.agregarPokemon(nuevo)) {
            JOptionPane.showMessageDialog(this, "Ese Pokémon ya está en el equipo");
            return;
        }

        actualizar();
    }

    private void buscar() {
        String nombre = JOptionPane.showInputDialog(this, "Nombre del Pokémon:");

        if (nombre == null || nombre.trim().isEmpty()) {
            return;
        }

        Pokemon pokemon = equipo.buscar(nombre.trim());

        if (pokemon == null) {
            JOptionPane.showMessageDialog(this, "No está en el equipo");
            return;
        }

        String texto = pokemon.toString() + "\n\nAtaques:";

        for (int i = 0; i < pokemon.contarAtaques(); i++) {
            texto = texto + "\n   " + pokemon.obtenerAtaque(i);
        }

        JOptionPane.showMessageDialog(this, texto, "Pokémon encontrado",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void eliminar() {
        int indice = listaVisual.getSelectedIndex();

        if (indice < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un Pokémon de la lista");
            return;
        }

        if (enBatalla) {
            JOptionPane.showMessageDialog(this, "No se puede cambiar el equipo durante el combate");
            return;
        }

        if (equipo.contar() == 1) {
            JOptionPane.showMessageDialog(this, "El equipo no puede quedar vacío");
            return;
        }

        String nombre = equipo.obtener(indice).getNombre();
        int confirmar = JOptionPane.showConfirmDialog(this, "¿Eliminar a " + nombre + "?",
                "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            equipo.eliminar(nombre);
            actualizar();
        }
    }

    private void moverAlPrimero() {
        int indice = listaVisual.getSelectedIndex();

        if (indice < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un Pokémon de la lista");
            return;
        }

        equipo.moverAlPrimerLugar(equipo.obtener(indice).getNombre());
        actualizar();
        listaVisual.setSelectedIndex(0);
    }
}
