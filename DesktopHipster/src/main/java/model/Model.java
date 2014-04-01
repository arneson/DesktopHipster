package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import filter.Filter;
import view.View;
import view.View.SubView;
import General.PropertyNames;

public class Model {
	private PropertyChangeSupport pcs;
	//private Library library = new Library();
	//private DdBox ddBox = new DdBox();
	private IHost[] hosts;
	private Filter[] filters;
	
	public Model() {
		pcs = new PropertyChangeSupport(this);
		
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}
	
	public void changeCardView(View.SubView sv) {
		pcs.firePropertyChange(PropertyNames.CHANGE_CARD_VIEW, null, sv);
	}
}