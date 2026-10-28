public class Pole {
    private boolean odkryte;
    private boolean flaga;
    private int sasiedniMiny;

    public boolean czyJestMina() {
        return false;
    }

    public void odkryj() {
        odkryte = true;
    }

    public boolean czyOdkryte() {
        return odkryte;
    }

    public boolean czyFlaga() {
        return flaga;
    }

    public void przelaczFlage() {
        if (!odkryte) {
            flaga = !flaga;
        }
    }

    public int getSasiedniMiny() {
        return sasiedniMiny;
    }

    public void setSasiedniMiny(int sasiedniMiny) {
        this.sasiedniMiny = sasiedniMiny;
    }
}
