
public class Banca {
    private double saldo;
    private boolean activa;

    public Banca(double saldoInicial) {
        this.saldo = saldoInicial;
        this.activa = true;
    }

    public synchronized double getSaldo() {
        return saldo;
    }

    public synchronized boolean isActiva() {
        return activa;
    }

    public synchronized void cobrarApuesta(double cantidad) {
        saldo += cantidad;
    }

    public synchronized boolean pagarPremio(double cantidad) {
        if (saldo >= cantidad) {
            saldo -= cantidad;
            return true;
        } else {
            saldo -= cantidad;
            activa = false;
            System.out.println("\nALERTA BANCA ¡La banca se ha quedado sin dinero! No puede pagar el premio de " + cantidad + " €.");
            return false;
        }
    }
}
