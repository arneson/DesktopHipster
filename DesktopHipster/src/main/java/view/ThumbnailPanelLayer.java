package view;

import filter.FiltersEnum;
import general.PropertyNames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import model.ThumbnailData;

/**
 * Provides support for representing image thumbnails with version management.
 * 
 * @author Robin Sveningson
 * @revised Edvard Hübinette
 * 
 */
@SuppressWarnings("serial")
public class ThumbnailPanelLayer extends JPanel {
	private final int iconSide = 35;

	private JScrollPane scroll;
	private JPanel content, contentWrapper, iconWrapper;
	private JButton deleteIcon;//, tagIcon, saveIcon;
	private PropertyChangeSupport pcs;
	private ThumbnailData data;

	public ThumbnailPanelLayer(PropertyChangeSupport pcs, ThumbnailData data,
			int side) {
		super();
		this.pcs = pcs;
		initialize(data, side);
	}

	public void initialize(final ThumbnailData data, int side) {
		hideLayer();
		setOpaque(false);

		this.data = data;
		content = new JPanel();
		contentWrapper = new JPanel();
		scroll = new JScrollPane(contentWrapper,
				JScrollPane.VERTICAL_SCROLLBAR_NEVER,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		//tagIcon = new JButton();
		deleteIcon = new JButton(new ImageIcon(getClass().getResource("/iconPanel_remove.png")));
		//saveIcon = new JButton();
		iconWrapper = new JPanel();

		deleteIcon.addActionListener(myActionListener);

		scroll.setBorder(null);
		scroll.setPreferredSize(new Dimension(0, side / 4
				+ scroll.getHorizontalScrollBar().getPreferredSize().height));
		contentWrapper.setPreferredSize(new Dimension(0, side / 4
				+ scroll.getHorizontalScrollBar().getPreferredSize().height));
		contentWrapper.setOpaque(false);
		scroll.setOpaque(false);
		content.setOpaque(false);
		scroll.getViewport().setOpaque(false);

		//tagIcon.setPreferredSize(new Dimension(iconSide, iconSide));
		deleteIcon.setPreferredSize(new Dimension(iconSide, iconSide));
		//saveIcon.setPreferredSize(new Dimension(iconSide, iconSide));
		//saveIcon.setBackground(Color.green);
		//tagIcon.setBackground(Color.red);
		//JLabel deleteLabel = new JLabel(new ImageIcon(getClass().getResource("/iconPanel_remove.png")));
		//deleteIcon.add(deleteLabel, BorderLayout.CENTER);

		deleteIcon.setToolTipText("Remove image from library");

		iconWrapper.setOpaque(false);
		iconWrapper.setPreferredSize(new Dimension(0, iconSide + 10));
		iconWrapper.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		iconWrapper.add(deleteIcon);
		//iconWrapper.add(tagIcon);
		//iconWrapper.add(saveIcon);
		iconWrapper.setBackground(null);

		contentWrapper.setLayout(new BorderLayout());
		contentWrapper.add(content, BorderLayout.WEST);

		addMouseListener(myMouseAdapter);

		setLayout(new BorderLayout());
		add(scroll, BorderLayout.SOUTH);
		add(iconWrapper, BorderLayout.NORTH);
	}

	private ActionListener myActionListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(deleteIcon)){
				System.out.println("Remove chosen image");
				pcs.firePropertyChange(PropertyNames.REMOVE_IMAGE_FROM_LIBRARY, null, null);
			}/* else if (e.getSource().equals(tagIcon)){
				System.out.println("Show tags");
			}*/
		}

	};

	private MouseAdapter myMouseAdapter = new MouseAdapter() {
		@Override
		public void mouseReleased(MouseEvent e) {
			pcs.firePropertyChange(PropertyNames.VIEW_NEW_IMAGE_CHOSEN,
					null, data);

		}
	};

	public void showLayer() {
		setVisible(true);
	}

	public void hideLayer() {
		setVisible(false);
	}

	public void addMouseMotionL(MouseAdapter ma) {
		addMouseMotionListener(ma);
		scroll.addMouseMotionListener(ma);
		content.addMouseMotionListener(ma);
		deleteIcon.addMouseMotionListener(ma);
		//tagIcon.addMouseMotionListener(ma);
	}

	public boolean isChild(Object o) {
		return o.equals(this) || o.equals(scroll) || o.equals(content)
				|| o.equals(deleteIcon);// || o.equals(tagIcon);
	}

	public void addVersionList(Map<FiltersEnum, BufferedImage> versions, int side) {
		side = side / 4;

		if (versions.size() > 4) {
			contentWrapper
			.setPreferredSize(new Dimension(versions.size() * side,
					side
					+ scroll.getHorizontalScrollBar()
					.getPreferredSize().height));
		} else {
			contentWrapper
			.setPreferredSize(new Dimension(4 * side, side
					+ scroll.getHorizontalScrollBar()
					.getPreferredSize().height));
		}

		if (versions.size() == 0) {
			scroll.setVisible(false);
		}

		content.removeAll();
		content.setLayout(new GridLayout(1, versions.size()));
		content.setPreferredSize(new Dimension(side * versions.size(), side));
		for (int i = 0; i < versions.size(); i++) {
			final JLabel version = new JLabel(new ImageIcon(versions.get(i).getScaledInstance(
					side - 1, -1, WIDTH)));
			version.setName(versions.keySet().toArray()[i].toString());
			version.setPreferredSize(new Dimension(side - 1, side - 1));
			version.setBorder(BorderFactory.createLineBorder(new Color(150,
					150, 150), 1));
			content.add(version);
			final int j = i;
			version.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseReleased(MouseEvent e) {
					JLabel sourceLabel = (JLabel) e.getSource();
					pcs.firePropertyChange(PropertyNames.VIEW_ACTIVE_FILTER_CHANGE, null, sourceLabel.getName());
					pcs.firePropertyChange(PropertyNames.VIEW_NEW_IMAGE_CHOSEN,
							null, data);
					pcs.firePropertyChange(PropertyNames.VIEW_REQUEST_CARD_CHANGE, null, View.CardState.UPLOAD);
				}
			});
		}
		content.setBackground(Constants.BACKGROUNDCOLOR.getColor());
		content.revalidate();
		content.repaint();
	}
}
