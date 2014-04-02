package desktopHipster;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import General.PropertyNames;
import view.ThumbnailGrid;

/**
 * Class to test the thumbnail grid. The class is not
 * supposed to be used in the final product.
 * 
 * @author Robin Sveningsonl
 *
 */
@SuppressWarnings("serial")
public class GridTest extends JFrame {
	private PropertyChangeSupport pcs;
	private ThumbnailGrid grid;
	
	public GridTest() {
		super();
		initialize();
	}
	
	public void initialize() {
		grid = new ThumbnailGrid();
		
		setLayout(new GridLayout(1,1));
		add(grid);
		
		final BufferedImage image = new BufferedImage(1000,1000,BufferedImage.TYPE_INT_ARGB);
		
		Graphics g = image.getGraphics();
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 1000, 1000);
		
		List<BufferedImage> list = new ArrayList<BufferedImage>() {{
			add(image);
			add(image);
			add(image);
			add(image);
			add(image);
			add(image);
			add(image);
			add(image);
			add(image);
			add(image);
			add(image);
			add(image);
			add(image);
			add(image);
			add(image);
		}};
		grid.setThumbnails(list);
		
		pcs = new PropertyChangeSupport(this);
		pcs.addPropertyChangeListener(grid);
		
		addComponentListener(new ComponentListener(){
			@Override
			public void componentResized(ComponentEvent e) {
				pcs.firePropertyChange(PropertyNames.MAIN_FRAME_RESIZE, null, null);
			}
			@Override
			public void componentMoved(ComponentEvent e) {}
			@Override
			public void componentShown(ComponentEvent e) {}
			@Override
			public void componentHidden(ComponentEvent e) {}
		});
		
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		GridTest gt = new GridTest();
	}
}
