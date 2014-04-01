package controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.ImageIcon;

import General.PropertyNames;
import view.View;
import model.ExtendedImage;
import model.Model;

public class Controller implements PropertyChangeListener {
	private Model model;
	
	public Controller(Model model) {
		this.model = model;
	}

	public void propertyChange(PropertyChangeEvent evt) {
		String name = evt.getPropertyName();

		switch(name){
		case PropertyNames.CHANGE_CARD_VIEW:
			model.changeCardView((View.SubView)evt.getNewValue());
			break;
		case PropertyNames.OPEN_FILE_EVENT:
			File file = (File) evt.getNewValue();
			model.setActiveImage(new ExtendedImage(new ImageIcon(file.getAbsolutePath())));
			break;
		case PropertyNames.ACTIVE_FILTER_CHANGED_EVENT:
			
			System.out.println("APPLY FILTER TO PREVIEW");
			break;
		}
	}
}
