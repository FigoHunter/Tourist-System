package GUI;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import journey.*;
/**
 * Manipulate JList cell
 * @author Group 78
 *
 */
public class CustomCellRenderer extends JLabel implements ListCellRenderer {

	public CustomCellRenderer() {
		this.setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		Route route = (Route) value;
		setText(route.toString());
		this.setBackground(route.color.brighter());
		if (isSelected) {
			this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		} else {
			this.setBorder(BorderFactory.createEmptyBorder());
		}
		return this;
	}

}
