import java.util.Random;

public class JugadorMartingala extends Jugador {
    private int numeroApostado;
    private double apuestaActual;
    private final double APUESTA_INICIAL = 10.0;

    public JugadorMartingala(String nombre, Ruleta ruleta, Banca banca) {
        super(nombre, ruleta, banca);
        Random rand = new Random();
        this.numeroApostado = rand.nextInt(36) + 1;
        this.apuestaActual = APUESTA_INICIAL;
    }

    @Override
    protected void ejecutarEstrategia() throws InterruptedException {
        // Si el saldo no es suficiente para la apuesta actual de la martingala, se ajusta o finaliza
        if (saldo < apuestaActual) {
            apuestaActual = saldo; // Apuesta lo que le queda
            if (apuestaActual <= 0) {
                activo = false;
                return;
            }
        }

        ruleta.hacerApuestaYEsperar(this, "MARTINGALA", "el número " + numeroApostado + " (Martingala: " + apuestaActual + " €)", apuestaActual);

        if (!banca.isActiva()) return;

        int ganador = ruleta.getNumeroGanador();

        if (ganador == numeroApostado) {
            // Premio proporcional a la apuesta en martingala (35 a 1 -> premio total = apuestaActual * 36)
            double premioTotal = apuestaActual * 36.0;
            double beneficioNeto = premioTotal - apuestaActual;
            
            if (banca.pagarPremio(beneficioNeto)) {
                saldo += beneficioNeto;
                System.out.println("Jugador " + nombre + " GANA con Martingala (" + apuestaActual + " € apostados al " + ganador + "). Saldo actual: " + saldo + " €");
            }
            // Al ganar, se reinicia la martingala a la apuesta inicial
            apuestaActual = APUESTA_INICIAL;
        } else {
            saldo -= apuestaActual;
            banca.cobrarApuesta(apuestaActual);
            System.out.println("Jugador " + nombre + " PIERDE con Martingala (" + apuestaActual + " € al " + ganador + "). Saldo actual: " + saldo + " €");
            // Al perder, se duplica la apuesta para el siguiente turno
            apuestaActual *= 2;
        }

        ruleta.finalizarTurno();
    }
}
