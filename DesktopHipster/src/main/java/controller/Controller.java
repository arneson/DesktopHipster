package controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import General.PropertyNames;
import view.View;
import model.Model;

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
