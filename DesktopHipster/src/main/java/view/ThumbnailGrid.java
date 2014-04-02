package view;

import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.*;

import General.PropertyNames;

/**
 * This class displays all the images in the library
 * as thumnails in a grid.
 * 
 * @author Robin Sveningson
 *
 */
@SuppressWarnings("serial")
public class ThumbnailGrid extends JScrollPane implements PropertyChangeListener {
	
	private JPanel content;
	private List<BufferedImage> images;
	
	public ThumbnailGrid() {
		super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		initialize();
	}
	
	public void initialize() {
		content = new JPanel();
		
		setBorder(null);
		setViewportView(content);
	}
	
	private void updateGrid() {
		int size = images.size();
		final int numberOfColumns = 10;
		int width = (getWidth()-2) / numberOfColumns;
		
		if(width > 500) {
			System.out.println("");
		}
		
		content.removeAll();
		content.setLayout(new GridLayout(
				(int)Math.ceil(size/numberOfColumns), 
				numberOfColumns));
		for(int i = 0; i < size; i++) {
			content.add(new ThumbnailPanel(images.get(i), width));
		}
		content.revalidate();
		content.repaint();
	}
	
	public void setThumbnails(List<BufferedImage> images) {
		this.images = images;
		updateGrid();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals(PropertyNames.MAIN_FRAME_RESIZE)) {
			updateGrid();
		}
	}
}
