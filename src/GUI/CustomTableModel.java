package GUI;

import javax.swing.table.*;
/**
 * TableModel can be selected but edited
 * @author Group 78
 *
 */
public class CustomTableModel extends DefaultTableModel {
	public CustomTableModel(Object[][] value, Object[] title) {
		super(value, title);
	}

	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
