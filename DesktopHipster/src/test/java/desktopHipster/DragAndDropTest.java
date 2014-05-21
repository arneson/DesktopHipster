package desktopHipster;

import static org.junit.Assert.*;

import org.junit.Test;

public class DragAndDropTest {
	@Test
	public void main(){
	
		DDFrameTest frameTest = new DDFrameTest();
		//Test if real image is accepted by file filter
		assertTrue(frameTest.testDropImage());
		//Test if file which is not an image is accepted by file filter
		assertFalse(frameTest.testDropNotImage());
		//Test if file with image file ending which is nor really an image is accepted by file filter
		assertFalse(frameTest.testDropNotImageWithImageExtension());	
		//See if tray icon can be created
		assertTrue(frameTest.testTrayIcon());
		//Test if it can handle nulls
		assertFalse(frameTest.testDropNull());
	}
}
