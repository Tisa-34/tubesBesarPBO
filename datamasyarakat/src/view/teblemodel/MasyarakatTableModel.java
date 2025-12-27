package view.teblemodel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.Masyarakat;

public class MasyarakatTableModel extends AbstractTableModel {
    private List<Masyarakat> masyarakatList = new ArrayList<>();
    private final String[] columnNames = { "ID", "NoKtp", "Nama", "Alamat" };

    public void setMasyarakatList(List<Masyarakat> masyarakatList) {
        this.masyarakatList = masyarakatList;
        fireTableDataChanged();
    }

    public Masyarakat getMasyarakatAt(int rowIndex) {
        return masyarakatList.get(rowIndex);
    }

    @Override
    public int getRowCount() {
        return masyarakatList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Masyarakat masyarakat = masyarakatList.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> masyarakat.getId();
            case 1 -> masyarakat.getNoKtp();
            case 2 -> masyarakat.getNama();
            case 3 -> masyarakat.getAlamat();
            default -> null;
        };
    }
}
