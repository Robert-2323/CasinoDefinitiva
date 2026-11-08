public abstract class Jugador extends Thread {
    protected String nombre;
    protected double saldo;
    protected Ruleta ruleta;
    protected Banca banca;
    protected boolean activo;

    public Jugador(String nombre, Ruleta ruleta, Banca banca) {
        this.nombre = nombre;
        this.saldo = 1000.0; // Saldo inicial requerido: 1.000 €
        this.ruleta = ruleta;
        this.banca = banca;
        this.activo = true;
    }

    public String getNombre() {
        return nombre;
    }

    public double getSaldo() {
        return saldo;
    }

    @Override
    public void run() {
        try {
            while (banca.isActiva() && saldo > 0 && activo) {
                ejecutarEstrategia();

                Thread.sleep(100);
            }
        } catch (InterruptedException e) {

        }
        System.out.println("-> El jugador " + nombre + " finaliza con un saldo de " + saldo + " €.");
    }

    protected abstract void ejecutarEstrategia() throws InterruptedException;
}
