package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import filter.FiltersEnum;

/**
 * Button class extending JButton to allow each button to have a specific filter
 * associated with it
 * 
 * @author Simon Arneson
 * @revised Edvard Hübinette
 */

@SuppressWarnings("serial")
public class FilterButton extends JButton {
	private FiltersEnum filter;
	private ImageIcon icon;

	FilterButton() {
		super();
	}

	FilterButton(String title) {
		super(title);
	}

	FilterButton(FiltersEnum filter) {
		super();
		this.filter = filter;
		icon = (new ImageIcon(getClass().getResource("/HipsterDemo.png")));
		BufferedImage img = new BufferedImage(icon.getIconWidth(),
				icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.createGraphics();
		(new ImageIcon(getClass().getResource("/HipsterDemo.png"))).paintIcon(
				null, g, 0, 0);
		g.dispose();
		img = filter.getFilter().applyFilter(img);

		this.setPreferredSize(new Dimension(150, 100));
		this.setIcon(new ImageIcon(img));
		JLabel title = new JLabel(filter.name());
		this.add(title);
	}

	public FiltersEnum getFilter() {
		return filter;
	}
}