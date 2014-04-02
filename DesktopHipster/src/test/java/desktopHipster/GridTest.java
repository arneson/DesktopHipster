package desktopHipster;

import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

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
	private ThumbnailGrid grid;
	
	public GridTest() {
		super();
		initialize();
	}
	
	public void initialize() {
		grid = new ThumbnailGrid();
		
		setLayout(new GridLayout(1,1));
		add(grid);
		
		ImageIcon icon = new ImageIcon(getClass().getResource("/robin.jpg"));
		final BufferedImage img = new BufferedImage(icon.getIconWidth(),icon.getIconHeight(),BufferedImage.TYPE_INT_ARGB);
		List<BufferedImage> list = new ArrayList<BufferedImage>() {{
			add(img);
			add(img);
			add(img);
			add(img);
			add(img);
			add(img);
			add(img);
			add(img);
			add(img);
			add(img);
			add(img);
			add(img);
			add(img);
			add(img);
			add(img);
		}};
		
		grid.setThumbnails(list);
		
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		GridTest gt = new GridTest();
	}
}
