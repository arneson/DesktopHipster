package view;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import filter.FiltersEnum;

/**
 * Button class extending JButton to allow each button to have  a specific filter associated with it
 * 
 * @author Simon Arneson
 * @revised Edvard Hübinette
 */
public class FilterButton extends JButton{
	private FiltersEnum filter;
	private ImageIcon icon;
	
	FilterButton(){
		super();
	}
	
	FilterButton(String title){
		super(title);
	}
	
	FilterButton(FiltersEnum filter){
		super();
		this.filter=filter;	
		icon = (new ImageIcon(getClass().getResource("/HipsterDemo.png")));
		this.setPreferredSize(new Dimension(150, 100));
		this.setIcon(icon);
		JLabel title = new JLabel(filter.name());
		this.add(title);
		
	}
	
	public FiltersEnum getFilter(){
		return filter;
	}
}