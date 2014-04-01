package view;
import javax.swing.*;

import filter.FiltersEnum;

/**
 * Button class extending JButton to allow each button to have  a specific filter associated with it
 * 
 * @author Simon Arneson
 */
public class FilterButton extends JButton{
	FiltersEnum filter;
	FilterButton(){
		super();
	}
	FilterButton(String title){
		super(title);
	}
	FilterButton(FiltersEnum filter){
		super();
		this.filter=filter;	
	}
	public FiltersEnum getFilter(){
		return filter;
	}

}
