package view;

import java.awt.Color;
import java.awt.Font;

public enum Constants {

	BACKGROUNDCOLOR(new Color(255, 255, 255)),
	BACKGROUNDCOLOR_1(new Color(245, 245, 245)),
	BACKGROUNDCOLOR_2(new Color(240, 240, 240)),
	TEXTCOLOR(new Color(150,150,150)),
	TEXTCOLORDARK(new Color(113,113,113)),
	ADDPANELBACKGROUND(new Color(250, 240, 200)),
	CONTRASTCOLOR(new Color(129, 238, 206)),//new Color(85,86,89)),
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
