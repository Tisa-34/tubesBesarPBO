import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.formdev.flatlaf.intellijthemes.FlatLightFlatIJTheme;

import controller.MasyarakatController;
import view.MasyarakatFrame;

public class EduCoreApp {
    public static void main(String[] args) throws Exception {
        try {
            UIManager.setLookAndFeel(new FlatLightFlatIJTheme());
        } catch (Exception ex) {
            System.err.println("Gagal mengatur tema flatlaf");
        }

        SwingUtilities.invokeLater(() -> {
            MasyarakatFrame frame = new MasyarakatFrame();
            new MasyarakatController(frame);
            frame.setVisible(true);
        });
    }
}
