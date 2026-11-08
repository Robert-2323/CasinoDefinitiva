import java.util.Random;

public class JugadorParImpar extends Jugador {
    private String tipoApuesta; // "PAR" o "IMPAR"
    private final double APUESTA = 10.0;

    public JugadorParImpar(String nombre, Ruleta ruleta, Banca banca) {
        super(nombre, ruleta, banca);
        Random rand = new Random();
        this.tipoApuesta = rand.nextBoolean() ? "PAR" : "IMPAR";
    }

    @Override
    protected void ejecutarEstrategia() throws InterruptedException {
        if (saldo < APUESTA) {
            activo = false;
            return;
        }

        ruleta.hacerApuestaYEsperar(this, "PARIMPAR", tipoApuesta, APUESTA);

        if (!banca.isActiva()) return;

        int ganador = ruleta.getNumeroGanador();
        boolean esPar = (ganador != 0 && ganador % 2 == 0);
        boolean esImpar = (ganador != 0 && ganador % 2 != 0);

        boolean ganado = false;
        if (tipoApuesta.equals("PAR") && esPar) {
            ganado = true;
        } else if (tipoApuesta.equals("IMPAR") && esImpar) {
            ganado = true;
        }

        if (ganado) {
            double premio = 20.0;
            if (banca.pagarPremio(premio - APUESTA)) {
                saldo += (premio - APUESTA);
                System.out.println("Jugador " + nombre + " gana (" + tipoApuesta + " con el " + ganador + "). Saldo actual: " + saldo + " €");
            }
        } else {
            saldo -= APUESTA;
            banca.cobrarApuesta(APUESTA);
            System.out.println("Jugador " + nombre + " pierde (" + tipoApuesta + " con el " + ganador + "). Saldo actual: " + saldo + " €");
        }

        ruleta.finalizarTurno();
    }
}
