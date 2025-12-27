package worker.masyarakat;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import api.MasyarakatApiClient;
import model.Masyarakat;
import view.MasyarakatFrame;

public class DeleteMasyarakatWorker extends SwingWorker<Void, Void> {
    private final MasyarakatFrame frame;
    private final MasyarakatApiClient masyarakatApiClient;
    private final Masyarakat masyarakat;

    public DeleteMasyarakatWorker (MasyarakatFrame frame, MasyarakatApiClient masyarakatApiClient, Masyarakat masyarakat){
        this.frame = frame;
        this.masyarakatApiClient = masyarakatApiClient;
        this.masyarakat = masyarakat;
        frame.getProgressBar().setIndeterminate(true);
        frame.getProgressBar().setString("Deleting masyarakat record...");
    }

    @Override
    protected Void doInBackground() throws Exception {
        masyarakatApiClient.delete(masyarakat.getId());
        return null;
    }

    @Override
    protected void done() {
        frame.getProgressBar().setIndeterminate(false);
        try {
            get();
            frame.getProgressBar().setString("Masyarakat deleted successfully");
            JOptionPane.showMessageDialog(frame,
                    "Masyarakat record has been deleted.",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            frame.getProgressBar().setString("Failed to delete masyarakat");
            JOptionPane.showMessageDialog(frame,
                    "Error deleting data: \n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
