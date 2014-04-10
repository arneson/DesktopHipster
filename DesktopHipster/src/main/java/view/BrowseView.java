package view;

import general.PropertyNames;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 * One of the cards used in View.java. This jPanel represents
 * the browse view where the user browses all of his/hers 
 * images.
 * 
 * @author Robin Sveningson
 *
 */
@SuppressWarnings("serial")
public class BrowseView extends Card implements PropertyChangeListener {
	private final PropertyChangeSupport pcs;
	
	private JButton proceedButton;
	private JLabel desc;
	private JButton chooseImageButton;
	private ThumbnailGrid grid;
	
	public BrowseView(PropertyChangeSupport pcs) {
		super();
		this.pcs = pcs;
		initialize();
		pcs.addPropertyChangeListener(grid);
	}
	
	public void initialize() {
		proceedButton = new JButton("proceed");
		desc = new JLabel("BrowseView");
		chooseImageButton = new JButton("Choose image");
		grid = new ThumbnailGrid();
		
		proceedButton.setEnabled(false);
		addNorth(new JPanel(){{
			add(proceedButton);
			add(desc);}});
		addCenter(new JPanel(){{setLayout(new java.awt.GridLayout(1,1));add(grid);}});
		
		//START OF GRID TEST THUMBNAILS
		final BufferedImage image = new BufferedImage(1000,1000,BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 1000, 1000);
		List<BufferedImage> list = new ArrayList<BufferedImage>();
		for(int i = 0; i < 20; i++) {
			list.add(image);
		}
		grid.setThumbnails(list);
		//END OF GRID TEST THUMBNAILS
		
		proceedButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				pcs.firePropertyChange(PropertyNames.VIEW_REQUEST_CARD_CHANGE, null, View.SubView.EDIT);
			}
		});

		
		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				grid.updateVisibleLayer(e);
			}
		});
		
		addMouseWheelListener(new MouseWheelListener(){
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				grid.mouseWheelMoved(e);
			}
		});
	}

	public void propertyChange(PropertyChangeEvent evt) {
		switch(evt.getPropertyName()){
		case PropertyNames.MODEL_ACTIVE_IMAGE_CHANGE:
			proceedButton.setEnabled(true);
			break;
		}
	}
}
