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
	private ExtendedImage activeImage;
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
	public void setActiveImage(ExtendedImage newImage){
		activeImage=newImage;
		pcs.firePropertyChange(PropertyNames.ACTIVE_IMAGE_CHANGED_EVENT, null, activeImage);
	}
	public ExtendedImage getActiveImage(){
		return activeImage;
	}
}
