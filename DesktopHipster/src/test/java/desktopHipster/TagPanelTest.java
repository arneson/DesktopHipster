package desktopHipster;

import static org.junit.Assert.*;

import java.beans.PropertyChangeSupport;

import model.ExtendedImage;

import org.junit.Test;

import view.TagsPanel;


public class TagPanelTest {
	public TagPanelTest(){
		assertTrue(testLoadingTagsFromImageWithTags());
		assertTrue(testLoadingTagsFromImageWithoutTags());
		
	}
	@Test
	public boolean testLoadingTagsFromImageWithTags(){
		ExtendedImage img = new ExtendedImage();
		img.addTag("TESTTAG1");
		img.addTag("TESTTAG2");
		TagsPanel tp = new TagsPanel(new PropertyChangeSupport(this));
		tp.loadActiveTagsFromImage(img);
		if(tp.getItems().size()==2){
			System.out.print(tp.getItems().size());
			return true;
		}
		else{
			return false;
		}
		
		
	}
	@Test
	public boolean testLoadingTagsFromImageWithoutTags(){
		ExtendedImage img = new ExtendedImage();
		TagsPanel tp = new TagsPanel(new PropertyChangeSupport(this));
		tp.loadActiveTagsFromImage(img);
		if(tp.getItems().size()==0){
			return true;
		}
		else{
			return false;
		}
	}
}
