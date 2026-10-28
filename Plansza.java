import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Plansza extends JFrame {
    private static final Color[] KOLORY = {
        Color.BLACK, new Color(0, 0, 200), new Color(0, 128, 0), new Color(200, 0, 0),
        new Color(0, 0, 100), new Color(128, 0, 0), new Color(0, 128, 128),
        Color.BLACK, Color.GRAY
    };

    private final int wiersze;
    private final int kolumny;
    private final int miny;
    private final Random losowy = new Random();
    private final JPanel panelPlanszy = new JPanel();
    private Pole[][] pola;
    private JButton[][] przyciski;
    private boolean koniec;
    private int odkryte;

    public Plansza(int wiersze, int kolumny, int miny) {
        this.wiersze = wiersze;
        this.kolumny = kolumny;
        this.miny = miny;

        setTitle("Saper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JButton nowaGra = new JButton("Nowa gra");
        nowaGra.addActionListener(e -> nowaGra());
        add(nowaGra, BorderLayout.NORTH);
        add(panelPlanszy, BorderLayout.CENTER);

        nowaGra();
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    private void nowaGra() {
        koniec = false;
        odkryte = 0;
        pola = new Pole[wiersze][kolumny];
        przyciski = new JButton[wiersze][kolumny];

        boolean[][] czyMina = new boolean[wiersze][kolumny];
        int rozmieszczone = 0;
        while (rozmieszczone < miny) {
            int w = losowy.nextInt(wiersze);
            int k = losowy.nextInt(kolumny);
            if (!czyMina[w][k]) {
                czyMina[w][k] = true;
                rozmieszczone++;
            }
        }

        for (int w = 0; w < wiersze; w++) {
            for (int k = 0; k < kolumny; k++) {
                pola[w][k] = czyMina[w][k] ? new PoleMina() : new Pole();
            }
        }

        for (int w = 0; w < wiersze; w++) {
            for (int k = 0; k < kolumny; k++) {
                pola[w][k].setSasiedniMiny(policzSasiednieMiny(w, k));
            }
        }

        panelPlanszy.removeAll();
        panelPlanszy.setLayout(new GridLayout(wiersze, kolumny));

        for (int w = 0; w < wiersze; w++) {
            for (int k = 0; k < kolumny; k++) {
                JButton przycisk = new JButton();
                przycisk.setPreferredSize(new Dimension(42, 42));
                przycisk.setMargin(new Insets(0, 0, 0, 0));
                przycisk.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
                przycisk.setFocusPainted(false);
                final int ww = w;
                final int kk = k;
                przycisk.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (SwingUtilities.isRightMouseButton(e)) {
                            przelaczFlage(ww, kk);
                        } else if (SwingUtilities.isLeftMouseButton(e)) {
                            kliknieciePola(ww, kk);
                        }
                    }
                });
                przyciski[w][k] = przycisk;
                panelPlanszy.add(przycisk);
            }
        }

        panelPlanszy.revalidate();
        panelPlanszy.repaint();
    }

    private int policzSasiednieMiny(int w, int k) {
        int licznik = 0;
        for (int dw = -1; dw <= 1; dw++) {
            for (int dk = -1; dk <= 1; dk++) {
                int nw = w + dw;
                int nk = k + dk;
                if ((dw != 0 || dk != 0) && wPlanszy(nw, nk) && pola[nw][nk].czyJestMina()) {
                    licznik++;
                }
            }
        }
        return licznik;
    }

    private boolean wPlanszy(int w, int k) {
        return w >= 0 && w < wiersze && k >= 0 && k < kolumny;
    }

    private void przelaczFlage(int w, int k) {
        if (koniec || pola[w][k].czyOdkryte()) {
            return;
        }
        pola[w][k].przelaczFlage();
        przyciski[w][k].setText(pola[w][k].czyFlaga() ? "F" : "");
        przyciski[w][k].setForeground(Color.RED);
    }

    private void kliknieciePola(int w, int k) {
        if (koniec || pola[w][k].czyOdkryte() || pola[w][k].czyFlaga()) {
            return;
        }

        if (pola[w][k].czyJestMina()) {
            koniec = true;
            pokazWszystkieMiny(w, k);
            JOptionPane.showMessageDialog(this, "Trafiłeś na minę. Przegrana!", "Koniec gry", JOptionPane.ERROR_MESSAGE);
            return;
        }

        odkryjPole(w, k);

        if (odkryte == wiersze * kolumny - miny) {
            koniec = true;
            JOptionPane.showMessageDialog(this, "Odkryłeś wszystkie pola bez min. Wygrana!", "Koniec gry", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void odkryjPole(int w, int k) {
        if (!wPlanszy(w, k)) {
            return;
        }
        Pole pole = pola[w][k];
        if (pole.czyOdkryte() || pole.czyFlaga() || pole.czyJestMina()) {
            return;
        }

        pole.odkryj();
        odkryte++;

        JButton przycisk = przyciski[w][k];
        przycisk.setBackground(new Color(225, 225, 225));
        przycisk.setOpaque(true);
        int liczba = pole.getSasiedniMiny();
        if (liczba > 0) {
            przycisk.setText(String.valueOf(liczba));
            przycisk.setForeground(KOLORY[liczba]);
        } else {
            przycisk.setText("");
            for (int dw = -1; dw <= 1; dw++) {
                for (int dk = -1; dk <= 1; dk++) {
                    if (dw != 0 || dk != 0) {
                        odkryjPole(w + dw, k + dk);
                    }
                }
            }
        }
    }

    private void pokazWszystkieMiny(int wybuchW, int wybuchK) {
        for (int w = 0; w < wiersze; w++) {
            for (int k = 0; k < kolumny; k++) {
                if (pola[w][k].czyJestMina()) {
                    przyciski[w][k].setText("*");
                    przyciski[w][k].setForeground(Color.BLACK);
                    przyciski[w][k].setOpaque(true);
                    przyciski[w][k].setBackground(w == wybuchW && k == wybuchK ? Color.RED : new Color(240, 170, 170));
                }
            }
        }
    }
}
