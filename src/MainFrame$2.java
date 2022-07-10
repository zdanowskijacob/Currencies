import javax.swing.table.DefaultTableModel;

class MainFrame$2 extends DefaultTableModel {
    MainFrame$2(Object[][] arg0, Object[] arg1) {
        super(arg0, arg1);
    }

    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
