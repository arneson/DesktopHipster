package MVC;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Controller implements PropertyChangeListener {
	private Model model;
	
	public Controller(Model model) {
		this.model = model;
	}

	public void propertyChange(PropertyChangeEvent evt) {
		String name = evt.getPropertyName();
		if(name.equals(PropertyNames.CHANGE_CARD_VIEW)) {
			model.changeCardView((View.SubView)evt.getNewValue());
		}
	}
}
