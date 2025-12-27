package view;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import model.Masyarakat;
import net.miginfocom.swing.MigLayout;

public class MasyarakatDialog extends JDialog {
    private final JTextField noKtpField = new JTextField(255);
    private final JTextField namaField = new JTextField(255);
    private final JTextField alamatField = new JTextField(255);
    private final JButton saveButton = new JButton("Save");
    private final JButton cancelButton = new JButton("Cancel");

    private Masyarakat masyarakat;

    public MasyarakatDialog(JFrame owner) {
        super(owner, "Add New Masyarakat", true);
        this.masyarakat = new Masyarakat();
        setupComponents();
    }

    public MasyarakatDialog(JFrame owner, Masyarakat masyarakatToEdit) {
        super(owner, "Edit Masyarakat", true);
        this.masyarakat = masyarakatToEdit;
        setupComponents();
        
        noKtpField.setText(masyarakatToEdit.getNoKtp());
        namaField.setText(masyarakatToEdit.getNama());
        alamatField.setText(masyarakatToEdit.getAlamat());
    }

    private void setupComponents() {
        setLayout(new MigLayout("fill, insets 30", "[right]20[grow]"));
        add(new JLabel("NoKtp"), "");
        add(noKtpField, "growx, wrap");
        add(new JLabel("Nama Lengkap"), "");
        add(namaField, "growx, wrap");
        add(new JLabel("Alamat"), "");
        add(alamatField, "growx, wrap");

        saveButton.setBackground(UIManager.getColor("Button.default.background"));
        saveButton.setForeground(UIManager.getColor("Button.default.foreground"));
        saveButton.setFont(saveButton.getFont().deriveFont(Font.BOLD));

        JPanel buttonPanel = new JPanel(new MigLayout("", "[]10[]"));
        cancelButton.addActionListener(e -> dispose());
        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);
        add(buttonPanel, "span, right");

        pack();
        setMinimumSize(new Dimension(500, 400));
        setLocationRelativeTo(getOwner());
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public Masyarakat getMasyarakat() {
        masyarakat.setNim(noKtpField.getText().trim());
        masyarakat.setNama(namaField.getText().trim());
        masyarakat.setAlamat(alamatField.getText().trim());
        return masyarakat;
    }
}
