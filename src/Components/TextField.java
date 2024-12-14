package Components;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

import MovieTracker.Theme;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class TextField extends JTextField {

	private static final long serialVersionUID = 1L;
	
	private boolean isPlaceHolder = false;
	private String placeHolder = "";
	private Color originalColor = null;
	private Color placeHolderColor = null;
	Theme theme;

	/**
	 * Create the panel.
	 */
	public TextField() {
		theme = Theme.getInstance();
		originalColor = getForeground();

		TextField txt = this;
		setHorizontalAlignment(SwingConstants.CENTER);
		setMaximumSize(new Dimension(9999, 18));
		setOpaque(false);
		setCaretColor(theme.applicationFG);
		setForeground(theme.applicationFG);
		setBorder(null);
		setPreferredSize(new Dimension(16*11, 18));
//		setFont(new Font("Arial", Font.BOLD, 16));
		setColumns(10);

		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (!isPlaceHolder) return;
				txt.removePlaceHolder();
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (isPlaceHolder) return;
				if (!txt.getText().equals("")) return;
				txt.addPlaceHolder();
			}
		});
	}
	
	@Override
	public void setText(String t) {
		if (t == null) t = "";
		if (t == "") {
			isPlaceHolder = true;
			t = placeHolder;
			super.setForeground(placeHolderColor);
			this.transferFocus();
		} else if (isPlaceHolder == true) {
			isPlaceHolder = false;
			super.setForeground(originalColor);
		}
		super.setText(t);
	}

	@Override
	public void setForeground(Color fg) {
		originalColor = fg;
		super.setForeground(fg);
	}

	private void addPlaceHolder() {
		isPlaceHolder = true;
		super.setForeground(placeHolderColor);
		super.setText(placeHolder);
	}
	private void removePlaceHolder() {
		isPlaceHolder = false;
		super.setForeground(originalColor);
		super.setText("");
	}
	

	public String getPlaceHolder() { return placeHolder; }
	public void setPlaceHolder(String placeHolder) { this.placeHolder = placeHolder; }
	public Color getPlaceHolderColor() { return placeHolderColor; }
	public void setPlaceHolderColor(Color placeHolderColor) { this.placeHolderColor = placeHolderColor; }

}
