package desktopHipster;

import static org.junit.Assert.*;
import general.PropertyNames;

import java.beans.PropertyChangeSupport;
import java.util.TreeSet;

import model.ExtendedImage;

import org.junit.Test;

import view.TagsPanel;


public class TagPanelTest {
	public void main(){
		assertTrue(testLoadingTagsFromImageWithTags());
		assertTrue(testLoadingTagsFromImageWithoutTags());
		assertTrue(testLoadingTagsFromNull());
		
	}
	@Test
	public boolean testLoadingTagsFromImageWithTags(){
		ExtendedImage img = new ExtendedImage();
		PropertyChangeSupport pcs = new PropertyChangeSupport(this);
		img.addTag("TESTTAG1");
		img.addTag("TESTTAG2");
		TagsPanel tp = new TagsPanel(pcs);
		TreeSet<String> tags = new TreeSet<String>();
		tags.add("TESTTAG1");
		tags.add("TESTTAG2");
		tags.add("TESTTAG3");
		pcs.addPropertyChangeListener(tp);
		pcs.firePropertyChange(PropertyNames.MODEL_TAGS_CHANGED, null, tags);
		pcs.firePropertyChange(PropertyNames.MODEL_ACTIVE_IMAGE_CHANGE, null, img);
		if(tp.getNrOfTags()==3&&tp.getNrOfSelectedTags()==2){
			return true;
		}
		else{
			return false;
		}
		
		
	}
	@Test
	public boolean testLoadingTagsFromImageWithoutTags(){
		ExtendedImage img = new ExtendedImage();
		PropertyChangeSupport pcs = new PropertyChangeSupport(this);
		TagsPanel tp = new TagsPanel(pcs);
		TreeSet<String> tags = new TreeSet<String>();
		tags.add("TESTTAG1");
		tags.add("TESTTAG2");
		tags.add("TESTTAG3");
		pcs.addPropertyChangeListener(tp);
		pcs.firePropertyChange(PropertyNames.MODEL_TAGS_CHANGED, null, tags);
		pcs.firePropertyChange(PropertyNames.MODEL_ACTIVE_IMAGE_CHANGE, null, img);
		if(tp.getNrOfTags()==3 && tp.getNrOfSelectedTags()==0){
			return true;
		}
		else{
			return false;
		}
	}
	@Test
	public boolean testLoadingTagsFromNull(){
		ExtendedImage img = null;
		PropertyChangeSupport pcs = new PropertyChangeSupport(this);
		TagsPanel tp = new TagsPanel(pcs);
		TreeSet<String> tags = new TreeSet<String>();
		tags.add("TESTTAG1");
		tags.add("TESTTAG2");
		tags.add("TESTTAG3");
		pcs.addPropertyChangeListener(tp);
		pcs.firePropertyChange(PropertyNames.MODEL_TAGS_CHANGED, null, tags);
		pcs.firePropertyChange(PropertyNames.MODEL_ACTIVE_IMAGE_CHANGE, null, img);
		if(tp.getNrOfTags()==3 && tp.getNrOfSelectedTags()==0){
			return true;
		}
		else{
			return false;
		}
	}
}
