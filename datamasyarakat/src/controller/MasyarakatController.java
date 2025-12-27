package controller;

import java.util.ArrayList;
import java.util.List;
import java.awt.event.MouseAdapter;
//import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.awt.event.MouseEvent;


import api.MasyarakatApiClient;
import model.Masyarakat;
import view.MasyarakatDialog;
import view.MasyarakatFrame;
import worker.masyarakat.DeleteMasyarakatWorker;
import worker.masyarakat.LoadMasyarakatWorker;
import worker.masyarakat.SaveMasyarakatWorker;
import worker.masyarakat.UpdateMasyarakatWorker;

public class MasyarakatController {
    private final MasyarakatFrame frame;
    private final MasyarakatApiClient masyarakatApiClient = new MasyarakatApiClient();

    private List<Masyarakat> allMasyarakat = new ArrayList<>();
    private List<Masyarakat> displayedMasyarakat = new ArrayList<>();

    public MasyarakatController(MasyarakatFrame frame) {
        this.frame = frame;
        setupEventListeners();
        loadAllMasyarakat();
    }

    private void setupEventListeners() {
        frame.getAddButton().addActionListener(e -> openMasyarakatDialog(null));
        frame.getRefreshButton().addActionListener(e -> loadAllMasyarakat());
        frame.getDeleteButton().addActionListener(e -> deleteSelectedMasyarakat());
        frame.getMasyarakatTable().addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = frame.getMasyarakatTable().getSelectedRow();
                    if (selectedRow >= 0) {
                        openMasyarakatDialog(displayedMasyarakat.get(selectedRow));
                    }
                }
            }
        });
        frame.getSearchField().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                applySearchFilter();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                applySearchFilter();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                applySearchFilter();
            }
            private void applySearchFilter() {
                String keyword = frame.getSearchField().getText().toLowerCase().trim();
                displayedMasyarakat = new ArrayList<>();
                for (Masyarakat masyarakat : allMasyarakat) {
                    if (masyarakat.getNoKtp().toLowerCase().contains(keyword) ||
                            masyarakat.getNama().toLowerCase().contains(keyword) ||
                            (masyarakat.getAlamat() != null
                                    && masyarakat.getAlamat().toLowerCase().contains(keyword))) {
                        displayedMasyarakat.add(masyarakat);
                    }
                }
                frame.getMasyarakatTableModel().setMasyarakatList(displayedMasyarakat);
                updateTotalRecordsLabel();
            }
        });
    }

    private void openMasyarakatDialog(Masyarakat masyarakatToEdit) {
        MasyarakatDialog dialog;
        if (masyarakatToEdit == null) {
            dialog = new MasyarakatDialog(frame);
        } else {
            dialog = new MasyarakatDialog(frame, masyarakatToEdit);
        }
        dialog.getSaveButton().addActionListener(e -> {
            Masyarakat masyarakat = dialog.getMasyarakat();
            SwingWorker<Void, Void> worker;
            if (masyarakatToEdit == null) {
                worker = new SaveMasyarakatWorker(frame, masyarakatApiClient, masyarakat);
            } else {
                worker = new UpdateMasyarakatWorker(frame, masyarakatApiClient, masyarakat);
            }
            worker.addPropertyChangeListener(evt -> {
                if (SwingWorker.StateValue.DONE.equals(evt.getNewValue())) {
                    dialog.dispose();
                    loadAllMasyarakat();
                }
            });
            worker.execute();
        });
        dialog.setVisible(true);
    }

    private void deleteSelectedMasyarakat() {
        int selectedRow = frame.getMasyarakatTable().getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(frame, "Please select a record to delete.");
            return;
        }
        Masyarakat masyarakat = displayedMasyarakat.get(selectedRow);
        int confirm = JOptionPane.showConfirmDialog(frame,
                "Delete masyarakat: " + masyarakat.getNoKtp() + " - " + masyarakat.getNama() + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            DeleteMasyarakatWorker worker = new DeleteMasyarakatWorker(frame, masyarakatApiClient, masyarakat);
            worker.addPropertyChangeListener(evt -> {
                if (SwingWorker.StateValue.DONE.equals(evt.getNewValue())) {
                    loadAllMasyarakat();
                }
            });
            worker.execute();
        }
    }

    private void loadAllMasyarakat() {
        frame.getProgressBar().setIndeterminate(true);
        frame.getProgressBar().setString("Loading data...");
        LoadMasyarakatWorker worker = new LoadMasyarakatWorker(frame, masyarakatApiClient);
        worker.addPropertyChangeListener(evt -> {
            if (SwingWorker.StateValue.DONE.equals(evt.getNewValue())) {
                try {
                    allMasyarakat = worker.get();
                    displayedMasyarakat = new ArrayList<>(allMasyarakat);
                    frame.getMasyarakatTableModel().setMasyarakatList(displayedMasyarakat);
                    updateTotalRecordsLabel();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Failed to load data.");
                } finally {
                    frame.getProgressBar().setIndeterminate(false);
                    frame.getProgressBar().setString("Ready");
                }
            }
        });
        worker.execute();
    }

    private void updateTotalRecordsLabel() {
        frame.getTotalRecordsLabel().setText(displayedMasyarakat.size() + " Records");
    }
}
