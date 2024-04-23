package presentation;

import javax.swing.table.AbstractTableModel;

public class TableModele extends AbstractTableModel {

    private static final int ROWS = 4;
    private static final int COLS = 4;

    private String[][] data;

    public TableModele() {
        data = new String[ROWS][COLS];
        // Initialize the data with empty strings
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                data[row][col] = "";
            }
        }
    }

    @Override
    public int getRowCount() {
        return ROWS;
    }

    @Override
    public int getColumnCount() {
        return COLS;
    }

    @Override
    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        try {
            String strValue = (String) value;
            data[row][col] = strValue;
            fireTableCellUpdated(row, col);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Value must be a String", e);
        }
    }


    @Override
    public boolean isCellEditable(int row, int col) {
        // Make cells non-editable
        return false;
    }
}
