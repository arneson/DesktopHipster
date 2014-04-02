package view;

import javax.swing.*;

import java.awt.GridLayout;
import java.awt.image.BufferedImage;

/**
 * Class to display an image on a JPanel.
 * 
 * @author Robin Sveningson
 *
 */
@SuppressWarnings("serial")
public class ThumbnailPanel extends JPanel {
	private ThumbnailCanvas canvas;
	
	public ThumbnailPanel(BufferedImage image) {
		super();
		initialize(image);
	}
	
	private void initialize(BufferedImage image) {
		canvas = new ThumbnailCanvas(image);
		canvas.setOpaque(true);
		
		setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		setBackground(java.awt.Color.gray);
		setLayout(new GridLayout(1,1));
		add(canvas);
	}
}
