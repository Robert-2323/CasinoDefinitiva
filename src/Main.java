public class Main {
    public static void main(String[] args) {

        System.out.println("=== INICIANDO RULETA DE CASINO =====");

        Banca banca = new Banca(50000.0);

        // Total de jugadores: 12
        int totalJugadores = 12;


        Ruleta ruleta = new Ruleta(totalJugadores, banca);

        Jugador[] jugadores = new Jugador[totalJugadores];


        for (int i = 1; i <= 4; i++) {
            jugadores[i - 1] = new JugadorNumero("Numero_" + i, ruleta, banca);
        }


        for (int i = 1; i <= 4; i++) {
            jugadores[i + 3] = new JugadorParImpar("ParImpar_" + i, ruleta, banca);
        }


        for (int i = 1; i <= 4; i++) {
            jugadores[i + 7] = new JugadorMartingala("Martingala_" + i, ruleta, banca);
        }


        for (Jugador j : jugadores) {
            j.start();
        }

        //  realiza una tirada cada 3000 milisegundos
        int numeroTirada = 1;
        try {
            while (banca.isActiva()) {
                Thread.sleep(3000);

                System.out.println("\n--------------------------------------------------");
                System.out.println(">>> INICIANDO RONDA DE TIRADA Nº " + numeroTirada++ + " (Saldo Banca: " + banca.getSaldo() + " €) <<<");
                
                // Hacer girar la ruleta y despertar a los hilos esperando
                ruleta.girarRuleta();
            }
        } catch (InterruptedException e) {
            System.out.println("Simulación interrumpida.");
        }

        System.out.println("=== FIN DE LA SIMULACIÓN DEL CASINO =============");
        System.out.println("Saldo final de la banca: " + banca.getSaldo() + " €");

        for (Jugador j : jugadores) {
            j.interrupt();
        }
    }
}
