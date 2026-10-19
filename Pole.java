public class Pole {
    private boolean odkryte;
    private int liczbaSasiednichMin;

    public Pole() {
        this.odkryte = false;
        this.liczbaSasiednichMin = 0;
    }

    public boolean czyJestMina() {
        return false;
    }

    public boolean czyOdkryte() {
        return odkryte;
    }

    public void odkryj() {
        odkryte = true;
    }

    public int getLiczbaSasiednichMin() {
        return liczbaSasiednichMin;
    }

    public void setLiczbaSasiednichMin(int liczba) {
        if (liczba >= 0 && liczba <= 8) {
            liczbaSasiednichMin = liczba;
        }
    }
}
