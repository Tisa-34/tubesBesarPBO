package worker.masyarakat;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import api.MasyarakatApiClient;
import model.Masyarakat;
import view.MasyarakatFrame;

public class SaveMasyarakatWorker extends SwingWorker<Void, Void> {
    private final MasyarakatFrame frame;
    private final MasyarakatApiClient masyarakatApiClient;
    private final Masyarakat masyarakat;

    public SaveMasyarakatWorker(MasyarakatFrame frame, MasyarakatApiClient masyarakatApiClient, Masyarakat masyarakat) {
        this.frame = frame;
        this.masyarakatApiClient = masyarakatApiClient;
        this.masyarakat = masyarakat;
        frame.getProgressBar().setIndeterminate(true);
        frame.getProgressBar().setString("Saving new masyarakat...");
    }

    @Override
    protected Void doInBackground() throws Exception {
        masyarakatApiClient.create(masyarakat);
        return null;
    }

    @Override
    protected void done() {
        frame.getProgressBar().setIndeterminate(false);
        try {
            get(); // To catch any exception
            frame.getProgressBar().setString("Masyarakat saved successfully");
            JOptionPane.showMessageDialog(frame,
                    "New masyarakat record has been saved.",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            frame.getProgressBar().setString("Failed to save masyarakat");
            JOptionPane.showMessageDialog(frame,
                    "Error saving data: \n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
