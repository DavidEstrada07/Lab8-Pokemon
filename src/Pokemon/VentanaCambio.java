package Pokemon;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


public class VentanaCambio extends JDialog {

    private ListaPokemon equipo;
    private JRadioButton[] opciones;
    private String elegido;

    public VentanaCambio(JDialog duenio, ListaPokemon equipo, boolean forzado) {
        super(duenio, "Seleccionar Pokémon", true);
        this.equipo = equipo;
        armar(forzado);
    }

    public VentanaCambio(javax.swing.JFrame duenio, ListaPokemon equipo, boolean forzado) {
        super(duenio, "Seleccionar Pokémon", true);
        this.equipo = equipo;
        armar(forzado);
    }

    private void armar(boolean forzado) {
        elegido = null;

        JPanel lista = new JPanel(new GridLayout(equipo.contar(), 1, 0, 6));
        lista.setBorder(BorderFactory.createEmptyBorder(14, 18, 10, 18));

        ButtonGroup grupo = new ButtonGroup();
        opciones = new JRadioButton[equipo.contar()];

        for (int i = 0; i < equipo.contar(); i++) {
            Pokemon pokemon = equipo.obtener(i);
            opciones[i] = new JRadioButton(textoDe(pokemon));
            opciones[i].setFont(new Font("SansSerif", Font.PLAIN, 14));
            opciones[i].setIcon(null);

            if (pokemon.estaDebilitado() || pokemon == equipo.obtenerActivo()) {
                opciones[i].setEnabled(false);
            }

            grupo.add(opciones[i]);
            lista.add(opciones[i]);
        }

        JLabel titulo = new JLabel("Elige el Pokémon que entra al combate", JLabel.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 15));
        titulo.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));

        JButton botonCambiar = new JButton("CAMBIAR");
        botonCambiar.addActionListener(e -> confirmar());

        JPanel abajo = new JPanel();
        abajo.add(botonCambiar);

        if (!forzado) {
            JButton botonCerrar = new JButton("CERRAR");
            botonCerrar.addActionListener(e -> dispose());
            abajo.add(botonCerrar);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        } else {
            setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }

        setLayout(new BorderLayout());
        add(titulo, BorderLayout.NORTH);
        add(lista, BorderLayout.CENTER);
        add(abajo, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(getOwner());
    }

    private String textoDe(Pokemon pokemon) {
        String texto = pokemon.getNombre() + "   Nv " + pokemon.getNivel()
                + "   " + pokemon.getVidaActual() + "/" + pokemon.getVidaMaxima();

        if (pokemon.estaDebilitado()) {
            texto = texto + "   DERROTADO";
        } else if (pokemon == equipo.obtenerActivo()) {
            texto = texto + "   (en combate)";
        }

        return texto;
    }

    private void confirmar() {
        for (int i = 0; i < opciones.length; i++) {
            if (opciones[i].isSelected()) {
                elegido = equipo.obtener(i).getNombre();
                dispose();
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Selecciona un Pokémon disponible");
    }

    public String getElegido() {
        return elegido;
    }
}

