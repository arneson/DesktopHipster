package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import filter.FiltersEnum;

/**
 * An action bar containing buttons for selecting filters in the edit stage
 * 
 * @author Edvard Hübinette
 * 
 */

@SuppressWarnings("serial")
public class FilterActionBar extends JPanel {
	private final int WIDTH = 1000;
	private final int HEIGHT = 300;
	private JPanel container = new JPanel();
	private JScrollPane scrollpane = new JScrollPane();
	private List<FilterButton> buttons = new ArrayList<FilterButton>();

	public FilterActionBar() {

		setBorder(new LineBorder(Color.WHITE, 10));
		add(scrollpane);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		container.setBackground(Constants.BACKGROUNDCOLOR.getColor());

		container.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		container.setLayout(new FlowLayout());
		container.setBorder(null);

		scrollpane.setBorder(null);

		for (int i = 0; i < FiltersEnum.values().length; i++) {
			buttons.add(new FilterButton(FiltersEnum.values()[i]));
		}

		for (FilterButton button : buttons) {
			button.setCursor(new Cursor(Cursor.HAND_CURSOR));
			container.add(button);
		}

		scrollpane.setViewportView(container);

	}

	public List<FilterButton> getFilterButtons() {
		return new ArrayList<FilterButton>(buttons);
	}
}
