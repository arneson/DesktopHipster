package desktopHipster;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This class is used by the filter test class only. It is
 * made to containt the library logic aswell as display 
 * different images. 
 * 
 * @author Robin Sveningson
 *
 */
@SuppressWarnings("serial")
public class Canvas extends JPanel{
	private BufferedImage filter;
	private BufferedImage original;
	private boolean originalVisible = true;

	public Canvas() {
		setPreferredSize(new Dimension(400,400));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		if(originalVisible) {
			g.drawImage(original, 0, 0, null);
		} else {
			g.drawImage(filter, 0, 0, null);
		}
	}
	
	public void setOriginalVisible(boolean b) {
		originalVisible = b;
		revalidate();
		repaint();
	}
	
	public void setFilterImage(BufferedImage b) {
		filter = b;
		setOriginalVisible(false);
		revalidate();
		repaint();
	}
	
	public BufferedImage getFilterImage() {
	    return filter;
	}
	
	public void setOriginalImage(BufferedImage b) {
		original = b;
		revalidate();
		repaint();
	}
	
	public BufferedImage getOriginalImage() {
	    return original;
	}
	
}
