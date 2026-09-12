package Pokemon;

public class ListaHistorial {

    private NodoRegistro cabeza;
    private NodoRegistro cola;
    private int cantidad;

    public ListaHistorial() {
        cabeza = null;
        cola = null;
        cantidad = 0;
    }

    public void insertar(int turno, String texto) {
        NodoRegistro nuevo = new NodoRegistro(new Registro(turno, texto));

        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            cola.setSiguiente(nuevo);
        }

        cola = nuevo;
        cantidad++;
    }

    public Registro obtener(int indice) {
        if (indice < 0 || indice >= cantidad) {
            return null;
        }

        NodoRegistro actual = cabeza;

        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }

        return actual.getRegistro();
    }

    public int contar() {
        return cantidad;
    }

    public void limpiar() {
        cabeza = null;
        cola = null;
        cantidad = 0;
    }

    public String ultimasLineas(int cuantas) {
        int desde = cantidad - cuantas;

        if (desde < 0) {
            desde = 0;
        }

        String texto = "";

        for (int i = desde; i < cantidad; i++) {
            texto = texto + obtener(i).getTexto() + "\n";
        }

        return texto;
    }

    public String textoCompleto() {
        String texto = "";
        int turnoAnterior = -1;

        for (int i = 0; i < cantidad; i++) {
            Registro registro = obtener(i);

            if (registro.getTurno() != turnoAnterior) {
                if (turnoAnterior != -1) {
                    texto = texto + "\n";
                }

                texto = texto + "Turno " + registro.getTurno() + "\n";
                turnoAnterior = registro.getTurno();
            }

            texto = texto + "   " + registro.getTexto() + "\n";
        }

        return texto;
    }
}