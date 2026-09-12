package Pokemon;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class VentanaHistorial extends JDialog {

    public VentanaHistorial(JFrame duenio, Batalla batalla) {
        super(duenio, "Historial de batalla", true);

        JTextArea area = new JTextArea();
        area.setFont(new Font("Monospaced", Font.PLAIN, 13));
        area.setEditable(false);
        area.setText(batalla.getHistorial().textoCompleto()
                + "\n--- Estadísticas ---\n" + batalla.getEstadisticas());
        area.setCaretPosition(0);

        JScrollPane scroll = new JScrollPane(area);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 6, 10));

        JButton botonCerrar = new JButton("Cerrar");
        botonCerrar.addActionListener(e -> dispose());

        JPanel abajo = new JPanel();
        abajo.add(botonCerrar);

        setLayout(new BorderLayout());
        add(scroll, BorderLayout.CENTER);
        add(abajo, BorderLayout.SOUTH);

        setSize(480, 520);
        setLocationRelativeTo(duenio);
    }
}
