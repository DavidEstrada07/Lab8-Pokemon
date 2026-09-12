package Pokemon;

import java.awt.Image;
import java.io.File;
import java.net.URL;
import javax.swing.ImageIcon;

public class Imagenes {

    private static final String[] CARPETAS = {
            "imagenes",
            "src/imagenes",
            "../imagenes",
            "build/classes/imagenes",
            "out/production/imagenes"
    };

    private static final int MAX_GUARDADAS = 40;

    private static String[] nombresGuardados = new String[MAX_GUARDADAS];
    private static Image[] imagenesGuardadas = new Image[MAX_GUARDADAS];
    private static int guardadas = 0;
    private static boolean avisoMostrado = false;

    public static Image cargar(String nombre) {
        Image guardada = buscarEnMemoria(nombre);

        if (guardada != null) {
            return guardada;
        }

        Image imagen = leerDelDisco(nombre);

        if (imagen != null) {
            guardarEnMemoria(nombre, imagen);
        }

        return imagen;
    }

    public static ImageIcon cargarIcono(String nombre, int ancho, int alto) {
        Image imagen = cargar(nombre);

        if (imagen == null) {
            return null;
        }

        return new ImageIcon(imagen.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH));
    }

    private static Image leerDelDisco(String nombre) {
        URL recurso = Imagenes.class.getResource("/imagenes/" + nombre + ".png");

        if (recurso != null) {
            return new ImageIcon(recurso).getImage();
        }

        for (int i = 0; i < CARPETAS.length; i++) {
            File archivo = new File(CARPETAS[i], nombre + ".png");

            if (archivo.exists()) {
                return new ImageIcon(archivo.getPath()).getImage();
            }
        }

        avisar(nombre);
        return null;
    }

    private static void avisar(String nombre) {
        if (avisoMostrado) {
            return;
        }

        avisoMostrado = true;

        System.out.println("No se encontró la imagen: " + nombre + ".png");
        System.out.println("El programa se está ejecutando desde: " + new File(".").getAbsolutePath());
        System.out.println("Coloca la carpeta imagenes en alguna de estas rutas:");

        for (int i = 0; i < CARPETAS.length; i++) {
            System.out.println("   " + new File(CARPETAS[i]).getAbsolutePath());
        }
    }

    private static Image buscarEnMemoria(String nombre) {
        for (int i = 0; i < guardadas; i++) {
            if (nombresGuardados[i].equals(nombre)) {
                return imagenesGuardadas[i];
            }
        }

        return null;
    }

    private static void guardarEnMemoria(String nombre, Image imagen) {
        if (guardadas >= MAX_GUARDADAS) {
            return;
        }

        nombresGuardados[guardadas] = nombre;
        imagenesGuardadas[guardadas] = imagen;
        guardadas++;
    }
}
