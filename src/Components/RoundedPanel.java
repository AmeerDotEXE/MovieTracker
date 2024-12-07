package Components;

import java.awt.Graphics;

import javax.swing.JPanel;

public class RoundedPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private int radius = 8;

	public RoundedPanel(int radius) {
		this.radius = radius;
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(getBackground());
		g.fillRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, radius, radius);
		g.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, radius, radius);
	}

}
