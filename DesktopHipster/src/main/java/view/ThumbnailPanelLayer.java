package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeSupport;
import java.util.List;

import javax.swing.*;

@SuppressWarnings("serial")
public class ThumbnailPanelLayer extends JPanel {
	private final int iconSide = 35;
	
	private JScrollPane scroll;
	private JPanel content, contentWrapper, iconWrapper, tagIcon, deleteIcon, saveIcon;
	private PropertyChangeSupport pcs;
	
	public ThumbnailPanelLayer(PropertyChangeSupport pcs, ThumbnailData data, int side) {
		super();
		this.pcs = pcs;
		initialize(data, side);
	}
	
	public void initialize(final ThumbnailData data, int side) {
		hideLayer();
		setOpaque(false);
		
		content = new JPanel();
		contentWrapper = new JPanel();
		scroll = new JScrollPane(contentWrapper, JScrollPane.VERTICAL_SCROLLBAR_NEVER, 
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		tagIcon = new JPanel();
		deleteIcon = new JPanel();
		saveIcon = new JPanel();
		iconWrapper = new JPanel();
		
		scroll.setBorder(null);
		scroll.setPreferredSize(new Dimension(0,side/4 + 
				scroll.getHorizontalScrollBar().getPreferredSize().height));
		contentWrapper.setPreferredSize(new Dimension(0,side/4 + 
				scroll.getHorizontalScrollBar().getPreferredSize().height));
		contentWrapper.setOpaque(false);
		scroll.setOpaque(false);
		content.setOpaque(false);
		scroll.getViewport().setOpaque(false);
		
		tagIcon.setPreferredSize(new Dimension(iconSide,iconSide));
		deleteIcon.setPreferredSize(new Dimension(iconSide,iconSide));
		saveIcon.setPreferredSize(new Dimension(iconSide,iconSide));
		saveIcon.setBackground(Color.green);
		tagIcon.setBackground(Color.red);
		deleteIcon.setBackground(Color.blue);
		
		iconWrapper.setOpaque(false);
		iconWrapper.setPreferredSize(new Dimension(0,iconSide+10));
		iconWrapper.setLayout(new FlowLayout(FlowLayout.RIGHT,10,10));
		iconWrapper.add(deleteIcon);
		iconWrapper.add(tagIcon);
		iconWrapper.add(saveIcon);
		
		contentWrapper.setLayout(new BorderLayout());
		contentWrapper.add(content, BorderLayout.WEST);
		
		addMouseListener(new MouseAdapter(){
			@Override
			public void mouseReleased(MouseEvent e) {
				pcs.firePropertyChange(PropertyNames.VIEW_NEW_IMAGE_CHOSEN, null, data);
			}
		});
		
		setLayout(new BorderLayout());
		add(scroll, BorderLayout.SOUTH);
		add(iconWrapper, BorderLayout.NORTH);
	}
	
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
		tagIcon.addMouseMotionListener(ma);
	}
	
	public boolean isChild(Object o) {
		return o.equals(this) || o.equals(scroll) || o.equals(content) || 
				o.equals(deleteIcon) || o.equals(tagIcon);
	}
	
	public void addVersionList(List<BufferedImage> versions, int side) {
		side = side / 4;
		
		if(versions.size() > 4) {
			contentWrapper.setPreferredSize(new Dimension(versions.size() * side, 
					side + scroll.getHorizontalScrollBar().getPreferredSize().height));
		} else {
			contentWrapper.setPreferredSize(new Dimension(4 * side, 
					side + scroll.getHorizontalScrollBar().getPreferredSize().height));
		}
		
		if(versions.size() == 0) {
			scroll.setVisible(false);
		}
		
		content.removeAll();
		content.setLayout(new GridLayout(1,versions.size()));
		content.setPreferredSize(new Dimension(side*versions.size(), side));
		for(BufferedImage img : versions) {
			JLabel version = new JLabel(new ImageIcon(img));
			version.setPreferredSize(new Dimension(side-1, side-1));
			version.setBorder(BorderFactory.createLineBorder(new Color(150,150,150), 1));
			content.add(version);
		}
		content.revalidate();
		content.repaint();
	}
}
