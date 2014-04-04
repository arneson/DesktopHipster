package view;

import java.awt.BorderLayout;
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
	private JPanel content, wrapper;
	private List<BufferedImage> images;
	
	public ThumbnailGrid() {
		super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		initialize();
	}
	
	public void initialize() {
		content = new JPanel();
		wrapper = new JPanel();
		
		content.setLayout(new BorderLayout());
		content.add(wrapper, BorderLayout.NORTH);
		
		setBorder(null);
		setViewportView(content);
	}
	
	private void updateGrid() {
		int size = images.size();
		final int numberOfColumns = 3;
		int side = (getWidth()-2) / numberOfColumns;
		final int numberOfRows = (int)Math.ceil((double)size/(double)numberOfColumns);
		
		wrapper.removeAll();
		wrapper.setLayout(new GridLayout(
				numberOfRows, 
				numberOfColumns));
		for(int i = 0; i < size; i++) {
			wrapper.add(new ThumbnailPanel(images.get(i), side));
		}
		wrapper.revalidate();
		wrapper.repaint();
	}
	
	public void setThumbnails(List<BufferedImage> images) {
		this.images = images;
		updateGrid();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals(PropertyNames.MODEL_MAIN_FRAME_RESIZE)) {
			updateGrid();
		}
	}
}
