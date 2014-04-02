package view;

import javax.swing.*;

import java.awt.Dimension;
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
	private final int borderSize = 2;
	
	public ThumbnailPanel(BufferedImage image, int width) {
		super();
		initialize(image, width);
	}
	
	private void initialize(final BufferedImage image, int width) {
		JLabel canvas = new JLabel(new ImageIcon(image));
		
		width -= borderSize * 2;
		
		setPreferredSize(new Dimension(width, getScaledHeight(image, width)));
		setBorder(BorderFactory.createEmptyBorder(borderSize, borderSize, borderSize, borderSize));
		setBackground(java.awt.Color.gray);
		setLayout(new GridLayout(1,1));
		add(canvas);
	}
	
	private int getScaledHeight(BufferedImage image, int width) {
		double widthHeightRatio = image.getWidth() / image.getHeight();
		double height = width / widthHeightRatio;
		return (int)Math.round(height);
	}
}
