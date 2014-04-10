package view;

import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to display an image on a JPanel.
 * 
 * @author Robin Sveningson
 *
 */
@SuppressWarnings("serial")
public class ThumbnailPanel extends JPanel {
	private final int borderSize = 2;
	
	private JLayeredPane layeredPane;
	private JLabel canvas;
	private ThumbnailPanelLayer topLayer;
	
	
	public ThumbnailPanel(BufferedImage image, int side) {
		super();
		initialize(image, side);
	}
	
	private void initialize(final BufferedImage image, int side) {
		side -= borderSize * 2;
		
		setPreferredSize(new Dimension(side, side));
		setBorder(BorderFactory.createEmptyBorder(borderSize, borderSize, borderSize, borderSize));
		setBackground(java.awt.Color.gray);
		
		canvas = new JLabel(new ImageIcon(image));
		topLayer = new ThumbnailPanelLayer(side);
		layeredPane = new JLayeredPane();
		
		canvas.setBounds(new Rectangle(0,0,side,side));
		topLayer.setBounds(new Rectangle(0,0,side,side));
		
		layeredPane.add(canvas);
		layeredPane.add(topLayer);
		
		layeredPane.moveToFront(topLayer);
		
		setLayout(new GridLayout(1,1));
		add(layeredPane);
		
		List<BufferedImage> versions = new ArrayList<BufferedImage>();
		final BufferedImage version = new BufferedImage(1000,1000,BufferedImage.TYPE_INT_ARGB);
		Graphics g = version.getGraphics();
		g.setColor(Color.green);
		g.fillRect(0, 0, 1000, 1000);
		for(int i = 0; i < 6; i++) {
			versions.add(version);
		}
		topLayer.addVersionList(versions, side);
	}
	
	public void addMouseMotionL(MouseAdapter ma) {
		canvas.addMouseMotionListener(ma);
		topLayer.addMouseMotionL(ma);
	}
	
	public void showLayer() {
		topLayer.showLayer();
	}
	
	public void hideLayer() {
		topLayer.hideLayer();
	}
	
	public boolean isChild(Object o) {
		return o.equals(canvas) || topLayer.isChild(o);
	}
	
	/*private int getScaledHeight(BufferedImage image, int width) {
		double widthHeightRatio = image.getWidth() / image.getHeight();
		double height = width / widthHeightRatio;
		return (int)Math.round(height);
	}*/
}
