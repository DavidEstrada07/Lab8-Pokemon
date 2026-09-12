package Pokemon;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class VentanaLogin extends JFrame {

    private ListaUsuarios usuarios;
    private Random aleatorio;

    private JTextField campoUsuario;
    private JPasswordField campoContrasena;

    public VentanaLogin() {
        aleatorio = new Random();
        usuarios = BancoDatos.crearUsuariosRivales(aleatorio);
        armar();
    }

    private void armar() {
        setTitle("Pokémon Battle");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        if (Imagenes.cargar("pokebola_32") != null) {
            setIconImage(Imagenes.cargar("pokebola_32"));
        }

        JLabel titulo = new JLabel("POKÉMON BATTLE", JLabel.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        titulo.setIcon(Imagenes.cargarIcono("pokebola_64", 48, 48));
        titulo.setBorder(BorderFactory.createEmptyBorder(18, 0, 10, 0));

        campoUsuario = new JTextField(14);
        campoContrasena = new JPasswordField(14);

        JPanel campos = new JPanel(new GridLayout(2, 2, 8, 8));
        campos.setBorder(BorderFactory.createEmptyBorder(0, 26, 10, 26));
        campos.add(new JLabel("Usuario:"));
        campos.add(campoUsuario);
        campos.add(new JLabel("Contraseña:"));
        campos.add(campoContrasena);

        JButton botonEntrar = new JButton("Iniciar sesión");
        botonEntrar.addActionListener(e -> iniciarSesion());

        JButton botonCrear = new JButton("Crear usuario");
        botonCrear.addActionListener(e -> crearUsuario());

        JButton botonAleatorio = new JButton("Entrenador aleatorio");
        botonAleatorio.addActionListener(e -> entrenadorAleatorio());

        JPanel botones = new JPanel(new GridLayout(3, 1, 0, 6));
        botones.setBorder(BorderFactory.createEmptyBorder(0, 26, 10, 26));
        botones.add(botonEntrar);
        botones.add(botonCrear);
        botones.add(botonAleatorio);

        JLabel ayuda = new JLabel("<html><center>Entrenadores cargados: Rojo, Azul, Verde, "
                + "Amarillo, Oro,<br>Plata, Cristal, Rubí, Zafiro, Esmeralda"
                + "<br>Contraseña de todos: 1234</center></html>", JLabel.CENTER);
        ayuda.setFont(new Font("SansSerif", Font.PLAIN, 11));
        ayuda.setBorder(BorderFactory.createEmptyBorder(0, 10, 14, 10));

        JPanel centro = new JPanel(new BorderLayout());
        centro.add(campos, BorderLayout.NORTH);
        centro.add(botones, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(titulo, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
        add(ayuda, BorderLayout.SOUTH);

        setSize(370, 380);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void iniciarSesion() {
        String nombre = campoUsuario.getText().trim();
        String contrasena = new String(campoContrasena.getPassword());

        if (nombre.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Escribe usuario y contraseña");
            return;
        }

        Usuario usuario = usuarios.buscar(nombre);

        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "Ese usuario no existe");
            return;
        }

        if (!usuario.contrasenaCorrecta(contrasena)) {
            JOptionPane.showMessageDialog(this, "Contraseña incorrecta");
            return;
        }

        continuar(usuario);
    }

    private void crearUsuario() {
        String nombre = campoUsuario.getText().trim();
        String contrasena = new String(campoContrasena.getPassword());

        try {
            Usuario usuario = new Usuario(nombre, contrasena);

            if (!usuarios.insertar(usuario)) {
                JOptionPane.showMessageDialog(this, "Ese nombre de usuario ya existe");
                return;
            }

            BancoDatos.llenarEquipoAleatorio(usuario.getEntrenador(), aleatorio);
            JOptionPane.showMessageDialog(this, "Usuario creado. Se te asignó un equipo inicial "
                    + "que puedes modificar antes de pelear.");
            continuar(usuario);

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void entrenadorAleatorio() {
        Usuario usuario = usuarios.obtenerAleatorio(aleatorio);
        JOptionPane.showMessageDialog(this, "Jugarás como " + usuario.getNombre());
        continuar(usuario);
    }

    private void continuar(Usuario usuario) {
        BancoDatos.llenarInventarioInicial(usuario.getEntrenador());

        VentanaEquipo ventanaEquipo = new VentanaEquipo(this, usuario.getEntrenador(), false);
        ventanaEquipo.setVisible(true);

        VentanaBatalla ventanaBatalla = new VentanaBatalla(usuario, usuarios, aleatorio);
        ventanaBatalla.setVisible(true);
        dispose();
    }
}
