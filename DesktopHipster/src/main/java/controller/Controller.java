package controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.ImageIcon;

import filter.FiltersEnum;
import General.PropertyNames;
import view.View;
import model.ExtendedImage;
import model.IHost;
import model.Model;
import model.NoSuchVersionException;

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
			ExtendedImage tempImg = model.getActiveImage();
			tempImg.setPreview(((FiltersEnum)evt.getNewValue())
					.getFilter().applyFilter(tempImg.getPreview()));
			model.setActiveImage(tempImg);
			model.setActiveFilter((FiltersEnum)evt.getNewValue());
			//System.out.println("APPLY FILTER TO PREVIEW");
			break;
		case PropertyNames.APPLY_ACTIVE_FILTER:
			FiltersEnum activeFilterName = model.getActiveFilter();
			model.getActiveImage().addVersion(activeFilterName, 
					activeFilterName.getFilter().applyFilter(model.getActiveImage()));
			break;
		case PropertyNames.UPLOAD_ACTIVE_IMAGE:
			IHost chosenHost = (IHost)evt.getNewValue();
			try{
				chosenHost.uploadImage(model.getActiveImage().getVersion(model.getActiveFilter()));
			}
			catch(NoSuchVersionException e){
				//Should be impossible
				System.out.println("Good job, send us an email on how you managed!");
			}
		}
	}
}
