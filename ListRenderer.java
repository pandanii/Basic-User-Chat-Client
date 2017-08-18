import java.awt.*;
import javax.swing.*;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;

public class ListRenderer extends DefaultListCellRenderer
{
@Override
public Component getListCellRendererComponent(JList jl, Object o, int index, boolean isSel, boolean hasFocus)
	{
	setOpaque(true);
	Color background = Color.RED;
	Color foreground = Color.BLACK;
	if(o instanceof JLabel)
		{
		this.setText(((JLabel)o).getText());
		this.setIcon(((JLabel)o).getIcon());
		if(isSel)
			{
			background = Color.LIGHT_GRAY;
			foreground = Color.MAGENTA;
			}
		else
			{
			background = Color.RED;
			foreground = Color.BLACK;
			}
		this.setBackground(background);
		this.setForeground(foreground);
		}
	return this;
	}

}