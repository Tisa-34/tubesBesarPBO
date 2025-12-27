package worker.masyarakat;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import api.MasyarakatApiClient;
import model.Masyarakat;
import view.MasyarakatFrame;

public class UpdateMasyarakatWorker extends SwingWorker<Void, Void> {
    private final MasyarakatFrame frame;
    private final MasyarakatApiClient masyarakatApiClient;
    private final Masyarakat masyarakat;

    public UpdateMasyarakatWorker(MasyarakatFrame frame, MasyarakatApiClient masyarakatApiClient, Masyarakat masyarakat) {
        this.frame = frame;
        this.masyarakatApiClient = masyarakatApiClient;
        this.masyarakat = masyarakat;
        frame.getProgressBar().setIndeterminate(true);
        frame.getProgressBar().setString("Updating masyarakat data...");
    }

    @Override
    protected Void doInBackground() throws Exception {
        masyarakatApiClient.update(masyarakat);
        return null;
    }

    @Override
    protected void done() {
        frame.getProgressBar().setIndeterminate(false);
        try {
            get();
            frame.getProgressBar().setString("Masyarakat updated successfully");
            JOptionPane.showMessageDialog(frame,
                    "Masyarakat record has been updated.",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            frame.getProgressBar().setString("Failed to update masyarakat");
            JOptionPane.showMessageDialog(frame,
                    "Error updating data: \n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
