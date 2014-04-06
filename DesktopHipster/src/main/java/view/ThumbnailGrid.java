package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
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
	private MouseAdapter ma;
	
	private List<ThumbnailPanel> tpList;
	
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
		
		ma = new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				updateVisibleLayer(e);
			}
		};
		content.addMouseMotionListener(ma);
		wrapper.addMouseMotionListener(ma);
	}
	
	private void updateGrid() {
		int size = images.size();
		final int numberOfColumns = 3;
		int side = (getWidth()-2) / numberOfColumns;
		final int numberOfRows = (int)Math.ceil((double)size/(double)numberOfColumns);
		
		tpList = new ArrayList<ThumbnailPanel>();
		wrapper.removeAll();
		wrapper.setLayout(new GridLayout(
				numberOfRows, 
				numberOfColumns));
		for(int i = 0; i < size; i++) {
			ThumbnailPanel tp = new ThumbnailPanel(images.get(i), side);
			tp.addMouseMotionL(ma);
			wrapper.add(tp);
			tpList.add(tp);
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
	
	public void updateVisibleLayer(MouseEvent e) {
		for(ThumbnailPanel tp : tpList) {
			if(tp.isChild(e.getSource())) {
				tp.showLayer();
			} else {
				tp.hideLayer();
			}
		}
	}
}
