package Pokemon;
public class TablaTipos {

    public static boolean esFuerte(Tipo atacante, Tipo defensor) {
        if (atacante == Tipo.FUEGO) {
            return defensor == Tipo.PLANTA;
        }

        if (atacante == Tipo.AGUA) {
            return defensor == Tipo.FUEGO || defensor == Tipo.TIERRA;
        }

        if (atacante == Tipo.PLANTA) {
            return defensor == Tipo.AGUA || defensor == Tipo.TIERRA;
        }

        if (atacante == Tipo.ELECTRICO) {
            return defensor == Tipo.AGUA;
        }

        if (atacante == Tipo.TIERRA) {
            return defensor == Tipo.FUEGO || defensor == Tipo.ELECTRICO;
        }

        if (atacante == Tipo.PSIQUICO) {
            return defensor == Tipo.LUCHA;
        }

        if (atacante == Tipo.FANTASMA) {
            return defensor == Tipo.PSIQUICO;
        }

        if (atacante == Tipo.LUCHA) {
            return defensor == Tipo.TIERRA;
        }

        return false;
    }

    public static double multiplicador(Tipo atacante, Tipo defensor) {
        if (esFuerte(atacante, defensor)) {
            return 2.0;
        }

        if (esFuerte(defensor, atacante)) {
            return 0.5;
        }

        return 1.0;
    }

    public static String describirEfecto(double multiplicador) {
        if (multiplicador > 1.0) {
            return "Es muy eficaz.";
        }

        if (multiplicador < 1.0) {
            return "No es muy eficaz.";
        }

        return "";
    }
}
