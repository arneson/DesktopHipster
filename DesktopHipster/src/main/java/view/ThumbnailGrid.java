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

import model.ThumbnailData;

import java.awt.Color;

/**
 * This class displays all the images in the library as thumnails in a grid.
 * 
 * @author Robin Sveningson
 * 
 */
@SuppressWarnings("serial")
public class ThumbnailGrid extends JScrollPane implements
		PropertyChangeListener {
	private JPanel content, wrapper;
	private AddPanel addPanel;
	private List<ThumbnailData> data;
	private List<ThumbnailPanel> panelList = new ArrayList<ThumbnailPanel>();
	private MouseAdapter ma;
	private PropertyChangeSupport pcs;
	private final int numberOfColumns = 4;
	private int side = 0;

	public ThumbnailGrid(PropertyChangeSupport pcs) {
		super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.pcs = pcs;
		initialize();
	}

	public void initialize() {
		
		content = new JPanel();
		wrapper = new JPanel();
		wrapper.setBackground(Constants.BACKGROUNDCOLOR.getColor());
		
		content.setLayout(new BorderLayout());
		content.add(wrapper, BorderLayout.NORTH);
		content.setBackground(Constants.BACKGROUNDCOLOR.getColor());

		setBorder(null);
		setViewportView(content);
		setBackground(Constants.BACKGROUNDCOLOR.getColor());

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
		addPanel = new AddPanel(side, pcs);
		addPanel.setBackground(Constants.BACKGROUNDCOLOR.getColor());
		addPanel.setOpaque(true);
		
		int size = data.size()+1;
		final int numberOfRows = (int)Math.ceil((double)size/(double)numberOfColumns);

		panelList = new ArrayList<ThumbnailPanel>();
		wrapper.removeAll();
		wrapper.setLayout(new GridLayout(
				numberOfRows, 
				numberOfColumns));
		wrapper.add(addPanel);
		for(int i = 0; i < numberOfRows * numberOfColumns; i++) {
			if (i < size-1) {

				ThumbnailPanel tp = new ThumbnailPanel(pcs, data.get(i), side);
				tp.setBackground(Constants.BACKGROUNDCOLOR.getColor());
				tp.addMouseMotionL(ma);
				wrapper.add(tp);
				panelList.add(tp);
			} else {
				JPanel placeHolder = new JPanel();
				placeHolder.setBackground(Constants.BACKGROUNDCOLOR.getColor());
				wrapper.add(placeHolder);
			}
		}
		wrapper.revalidate();
		wrapper.repaint();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String name = evt.getPropertyName();
		switch (name) {
		case PropertyNames.MODEL_GRID_UPDATE:
			calculateGridWidth();
			pcs.firePropertyChange(PropertyNames.VIEW_WIDTH_UPDATE, null, (Integer)side);

			this.data = (List<ThumbnailData>)evt.getNewValue();

			updateGrid();
			break;
		case PropertyNames.MODEL_ACTIVE_IMAGE_CHANGE:
			ThumbnailData selected = (ThumbnailData) evt.getNewValue();
			updatePanelBorders(selected);
			break;
		}
	}

	private void updatePanelBorders(ThumbnailData selected) {
		for (ThumbnailPanel tp : panelList) {
			tp.setSelected(tp.getData() == selected);
			tp.updateBorderColor();
		}
	}

	public void updateVisibleLayer(MouseEvent e) {
		for (ThumbnailPanel tp : panelList) {
			if (tp.isChild(e.getSource())) {
				tp.showLayer();
			} else {
				tp.hideLayer();
			}
		}
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		MouseWheelListener[] listeners = getMouseWheelListeners();
		for (MouseWheelListener listener : listeners) {
			listener.mouseWheelMoved(e);
		}
	}

	public void frameRezise() {
		calculateGridWidth();
		pcs.firePropertyChange(PropertyNames.VIEW_GRID_RESIZE, null,
				(Integer) side);
	}

	private void calculateGridWidth() {
		side = (getWidth() - 2) / numberOfColumns;
	}
}
