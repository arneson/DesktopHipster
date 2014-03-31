package MVC;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Model {
	private PropertyChangeSupport pcs;
	
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
