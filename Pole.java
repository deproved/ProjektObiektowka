public class Pole {
    protected boolean odkryte;
    protected int liczbaMin;

    public Pole() {
        this.odkryte = false;
        this.liczbaMin = 0;
    }

    public boolean czyJestMina() {
        return false;
    }

    public void odkryj() {
        odkryte = true;
    }

    public boolean czyOdkryte() {
        return odkryte;
    }

    public int getLiczbaMin() {
        return liczbaMin;
    }

    public void setLiczbaMin(int liczbaMin) {
        this.liczbaMin = liczbaMin;
    }
}
