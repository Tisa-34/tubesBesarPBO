package worker.masyarakat;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import api.MasyarakatApiClient;
import model.Masyarakat;
import view.MasyarakatFrame;

public class LoadMasyarakatWorker extends SwingWorker<List<Masyarakat>, Void> {
    private final MasyarakatFrame frame;
    private final MasyarakatApiClient masyarakatApiClient;

    public LoadMasyarakatWorker(MasyarakatFrame frame, MasyarakatApiClient masyarakatApiClient) {
        this.frame = frame;
        this.masyarakatApiClient = masyarakatApiClient;
        frame.getProgressBar().setIndeterminate(true);
        frame.getProgressBar().setString("Loading masyarakat data...");
    }

    @Override
    protected List<Masyarakat> doInBackground() throws Exception {
        return masyarakatApiClient.findAll();
    }

    @Override
    protected void done() {
        frame.getProgressBar().setIndeterminate(false);
        try {
            List<Masyarakat> result = get();
            frame.getProgressBar().setString(result.size() + " records loaded");
        } catch (Exception e) {
            frame.getProgressBar().setString("Failed to load data");
            JOptionPane.showMessageDialog(frame,
                    "Error loading data: \n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


}
