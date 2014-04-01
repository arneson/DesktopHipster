package view;
import javax.swing.*;

import filter.FiltersEnum;
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
