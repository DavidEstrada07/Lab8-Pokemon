package Pokemon;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


public class VentanaObjetos extends JDialog {

    private Batalla batalla;
    private ListaObjetos inventario;
    private JRadioButton[] opciones;
    private boolean seUso;

    public VentanaObjetos(JFrame duenio, Batalla batalla) {
        super(duenio, "Objetos", true);
        this.batalla = batalla;
        inventario = batalla.getJugador().getInventario();
        seUso = false;
        armar();
    }

    private void armar() {
        JPanel lista = new JPanel(new GridLayout(inventario.contar(), 1, 0, 8));
        lista.setBorder(BorderFactory.createEmptyBorder(14, 18, 10, 18));

        ButtonGroup grupo = new ButtonGroup();
        opciones = new JRadioButton[inventario.contar()];

        for (int i = 0; i < inventario.contar(); i++) {
            Objeto objeto = inventario.obtener(i);
            opciones[i] = new JRadioButton(objeto.getNombre() + "  x" + objeto.getCantidad()
                    + "   -   " + objeto.getDescripcion());
            opciones[i].setFont(new Font("SansSerif", Font.PLAIN, 14));
            opciones[i].setIcon(Imagenes.cargarIcono(objeto.getImagen(), 28, 28));

            if (!objeto.hayDisponibles()) {
                opciones[i].setEnabled(false);
            }

            grupo.add(opciones[i]);
            lista.add(opciones[i]);
        }

        JLabel titulo = new JLabel("Inventario", JLabel.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        titulo.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));

        JButton botonUsar = new JButton("UTILIZAR");
        botonUsar.addActionListener(e -> utilizar());

        JButton botonCerrar = new JButton("CERRAR");
        botonCerrar.addActionListener(e -> dispose());

        JPanel abajo = new JPanel();
        abajo.add(botonUsar);
        abajo.add(botonCerrar);

        setLayout(new BorderLayout());
        add(titulo, BorderLayout.NORTH);
        add(lista, BorderLayout.CENTER);
        add(abajo, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(getOwner());
    }

    private void utilizar() {
        Objeto objeto = objetoSeleccionado();

        if (objeto == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un objeto disponible");
            return;
        }

        Pokemon objetivo = pedirObjetivo(objeto);

        if (objetivo == null) {
            return;
        }

        if (!batalla.usarObjeto(objeto, objetivo)) {
            JOptionPane.showMessageDialog(this, "No se puede usar " + objeto.getNombre()
                    + " en " + objetivo.getNombre());
            return;
        }

        seUso = true;
        dispose();
    }

    private Objeto objetoSeleccionado() {
        for (int i = 0; i < opciones.length; i++) {
            if (opciones[i].isSelected() && opciones[i].isEnabled()) {
                return inventario.obtener(i);
            }
        }

        return null;
    }


    private Pokemon pedirObjetivo(Objeto objeto) {
        ListaPokemon equipo = batalla.getJugador().getEquipo();
        int validos = 0;

        for (int i = 0; i < equipo.contar(); i++) {
            if (esObjetivoValido(objeto, equipo.obtener(i))) {
                validos++;
            }
        }

        if (validos == 0) {
            JOptionPane.showMessageDialog(this, "Ningún Pokémon puede recibir ese objeto");
            return null;
        }

        String[] nombres = new String[validos];
        int posicion = 0;

        for (int i = 0; i < equipo.contar(); i++) {
            Pokemon pokemon = equipo.obtener(i);

            if (esObjetivoValido(objeto, pokemon)) {
                nombres[posicion] = pokemon.getNombre() + "  ("
                        + pokemon.getVidaActual() + "/" + pokemon.getVidaMaxima() + ")";
                posicion++;
            }
        }

        Object respuesta = JOptionPane.showInputDialog(this,
                "Aplicar " + objeto.getNombre() + " a:", "Elegir Pokémon",
                JOptionPane.QUESTION_MESSAGE, null, nombres, nombres[0]);

        if (respuesta == null) {
            return null;
        }

        String texto = respuesta.toString();
        String nombre = texto.substring(0, texto.indexOf("  ("));
        return equipo.buscar(nombre);
    }

    private boolean esObjetivoValido(Objeto objeto, Pokemon pokemon) {
        if (objeto.revive()) {
            return pokemon.estaDebilitado();
        }

        return !pokemon.estaDebilitado() && pokemon.getVidaActual() < pokemon.getVidaMaxima();
    }

    public boolean seUsoUnObjeto() {
        return seUso;
    }
}
