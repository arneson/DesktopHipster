package view;

import general.PropertyNames;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
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
public class ThumbnailGrid extends JScrollPane implements PropertyChangeListener {	
	private JPanel content, wrapper;
	private List<ThumbnailData> data;
	private List<ThumbnailPanel> panelList;
	private MouseAdapter ma;
	private PropertyChangeSupport pcs;
	
	public ThumbnailGrid(PropertyChangeSupport pcs) {
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
		int size = data.size();
		final int numberOfColumns = 3;
		int side = (getWidth()-2) / numberOfColumns;
		final int numberOfRows = (int)Math.ceil((double)size/(double)numberOfColumns);
		
		panelList = new ArrayList<ThumbnailPanel>();
		wrapper.removeAll();
		wrapper.setLayout(new GridLayout(
				numberOfRows, 
				numberOfColumns));
				
		for(int i = 0; i < size; i++) {
			ThumbnailPanel tp = new ThumbnailPanel(pcs, data.get(i), side);
			tp.addMouseMotionL(ma);
			wrapper.add(tp);
			panelList.add(tp);
		}
		wrapper.revalidate();
		wrapper.repaint();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String name = evt.getPropertyName();
		switch(name) {
		case PropertyNames.MODEL_GRID_UPDATE:
			this.data = (List<ThumbnailData>)evt.getNewValue();
			updateGrid();
			break;
		}
	}
	
	public void updateVisibleLayer(MouseEvent e) {
		for(ThumbnailPanel tp : panelList) {
			if(tp.isChild(e.getSource())) {
				tp.showLayer();
			} else {
				tp.hideLayer();
			}
		}
	}
	
	public void mouseWheelMoved(MouseWheelEvent e) {
		MouseWheelListener[] listeners = getMouseWheelListeners();
		for(MouseWheelListener listener : listeners) {
			listener.mouseWheelMoved(e);
		}
	}
}
