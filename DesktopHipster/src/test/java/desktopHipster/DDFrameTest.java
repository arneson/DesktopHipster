package desktopHipster;

import java.awt.HeadlessException;
import java.awt.dnd.DropTargetDropEvent;

import org.junit.Test;

import dragNdrop.DDFrame;
public class DDFrameTest {
	@Test
	public boolean testDropImage(){
		try{
			DDFrame testFrame = new DDFrame();
			//testFrame.drop(new DropTargetDropEvent());
		}
		catch (HeadlessException he){
			return false;
		}
		return true;
	}
	/*
	@Test
	public boolean testDropNotImage(){
		DDFrame testFrame = new DDFrame();
		return false;
	}
	@Test
	public boolean testDropNotImageWithImageExtension(){
		DDFrame testFrame = new DDFrame();
		return false;		
	}*/
	
}
