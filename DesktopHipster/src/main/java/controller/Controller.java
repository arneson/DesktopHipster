package controller;

import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ImageIcon;

import dragNdrop.DragNDropTray;
import filter.FiltersEnum;
import General.PropertyNames;
import view.View;
import model.ExtendedImage;
import model.IHost;
import model.Model;
import model.NoSuchVersionException;

/**
 * The controller is a part of the MVC. It will listen
 * to events from the View and then tell the model how
 * to react.
 * 
 * @author Robin Sveningson
 *
 */
public class Controller implements PropertyChangeListener {
	private Model model;
	private View view;
	private DragNDropTray dndTray;

	public Controller() {
		view = new View();
		model = new Model();
		dndTray = new DragNDropTray();
		
		view.addPropertyChangeListener(this);
		model.addPropertyChangeListener(view);
		dndTray.addPropertyChangeListener(this);
	}

	public void propertyChange(PropertyChangeEvent evt) {
		String name = evt.getPropertyName();

		switch (name) {
		case PropertyNames.VIEW_REQUEST_CARD_CHANGE:
			model.changeCardView((View.SubView) evt.getNewValue());
			break;
		case PropertyNames.VIEW_OPEN_FILE_CLICKED:
			File file = (File) evt.getNewValue();
			model.setActiveImage(new ExtendedImage(new ImageIcon(file
					.getAbsolutePath())));
			break;
		case PropertyNames.VIEW_ACTIVE_FILTER_CHANGE:
			ExtendedImage tempImg = model.getActiveImage();
			tempImg.setPreview(((FiltersEnum) evt.getNewValue()).getFilter()
					.applyFilter(tempImg.getPreview()));
			model.setActiveImage(tempImg);
			model.setActiveFilter((FiltersEnum) evt.getNewValue());
			break;
		case PropertyNames.VIEW_APPLY_FILTER:
			FiltersEnum activeFilterName = model.getActiveFilter();
			if (activeFilterName != null) {
				model.getActiveImage().addVersion(
						activeFilterName,
						activeFilterName.getFilter().applyFilter(
								model.getActiveImage()));
			}
			model.changeCardView(View.SubView.UPLOAD);
			break;
		case PropertyNames.VIEW_UPLOAD_ACTIVE_IMAGE:
			IHost chosenHost = (IHost) evt.getNewValue();
			try {
				BufferedImage imageToUpload;
				if (model.getActiveFilter() != null) {
					imageToUpload = model.getActiveImage().getVersion(
							model.getActiveFilter());

				} else {
					imageToUpload = model.getActiveImage();
				}
				chosenHost.uploadImage(imageToUpload);

			} catch (NoSuchVersionException e) {
				// Should be impossible
				System.out
						.println("Good job, send us an email on how you managed!");
			}
			break;
		case PropertyNames.VIEW_SAVE_IMAGE_TO_DISC:
			try {
				BufferedImage imageToSave = model.getActiveImage().getVersion(model.getActiveFilter());
				model.getLibrary().save(imageToSave, "name.png");
			} catch (NoSuchVersionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case PropertyNames.ADD_NEW_IMAGE_TO_LIBRARY:
	    	System.out.println(evt.getNewValue());
	    	//TODO TEMP SOLUTION FOR TESTING
	    	File imageFile = (File) evt.getNewValue();
			model.setActiveImage(new ExtendedImage(new ImageIcon(imageFile
					.getAbsolutePath())));
			break;
		}
	}
}
