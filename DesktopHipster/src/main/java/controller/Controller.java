package controller;

import filter.FiltersEnum;
import general.PropertyNames;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.TreeSet;

import model.ExtendedImage;
import model.IHost;
import model.Model;
import model.NoSuchVersionException;
import model.UploadRunnable;
import view.DragNDropTray;
import view.UploadPop;
import view.View;

/**
 * The controller is a part of the MVC. It will listen to events from the View
 * and then tell the model how to react.
 * 
 * @author Robin Sveningson
 * @revised Lovisa Jäberg
 * @revised Edvard Hübinette
 * @revised Simon Arneson
 */
public class Controller implements PropertyChangeListener {
	private Model model;
	private View view;
	private DragNDropTray dndTray;
	private UploadPop uploadPop;

	public Controller() {
		view = new View();
		model = new Model();

		dndTray = new DragNDropTray();
		uploadPop = new UploadPop();

		view.addPropertyChangeListener(this);
		model.addPropertyChangeListener(view);
		dndTray.addPropertyChangeListener(this);
		uploadPop.setVisible(true);
		try{
			model.startUp();
		}
		catch(ClassNotFoundException e){
			model.clearBackup();
		}
		model.updateGrid(null);
		uploadPop.setVisible(false);
	}

	@SuppressWarnings("unchecked")
	public void propertyChange(PropertyChangeEvent evt) {
		String name = evt.getPropertyName();

		switch (name) {
		case PropertyNames.VIEW_REQUEST_CARD_CHANGE:
			model.changeCardView((String) evt.getNewValue());
			break;
		case PropertyNames.VIEW_NEW_IMAGE_CHOSEN:
			ExtendedImage recievedImage = (ExtendedImage) evt.getNewValue();
			if (model.getActiveImage() != recievedImage)
				model.setActiveImage((ExtendedImage) evt.getNewValue());
			else
				model.setActiveImage(null);
			break;
		case PropertyNames.VIEW_ACTIVE_FILTER_CHANGE:
			ExtendedImage tempImg = model.getActiveImage();
			if( evt.getNewValue()!=null){
				tempImg.setPreview(((FiltersEnum) evt.getNewValue()).
						getFilter()
						.applyFilter(
								tempImg.
								getPreviewOriginal()));
				model.setActiveImage(tempImg);
				model.setActiveFilter((FiltersEnum) evt.getNewValue());
			}
			else
				model.setActiveFilter(null);
			break;
		case PropertyNames.VIEW_APPLY_FILTER:
			FiltersEnum activeFilterName = model.getActiveFilter();
			if (activeFilterName != null) {
				model.getActiveImage().addVersion(
						activeFilterName,
						activeFilterName.getFilter().applyFilter(
								model.getActiveImage().getOriginal()));
			}
			break;
		case PropertyNames.VIEW_UPLOAD_ACTIVE_IMAGE:
			uploadPop.setLocation(dndTray.getPopPosition(), 24);
			uploadPop.setVisible(true);
			IHost chosenHost = (IHost) evt.getNewValue();
			try {
				BufferedImage imageToUpload;
				if (model.getActiveFilter() != null) {
					imageToUpload = model.getActiveImage().getVersion(
							model.getActiveFilter());

				} else {
					imageToUpload = model.getActiveImage().getOriginal();
				}
				UploadRunnable upRun = new UploadRunnable(imageToUpload,chosenHost,uploadPop);
				Thread uploadThread = new Thread(upRun);  
				uploadThread.start();

			} catch (NoSuchVersionException e) {
				// Should be impossible
				System.out
						.println("Good job, send us an email on how you managed!");
			}
			break;

		case PropertyNames.VIEW_SAVE_IMAGE_TO_DISC:
			try {
				BufferedImage imageToSave;
				if (model.getActiveImage().getVersion(model.getActiveFilter())
						.equals(null)) {
					imageToSave = model.getActiveImage().getOriginal();
				} else {
					imageToSave = model.getActiveImage().getVersion(
							model.getActiveFilter());
				}
				model.getLibrary().save(imageToSave, (File) evt.getNewValue());

			} catch (NoSuchVersionException e) {
				System.out.println("No such version!");
				e.printStackTrace();

			} catch (FileNotFoundException e) {
				System.out.println("File not found!");
				e.printStackTrace();

			} catch (IOException e) {
				System.out.println("IO Exception!");
				e.printStackTrace();
			}
			break;

		case PropertyNames.VIEW_ADD_NEW_TAG:
			model.addTag(evt.getNewValue().toString());
			break;
		case PropertyNames.VIEW_REMOVE_TAG:
			model.removeTag(evt.getNewValue().toString());
			break;
		case PropertyNames.VIEW_TAGS_ON_IMAGE_CHANGED:
			if ((boolean) evt.getOldValue()){
				model.addTagToActiveImage(evt.getNewValue().toString());
			}else{
				model.removeTagOnActiveImage(evt.getNewValue().toString());
			}
			break;
		case PropertyNames.VIEW_ADD_NEW_IMAGE_TO_LIBRARY:
			File imageFile = (File) evt.getNewValue();
			try {
				model.addFileToLibrary(imageFile);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			break;
		case PropertyNames.VIEW_GRID_RESIZE:
			model.gridWidthChanged((Integer) evt.getNewValue());
			model.updateGrid(null);
			break;
		case PropertyNames.VIEW_WIDTH_UPDATE:
			model.gridWidthChanged((Integer) evt.getNewValue());
			break;
		case PropertyNames.VIEW_SHOW_IMAGES_WITH_TAGS:
			model.updateGrid((TreeSet<String>) evt.getNewValue());
			break;
		case PropertyNames.REMOVE_IMAGE_FROM_LIBRARY:
			model.getLibrary().remove(model.getActiveImage());
			break;
		case PropertyNames.GO_STRAIGHT_TO_UPLOAD:
			ExtendedImage img = (ExtendedImage)evt.getNewValue();
			if( evt.getNewValue()!=null){
				img.setPreview(((FiltersEnum) evt.getOldValue()).
						getFilter()
						.applyFilter(
								img.
								getPreviewOriginal()));
				model.setActiveImage(img);
				model.setActiveFilter((FiltersEnum) evt.getOldValue());
			}
			else
				model.setActiveFilter(null);
			break;
		}
	}

	public void shutDownEverything() {
		model.saveState();
	}
}
