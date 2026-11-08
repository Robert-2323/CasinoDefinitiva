
public class Ruleta {
    private int numeroGanador = -1;
    private boolean apuestaRealizada = false;
    private boolean resultadoListo = false;
    private int jugadoresListos = 0;
    private int totalJugadores;
    private Banca banca;

    public Ruleta(int totalJugadores, Banca banca) {
        this.totalJugadores = totalJugadores;
        this.banca = banca;
    }

    public synchronized void hacerApuestaYEsperar(Jugador jugador, String tipoApuesta, String detalleApuesta, double cantidad)  {
        try {
            while (!banca.isActiva() || apuestaRealizada) {
                wait();
            }

            if (!banca.isActiva()) return;
            System.out.println("Jugador " + jugador.getNombre() + " apuesta " + cantidad + " € a " + detalleApuesta);

            jugadoresListos++;


            if (jugadoresListos == totalJugadores) {
                apuestaRealizada = true;
                notifyAll();
            }

            while (!resultadoListo && banca.isActiva()) {
                wait();
            }
        }catch (InterruptedException e){
            System.out.println("ERROR: "+e.getMessage());
        }

    }

    public synchronized void girarRuleta() {
        // Esperar a que todos los jugadores realicen sus apuestas

        try {
            while (jugadoresListos < totalJugadores && banca.isActiva()) {
                wait();
            }

            if (!banca.isActiva()) return;


            numeroGanador = (int) (Math.random() * 37);
            System.out.println("\n>>> Nueva tirada: " + numeroGanador + " <<<");

            resultadoListo = true;
            notifyAll();
        }catch (InterruptedException e){
            System.out.println("ERROR: "+e.getMessage());
        }
    }

    public synchronized void finalizarTurno() {
        jugadoresListos--;
        if (jugadoresListos == 0) {
            apuestaRealizada = false;
            resultadoListo = false;
            numeroGanador = -1;
            notifyAll();
        }
    }

    public int getNumeroGanador() {
        return numeroGanador;
    }
}
