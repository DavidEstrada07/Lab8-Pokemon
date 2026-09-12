package Pokemon;
public enum Tipo {

    FUEGO("Fuego"),
    AGUA("Agua"),
    PLANTA("Planta"),
    ELECTRICO("Eléctrico"),
    TIERRA("Tierra"),
    PSIQUICO("Psíquico"),
    FANTASMA("Fantasma"),
    LUCHA("Lucha");

    private String nombreVisible;

    Tipo(String nombreVisible) {
        this.nombreVisible = nombreVisible;
    }

    @Override
    public String toString() {
        return nombreVisible;
    }
}
