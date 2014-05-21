package view;

import java.awt.Color;
import java.awt.Font;

public enum Constants {

	BACKGROUNDCOLOR(new Color(255, 255, 255)), 
	TEXTCOLOR(new Color(104, 106, 108)),
	ADDPANELBACKGROUND(new Color(250, 240, 200)),
	CONTRASTCOLOR(new Color(129, 238, 206)),
	CONTRASTCOLORLIGHT(new Color(186, 245, 228));

	private Color color;
	private Font font;

	Constants(Color color) {
		this.color = color;
	}

	Constants(Font font) {
		this.font = font;
	}

	public Color getColor() {
		return color;
	}

	public Font getFont() {
		return font;
	}

}
