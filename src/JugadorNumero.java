import java.util.Random;

public class JugadorNumero extends Jugador {
    private int numeroApostado;
    private final double APUESTA = 10.0;

    public JugadorNumero(String nombre, Ruleta ruleta, Banca banca) {
        super(nombre, ruleta, banca);
        Random rand = new Random();
        this.numeroApostado = rand.nextInt(36) + 1;
    }

    @Override
    protected void ejecutarEstrategia() throws InterruptedException {
        if (saldo < APUESTA) {
            activo = false;
            return;
        }

        ruleta.hacerApuestaYEsperar(this, "NUMERO", "el número " + numeroApostado, APUESTA);

        if (!banca.isActiva()) return;

        int ganador = ruleta.getNumeroGanador();

        // Comprobar resultado
        if (ganador == numeroApostado) {
            double premio = 360.0;
            if (banca.pagarPremio(premio - APUESTA)) {
                saldo += (premio - APUESTA);
                System.out.println("Jugador " + nombre + " gana (acertó el " + ganador + "). Saldo actual: " + saldo + " €");
            }
        } else {
            saldo -= APUESTA;
            banca.cobrarApuesta(APUESTA);
            System.out.println("Jugador " + nombre + " pierde (salió el " + ganador + "). Saldo actual: " + saldo + " €");
        }

        ruleta.finalizarTurno();
    }
}
