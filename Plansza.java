import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Plansza extends JFrame {
    private static final int WIERSZE = 9;
    private static final int KOLUMNY = 9;
    private static final int LICZBA_MIN = 10;

    private Pole[][] pola;
    private JButton[][] przyciski;
    private JPanel panelPlanszy;
    private JLabel etykietaStatusu;
    private int odkrytePola;
    private boolean koniecGry;

    public Plansza() {
        setTitle("Saper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout(8, 8));

        etykietaStatusu = new JLabel("Odkryj pola i unikaj min!", SwingConstants.CENTER);
        etykietaStatusu.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        add(etykietaStatusu, BorderLayout.NORTH);

        panelPlanszy = new JPanel(new GridLayout(WIERSZE, KOLUMNY, 2, 2));
        panelPlanszy.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        add(panelPlanszy, BorderLayout.CENTER);

        JButton nowaGra = new JButton("Nowa gra");
        nowaGra.addActionListener(e -> rozpocznijNowaGre());

        JPanel panelDolny = new JPanel();
        panelDolny.add(nowaGra);
        add(panelDolny, BorderLayout.SOUTH);

        rozpocznijNowaGre();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void rozpocznijNowaGre() {
        pola = new Pole[WIERSZE][KOLUMNY];
        przyciski = new JButton[WIERSZE][KOLUMNY];
        odkrytePola = 0;
        koniecGry = false;

        panelPlanszy.removeAll();

        for (int w = 0; w < WIERSZE; w++) {
            for (int k = 0; k < KOLUMNY; k++) {
                pola[w][k] = new Pole();

                JButton przycisk = new JButton();
                przycisk.setPreferredSize(new Dimension(42, 42));
                przycisk.setFont(new Font("Arial", Font.BOLD, 16));
                przycisk.setFocusPainted(false);

                final int wiersz = w;
                final int kolumna = k;

                przycisk.addActionListener(e -> obsluzKlikniecie(wiersz, kolumna));

                przyciski[w][k] = przycisk;
                panelPlanszy.add(przycisk);
            }
        }

        rozmiescMiny();
        obliczLiczbeMin();

        etykietaStatusu.setText("Odkryj pola i unikaj min!");

        panelPlanszy.revalidate();
        panelPlanszy.repaint();
        pack();
    }

    private void rozmiescMiny() {
        Random random = new Random();
        int rozmieszczone = 0;

        while (rozmieszczone < LICZBA_MIN) {
            int w = random.nextInt(WIERSZE);
            int k = random.nextInt(KOLUMNY);

            if (!pola[w][k].czyJestMina()) {
                pola[w][k] = new PoleMina();
                rozmieszczone++;
            }
        }
    }

    private void obliczLiczbeMin() {
        for (int w = 0; w < WIERSZE; w++) {
            for (int k = 0; k < KOLUMNY; k++) {
                if (pola[w][k].czyJestMina()) {
                    continue;
                }

                int liczba = 0;

                for (int dw = -1; dw <= 1; dw++) {
                    for (int dk = -1; dk <= 1; dk++) {
                        if (dw == 0 && dk == 0) {
                            continue;
                        }

                        int nw = w + dw;
                        int nk = k + dk;

                        if (czyPoprawnePole(nw, nk) && pola[nw][nk].czyJestMina()) {
                            liczba++;
                        }
                    }
                }

                pola[w][k].setLiczbaMin(liczba);
            }
        }
    }

    private boolean czyPoprawnePole(int w, int k) {
        return w >= 0 && w < WIERSZE && k >= 0 && k < KOLUMNY;
    }

    private void obsluzKlikniecie(int w, int k) {
        if (koniecGry || pola[w][k].czyOdkryte()) {
            return;
        }

        if (pola[w][k].czyJestMina()) {
            pola[w][k].odkryj();
            przyciski[w][k].setText("M");
            przyciski[w][k].setBackground(Color.RED);
            koniecGry = true;

            odkryjWszystkieMiny();

            etykietaStatusu.setText("Koniec gry!");
            JOptionPane.showMessageDialog(
                this,
                "Niestety, trafiłeś na minę!",
                "Przegrana",
                JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

        odkryjPole(w, k);

        if (odkrytePola == WIERSZE * KOLUMNY - LICZBA_MIN) {
            koniecGry = true;
            etykietaStatusu.setText("Gratulacje! Wygrałeś!");

            JOptionPane.showMessageDialog(
                this,
                "Udało Ci się odkryć wszystkie pola bez min!",
                "Wygrana",
                JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            etykietaStatusu.setText(
                "Odkryte pola: " + odkrytePola + " / " + (WIERSZE * KOLUMNY - LICZBA_MIN)
            );
        }
    }

    private void odkryjPole(int w, int k) {
        if (!czyPoprawnePole(w, k) || koniecGry || pola[w][k].czyOdkryte()
                || pola[w][k].czyJestMina()) {
            return;
        }

        pola[w][k].odkryj();
        odkrytePola++;

        JButton przycisk = przyciski[w][k];
        przycisk.setEnabled(false);
        przycisk.setBackground(new Color(220, 220, 220));

        int liczba = pola[w][k].getLiczbaMin();

        if (liczba > 0) {
            przycisk.setText(String.valueOf(liczba));

            Color[] kolory = {
                Color.BLACK,
                Color.BLUE,
                new Color(0, 128, 0),
                Color.RED,
                new Color(0, 0, 128),
                new Color(128, 0, 0),
                new Color(0, 128, 128),
                Color.BLACK,
                Color.GRAY
            };

            przycisk.setForeground(kolory[liczba]);
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

    private void odkryjWszystkieMiny() {
        for (int w = 0; w < WIERSZE; w++) {
            for (int k = 0; k < KOLUMNY; k++) {
                if (pola[w][k].czyJestMina()) {
                    przyciski[w][k].setText("M");
                    przyciski[w][k].setBackground(Color.RED);
                    przyciski[w][k].setEnabled(false);
                } else if (!pola[w][k].czyOdkryte()) {
                    przyciski[w][k].setEnabled(false);
                }
            }
        }
    }
}
