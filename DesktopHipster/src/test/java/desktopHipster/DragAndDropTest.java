package desktopHipster;

import static org.junit.Assert.*;

import org.junit.Test;

public class DragAndDropTest {
	@Test
	public void main(){
		DDFrameTest frameTest = new DDFrameTest();
		assertTrue(frameTest.testDropImage());
		assertFalse(frameTest.testDropNotImage());
		assertFalse(frameTest.testDropNotImageWithImageExtension());	
	}
}
