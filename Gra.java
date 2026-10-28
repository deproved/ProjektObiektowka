import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Gra {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
        }
        SwingUtilities.invokeLater(() -> {
            Plansza plansza = new Plansza(9, 9, 10);
            plansza.setVisible(true);
        });
    }
}
