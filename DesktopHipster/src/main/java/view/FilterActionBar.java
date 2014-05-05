package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.util.List;

import javax.swing.JPanel;

public class FilterActionBar extends JPanel {
	private final int WIDTH = 800;
	private final int HEIGHT = 200;

	public FilterActionBar(List<FilterButton> filterButtons){
		setSize(new Dimension(WIDTH, HEIGHT));
		setLayout(new FlowLayout());
		//setBackground();
		
		for(FilterButton button : filterButtons){
			add(button);
		}
		
	}
}