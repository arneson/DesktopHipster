package view;

import javax.swing.*;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeSupport;

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
	private PropertyChangeSupport pcs;
	
	
	public ThumbnailPanel(PropertyChangeSupport pcs, ThumbnailData data, int side) {
		super();
		this.pcs = pcs;
		initialize(data, side);
	}
	
	private void initialize(final ThumbnailData data, int side) {
		side -= borderSize * 2;
		
		setPreferredSize(new Dimension(side, side));
		setBorder(BorderFactory.createEmptyBorder(borderSize, borderSize, borderSize, borderSize));
		
		BufferedImage image = data.getSelectedVersion();
		if(image != null) {
			canvas = new JLabel(new ImageIcon(image));
		}
		topLayer = new ThumbnailPanelLayer(pcs, data, side);
		layeredPane = new JLayeredPane();
		
		canvas.setBounds(new Rectangle(0,0,side,side));
		topLayer.setBounds(new Rectangle(0,0,side,side));
		
		layeredPane.add(canvas);
		layeredPane.add(topLayer);
		
		layeredPane.moveToFront(topLayer);
		
		setLayout(new GridLayout(1,1));
		add(layeredPane);
		
		topLayer.addVersionList(data.getVersions(), side);
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
