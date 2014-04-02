package view;

import java.awt.GridLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.*;

/**
 * This class displays all the images in the library
 * as thumnails in a grid.
 * 
 * @author Robin Sveningson
 *
 */
@SuppressWarnings("serial")
public class ThumbnailGrid extends JScrollPane {
	private final static int MINIMUM_THUMBNAIL_SIZE = 200;
	
	private JPanel content;
	private List<BufferedImage> images;
	
	public ThumbnailGrid() {
		super();
		initialize();
	}
	
	public void initialize() {
		content = new JPanel();
		
		addComponentListener(new ComponentListener(){
			@Override
			public void componentResized(ComponentEvent e) {
				updateGrid();
			}
			@Override
			public void componentMoved(ComponentEvent e) {}
			@Override
			public void componentShown(ComponentEvent e) {}
			@Override
			public void componentHidden(ComponentEvent e) {}
		});
		
		setViewportView(content);
	}
	
	private void updateGrid() {
		int s = images.size();
		int w = getWidth();
		
		int imagesPerRow = w / ThumbnailGrid.MINIMUM_THUMBNAIL_SIZE;
		int columns = (imagesPerRow % s == 0) ? imagesPerRow / s : imagesPerRow / s + 1;
		
		if(columns > 0 && imagesPerRow > 0) {
			content.removeAll();
			content.setLayout(new GridLayout(imagesPerRow, columns));
			for(int i = 0; i < s; i++) {
				content.add(new ThumbnailPanel(images.get(i)));
			}
			content.revalidate();
			content.repaint();
		}
	}
	
	public void setThumbnails(List<BufferedImage> images) {
		this.images = images;
		updateGrid();
	}
}
