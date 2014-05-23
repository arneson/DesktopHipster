package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;

import javax.swing.*;

@SuppressWarnings("serial")
public abstract class Card extends JPanel {
	private JPanel northPanel, north, south, center, west, east, subNorth,
			subSouth, subCenter, subWest, subEast;

	private JLabel logo;

	public Card() {
		super();
		cardInitialize();
	}

	public void cardInitialize() {
		setBackground(Constants.BACKGROUNDCOLOR.getColor());

		north = new JPanel();
		south = new JPanel();
		center = new JPanel();
		west = new JPanel();
		east = new JPanel();

		north.setBackground(Constants.BACKGROUNDCOLOR.getColor());
		south.setBackground(Constants.BACKGROUNDCOLOR.getColor());
		center.setBackground(Constants.BACKGROUNDCOLOR.getColor());
		west.setBackground(Constants.BACKGROUNDCOLOR.getColor());
		east.setBackground(Constants.BACKGROUNDCOLOR.getColor());

		north.setLayout(new GridLayout(1, 1));
		south.setLayout(new GridLayout(1, 1));
		center.setLayout(new BorderLayout());
		west.setLayout(new GridLayout(1, 1));
		east.setLayout(new GridLayout(1, 1));

		north.setPreferredSize(new Dimension(1000, 300));
		south.setPreferredSize(new Dimension(1000, 150));
		center.setPreferredSize(new Dimension(0, 0));
		west.setPreferredSize(new Dimension(150, 0));
		east.setPreferredSize(new Dimension(150, 0));

		setLayout(new BorderLayout());
		add(north, BorderLayout.NORTH);
		add(south, BorderLayout.SOUTH);
		add(center, BorderLayout.CENTER);
		add(west, BorderLayout.WEST);
		add(east, BorderLayout.EAST);
		
		ImageIcon logoImage = new ImageIcon(getClass().getResource("/Images/logo.png"));

		logo = new JLabel(logoImage);
		logo.setOpaque(true);
		logo.setBackground(Constants.BACKGROUNDCOLOR.getColor());

		northPanel = new JPanel(new BorderLayout());{{
				add(logo, BorderLayout.NORTH);
		}};
		northPanel.setBackground(Constants.BACKGROUNDCOLOR.getColor());
		
		addNorth(northPanel);
	}

	protected void addNorth(JPanel north) {
		subNorth = north;
		this.north.add(north);
	}

	protected void addSouth(JPanel south) {
		subSouth = south;
		this.south.add(south);
	}

	protected void addCenter(JPanel center) {
		subCenter = center;
		this.center.add(center);
	}

	protected void addWest(JPanel west) {
		subWest = west;
		this.west.add(west);
	}

	protected void addEast(JPanel east) {
		subEast = east;
		this.east.add(east);
	}

	@Override
	public void setBackground(Color c) {
		if (east != null) {
			west.setBackground(c);
			east.setBackground(c);
			center.setBackground(c);
			north.setBackground(c);
			south.setBackground(c);
		}
		if (subNorth != null) {
			subNorth.setBackground(c);
		}
		if (subSouth != null) {
			subSouth.setBackground(c);
		}
		if (subCenter != null) {
			subCenter.setBackground(c);
		}
		if (subWest != null) {
			subWest.setBackground(c);
		}
		if (subEast != null) {
			subEast.setBackground(c);
		}
	}

	@Override
	public void addMouseMotionListener(MouseMotionListener l) {
		super.addMouseMotionListener(l);
		north.addMouseMotionListener(l);
		south.addMouseMotionListener(l);
		west.addMouseMotionListener(l);
		east.addMouseMotionListener(l);
		center.addMouseMotionListener(l);
	}

	@Override
	public void addMouseWheelListener(MouseWheelListener l) {
		super.addMouseWheelListener(l);
		north.addMouseWheelListener(l);
		south.addMouseWheelListener(l);
		west.addMouseWheelListener(l);
		east.addMouseWheelListener(l);
		center.addMouseWheelListener(l);
	}

}
